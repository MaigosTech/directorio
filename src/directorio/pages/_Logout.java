package directorio.pages;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import directorio.models.UserSession;
import directorio.models.session.Sesiones;

public class _Logout {
	public static Logger log = Logger.getLogger(_Logout.class.getName());
	@SessionState private UserSession usuario;
	@Inject private RequestGlobals request;
	@Inject private Request req;
	
	public Object onActivate() {
		log.debug("en onActivate de Logout");
		usuario = null;
		try {
			HttpSession session = request.getHTTPServletRequest().getSession(); 
			log.debug("usuario:" + session.getAttribute("state:Login::usuario"));
			Sesiones.remove((String)session.getAttribute("state:Login::usuario"));
			log.debug("sesiones activas: " + Sesiones.print());
			log.debug("Inicia Invalidando sesion...");
			req.getSession(false).invalidate();
			//session.invalidate(); //Falla al redireccionar a login.
			log.debug("Termina Invalidando sesion...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Login";
	}

}
