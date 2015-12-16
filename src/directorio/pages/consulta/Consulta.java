package directorio.pages.consulta;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import directorio.models.Compania;
import directorio.models.Persona;
import directorio.models.Ramo;
import directorio.pages.OcraBasePage;
import directorio.pages.admindirectorio.MuestraCompania;
import directorio.pages.admindirectorio.MuestraPersona;
import directorio.services.ExcelGenerator;
import directorio.services.PdfGenerator;
import directorio.utils.PDFAttachment;
import directorio.utils.UtilsBD;
import directorio.utils.XLSAttachment;

//@IncludeStylesheet({"Menu.css"})
//@IncludeJavaScriptLibrary({"Consulta.js"})
public class Consulta extends OcraBasePage{
	
	@Property
	private String errorTmp;
	@Property
	private String titulo;
	@Property
	private String nombre;
	@Property
	private String paterno;
	@Property
	private String materno;
	@Property
	private String area;
	@Property
	private String puesto;
	@Property
	private String cia;
	@Property
	private String estado;
	@Property
	private Persona persona;
	@InjectPage
	private MuestraPersona muestraPersona;
	
	@Property
	private String tablaJsonP;
	@Property
	private String tablaJsonC;
	
	//Para las Compañias
	@Property
	private String razonSocial;
	@Property
	private String nombreCorto;
	@Property
	private String ramo;
	@Property
	private String tipoMercado;
	@Property
	private String canalVentas;
	@Property
	private String capitalSocial;
	@Property
	private String estadoCia;
	@Property
	private Compania compania;
	@Property
	private Ramo muestraRamo;		
	@InjectPage
	private MuestraCompania muestraCompania;
	@Property
	@Persist
	private List listaPersona;	
	@InjectPage
	private Consulta consulta;
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String ruta;
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String nombreArchivo;
	
	
	
	public List<Persona> getPersonas(){
		String sql = "SELECT * FROM PERSONA";
		
		List listaMapasPersona = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
		
		List listaMapaPersona = (List)listaMapasPersona;
		
		List<Persona> listaPersona = new ArrayList<Persona>();
		
		for(int i=0; i<listaMapaPersona.size(); i++){
			HashMap mapaPersona = (HashMap)listaMapaPersona.get(i);
			listaPersona.add(Persona.loadFromMapa(mapaPersona));
		}
		this.listaPersona = listaPersona;
		return listaPersona;
	}
	
	Object onActionFromModifica(int personaIdPersona){		
		log.debug("usuarioId: " + personaIdPersona);
		muestraPersona.muestraPersona(personaIdPersona);
		return muestraPersona;
	}
	
	public List<Compania> getCompanias(){
		String sql = "SELECT * FROM COMPANIA";
		
		List listaMapasCompania = (List)new UtilsBD().queryMultipleListSybase(sql).get(0);
						
		List listaMapaCompania = (List)listaMapasCompania;

		List<Compania> listaCompania = new ArrayList<Compania>();
						
		for(int i=0; i<listaMapaCompania.size(); i++){
			HashMap mapaCompania = (HashMap)listaMapaCompania.get(i);
			listaCompania.add(Compania.loadFromMapa(mapaCompania));
		}
		return listaCompania;		
	}
	
	void onSelectedFromImprimeXlsP(){
		log.debug("Imprimiendo tabla JSON: " + tablaJsonP);
		String arregloId[] = tablaJsonP.split(",");
		List<Persona> listaFiltrada = getListaFiltrada(arregloId);		
		log.debug("Generando Excel");
		String nombre = "personas_" + getNumeroArchivo() + ".xls";
		String ruta = getRutaTemporal() + nombre;
		this.ruta = ruta; this.nombreArchivo = nombre;
		try{
			ExcelGenerator.generaExcelP(ruta, listaFiltrada);
		}catch(Exception e){
			log.debug("Hubo un error al generar el excel Personas");
			e.printStackTrace();
		}
		
	}
	void onSelectedFromImprimePdfP(){
		log.debug("Imprimiendo tabla JSON: " + tablaJsonP);
		String arregloId[] = tablaJsonP.split(",");
		List<Persona> listaFiltrada = getListaFiltrada(arregloId);		
		log.debug("Generando PDF");
		String nombre = "personas_" + getNumeroArchivo() + ".pdf";
		String ruta = getRutaTemporal() + nombre;
		this.ruta = ruta; this.nombreArchivo = nombre;
		try{
			PdfGenerator.generaPdfP(ruta, listaFiltrada);
		}catch(Exception e){
			log.debug("Hubo un error al generar el pdf Personas ");
			e.printStackTrace();
		}
		
	}
	void onSelectedFromImprimeXlsC(){
		log.debug("Imprimiendo tabla JSON: " + tablaJsonC);
		String arregloId[] = tablaJsonC.split(",");
		List<Compania> listaFiltrada = getListaFiltradaC(arregloId);		
		log.debug("Generando Excel");
		String nombre = "companias_" + getNumeroArchivo() + ".xls";
		String ruta = getRutaTemporal() + nombre;
		this.ruta = ruta; this.nombreArchivo = nombre;
		try{
			ExcelGenerator.generaExcelC(ruta, listaFiltrada);
		}catch(Exception e){
			log.debug("Hubo un error al generar el excel Compañias");
			e.printStackTrace();
		}
		
	}
	void onSelectedFromImprimePdfC(){
		log.debug("Imprimiendo tabla JSON: " + tablaJsonC);
		String arregloId[] = tablaJsonC.split(",");
		List<Compania> listaFiltrada = getListaFiltradaC(arregloId);		
		log.debug("Generando PDF");
		String nombre = "companias_" + getNumeroArchivo() + ".pdf";
		String ruta = getRutaTemporal() + nombre;
		this.ruta = ruta; this.nombreArchivo = nombre;
		try{
			PdfGenerator.generaPdfC(ruta, listaFiltrada);
		}catch(Exception e){
			log.debug("Hubo un error al generar el pdf Compañias ");
			e.printStackTrace();
		}
		
	}
	Object onActionFromModificaCia(int companiaIdCia){
		muestraCompania.muestraCompania(companiaIdCia);
		return muestraCompania;
	}
	private List<Persona> getListaFiltrada(String[] arregloId){		
		List<Persona> listaFiltrada = new ArrayList<Persona>();
		Persona persona;
		for(int i=0; i<arregloId.length; i++){
			persona = (Persona)listaPersona.get(Integer.parseInt(arregloId[i])-1);			
			listaFiltrada.add(persona);
		}
		return listaFiltrada;
	}
	
	private List<Compania> getListaFiltradaC(String[] arregloId){
		List<Compania> listaFiltrada = new ArrayList<Compania>();		
		for(int i=0; i<arregloId.length; i++){			
			listaFiltrada.add(Compania.loadFromId(Integer.parseInt(arregloId[i])));
		}
		log.debug("Lista filtrada de Companias: " + listaFiltrada );
		return listaFiltrada;
	}
	
	public StreamResponse onSuccess(){
		InputStream input = null;
		try {
			log.debug("Buscando archivo en: " + this.ruta);
			input = new FileInputStream(this.ruta);
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		if(this.nombreArchivo.contains(".xls"))
			return new XLSAttachment(input, this.nombreArchivo);
		else
			return new PDFAttachment(input, this.nombreArchivo);
	}
	
	public boolean getPermisoImp(String rol){
		if(getUsuarioSesion().getRol().getNombre().contains(rol))
			return false;
		else
			return true;
	}
	
	private int getNumeroArchivo(){
		Random ran = new Random();
		return (int)(ran.nextDouble()*100);
	}
}