package bit;

import java.util.Date;

/**
 * <BR> Изменение бита символа и обратное преобразование - примитивная шифрация.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 24.07.2006
 * <BR> Time: 18:02:21
 */
public class Bit
{
    public static void main ( String[] args )
    {
        byte c   = 16;
        String str, res, str2;
        char[] c1,c2;
        str  = "abcdef.195-XxA0";
        System.out.println ( "Ish 1 = " + str );
        c1  = str.toCharArray ();

        c2  = new char[c1.length];
        for ( int i=0; i<c1.length; i++ )
        {
            c2[i]   = ( char ) ( c1[i] ^ c );
        }
        res = new String ( c2 );
        System.out.println ( "Res = " + res );

        c1  = res.toCharArray ();
        c2  = new char[c1.length];
        for ( int i=0; i<c1.length; i++ )
        {
            c2[i]   = ( char ) ( c1[i] ^ c );
        }
        str2 = new String ( c2 );
        System.out.println ( "Ish 2 = " + str2 );
        System.out.println ( "Date = " + (new Date ()).getTime () ); // 13 символов
    }

}
