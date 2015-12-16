package directorio.models;

import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class CapitalOrigen extends Entidad {
	public static Logger log = Logger.getLogger(CapitalOrigen.class.getName());
	private int idCapitalOrigen;
	private String descripcion;
	
	public CapitalOrigen(int id, String descripcion){
		this.id = id;
		this.nombre = descripcion;
		this.padre = null;
	}
	
	public int getIdCapitalOrigen() {
		return idCapitalOrigen;
	}
	public void setIdCapitalOrigen(int idCapitalOrigen) {
		this.idCapitalOrigen = idCapitalOrigen;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static CapitalOrigen loadFromId(int idCapitalOrigen){
		String sql = "SELECT * FROM CAT_CAPITAL_ORIGEN WHERE ID_CAPITAL_ORIGEN = " + idCapitalOrigen;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return loadFromMapa(mapa);
	}
	
	public static CapitalOrigen loadFromMapa(HashMap mapa){
		try{
			return new CapitalOrigen(Integer.parseInt((String)mapa.get("id_capital_origen")), 
					(String)mapa.get("descripcion"));
		}catch(NumberFormatException e){
			return null;
		}
	}
	
}
