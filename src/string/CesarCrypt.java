package string;


/**
 * Преобразовать текст в шифро-текст - исп сдвиг символов - шифр Цезаря.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.10.12 13:13
 */
public class CesarCrypt
{
    public static void main ( String[] args )
    {
        String src, result;
        byte[] buf, tb;
        int sm;

        /*
        if ( args.length != 2 )
        {
            System.out.println ( "Use option: text sm" );
        }
        */

        src = " already used in '";
        sm  = 5;

        buf = src.getBytes();
        tb  = new byte[buf.length];

        for ( int i=0; i<buf.length; i++ )
        {
            tb[i]  = (byte) (buf[i] + sm);
        }

        result = new String ( tb );

        System.out.println ( "src    = '" + src + "'." );
        System.out.println ( "sm     = " + sm );
        System.out.println ( "result = '" + result + "'." );
    }

}
