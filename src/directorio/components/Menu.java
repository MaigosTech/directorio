package directorio.components;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.SessionState;

import directorio.models.Rol;
import directorio.models.UserSession;
import directorio.pages.OcraBasePage;

/** 
 * Controla el funcionamiento del menu de PT, en lo referente a los permisos.
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class Menu 
{
	public static Logger log = Logger.getLogger(Menu.class.getName());
	@SessionState
	private UserSession visit;
	
    public void beginRender() {
    	log.debug("beginRender de Menu");
	}
	
	public boolean validaPermiso(String permiso) {
		Rol rol = visit.getUsuario().getRol();
		log.debug("rol:" + rol + ", permiso: " + permiso);
		if (rol != null && rol.startsWith(permiso.toUpperCase()))
			return true;
		return false;
	}
}
