package directorio.utils;

import java.util.Properties;
import java.util.Date;
import java.sql.*;

public class DBConnectionPoolUA extends AbstractPool {
    private static DBConnectionPoolUA instance = new DBConnectionPoolUA(
			 						Integer.parseInt(Propiedades.get("ua.dbpool.min")),
			 						Integer.parseInt(Propiedades.get("ua.dbpool.max")));

    private DBConnectionPoolUA(int min, int max) {
        super(min, max);
    }
    
    protected boolean beforeFreeObject(Object object) {
        return true;
    }
    
    protected Object createObject() {
		try {
			 Properties props = new Properties();
			 Class.forName(Propiedades.get("ua.db.jdbc"));
			 String cadena = Propiedades.get("ua.db.cadena");
			 String user   = Propiedades.get("ua.db.user");
			 String pwd    = Propiedades.get("ua.db.pwd");
			 cadena = replaceFirst(cadena, "#server#", Propiedades.get("ua.db.server"));
			 cadena = replaceFirst(cadena, "#puerto#", Propiedades.get("ua.db.puerto"));
			 cadena = replaceFirst(cadena, "#db#", Propiedades.get("ua.db.db"));

			 log.debug("Conectando con " + cadena);
			 Connection conn = DriverManager.getConnection (cadena, user, pwd);

			 return conn;
	 	} catch (Exception e) {
			 log.debug("Init: Error inicializando conexi�n a base de datos del UA, " + e.getMessage());
		}
		return null;
    }
    
    public static DBConnectionPoolUA getInstance(){
        return instance;
    }
    
    /**
     *  El m�todo solo hace un llamado a la clase padre, pero es para hacer
     *  la interfaz m�s clara
     */
    public Connection getConexionDB(){
        return (Connection) super.getObject();
    }

    public Connection getConnection(){
        return (Connection) super.getObject();
    }

	 public void freeConnection(Connection con) {
		 super.freeObject(con);
	 }
    
    protected boolean testObject(Object object) {
        return validateConnection((Connection)object);
    }
    
    private boolean validateConnection(Connection con){
		  // Si ya reutilic� el objeto suficientes veces, obligo a que se refresque
		  // y cierro la conexi�n.
		 /*
		  int num = addOne(con);
		  //log.debug("Conexi�n " + con + " , " + num);
		  if ((num >= maxUsos) && (Math.random() > .3333333)) {
			  resetObject( con );
			  try {
				  con.close();
				  con = null;
			  } catch (Exception e) {
				  log.debug("Error tratando de cerrar conexi�n con m�ximo de reusos: " + e.getMessage() + " (conexion:" + con.toString() + ")");
			  }
			  return false;
		  }
		  */
		  
		  String sql = "select getdate()";
        if(con == null)
            return false;
                
        Statement statement = null;
		  ResultSet rs        = null;

        try{
            statement = con.createStatement();
            rs = statement.executeQuery(sql);
        } catch(SQLException sqlException){
			  /*  Si entramos en el catch por cualquier causa entonces la
					conexion ya est� cerrada
				*/
            log.debug("DBConnectionPoolUA.validateConnection(Object): No se pudo ejecutar el query de validaci�n de conexion: " + sqlException.getMessage());
            try{
                con.close();
            } catch(SQLException sqlException1){
                log.debug("DBConnectionPoolUA.validateConnection(Connection):con.close():" + sqlException1.getMessage());
            }
            return false;
        } finally{
            try{
					 if (rs != null)
						 rs.close();
					 rs = null;
                if(statement != null)
                    statement.close();
					 statement = null;
            } catch(SQLException anotherSQLException){
                log.debug("DBConnectionPoolUA.validateConnection(Connection):statement.close():" + anotherSQLException.getMessage()); } }
        
		  return true; 
	 }

	 /** Este m�todo es un reemplazo para el String.replaceFirst que s�lo existe
	  * a partir del JDK 1.4
	  */
	 private String replaceFirst(String cadena, String search, String replace) {
		 if (cadena == null || cadena.length() == 0)
			 return "";
		 int pos = cadena.indexOf(search);
		 if (pos < 0)
			 return cadena;
		 return cadena.substring(0, pos) + 
			 	  replace + 
				  cadena.substring(pos+search.length(), cadena.length());
	 }
}
