package directorio.models;

import java.util.Date;
import java.util.HashMap;

import directorio.utils.UtilsBD;

public class PersonaHistorico {

	private int idPersona;
	private int idHistorial;
	private int idPersonaMod;
	private int idCia;
	private int idTipoPersona;
	private int idNivelPersona;
	private int idArea;
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
	private String mailEmpresa;
	private String mailPersonal;
	private char estatus;
	private String fechaAlta;
	private String fechaMod;
	private String observaciones;
	
	public int getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	public int getIdHistorial() {
		return idHistorial;
	}
	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}
	public int getIdPersonaMod() {
		return idPersonaMod;
	}
	public void setIdPersonaMod(int idPersonaMod) {
		this.idPersonaMod = idPersonaMod;
	}
	public int getIdCia() {
		return idCia;
	}
	public void setIdCia(int idCia) {
		this.idCia = idCia;
	}
	public int getIdTipoPersona() {
		return idTipoPersona;
	}
	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}
	public int getIdNivelPersona() {
		return idNivelPersona;
	}
	public void setIdNivelPersona(int idNivelPersona) {
		this.idNivelPersona = idNivelPersona;
	}
	public int getIdArea() {
		return idArea;
	}
	public void setIdArea(int idArea) {
		this.idArea = idArea;
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
	public String getMailEmpresa() {
		return mailEmpresa;
	}
	public void setMailEmpresa(String mailEmpresa) {
		this.mailEmpresa = mailEmpresa;
	}
	public String getMailPersonal() {
		return mailPersonal;
	}
	public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}
	public char getEstatus() {
		return estatus;
	}
	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaMod() {
		return fechaMod;
	}
	public void setFechaMod(String fechaMod) {
		this.fechaMod = fechaMod;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	/*private int idPersona;
	private int idHistorial;
	private int idPersonaMod;
	private int idCia;
	private int idTipoPersona;
	private int idNivelPersona;
	private int idArea; */
	
	
	public Persona getPersonaFromId(int idPersona){
		return Persona.loadFromId(idPersona);
	}
	
	public static String getNombreCia(String idCia){
		String sql = "SELECT NOMBRE_CORTO FROM COMPANIA WHERE ID_CIA = " + idCia;
		HashMap mapa = new UtilsBD().queryMapa(sql);		
		return (String)mapa.get("nombre_corto");
	}
	
	public static String getDescripcionFromCatalogo(String id, String nombreCatalogo){
		String sql;
		if(nombreCatalogo.equalsIgnoreCase("CAT_CANAL_VENTA"))
			sql = "SELECT DESCRIPCION FROM " + nombreCatalogo + " WHERE ID_CANAL_VENTAS = " + id;
		else{
			sql = "SELECT DESCRIPCION FROM " + nombreCatalogo + " WHERE ID_" + nombreCatalogo.substring(4) + " = " + id;
		}				
		HashMap mapa = new UtilsBD().queryMapa(sql);		
		return (String)mapa.get("descripcion");
	}
}
