package directorio.utils;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.DataSources;

import directorio.models.Global;

/** 
 * Clase wrapper para las operaciones de base de datos. <br>
 * Se emplea para abreviar el acceso a la base de datos, sin tener
 * que declarar excepciones y bloques try..catch. <br>
 * Los m�todos principales son queryMapa, queryLista, queryInt y queryString;
 * estos dos �ltimos asumen que se debe devolver s�lo la primer columna
 * del primer rengl�n del resultado, independientenemente del query.
 * <br>. Todos los m�todos que devuelven resultados de queries devuelven
 * un objeto null cuando el query no devuelve resultados o tiene error, excepto
 * queryInt que devuelve un -1.<br>
 * <b>Uso recomendado</b><br>
 * En general, es recomendable instanciar el objeto UtilsBD() dentro del m�todo
 * que lo manda llamar, porque su inicializaci�n es casi gratuita y as� se evita
 * tener el objeto rondando por ah� en el caso de hacerlo variable miembro de la clase. 
 * De este modo, el c�digo se mantiene limpio de potenciales problemas por el uso de 
 * variables de clase y contenci�n causada por c�digo no thread-safe. Esta clase
 * no es thread-safe porque no sincroniza en ning�n momento sus m�todos, para 
 * darle velocidad.<br>
 * El uso t�pico es simplemente hacer lo siguiente:
 * <pre>String nombre = new UtilsBD().queryString("select nombre from tabla");</pre>
 * El ejemplo anterior aplica para queries que devuelvan un rengl�n con una columna. 
 * Cuando sabemos que el resultado ser� un <i>int</i>, podemos hacer lo siguiente:
 * <pre>int max = new UtilsBD().queryInt("select max(id) from tabla");</pre>
 * Para casos m�s generales, se utiliza el m�todo queryLista, o si sabemos que
 * el query me devuelve s�lo un rengl�n, puede usarse queryMapa. Ver la documentaci�n
 * de las clases que acceden estos datos para entender c�mo consumir estos resultados.
 * <br>
 * <b>Soporte para transacciones</b><br>
 * Simplemente hay que mandar llamar al m�todo iniciaTran(), que se encarga 
 * de inicializar una conexi�n y utilizarla en todos los queries subsecuentes.
 * Al final se debe llamar, obviamente, a commit() o a rollback() en caso de
 * problemas; sin embargo, si el programador olvida llamar alguno de esos dos m�todos,
 * asumo que todo va bien y en el m�todo destroy() del objeto hago el commit,
 * devolviendo la conexi�n al pool (esto es important�simo, de lo contrario estar�a
 * consumiendo conexiones del pool cada vez que utilizara transacciones).<br>
 * Despu�s de llamar al m�todo commit() o rollback(), el objeto UtilsBD sigue
 * siendo v�lido para ejecutar queries, sin embargo 
 *
 * <br><br>
 * Julio 2007: DV, cambios para hacer que soporte transacciones.
 * 
 * @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
 * @version 
 */
public class UtilsBD {
	private static final Logger logger = Logger.getLogger(UtilsBD.class.getName());
	
	/** 
	 * Contiene la conexi�n que se utilizar� s�lo cuando se inicie una
	 * transacci�n con el m�todo iniciaTran().
	 */
	private Connection conexionInterna = null;

	/**
	 * Contiene un statement que se almacena para que se pueda hacer un batch.
	 */
	private Statement stmtInterno = null;

	/** 
	 * True cuando ya quiero que se cierre la conexion interna; util para
	 * los metodos commit() y rollback(). 
	 */
	private boolean cierraConexionInterna = false;

	/** 
	 * Guarda el estatus de la ultima operacion ejecutada. 
	 */
	private boolean ultimaOperacionOK = false;
	
	/** 
	 * Apunta hacia el pool de conexiones; es una variable estatica para solo
	 * tener que obtener una vez la referencia hacia ella. <br>Es un objeto
	 * de tipo javax.sql.DataSource. 
	 */
	private static DataSource pool = getDataSource();
	//private static JCMCache pool = getJCMCache();
	//static DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************************** Inicia definici�n de m�todos ******************/
	public UtilsBD() {
	}

