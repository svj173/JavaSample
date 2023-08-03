package mail;


/**
 * Send an Email via Gmail SMTP server using TLS connection.
 * <BR/> Для smtp.gmail.com  login/password=свои   (username/password)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 11:03:46
 */
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMailTLS
{

    private static final String GMAIL_HOST  = "smtp.gmail.com";
    private static final String ELTEX_HOST  = "172.16.0.3";

	public static void main(String[] args)
    {
        String      host, toAddress;
		Properties  props;
        String      username, password;
        int         port;
        Message     message;
        Transport   transport;
        Session     session;

        host        = ELTEX_HOST;
		port        = 25;     // 587
		//username    = "ems_test@eltex.loc";
		username    = "ems_test";
		password    = "87р345а89";
		toAddress   = "sergey.zhiganov@eltex.loc";

		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
        
        //props.put("mail.transport.protocol", "smtp");

		session = Session.getInstance(props);
        session.setDebug(true);

		try
        {
			message = new MimeMessage(session);
			message.setFrom ( new InternetAddress("from@no-spam.com") );
			message.setRecipients ( Message.RecipientType.TO, InternetAddress.parse(toAddress) );
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,\n\n No spam to my email, please!");

			transport = session.getTransport("smtp");
			//transport = session.getTransport("pop");

			transport.connect ( host, port, username, password );
			//transport.connect(host, username, password);

			Transport.send(message);

			System.out.println("Done");

		} catch ( MessagingException e ) {
            System.out.println ( "err" );
            e.printStackTrace();
			//throw new RuntimeException(e);
		}
	}
}
