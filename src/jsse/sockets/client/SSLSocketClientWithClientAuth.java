package jsse.sockets.client;


import com.svj.utils.ssl.SslManager;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.security.KeyStore;

/*
 * This example shows how to set up a key manager to do client
 * authentication if required by server.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.

 Сообщения когда нет коннекта:
 1. Connection refused: connect

 Для старта используем:
 java -Djavax.net.ssl.trustStore=c:\Serg\Projects\SVJ\JavaSample\src\jsse\samplecacerts
      -Djavax.net.ssl.trustStorePassword=changeit
      localhost 9000 index.html

 Используемые хранилища:
 1. Ключи
  - файл: testkeys
  - тип: JKS
  - провайдер: SUN
  - Записи - всего одна - ключ с сертификатом (keyEntry)
      - алиас: duke
 2. Сертификаты   (грузятся по умолчанию, используя системные переменные 'javax.net.ssl.trustStore' и т.д.)
  - файл: samplecacerts
 */

public class SSLSocketClientWithClientAuth
{

    public static void main ( String[] args ) throws Exception
    {
        String host = null;
        int port = -1;
        String path = null;    // URL address for get
        for ( int i = 0; i < args.length; i++ )
        {
            System.out.println ( args[i] );
        }

        if ( args.length < 3 )
        {
            System.out.println (
                    "USAGE: java SSLSocketClientWithClientAuth host port requestedfilepath" );
            System.exit ( -1 );
        }

        try
        {
            host = args[0];
            port = Integer.parseInt ( args[1] );
            path = args[2];
        } catch ( IllegalArgumentException e ) {
            System.out.println ( "USAGE: java SSLSocketClientWithClientAuth " +
                    "host port requestedfilepath" );
            System.exit ( -1 );
        }

        try
        {
            SSLSocket socket;
            /*
            * Set up a key manager for client authentication
            * if asked by the server.  Use the implementation's
            * default TrustStore and secureRandom routines.
            */
            /*
            SSLSocketFactory factory = null;
            try
            {
                SSLContext          ctx;
                KeyManagerFactory   kmf;
                KeyStore            ks;
                char[] passphrase = "passphrase".toCharArray ();

                ctx     = SSLContext.getInstance ( "TLS" );
                kmf     = KeyManagerFactory.getInstance ( "SunX509" );
                ks      = KeyStore.getInstance ( "JKS" );

                ks.load ( new FileInputStream ( "testkeys" ), passphrase );

                kmf.init ( ks, passphrase );

                // javax.net.ssl.trustStore = samplecacerts
			       // javax.net.ssl.trustStorePassword=changeit

                TrustManagerFactory tmf;
                TrustManager[]   managers;
                // Алгоритм, Провайдер
                //tmf     = TrustManagerFactory.getInstance ( "SunX509", "SUN" );
                tmf     = TrustManagerFactory.getInstance ( "SunX509" );
                KeyStore ts = KeyStore.getInstance ( "JKS" );
                ts.load ( new FileInputStream("c:/Serg/Projects/SVJ/JavaSample/src/jsse/samplecacerts"), "changeit".toCharArray() );
                // Инициализировать производителя доверенных диспетчеров
                tmf.init ( ts );     // keyStore
                managers    = tmf.getTrustManagers ();

                //   Params:  KeyManagers[], TrustManagers[], secureRandom
                //ctx.init ( kmf.getKeyManagers(), null, null );
                ctx.init ( kmf.getKeyManagers(), managers, null );

                factory = ctx.getSocketFactory ();
            } catch ( Exception e ) {
                throw new IOException ( e.getMessage () );
            }

            SSLSocket socket = ( SSLSocket ) factory.createSocket ( host, port );
            */

            String  ksFile, ksPassw, ksType, trFile, trPassw, trType, type;
            ksFile  = "testkeys";
            ksPassw  = "passphrase";
            trFile  = "c:/Serg/Projects/SVJ/JavaSample/src/jsse/samplecacerts";
            trPassw  = "changeit";
            type    = "TLS";
            ksType  = trType    = "JKS";
            SslManager  sslManager = new SslManager ( ksFile, ksPassw, ksType, trFile, trPassw, trType, type );

            socket = (SSLSocket) sslManager.getSocket ( host, port );

            /*
            * send http request
            *
            * See SSLSocketClient.java for more information about why
            * there is a forced handshake here when using PrintWriters.
            */
            socket.startHandshake ();

            System.out.println ( "Create SSL socket. Send data" );

            PrintWriter out = new PrintWriter (
                    new BufferedWriter (
                            new OutputStreamWriter (
                                    socket.getOutputStream () ) ) );
            out.println ( "GET " + path + " HTTP/1.0" );
            out.println ();
            out.flush ();

            /*
            * Make sure there were no surprises
            */
            if ( out.checkError () )
            {
                System.out.println (
                        "SSLSocketClient: java.io.PrintWriter error" );
            }

            /* read response */
            BufferedReader in = new BufferedReader (
                    new InputStreamReader (
                            socket.getInputStream () ) );

            String inputLine;

            while ( ( inputLine = in.readLine () ) != null ) System.out.println ( inputLine );

            in.close ();
            out.close ();
            socket.close ();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
    }

}
