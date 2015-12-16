package directorio.utils;

import java.io.InputStream;

public class PDFAttachment extends AttachmentStreamResponse {
        public PDFAttachment(InputStream is, String args) {
                super(is, args);
                this.contentType = "application/pdf";
                this.extension = "";
        }

        public PDFAttachment(InputStream is) {
                super(is);
                this.contentType = "application/pdf";
                this.extension = "";
        }
}
