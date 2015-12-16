package directorio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import directorio.misc.EntidadFactory;

public class Global implements Serializable {
	
	public static String OUTPUT_PARAMETER = "outputParameter";
	public static Logger log = Logger.getLogger(Global.class.getName());
	private static final boolean SELECCIONE_UNA_OPCION = true;
	private static final boolean NO_SELECCIONE_UNA_OPCION = false;
	private static List ramos = new ArrayList();
	private static List oficinas = new ArrayList();
	private static List capitalSocial = new ArrayList();
	private static List capitalOrigen = new ArrayList();
	private static List tipoMercado = new ArrayList();
	private static List canalVenta = new ArrayList();
	private static List ramoCompania = new ArrayList();
	private static List compania = new ArrayList();
	private static List companiaHistorica = new ArrayList();
	private static List areas = new ArrayList();
	private static List tipoPersona = new ArrayList();
	private static List nivelPersona = new ArrayList();
	private static List persona = new ArrayList();
	private static List personaHistorica = new ArrayList();
	private static List usuarios = new ArrayList();
	private static List roles = new ArrayList();
	
	static {
		long l = System.currentTimeMillis();
		log.debug("Inicializando Global");		
		try{
			initEntidad(Entidad.ENTIDAD_RAMO, ramos, null);
			initEntidad(Entidad.ENTIDAD_RAMO_CIA, ramoCompania, null);
			initEntidad(Entidad.ENTIDAD_CAPITALS,capitalSocial, null);
			initEntidad(Entidad.ENTIDAD_CAPITALO, capitalOrigen, null);
			initEntidad(Entidad.ENTIDAD_COMPANIA, compania, null, NO_SELECCIONE_UNA_OPCION);
			initEntidad(Entidad.ENTIDAD_TIPOMER, tipoMercado, null);
			initEntidad(Entidad.ENTIDAD_CANALVENTA, canalVenta, null);
			initEntidad(Entidad.ENTIDAD_AREA, areas, null);
			initEntidad(Entidad.ENTIDAD_TIPOP, tipoPersona, null);
			initEntidad(Entidad.ENTIDAD_PERSONA, persona, null);
			initEntidad(Entidad.ENTIDAD_NIVELP, nivelPersona, null);
			initEntidad(Entidad.ENTIDAD_ROL, roles, null);
			initEntidad(Entidad.ENTIDAD_OFICINA, oficinas, null, NO_SELECCIONE_UNA_OPCION);
			
		}catch(Exception e){
			log.debug("Error inicializando global: " + e.getLocalizedMessage());
			//e.printStackTrace();
		}
		log.debug("fin inicializacion Global:" + (System.currentTimeMillis() - l));
	}
	
	public static void initClass(){
				iniciaTodo();
	} 

	public static void iniciaTodo(){
		/* tiposTransporte = new ArrayList();
		tiposTransporteConsultas = new ArrayList();
		
		marcas = new ArrayList();
		submarcas = new ArrayList();
		
		initEntidad(Entidad.ENTIDAD_PAIS, paises, null);
		initEntidad(Entidad.ENTIDAD_ESTADO, estados, paises);
		initEntidad(Entidad.ENTIDAD_COMPANIA_COMBO, companias, null);
		initEntidadConsultas(Entidad.ENTIDAD_COMPANIA_COMBO, companiasConsultas);
		initEntidad(Entidad.ENTIDAD_OFICINA, oficinas, companias); */
		
	}

	/** 
	 * Busca en un List un objeto cuyo ID sea igual al recibido como
	 * par�metro.
	 *
	 * @param collection 
	 * @param idPadre 
	 * @return 
	 */
	public static Entidad findById(List collection, int id) {
		Iterator it = collection.iterator();
		while(it.hasNext()) {
			Entidad entidad = (Entidad)it.next();
			if (entidad.id == id)
				return entidad;
		}
		return null;
	}
	
	/**
	 * Busca en una lista un objeto cuya descripcion sea la recibida.
	 * @param collection
	 * @param idPadre
	 * @return
	 */
	public static Entidad findByNombre(List collection, String nombre) {
		Iterator it = collection.iterator();
		while(it.hasNext()) {
			Entidad entidad = (Entidad)it.next();
			if (entidad.nombre.equals(nombre))
				return entidad;
		}
		return null;
	}
	
	public static Entidad findHijoByNombre(Entidad padre, String nombre) {
		List lista = padre.hijos;
		Iterator it = lista.iterator();
		while(it.hasNext()) {
			Entidad entidad = (Entidad)it.next();
			if (entidad.nombre.equals(nombre))
				return entidad;
		}
		return null;
	}

