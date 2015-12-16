package directorio.models.enums;

/**
 * Describe un tipo de operacion: ppt, pago, salvamento, consulta
 * @author dariovasconcelos
 *
 */
public enum TipoMovimiento {
	ALTA_O_MODIFICACION,CANCELACION_O_REHABILITACION;

	public static String getString(TipoMovimiento tipoOperacion) {
		if (tipoOperacion == null)
			return null;
		return tipoOperacion.toString();
	}
}
