package svj.tools;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.10.2016 16:22
 */
public class CommonTools
{
    /* Сравнить две строки, учитывая то что может быть NULL */
    public static int compareToWithNull ( String value1, String value2 )
    {
        if ( (value1 == null ) && (value2 == null) ) return 0;
        if ( (value1 != null ) && (value2 == null) ) return 1;
        if ( (value1 == null ) && (value2 != null) ) return -1;

        return value1.compareTo ( value2 );
    }
}
