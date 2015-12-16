package directorio.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

public class NivelPersona extends Entidad {
	
	public NivelPersona(int id, String nombre){
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}

	public static NivelPersona loadFromId(int idNivelPersona){
		String sql = "SELECT * FROM CAT_NIVEL_PERSONA WHERE ID_NIVEL_PERSONA = " + idNivelPersona;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa == null)
			return null;
		return new NivelPersona(Integer.parseInt((String)mapa.get("id_nivel_persona")),
						   (String)mapa.get("descripcion"));
	}
	
	public static NivelPersona loadFromMapa(HashMap mapa){
		
		NivelPersona nivelPersona = NivelPersona.loadFromId(Integer.parseInt((String)mapa.get("id_nivel_persona")));
		return nivelPersona;
	}
	
	public static List<NivelPersona> getNiveles(int idPersona){
		String sql = "SELECT * FROM NIVEL_PERSONA WHERE ID_PERSONA = " + idPersona;
		try{
			List listaMapasNivel = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			List listaMapaNivel = (List)listaMapasNivel;
			List<NivelPersona> listaNivel= new ArrayList<NivelPersona>();
			for(int i=0; i<listaMapaNivel.size(); i++){
				HashMap mapaNivel = (HashMap)listaMapaNivel.get(i);
				listaNivel.add(NivelPersona.loadFromMapa(mapaNivel));
			}		
			return listaNivel;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	public static List<NivelPersona> getCheckBoxValues(){
		String sql = "SELECT * FROM CAT_NIVEL_PERSONA";
		
		try{
			List listaMapasNivel = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
			List listaMapaNivel = (List)listaMapasNivel;
			List<NivelPersona> listaNivel= new ArrayList<NivelPersona>();
			for(int i=0; i<listaMapaNivel.size(); i++){
				HashMap mapaNivel = (HashMap)listaMapaNivel.get(i);
				listaNivel.add(NivelPersona.loadFromMapa(mapaNivel));
			}		
			return listaNivel;			
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
}
