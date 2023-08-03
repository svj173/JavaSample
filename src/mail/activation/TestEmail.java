package mail.activation;


/**
 * Sample Code to Send E-Mail.
 * Send a simple, single part, text/plain e-mail
 * <BR/> Use activation.jar (from the JavaBeans Activation Framework download)
 * <BR/> This sample code has debugging turned on ("mail.debug") to see what is going on behind the scenes in JavaMail code.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 11:11:08
 */
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class TestEmail
{
    private static final String GMAIL_HOST  = "smtp.gmail.com";
    private static final String ELTEX_HOST  = "172.16.0.3";

    public static void main(String[] args)
    {
        String to, from, host;
        Properties props;
        Session session;
        Message msg;

        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        //to      = "vipan@vipan.com";
        to      = "sergey.zhiganov@eltex.loc";
        //from    = "vipan@vipan.com";
        from    = "ems_test@eltex.loc";
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        //host    = "smtp.yourisp.net";
        host    = "172.16.0.3";

        // Create properties, get Session
        props = new Properties();
        // If using static Transport.send(),
        // need to specify which host to send it to
        props.put("mail.smtp.host", host);
        // To see what is going on behind the scene
        props.put("mail.debug", "true");

        session = Session.getInstance(props);

        try
        {
            // Instantiatee a message
            msg = new MimeMessage(session);

            //Set message attributes
            msg.setFrom(new InternetAddress(from));

            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject("Test E-Mail through Java");
            msg.setSentDate(new Date());

            // Set message content
            msg.setText("This is a test of sending a plain text e-mail through Java.\nHere is line 2.");

            //Send the message
            Transport.send(msg);
        }
        catch ( Exception mex) {
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
        }
    }
}//End of class
