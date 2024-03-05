package spectra.attic.mail.imap;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

public class FetchEmail {

    public static void main(String[] args) {
        String host = "";
        String username = "yourusername";
        String password = "yourpassword";

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");

        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1); // 하루 전 날짜로 설정
            Date startDate = cal.getTime();

            SearchTerm searchTerm = new ReceivedDateTerm(ComparisonTerm.GE, startDate);
            Message[] messages = inbox.search(searchTerm);

            for (Message message : messages) {
                System.out.println("From: " + InternetAddress.toString(message.getFrom()));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("Content: " + message.getContent());
                System.out.println("Content Type: " + message.getContentType());
                System.out.println("Content Transfer Encoding: " + ((MimeMessage) message).getEncoding());
            }

            for (Message message : messages) {
                if (message.getContent() instanceof MimeMultipart) {
                    MimeMultipart multipart = (MimeMultipart) message.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                            System.out.println("Attachment: " + bodyPart.getFileName());
                        }
                    }
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
