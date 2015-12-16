package directorio.pages.admindirectorio;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;

import directorio.annotations.InjectSelectionModel;
import directorio.models.Area;
import directorio.models.Compania;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.Oficina;
import directorio.models.Persona;
import directorio.models.TipoPersona;
import directorio.pages.consulta.Consulta;
import directorio.utils.UtilsFechas;

@IncludeJavaScriptLibrary("utils.js")
public class AdminPersona {
	public static Logger log = Logger.getLogger(AdminPersona.class.getName());
	@Property
	private String errorTmp;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCia = Global.getCompania();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaTipoPersona = Global.getTipoPersona();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaArea = Global.getAreas();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaOficina = Global.getOficinas();
	@Property
	private Compania cia;
	@Property
	private TipoPersona tipoPersona;
	@Property
	private Area area;
	@Property
	private Oficina oficina;
	@Property
	private String titulo;
	@Property
	private String nombre;
	@Property
	private String paterno;
	@Property
	private String materno;
	@Property
	private String puesto;
	@Property
	private String telefonocia;
	@Property
	private String telefonodir;
	@Property
	private String telefonocasa;
	@Property
	private String telefonocel;
	@Property
	private String facebook;
	@Property
	private String whatsapp;
	@Property
	private String twitter;
	@Property
	private String emailcia;
	@Property
	private String emailp;
	@Property
	private String estatus;
	@Property
	private String observaciones;
	@Property
	private String estado;
	@InjectPage
	private Consulta consulta;
	
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
	
	public boolean getEstatusPagina(String cadena){
		if (estatus == null)
			return true;
		return false;
	}
	
	public List<TipoPersona> getListaTipoPersona(){
		
		return Global.getTipoPersona();
	}
	
	void onSubmitFromFormDatosPersona(){
		String fechaActual = UtilsFechas.formatoMesLetraSybase(new Date());
		
		StringBuilder sql = new StringBuilder();
		
		int IdPersona = Persona.obtenUltimoId();
		
		sql.append("INSERT INTO PERSONA VALUES (").append(IdPersona).append(", ").append(cia.id).append(", ")
			.append(oficina.id).append(", ").append(tipoPersona.id).append(", ").append(area.id).append(", ").append(isNull(titulo))
			.append(isNull(paterno)).append(isNull(materno)).append(isNull(nombre))
			.append(isNull(puesto)).append(isNull(telefonocia)).append(isNull(telefonodir))
			.append(isNull(telefonocasa)).append(isNull(telefonocel)).append(isNull(facebook))
			.append(isNull(whatsapp)).append(isNull(twitter)).append(isNull(emailcia))
			.append(isNull(emailp)).append("'").append(estado).append("', '").append(fechaActual).append("', ").append(isNullLast(observaciones));
		log.debug("Creando Persona: " + sql.toString());
		Persona.creaPersona(sql.toString());
		log.debug("Insertando Niveles");
		
		Persona persona = new Persona();
		
		persona.insertaNiveles(comiteEjecutivo, directorGeneral, expresidentes, directoresFinanzas, 
			asistentesDG, vocal, contacto, aseguradoresD, oii, ocra, IdPersona);
	}
	
	private String isNull(String campo){
		if (campo != null)
			return "'" + campo + "', ";
		else
			return "'', ";
	}
	
	private String isNullLast(String campo){
		if (campo != null)
			return "'" + campo + "') ";
		else
			return "'') ";
	}
}