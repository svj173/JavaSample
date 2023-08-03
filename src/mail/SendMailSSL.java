package mail;


/**
 * Send an Email via Gmail SMTP server using SSL connection.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 11:07:27
 */
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailSSL
{
    private static final String GMAIL_HOST  = "smtp.gmail.com";
    private static final String ELTEX_HOST  = "172.16.0.3";

	public static void main(String[] args)
    {
        String host;
		Properties props;
        String  mailPort;

        host        = ELTEX_HOST;
        mailPort    = "465";

        props = new Properties();

		props.put("mail.smtp.host", host );
		props.put("mail.smtp.socketFactory.port", mailPort );
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", mailPort );

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("username","password");
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("to@no-spam.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler, \n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch ( MessagingException e ) {
			throw new RuntimeException(e);
		}
	}
}
