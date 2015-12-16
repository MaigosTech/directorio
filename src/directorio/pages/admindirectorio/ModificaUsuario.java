package directorio.pages.admindirectorio;

import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.annotations.InjectSelectionModel;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.Usuario;
import directorio.pages.OcraBasePage;

public class ModificaUsuario extends OcraBasePage{
	
	@Property
	private String errorTmp;
	@Persist
	@Property
	private Usuario usuario;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaRoles = Global.getRoles();
	
	void modificaPersona(int idPersona){
		usuario = Usuario.getUsuarioFromId(idPersona);
	}
	
}