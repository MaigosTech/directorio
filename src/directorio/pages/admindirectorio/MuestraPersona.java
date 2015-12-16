package directorio.pages.admindirectorio;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.annotations.InjectSelectionModel;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.NivelPersona;
import directorio.models.Persona;
import directorio.models.PersonaHistorico;
import directorio.pages.OcraBasePage;
import directorio.pages.consulta.Consulta;
import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

@IncludeJavaScriptLibrary("utils.js")
public class MuestraPersona extends OcraBasePage {
	@Property
	private String errorTmp;
	@Property
	private String titulo;
	@Persist
	@Property
	private Persona persona;
	@Property
	private HashMap mapaHistorico;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaTipoPersona = Global.getTipoPersona();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCia = Global.getCompania();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaArea = Global.getAreas();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaOficina = Global.getOficinas();
	@InjectPage
	private Consulta consulta;
	@InjectPage
	private MuestraHistorico muestraHistorico;
	@Persist
	@Property
	private int muestraTabla;
	@Persist
	@Property
	private List listaMapaPersona;
	@Persist
	@Property
	private List listaNivelesPersona;
	@Property
	private NivelPersona nivelPersona;
	
	//Niveles Persona
	@Property 
	private boolean comiteEjecutivo;
	@Property 
	private boolean directorGeneral;
	@Property 
	private boolean expresidentes;
	@Property 
	private boolean directoresFinanzas;
	@Property 
	private boolean asistentesDG;
	@Property 
	private boolean vocal;
	@Property 
	private boolean contacto;
	@Property 
	private boolean aseguradoresD;
	@Property 
	private boolean oii;
	@Property 
	private boolean ocra;
	
	public void muestraPersona(int idPersona){
		log.debug("Llegue " + idPersona);
		persona = Persona.loadFromId(idPersona);
		muestraTabla = getListaMapasHistorico();
		listaNivelesPersona = persona.getListaNivelesPersona(idPersona);
		log.debug("Lista Niveles persona: " + listaNivelesPersona);
		log.debug("Compañia de la persona: " + persona.getCia().toString());
		
		log.debug("Lista de compañias: " + listaCia);
		log.debug("Lista de oficinas: " + listaOficina);
	}
	
	void onSelectedFromModificaPersona(){		
		if(persona.insertaHistorico(persona.getIdPersona(), getUsuarioSesion().getPersona().getIdPersona()) != -1){
			String fechaActual = UtilsFechas.formatoMesLetraSybase(new Date());
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PERSONA SET ").append("ID_PERSONA = " + persona.getIdPersona()).append(", ")
			.append("ID_CIA = " + persona.getCia().id).append(", ")
			.append("ID_OFICINA = " + persona.getOficina().getId()).append(", ")
			.append("ID_TIPO_PERSONA = " + persona.getTipoPersona().id).append(", ")
			.append("ID_AREA = " + persona.getArea().getId()).append(", ")
			.append("TITULO = " + isNull(persona.getTitulo()))
			.append("PATERNO = " + isNull(persona.getPaterno()))
			.append("MATERNO = " + isNull(persona.getMaterno()))
			.append("NOMBRE = " + isNull(persona.getNombre()))
			.append("PUESTO = " + isNull(persona.getPuesto()))
			.append("TELEFONO_CIA = " + isNull(persona.getTelefonoCia()))
			.append("TELEFONO_DIRECTO = " + isNull(persona.getTelefonoDirecto()))
			.append("TELEFONO_CASA = " + isNull(persona.getTelefonoCasa()))
			.append("CEL = " + isNull(persona.getCel()))
			.append("FACEBOOK = " + isNull(persona.getFacebook()))
			.append("WHATSAPP = " + isNull(persona.getWhatsapp()))
			.append("TWITTER = " + isNull(persona.getTwitter()))
			.append("MAIL_EMPRESA = " + isNull(persona.getEmailEmpresa()))
			.append("MAIL_PERSONAL = " + isNull(persona.getEmailPersonal()))
			.append("ESTATUS = '" + persona.getEstatus()).append("', ")
			.append("FECHA_ALTA = '" + fechaActual).append("', ")
			.append("OBSERVACIONES = " + isNullLast(persona.getObservaciones()))
			.append("WHERE ID_PERSONA = " + persona.getIdPersona());
			
			log.debug("Respaldo a Historico genereado correctamente: Actualizando Persona... " + sql.toString());
			if(Persona.creaPersona(sql.toString()) != -1){
				log.debug("Persona actualizada correctamente. Actualizando niveles...");
				int res = persona.insertaNiveles(comiteEjecutivo, directorGeneral, expresidentes, directoresFinanzas, 
						asistentesDG, vocal, contacto, aseguradoresD, oii, ocra, persona.getIdPersona());
				if(res != -1){
					log.debug("Niveles de persona actualizados correctamente");
				}else{
					log.debug("Ocurrio un problema actualizando los niveles de persona");
				}
			}else{
				log.debug("Ocurrio un error al actualizar a la persona");
			}
		}else{
			log.debug("Ocurrio un error al insertar en historico a la persona. No se puede continuar actualizando a la persona");
		}
	}
	
	void onSelectedFromMuestraHistorico(){
		
	}
	
	public int getListaMapasHistorico(){
		String sql = "SELECT ID_PERSONA, ID_HISTORIAL, FECHA_MOD, ID_CIA, PUESTO FROM PERSONA_HISTORICA WHERE ID_PERSONA = " + persona.getIdPersona();
		try{
			List listaMapasPersona = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			listaMapaPersona = (List)listaMapasPersona;
			return 1;
		}catch(IndexOutOfBoundsException e){
			return 0;
		}
	}
	
	public String getnombreCia(String idCia){
		
		return PersonaHistorico.getNombreCia(idCia);
	}
	
	public String getChecked(int idNivelPersona){				
		
		if(listaNivelesPersona != null){
			HashMap mapa;
			String s = null;
			for(int i = 0; i<listaNivelesPersona.size(); i++){
				mapa = (HashMap)listaNivelesPersona.get(i);				
				if(Integer.parseInt((String)mapa.get("idnivelpersona")) == idNivelPersona)
					s = "checked";
			}
			return s;
			
		}else{
			return null;			
		}	
	}
	
	public boolean getRol(String rol){		
		String rolUsuarioSesion = getUsuarioSesion().getRol().getNombre();
		int areaUsuarioSesion = getUsuarioSesion().getPersona().getArea().getId();
		
		log.debug("Checando Rol:" + rolUsuarioSesion);
		if(rolUsuarioSesion.contains(rol)){			
			if((rolUsuarioSesion.equalsIgnoreCase("admindirectorio")))
				return true;
			else if((rolUsuarioSesion.equalsIgnoreCase("adminarea")) && (areaUsuarioSesion == persona.getArea().getId()))
				return true;
			else
				return false;			
		}else
			return false;
	}
	
	private String isNull(String campo){
		if (campo != null)
			return "'" + campo + "', ";
		else
			return "'', ";
	}
	
	private String isNullLast(String campo){
		if (campo != null)
			return "'" + campo + "' ";
		else
			return "'' ";
	}
	
	Object onActionFromDetalleHistorico(int idHistorial){
		muestraHistorico.muestraPersonaHistorico(idHistorial);
		return muestraHistorico;
	}
	
	Object onSuccess(){
		return consulta;
	}
}