	/*	
	private static JCMCache getJCMCache() {
		try {
			return JCM.getCacheByName(Propiedades.get("db.dataSource"));
		} catch (Exception e) {
			logger.debug("Error al obtener el cache:" + e.getMessage());
		}
		return null;
	}
	*/

	/** 
	 * Obtiene el data source desde el archivo de propiedades y lo utiliza
	 * para las conexiones a la base de datos. <br>
	 * <b>Esto se obtiene antes que el archivo de configuracion</b>
	 * 
	 * @return 
	 */
	private static DataSource getDataSource() {
		Locale.setDefault(Locale.US);
		Context ctx = null;
		try {
			logger.debug( "Utilizando datasource " + 
				Propiedades.get("db.dataSource"));

			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/" + Propiedades.get("db.dataSource"));
			return ds;
		} catch (Exception e) {
			// Intento con el otro tipo de DS (tomcat)
			try {
				logger.debug( "Intentando con el otro dataSource");
				Context envCtx = (Context) ctx.lookup("java:comp/env");
				return (DataSource)envCtx.lookup("jdbc/" + Propiedades.get("db.dataSource"));
			} catch (Exception e2) {
				// Ahora hago el intento con c3p0
				DataSource unpooled;
				try {
					Class.forName(Propiedades.get("db.jdbc").trim());
					String cadena = Propiedades.get("db.cadena");
					cadena = cadena.replaceAll("#server#", Propiedades.get("db.server"));
					cadena = cadena.replaceAll("#puerto#", Propiedades.get("db.puerto"));
					cadena = cadena.replaceAll("#db#", Propiedades.get("db.db"));
					logger.debug("la cadena jdbc es:" + cadena);
					cadena = cadena + "?charset=cp1252"; // Cp1252 for ISO-1 (Latin-1)
					
					//Properties props = new Properties();
					//props.put("USER", Propiedades.get("db.user"));
					//props.put("PASSWORD", Propiedades.get("db.pwd"));
					//props.put("GET_BY_NAME_USES_COLUMN_LABEL", "true");
					
					unpooled = DataSources.unpooledDataSource(cadena, 
							Propiedades.get("db.user"), 
							Propiedades.get("db.pwd"));
					logger.debug("unpooled:" + unpooled);
					
                    DataSource pooled = DataSources.pooledDataSource( unpooled );
					logger.debug("pooled:" + pooled);
                    return pooled;
				} catch (ClassNotFoundException e3) {
    				logger.error("Error al inicializar el driver de JDBC: consulte al administrador");
    				logger.error(e3.getMessage());
    				e3.printStackTrace();
				} catch (SQLException e1) {
    				logger.error("Error al inicializar el datasource: consulte al administrador");
    				logger.error(e1.getMessage());
    				//e2.printStackTrace();
				}
			}
		}
		logger.debug("Ninguno");
		return null;
		/*
		DataSource dataSource = (DataSource) BeanFactory.getBean("DataSource");
		return dataSource;
		*/
	}

	/** Devuelve una referencia al pool, que es una variable estatica
	 */
	//public JCMCache getPool() {
	public DataSource getPool() {
		return pool;
	}

	/** Devuelve una referencia al pool, que es una variable estatica
	 */
	/*
	public DBConnectionPool getPool() {
		return pool;
	}
	*/

	/**
	 * Se usa cuando se quiere utilizar directamente el objeto Connection para alguna
	 * tarea no implementada en esta clase, como PreparedStatements o similares.<br>
	 * NO funciona con transacciones.
	 * 
	 * @return una Conexion nueva.
	 */
	public Connection getConnection() {
		try {
			return pool.getConnection();
		} catch (Exception e) {
			logger.error("UtilsBD: error en getConnection: " + e.getMessage());
		}
		return null;
	}

