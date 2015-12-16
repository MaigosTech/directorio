package directorio.services;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import directorio.models.Compania;
import directorio.models.Persona;

public class PdfGenerator {
	public static Logger log = Logger.getLogger(PdfGenerator.class.getName());	
	public static void generaPdfP(String nombre, List lista){
		
		Document document = new Document(PageSize.LETTER.rotate(), 0f, 0f, 0f, 30f);		
		
		try{
			PdfWriter.getInstance(document, new FileOutputStream(nombre));
			document.open();
			document.addTitle("Personas Directorio");
			
			PdfPTable table = new PdfPTable(20);			
			Font font = new Font(FontFamily.HELVETICA, 8);
			//PdfPCell cell = new PdfPCell(new Phrase("PERSONAS"));
			//table.addCell(cell);
			
			Iterator it = lista.iterator();
			
			while(it.hasNext()){
				Persona persona = (Persona)it.next();
				table.addCell(persona.getTitulo() != null ? persona.getTitulo():"N/D");
				table.addCell(persona.getNombreCompleto());
				table.addCell(persona.getCia() != null?persona.getCia().getNombre():"N/D");
				table.addCell(persona.getOficina() != null?persona.getOficina().getNombre():"N/D");
				table.addCell(persona.getTipoPersona() != null ? persona.getTipoPersona().getNombre() : "N/D");
				table.addCell(persona.getNivelPersona() != null ? persona.getNivelPersona().toString():"N/D");
				table.addCell(persona.getArea() != null ? persona.getArea().getNombre():"N/D");
				table.addCell(persona.getPuesto() != null ? persona.getPuesto(): "N/D");
				table.addCell(persona.getTelefonoCia() != null ? persona.getTelefonoCia():"N/D");
				table.addCell(persona.getTelefonoDirecto() != null ? persona.getTelefonoDirecto():"N/D");
				table.addCell(persona.getTelefonoCasa() != null ? persona.getTelefonoCasa():"N/D");
				table.addCell(persona.getCel() != null ? persona.getCel():"N/D");
				table.addCell(persona.getFacebook() != null ? persona.getFacebook():"N/D");
				table.addCell(persona.getWhatsapp() != null ? persona.getWhatsapp():"N/D");
				table.addCell(persona.getTwitter() != null ? persona.getTwitter():"N/D");
				table.addCell(persona.getEmailEmpresa() != null ? persona.getEmailEmpresa():"N/D");
				table.addCell(persona.getEmailPersonal() != null ? persona.getEmailPersonal():"N/D");
				table.addCell(/*persona.getEstatus()*/ "A");
				table.addCell(persona.getFechaAlta().toString());
				table.addCell(persona.getObservaciones() != null ? persona.getObservaciones() : null);
			}
			document.add(table);
			document.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		log.debug("Se genero el pdf");
	}
	
	public static void generaPdfC(String nombre, List lista){
		
		Document document = new Document(PageSize.LETTER.rotate(), 0f, 0f, 0f, 0f);		
		
		try{
			PdfWriter.getInstance(document, new FileOutputStream(nombre));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		document.open();
		
		PdfPTable table = new PdfPTable(15);
		table.setWidthPercentage(100);
		Font font = new Font(FontFamily.HELVETICA, 14);
		PdfPCell cell = new PdfPCell(new Phrase("COMPAÑIAS"));
		table.addCell(cell);
		
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			Compania cia = (Compania)it.next();
			table.addCell(cia.getRazonSocial() != null ? cia.getRazonSocial() : "N/D");
			table.addCell(cia.getRutaLogo() != null ? cia.getRutaLogo() : "N/D");
			table.addCell(cia.getFechaFundacion() != null ? cia.getFechaFundacion().toString() : "N/D");
			table.addCell(cia.getPaginaWeb() != null ? cia.getPaginaWeb() : "N/D");
			table.addCell(cia.getAfiliadaAmis() != null ? cia.getAfiliadaAmis() : "N/D");
			table.addCell(cia.getAccionesInmobiliariaAmis() != null ? cia.getAccionesInmobiliariaAmis() : "N/D");
			table.addCell(cia.getCertificadoAportacionPatrimonial() != null ? cia.getCertificadoAportacionPatrimonial() : "N/D");
			table.addCell(cia.getCapitalOrigen() != null ? cia.getCapitalOrigen().nombre : "N/D");
			table.addCell(cia.getGrupoFinaciero() != null ? cia.getGrupoFinaciero() : "N/D");
			table.addCell(cia.getListaRamo() != null ? cia.getListaRamo().toString() : "N/D");
			table.addCell(cia.getTipoMercado() != null ? cia.getTipoMercado().getNombre() : "N/D");
			table.addCell(cia.getCanalVenta() != null ? cia.getCanalVenta().getNombre() : "N/D");
			table.addCell(cia.getCapitalSocial() != null ? cia.getCapitalSocial().getNombre() : "N/D");
			table.addCell(cia.getEstado() != null ? cia.getEstado() : "N/D");
			table.addCell(cia.getFechaAlta() != null ? cia.getFechaAlta().toString() : "N/D");			
		}
		
		try {
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		document.close();
		
		log.debug("Se genero el pdf");
	}
}
