package svj.mail;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


/**
 * Отправка сообщений по электронной почте.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 14:21:21
 */
public class MailSender
{
    private EmsAuthenticator authenticator;
    private String mailHost;
    private int mailPort;

    public MailSender ( String username, String password, String mailHost, int mailPort )
    {
        authenticator = new EmsAuthenticator ( username, password );

        this.mailHost   = mailHost;
        this.mailPort   = mailPort;
    }

    private Session getSession()
    {
        Properties properties;

        properties = new Properties();
        properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName() );
        properties.setProperty("mail.smtp.auth", "true");

        properties.setProperty("mail.smtp.host", mailHost );
        properties.setProperty("mail.smtp.port", Integer.toString(mailPort) );

        // не требовать всех валидных адресов. хоть один валидный получит это письмо
        properties.setProperty("mail.smtp.sendpartial", "true");

        return Session.getInstance ( properties, authenticator );
    }

    public Message createMessage ()
    {
        Session session;
        Message message;

        session = getSession();
        message = new MimeMessage ( session );

        return message;
    }


    private class EmsAuthenticator extends Authenticator
    {
        private PasswordAuthentication authentication;

        public EmsAuthenticator ( String username, String password  )
        {
            authentication = new PasswordAuthentication ( username, password );
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    /*
            <mailserver>172.16.0.3</mailserver>
            <login>ems_test</login>
            <password>123456</password>
            <subject>Eltex.EMS. Сообщение от объекта '%s'.</subject>
            <body>Получено сообщение от объекта '%s', время '%s', тип сообщения '%s'.</body>
            <from>ems_test@eltex.loc</from>

     */
    public static void main ( String[] args )
    {
        MailSender  mailSender;
        Message     message;
        String      str;
        String[]    toAddresses;
        InternetAddress[] from;
        InternetAddress   ia;

        toAddresses = new String[] { "sergey.zhiganov@eltec.loc" };

        try
        {
            // String username, String password, String mailHost, int mailPort
            mailSender = new MailSender ( "ems_test", "123456", "172.16.0.3", 25 );

            ia          = new InternetAddress("ems_test@eltex.loc");
            from        = new InternetAddress[]{ia};

            message = mailSender.createMessage();

            for (String to : toAddresses) {
                if (to != null) {
                    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress (to));
                }
            }

            message.addFrom(from);

            str = "Subject test";
            message.setSubject(str);

            message.setSentDate(new Date ());

            str = "Message";
            //message.setContent ( str, "text/plain" );    // Почему-то ломаются русские буквы.
            message.setText(str);

            Transport.send ( message );

            //LogWriter.alert.debug("Send e-mail to '", toAddresses, "'.");

        } catch (Exception e)     {
            e.printStackTrace();
        }
    }

}
