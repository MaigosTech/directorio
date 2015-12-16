package directorio.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import directorio.models.Entidad;

/** 
 * Clase con utiler�as para strings 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class UtilsStrings {
	
	public final static int LOTE_NORMAL = 1;
	public final static int LOTE_CONSULTA = 2;
	
	/** 
	 * Este m�todo est� aqu� para poder implementar un split igual que 
	 * del JDK 1.4 cuando estamos bajo 1.3 
	 * 
	 * @param cadena 
	 * @param separador 
	 * @return 
	 */
	public static String[] split(String cadena, String separador) {
		if (cadena == null || cadena.length() == 0)
			return new String[0];

		ArrayList lista = new ArrayList();
		StringTokenizer tok = new StringTokenizer(cadena, separador);
		while (tok.hasMoreTokens()) {
			lista.add(tok.nextToken());
		}

		return (String[])lista.toArray(new String[lista.size()]);
	}

	/** 
	 * Sirve para convertir una List de entidades en un arreglo de Strings,
	 * para pasarlo a javascript. 
	 * 
	 * @param entidades 
	 * @return 
	 */
	public static String[] toArregloEntidades(List entidades) {
		String[] res = new String[entidades.size()];

		for (int i=0; i<entidades.size(); i++) {
			Entidad ent = (Entidad) entidades.get(i);
			res[i] = ent.id + "|" + ent.nombre;
		}
		return res;
	}
	
	/**
	 * Recibe un String, si es nulo devuelve cadena vacia.
	 * @param source String a validar
	 * @return
	 */
	public static String emptyIfIsNull(Object source){
		if (source == null)
			source = "";
		return ((String)source);
	}
	
	public static String llenaCeros( int num, int longitud) {
		String str = new Integer( num).toString();

		while( str.length() < longitud)
			str = "0" + str;
		return str;
	}

   public static String llenaCeros(String cadena, int longitud) {
		if (cadena == null)
			cadena = "";
		while( cadena.length() < longitud)
			cadena = "0" + cadena;
		return cadena;
   }
    

}
