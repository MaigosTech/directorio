package directorio.models;

import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class CanalVenta extends Entidad {
	public static Logger log = Logger.getLogger(CanalVenta.class.getName());
	private int idCanalVenta;
	private String descripcion;
	
	public CanalVenta(int id, String nombre){
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public int getIdCanalVenta() {
		return idCanalVenta;
	}
	public void setIdCanalVenta(int idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static CanalVenta loadFromId(int idCanalVenta){
		String sql = "SELECT * FROM CAT_CANAL_VENTA WHERE ID_CANAL_VENTAS = " + idCanalVenta;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return loadFromMapa(mapa);
	}
	
	public static CanalVenta loadFromMapa(HashMap mapa){
		return new CanalVenta(Integer.parseInt((String)mapa.get("id_canal_ventas")), 
								(String)mapa.get("descripcion"));
	}
}
