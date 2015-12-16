package directorio.models;

import java.util.HashMap;

import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;


public class TipoPersona extends Entidad{
	
	public TipoPersona(int id, String nombre){
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
	
	public static TipoPersona loadFromId(int idTipoPersona){
		String sql = "SELECT * FROM CAT_TIPO_PERSONA WHERE ID_TIPO_PERSONA = " + idTipoPersona;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa == null)
			return null;
		return new TipoPersona(Integer.parseInt((String)mapa.get("id_tipo_persona")),						
						   (String)mapa.get("descripcion"));
	}
}
