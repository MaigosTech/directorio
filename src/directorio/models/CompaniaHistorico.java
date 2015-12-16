package directorio.models;

import java.util.HashMap;

import directorio.utils.UtilsBD;

public class CompaniaHistorico {
	
	public static String getNombreCia(String idCia){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT NOMBRE_CORTO FROM COMPANIA WHERE ID_CIA = ").append(idCia);
		HashMap mapa = new UtilsBD().queryMapa(sql.toString());
		return (String)mapa.get("nombre_corto");
	}
}
