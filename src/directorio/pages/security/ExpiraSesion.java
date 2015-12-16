package directorio.pages.security;

import java.util.ArrayList;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.models.session.ObjSesion;
import directorio.models.session.Sesiones;
import directorio.pages.OcraBasePage;

public class ExpiraSesion extends OcraBasePage {
	@Persist @Property private String mensaje;
	@Property private ObjSesion sesion;
	
	public ArrayList<ObjSesion> getSesiones() {
		return Sesiones.getLista();
	}
	
	public void onActionFromExpirar(String usuario) {
		log.debug("expirar: " + usuario);
		Sesiones.remove(usuario);
	}

    public void beginRender() {
    	log.debug("beginRender de ExpiraSesion");
	}
	
	void onActivate(String origen) {
		log.debug("onActivate CambiaPassword, origen: " + origen);
		init();
	}
	
	private void init() {
		mensaje = null;
	}
}
