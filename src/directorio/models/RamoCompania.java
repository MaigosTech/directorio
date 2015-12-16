package directorio.models;

import org.apache.log4j.Logger;

public class RamoCompania {
	public static Logger log = Logger.getLogger(RamoCompania.class.getName());
	private int idRamo;
	private int idCia;
	
	public int getIdRamo() {
		return idRamo;
	}
	public void setIdRamo(int idRamo) {
		this.idRamo = idRamo;
	}
	public int getIdCia() {
		return idCia;
	}
	public void setIdCia(int idCia) {
		this.idCia = idCia;
	}
}
