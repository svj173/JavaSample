package md5;


import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Подсчитать MD5 для строки.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.07.2013 15:51
 */
public class CreateMd5
{
    /**
     *
     * @param text
     * @param charsetName    "UTF-8"
     * @return   MD5 выдать в виде текста.
     */
    public String md5 ( String text, String charsetName )
    {
        String hash = null;
        try
        {
            MessageDigest m = MessageDigest.getInstance ( "MD5" );
            m.update ( text.getBytes ( Charset.forName ( charsetName ) ), 0, text.length () );
            hash = new BigInteger ( 1, m.digest() ).toString ( 16 );

        } catch ( Exception e )        {
            e.printStackTrace ();
        }

        return hash;
    }

}