	/** 
	 * Inicia una transacci�n; internamente, abre una conexi�n a la base de datos
	 * y pone el autoCommit a false, para que todas las operaciones se ejecuten dentro
	 * de una transacci�n. 
	 */
	public boolean iniciaTran() {
		try {
			this.conexionInterna = pool.getConnection();
			this.conexionInterna.setAutoCommit(false);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			logger.error(getHora() + "UtilsBD, Error en iniciaTran: " + e.getMessage());
			// Si no puedo abrir la conexion, mando un false para 
			// que la aplicacion maneje el problema...
			return false;
		}
		return true;
	}

	/**
	 * Inicializa el modo batch, por supuesto solo si estoy en modo transaccion
	 * 
	 * @return
	 */
	public boolean iniciaBatch() {
		ultimaOperacionOK = false;
		if (conexionInterna == null) 
			return false;
		try {
			stmtInterno = conexionInterna.createStatement();
		} catch (Exception e) {
			logger.error(getHora() + "UtilsBD, Error en iniciaBatch: " + e.getMessage());
			return false;
		}
		ultimaOperacionOK = true;
		return true;
	}

	/**
	 * Agrega una sentencia al batch del statement interno, solamente debe utilizarse
	 * si antes ya se inicializo el batch y si estoy usando transacciones.
	 * @param query
	 * @return
	 */
	public boolean addBatch(String query) {
		ultimaOperacionOK = false;
		if (stmtInterno == null) 
			return false;
		try {
			stmtInterno.addBatch(query);
		} catch (Exception e) {
			logger.error(getHora() + "UtilsBD, Error en addBatch: " + e.getMessage());
			return false;
		}
		ultimaOperacionOK = true;
		return true;
	}

	public String ejecutaBatch() {
		ultimaOperacionOK = false;
		if (stmtInterno == null) 
			return "Opci&oacute;n inv&aacute;lida: no se ha inicializado el batch";
		try {
			stmtInterno.executeBatch();
		} catch (BatchUpdateException e) {
			int[] updates = e.getUpdateCounts();
			for (int i=0; i<updates.length; i++)
				logger.debug("Update " + i + " , " + updates[i]);
			logger.debug(getHora() + "UtilsBD, Error en addBatch (bue): " + e.getMessage());
			return e.getMessage();
		} catch (SQLException e) {
			logger.error(getHora() + "UtilsBD, Error en addBatch: " + e.getLocalizedMessage());
			SQLException siguiente = e.getNextException();
			while (siguiente != null) {
				logger.error("error -->" + siguiente.getLocalizedMessage());
				siguiente = siguiente.getNextException();
			}
			return e.getMessage();
		}
		ultimaOperacionOK = true;
		return null;
	}

