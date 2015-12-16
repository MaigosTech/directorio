package directorio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;

public class Rol extends Entidad implements Serializable {
	public static Logger log = Logger.getLogger(Rol.class.getName());
    
	public Rol(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}

	public String toString() {
		return  "id:" + id + ", descripcion rol: " + nombre;
	}

	/** 
	 * Constructor simple, sin permisos. 
	 * 
	 * @param id 
	 * @param descripcion 
	 */

	public static Rol loadFromUsuario(int idPersona) {
		String sql = "SELECT UR.id_rol, CR.DESCRIPCION " +
				" FROM USUARIO_ROL UR, CAT_ROL CR " +
				" WHERE UR.id_rol = CR.id_rol " + 
				" AND UR.id_persona = " + idPersona;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		return new Rol(Integer.parseInt((String)mapa.get("id_rol")),
								  (String)mapa.get("descripcion"));
	}

	/** 
	 * Lo utilizo cuando cargo el rol a partir de su propio id; 
	 * obtengo tambi�n la lista de todos los permisos que tiene
	 * este rol en particular.
	 * 
	 * @param idUsuario 
	 * @return 
	 */
	public static Rol loadFromId(int idRol) {
		String sql = "SELECT DESCRIPCION " +
				" FROM CAT_ROL " +
				" WHERE ID_ROL = " + idRol;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa != null) {
			
			return new Rol(idRol, (String)mapa.get("descripcion"));
		}
		return null;
	}
	
	public boolean startsWith(String pagina){
		if(pagina.startsWith(this.nombre))
			return true;
		return false;
	}
}

