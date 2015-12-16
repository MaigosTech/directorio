package directorio.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

public class ExcelStreamResponse implements StreamResponse {
	private InputStream _is;
	private String nombreArchivo = "exportPT";
	
	public ExcelStreamResponse(InputStream is) {
		_is = is;
	}

	public String getContentType() {
		return "application/x-msexcel";
	}

	public InputStream getStream() throws IOException {
		return _is;
	}

	public void prepareResponse(Response arg0) {
        arg0.setHeader("Content-Disposition", "attachment; filename="
                + nombreArchivo + ".xls");
	}

}
