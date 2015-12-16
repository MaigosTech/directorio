package directorio.models.enums;

/**
 * Describe un tipo de operacion: ppt, pago, salvamento, consulta
 * @author dariovasconcelos
 *
 */
public enum TipoOperacion {
	EMISION, SINIESTRO, RECHAZO, PREVENCION, VALUACION;

	public static String getString(TipoOperacion tipoOperacion) {
		if (tipoOperacion == null)
			return null;
		return tipoOperacion.toString();
	}
}
