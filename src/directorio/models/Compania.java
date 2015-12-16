package directorio.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import directorio.utils.UtilsBD;
import directorio.utils.UtilsFechas;

public class Compania extends Entidad implements Serializable {
	public static Logger log = Logger.getLogger(Compania.class.getName());
	private String razonSocial;	
	private String rutaLogo;
	//private List<Oficina> listaOficina;
	private Date fechaFundacion;
	private String paginaWeb;
	private String afiliadaAmis;
	private String accionesInmobiliariaAmis;
	private String certificadoAportacionPatrimonial;
	private CapitalOrigen capitalOrigen;
	private String grupoFinaciero;
	private List<Ramo> listaRamo;
	private TipoMercado tipoMercado;
	private CanalVenta canalVenta;
	private CapitalSocial capitalSocial;
	private String estado;
	private Date fechaAlta;
	
	public Compania(){
		
	}
	
	public Compania(int id, String nombre) {		
		this.id = id;
		this.nombre = nombre;
		this.padre = null;
	}
    
	public Compania(int idCia, String razonSocial, String nombreCorto,
			String rutaLogo, //List<Oficina> listaOficina, 
			Date fechaFundacion, String paginaWeb,
			String afiliadaAmis, String accionesInmobiliariaAmis,
			String certificadoAportacionPatrimonial,
			CapitalOrigen capitalOrigen, String grupoFinaciero, List<Ramo> listaRamo,
			TipoMercado tipoMercado, CanalVenta canalVenta,
			CapitalSocial capitalSocial, String estado, Date fechaAlta) {
		super();
		this.id = idCia;
		this.razonSocial = razonSocial;		
		this.nombre = nombreCorto;		
		this.rutaLogo = rutaLogo;
		//this.listaOficina = listaOficina;
		this.fechaFundacion = fechaFundacion;		
		this.paginaWeb = paginaWeb;		
		this.afiliadaAmis = afiliadaAmis;		
		this.accionesInmobiliariaAmis = accionesInmobiliariaAmis;	
		this.certificadoAportacionPatrimonial = certificadoAportacionPatrimonial;		
		this.capitalOrigen = capitalOrigen;
		this.grupoFinaciero = grupoFinaciero;
		this.listaRamo = listaRamo;		
		this.tipoMercado = tipoMercado;		
		this.canalVenta = canalVenta;		
		this.capitalSocial = capitalSocial;		
		this.estado = estado;
		this.fechaAlta = fechaAlta;
	}

	public Compania(int idCia, String razonSocial, String nombreCorto
			/*char estado*/){
		super();
		this.id = idCia;
		this.razonSocial = razonSocial;
		this.nombre = nombreCorto;
		//this.estado = estado;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRutaLogo() {
		return rutaLogo;
	}

	public void setRutaLogo(String rutaLogo) {
		this.rutaLogo = rutaLogo;
	}

	/*public List<Oficina> getListaOficina() {
		return listaOficina;
	}

	public void setListaOficina(List<Oficina> listaOficina) {
		this.listaOficina = listaOficina;
	} */

	public Date getFechaFundacion() {
		return fechaFundacion;
	}

	public void setFechaFundacion(Date fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getAfiliadaAmis() {
		return afiliadaAmis;
	}

	public void setAfiliadaAmis(String afiliadaAmis) {
		this.afiliadaAmis = afiliadaAmis;
	}

	public String getAccionesInmobiliariaAmis() {
		return accionesInmobiliariaAmis;
	}

	public void setAccionesInmobiliariaAmis(String accionesInmobiliariaAmis) {
		this.accionesInmobiliariaAmis = accionesInmobiliariaAmis;
	}

	public String getCertificadoAportacionPatrimonial() {
		return certificadoAportacionPatrimonial;
	}

	public void setCertificadoAportacionPatrimonial(
			String certificadoAportacionPatrimonial) {
		this.certificadoAportacionPatrimonial = certificadoAportacionPatrimonial;
	}

	public CapitalOrigen getCapitalOrigen() {
		return capitalOrigen;
	}

	public void setCapitalOrigen(CapitalOrigen capitalOrigen) {
		this.capitalOrigen = capitalOrigen;
	}

	public String getGrupoFinaciero() {
		return grupoFinaciero;
	}

	public void setGrupoFinaciero(String grupoFinaciero) {
		this.grupoFinaciero = grupoFinaciero;
	}

	public List<Ramo> getListaRamo() {
		return listaRamo;
	}

	public void setListaRamo(List<Ramo> listaRamo) {
		this.listaRamo = listaRamo;
	}

	public TipoMercado getTipoMercado() {
		return tipoMercado;
	}

	public void setTipoMercado(TipoMercado tipoMercado) {
		this.tipoMercado = tipoMercado;
	}

	public CanalVenta getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
	}

