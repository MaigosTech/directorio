package directorio.utils;

import java.util.*;
import java.util.regex.*;

import directorio.utils.UtilsBD;

/** 
 * Clase con utiler�as para hacer validaciones de campos 
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 
 */
public class Validaciones {
	
	public static boolean comun(String cadena){
		Pattern p = Pattern.compile("[^A-Z]&&[^0-9]");
        Matcher m = p.matcher(cadena);
        return m.find();	
	}

	public static boolean comunEsp(String cadena){
		Pattern p = Pattern.compile("[^A-Z][^0-9][^\\-][^\\/]");
        Matcher m = p.matcher(cadena);
        return m.find();	
	}

	public static boolean cp(String cadena){
		if(cadena == "")
			return false;
		Pattern p = Pattern.compile("[^0-9]");
        Matcher m = p.matcher(cadena);
        return m.find();
	}

	public static boolean hora(String cadena){
		if(cadena == "")
			return false;
		int hora = Integer.parseInt(cadena.substring(0,2));
		if(hora < 0 || hora > 23)
			return false;
		int min = Integer.parseInt(cadena.substring(3));
		if(min < 0 || min > 59)
			return false;
		return true;
	}

	public static boolean fecha(String cadena){
		String fechaSis = fechaBD();
		int diaSis = Integer.parseInt(fechaSis.substring(0,fechaSis.indexOf("/")));
		int mesSis = Integer.parseInt(fechaSis.substring(fechaSis.indexOf("/")+1,fechaSis.indexOf("-")));
		int anoSis = Integer.parseInt(fechaSis.substring(fechaSis.indexOf("-")+1));
		int diaEnt = Integer.parseInt(cadena.substring(0,2));
		int mesEnt = Integer.parseInt(cadena.substring(3,5));
		int anoEnt = Integer.parseInt(cadena.substring(6));
		if(anoEnt > anoSis)
			return false;
		if((anoEnt == anoSis) && (mesEnt > mesSis))
			return false;
		if((anoEnt == anoSis) && (mesEnt == mesSis) && (diaEnt > diaSis))
			return false;
		return true;
	}
	
	public static boolean no_serie(String cadena) {

		if(cadena.length() < 5)
			return false;
	  
		Pattern p = Pattern.compile("[^A-Z][^0-9]");
        Matcher m = p.matcher(cadena);
        return m.find();
	}

	public static boolean modelo(String cadena){
		String mesAno = backMesAno();
		int mes = Integer.parseInt(mesAno.substring(0,mesAno.indexOf("-")));
		int anio = Integer.parseInt(mesAno.substring(mesAno.indexOf("-")+1));
		if(mes > 2)
			anio++;
		if(Integer.parseInt(cadena) > anio)
			return false;
		return true;
	}

	public static String backMesAno(){
		String sql = "select getDate() fechadb";
		HashMap fechaMap = new UtilsBD().queryMapa(sql);
		String sFecha = (String)fechaMap.get("fechadb");
		String mes = sFecha.substring(5,7);
		String anio = sFecha.substring(0,4);
		return mes + "-" + anio;
	}

	public static String fechaBD(){
		String sql = "select getDate() fechadb";
		HashMap fechaMap = new UtilsBD().queryMapa(sql);
		String sFecha = (String)fechaMap.get("fechadb");
		String mes = sFecha.substring(5,7);
		String dia = sFecha.substring(8,10);
		String anio = sFecha.substring(0,4);
		
		sFecha = dia + "/" + mes + "-" + anio;
		return sFecha;
	}
}
