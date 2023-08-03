package string;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.04.2017 11:02
 */
public class ReplaceStr
{
    public static void main ( String[] args )
    {
        String str, result;

        str     = "-test.root";

        result  = str.replaceAll("[:-]",":");

        System.out.println ( "str    = " + str );
        System.out.println ( "result = " + result );
    }
}
