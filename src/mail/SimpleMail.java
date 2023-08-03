package mail;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 12:49:00
 */
import tools.DumpTools;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

class SimpleMail
{
    public static void main(String[] args) throws Exception
    {
        Properties  props;
        Session     mailSession;
        MimeMessage message;
        Transport   transport;
        String      to, from, host, username, password;
        Address[]   addresses;

        System.out.println ( "Sending mail..." );

        transport   = null;

        to          = "sergey.zhiganov@eltex.loc";
        //to          = "svj173@yahoo.com";

        from        = "ems_test@eltex.loc";    // Sender address rejected: not logged in
        //from        = "Test <ems_test@eltex.loc>";
        //from        = "ems_test";         // Sender address rejected: User unknown in local recipient table
        //from        = "ems_test@172.16.0.3";   // Bad sender address syntax
        //from        = "ems_test@localhost";   // Sender address rejected: User unknown in local recipient table

        //host        = "172.16.0.3";
        host        = "eltex.loc";

        username    = "ems_test";
        //password    = "87р345а89";
        password    = "123456";

        try
        {
            props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", host);
            props.setProperty("mail.user", username );
            props.setProperty("mail.password", password );

            //props.setProperty("mail.smtp.auth", "true" );
            props.setProperty("mail.smtp.auth.login.disable", "true" );

            mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(true);
            transport   = mailSession.getTransport();

            message     = new MimeMessage(mailSession);
            message.setSubject("HTML mail with images");
            message.setFrom(new InternetAddress(from));
            message.setContent("<h1>Hello world</h1>", "text/html");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.saveChanges();

            //transport.connect();
            transport.connect ( host, 25, username, password );
            System.out.println ( "-------------- connect OK ---------------------" );

            // Ошибка: Sender address rejected: not logged in
            //  -- Отклонен адрес отправителя: не входит в систему.

            addresses = message.getRecipients ( Message.RecipientType.TO );
            System.out.println ( "addresses TO = " + DumpTools.listArray( addresses, ',') );

            transport.sendMessage ( message, addresses );

        } catch ( Exception e )        {
            e.printStackTrace();
        } finally        {
            if ( transport != null )  transport.close();
        }
    }
    
}
