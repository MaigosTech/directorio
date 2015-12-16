package directorio.pages.admindirectorio;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.upload.services.UploadedFile;

import directorio.annotations.InjectSelectionModel;
import directorio.models.Compania;
import directorio.models.CompaniaHistorico;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.Ramo;
import directorio.pages.OcraBasePage;
import directorio.pages.consulta.Consulta;
import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

public class MuestraCompania extends OcraBasePage{
	
	@Property
	private String titulo;
	@Property
	private String errorTmp;
	@Persist
	@Property
	private Compania compania;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCapitalO = Global.getCapitalOrigen();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaTipoM = Global.getTipoMercado();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCanalV = Global.getCanalVenta();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCapitalS = Global.getCapitalSocial();
	@InjectPage
	private Consulta consulta;
	@Property
	private HashMap mapaHistorico;
	@Property
	private HashMap oficina;
	@InjectPage
	private MuestraHistorico muestraHistorico;
	@InjectPage
	private OficinaCompania oficinaCompania;
	@Property
	private Object nombrePantalla;
	@Persist
	@Property
	private List listaRamos;
	@Property
	private Ramo ramo;
	
	//Subiendo Logo
	@Property 
	private UploadedFile logo;
	
	//Ramos
	@Property
	private Compania cia;
	@Property
	private boolean autos;
	@Property
	private boolean vida;
	@Property
	private boolean aye;
	@Property
	private boolean danos;
	@Property
	private boolean reaseg;
	@Property
	private boolean pension;
	@Property
	private boolean salud;
	
	public void muestraCompania(int idCompania){
		log.debug("Mostrando compania con id: " + idCompania);
		compania = Compania.loadFromId(idCompania);
		listaRamos = compania.getListaRamos(idCompania);		
	}
	
	void onSelectedFromModificaCompania(){
		
		if(insertaHistorico(compania.getId(), getUsuarioSesion().getPersona().getIdPersona()) != -1){
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE COMPANIA SET ")
			.append("ID_CIA = ").append(compania.getId()).append(", ")
			.append("RAZON_SOCIAL = '").append(compania.getRazonSocial()).append("', ")
			.append("NOMBRE_CORTO = '").append(compania.getNombre()).append("', ")
			.append("RUTA_LOGO = '").append(getRutaLogoActual()).append("', ")
			.append("FECHA_FUNDACION = '").append(UtilsFechas.formatoMesLetraSybase(compania.getFechaFundacion())).append("', ")
			.append("PAGINA_WEB = '").append(compania.getPaginaWeb()).append("', ")
			.append("AFILIADA_AMIS = ").append(compania.getAfiliadaAmis()).append(", ")
			.append("ACCIONES_INMOBILIARIA_AMIS = ").append(compania.getAccionesInmobiliariaAmis()).append(", ")
			.append("CERTIFICADO_APORTACION_PATRIMO = ").append(compania.getCertificadoAportacionPatrimonial()).append(", ")
			.append("ID_CAPITAL_ORIGEN = ").append(compania.getCapitalOrigen().getId()).append(", ")
			//TODO .append("GRUPO_FINANCIERO = ").append(compania.getGrupoFinaciero()).append(", ")
			.append("GRUPO_FINANCIERO = ").append(1).append(", ")
			.append("ID_TIPO_MERCADO = ").append(compania.getTipoMercado().getId()).append(", ")
			.append("ID_CANAL_VENTAS = ").append(compania.getCanalVenta().getId()).append(", ")
			.append("ID_CAPITAL_SOCIAL = ").append(compania.getCapitalSocial().getId()).append(", ")
			.append("ESTADO = '").append(compania.getEstado()).append("', ")
			.append("FECHA_ALTA = '").append(UtilsFechas.formatoMesLetraSybase(compania.getFechaAlta())).append("' ")
			.append("WHERE ID_CIA = ").append(compania.getId());			
			log.debug("Respaldo a Historico genereado correctamente: Actualizando Compañia... " + sql.toString());			
			if(Compania.creaCompania(sql.toString()) != -1){
				log.debug("Compañia actualizada correctamente. Actualizando ramos...");
				int res = compania.insertaRamos(autos, vida, aye, 
						danos, reaseg, pension, salud, compania.getId());				
				if(res != -1){
					log.debug("Ramos de compañia actualizados correctamente");
					nombrePantalla = consulta;
				}else{
					log.debug("Ocurrio un problema actualizando los ramos de la compañia");
				}
			}else{
				log.debug("Ocurrio un problema al actualizar la compañia");
			}
		}else{
			log.debug("Ocurrio un error al insertar el histórico de la compañia: " + compania.getNombre());
		}
	}
	
	Object onActionFromDetalleHistorico(int idHistorial){
		muestraHistorico.muestraCompaniaHistorico(idHistorial);
		return muestraHistorico;
	}
	
	Object onActionFromActualizaOficina(int idOficina){
		oficinaCompania.modificaOficina(compania.getId(), idOficina);
		return oficinaCompania;
	}
	
	Object onSuccess(){
		return nombrePantalla;
	}
	