	public CapitalSocial getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(CapitalSocial capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public static Compania loadFromId(int idCompania){
		String sql = "SELECT * " +
						"FROM COMPANIA " + "WHERE ID_CIA = " + idCompania;
		HashMap mapa = new UtilsBD().queryMapa(sql);
		if (mapa == null)
			return null;
		return loadFromMapa(mapa);
	}
	
public static Compania loadFromMapa(HashMap mapa){    
	return new Compania(Integer.parseInt((String)mapa.get("id_cia")), 
					   (String)mapa.get("razon_social"), 
					   (String)mapa.get("nombre_corto"),
					   (String)mapa.get("ruta_logo"),
					    //Oficina.getOficinas(Integer.parseInt((String)mapa.get("id_cia"))),
					    UtilsFechas.parseFechaUsuario((String)mapa.get("fecha_fundacion")), 
					   (String)mapa.get("pagina_web"),
					   (String)mapa.get("afiliada_amis"),
					   (String)mapa.get("acciones_inmobiliarias"), 
					   (String)mapa.get("certificado_aportacion_patrimo"),
					    CapitalOrigen.loadFromId((Integer.parseInt((String)mapa.get("id_capital_origen")))),
					   (String)mapa.get("grupo_financiero"),
						Ramo.getRamos(Integer.parseInt((String)mapa.get("id_cia"))),
						TipoMercado.loadFromId(Integer.parseInt((String)mapa.get("id_tipo_mercado"))),
						CanalVenta.loadFromId(Integer.parseInt((String)mapa.get("id_canal_ventas"))), 
						CapitalSocial.loadFromId(Integer.parseInt((String)mapa.get("id_capital_social"))),
						(String)mapa.get("estado"),
						UtilsFechas.parseFechaUsuario((String)mapa.get("fecha_alta")));
}
	
	public static int obtenUltimoId(){
		String sql = "SELECT MAX(ID_CIA)+1 AS ID FROM COMPANIA";
		HashMap i = (HashMap)new UtilsBD().querySingleListSybase(sql).get(0);
		return Integer.parseInt((String)i.get("id"));
	}
	
	public static int creaCompania(String sql){
		int res;		
		res = new UtilsBD().update(sql);
		return res;
	}
	public static int obtenUltimoIdHistorico(){
		String sql = "SELECT MAX(ID_HISTORIAL)+1 AS ID FROM COMPANIA_HISTORICA";
		HashMap i = new UtilsBD().queryMapa(sql);
		
		if((String)i.get("id") == "")
			return 1;
		else
			return Integer.parseInt((String)i.get("id"));
	}
	
	public static String getNombreCiaFromId(int idCia){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT NOMBRE_CORTO FROM COMPANIA WHERE ID_CIA = ").append(idCia);
		HashMap mapa = new UtilsBD().queryMapa(sql.toString());
		return (String)mapa.get("nombre_corto");
	}
	
	public int insertaRamos(boolean autos, boolean vida, boolean aye, boolean  danos, 
			boolean  reaseg, boolean pension, boolean salud, int idCompania){
		
		int res;
		List<Boolean> listaNiveles = new ArrayList<Boolean>();
		listaNiveles.add(autos);
		listaNiveles.add(vida);
		listaNiveles.add(aye);
		listaNiveles.add(danos);
		listaNiveles.add(reaseg);
		listaNiveles.add(pension);
		listaNiveles.add(salud);
		
		StringBuilder sql = new StringBuilder();				
		
		for(int i=0; i<listaNiveles.size(); i++){
			if(listaNiveles.get(i) != false)
				sql.append("INSERT INTO RAMO_COMPANIA VALUES (").append(i+1).append(", ").append(idCompania).append(")\n");
			else
				sql.append("");
		}
		log.debug("Antes de insertar los niveles de la persona " + idCompania + "sql: " + sql.toString());
		res = new UtilsBD().update(sql.toString());
		return res;
	}
	
	public List getListaRamos(int idCompania){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT RC.ID_RAMO AS idRamo, DESCRIPCION AS NOMBRE FROM RAMO_COMPANIA RC INNER JOIN CAT_RAMO CR ON RC.ID_RAMO = CR.ID_RAMO WHERE ID_CIA = ")
		.append(idCompania);
		try{
			List listaMapasRamos = (List)new UtilsBD().queryMultipleListSybase(sql.toString()).get(0);
			return (List)listaMapasRamos;
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	public int insertaRamosHistorico(int idCompania, int idHistorico){		
		int res;
		StringBuilder sql = new StringBuilder();
		List listaRamos = getListaRamos(idCompania);
		HashMap mapa;
		log.debug(listaRamos);
		if(listaRamos != null){
			for(int i=0; i<listaRamos.size(); i++){
				mapa = (HashMap)listaRamos.get(i);
				sql.append("INSERT INTO RAMO_COMPANIA_HISTORICA VALUES (").append((String)mapa.get("idramo")).append(", ").append(idCompania)
					.append(", ").append(idHistorico).append(")\n");
			}
			log.debug("Insertando ramos en RAMO_COMPANIA_HISTORICA" + sql.toString());
			res = new UtilsBD().update(sql.toString());
			if(res != -1){
				if(borraRamosCias(idCompania) != -1)
					return 0;
				else
					log.debug("No se pudieron borrar los ramos de la compañia");
					return -1;
			}else{
				log.debug("No se pudo insertar los ramos de la compañia en el historico");
				return -1;
			}
		}		
		else
			log.debug("No se realizo la inserción en RAMO_COMPANIA_HISTORICA, la lista viene null, idCompania = " + idCompania);
			return 0;
	}
	
	public int borraRamosCias(int idCompania){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM RAMO_COMPANIA WHERE ID_CIA = ").append(idCompania);
		int res = new UtilsBD().update(sql.toString());
		return res;
	}
}

