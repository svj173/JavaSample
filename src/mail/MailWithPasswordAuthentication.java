package mail;


/**
 * For more info see: http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html

You need activation.jar, smtp.jar, and mailapi.jar in your classpath for this to work.
 * <BR/>
 * <BR/> Отработало нормально.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 13:56:23
 */
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

public class MailWithPasswordAuthentication
{
	public static void main(String[] args) throws MessagingException
    {
		new MailWithPasswordAuthentication().run();
	}

	private void run() throws MessagingException
    {
        Session session;
        Message message;

        session = getSession();

        message = new MimeMessage ( session );

		message.addRecipient(RecipientType.TO, new InternetAddress("sergey.zhiganov@eltex.loc"));
		message.addFrom(new InternetAddress[] { new InternetAddress("ems_test@eltex.loc") });

		message.setSubject("Тема сообщения -- test");
		message.setText ( "Тело сообщения\nПроверка\nProba" );
		//message.setContent("Тело сообщения", "text/plain");    // Почему-то ломаются русские буквы.

		Transport.send(message);
	}

    // Cp1251
    protected String makeCP1251 ( String s, String codePage ) throws Exception
    {
        try
        {
            String s2 = new String ( s.getBytes ( codePage ), "ISO-8859-1" );
            return s2;
        } catch ( Exception e )         {
            e.printStackTrace();
            //LogWriter.l.debug ( "Error!!!" );
            return s;
        }
    }

    private Session getSession()
    {
		Authenticator authenticator = new Authenticator();

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName() );
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", "eltex.loc");
		properties.setProperty("mail.smtp.port", "25");

		return Session.getInstance ( properties, authenticator );
	}

	private class Authenticator extends javax.mail.Authenticator
    {
		private PasswordAuthentication authentication;

		public Authenticator()
        {
			String username = "ems_test";
			String password = "123456";
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

}

