package directorio.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.log4j.Logger;

/**
 *  Clase abstracta para crear un pool de cualquier tipo de objetos,
 *  la sincronizaci�n, generaci�n y borrado de objetos ya est� implementada,
 *  solo se necesitan sobrecargar dos m�todos, uno que devuelva una instancia
 *  del objeto deseado (este m�todo b�sicamente se implementa con la llamada de
 *  un constructor) y un evento que se llama antes de liberar el objeto, en
 *  donde se pueden agregar funciones para reinicializar el objeto, liberar
 *  una conexi�n a bd, cerrar archivos, etc.
 *
 */
public abstract class AbstractPool{
	public static Logger log = Logger.getLogger(AbstractPool.class.getName());
	 /** Utilizo un collection porque le voy quitando y poniendo elementos.
	  */
    private Collection freeObjects = new HashSet();
	/** Utilizo un mapa porque necesito llevar la cuenta de cu�ntas veces ha sido
	    usado cada objeto.*/
	 //private Map allObjects   = new HashMap();
    private int min;
    private int max;
    private int borrowedObjects = 0;

    protected AbstractPool(int min, int max){
        this.min = min;
        this.max = max;

        for(int i=0; i<min; i++)
			  addNewObject();
    }

	 /** Rutina para crear un nuevo objeto, agregarlo al arreglo "freeObjects"
	  * (los objetos que est�n en el pool) y tambi�n agregarlo al "allObjects"
	  * (los objetos que han estado en el pool) con una cuenta de 0 usos.
	  */
	 protected Object addNewObject() {
		Object obj = createObject();
		freeObjects.add(obj);
		//allObjects.put(obj, new Integer(0));
		return obj;
	 }
    
    protected abstract Object createObject();

	 /** Le suma uno al n�mero de "usos" de cada objeto 
	  * @param obj El objeto en cuesti�n
	  * @return El n�mero nuevo de usos que tiene el objeto 
	  * */
	 /*
	 public int addOne(Object obj) {
		 int valor = ((Integer)allObjects.get(obj)).intValue();
		 allObjects.put(obj, new Integer(valor + 1));
		 return valor +1;
	 }
	 */

	 /** Remueve el objeto del mapa allObjects, porque ya no va a ser utilizado. */
	 /*
	 public void resetObject(Object obj) {
		 log.debug("ResetObject: objeto " + obj);
		 allObjects.remove(obj);
	 }
	 */

	 /** Devuelve el mapa que mantiene todos los objetos de este pool.
	  */
	 /*
	 public Map getAllObjectsPool() {
		 return allObjects;
	 }
	 */

	 /** Devuelve la lista que mantiene los objetos de este pool.
	  */
	 public Collection getPool() {
		 return freeObjects;
	 }

    public synchronized Object getObject(){
        //Mientras no existan objetos disponibles
        while(freeObjects.size() < 1){
            /*  Si ya prestamos tantos objetos como ten�amos permitidos
                entonces tendremos que esperar a que se libere uno
             */
            if(borrowedObjects >= max){
                try{
                    wait();
                } catch(InterruptedException interruptedException){ }
            }
            /*  Si todav�a podemos prestar m�s, entonces creamos otro*/
            else{
					 addNewObject();
            }
        }
        /*  Prestamos un objeto del mapa: obtengo un numero aleatorio
			*  para saber cual escoger (por culpa del HashSet, que siempre
			*  utiliza el mismo orden en sus elementos) */
		  int numObjeto = (int)(Math.random() * freeObjects.size() + 1);
		  Iterator it = freeObjects.iterator();
		  Object object = null;
		  for (int i=0; i<numObjeto ; i++)
			  object = it.next();
		  freeObjects.remove(object);
		  // Si testObject devuelve true -significando que el objeto es v�lido-, 
		  // devuelvo el objeto en cuesti�n
        if(testObject(object)){
            borrowedObjects++;
            return object;
        } else {
			   log.debug("Posible desconexi�n... reintentando en 1 seg");
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) { }
				//Quit� el getObject porque estaba devolviendo objetos del pool, no creando
				//uno nuevo...
            //return getObject();
			   return addNewObject();
        }
    }

	 /** Agrego el objeto de vuelta al arreglo freeObjects, pero no al 
	  * allObjects, porque ya est� ah� y desde aqu� no se suma uno. */
    public synchronized void freeObject(Object object){
        if(beforeFreeObject(object)){
            freeObjects.add(object);
            
        }
        /*  Nos devolvieron un objeto asi que lo apuntamos y avisamos a quien
                est� esperando que ya hay un objeto libre*/
        borrowedObjects--;
        notify();
    }
    /**
     *  El m�todo se debe implementar por si se quiere hacer algo con el objeto
     *  que va a formar parte de los objetos libres, si no se quiere que el objeto
     *  vuelva a formar parte de los objetos libres, sino que la intenci�n es
     *  desecharlo, entonces el m�todo debe regresar false.
     */
    protected abstract boolean beforeFreeObject(Object object);
    /**
     *  La implementaci�n de este m�todo debe devolver true si es un objeto
     *  valido y false si es un objeto no valido, el m�todo es llamado antes
     *  de devolver un objeto en <code>getObject()</code>.  El m�todo
     *  es especialmente �til cuando se necesita probar que un objeto est�
     *  listo como una conexi�n a bd.
     */
    protected abstract boolean testObject(Object object);
}