	/** 
	 * Ejecuta el commit a la conexion y la cierra para que el programador
	 * no tenga que ejecutar siempre el devuelveConexion(); si quiere reutilizar
	 * el objeto UtilsBD, estar� us�ndolo en modo no-transacci�n, a menos por 
	 * supuesto que haga un iniciaTran().
	 * 
	 * @return true si no hubo ningun problema al hacer el commit, false
	 * 		si hubo problemas o si no estoy en modo de transaccion
	 */
	public boolean commit() {
		if (this.conexionInterna == null)
			return false;
		try {
			this.conexionInterna.commit();
		} catch (Exception e) {
			logger.error(getHora() + "Excepcion al hacer el commit");
			return false;
		} finally {
			// En cualquier caso, devuelvo la conexi�n al pool y la pongo 
			// a null.
			cierraConexionInterna = true;
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
		return true;
	}

	/** 
	 * Mismo caso que el commit, pero para el rollback.
	 * 
	 * @return 
	 */
	public boolean rollback() {
		if (this.conexionInterna == null)
			return false;
		try {
			this.conexionInterna.rollback();
		} catch (Exception e) {
			logger.error(getHora() + "Excepcion al hacer el rollback" + e.getMessage());
			return false;
		} finally {
			// En cualquier caso, devuelvo la conexi�n al pool y la pongo 
			// a null.
			cierraConexionInterna = true;
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
		return true;
	}

	/** 
	 * Llamado cuando el objeto es desechado del heap; si estaba en modo
	 * transaccional, entonces hace el commit() (para entender las razones de esto,
	 * leer la documentaci�n del inicio de la clase) y desecha la conexi�n.
	 * 
	 * @throws Throwable 
	 */
	protected void finalize() throws Throwable {
		if (this.conexionInterna != null) {
			this.conexionInterna.commit();
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
	}

	/** 
	 * Devuelve el estatus de la �ltima operaci�n ejecutada. 
	 * 
	 * @return 
	 */
	public boolean isOK() {
		return ultimaOperacionOK;
	}

	/** Convierte un rengl�n (s�lo uno) de un ResultSet en un HashMap. �til
	 * para cuando hay que guardar los datos de un resultado en un Collection
	 * de alg�n tipo. Para que todo el ResultSet quede en un Collection, llamar
	 * a RSToHashMap.<br>Antes de agregar la columna al mapa, le hago un toLowerCase()
	 * para evitar problemas.
	 * @param rs ResultSet con el rengl�n correcto (no le hago next() desde aqu�)
	 * @return Mapa con los nombres de las columnas asociados con sus valores.
	 */
	public static HashMap RSRowToHashMap (ResultSet rs) throws SQLException {
		LinkedHashMap mapa = new LinkedHashMap();
		ResultSetMetaData meta = rs.getMetaData();
		int numCols = meta.getColumnCount();
		for (int i=1; i<=numCols; i++) {
			String nombre = meta.getColumnLabel( i ).toLowerCase();
			if (rs.getString(nombre) == null)
    			mapa.put(nombre, "");
			else
    			mapa.put(nombre, rs.getString(nombre));
		}
		return mapa;
	}

	/** 
	 * Convierte un ResultSet a una lista, agregando un mapa por cada rengl�n del
	 * resultado. 
	 * 
	 * @param rs 
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList RSToArrayList (ResultSet rs) {
		ArrayList lista = new ArrayList();
		try {
			while (rs.next()) {
				HashMap mapa = RSRowToHashMap( rs );
				lista.add(mapa);
			}
		} catch (Exception e) {
			logger.error(getHora() + "Error al recorrer el rs: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			//cierraJDBC(rs, stmt, con);
		}
		return lista;
	}

	/** Alimenta todos los objetos que est�n dentro de una lista a un 
	 * preparedStatement. Esto lo hace obteniendo el tipo de cada objeto
	 * del arrayList, para alimentar el tipo correcto.
	 */
	private static void alimentaParams( PreparedStatement stmt, ArrayList lista) {
		try {
			for (int i=0; i<lista.size(); i++) {
				Object elem = lista.get(i);
				if (elem instanceof String)
					stmt.setString(i+1,(String)elem);
				else if (elem instanceof Integer)
					stmt.setInt(i+1,((Integer)elem).intValue());
				else if (elem instanceof java.sql.Date)
					stmt.setDate(i+1,(java.sql.Date)elem);
				else
					logger.debug(getHora() + "Error: elemento de tipo desconocido: " + elem.getClass().getName());

			}
		} catch (Exception e) {
			logger.error(getHora() + "Error: el n�mero de par�metros en la lista no coincide con " +
					" el statement: " + lista);
		}
	}

	/** Devuelve la hora, formateada para el log de errores. 
	 */
	protected static String getHora() {
		Calendar fecha = Calendar.getInstance();
		return fecha.get(Calendar.MONTH) + "/" +
				 fecha.get(Calendar.DAY_OF_MONTH) + "/" +
				 fecha.get(Calendar.YEAR) + " " +
				 fecha.get(Calendar.HOUR_OF_DAY) + ":" +
				 fecha.get(Calendar.MINUTE) + ":" +
				 fecha.get(Calendar.SECOND) + ":" +
				 fecha.get(Calendar.MILLISECOND) + ". ";
	}

	/** 
	 * Cierra la conexi�n y hace todas las operaciones de limpieza (cierra el
	 * resultset, y cierra el statement y conexi�n). 
	 * 
	 * @param rs 
	 * @param stmt 
	 * @param con 
	 */
	public void cierraJDBC(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null) 
				rs.close();
		} catch (Exception e) {
			logger.error(getHora() + "Error en cierraJDBC: " + e.getMessage());
		} finally {
			rs = null;
			cierraJDBC(stmt, con);
		}
	}

	/** 
	 * Cierra un statement y una conexi�n. 
	 * <br>
	 * Julio 2007 DV. Le hice un cambio para que, si estoy utilizando
	 * el modo transaccional, se cierre el Statement pero no el 
	 * connection, de modo que s�lo se cierra un objeto Connection
	 * en modo transaccional si antes de llamar a cierraJDBC pongo
	 * la variable <i>cierraConexionInterna</i> a true.
	 * 
	 * @param stmt 
	 * @param con 
	 */
	public void cierraJDBC(Statement stmt, Connection con) {
		try {
			if (stmt != null) 
				stmt.close();
		} catch (Exception e) {
			logger.error(getHora() + "Error en cierraJDBC: " + e.getMessage());
		} finally {
			stmt = null;
			if (con == null)
				return;
			if (cierraConexionInterna == true || this.conexionInterna == null) {
				// Solo entro a este bloque si la conexion interna es nulo (es decir,
				// no estoy en modo transaccional) o si cierraConexionInterna es 
				// true, es decir, estoy haciendo commit o rollback.
				try {
					//pool.freeConnection(con);
					con.close(); // El close es la se�al para que el DataSource devuelva
									// la conexi�n al pool.
					cierraConexionInterna = false;
				} catch (Exception e2) {
					con = null;
					logger.error(getHora() + "Error en cierraJDBC, cerrando conexion: " + 
												e2.getMessage());
					// Probablemente no hay nada que hacer, el pool no va a poder recuperar
					// esta conexion.
				}
			} // fin del if con!= null...
		} // fin del finally.
	}
        
      /**
       * @Parameters
       *    query - Es la cadena preconstruida SQL
       * @Return
       *    result - Es el resultset que se obtiene, este debera
       *    cerrarse posteriormente mediante el metodo
       *    public void cierraRS(ResultSet rs)
       */
        
     public ResultSet ejecutaQuery(String query) throws Exception{
         Connection con = null;
         Statement stmt = null;
         ResultSet result = null;
         try {
            con = pool.getConnection();
            stmt = con.createStatement();
            result = stmt.executeQuery(query);
        } catch (Exception se) {
            logger.error(getHora() + "ejecutaQuery: Error ejecutando el query " + query +
                    "\n               Motivo: " + se.getMessage());
            throw se;
        } finally {
            //cierraJDBC(stmt, con);
        }
        return result;
    }
           
      /**
       * Este metodo se utiliza para cerrar un resultset desde una clase de servicios
       * @Parameters
       *    rs - Es el resultset a cerrarse
       */
     
    public void cierraRS(ResultSet rs){
        try{
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

	 /** 
	  * Devuelve true si estoy utilizando una conexion interna. Utilizado para los
	  * casos de prueba, en los que quiero saber si se esta abriendo una conexion
	  * interna. 
	  * 
	  * @return 
	  */
	 public boolean getHayConexionInterna() {
		 return (this.conexionInterna != null);
	 }

	/**
	 * Ejecuta un SP que retorna un RS. Devuelve un mapa con 2 elementos:
	 * "id_lote" y "error".
	 * @param query
	 * @param params
	 * @return
	 */
	public HashMap queryMapaErrorSybase(String query, List<Object> params) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		ArrayList lista = null;
		HashMap mapa = new HashMap();
		int cont = 1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.prepareCall(query);
			if(params!=null){
				for (Object param: params) {
					if(param instanceof String)
						stmt.setString(cont++, String.valueOf(param));
					else if(param instanceof Integer)
						stmt.setInt(cont++, Integer.valueOf(String.valueOf(param)));
				}				
			}
			rs = stmt.executeQuery();
			if(rs.next()){
				int error = rs.getInt(1);
				int idLote = rs.getInt(2);
				mapa.put("id_lote", String.valueOf(idLote));				
				mapa.put("error", String.valueOf(error));
				logger.error("error devuelto por queryListaSybase, 1:" + error);
			}
			ultimaOperacionOK = true;
		} catch (Exception e) {
			logger.debug("Tron� el 1er intento: " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		logger.debug("Mapa Sybase:" + mapa);
		return mapa;
	}
	
	/**
	 * Retorna un mapa con "n" elementos seg�n los par�metros de salida devueltos por el SP
	 * La "llave" para recuperar el elemento, es el n�mero de la posici�n en que fue seteado el "output"
	 * y el "valor" ser� variable seg�n como haya sido declarado en el SP. 
	 * @param query
	 * @param params
	 * @return
	 */
	public HashMap querySPMapaSybase(String query, List<Object> params) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		ArrayList lista = null;
		HashMap mapa = new HashMap();
		List outIndexes = new ArrayList();
		int cont = 1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.prepareCall(query);
			for (Object param: params) {
				if(param instanceof String){
					if(String.valueOf(param).equals(Global.OUTPUT_PARAMETER)){
						int index = cont++;
						outIndexes.add(index);
						stmt.registerOutParameter(index, Types.INTEGER );
					}
					else
						stmt.setString(cont++, String.valueOf(param));
				}
				else if(param instanceof Integer){
					stmt.setInt(cont++, Integer.valueOf(String.valueOf(param)));
				}
				else if(param instanceof Double){
					stmt.setDouble(cont++, Double.valueOf(String.valueOf(param)));
				}
				else if(param instanceof Date){
					stmt.setDate(cont++, new java.sql.Date(((Date) param).getTime()));
				}
			}
			stmt.execute();
			for(Object outIndex: outIndexes){
				String key = String.valueOf(outIndex);
				int value = stmt.getInt(Integer.valueOf(key));
				mapa.put(key, value);
			}
			ultimaOperacionOK = true;
		} catch (Exception e) {
			logger.debug("Tron� el 1er intento: " + e.getLocalizedMessage());
			e.printStackTrace();
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		logger.debug("Mapa Sybase:" + mapa);
		return mapa;
	}
	
	public ArrayList querySingleListSybase( String query ) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		ArrayList lista = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			
			if(!query.toUpperCase().contains("SELECT"))
				query = "{ call " + query + " )}";			
			stmt = con.prepareCall(query);
			rs = stmt.executeQuery();
			lista = RSToArrayList(rs);
			ultimaOperacionOK = true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ultimaOperacionOK = false;
		} 
		catch (Exception e) {
				logger.error("Excepcion desde el 2do nivel");
    			e.printStackTrace();
    			ultimaOperacionOK = false;
		}
		finally {
			cierraJDBC(rs, stmt, con);
		}
		return lista;
	}
	
