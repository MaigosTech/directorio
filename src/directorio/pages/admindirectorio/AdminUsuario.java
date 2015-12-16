package directorio.pages.admindirectorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.annotations.InjectSelectionModel;
import directorio.misc.EntidadFactory;
import directorio.models.Area;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.Persona;
import directorio.models.Rol;
import directorio.models.Usuario;
import directorio.pages.OcraBasePage;
import directorio.pages.consulta.Consulta;
import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

@IncludeJavaScriptLibrary("utils.js")
public class AdminUsuario extends OcraBasePage{
	public static Logger log = Logger.getLogger(AdminUsuario.class.getName());
	@Property
	private String titulo;
	@Property
	private String errorTmp;
	@Persist
	@Property
	private String estatus;
	@Property
	private String loginUsuario;
	@Property
	private String contrasena;
	@Property
	private String recontrasena;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaRoles = Global.getRoles();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaAreas = Global.getAreas();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List <Entidad> listaPersonas = getPersonas();
	@Property
	private boolean muestraTabla;
	@Property
	private String buscaUsuario;
	@Property
	private String estado;
	@Property
	private Rol rol;
	@Property
	private Area area;
	@Property
	private Persona persona;
	@Property
	private Usuario usuariod;
	@InjectPage
	private ModificaUsuario modifica;
	@InjectPage
	private Consulta consulta;
	
	public List<Usuario> getUsuarios(){
		String sql = "SELECT * FROM USUARIO";		
		try{
			List listaMapasUsuario = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);		
			List listaMapaUsuario = (List)listaMapasUsuario;		
			List<Usuario> listaUsuario = new ArrayList<Usuario>();		
			for(int i=0; i<listaMapaUsuario.size(); i++){
				HashMap mapaUsuario = (HashMap)listaMapaUsuario.get(i);
				listaUsuario.add(Usuario.loadFromMapa(mapaUsuario));
			}		
			return listaUsuario;			
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	void onSubmitFromFormDatosIniciales(){
		
		String fechaActual = UtilsFechas.formatoMesLetraSybase(new Date());
		String sql = "INSERT INTO USUARIO " +
				"VALUES (" + persona.id + ", " + rol.id + ", '" + loginUsuario + "', '" + contrasena + "', '" + fechaActual + "', '" +
				estado + "', '" + fechaActual + "', " + "NULL)";		
		if(verificaExistencia(persona.id)){
			errorTmp = "Ya existe un Usuario asignado a esta Persona";
			log.debug("Mensaje: " + errorTmp);
		}
		else
			Usuario.creaUsuario(sql);		
	}
	
	Object onActionFromModifica(int usuariodIdPersona){
		estatus = "modificaUsuario";
		log.debug("estatus: " + estatus + " usuarioId: " + usuariodIdPersona);
		modifica.modificaPersona(usuariodIdPersona);
		return modifica;
	}
	
	public void beginRender() {
    	titulo = "Alta Usuario";
    	log.debug("beginRender de AltaUsuario, estatus:" + estatus);
    	if (estatus != null && estatus.equals("datosUsuarioDirectorio"))
    			errorTmp = null;
	}
	
	Object onActionFromRegresa(){
		return consulta;
	}
	
	private boolean verificaExistencia(int idPersona){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID_PERSONA FROM USUARIO WHERE ID_PERSONA = ").append(idPersona);
		log.debug(sql.toString());
		HashMap mapa = new UtilsBD().queryMapa(sql.toString());
		log.debug("El mapa de usuarios: " + mapa);
		if(mapa != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean getEstatusPagina(String cadena){
		// Esto lo hago porque el estatus 'primera' y el nulo dirigen al mismo lugar.
		log.debug("Checando cadena: " + cadena);
		log.debug("Checando estatus: " + estatus);
		if (estatus == null && cadena.equals("datosUsuarioDirectorio"))
			return true;
		if (estatus != null && cadena.equals("entradaModificacion") && estatus.equals("entradaAlta"))
			return true;
		if (estatus != null && estatus.equals(cadena))
			return true;
		return true;
	}
	
	private List<Entidad> getPersonas(){
		int tipoEntidad = Entidad.ENTIDAD_PERSONA;
		List listaDestino = new ArrayList();
		try{
			List entidadesBD = Entidad.getEntidades(tipoEntidad);
			// Agrego el 'seleccione una opcion'						
			Entidad entidadSeleccione = EntidadFactory.initEntidad(tipoEntidad, -1, "Seleccione una opci\u00F3n");
			listaDestino.add(entidadSeleccione);
			for (int i=0; i<entidadesBD.size(); i++) {
				HashMap mapa = (HashMap)entidadesBD.get(i);
				Entidad entidad = null;				
					entidad = EntidadFactory.initEntidad(tipoEntidad, 
													Integer.parseInt(mapa.get("id").toString()),
													mapa.get("descripcion").toString());
				listaDestino.add(entidad);
			}
			return listaDestino;
		}catch (Exception e) {
			log.debug("Error inicializando entidad: " + tipoEntidad + ":" + 
					e.getMessage());
			return listaDestino;
		}
	}
}