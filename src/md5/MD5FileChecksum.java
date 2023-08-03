package md5;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.07.2013 15:54
 */
public class MD5FileChecksum
{
    // use com.google.common.hash API - сторонняя библиотека
    public static void createCheck ( File file )
    {
        /*
        HashCode md5 = Files.hash(file, Hashing.md5());
        byte[] md5Bytes = md5.asBytes();
        String md5Hex = md5.toString();

        HashCode crc32 = Files.hash(file, Hashing.crc32());
        int crc32Int = crc32.asInt();

        // the Checksum API returns a long, but it's padded with 0s for 32-bit CRC
        // this is the value you would get if using that API directly
        long checksumResult = crc32.padToLong();
        */
    }

    public static byte[] createChecksum ( String filename ) throws Exception
    {
        InputStream fis = new FileInputStream ( filename );

        byte[] buffer = new byte[ 1024 ];
        MessageDigest complete = MessageDigest.getInstance ( "MD5" );
        int numRead;

        do
        {
            numRead = fis.read ( buffer );
            if ( numRead > 0 )
            {
                complete.update ( buffer, 0, numRead );
            }
        }
        while ( numRead != -1 );

        fis.close ();

        return complete.digest();
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public static String getMD5Checksum ( String filename ) throws Exception
    {
        byte[] b = createChecksum ( filename );
        String result = "";

        for ( int i = 0; i < b.length; i++ )
        {
            result += Integer.toString ( ( b[ i ] & 0xff ) + 0x100, 16 ).substring ( 1 );
        }

        // Второй вариант получения суммы md5 - как целое число
        // new BigInteger ( 1, m.digest() )

        // 3-й
        //hash = new BigInteger ( 1, m.digest() ).toString ( 16 );


        return result;
    }

    public static void main ( String args[] )
    {
        try
        {
            System.out.println ( getMD5Checksum ( "apache-tomcat-5.5.17.exe" ) );
            // output :
            //  0bb2827c5eacf570b6064e24e0e6653b
            // ref :
            //  http://www.apache.org/dist/
            //          tomcat/tomcat-5/v5.5.17/bin
            //              /apache-tomcat-5.5.17.exe.MD5
            //  0bb2827c5eacf570b6064e24e0e6653b *apache-tomcat-5.5.17.exe
        } catch ( Exception e )    {
            e.printStackTrace();
        }
    }

}
