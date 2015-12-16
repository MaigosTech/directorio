package directorio.pages;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import directorio.models.UserSession;
import directorio.models.Usuario;
import directorio.utils.Propiedades;
import directorio.utils.UtilsFechas;

/** 
 * Pagina base para el resto de las paginas, implementa una validacion de la sesion.<br>
 * Todas las demas paginas deben implementar esta clase. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public abstract class OcraBasePage {
	public static Logger log = Logger.getLogger(OcraBasePage.class.getName());
	@SessionState(create=false)
	private UserSession session;
	
	private boolean sessionExists;
	@Inject
	private Response response;
    @Inject
    private ComponentResources resources;
    
    private String nombrePagina = null;
    @Property @Persist private String _noSerie;
    
    protected void setNombrePagina(String nombre) { nombrePagina = nombre; }
    protected String getNombrePagina() { return nombrePagina; }
	protected String getNoSeriePagina() { return _noSerie; }
	protected void setNoSeriePagina(String noSerie) { _noSerie = noSerie; }
	
	/*
	public Object onActivate() {
		if (session == null)
			return paginaLogin;
		return this;
	}
	*/
	
	public void pageAttached() {
		// Reviso si tiene sesion
		if (!sessionExists) {
            try {
				response.sendRedirect("/directorioamis/login");
			} catch (IOException e) { e.printStackTrace(); }
		} else {
			// Reviso si tiene permisos para esta pagina
			
			String pagina = resources.getPageName();
			log.debug("pagina:" + pagina);
			
			// Para el Index todos tienen permisos
			/*if (!pagina.equals("Index") && !validaPermiso(pagina)) {
                try {
    				response.sendRedirect("/directorioamis/login");
    			} catch (IOException e) { e.printStackTrace(); }
			}*/
		}
	}
	
	public Usuario getUsuarioSesion() {
		return session.getUsuario();
	}
	
	public String formateaFecha(Date fecha) {
		if (fecha == null)
			return null;
		//return UtilsFechas.formatoH(fecha);
		return UtilsFechas.formato(fecha);
	}
	
	public String formateaFechaConHora(Date fecha) {
		log.debug("fecha: " + fecha);
		if (fecha == null)
			return null;
		log.debug("fecha formateada:" + UtilsFechas.formatoH(fecha));
		return UtilsFechas.formatoH(fecha);
	}
	
	public String formateaMoneda(String numStr) {
		try {
			Float num = Float.parseFloat(numStr);
			NumberFormat fmt = NumberFormat.getCurrencyInstance(Locale.US);
			return fmt.format(num);
		} catch (Exception e) {
			return null;
		}
	}
	
	/* public String getNombreCia(Integer id) {
		log.debug("idCia:" + id);
		if (id == null)
			return "";
		CompaniaCombo tipo = (CompaniaCombo) Global.findById(Global.getCompanias(), id);
		return tipo.nombre;
	} */
	
	/**
	 * Devuelve un cero si el objeto TipoPersona no existe.
	 * @param tipoPersona
	 * @return
	 */
    /* public String safeId(TipoPersona tipoPersona) {
    	if (tipoPersona == null)
    		return "null";
    	if (tipoPersona.id == -1)
    		return "null";
    	return String.valueOf(tipoPersona.id);
	} */
	
	public String formateaStringComoFecha(String fecha) {
		if (fecha == null)
			return null;
		return UtilsFechas.formato(fecha);
	}
	
	public String formateaStringComoFechaConHora(String fecha) {
		if (fecha == null)
			return null;
		return UtilsFechas.formatoConHora(fecha);
	}
	
	protected String getRutaLogo() {
		//return Propiedades.get("rutaCompleta") + "logos/";
		return Propiedades.get("rutalogos");
	}
	
	protected String getRutaTemporal() {
		return Propiedades.get("rutaCompleta") + "logosTmp/";
		
	}
	
	/**
	 * Agrega automaticamente las apostrofes, pero antes revisa si es null para devolver NULL.
	 * @param inciso
	 * @return
	 */
    protected String toCadenaSQLNull(String cadena) {
    	if (cadena == null || cadena.length()==0)
    		return "NULL";
    	else return "'" + cadena + "'";
	}

}

