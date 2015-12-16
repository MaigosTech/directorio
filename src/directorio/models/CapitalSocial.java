package directorio.models;

import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class CapitalSocial extends Entidad {
	public static Logger log = Logger.getLogger(CapitalSocial.class.getName());
	private int idCapitalSocial;
	private String descripcion;
	
	public CapitalSocial(int id, String nombre){
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public int getIdCapitalSocial() {
		return idCapitalSocial;
	}
	public void setIdCapitalSocial(int idCapitalSocial) {
		this.idCapitalSocial = idCapitalSocial;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static CapitalSocial loadFromId(int idCapitalSocial){
		String sql = "SELECT * FROM CAT_CAPITAL_SOCIAL WHERE ID_CAPITAL_SOCIAL = " + idCapitalSocial;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return loadFromMapa(mapa);
	}
	
	public static CapitalSocial loadFromMapa(HashMap mapa){
		return new CapitalSocial(Integer.parseInt((String)mapa.get("id_capital_social")), 
								(String)mapa.get("descripcion"));
	}

}
