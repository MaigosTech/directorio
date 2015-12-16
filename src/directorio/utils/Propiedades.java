package directorio.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Propiedades {
	public static Logger log = Logger.getLogger(Propiedades.class.getName());
	private static Properties props = new Properties();

	public static void inicializa(String archivo) {
		try {
			log.info("Inicializando archivo " + archivo);
			props.load(new FileInputStream(archivo));
		} catch (IOException e) {
			log.error("Error abriendo archivo " + archivo + 
							", mensaje: " + e.getMessage());
		}
	}

	/** 
	 * Recibe un InputStream en vez de un String 
	 * 
	 * @param archivo 
	 */
	public static void inicializa(InputStream archivo) {
		try {
			log.info("Inicializando archivo " + archivo);
			props.load(archivo);
		} catch (IOException e) {
			log.error("Error abriendo archivo " + archivo + 
							", mensaje: " + e.getMessage());
		}
	}
	public static String get(String propiedad) {
		return props.getProperty(propiedad);
	}
	public static Enumeration getPropertyNames() {
		return props.propertyNames();
	}
	public static void put(String key, String value) {
		props.put(key, value);
	}
}
