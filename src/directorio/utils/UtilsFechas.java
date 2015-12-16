package directorio.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import directorio.pages.OcraBasePage;

/** 
 * Utiler�as varias para formatear fechas 
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class UtilsFechas {
	public static Logger log = Logger.getLogger(OcraBasePage.class.getName());
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfMesEspecial = new SimpleDateFormat("yyyy/M/dd");
	private static SimpleDateFormat sdfConHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat sdfConHora2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private static SimpleDateFormat sdfPlano = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfLotes = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat sdfLotesHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static SimpleDateFormat sdfMesLetra = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfSybase = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfSybaseConHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
	private static SimpleDateFormat soloMes = new SimpleDateFormat("MM");
	private static SimpleDateFormat soloAno = new SimpleDateFormat("yyyy");
        
   private static SimpleDateFormat sdfSybaseh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   private static SimpleDateFormat sdfVerboso = new SimpleDateFormat("yyyy MMM dd HH:mm",Locale.US);
   private static SimpleDateFormat sdfReportes = new SimpleDateFormat("dd-MMM-yyyy HH:mm",new Locale("ES", "MX"));

   public static Date parseSybaseH(String fecha) {
	   if (fecha == null)
		   return null;
	   try {
		return sdfSybaseh.parse(fecha);
	   } catch (ParseException e) {
    		e.printStackTrace();
   	   }
	   return null;
   }
   
   public static Date parseLotes(String fecha) {
	   if (fecha == null)
		   return null;
	   try {
		return sdfLotes.parse(fecha);
	   } catch (ParseException e) {
    		e.printStackTrace();
   	   }
	   return null;
   }
   
   public static String fromDateToStringLotes(Date fecha) {
	   if (fecha == null)
    	   return null;
	   try {
		   return sdf.format(fecha);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return null;
   }
   
   public static Date parseFechaUsuario(String fecha) {
	   if (fecha == null || fecha == "")
		   return null;
	   try {
		return sdfSybase.parse(fecha);
	   } catch (ParseException e) {
    		e.printStackTrace();
   	   }
	   return null;
   }
   
	public static String formatoLotes(String fecha) {
		if (fecha == null || fecha.length() == 0)
			return "";
		try {
			return sdf.format(sdfLotes.parse(fecha));
		} catch (Exception e) {
			log.debug("Error parseando fecha " + fecha + ": " + e.getMessage());
			return null;
		}
	}
   
	public static String formatoLotesHora(String fecha) {
		if (fecha == null || fecha.length() == 0)
			return "";
		try {
			return sdf.format(sdfLotesHora.parse(fecha));
		} catch (Exception e) {
			log.debug("Error parseando fecha " + fecha + ": " + e.getMessage());
			return null;
		}
	}

	public static String formato(String fecha) {
		try {
			return sdf.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}

	public static String formatoConHora(String fecha) {
		try {
			return sdfConHora.format(sdfSybaseConHora.parse(fecha));
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}
	
	public static String formatoArchivoExcel(String fecha) {
		try {
			return sdfMesEspecial.format(sdfSybaseConHora.parse(fecha));
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}

	public static String formato(Date fecha) {
		try {
			return sdf.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}

	public static String formatoReportes(Date fecha) {
		try {
			return sdfReportes.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}

	public static String formatoMesLetra(Date fecha) {
		try {
			return sdfMesLetra.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}
	
	public static String formatoMesLetraSybase(Date fecha) {
		try {
			return sdfSybase.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}

	public static String formatoMesLetra2(Date fecha) {
		try {
			return sdfLotes.format(fecha);
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			return "";
		}
	}
	
	public static String formatoMesLetra(String fecha) {
		try {
			return sdfMesLetra.format(sdfLotes.parse(fecha));
		} catch (Exception e) {
			log.debug("Error: " + e.getMessage());
			try {
				return sdfMesLetra.format(sdfSybaseConHora.parse(fecha));
			} catch (Exception e2) {
				log.debug("Error: " + e.getMessage());
			}
			return "";
		}
		
	}
        
        //Convierte un Date en Cadena, a�adiendo la HH:mm:ss
    public static String formatoH(Date fecha) {
		try {
			return sdfSybaseh.format(fecha);
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return "";
		}
	}
    
    public static String formatoPlano(Date fecha) {
		try {
			return sdfPlano.format(fecha);
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return "";
		}
	}
        
   public static String formatoVerboso(String fecha) {
		try {
			return sdfVerboso.format(sdf.parse(fecha));
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return "";
		}
	}

    public static Date parseSybaseAlt(String fecha) {
	   if (fecha == null)
		   return null;
	   try {
		return sdf.parse(fecha);
	   } catch (ParseException e) {
    		//e.printStackTrace();
   	   }
	   return null;
    }

    public static Date parseSybaseAltConHora(String fecha) {
	   if (fecha == null)
		   return null;
	   try {
		return sdfConHora2.parse(fecha);
	   } catch (ParseException e) {
    		//e.printStackTrace();
   	   }
	   return null;
    }

	public static int muestraMes(Date fecha) {
		try {
			return Integer.parseInt(soloMes.format(fecha));
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return 0;
		}
	}
	public static int muestraAno(Date fecha) {
		try {
			return Integer.parseInt(soloAno.format(fecha));
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return 0;
		}
	}
	
	public static Date quitaHora(Date fecha) {
		try {
			return sdf.parse(sdf.format(fecha));
		} catch (Exception e) {
                    e.printStackTrace();
                    log.debug("Error: " + e.getMessage());
		    return null;
		} 
	}
}
