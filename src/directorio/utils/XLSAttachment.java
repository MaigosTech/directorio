package directorio.utils;

import java.io.InputStream;

public class XLSAttachment extends AttachmentStreamResponse {
    public XLSAttachment(InputStream is, String nombreArchivo) {
            super(is, nombreArchivo);
            this.contentType = "application/vnd.ms-excel";
            this.extension = "";
    }
}
