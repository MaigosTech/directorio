package directorio.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class TextDownloadService {
	public static final String SERVICE_NAME = "textdownload";

//	public ILink getLink(Object[] parameters) {
//		return constructLink(cycle, SERVICE_NAME, null, parameters, false);
//	}
//
//	public void service( IEngineServiceView engine, IRequestCycle cycle,
//						ResponseOutputStream output) throws ServletException, IOException {
//		// Obtengo parametros
//		Object[] params = getParameters(cycle);
//		String nombreArchivo = (String) params[0];
//		String finalDirectorio = (String) params[1];
//
//		//log.debug("dentro de service de filedownload, nombreArchivo " + nombreArchivo);
//
//		// Obtengo ruta completa del archivo
//		String rutaCompleta = cycle.getRequestContext().getServlet().
//								getServletContext().getRealPath("/") + 
//								"tmp/lotes_" + finalDirectorio + "/" + nombreArchivo + 
//								".txt";
//
//		//log.debug("ruta completa:" + rutaCompleta);
//
//		// Pongo contentType y el header para attachment
//		HttpServletResponse resp = cycle.getRequestContext().getResponse();
//		//log.debug("resp:" + resp);
//		output.setContentType("text/plain");
//
//		// Eliminado porque hay un bug en el Tomcat de Linux.
//		/*
//		resp.setHeader("Content-disposition", 
//						"attachment; filename=\"" + nombreArchivo +
//						("error".equals(finalDirectorio)?"_error":"") + "\"");
//						*/
//
//		// Then stream the data
//		String contenidoArchivo = getFileContents(rutaCompleta);
//		resp.setContentLength(contenidoArchivo.length());
//		//log.debug("Contenido del archivo:"+ contenidoArchivo);
//		output.write(contenidoArchivo.getBytes(), 0, contenidoArchivo.length());
//		output.close();
//	}
//
//	public String getFileContents(String ruta) {
//	   String respuesta;
//		BufferedReader in = null;
//		StringBuffer out = new StringBuffer();
//		try {
//			in = new BufferedReader(new FileReader(ruta));
//			while (( respuesta = in.readLine()) != null) {
//				out.append(respuesta + "\n");
//			}
//		} catch (Exception e) {
//			log.debug("Error al abrir el archivo " + ruta + ":" + e.getMessage());
//		} finally {
//			try {
//				in.close();
//			} catch(Exception e) {}
//		}
//		return out.toString();
//	}
//
//	public String getName() {
//		return SERVICE_NAME;
//	}

}
