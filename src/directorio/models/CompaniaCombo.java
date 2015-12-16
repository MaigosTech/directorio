package directorio.models;

/** 
 * Representa a un compa�ia combo; no tiene elemento padre. 
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 
 */
public class CompaniaCombo extends Entidad {
	public CompaniaCombo(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}
