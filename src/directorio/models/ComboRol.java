package directorio.models;

/** 
 * Representa a un rol. 
 * No extiende de Entidad ya que fue creado para obtener cada vez que se llame sus datos completos
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 
 */
public class ComboRol {
	public int id;
	public String nombre;

	private boolean selected;

	public String toString() {
		return id + "," + nombre;
	}
	
	public ComboRol(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() { return nombre; }
	public int getId() { return id; }

	public boolean getSelected() { return selected; }
	public void setSelected(boolean selected) { this.selected = selected; }
	public boolean equals(Object obj) {
		try	{
			ComboRol obj2 = (ComboRol)obj;
			if (obj2.id == this.id && obj2.nombre.equals(this.nombre)) 
				return true;
			else
				return false;
		}
		catch (Exception e)	{
			// Si entro aqu�, significa que el cast no jal�, entonces devuelvo false.
			return false;
		}
	}
}
