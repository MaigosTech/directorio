package directorio.models;

/** 
 * Representa a un pa�s; no tiene elemento padre. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class Pais extends Entidad {
	public Pais(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}
