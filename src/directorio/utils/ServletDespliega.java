/*
 * Created on Sep 8, 2004
 *
 */
package directorio.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Sirve para desplegar un PDF que se encuentra en el filesystem.<br>
 * S�lo funciona si recibe los par�metros v�a POST (para agregarle algo de
 * seguridad) y eventualmente revisa si el usuario ha iniciado la sesi�n. 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class ServletDespliega extends HttpServlet {
	public static Logger log = Logger.getLogger(ServletDespliega.class.getName());
        
    /** Maneja el request de un GET; no devuelve nada porque est� deshabilitado.
	  *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
	 	return;
    }
    
    /** Handles the HTTP <code>POST</code> method.
	  * El param "a" es el nombre del documento, 
	  * el param "b" es una bandera para poner el #de a�o o no
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
	 	String param = request.getParameter("a");
	 	if ( param == null)
			return;
		if (param.indexOf("-08") != -1)
			param = param.substring(0, param.length()-3);

		// Obtengo la ruta
		String ruta = getServletContext().getRealPath("WEB-INF/pdf");

		log.debug("ServletDespliega: " + param);
	 	File archivo = null;
		if (request.getParameter("b") != null)
			archivo = new File(ruta + "/" + param + ".pdf");
		else
			archivo = new File(ruta + "/" + param + "-08.pdf");

		if (!archivo.isFile()) {
			log.debug("ServletDespliega: el archivo " + archivo.toString() + " no existe" );
			return;
		}
		try { 
			BufferedInputStream in = new BufferedInputStream( 
											new FileInputStream(archivo));
		   ServletOutputStream out = response.getOutputStream();

		   response.setContentType(getContentType(param));
			response.setHeader("Content-disposition","attachment; filename=\"" + 
					archivo.getName() + "\"");

			byte[] arrCar = new byte[1000];
			int bytesLeidos = in.read(arrCar, 0, 1000);
			while (bytesLeidos != -1) {
				out.write(arrCar, 0, bytesLeidos);
				bytesLeidos = in.read(arrCar, 0, 1000);
			}

			in.close();
			out.close();

		} catch (FileNotFoundException fnfe) {
			log.debug("ServletDespliega - Error: archivo no encontrado: " + fnfe.getMessage());
			return;
		} catch (IOException ioe) {
			log.debug("ServletDespliega - Error de I/O: " + ioe.getMessage());
			return;
		}
    }
    
	 /** 
	  * Devuelve el content type del archivo, dependiendo de su
	  * extensi�n 
	  * 
	  * @param cadena 
	  * @return 
	  */
	 private String getContentType(String cadena) {
		int pos = cadena.lastIndexOf(".");
		if (pos == -1)
			return "application/octet-stream";
		else
			return lookupContentType(cadena.substring(pos+1, cadena.length()).toLowerCase());
	 }

	 /** 
	  * Contiene una tabla de tipos mime comunes... 
	  * 
	  * @param cadena 
	  * @return 
	  */
	 private String lookupContentType(String cadena) {
		 if (cadena == null || cadena.length() == 1)
			 return "application/octet-stream";
		 else if ("ppt".equals(cadena))
			 return "application/mspowerpoint";
		 else if ("xls".equals(cadena))
			 return "application/excel";
		 else if ("doc".equals(cadena))
			 return "application/msword";
		 else if ("jpg".equals(cadena))
			 return "image/jpeg";
		 else if ("java".equals(cadena))
			 return "text/plain";
		 else if ("pdf".equals(cadena))
			 return "application/pdf";
		 else if ("pps".equals(cadena))
			 return "application/mspowerpoint";
		 else if ("htm".equals(cadena))
			 return "text/html";
		 else if ("html".equals(cadena))
			 return "text/html";
		 else if ("txt".equals(cadena))
			 return "text/plain";
		 else
			 return "application/octet-stream";
	 }

}

