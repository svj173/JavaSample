package svj.radius;


import net.jradius.client.RadiusClient;
import net.jradius.client.auth.CHAPAuthenticator;
import net.jradius.client.auth.RadiusAuthenticator;
import net.jradius.dictionary.Attr_NASPort;
import net.jradius.dictionary.Attr_NASPortType;
import net.jradius.dictionary.Attr_UserName;
import net.jradius.dictionary.Attr_UserPassword;
import net.jradius.packet.AccessRequest;
import net.jradius.packet.RadiusRequest;
import net.jradius.packet.RadiusResponse;
import net.jradius.packet.attribute.AttributeFactory;
import net.jradius.packet.attribute.AttributeList;

import java.net.InetAddress;

/**
 * An example use of the JRadius RADIUS Client API
 *
 * @author David Bird
 */
public class ExampleRadiusClient
{
    public static void main ( String[] args )
    {
        String              userName, userPwd, replyMessage, hostName;
        RadiusAuthenticator radiusAuthenticator;
        InetAddress         host;
        RadiusClient        rc;
        RadiusRequest       request;
        RadiusResponse      reply;
        boolean             isAuthenticated;
        int                 authPort, acctPort;

        userName = "admin";
        //userPwd  = "admin";
        userPwd  = "";

        //userName = "steve";
        //userPwd  = "testing";

        //userName = "bob";
        //userPwd  = "hello";

        hostName = "localhost";
        //hostName = "192.168.26.205";

        //authPort = 1812;
        //acctPort = 1813;
        authPort = 21812;
        acctPort = 21813;

        try
        {
            // Для чего это? Но без этого не приводятся классы-обьекты ответов.
            // - use org.apache.commons.pool.KeyedObjectPool --  commons-pool.jar
            AttributeFactory.loadAttributeDictionary ( "net.jradius.dictionary.AttributeDictionaryImpl" );

            host    = InetAddress.getByName ( hostName );
            rc      = new RadiusClient ( host, "testing123", authPort, acctPort, 100 );

            AttributeList attrs = new AttributeList();
            attrs.add(new Attr_UserName(userName));
            attrs.add(new Attr_NASPortType(Attr_NASPortType.Wireless80211));
            attrs.add ( new Attr_NASPort ( new Long ( 1 ) ) );

            request = new AccessRequest(rc, attrs);
            request.addAttribute ( new Attr_UserPassword ( userPwd ) );

            // Аутентификация и авторизация совмещены в один процесс
            System.out.println ( "---------------- Authenticate ---------------------" );

            // EAPMSCHAPv2Authenticator

            radiusAuthenticator = new CHAPAuthenticator();            // OK
            //radiusAuthenticator = new EAPAuthenticator();
            //radiusAuthenticator = new EAPMD5Authenticator();
            //radiusAuthenticator = new EAPMSCHAPv2Authenticator ();    // no  т.к. java не поддерживает MD4
            //radiusAuthenticator = new MSCHAPv1Authenticator();
            //radiusAuthenticator = new MSCHAPv2Authenticator();      // no
            //radiusAuthenticator = new PAPAuthenticator();

            System.out.println ( "Sending:\n" + request.toString() );

            reply = rc.authenticate ( (AccessRequest)request, radiusAuthenticator, 2 );

            System.out.println("Received:\n" + reply.toString());

            /*
            isAuthenticated = (reply instanceof AccessAccept);

            replyMessage    = (String) reply.getAttributeValue(Attr_ReplyMessage.TYPE);

            // Если не прошла авторизация - isAuthenticated = false; Reply Message: null
            System.out.println( "isAuthenticated = " + isAuthenticated +"; Reply Message: " + replyMessage );


            if ( ! isAuthenticated ) return;

            // Учет применения ресурса.
            System.out.println ( "---------------- Accounting ---------------------");

            // Создать ИД сессии
            attrs.add(new Attr_AcctSessionId(RadiusRandom.getRandomString(24)));

            // Занести - начало работы юзера
            request = new AccountingRequest(rc, attrs);
            request.addAttribute(new Attr_AcctStatusType("Start"));

            System.out.println("Sending:\n" + request.toString());

            reply = rc.accounting((AccountingRequest)request, 5);

            System.out.println("Received:\n" + reply.toString());

            // занести какие-то данные по работе - ограничение на работу сессии?
            request = new AccountingRequest(rc, attrs);
            request.addAttribute(new Attr_AcctStatusType("Interim-Update"));
            request.addAttribute(new Attr_AcctInputOctets(new Long(42949670L)));
            request.addAttribute(new Attr_AcctOutputOctets(new Long(5)));
            request.addAttribute(new Attr_AcctSessionTime(new Long(10)));

            System.out.println("Sending:\n" + request.toString());

            reply = rc.accounting((AccountingRequest)request, 5);

            System.out.println("Received:\n" + reply.toString());

            // еще какие-то данные
            request = new AccountingRequest(rc, attrs);
            request.addAttribute(new Attr_AcctStatusType("Interim-Update"));
            request.addAttribute(new Attr_AcctInputOctets(new Long(429496700L)));
            request.addAttribute(new Attr_AcctOutputOctets(new Long(5)));
            request.addAttribute(new Attr_AcctSessionTime(new Long(30)));

            System.out.println("Sending:\n" + request.toString());

            reply = rc.accounting((AccountingRequest)request, 5);

            System.out.println("Received:\n" + reply.toString());

            // окончание работы  юзера
            request = new AccountingRequest(rc, attrs);
            request.addAttribute(new Attr_AcctStatusType("Stop"));
            request.addAttribute(new Attr_AcctInputOctets(new Long(4294967000L)));
            request.addAttribute(new Attr_AcctOutputOctets(new Long(10)));
            request.addAttribute(new Attr_AcctSessionTime(new Long(60)));
            request.addAttribute(new Attr_AcctTerminateCause(Attr_AcctTerminateCause.UserRequest));

            System.out.println("Sending:\n" + request.toString());

            reply = rc.accounting ((AccountingRequest)request, 5);

            System.out.println("Received:\n" + reply.toString());
            */

        } catch (Exception e)        {
            e.printStackTrace();
        }
    }
}
