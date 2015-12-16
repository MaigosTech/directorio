package directorio.models;

/** 
 * Representa a un usuario, para la lista de seleccion de mail_cc
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class UsuarioSimple extends Entidad {
	public UsuarioSimple(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}
