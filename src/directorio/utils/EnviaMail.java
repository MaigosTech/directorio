package directorio.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviaMail {
    
    public EnviaMail() {
    }
    
    public static void enviar(String fromAddress, String toAddress, String subject, String data) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", Propiedades.get("mail.smtp.server"));
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(fromAddress));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(data);
            Transport.send(message);
        }
        catch (MessagingException me){
            me.printStackTrace();
        }
    }
  
}
