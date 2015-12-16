package directorio.pages.admindirectorio;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.models.Compania;
import directorio.models.Oficina;
import directorio.pages.OcraBasePage;
import directorio.pages.consulta.Consulta;
import directorio.utils.UtilsBD;

public class OficinaCompania extends OcraBasePage {
	@Persist
	@Property
	private String idCia;		
	@Property
	private String titulo;
	@Property
	private String errorTmp;
	@Property
	private String descripcion;
	@Property
	private String calle;
	@Property
	private String colonia;
	@Property
	private String cp;
	@Property
	private String telefono;
	@InjectPage
	private Consulta consulta;	
	@Persist
	@Property
	private String accion;
	@Persist
	@Property
	private Oficina oficina;
	
	void creaOficina(int idCia){
		this.idCia = (String.valueOf(idCia));
		accion = "agrega";
	}
	
	void modificaOficina(int idCia, int idOficina){
		this.idCia = (String.valueOf(idCia));
		oficina = Oficina.loadFromId(idOficina);		
		oficina.setPartes(oficina.getDireccion());
		accion = "modifica";
	}
	
	public boolean getAccion(String accion){
		log.debug("Accion de pantalla: " + accion + "\nAccion esperada: " + this.accion);
		if(accion.contains(this.accion))
			return true;
		else
			return false;
	}
	
	public String getNombreCiaFromId(String idCia){
		return Compania.getNombreCiaFromId(Integer.parseInt(idCia));
	}
	
	public void onSelectedFromAgregaOficina(){
		Oficina oficina = new Oficina();
		log.debug("Creando oficina para la compañia: " + idCia);
		StringBuilder direccion = new StringBuilder();
		direccion.append(calle).append("|").append(colonia).append("|").append(cp);
		if(oficina.creaOficina(idCia, descripcion, direccion, telefono) != -1)
			log.debug("Se creó correctamente la oficina");
		else
			log.debug("Ocurrió un error al crear la oficina");
	}
	
	public void onSelectedFromModificaOficina(){
		log.debug("Obteniendo datos para modificar oficina: " + oficina.getId());
		StringBuilder direccion = new StringBuilder();
		direccion.append(oficina.getCalle()).append("|").append(oficina.getColonia()).append("|").append(oficina.getCp());
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE OFICINA SET DESCRIPCION = '").append(oficina.getNombre()).append("', DIRECCION = '").append(direccion.toString()).append("', TELEFONO = '")
		   .append(oficina.getTelefono()).append("'").append("WHERE ID_OFICINA = ").append(oficina.getId());
		log.debug("Actualizando oficina..." + sql.toString());
		int res = new UtilsBD().update(sql.toString());
		if(res != -1){
			log.debug("Se actualizó correctamente la oficina");
		}else{
			log.debug("Ocurrió un error en la actualización de la oficina");
			errorTmp = "Ocurrió un error en la actualización de la oficina";
		}
	}
	
	public boolean getRol(String rol){		
		String rolUsuarioSesion = getUsuarioSesion().getRol().getNombre();
		int areaUsuarioSesion = getUsuarioSesion().getPersona().getArea().getId();
		log.debug("Checando Rol:" + rolUsuarioSesion);
		if(rolUsuarioSesion.equalsIgnoreCase(rol)){			
			return true;		
		}else
			return false;
	}
	
	Object onSuccess(){
		return consulta;
	}
}