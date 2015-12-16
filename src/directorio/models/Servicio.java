package directorio.models;

public class Servicio extends Entidad {
	public Servicio(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}

}
