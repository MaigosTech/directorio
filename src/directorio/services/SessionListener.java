package directorio.services;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import directorio.models.session.Sesiones;
import directorio.utils.Propiedades;

public class SessionListener implements HttpSessionListener {
	public static Logger log = Logger.getLogger(SessionListener.class.getName());
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		log.debug("sesion iniciada:" + imprimeEnum(sessionEvent));
		log.debug("usuario:" + sessionEvent.getSession().getAttribute("state:Login::usuario"));
		log.debug("sesiones activas: " + Sesiones.print());
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		//Nada que hacer...
	}
	
	private String imprimeEnum(HttpSessionEvent sessionEvent) {
		
		StringBuffer buf = new StringBuffer("Elementos de la sesion: ");
		
		try {
			
			if (sessionEvent == null) 
				return "sessionEvent nulo";
			
			HttpSession session = sessionEvent.getSession();
			if (session == null)
				return "session nulo";
			
			Enumeration en = session.getAttributeNames();
			
			buf = new StringBuffer("Elementos de la sesion: ");
			while (en.hasMoreElements())
				buf.append(en.nextElement().toString()).append(" - ");
		} 
		catch (Exception e) {
			//TODO Si persiste el problema en el cierre de sesion, imprimir el trace de la excepcion.
		}
		return buf.toString();		
		
	}

}
