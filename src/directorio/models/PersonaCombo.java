package directorio.models;

public class PersonaCombo extends Entidad{
	public PersonaCombo(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
}
