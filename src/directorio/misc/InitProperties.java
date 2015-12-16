package directorio.misc;


import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import org.apache.log4j.Logger;

import directorio.pages.OcraBasePage;
import directorio.utils.Propiedades;

/** 
 * Abre el archivo config.properties y lee las propiedades 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public final class InitProperties extends HttpServlet {
	public static Logger log = Logger.getLogger(InitProperties.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response)
		 throws IOException, ServletException {
	 }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 }

    public void init(ServletConfig config) throws ServletException {
    	
		 String archivo = config.getInitParameter("config.file");
		 log.debug("Directorio/initParam: " + archivo);
		 if (archivo == null)
			 archivo = "config.properties";

		 // Esto del getResourceAsStream lo hago porque puede que la aplicaci�n
		 // se est� ejecutando desde un .war		 
		 Propiedades.inicializa(config.getServletContext().
				 getResourceAsStream("/WEB-INF/" + archivo)); 
		 //Propiedades.inicializa( ruta + archivo );
		 String rutaCompleta = config.getServletContext().getRealPath("/");
		 log.debug("rutaCompleta: " + rutaCompleta);
		 Propiedades.put("rutaCompleta", rutaCompleta);
	 }

}
