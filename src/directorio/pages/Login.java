package directorio.pages;

import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import directorio.models.UserSession;
import directorio.models.Usuario;
import directorio.models.session.Sesiones;
import directorio.pages.administracion.CambiaPassword;
import directorio.pages.consulta.Consulta;
import directorio.utils.Propiedades;


public class Login {
	private static final long DIAS_60 = 1000l * 60l * 60l * 24l * (long)Integer.parseInt(Propiedades.get("expirapassword.dias"));
	private static Logger log = Logger.getLogger(Login.class.getName());
	private static Random rnd = new Random();
	
	@Inject private RequestGlobals requestGlobals;
    
	@Persist @Property private String usuario;
	@Persist @Property private String password;
	
	@Persist @Property private String error;
	@Persist @Property private String errorLoginDuplicado;
	
	@Property private String prueba;
	
	@SessionState(create=true) private UserSession usuarioSesion;
	
	@InjectPage private Consulta consulta;
	@InjectPage private CambiaPassword cambiaPassword;
	
	@Component
	private Form loginform;
    
	void onValidateFormFromloginform(){
		log.debug("on Validate");
    	loginform.clearErrors();
		if(usuario!=null) usuario.toUpperCase();
	    if(password!=null) password.toUpperCase();
		if(usuario!=null && password!=null){
			try{
				Usuario miUsuario = /*Usuario.usuarioPrueba(usuario, password);*/  Usuario.loadFromLogin(usuario,password);				
				log.debug("miUsuario: " + miUsuario.toString());
				usuarioSesion.setUsuario(miUsuario);
				Sesiones.remove(usuario);
				if (Sesiones.checkAndAdd(miUsuario.getLogin(), requestGlobals.getHTTPServletRequest().getRemoteAddr()) == false) {
					usuarioSesion.setUsuario(miUsuario);
					loginform.recordError("Error de Login Duplicado");
					errorLoginDuplicado = "error";
				}
			}catch(Exception e){
				loginform.recordError("Error de Base de Datos");
				log.error(e.getMessage());
			}
		}
		else 	loginform.recordError("Error de Base de Datos");
		
		if(usuarioSesion!=null)
    		if(!usuarioSesion.validaUsuario())
    			loginform.recordError("Error de Usuario");
	}
	
    Object onSuccessFromLoginform(){
		// Checo si ha expirado
    	/*Usuario miUsuario = usuarioSesion.getUsuario();  TODO// ****Esto lo Usare cuando este la base ****
		if (miUsuario.lastUpdate == null)
			miUsuario.actualizaLastUpdate();
		else if ((System.currentTimeMillis() - miUsuario.lastUpdate.getTime())
					> DIAS_60) {
			ArrayList listaTemp = new ArrayList();
			listaTemp.add("admin");
			miUsuario.rol.backupPermisos();
			miUsuario.rol.listaPermisos = listaTemp;
			cambiaPassword.setMensajePagina("Su contrase&ntilde;a ha expirado, favor de modificarla."); //Netweaver no lo procesa.
			//index.setMensajeCambioPasswordIndex("Su contrase&ntilde;a ha expirado, favor de modificarla. Ir a: Admin > Cambio de Contrase&ntilde;a");
			return cambiaPassword;
		}*/
    	return consulta;
    }
    
    Object onFailureFromLoginform(){
    	//log.debug("Errores: " + loginform.getHasErrors());
    	log.debug("Errores: " + loginform.getHasErrors());
    	if (errorLoginDuplicado == null)
    		error = "Usuario o contrase&ntilde;a inv&acute;lidos";
		usuario = password = "";
		return this;
    }
    
    void onSubmitFromLoginform(){
    	log.debug("LoginForm submitted");
    }
    

	 /** 
	  * Devuelve un numero que depende de la cantidad de imagenes
	  * que hay en el directorio /graphics/head/, y que es aleatorio,
	  * para que siempre se muestre una imagen distinta. 
	  * 
	  * @return 
	  */
	 public String getRandom() {
		 int max = Integer.parseInt(Propiedades.get("head.numImg"));
		 String num = String.valueOf(rnd.nextInt(max) + 1);
		 return num;
		 //return (num.length()==1)? "0" + num : num ;
	 }
}
