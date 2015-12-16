package directorio.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class WriteImpl {
	
	private static final long serialVersionUID = -6949133070590737694L;

	private static final String HEADER = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n"+
	"xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n"+
	"xmlns=\"http://www.w3.org/TR/REC-html40\"\n"+
	"extension-element-prefixes=\"x o\">\n"+
	"<head>\n"+
	"<meta http-equiv=Content-Type content=\"text/html; charset=iso-8859-1\">\n"+
	"<meta name=ProgId content=Excel.Sheet>\n"+
	"<meta name=Generator content=\"Microsoft Excel 11\">\n"+
	"<!--\n"+
	"[if gte mso 9]><xml>\n"+
	" <x:ExcelWorkbook>\n"+
	"  <x:ExcelWorksheets>\n"+
	"   <x:ExcelWorksheet>\n"+
	"    <x:Name>Worksheet Name</x:Name>\n"+
	"    <x:WorksheetOptions>\n"+
	"     <x:Selected/>\n"+
	"     <x:FreezePanes/>\n"+
	"     <x:FrozenNoSplit/>\n"+
	"     <x:SplitHorizontal>1</x:SplitHorizontal>\n"+
	"     <x:TopRowBottomPane>1</x:TopRowBottomPane>\n"+
	"     <x:SplitVertical>1</x:SplitVertical>\n"+
	"     <x:LeftColumnRightPane>1</x:LeftColumnRightPane>\n"+
	"     <x:ActivePane>0</x:ActivePane>\n"+
	"     <x:Panes>\n"+
	"      <x:Pane>\n"+
	"       <x:Number>3</x:Number>\n"+
	"      </x:Pane>\n"+
	"      <x:Pane>\n"+
	"       <x:Number>1</x:Number>\n"+
	"      </x:Pane>\n"+
	"      <x:Pane>\n"+
	"       <x:Number>2</x:Number>\n"+
	"      </x:Pane>\n"+
	"      <x:Pane>\n"+
	"       <x:Number>0</x:Number>\n"+
	"      </x:Pane>\n"+
	"     </x:Panes>\n"+
	"     <x:ProtectContents>False</x:ProtectContents>\n"+
	"     <x:ProtectObjects>False</x:ProtectObjects>\n"+
	"     <x:ProtectScenarios>False</x:ProtectScenarios>\n"+
	"    </x:WorksheetOptions>\n"+
	"   </x:ExcelWorksheet>\n"+
	"  </x:ExcelWorksheets>\n"+
	"  <x:ProtectStructure>False</x:ProtectStructure>\n"+
	"  <x:ProtectWindows>False</x:ProtectWindows>\n"+
	" </x:ExcelWorkbook>\n"+
	"</xml><![endif]\n"+
	"-->\n"+
	"</head>\n<body>\n"; 
	
	private static final String FOOTER = "\n</body>\n</head>";
	
	/*
	private public String escribeArchivo(String user, String nombre, String titulo, 
                			ArrayList<HashMap<String, String> lista) {
		if (lista == null)
			return "null";
		WriteImpl obj = new WriteImpl();
		HashMap<String, String> mapa0 = lista.get(0);
		
		int columns = mapa0.keySet().size();
		obj.write(true, user, nombre, titulo, null, columns, headers, hide, span, spanTitle, data);
		
	}
	
	public String write(boolean newFile,
			 String user,
             String fileName,
             String title,
             String subTitle,
             int columns,
             String[] headers,
             boolean[] hide,
             int[] span,
             String[] spanTitle,
             ArrayList<ArrayList<String>> data) {
		String localFileName = null;
		ArrayList<String> tmp = null;
		int index = 0, rows = 0, col = 0;
		BufferedWriter out = null;
		
		
		if (newFile)
			localFileName = generateFileName(user, fileName);
		else
			localFileName = fileName;
		//log.debug("Prim:write, fileName = " + localFileName);
		try{
	    	
	    	
	    	out = new BufferedWriter(new FileWriter(localFileName, !newFile));
	    	
	    	if (data != null){
	    		rows = data.size();
		    	if (newFile)
		    		out.write(WriteImpl.HEADER);
		        
		        out.write("<table>\n");
		        if (newFile){
		        	out.write("<tr><td></td></tr>");
		        	out.write("<tr><td colspan=\"10\" style=\"font-size:12pt; font-weight:bold; text-align:center; color:#9FC8E8;\">");
		        	out.write(title);
		        	out.write("</td></tr>");
		        	out.write("<tr><td colspan=\""+columns+"\"></td></tr>");
		        }
		        
		        if (subTitle != null){
	        		out.write("<tr><td></td></tr>");
	        		out.write("<tr><td colspan=\"10\" style=\"font-size:10pt; font-weight:bold; text-align:center; color:#9FC8E8;\">");
		        	out.write(subTitle);
		        	out.write("</td></tr>");
	        	}
		        
		        out.write("<tr>\n");
	        	for (col = 0; col < columns; col++){
	        		if (span[col] > 0){
		        		out.write("<td colspan=\""+span[col]+"\" style=\"background-color:#9FC8E8; color:#FFFFFF; font-size:8pt; font-weight:bold; text-align:center;\">");
		        		out.write(UtilsStrings.emptyIfIsNull(spanTitle[col]));
		        		out.write("</td>");
	        		}
	        		else
	        			out.write("<td></td>");
	        	}
	        	out.write("</tr>\n");
		        
		        out.write("<tr>\n");
	        	for (col = 0; col < columns; col++){
	        		if (!hide[col]){
		        		out.write("<td style=\"background-color:#9FC8E8; color:#FFFFFF; font-size:8pt; font-weight:bold; text-align:center;\">");
		        		out.write(UtilsStrings.emptyIfIsNull(headers[col]));
		        		out.write("</td>");
	        		}
	        	}
	        	out.write("</tr>\n");
		        
		        for (index = 0; index < rows; index++){
		        	tmp = data.get(index);
		        	out.write("<tr>\n");
		        	for (col = 0; col < columns; col++){
		        		if (!hide[col]){
			        		out.write("<td>");
			        		out.write(UtilsStrings.emptyIfIsNull(tmp.get(col)));
			        		out.write("</td>");
		        		}
		        	}
		        	out.write("</tr>\n");
		        }
		        out.write("</table>\n");
	    	}
	    	else
	    		out.write(WriteImpl.FOOTER);
	    	
	        out.close();
	    }catch(Exception e){
	    	log.debug("write, fileName = " + localFileName+", error: "+e.getMessage());
	    	if (out != null)
	    		try{out.close();}catch(Exception b){log.debug("error al cerrar el archivo.");}
	    	localFileName = null;
	    }
	    
	    log.debug("write, fileName = " + localFileName);
		return (localFileName);
	}
	
	private String generateFileName(String user, String name){
		String fileName = null, nowStr = null;
		Calendar loSource = new GregorianCalendar();
		int day = 0, month = 0, year = 0, hour = 0, min = 0, sec = 0;
		Date now = new Date();
		String contextPath = null;
		
		try{
	    	contextPath = Propiedades.get("rutaCompleta");
	    	//log.debug(GWT.getModuleBaseURL());
	    	
			loSource.setTime(now);
			day = loSource.get(Calendar.DAY_OF_MONTH);
			month = loSource.get(Calendar.MONTH) + 1;
			year = loSource.get(Calendar.YEAR);
			hour = loSource.get(Calendar.HOUR_OF_DAY);
			min = loSource.get(Calendar.MINUTE);
			sec = loSource.get(Calendar.SECOND);
		}catch(Exception e){
			;
		}
		nowStr = UtilsStrings.llenaCeros(String.valueOf(month), 2)+
				 UtilsStrings.llenaCeros(String.valueOf(day), 2)+
				 String.valueOf(year)+
				 UtilsStrings.llenaCeros(String.valueOf(hour), 2)+
				 UtilsStrings.llenaCeros(String.valueOf(min), 2)+
				 UtilsStrings.llenaCeros(String.valueOf(sec), 2); 
		
		fileName = contextPath+"xls"+File.separatorChar+name+"_"+user+"_"+nowStr+".xls";
		return (fileName);
	}
	*/
}
