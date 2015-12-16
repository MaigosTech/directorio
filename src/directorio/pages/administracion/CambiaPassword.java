package directorio.pages.administracion;

import java.util.HashMap;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;

import directorio.models.UserSession;
import directorio.models.Usuario;
import directorio.pages.Index;
import directorio.pages.OcraBasePage;
import directorio.utils.UtilsBD;

public class CambiaPassword extends OcraBasePage {
	@SessionState
	private UserSession visit;
	@Component
	private Form cambiaPassword;
	@InjectPage private Index index;
	
	@InjectComponent(value="anterior")
	private Field fieldAnterior;
	@InjectComponent(value="nueva")
	private Field fieldNueva;
	@InjectComponent(value="confirmacion")
	private Field fieldConfirmacion;
	
	@Persist @Property private String anterior;
	@Persist @Property private String nueva;
	@Persist @Property private String confirmacion;
	@Persist @Property private String mensaje;

    public void beginRender() {
    	log.debug("beginRender de CambiaPassword");
	}
	
	void onActivate(String origen) {
		log.debug("onActivate CambiaPassword, origen: " + origen);
		init();
	}
	
	private void init() {
		mensaje = null;
	}
	
	public Object onSuccessFromCambiaPassword() {
		log.debug("en submit, onSuccessFromCambiaPassword");
		String query = "UPDATE USUARIO SET PASSWORD = '" + nueva.toUpperCase() + "', " +
			"LAST_UPDATE = getdate() " +
			" WHERE ID = " + visit.getUsuario().getPersona().getIdPersona();
		log.debug("query: " + query);
		int res = new UtilsBD().update(query);
		log.debug("res: " + res);
		
		if (res != -1)
			mensaje = "Contrase&ntilde;a modificada correctamente";
		else
			mensaje = "Ha ocurrido un error al actualizar su contrase&ntilde;a, favor de reintentar";
		
		Usuario miUsuario = visit.getUsuario();
		log.debug("miUsuario::" + miUsuario);
		/* if (miUsuario != null && miUsuario.getRol() != null)
    		miUsuario.rol.regresaPermisos(); */
		
		index.setMensajeCambioPasswordIndex(mensaje);
		return index;
	}
	
	void onValidateForm() {
		log.debug("en validate: " + anterior + ", " + nueva + "," + confirmacion );
		if (! nueva.equals(confirmacion))
			cambiaPassword.recordError(fieldConfirmacion, "La contrase&ntilde;a y su confirmaci&oacute;n deben ser iguales.");
	}
		
	void onValidateFromAnterior(String anterior) {
		log.debug("en validateFromAnterior: " + anterior.toUpperCase() );
		/* HashMap mapa = Usuario.getDetalleUsuario(visit.getUsuario().getId());
		log.debug("mapa:" + mapa);
		if (!((String)mapa.get("password")).equals(anterior.toUpperCase()))
			cambiaPassword.recordError(fieldAnterior, "La contrasena actual es incorrecta."); */
	}
    
    public void setMensajePagina(String m) { mensaje = m; }
}
