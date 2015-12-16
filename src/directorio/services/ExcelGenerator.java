package directorio.services;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import directorio.models.Compania;
import directorio.models.Persona;
import directorio.pages.admindirectorio.AdminUsuario;

/**
 * Crea un archivo excel utilizando POI a partir de una lista
 * @author abaez
 *
 */
public class ExcelGenerator {
	public static Logger log = Logger.getLogger(ExcelGenerator.class.getName());
	public static void generaExcelP(String nombreArchivo, List lista) throws Exception{		
		log.debug("Se pondra el archivo en " + nombreArchivo);
		Workbook workbook = null;
		
		if(nombreArchivo.endsWith("xlsx")){
			workbook = new XSSFWorkbook();			
		}else if (nombreArchivo.endsWith("xls")){
			workbook = new HSSFWorkbook();
		}else{
			throw new Exception("Nombre invalido, debe de terminar con .xls o .xlsx");
		}
		
		Sheet sheet = workbook.createSheet("Personas");
		Iterator iterator = lista.iterator();
		
		int rowIndex = 0;
		
		Row titleRow = sheet.createRow(rowIndex++);
		titleRow.createCell(0).setCellValue("Título");
		titleRow.createCell(1).setCellValue("Nombre Completo");
		titleRow.createCell(2).setCellValue("Compañía");
		titleRow.createCell(3).setCellValue("Oficina");
		titleRow.createCell(4).setCellValue("Tipo de Persona");
		titleRow.createCell(5).setCellValue("Nivel de Persona");
		titleRow.createCell(6).setCellValue("Área");			
		titleRow.createCell(7).setCellValue("Puesto");
		titleRow.createCell(8).setCellValue("Teléfono Compañía");
		titleRow.createCell(9).setCellValue("Teléfono Directo");
		titleRow.createCell(10).setCellValue("Teléfono Casa");
		titleRow.createCell(11).setCellValue("Celular");
		titleRow.createCell(12).setCellValue("Facebook");
		titleRow.createCell(13).setCellValue("WhatsApp");
		titleRow.createCell(14).setCellValue("Twitter");
		titleRow.createCell(15).setCellValue("Email Empresa");
		titleRow.createCell(16).setCellValue("Email Personal");
		titleRow.createCell(17).setCellValue("Estado");
		titleRow.createCell(18).setCellValue("Fecha Alta");
		titleRow.createCell(19).setCellValue("Observaciones");
		
		while(iterator.hasNext()){
			Persona persona = (Persona)iterator.next();
			log.debug(persona.toString());
			Row row = sheet.createRow(rowIndex++);							 
			row.createCell(0).setCellValue(persona.getTitulo() != null ? persona.getTitulo():"N/D");
			row.createCell(1).setCellValue(persona.getNombreCompleto());
			row.createCell(2).setCellValue(persona.getCia() != null?persona.getCia().getNombre():"N/D");
			row.createCell(3).setCellValue(persona.getOficina() != null?persona.getOficina().getNombre():"N/D");
			row.createCell(4).setCellValue(persona.getTipoPersona() != null ? persona.getTipoPersona().getNombre() : "N/D");
			row.createCell(5).setCellValue(persona.getNivelPersona() != null ? persona.getNivelPersona().toString():"N/D");
			row.createCell(6).setCellValue(persona.getArea() != null ? persona.getArea().getNombre():"N/D");			
			row.createCell(7).setCellValue(persona.getPuesto() != null ? persona.getPuesto(): "N/D");
			row.createCell(8).setCellValue(persona.getTelefonoCia() != null ? persona.getTelefonoCia():"N/D");
			row.createCell(9).setCellValue(persona.getTelefonoDirecto() != null ? persona.getTelefonoDirecto():"N/D");
			row.createCell(10).setCellValue(persona.getTelefonoCasa() != null ? persona.getTelefonoCasa():"N/D");
			row.createCell(11).setCellValue(persona.getCel() != null ? persona.getCel():"N/D");
			row.createCell(12).setCellValue(persona.getFacebook() != null ? persona.getFacebook():"N/D");
			row.createCell(13).setCellValue(persona.getWhatsapp() != null ? persona.getWhatsapp():"N/D");
			row.createCell(14).setCellValue(persona.getTwitter() != null ? persona.getTwitter():"N/D");
			row.createCell(15).setCellValue(persona.getEmailEmpresa() != null ? persona.getEmailEmpresa():"N/D");
			row.createCell(16).setCellValue(persona.getEmailPersonal() != null ? persona.getEmailPersonal():"N/D");
			row.createCell(17).setCellValue(persona.getEstatus());
			row.createCell(18).setCellValue(persona.getFechaAlta() != null ? persona.getFechaAlta().toString(): "N/D");
			row.createCell(19).setCellValue(persona.getObservaciones() != null ? persona.getObservaciones():"N/D");			
		}
		
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		workbook.write(fos);
		fos.close();
		log.debug("Se genero el archivo XLS");
	}
	public static void generaExcelC(String nombreArchivo, List lista) throws Exception{
		log.debug("Se pondra el archivo en " + nombreArchivo);
		Workbook workbook = null;
		
		if(nombreArchivo.endsWith("xlsx")){
			workbook = new XSSFWorkbook();			
		}else if (nombreArchivo.endsWith("xls")){
			workbook = new HSSFWorkbook();
		}else{
			throw new Exception("Nombre invalido, debe de terminar con .xls o .xlsx");
		}
		
		Sheet sheet = workbook.createSheet("Compañías");
		Iterator iterator = lista.iterator();
		
		int rowIndex = 0;		
		
		while(iterator.hasNext()){
			Compania cia = (Compania)iterator.next();
			log.debug(cia.toString());
			Row row = sheet.createRow(rowIndex++);							 
			row.createCell(0).setCellValue(cia.getRazonSocial() != null ? cia.getRazonSocial(): "N/D");
			row.createCell(1).setCellValue(cia.getRutaLogo() != null ? cia.getRutaLogo(): "N/D");
			row.createCell(2).setCellValue(cia.getFechaFundacion() != null ? cia.getFechaFundacion().toString():"N/D");
			row.createCell(3).setCellValue(cia.getPaginaWeb() != null ? cia.getPaginaWeb(): "N/D");
			row.createCell(4).setCellValue(cia.getAfiliadaAmis() != null ? cia.getAfiliadaAmis() : "N/D");
			row.createCell(5).setCellValue(cia.getAccionesInmobiliariaAmis() != null ? cia.getAccionesInmobiliariaAmis():"N/D");
			row.createCell(6).setCellValue(cia.getCertificadoAportacionPatrimonial() != null ? cia.getCertificadoAportacionPatrimonial():"N/D");			
			row.createCell(7).setCellValue(cia.getCapitalOrigen() != null ? cia.getCapitalOrigen().getNombre(): "N/D");
			row.createCell(8).setCellValue(cia.getGrupoFinaciero() != null ? cia.getGrupoFinaciero():"N/D");
			row.createCell(9).setCellValue(cia.getListaRamo() != null ? cia.getListaRamo().toString():"N/D");
			row.createCell(10).setCellValue(cia.getTipoMercado() != null ? cia.getTipoMercado().getNombre():"N/D");
			row.createCell(11).setCellValue(cia.getCanalVenta() != null ? cia.getCanalVenta().getNombre():"N/D");
			row.createCell(12).setCellValue(cia.getCapitalSocial() != null ? cia.getCapitalSocial().getNombre():"N/D");
			row.createCell(13).setCellValue(cia.getEstado() != null ? cia.getEstado():"N/D");
			row.createCell(14).setCellValue(cia.getFechaAlta() != null ? cia.getFechaAlta().toString():"N/D");					
		}
		
		FileOutputStream fos = new FileOutputStream(nombreArchivo);
		workbook.write(fos);
		fos.close();
		log.debug("Se genero el archivo XLS");
	}
}
