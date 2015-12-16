package directorio.models;

import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class Area extends Entidad{
	public static Logger log = Logger.getLogger(Area.class.getName());
	
	public Area(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public static Area loadFromId(int idArea){
		String sql = "SELECT * FROM CAT_AREA " +
						"WHERE ID_AREA = " + idArea;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return new Area(Integer.parseInt((String)mapa.get("id_area")),
							(String)mapa.get("descripcion"));
	}
}