	/** 
	 * Inicializa desde la base de datos un objeto entidad en particular.
	 * 
	 * @param listaDestino 
	 * @param listaPadre 
	 * @return 
	 */
	private static void initEntidad(int tipoEntidad, List listaDestino, List listaPadre) {
		initEntidad(tipoEntidad, listaDestino, listaPadre, SELECCIONE_UNA_OPCION);
	}
	
	private static void initEntidad(int tipoEntidad, List listaDestino, 
					List listaPadre, boolean seleccioneUnaOpcion) {
		try {
			List entidadesBD = Entidad.getEntidades(tipoEntidad);
			// Agrego el 'seleccione una opcion'
			if (listaPadre == null && seleccioneUnaOpcion) {
				Entidad entidadSeleccione = EntidadFactory.initEntidad(tipoEntidad, -1, 
						"Seleccione una opci\u00F3n");
				listaDestino.add(entidadSeleccione);
			}

			for (int i=0; i<entidadesBD.size(); i++) {
				HashMap mapa = (HashMap)entidadesBD.get(i);
				Entidad entidad = null;
				if (listaPadre == null){ // Si la listaPadre es null, uso el factory sencillo
					entidad = EntidadFactory.initEntidad(tipoEntidad, 
													Integer.parseInt(mapa.get("id").toString()),
													mapa.get("descripcion").toString());
				}else{
					entidad = EntidadFactory.initEntidad(tipoEntidad, 
													Integer.parseInt(mapa.get("id").toString()),
													mapa.get("descripcion").toString(),
													findById(listaPadre,
														Integer.parseInt(mapa.get("id_padre")
																	.toString())));
				}
				listaDestino.add(entidad);
			}
		} catch (Exception e) {
			log.debug("Error inicializando entidad: " + tipoEntidad + ":" + 
										e.getMessage());
			//e.printStackTrace();
		}
	}

	/** 
	 * Metodo copia de initEntidad, solo para cuando necesitas una opcion "TODAS LAS ANTERIORES" 
	 * 
	 * @param tipoEntidad 
	 * @param listaDestino 
	 * @param listaPadre 
	 */
	private static void initEntidadConsultas(int tipoEntidad, List listaDestino) {
		try {
			List entidadesBD = Entidad.getEntidades(tipoEntidad);
			// Agrego el 'seleccione una opcion'
			
			Entidad entidadSeleccione = 
				EntidadFactory.initEntidad(tipoEntidad, -1, seleccionaTextoTodos(tipoEntidad));
			listaDestino.add(entidadSeleccione);
			

			for (int i=0; i<entidadesBD.size(); i++) {
				HashMap mapa = (HashMap)entidadesBD.get(i);
				Entidad entidad = null;
				
				entidad = EntidadFactory.initEntidad(tipoEntidad, 
								Integer.parseInt(mapa.get("id").toString()),
								mapa.get("descripcion").toString());
			
				listaDestino.add(entidad);
			}
		} catch (Exception e) {
			log.debug("Error inicializando entidad: " + tipoEntidad + ":" + 
										e.getMessage());
		}
	}

	/** 
	 * Devuelve el texto que corresponde al compo 'todos' dependiendo de la entidad
	 * 
	 * @param tipoEntidad 
	 * @return 
	 */
	private static String seleccionaTextoTodos(int tipoEntidad) {
		/* switch (tipoEntidad) {
			case Entidad.ENTIDAD_TIPO_TRANS:
				return "TODOS LOS TRANSPORTES";
			case Entidad.ENTIDAD_COMPANIA_COMBO:
				return "TODAS LAS COMPANIAS";
			default: */
				return "Cualquiera";
		//}

	}
 
	public static List getRamos() { return ramos; }
	public static List getOficinas() { return oficinas; }
	public static List getCapitalSocial() { return capitalSocial; }
	public static List getCapitalOrigen() { return capitalOrigen; }
	public static List getTipoMercado() { return tipoMercado; }
	public static List getCanalVenta() { return canalVenta; }
	public static List getRamoCompania() { return ramoCompania; }
	public static List getCompania() { return compania; }
	public static List getCompaniaHistorica() { return companiaHistorica; }
	public static List getAreas() { return areas; }
	public static List getTipoPersona() { return tipoPersona; }
	public static List getNivelPersona() { return nivelPersona; }
	public static List getPersona() { return persona; }
	public static List getPersonaHistorica() { return personaHistorica; }
	public static List getUsuarios() { return usuarios; }
	public static List getRoles() { return roles; }
}
