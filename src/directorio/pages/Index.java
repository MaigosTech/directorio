package directorio.pages;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.services.ExceptionReporter;

import directorio.models.UserSession;
import directorio.models.Usuario;
import directorio.utils.UtilsBD;


/** 
 * Es la pagina principal de la aplicacion, una vez que el usuario ha
 * introducido user y password en la pagina Login.
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 1.0
 */
public class Index extends OcraBasePage implements ExceptionReporter {
	@SessionState
	private UserSession session;
	@Property @Persist(PersistenceConstants.FLASH) private String _mensajeCambioPassword;
	
	@Property @Persist(PersistenceConstants.FLASH) private String mensaje;
	
	public Usuario getUsuario() {
		return session.getUsuario();
	}
	
	public void beginRender() {
	}
	
	public void setMensajeCambioPasswordIndex(String mensaje) {
		_mensajeCambioPassword = mensaje;
	}

	public void reportException(Throwable arg0) {
		mensaje = arg0.getLocalizedMessage();
	}
	
}
