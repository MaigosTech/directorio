package directorio.models;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

import directorio.utils.UtilsBD;

/** 
 * Representa a una entidad, clase abstracta. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public abstract class Entidad implements Comparable {
	public final static int ENTIDAD_RAMO = 1;
	public final static int ENTIDAD_RAMO_CIA = 2;
	public final static int ENTIDAD_OFICINA = 3;
	public final static int ENTIDAD_CAPITALS = 4;
	public final static int ENTIDAD_CAPITALO = 5;
	public final static int ENTIDAD_TIPOMER = 6;
	public final static int ENTIDAD_CANALVENTA = 7;
	public final static int ENTIDAD_COMPANIA = 8;
	public final static int ENTIDAD_COMPANIAH = 9;
	public final static int ENTIDAD_AREA = 10;
	public final static int ENTIDAD_TIPOP = 11;
	public final static int ENTIDAD_NIVELP = 12;
	public final static int ENTIDAD_PERSONA = 13;
	public final static int ENTIDAD_PERSONAH = 14;
	public final static int ENTIDAD_USUARIO = 15;
	public final static int ENTIDAD_ROL = 16;
	
	
	
	/** 
	 * Apunta hacia otro objeto que tiene que ser una entidad
	 * de nivel mayor. 
	 */
	public Entidad padre;
	public int id;
	public String nombre;
	public List hijos = new ArrayList();
	
	
	/** 
	 * Variable que sirve para Tapestry, para ver si est� seleccionada la entidad. 
	 */
	private boolean selected;

	public Entidad() {
	}

	public String toString() {
		return id + "|" + nombre + "|" + padre;
	}

	public void agregaHijo(Entidad hijo) {
		hijos.add(hijo);
	}

	/** 
	 * Implementado para interfase Comparable, porque traigo un
	 * arreglo ordenado (TreeSet) de elementos de tipo Entidad... 
	 * 
	 * @param o1 
	 * @param o2 
	 * @return 
	 */
	public int compareTo(Object o1) {
		String nombre1 = ((Entidad)o1).getNombre();

		return this.nombre.compareTo(nombre1);
	}

	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public int getId() { return id; }
	public void setId() { this.id = id; }
	public List getHijos() { return hijos; }
	public Entidad getPadre() { return padre; }

	/** 
	 * Devuelve una lista proveniente de la base de datos que trae
	 * todas las entidades de un cierto tipo (pais, estado, municipio, etc)
	 * con su ID, descripcion y el ID del padre. 
	 * 
	 * @param tipoEntidad 
	 * @return 
	 * @throws Exception 
	 */
	public static List getEntidades(int tipoEntidad) throws Exception {
		String sql = "";
		switch(tipoEntidad) {
		case ENTIDAD_RAMO:
			sql = "SELECT ID_RAMO AS ID, DESCRIPCION FROM CAT_RAMO ORDER BY DESCRIPCION";
			break;
		case ENTIDAD_RAMO_CIA:
			sql = "SELECT ID_RAMO, ID_CIA FROM RAMO_COMPANIA";
			break;
		case ENTIDAD_OFICINA:
			sql = "SELECT ID_OFICINA AS ID, CONVERT(VARCHAR, ID_CIA) + '-' + DESCRIPCION AS DESCRIPCION FROM OFICINA";			
			break;
		case ENTIDAD_CAPITALS:
			sql = "SELECT ID_CAPITAL_SOCIAL AS ID, DESCRIPCION FROM CAT_CAPITAL_SOCIAL";
			break;
		case ENTIDAD_COMPANIA:
			sql = "SELECT ID_CIA AS ID, NOMBRE_CORTO AS DESCRIPCION FROM COMPANIA ORDER BY ID";
			break;
		case ENTIDAD_CAPITALO:
			sql = "SELECT ID_CAPITAL_ORIGEN AS ID, DESCRIPCION FROM CAT_CAPITAL_ORIGEN";
			break;
		case ENTIDAD_TIPOMER:
			sql = "SELECT ID_TIPO_MERCADO AS ID, DESCRIPCION FROM CAT_TIPO_MERCADO";
			break;
		case ENTIDAD_CANALVENTA:
			sql = "SELECT ID_CANAL_VENTAS AS ID, DESCRIPCION FROM CAT_CANAL_VENTA";
			break;
		case ENTIDAD_AREA:
			sql = "SELECT ID_AREA AS ID, DESCRIPCION FROM CAT_AREA";
			break;
		case ENTIDAD_TIPOP:
			sql = "SELECT ID_TIPO_PERSONA AS ID, DESCRIPCION FROM CAT_TIPO_PERSONA";
			break;
		case ENTIDAD_NIVELP:
			sql = "SELECT ID_NIVEL_PERSONA AS ID, DESCRIPCION FROM CAT_NIVEL_PERSONA";
			break;
		case ENTIDAD_PERSONA:
			sql = "SELECT ID_PERSONA AS ID, DESCRIPCION = (NOMBRE + ' ' + PATERNO + ' ' + MATERNO) FROM PERSONA WHERE ID_CIA = 10000";
			break;
		case ENTIDAD_ROL:
			sql = "SELECT ID_ROL AS ID, DESCRIPCION FROM CAT_ROL";
			break;
		default:
			throw new Exception("TIPO DE ENTIDAD NO VÁLIDO: " + tipoEntidad);
		}		
		return new UtilsBD().querySingleListSybase(sql);
	}

	public boolean getSelected() { return selected; }
	public void setSelected(boolean selected) { this.selected = selected; }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidad other = (Entidad) obj;
		if (id != other.id)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}


}
