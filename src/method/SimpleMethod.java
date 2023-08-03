package method;


/**
 * Для дергания методов.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.04.2012 13:31:33
 */
public class SimpleMethod
{
    public String process ( String str, Integer i )
    {
        String result;

        result  = str+"_"+(i*10000 + i);
        System.out.println ( "SimpleMethod.process: sign = "+ str+"/" +i + "; return = " + result );

        return result;
    }

}
