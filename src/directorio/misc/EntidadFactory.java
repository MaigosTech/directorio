package directorio.misc;

import directorio.models.Area;
import directorio.models.CanalVenta;
import directorio.models.CapitalOrigen;
import directorio.models.CapitalSocial;
import directorio.models.Compania;
import directorio.models.CompaniaCombo;
import directorio.models.Entidad;
import directorio.models.Estado;
import directorio.models.NivelPersona;
import directorio.models.Oficina;
import directorio.models.Persona;
import directorio.models.Ramo;
import directorio.models.Rol;
import directorio.models.TipoMercado;
import directorio.models.TipoPersona;

/** 
 * Clase que crea objetos de tipo Entidad a partir del tipo de entidad
 * y los datos b�sicos que se utilizan en el constructor. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
 
public class EntidadFactory {
	public static Entidad initEntidad(int tipoEntidad, int id, String descripcion) throws Exception {
	//public static Entidad initEntidad(int tipoEntidad, int id, String descripcion) {
		switch(tipoEntidad) {
			case Entidad.ENTIDAD_RAMO:
				return new Ramo(id, descripcion);
			case Entidad.ENTIDAD_ROL:
				return new Rol(id, descripcion);
			case Entidad.ENTIDAD_AREA:
				return new Area(id, descripcion);
			case Entidad.ENTIDAD_PERSONA:
				return new Persona(id, descripcion);	
			case Entidad.ENTIDAD_CAPITALO:
				return new CapitalOrigen(id, descripcion);
			case Entidad.ENTIDAD_TIPOMER:
				return new TipoMercado(id, descripcion);
			case Entidad.ENTIDAD_CANALVENTA:
				return new CanalVenta(id, descripcion);
			case Entidad.ENTIDAD_CAPITALS:
				return new CapitalSocial(id, descripcion);
			case Entidad.ENTIDAD_COMPANIA:
				return new Compania(id, descripcion);
			case Entidad.ENTIDAD_TIPOP:
				return new TipoPersona(id, descripcion);
			case Entidad.ENTIDAD_NIVELP:
				return new NivelPersona(id, descripcion);
			case Entidad.ENTIDAD_OFICINA:
				return new Oficina(id, descripcion);
				/*
			//case Entidad.ENTIDAD_PERSONA:
				//return new TipoPersona(id, descripcion);
			case Entidad.ENTIDAD_USUARIO:
				return new UsuarioSimple(id, descripcion);
			case Entidad.ENTIDAD_TIPO_SERVICIO:
				return new Servicio(id, descripcion); */
			default:
				throw new Exception("EntidadFactory: tipo de entidad no declarado - " + tipoEntidad);
				//return new Pais(id, descripcion);
		}
	}

	public static Entidad initEntidad(int tipoEntidad, int id, String descripcion, 
												Entidad padre) {
		switch(tipoEntidad) {
		/*case Entidad.ENTIDAD_OFICINA:
			return new Oficina(id, descripcion, padre);*/
		/*case Entidad.ENTIDAD_ESTADO:
				return new Estado(id, descripcion, padre); */
			/*case Entidad.ENTIDAD_OFICINA:
				return new Oficina(id, descripcion, padre); */
			default:
				return new Estado(id, descripcion, padre);
		}
	}
}
