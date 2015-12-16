package directorio.pages;

import java.util.*;

import directorio.utils.Propiedades;
import directorio.utils.UtilsBD;

/** 
 * Rol
 * 
 * @author Francisco Pe�a (pacop28@gmail.com)
 * @version 1.0
 */
public abstract class Info extends OcraBasePage {
	public abstract void setVersionJava(String s);
	public abstract void setIpBase(String s);
	public abstract void setRutaTomcat(String s);
	public abstract void setAplicacion(String s);
	public abstract void setSmtp(String s);
	public abstract void setMail(String s);
	public abstract void setCesvi(String s);
	
   public void submitForm() {
   }

   public void pageBeginRender() {
	    obtieneDatos();
   }

   public void obtieneDatos(){
		try{			
			//Obtengo las propiedades del sistema
			Properties props = System.getProperties();

			String catalina = (String)props.get("catalina.home");
			String javaVer = (String)props.get("java.version");
			String aplica = Propiedades.get("db.dataSource");
			String ipDB = Propiedades.get("db.ip");
			String smtp = Propiedades.get("mail.smtp.server");
			String mail = Propiedades.get("mail.ocra.sender");
			String cesvi = Propiedades.get("cesvi.URL");
			
			setVersionJava(javaVer);
			setRutaTomcat(catalina);
			setIpBase(ipDB);
			setAplicacion(aplica);
			setCesvi(cesvi);
			setSmtp(smtp);
			setMail(mail);
			
		}catch(Exception e){
		}
   }
}
