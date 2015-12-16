package directorio.pages.admindirectorio;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import directorio.annotations.InjectSelectionModel;
import directorio.models.CanalVenta;
import directorio.models.CapitalOrigen;
import directorio.models.CapitalSocial;
import directorio.models.Compania;
import directorio.models.Entidad;
import directorio.models.Global;
import directorio.models.TipoMercado;
import directorio.pages.OcraBasePage;
import directorio.utils.UtilsFechas;

public class AdminCompania extends OcraBasePage {
	
	@Property
	private String titulo;
	@Property
	private String estatus;
	@Property
	private String errorTmp;
	@Property
	private String razon;
	@Property
	private String nombrec;
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCapitalO = Global.getCapitalOrigen();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaTipoM = Global.getTipoMercado();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCanalV = Global.getCanalVenta();
	@InjectSelectionModel(labelField="nombre", idField="id")
	private List<Entidad> listaCapitalS = Global.getCapitalSocial();
	@Property
	private Date fechaf;
	@Property
	private String web;
	@Property
	private String afiliada;
	@Property
	private String acciones;
	@Property
	private String certificado;
	@Property
	private String grupof;
	@Property
	private TipoMercado tipom;
	@Property
	private CanalVenta canal;
	@Property
	private CapitalSocial capitals;
	@Property
	private String buscacompania;
	@Property
	private CapitalOrigen capitalo;
	@Property
	private String estado;
	
	//Subiendo Logo
	@Property 
	private UploadedFile logo;
	
	//Ramos
	@Property
	private Compania cia;
	@Property
	private boolean autos;
	@Property
	private boolean vida;
	@Property
	private boolean aye;
	@Property
	private boolean danos;
	@Property
	private boolean reaseg;
	@Property
	private boolean pension;
	@Property
	private boolean salud;
	
	void onSubmitFromFormDatosIniciales(){
		String fechaActual = UtilsFechas.formatoMesLetraSybase(new Date());
		int idCompania = Compania.obtenUltimoId();
		StringBuilder logourl = new StringBuilder();
		//logourl.append(getRutaLogo()).append("/");
		logourl.append(getRutaLogo());
		
		File dir = new File(getRutaLogo() + "compania_" + idCompania);
		if(dir.exists()){
			log.debug("El directorio " + nombrec + " ya existe");
		}else{
			log.debug("No existe el directorio " + nombrec + " creandolo");
			try{
				dir.mkdir();
			}catch(SecurityException e){
				log.debug("Hubo un problema para crear el directorio");
			}			
		}
		
		File copied = new File(dir + "/" + logo.getFileName());		
		log.debug("Se copiara en: " + copied);
		logo.write(copied);
		log.debug("Se subio correctamente el logo");
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO COMPANIA VALUES (").append(idCompania).append(", '").append(razon).append("', '").append(nombrec).append("', '")
		   .append(copied.toString()).append("', '").append(UtilsFechas.formatoMesLetraSybase(fechaf)).append("', '").append(web).append("', ").append(afiliada)
		   .append(", ").append(acciones).append(", ").append(certificado).append(", ").append(capitalo.id).append(", ").append(1).append(", ")
		   .append(tipom.id).append(", ").append(canal.id).append(", ").append(capitals.id).append(", '").append(estado).append("', '").append(fechaActual)
		   .append("')");
		
		log.debug("#####SQL AdminCompania: " + sql);
		Compania.creaCompania(sql.toString());
		cia = new Compania(idCompania, nombrec);
		cia.insertaRamos(autos, vida, aye, danos, reaseg, pension, salud, idCompania);
				
	}
	
	public boolean getEstatusPagina(String cadena){
		if (estatus == null)
			return true;
		return false;
	}
	
	private String isNull(String campo){
		if (campo != null)
			return "'" + campo + "', ";
		else
			return "'', ";
	}
	
	private String isNullLast(String campo){
		if (campo != null)
			return "'" + campo + "') ";
		else
			return "'') ";
	}
}