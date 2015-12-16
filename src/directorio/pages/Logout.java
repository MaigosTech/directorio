package directorio.pages;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import directorio.models.UserSession;
import directorio.models.session.Sesiones;

public class Logout {
	public static Logger log = Logger.getLogger(Logout.class.getName());
	@SessionState private UserSession usuario;
	@Inject private Request request;
	
	public Object onActivate() {
		log.debug("en onActivate de Logout");
		usuario = null;
		 try {
			Sesiones.remove((String)request.getSession(false).getAttribute("state:Login::usuario"));
		 } 
		 catch (Exception e) {
			e.printStackTrace();
		 }
		 finally{
			request.getSession(false).invalidate();
		 } 
		 return "Login";
	}

}
