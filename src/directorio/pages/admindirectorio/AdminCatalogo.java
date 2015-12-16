package directorio.pages.admindirectorio;

import java.util.HashMap;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.models.Entidad;
import directorio.pages.OcraBasePage;
import directorio.utils.UtilsBD;

public class AdminCatalogo extends OcraBasePage {
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean modifica;
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean agrega;
	@Persist(PersistenceConstants.FLASH)
	@Property
	private boolean muestra;
	@Persist
	@Property
	private Entidad entidad;
	@Persist
	@Property
	private int idEntidad;
	@Property
	private String nuevoNombre;
	
	public void modificaCatalogo(int idEntidad, Entidad entidad){
		this.idEntidad = idEntidad;
		this.entidad = entidad;
		log.debug("Modificando Entidad: " + getNombreEntidad(this.idEntidad) + "ID: " + entidad.id + " NOMBRE: " + entidad.nombre);
		modifica = true;
		muestra = true;
	}
	public void agregaRegistro(int idEntidad){
		this.idEntidad = idEntidad;
		agrega = true;
		muestra = true;
	}
	void onSubmitFromFormCatalogoAgregar(){
		String nombreEntidad = getNombreEntidad(idEntidad);
		log.debug("ID de la entidad: " + idEntidad + " Nombre de la entidad: " + nombreEntidad);
		StringBuilder sql =  new StringBuilder();
		sql.append("INSERT INTO CAT_").append(nombreEntidad).append(" VALUES (").append(obtenUltimoId(nombreEntidad))
		   .append(", '").append(nuevoNombre).append("')");
		log.debug("Insertando en catalogo: " + sql.toString());
		int res = new UtilsBD().update(sql.toString());
	}
	
	void onSelectedFromModificar(){
		String nombreEntidad = getNombreEntidad(idEntidad);
		StringBuilder sql =  new StringBuilder();
		sql.append("UPDATE CAT_").append(nombreEntidad).append(" SET DESCRIPCION = '").append(entidad.nombre).append("' WHERE ID_")
			.append(nombreEntidad).append(" = ").append(entidad.id);
		log.debug("Actualizando catalogo: " + nombreEntidad + " SQL: " + sql.toString());
		if(new UtilsBD().update(sql.toString()) != -1)
			log.debug("Se actualizó correctamente el catálogo");
		else
			log.debug("Hubo un problema al actualizar el catálogo");
	}
	
	private String getNombreEntidad(int idEntidad){
		switch(idEntidad){
			case Entidad.ENTIDAD_AREA: return "AREA";
			case Entidad.ENTIDAD_CANALVENTA: return "CANAL_VENTA";
			case Entidad.ENTIDAD_CAPITALO: return "CAPITAL_ORIGEN";
			case Entidad.ENTIDAD_CAPITALS: return "CAPITAL_SOCIAL";
			case Entidad.ENTIDAD_NIVELP: return "NIVEL_PERSONA";
			case Entidad.ENTIDAD_RAMO: return "RAMO";
			case Entidad.ENTIDAD_ROL: return "ROL";
			case Entidad.ENTIDAD_TIPOMER: return "TIPO_MERCADO";
			case Entidad.ENTIDAD_TIPOP: return "TIPO_PERSONA";
			default: return null;
		}
	}
	
	private int obtenUltimoId(String nombreEntidad){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MAX(ID_").append(nombreEntidad).append(")+1 AS ID FROM CAT_").append(nombreEntidad);				
		HashMap i = (HashMap)new UtilsBD().querySingleListSybase(sql.toString()).get(0);
		
		if((String)i.get("id") == "")
			return 1;
		else
			return Integer.parseInt((String)i.get("id"));
	}
}