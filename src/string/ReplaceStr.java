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

        ReplaceStr handler = new ReplaceStr();

        // 1
        result = handler.replaceAll(str, "[:-]",":");
        System.out.println ( "\n ------ replaceAll  ------\nstr    = " + str + "; result = " + result);

        // 2
        System.out.println ( "\n\n-------------- replacePoint --------------- ");
        String[] arStr = new String[] {"mode.first", "funct", "A. PROGRAM", "POWER", "vh.+.UP"};
        for (String s: arStr) {
            handler.replacePoint(s);
        }


        //System.out.println ( "str    = " + str );
        //System.out.println ( "result = " + result );
    }

    private String replaceAll (String source, String from, String to) {
        return source.replaceAll(from, to);
    }
    
    private String replacePoint (String source) {
        String result;
        if (source.contains(".")) {
            System.out.print ( "-- source = " + source );
            result = source.replace('.', '_');
            System.out.print ( "; source result 1  = " + result );
            result = source.replace(".", "_");
            System.out.println ( "; source result 2  = " + result );
        }
        return "";
    }

}
