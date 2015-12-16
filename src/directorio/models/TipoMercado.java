package directorio.models;

import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class TipoMercado extends Entidad {
	public static Logger log = Logger.getLogger(TipoMercado.class.getName());
	
	public TipoMercado(int id, String nombre){
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public static TipoMercado loadFromId(int idTipoMercado){
		String sql = "SELECT * FROM CAT_TIPO_MERCADO WHERE ID_TIPO_MERCADO = " + idTipoMercado;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if(mapa == null){
			return null;
		}
		return loadFromMapa(mapa);
	}
	
	public static TipoMercado loadFromMapa(HashMap mapa){
		return new TipoMercado(Integer.parseInt((String)mapa.get("id_tipo_mercado")), 
								(String)mapa.get("descripcion"));
	}
}
