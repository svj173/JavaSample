package jsse.sockets.server;


import com.svj.utils.ssl.SslManager;
import com.svj.utils.ssl.SSLCons;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

/* ClassFileServer.java -- a simple file server that can server
 * Http get request in both clear and secure channel
 *
 * The ClassFileServer implements a ClassServer that
 * reads files from the file system. See the
 * doc for the "Main" method for how to run this
 * server.

 Используемые хранилища:
 1. Ключи
  - файл: testkeys
  - тип: JKS
  - провайдер: SUN
  - Записи - всего одна - ключ с сертификатом (keyEntry)
      - алиас: localhost
 2. Сертификаты
  - файл: samplecacerts
  - кол-во записей: 32
  - тип записей: trustedCertEntry
  - алиасы:
     - localhost
     - duke
     - equifaxsecureebusinessca1
     - verisignclass1g3ca
     - verisignclass2g2ca
     - verisignclass3g3ca
     - entrustglobalclientca
     - gtecybertrustglobalca


 */

public class ClassFileServer extends ClassServer
{

    private String docroot;

    private static int DefaultServerPort = 2001;

    /**
     * Constructs a ClassFileServer.
     *
     * @param docroot the path where the server locates files
     */
    public ClassFileServer ( ServerSocket ss, String docroot ) throws IOException
    {
        super ( ss );
        this.docroot = docroot;
    }

    /**
     * Returns an array of bytes containing the bytes for
     * the file represented by the argument <b>path</b>.
     *
     * @return the bytes for the file
     * @throws FileNotFoundException if the file corresponding
     *                               to <b>path</b> could not be loaded.
     */
    public byte[] getBytes ( String path )
            throws IOException
    {
        System.out.println ( "reading: " + path );
        File f = new File ( docroot + File.separator + path );
        int length = ( int ) ( f.length () );
        if ( length == 0 )
        {
            throw new IOException ( "File length is zero: " + path );
        }
        else
        {
            FileInputStream fin = new FileInputStream ( f );
            DataInputStream in = new DataInputStream ( fin );

            byte[] bytecodes = new byte[length];
            in.readFully ( bytecodes );
            return bytecodes;
        }
    }

    /**
     * Main method to create the class server that reads
     * files. This takes two command line arguments, the
     * port on which the server accepts requests and the
     * root of the path. To start up the server: <br><br>
     * <p/>
     * <code>   java ClassFileServer <port> <path>
     * </code><br><br>
     * <p/>
     * <code>   new ClassFileServer(port, docroot);
     * </code>
     */
    public static void main ( String args[] )
    {
        System.out.println (
                "USAGE: java ClassFileServer port docroot [TLS [true]]" );
        System.out.println ( "" );
        System.out.println (
                "If the third argument is TLS, it will start as\n" +
                        "a TLS/SSL file server, otherwise, it will be\n" +
                        "an ordinary file server. \n" +
                        "If the fourth argument is true,it will require\n" +
                        "client authentication as well." );

        int port = DefaultServerPort;
        String docroot = "";

        if ( args.length >= 1 ) {
            port = Integer.parseInt ( args[0] );
        }

        if ( args.length >= 2 ) {
            docroot = args[1];
        }
        String type = "PlainSocket";
        if ( args.length >= 3 ) {
            type = args[2];
        }

        try
        {
            ServerSocketFactory ssf;
            ServerSocket ss;
            // Old variant
            //ssf = ClassFileServer.getServerSocketFactory ( type );
            //ss = ssf.createServerSocket ( port );
            // My variant
            SslManager  ssm;
            String  ksType, sslType;
            ksType  = SSLCons.JKS;
            sslType = SSLCons.TLS;
            ssm = new SslManager ( "testkeys", "passphrase", ksType, "c:/Serg/Projects/SVJ/JavaSample/src/jsse/samplecacerts", "changeit", ksType, sslType );
            //ssm.setType ( type );
            System.out.println ( "SslManager = " + ssm );
            ss  = ssm.getServerSocket ( port );

            if ( args.length >= 4 && args[3].equals ( "true" ) ) {
                System.out.println ( "SET: NeedClientAuth" );
                ( ( SSLServerSocket ) ss ).setNeedClientAuth ( true );
            }
            new ClassFileServer ( ss, docroot );
        } catch ( IOException e ) {
            System.out.println ( "Unable to start ClassServer: " +
                    e.getMessage () );
            e.printStackTrace ();
        }
    }

    private static ServerSocketFactory getServerSocketFactory ( String type )
    {
        if ( type.equals ( "TLS" ) )
        {
            SSLServerSocketFactory ssf = null;
            try
            {
                // set up key manager to do server authentication
                SSLContext ctx;
                KeyManagerFactory kmf;
                KeyStore ks;
                char[] passphrase = "passphrase".toCharArray ();

                ctx = SSLContext.getInstance ( "TLS" );
                kmf = KeyManagerFactory.getInstance ( "SunX509" );
                ks = KeyStore.getInstance ( "JKS" );

                ks.load ( new FileInputStream ( "testkeys" ), passphrase );
                kmf.init ( ks, passphrase );
                ctx.init ( kmf.getKeyManagers (), null, null );

                ssf = ctx.getServerSocketFactory ();
                return ssf;
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
        }
        else
        {
            return ServerSocketFactory.getDefault ();
        }
        return null;
    }
}




