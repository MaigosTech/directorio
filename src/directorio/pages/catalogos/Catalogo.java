package directorio.pages.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.misc.EntidadFactory;
import directorio.models.Entidad;
import directorio.pages.OcraBasePage;
import directorio.pages.admindirectorio.AdminCatalogo;
import directorio.utils.UtilsBD;
public class Catalogo extends OcraBasePage {	
	
	@Property
	private List listaEntidades;
	@Property
	private Entidad entidad;
	@Persist(PersistenceConstants.FLASH)
	@Property	
	private int idEntidad;
	@InjectPage
	private AdminCatalogo adminCatalogo;
	
	void onActivate(int idEntidad){
		this.idEntidad = idEntidad;
		listaEntidades = getCatalogoFromId(idEntidad);
	}
	
	private List getCatalogoFromId(int idEntidad){
		List listaDestino = new ArrayList();
		
		try {
			List entidadesBD = Entidad.getEntidades(idEntidad);			
			for (int i=0; i<entidadesBD.size(); i++) {				
				HashMap mapa = (HashMap)entidadesBD.get(i);
				Entidad entidad = null;
				entidad = EntidadFactory.initEntidad(idEntidad, 
						Integer.parseInt(mapa.get("id").toString()),
						mapa.get("descripcion").toString());
				listaDestino.add(entidad);
			}
			log.debug("Catalogo desplegado: " + listaDestino);
			return listaDestino;
		} catch (Exception e) {
			log.debug("Error inicializando entidad: " + idEntidad + ":" + 
										e.getMessage());
			log.debug("Error inicializando entidad: " + idEntidad + ":" + 
										e.getMessage());
			return null;
		}
	}
	
	void onSelectedFromAgregar(){
		log.debug("Agregando nuevo registro");
		adminCatalogo.agregaRegistro(idEntidad);
	}
	
	Object onSuccess(){
		return adminCatalogo;
	}
	
	Object onActionFromModificaCatalogo(int id, String nombre){
		log.debug("De la pagina: " + id + ", nombre: " + nombre);
		try {
			adminCatalogo.modificaCatalogo(idEntidad, EntidadFactory.initEntidad(idEntidad, id, nombre));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminCatalogo;
	}
}