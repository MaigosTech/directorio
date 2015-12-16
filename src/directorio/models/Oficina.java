package directorio.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import directorio.services.ExcelGenerator;
import directorio.utils.UtilsBD;

/** 
 * Representa a un oficina
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 
 */
public class Oficina extends Entidad {
	
	public static Logger log = Logger.getLogger(Oficina.class.getName());
	private int idCia;
	private String direccion;
	private String telefono;
	
	private String descripcion;
	private String calle;
	private String colonia;
	private String cp;	
	
	public Oficina(){
		
	}
	
	public Oficina(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = padre;
	}
	
	public Oficina(int id, int idCia, String nombre, Entidad padre, String direccion, String telefono) {
		this.id = id;
		this.idCia = idCia;
		this.nombre = nombre;
		this.padre = padre;
		this.direccion = direccion;		
		this.telefono = telefono;
		
	}
	
	public int getIdCia() {
		return idCia;
	}

	public void setIdCia(int idCia) {
		this.idCia = idCia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;	
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setPartes(String direccion){
		String[] partes = direccion.split("\\|");
		setCalle(partes[0]);
		setColonia(partes[1]);
		setCp(partes[2]);
	}

	public static Oficina loadFromId(int idOficina){
		String sql = "SELECT * FROM OFICINA WHERE ID_OFICINA = " + idOficina;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa == null)
			return null;
		return new Oficina(Integer.parseInt((String)mapa.get("id_oficina")),
						   Integer.parseInt((String)mapa.get("id_cia")),
						   (String)mapa.get("descripcion"),
						   null,
						   (String)mapa.get("direccion"),
						   (String)mapa.get("telefono"));
	}
	
	public static Oficina loadFromMapa(HashMap mapa){
		Oficina oficina = Oficina.loadFromId(Integer.parseInt((String)mapa.get("id_oficina")));
		return oficina;
	}
	
	public static List<Oficina> getOficinas(int idCia){
		String sql = "SELECT * FROM OFICINA WHERE ID_CIA = " + idCia;
		List listaMapasOficina = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
		List listaMapaOficina = (List)listaMapasOficina;
		List<Oficina> listaOficina= new ArrayList<Oficina>();
		for(int i=0; i<listaMapaOficina.size(); i++){
			HashMap mapaOficina = (HashMap)listaMapaOficina.get(i);
			listaOficina.add(Oficina.loadFromMapa(mapaOficina));
		}
		return listaOficina;
	}
	
	public int obtenUltimoId(){
		String sql = "SELECT MAX(ID_OFICINA)+1 AS ID FROM OFICINA";
		HashMap i = (HashMap)new UtilsBD().querySingleListSybase(sql).get(0);		
		if((String)i.get("id") == "")
			return 1;
		else
			return Integer.parseInt((String)i.get("id"));
	}
	
	public int creaOficina(String idCia, String descripcion, StringBuilder direccion, String telefono){
		log.debug("Datos entrada: " + idCia + ", " + descripcion + ", " + direccion + "," + telefono);
		StringBuilder sql = new StringBuilder();
		int idOficina = obtenUltimoId();
		sql.append("INSERT INTO OFICINA VALUES(").append(idOficina).append(", ").append(idCia).append(", '").append(descripcion).append("', '")
		   .append(direccion).append("', '").append(telefono).append("')");
		int res = new UtilsBD().update(sql.toString());
		return res;
	}
}
