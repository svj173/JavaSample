package bit;


/**
 * Пример побитного AND
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 06.08.2014 14:36
 */
public class AndDemo
{
    public static void main ( String[] args )
    {
        int i1, i2, i3;

        i1 = 7169;
        i2 = 0xFF;
        i3 = i1 & i2;

        System.out.println ( "- i1 = " + i1 );
        System.out.println ( "- i2 = " + i2 );
        System.out.println ( "- i3 = " + i3 );
        System.out.println ( "- i3 == 1 : " + (i3 == 1) );
        System.out.println ( "- res = " + ((i1 & i2) == 1) );
    }
}
