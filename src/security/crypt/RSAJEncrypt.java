package security.crypt;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.09.12 15:31
 */

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;

class RSAJEncrypt
{
    public static void main ( String[] args )
    {
        if ( args.length != 1 ) System.out.println ( "Usage: RSAEncrypt nameOfFileToEncrypt" );
        else try
        {
            //Security.addProvider ( new BouncyCastleProvider() ); /* Use existing keystore */
            Security.addProvider ( new com.sun.net.ssl.internal.ssl.Provider() );

            /* Use existing keystore */
            String ALIAS = "mykey";
            // keystore alias
            KeyStore keystore = KeyStore.getInstance ( "JKS" );
            keystore.load ( new FileInputStream ( ".keystore" ), null );
            X509Certificate cert = ( X509Certificate ) keystore.getCertificate ( ALIAS );
            Cipher rsaCipher = Cipher.getInstance ( "RSA/ECB/PKCS1Padding", "BC" );
            rsaCipher.init ( Cipher.ENCRYPT_MODE, cert );
            //------ Get the content data from file -------------
            File f = new File ( args[ 0 ] );
            int sizecontent = ( ( int ) f.length () );
            byte[] data = new byte[ sizecontent ];
            try
            {
                FileInputStream freader = new FileInputStream ( f );
                System.out.println ( "\nContent Bytes: " + freader.read ( data, 0, sizecontent ) );
                freader.close ();
            } catch ( IOException ioe ) {
                System.out.println ( ioe.toString () );
                return;
            }
            byte[] encrypteddata = rsaCipher.doFinal ( data );
            RSAJEncrypt.displayData ( encrypteddata ); /* Save the signature in a file */
            FileOutputStream sigfos = new FileOutputStream ( "rsaJencrypted" );
            sigfos.write ( encrypteddata );
            sigfos.close ();

        } catch ( Exception e )         {
            System.err.println ( "Caught exception " + e.toString () );
        }
    }

    private static void displayData ( byte[] data )
    {
        System.out.println ( "Size of encrypted data: " + data.length );
        int bytecon = 0;
        //to get unsigned byte representation
        for ( int i = 0; i < data.length; i++ )
        {
            bytecon = data[ i ] & 0xFF;
            // byte-wise AND converts signed byte to unsigned.
            if ( bytecon < 16 ) System.out.print ( "0" + Integer.toHexString ( bytecon ).toUpperCase () + " " );
                // pad on left if single hex digit.
            else System.out.print ( Integer.toHexString ( bytecon ).toUpperCase () + " " );
            // pad on left if single hex digit.
        }
    }
}