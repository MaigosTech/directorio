package directorio.models.enums;

/**
 * Describe un tipo de operacion: ppt, pago, salvamento, consulta
 * @author dariovasconcelos
 *
 */
public enum TipoConsultaLote {
	EMISION_SINIESTRO, SINIESTRO, ALERTAS;

	public static String getString(TipoConsultaLote tipoOperacion) {
		if (tipoOperacion == null)
			return null;
		return "CONSULTA-" + tipoOperacion.toString();
	}
}
