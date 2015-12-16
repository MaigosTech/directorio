package directorio.models;

/** 
 * Representa a un area; no tiene elemento padre. 
 * 
 * @author Francisco Peña (pacop28@gmail.com)
 * @version 
 */
public class AreaCombo extends Entidad {
	public AreaCombo(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}