package directorio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

public class Usuario implements Serializable {
	public static Logger log = Logger.getLogger(Usuario.class.getName());
	/*public int id;
	private String login;
	public String nombre;
	public String apPaterno;
	public String apMaterno;
	public String email;
	public int estatus;
	public Rol rol;
	//public Oficina oficina;
	public Compania compania;
	public Date lastUpdate; */
	
	private Persona persona;
	private String login;
	private String pass;
	private Rol rol;
	private Date fecha_alta;
	private char estatus;
	private Date fecha_bit;
	private String observaciones;
	
	public Usuario(){
		;
	}
	
	public Usuario(Persona persona, String login, String pass,
			Rol rol, Date fecha_alta, char estatus, Date fecha_bit,
			String observaciones) {
		super();
		this.persona = persona;
		this.login = login;
		this.pass = pass;
		this.rol = rol;
		this.fecha_alta = fecha_alta;
		this.estatus = estatus;
		this.fecha_bit = fecha_bit;
		this.observaciones = observaciones;
	}
	//Este constructor es de prueba
	public Usuario(String login, String pass, Rol rol,  Persona persona){
		this.login = login;
		this.pass = pass;
		this.rol = rol;		
		this.persona = persona;
		this.persona.setIdPersona(1);
		this.persona.setArea(new Area(1, "TI"));
	}

	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Rol getRol(){
		return rol;
	}
	
	public void setRol(Rol rol){
		this.rol = rol;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getFechaAlta() {
		return fecha_alta;
	}

	public void setFechaAlta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public char getEstatus() {
		return estatus;
	}

	public void setEstatus(char estatus) {
		this.estatus = estatus;
	}

	public Date getFechaBit() {
		return fecha_bit;
	}

	public void setFechaBit(Date fecha_bit) {
		this.fecha_bit = fecha_bit;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public static Usuario loadFromLogin(String login, String password){
		String sql = "SELECT ID_PERSONA, ID_ROL, LOGIN, ESTATUS " +
						"FROM USUARIO " + "WHERE LOGIN = '" + login +
						"' AND PASS = '" + password + "'";
		log.debug("Antes de ejecutar el query de login");
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null)
			return null;
		return loadFromMapa(mapa);
	}
	
	public static Usuario loadFromMapa(HashMap mapa){
		Usuario usuario = new Usuario();
		usuario.initUsuario(Persona.loadFromId(Integer.parseInt((String)mapa.get("id_persona"))),
								(String)mapa.get("login"), 
								(String)mapa.get("pass"), 
								Rol.loadFromId(Integer.parseInt((String)mapa.get("id_rol"))),
								UtilsFechas.parseFechaUsuario((String)mapa.get("fecha_alta")));		
		return usuario;
	}
	
	public void initUsuario(Persona persona, String login, String pass, Rol rol, Date fecha_alta) {
		this.persona = persona;
		this.login = login;
		this.pass = pass;
		this.rol = rol;
		this.fecha_alta = fecha_alta;
	}

	public static void creaUsuario(String sql){
		int res;
		res = new UtilsBD().update(sql);
	}
	
	public static void actualizaUsuario(Usuario actualizaUsuario){
		int res;
	}
	
	public static Usuario getUsuarioFromId(int idPersona){
		String query = "SELECT * FROM USUARIO WHERE ID_PERSONA = " + idPersona; 
		HashMap mapa = new UtilsBD().queryMapa(query);		
		return Usuario.loadFromMapa(mapa);
		
	}
	
	public static Usuario usuarioPrueba(String login, String pw){
		
		Rol rolTest = null;
		
		if(login.contains("DIRECTORIO"))
			rolTest = new Rol(1, "admindirectorio");			
		else if (login.contains("AREA"))
			rolTest = new Rol(2, "adminarea");
		
		Usuario usuarioTest = new Usuario(login, pw, rolTest, new Persona());
		return usuarioTest;
	}
	
	@Override
	public String toString() {
		return "Usuario [persona=" + persona + ", login=" + login + ", pass="
				+ pass + ", rol=" + rol + ", fecha_alta=" + fecha_alta
				+ ", estatus=" + estatus + ", fecha_bit=" + fecha_bit
				+ ", observaciones=" + observaciones + "]";
	}
}
