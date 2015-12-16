package directorio.models;

/** 
 * Representa a un estado; su elemento padre es un pa�s
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class Estado extends Entidad {
	public Estado(int id, String nombre, Entidad padre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = padre;
		padre.agregaHijo(this);
	}
}
