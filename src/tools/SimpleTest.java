package tools;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.06.2012 17:33:08
 */
public class SimpleTest
{
    public static void main ( String[] args )
    {
        int[]   buf;
        char    ch;

        buf  = new int[] { -17, -65 };
        for ( int i : buf )
        {
            ch  = (char) i;
            System.out.println ( "-- " + i + " = " + ch );   // выводит квадратики
        }
    }
    
}
