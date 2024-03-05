package spectra.attic.mail.pop3;

import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class FetchEmail {

    public static void main(String[] args) {
        String host = "pop.yourmailserver.com";
        String username = "yourusername";
        String password = "yourpassword";

        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("pop3s");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                System.out.println("From: " + InternetAddress.toString(message.getFrom()));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Content: " + message.getContent());
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
