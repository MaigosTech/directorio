package directorio.pages.admindirectorio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.models.PersonaHistorico;
import directorio.pages.OcraBasePage;
import directorio.utils.UtilsBD;

public class MuestraHistorico extends OcraBasePage {
	@Property
	private String titulo;
	@Property
	private String errorTmp;
	@Property
	private PersonaHistorico personaHistorica;
	@Property
	private String nombre;
	@Persist
	@Property
	private HashMap detallePersona;
	@Persist
	@Property
	private HashMap detalleCia;
	@Persist
	@Property
	private String tipo;	
	@Property
	private HashMap nivel;
	@InjectPage
	private MuestraPersona muestraPersona;
	@InjectPage
	private MuestraCompania muestraCia;
	
	private Object pagina;
	
	public void muestraPersonaHistorico(int idHistorial){
		String sql = "SELECT * FROM PERSONA_HISTORICA WHERE ID_HISTORIAL = " + idHistorial;		
		detallePersona = new UtilsBD().queryMapa(sql);
		tipo = "persona";				
	}
	
	public String getDescripcionFromCatalogo(String id, String catalogo){
		return PersonaHistorico.getDescripcionFromCatalogo(id, catalogo);
	}
	
	public String getNombreCia(String idCia){
		return PersonaHistorico.getNombreCia(idCia);
	}
	
	public void muestraCompaniaHistorico(int idHistorial){
		String sql = "SELECT * FROM COMPANIA_HISTORICA WHERE ID_HISTORIAL = " + idHistorial;
		detalleCia = new UtilsBD().queryMapa(sql);
		tipo = "cia";
	}
	
	public boolean muestraDetalle(String tipoDetalle){
		log.debug(tipo);
		if(tipo.contains(tipoDetalle)){
			return true;
		}
		if(tipo.contains(tipoDetalle)){
			return true;
		}
		return false;
	}
	
	public String getLogoHistorico(String idHistorico){
		
		String sql = "SELECT NOMBRE_CORTO, RUTA_LOGO FROM COMPANIA_HISTORICA WHERE ID_HISTORIAL = " + idHistorico;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		String rutaTemporal = (String)mapa.get("ruta_logo");
		log.debug("El logo historico esta en: " + rutaTemporal);
		File dir = new File(getRutaTemporal() + (String)mapa.get("nombre_corto"));
		if(dir.exists()){
			log.debug("El directorio temporal ya existe");
		}else{
			log.debug("No existe el directorio temporal creandolo");
		}
		try{
			dir.mkdir();
		}catch(SecurityException e){
			log.debug("Hubo un problema para crear el directorio temporal");
		}
		try{
			File logoHistTmp = new File(dir + "/historico.jpg");
			FileUtils.copyFile(new File((String)mapa.get("ruta_logo")), logoHistTmp);
			StringBuilder ruta = new StringBuilder();
			ruta.append("/directorioamis/logosTmp/").append((String)mapa.get("nombre_corto")).append("/").append("historico.jpg");
			return ruta.toString();
		}catch(IOException e){
			log.debug("Hubo un problema para copiar el logo a temporales");
			return null;
		}
	}
	
	public List getListaMapasNivel(String idHistorial){		
		String sql = "SELECT DESCRIPCION FROM NIVEL_PERSONA_HISTORICA NPH INNER JOIN CAT_NIVEL_PERSONA CNP ON NPH.ID_NIVEL_PERSONA = CNP.ID_NIVEL_PERSONA WHERE ID_HISTORIAL = " + idHistorial;
		try{
			List listaMapasNivel = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			listaMapasNivel = (List)listaMapasNivel;
			return listaMapasNivel;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	void onSelectedFromRegresa(){
		log.debug("usuarioId: " + (String)detallePersona.get("id_persona"));
		muestraPersona.muestraPersona(Integer.parseInt((String)detallePersona.get("id_persona")));
		pagina = muestraPersona;
	}
	void onSelectedFromRegresaCia(){
		log.debug("companiaId: " + (String)detalleCia.get("id_cia"));
		muestraCia.muestraCompania(Integer.parseInt((String)detalleCia.get("id_cia")));
		pagina = muestraCia;
	}
	
	public String getSiNo(String dato){
		if(dato.equalsIgnoreCase("1"))
			return "Sí";
		else if(dato.equalsIgnoreCase("0"))
			return "No";
		else
			return "N/A";
	}
	
	Object onSuccess(){
		return pagina;
	}
}