	public List getListaMapasHistorico(){
		
		String sql = "SELECT ID_CIA, ID_HISTORIAL, FECHA_MOD, RAZON_SOCIAL FROM COMPANIA_HISTORICA WHERE ID_CIA = " + compania.getId();
		
		try{
			List listaMapasPersona = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);			
			List listaMapaPersona = (List)listaMapasPersona;						
			return listaMapaPersona;			
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	public List getListaOficinas(){
		
		String sql = "SELECT * FROM OFICINA WHERE ID_CIA = " + compania.getId();
		
		try{
			List listaMapasOficina = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			
			List listaMapaOficina = (List)listaMapasOficina;
						
			return listaMapaOficina;
			
		}catch(IndexOutOfBoundsException e){
			return null;
		}				
	}
	
	public String getNombreCia(String idCia){
		return CompaniaHistorico.getNombreCia(idCia);
	}
	
	public boolean getRol(String rol){		
		String rolUsuarioSesion = getUsuarioSesion().getRol().getNombre();
		int areaUsuarioSesion = getUsuarioSesion().getPersona().getArea().getId();
		
		log.debug("Checando Rol:" + rolUsuarioSesion);
		if(rolUsuarioSesion.equalsIgnoreCase(rol)){			
			return true;		
		}else
			return false;
	}
	
	public void onSelectedFromAgregaOficina(){		
		oficinaCompania.creaOficina(compania.getId());
		nombrePantalla = oficinaCompania;
	}
	public String getChecked(int idRamo){
		if(listaRamos != null){
			HashMap mapa;
			String s = null;
			for(int i = 0; i<listaRamos.size(); i++){
				mapa = (HashMap)listaRamos.get(i);				
				if(Integer.parseInt((String)mapa.get("idramo")) == idRamo)
					s = "checked";
			}
			return s;
			
		}else{
			return null;			
		}	
	}
	
	public int insertaHistorico(int idCompania, int idPersonaModifica){
		int res;
		String sql = "SELECT * FROM COMPANIA WHERE ID_CIA = " + idCompania;
		int idHistorico = Compania.obtenUltimoIdHistorico();
		HashMap mapa = new UtilsBD().queryMapa(sql);
		String fechaModificacion = UtilsFechas.formatoMesLetraSybase(new Date());
		String sqlHist = "INSERT INTO COMPANIA_HISTORICA VALUES (" +
						 Integer.parseInt((String)mapa.get("id_cia")) + ", " +
						 idHistorico + ", " +
						 idPersonaModifica + ", '" +
						 (String)mapa.get("razon_social") + "', '" +						 
						 (String)mapa.get("nombre_corto") + "', '" +
						 setLogoHistorico(idHistorico) + "', '" +
						 (String)mapa.get("fecha_fundacion") + "', '" +
						 (String)mapa.get("pagina_web") + "', " +
						 (String)mapa.get("afiliada_amis") + ", " +
						 (String)mapa.get("acciones_inmobiliaria_amis") + ", " +
						 (String)mapa.get("certificado_aportacion_patrimo") + ", " +
						 (String)mapa.get("id_capital_social") + ", " +
						 (String)mapa.get("id_capital_origen") + ", " +
						 (String)mapa.get("grupo_financiero") + ", " +
						 (String)mapa.get("id_tipo_mercado") + ", " +
						 (String)mapa.get("id_canal_ventas") + ", '" +
						 (String)mapa.get("estado") + "', '" +
						 (String)mapa.get("fecha_alta") + "', '" +						 						
						 fechaModificacion + "')";
		log.debug("Insertando historico de la compañia: " + sqlHist.toString());
		res = new UtilsBD().update(sqlHist);
		if (res != -1){
			if(compania.insertaRamosHistorico(idCompania, idHistorico) != -1){
				return 0;
			}else{
				log.debug("No se pudo insertar el ramo historico");
				return -1;
			}
		}else{
			log.debug("No se pudo poner el histórico de la compañia");
			return -1;
		}		
	}
	
	public String getLogoActual(){
		log.debug("El logo esta en: " + compania.getRutaLogo());		
		File dir = new File(getRutaTemporal() + "compania_" + compania.getId());
		if(dir.exists()){
			log.debug("El directorio temporal ya existe");
		}else{
			log.debug("No existe el directorio temporal creandolo");
			try{
				dir.mkdir();
			}catch(SecurityException e){
				log.debug("Hubo un problema para crear el directorio temporal");
				return null;
			}
		}
		try{
			File logoTmp = new File(dir + "/actual.jpg");
			FileUtils.copyFile(new File(compania.getRutaLogo()), logoTmp);
			StringBuilder ruta = new StringBuilder();
			ruta.append("/directorioamis/logosTmp/compania_").append(compania.getId()).append("/").append("actual.jpg");
			return ruta.toString();			
		}catch(IOException e){
			log.debug("Hubo un problema para copiar el logo a temporales");
			return null;
		}
	}
	
	public String getRutaLogoActual(){		
		if(logo != null){
			File dir = new File(getRutaLogo() + "compania_" + compania.getId());
			if(dir.exists()){
				log.debug("El directorio " + "compania_" + compania.getId() + " ya existe");
			}else{
				log.debug("No existe el directorio " + "compania_" + compania.getId() + " creandolo");
				try{
					dir.mkdir();
				}catch(SecurityException e){
					log.debug("Hubo un problema para crear el directorio");
				}			
			}
			
			File copied = new File(dir + "/" + logo.getFileName());
			log.debug("Se copiara en: " + copied);
			logo.write(copied);
			log.debug("Se actualizo correctamente el logo");
			
			return copied.toString();
		}else{
			return compania.getRutaLogo();
		}		
	}
	
	public String setLogoHistorico(int idHistorico){
		log.debug("El logo actual esta en: " + compania.getRutaLogo());
		File logoActual = new File(compania.getRutaLogo());		
		File logoHist = new File(getRutaLogo() + "compania_" + compania.getId() + "/" + logoActual.getName() + "_" + idHistorico);
		try{
			FileUtils.copyFile(logoActual, logoHist);
			return logoHist.toString();
		}catch(IOException e){
			log.debug("Hubo un problema para respaldar el logo");
			return null;
		}
	}
}