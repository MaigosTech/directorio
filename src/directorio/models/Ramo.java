package directorio.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import directorio.utils.UtilsBD;

public class Ramo extends Entidad{
	
	public Ramo(int idRamo, String descripcion){
		this.id = idRamo;
		this.nombre = descripcion;
		this.padre = null;
	}
	
	public static Ramo loadFromId(int idRamo){
		String sql = "SELECT * FROM CAT_RAMO WHERE ID_RAMO = " + idRamo;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa == null)
			return null;
		return new Ramo(Integer.parseInt((String)mapa.get("id_ramo")),
					   (String)mapa.get("descripcion"));
	}
	
	public static Ramo loadFromMapa(HashMap mapa){
		Ramo ramo = Ramo.loadFromId(Integer.parseInt((String)mapa.get("id_ramo")));
		return ramo;
	}
	
	public static List<Ramo> getRamos(int idCia){		
		String sql = "SELECT * FROM RAMO_COMPANIA WHERE ID_CIA = " + idCia;
		
		try{
			List listaMapasRamo = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			List listaMapaRamo = (List)listaMapasRamo;		
			List<Ramo> listaRamo= new ArrayList<Ramo>();
			for(int i=0; i<listaMapaRamo.size(); i++){
				HashMap mapaRamo = (HashMap)listaMapaRamo.get(i);
				listaRamo.add(Ramo.loadFromMapa(mapaRamo));
			}
			return listaRamo;			
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

}
