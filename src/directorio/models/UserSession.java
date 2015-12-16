package directorio.models;

import java.util.*;
import java.io.Serializable;
import java.security.Principal;

import directorio.models.*;

/** 
 * Clase Visit que representa la sesión en Tapestry. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class UserSession implements Serializable {
	
	/** 
	 * Usuario es el usuario que está firmado
	 */
	private Usuario usuario = null;

	public Usuario getUsuario() { return usuario; }
	public void setUsuario(Usuario u) { this.usuario = u; }
	public boolean validaUsuario() {
		return usuario != null;
	}


	/*
	private Usuario creaUsuario(Principal principal) {
		return usuario;
	}
	*/

}
