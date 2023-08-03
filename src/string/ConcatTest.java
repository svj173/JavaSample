package string;


/**
 * Проверка, что быстрее складывает строк - плюс или StringBuilder.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.05.2016 9:25
 */
public class ConcatTest
{
    public static void main ( String[] args )
    {
        StringBuilder sb;
        String S1 = "111";
        String S2 = "2222";
        String str;
        int  max;
        long start, delta1, delta2;

        max     = 10000;
        str     = "";
        sb      = new StringBuilder();

        System.out.println ( "max = " + max );

        start   = System.currentTimeMillis();
        for ( int i=0; i<max; i++ )  str = str + S1;
        delta1   = System.currentTimeMillis () - start;
        System.out.println ( "d1 = " + delta1 );

        start   = System.currentTimeMillis();
        for ( int i=0; i<max; i++ )  sb.append ( S1 );
        delta2   = System.currentTimeMillis() - start;
        System.out.println ( "d2 = " + delta2 );

    }

}