	public ArrayList queryMultipleListSybase( String query ) {		
		ArrayList listas = new ArrayList();
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			if(!query.toUpperCase().contains("SELECT"))
				query = "{ call " + query + " )}";
			stmt = con.prepareCall(query);
			boolean rsAvailable = stmt.execute();
			while(true){
			    if (rsAvailable) {
			        rs = stmt.getResultSet();
			        List lista = RSToArrayList(rs);
			        if(lista!=null && !lista.isEmpty())
			        	listas.add(lista);
			    } else {
			        int updateCount = stmt.getUpdateCount();
			        if (updateCount==-1) {
			            break;
			        }
			    }
			    rsAvailable = stmt.getMoreResults();
			}			
		} 
		catch (SQLException e) {
			e.printStackTrace();
			ultimaOperacionOK = false;
		} 
		catch (Exception e) {
				logger.error("Excepcion desde el 2do nivel");
    			e.printStackTrace();
    			ultimaOperacionOK = false;
		}
		finally {
			cierraJDBC(rs, stmt, con);
		}		
		return listas;
	}
	
	/** 
	 * Ejecuta un query, y devuelve un Resultset. Este m�todo no utiliza toda
	 * la infraestructura de devolver Collections, de modo que hay que utilizarlo
	 * con cuidado porque no devuelve la conexi�n al pool ni est� preparado
	 * para utilizar transacciones intr�nsecamente.
	 * 
	 * @param query 
	 * @param stmt 
	 * @param rs 
	 * @return 
	 */
	public ResultSet query( String query, Statement stmt, ResultSet rs) {
		try {
			rs = stmt.executeQuery( query );
		} catch (Exception e) {
			logger.error(getHora() + "query: Error ejecutando el query " + query +
						                    "\n              Motivo: " + e.getMessage());
		}
		return rs;
	}
	
	/** 
	 * Devuelve un mapa, para un query que devuelve solo un
	 * renglon. <br>Es importante mencionar que dentro del mapa
	 * cada valor queda ligado al nombre de la columna, convertido
	 * a minusculas. 
	 * 
	 * @param query 
	 * @return 
	 */
	public HashMap<String, String> queryMapa( String query ) {
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		ArrayList<HashMap<String, String>> lista = null;
		HashMap<String, String> mapa = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			rs = query( query, stmt, rs);
			lista = RSToArrayList(rs);
			if (lista != null && lista.size() > 0) {
    			mapa = (HashMap<String,String>)lista.get(0);
    			ultimaOperacionOK = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return mapa;
	}
	
	/** 
	 * Devuelve un mapa, para un SP que devuelve solo un
	 * renglon. <br>Es importante mencionar que dentro del mapa
	 * cada valor queda ligado al nombre de la columna, convertido
	 * a minusculas. 
	 * 
	 * @param query 
	 * @return 
	 */
	public HashMap<String, String> spMapRecord( String query ) {
		
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		ArrayList<HashMap<String, String>> lista = null;
		HashMap<String, String> mapa = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			query = "{ call " + query + " )}";
			stmt = con.prepareCall(query);
			rs = stmt.executeQuery();
			lista = RSToArrayList(rs);
			if (lista != null && lista.size() > 0) {
    			mapa = (HashMap<String,String>)lista.get(0);
    			ultimaOperacionOK = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return mapa;
	}
	
	/** 
	 * Ejecuta un update o insert, a partir de una cadena que es el query. 
	 * 
	 * @param query Query a ejecutar
	 * @return Un -1 si algo est� mal
	 */
	public int update( String query ) {
		Connection con = null;
		Statement stmt = null;
		int result = -1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			result = stmt.executeUpdate( query );
			ultimaOperacionOK = true;
		} catch (Exception e) {
			logger.error(getHora() + "update: Error ejecutando el update " + query +
												  "\n               Motivo: " + e.getMessage());
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(stmt, con);
		}
		return result;
	}
	
	/** Simplifica obtener un s�lo dato de un query (String); devuelve <i>null</i> 
	 * si ocurre error en el query.
	 * @param query Cadena con el query a ejecutar
	 * @return El valor como String de la 1a columna del 1er rengl�n del resultado. null si ocurre un error al ejecutar el query.
	 */
	public String queryString( String query ) {
		Connection con = null;
		String result = null;
		Statement stmt = null;
		ResultSet rs   = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			rs = query( query, stmt, rs);
			rs.next();
			result = rs.getString(1);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return result;
	}
	
	public int queryIntSybase(String query) {
		Connection con = null;
		CallableStatement stmt = null;
		ResultSet rs   = null;
		int result = -1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.prepareCall("{call " + query + ")}");
			rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			e.printStackTrace();
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return result;
	}
	
}

