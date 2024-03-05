package spectra.attic.mail.smtp;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void main(String[] args) {
        String host = "smtp.yourmailserver.com";
        String port = "587"; // SMTP 포트 번호
        String username = "yourusername";
        String password = "yourpassword";
        String toAddress = "recipient@example.com";
        String subject = "Test Email";
        String messageContent = "This is a test email sent using Java.";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
