package directorio.models.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import directorio.pages.Logout;

public class Sesiones implements Serializable {
	public static Logger log = Logger.getLogger(Sesiones.class.getName());
	private static ArrayList<ObjSesion> lista = new ArrayList();
	
	public static boolean checkAndAdd(String obj, String ip) {
		if (contains(obj))
			return false;
		
		add(obj, ip);
		return true;
	}
	
	public static void add(String usuario, String ip) {
		log.debug("Agregando usuario a la lista de sesiones: " + usuario);
		lista.add(new ObjSesion(usuario, new Date(), ip));
	}
	
	public static boolean remove(String obj) {
		log.debug("######## La lista es de " + lista.size());		
		log.debug("######## String de obj es " + obj);
		
		for (int i=0; i<lista.size(); i++) {
			log.debug("######## Usuario " + lista.get(i).usuario);
			if (lista.get(i).usuario.equals(obj)) {
				lista.remove(i);
				log.debug("Se removio sesión de usuario: " + obj );
				return true;
			}
		}
			
		return false;
	}
	
	public static boolean contains(String obj) {
		for (int i=0; i<lista.size(); i++)
			if (lista.get(i).usuario.equals(obj))
				return true;
		return false;
	}
	
	public static String print() {
		return lista.toString();
	}

	public static ArrayList<ObjSesion> getLista() {
		return lista;
	}
	
	public static boolean checkWithIP(String userId, String remoteAddr) {
		log.debug("Userid: " + userId + ", remoteAddr:" + remoteAddr);
		for (int i=0; i<lista.size(); i++) {
			log.debug(lista.get(i).toString());
			if (lista.get(i).usuario.equals(userId)) {
				String ip = lista.get(i).ip;
				log.debug("ip:" + ip + "**, remoteAddr: " + remoteAddr + "**");
				if (remoteAddr.equals(ip))
					return true;
				else 
					return false;
			}
		}
		return false;
	}
	
}
