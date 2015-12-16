package directorio.models;

/** 
 * Representa a un origen; no tiene elemento padre. 
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 
 */
public class Origen extends Entidad {
	public Origen(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}

