package directorio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

public class Persona extends Entidad implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2982875018557585412L;
	public static Logger log = Logger.getLogger(Persona.class.getName());
	private int idPersona;
	private Compania cia;
	private Oficina oficina;
	private TipoPersona tipoPersona;
	private List<NivelPersona> nivelPersona;	
	private Area area;
	private String titulo;
	private String paterno;
	private String materno;
	private String nombre;
	private String puesto;
	private String telefonoCia;
	private String telefonoDirecto;
	private String telefonoCasa;
	private String cel;
	private String facebook;
	private String whatsapp;
	private String twitter;
	private String emailEmpresa;
	private String emailPersonal;
	private char estatus;
	private Date fechaAlta;
	private String observaciones;
	
	public Persona(){
		
	}
	
	public Persona(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public Persona(int idPersona, Compania cia, TipoPersona tipoPersona,
			List nivelPersona, Oficina oficina, Area area, String titulo,
			String paterno, String materno, String nombre, String puesto,
			String telefonoCia, String telefonoDirecto, String telefonoCasa,
			String cel, String facebook, String whatsapp, String twitter,
			String emailEmpresa, String emailPersonal, char estatus,
			Date fechaAlta, String observaciones) {
		super();
		this.idPersona = idPersona;
		this.cia = cia;
		this.tipoPersona = tipoPersona;
		this.nivelPersona = nivelPersona;
		this.oficina = oficina;
		this.area = area;
		this.titulo = titulo;
		this.paterno = paterno;
		this.materno = materno;
		this.nombre = nombre;
		this.puesto = puesto;
		this.telefonoCia = telefonoCia;
		this.telefonoDirecto = telefonoDirecto;
		this.telefonoCasa = telefonoCasa;
		this.cel = cel;
		this.facebook = facebook;
		this.whatsapp = whatsapp;
		this.twitter = twitter;
		this.emailEmpresa = emailEmpresa;
		this.emailPersonal = emailPersonal;
		this.estatus = estatus;
		this.fechaAlta = fechaAlta;		
		this.observaciones = observaciones;
	}
	
	public Persona(int idPersona, Compania cia, Area area, String titulo, String paterno, 
			String materno, String nombre, String puesto /*, char estatus*/){
		this.idPersona = idPersona;
		this.cia = cia;
		this.area = area;
		this.titulo = titulo;
		this.paterno = paterno;
		this.materno = materno;
		this.nombre = nombre;
		this.puesto = puesto;
		//this.estatus = estatus;
	}

	public int getIdPersona() {
		return idPersona;
	}



	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}



	public Compania getCia() {
		return cia;
	}



	public void setCia(Compania cia) {
		this.cia = cia;
	}



	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}



	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public List<NivelPersona> getNivelPersona() {
		return nivelPersona;
	}

	public void setNivelPersona(List<NivelPersona> nivelPersona) {
		this.nivelPersona = nivelPersona;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}

	public Area getArea() {
		return area;
	}



	public void setArea(Area area) {
		this.area = area;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getPaterno() {
		return paterno;
	}



	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}



	public String getMaterno() {
		return materno;
	}



	public void setMaterno(String materno) {
		this.materno = materno;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPuesto() {
		return puesto;
	}


	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}


	public String getTelefonoCia() {
		return telefonoCia;
	}


	public void setTelefonoCia(String telefonoCia) {
		this.telefonoCia = telefonoCia;
	}


	public String getTelefonoDirecto() {
		return telefonoDirecto;
	}


	public void setTelefonoDirecto(String telefonoDirecto) {
		this.telefonoDirecto = telefonoDirecto;
	}


	public String getTelefonoCasa() {
		return telefonoCasa;
	}


	public void setTelefonoCasa(String telefonoCasa) {
		this.telefonoCasa = telefonoCasa;
	}


	public String getCel() {
		return cel;
	}


	public void setCel(String cel) {
		this.cel = cel;
	}


	public String getFacebook() {
		return facebook;
	}


	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}


	public String getWhatsapp() {
		return whatsapp;
	}


	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}


	public String getTwitter() {
		return twitter;
	}


	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}


	public String getEmailEmpresa() {
		return emailEmpresa;
	}


	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}


	public String getEmailPersonal() {
		return emailPersonal;
	}


	public void setEmailPersonal(String emailPersonal) {
		this.emailPersonal = emailPersonal;
	}


	public char getEstatus() {
		return estatus;
	}


	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}


	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getObservaciones() {
		return observaciones;
	}
	
	
	
	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", cia=" + cia
				+ ", tipoPersona=" + tipoPersona + ", oficina=" + oficina + ", area=" + area
				+ ", titulo=" + titulo + ", paterno=" + paterno + ", materno="
				+ materno + ", nombre=" + nombre + ", puesto=" + puesto
				+ ", telefonoCia=" + telefonoCia + ", telefonoDirecto="
				+ telefonoDirecto + ", telefonoCasa=" + telefonoCasa + ", cel="
				+ cel + ", facebook=" + facebook + ", whatsapp=" + whatsapp
				+ ", twitter=" + twitter + ", emailEmpresa=" + emailEmpresa
				+ ", emailPersonal=" + emailPersonal + ", estatus=" + estatus
				+ ", fechaAlta=" + fechaAlta + ", observaciones="
				+ observaciones + "]";
	}

	public static Persona loadFromId(int idPersona){
		String sql = "SELECT * " +
						"FROM PERSONA " + "WHERE ID_PERSONA = " + idPersona;
		log.debug("Antes de ejecutar query Persona");
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return loadFromMapa(mapa);
	}
	
	public static Persona loadFromMapa(HashMap mapa){
		
		String s = (String)mapa.get("estatus");
				
		/*return new Persona(Integer.parseInt((String)mapa.get("id_persona")),
							Compania.loadFromId(Integer.parseInt((String)mapa.get("id_cia"))),
							TipoPersona.loadFromId(Integer.parseInt((String)mapa.get("id_tipo_persona"))),
							NivelPersona.getNiveles(Integer.parseInt((String)mapa.get("id_persona"))),
							Oficina.loadFromId(Integer.parseInt((String)mapa.get("id_oficina"))),							
							Area.loadFromId(Integer.parseInt((String)mapa.get("id_area"))),					
							(String)mapa.get("titulo"), 
							(String)mapa.get("paterno"),
							(String)mapa.get("materno"), 
							(String)mapa.get("nombre"),
							(String)mapa.get("puesto"),
							(String)mapa.get("telefono_cia"),
							(String)mapa.get("telefono_directo"),
							(String)mapa.get("telefono_casa"),
							(String)mapa.get("cel"),
							(String)mapa.get("facebook"),
							(String)mapa.get("whatsapp"),
							(String)mapa.get("twitter"),
							(String)mapa.get("mail_empresa"),
							(String)mapa.get("mail_personal"),
							s.charAt(0),
							UtilsFechas.parseFechaUsuario((String)mapa.get("fecha_alta")),							
							(String)mapa.get("observaciones"));		
	} */
		return new Persona(Integer.parseInt((String)mapa.get("id_persona")),
				Compania.loadFromId(Integer.parseInt((String)mapa.get("id_cia"))),
				TipoPersona.loadFromId(Integer.parseInt((String)mapa.get("id_tipo_persona"))),
				NivelPersona.getNiveles(Integer.parseInt((String)mapa.get("id_persona"))),
				Oficina.loadFromId(Integer.parseInt((String)mapa.get("id_oficina"))),
				Area.loadFromId(Integer.parseInt((String)mapa.get("id_area"))),
				(String)mapa.get("titulo"),
				(String)mapa.get("paterno"),
				(String)mapa.get("materno"),
				(String)mapa.get("nombre"),
				(String)mapa.get("puesto"),
				(String)mapa.get("telefono_cia"),
				(String)mapa.get("telefono_directo"),
				(String)mapa.get("telefono_casa"),
				(String)mapa.get("cel"),
				(String)mapa.get("facebook"),
				(String)mapa.get("whatsapp"),
				(String)mapa.get("twitter"),
				(String)mapa.get("mail_empresa"),
				(String)mapa.get("mail_personal"),
				s.charAt(0),
				UtilsFechas.parseFechaUsuario((String)mapa.get("fecha_alta")),
				(String)mapa.get("observaciones"));
	}
	
	public String getNombreCompleto(){
		return getNombre() + " " + getPaterno() + " " + getMaterno();
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public static int obtenUltimoId(){
		String sql = "SELECT MAX(ID_PERSONA)+1 AS ID FROM PERSONA";
		HashMap i = (HashMap)new UtilsBD().querySingleListSybase(sql).get(0);
		return Integer.parseInt((String)i.get("id"));
	}
	
	public static int obtenUltimoIdHistorico(){
		String sql = "SELECT MAX(ID_HISTORIAL)+1 AS ID FROM PERSONA_HISTORICA";
		HashMap i = (HashMap)new UtilsBD().querySingleListSybase(sql).get(0);
		
		if((String)i.get("id") == "")
			return 1;
		else
			return Integer.parseInt((String)i.get("id"));
	}
	
	public static int creaPersona(String sql){
		int res;
		res = new UtilsBD().update(sql);
		return res;
	}
	
	public int insertaHistorico(int idPersona, int idPersonaModifica){		
		int res;
		int idHistorico = obtenUltimoIdHistorico();
		String sql = "SELECT * FROM PERSONA WHERE ID_PERSONA = " + idPersona;
		HashMap mapa = (HashMap)new UtilsBD().querySingleListSybase(sql).get(0);
		String fechaModificacion = UtilsFechas.formatoMesLetraSybase(new Date());
		String sqlHist = "INSERT INTO PERSONA_HISTORICA VALUES (" +
						 Integer.parseInt((String)mapa.get("id_persona")) + ", " +
						 idHistorico + ", " +
						 idPersonaModifica + ", " +
						 Integer.parseInt((String)mapa.get("id_cia")) + ", " +
						 Integer.parseInt((String)mapa.get("id_oficina")) + ", " +
						 Integer.parseInt((String)mapa.get("id_tipo_persona")) + ", " +						 
						 Integer.parseInt((String)mapa.get("id_area")) + ", '" +
						 (String)mapa.get("titulo") + "', '" +
						 (String)mapa.get("paterno") + "', '" +
						 (String)mapa.get("materno") + "', '" +
						 (String)mapa.get("nombre") + "', '" +
						 (String)mapa.get("puesto") + "', '" +
						 (String)mapa.get("telefono_cia") + "', '" +
						 (String)mapa.get("telefono_directo") + "', '" +
						 (String)mapa.get("telefono_casa") + "', '" +
						 (String)mapa.get("cel") + "', '" +
						 (String)mapa.get("facebook") + "', '" +
						 (String)mapa.get("whatsapp") + "', '" +
						 (String)mapa.get("twitter") + "', '" +
						 (String)mapa.get("mail_empresa") + "', '" +
						 (String)mapa.get("mail_personal") + "', '" +
						 (String)mapa.get("estatus") + "', '" +
						 (String)mapa.get("fecha_alta") + "', '" +
						 fechaModificacion + "','" +
						 (String)mapa.get("observaciones") + "')";
		log.debug("Insertando persona en historico: " + sqlHist.toString());
		res = new UtilsBD().update(sqlHist);
		log.debug("RESULTADO DE INSERTAR PERSONA EN HISTORICO: " + res);
		if(res != -1){
			if(insertaNivelesHistorico(idPersona, idHistorico) != -1)
				return 0;
			else
				log.debug("No se pudo insertar el nivel historico");
				return -1;
		}else{
			log.debug("No se pudo poner en historico a la persona :" + idPersona);
			return -1;
		}
	}
	
	public int insertaNiveles(boolean comiteEjecutivo, boolean directorGeneral, boolean expresidentes, boolean  directoresFinanzas, 
			boolean  asistentesDG, boolean vocal, boolean contacto, boolean aseguradoresD, boolean oii, boolean ocra, int idPersona){
		
		log.debug("Entrando paara insertar personas");
		
		List<Boolean> listaNiveles = new ArrayList<Boolean>();
		listaNiveles.add(comiteEjecutivo);
		listaNiveles.add(directorGeneral);
		listaNiveles.add(expresidentes);
		listaNiveles.add(directoresFinanzas);
		listaNiveles.add(asistentesDG);
		listaNiveles.add(vocal);
		listaNiveles.add(contacto);
		listaNiveles.add(aseguradoresD);
		listaNiveles.add(oii);
		listaNiveles.add(ocra);
		
		StringBuilder sql = new StringBuilder();				
		
		for(int i=0; i<listaNiveles.size(); i++){
			if(listaNiveles.get(i) != false)
				sql.append("INSERT INTO NIVEL_PERSONA VALUES (").append(idPersona).append(", ").append(i+1).append(")\n");
			else
				sql.append("");
		}
		log.debug("Insertando niveles persona " + sql.toString());
		log.debug("Antes de insertar los niveles de la persona " + idPersona);
		return new UtilsBD().update(sql.toString());	
	}
	
	public int insertaNivelesHistorico(int idPersona, int idHistorico){		
		int res;
		StringBuilder sql = new StringBuilder();
		List listaNiveles = getListaNivelesPersona(idPersona);
		HashMap mapa;
		log.debug(listaNiveles);
		if(listaNiveles != null){
			for(int i=0; i<listaNiveles.size(); i++){
				mapa = (HashMap)listaNiveles.get(i);
				sql.append("INSERT INTO NIVEL_PERSONA_HISTORICA VALUES (").append(idPersona).append(", ").append(idHistorico)
					.append(", ").append((String)mapa.get("idnivelpersona")).append(")\n");
			}
			log.debug("Insertando niveles persona en NIVEL_PERSONA_HISTORICA" + sql.toString());
			res = new UtilsBD().update(sql.toString());
			if(res != -1){
				if(borraNivelesPersona(idPersona) != -1)
					return 0;
				else
					log.debug("No se pudieron borrar los niveles de la persona");
					return -1;
			}else{
				log.debug("No se pudo insertar los niveles de la persona en el historico");
				return -1;
			}
		}		
		else
			log.debug("No se realizo la inserción en NIVEL_PERSONA_HISTORICA, la lista viene null, idPersona = " + idPersona);
			return 0;
	}
	
	public int borraNivelesPersona(int idPersona){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM NIVEL_PERSONA WHERE ID_PERSONA = ").append(idPersona);
		log.debug("Borrando Niveles Persona Antiguos " + sql.toString());
		int res = new UtilsBD().update(sql.toString());
		return res;
	}
	
	public List getListaNivelesPersona(int idPersona){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CNP.ID_NIVEL_PERSONA AS idNivelPersona, DESCRIPCION AS NOMBRE FROM NIVEL_PERSONA NP INNER JOIN CAT_NIVEL_PERSONA CNP ON NP.ID_NIVEL_PERSONA = CNP.ID_NIVEL_PERSONA WHERE ID_PERSONA = ")
		   .append(idPersona);		
		try{
			List listaMapasNivelesPersona = (List)new UtilsBD().queryMultipleListSybase(sql.toString()).get(0);
			return (List)listaMapasNivelesPersona;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
}
