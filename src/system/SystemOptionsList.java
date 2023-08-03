package system;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Отобразить список всех системных переменных.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2012 15:03:45
 */
public class SystemOptionsList
{
    public static void main ( String[] args )
    {
        Enumeration<?> e = System.getProperties().propertyNames ();
        List<String> list = new ArrayList<String> ();
        while ( e.hasMoreElements () )
        {
            list.add ( ( String ) e.nextElement () );
        }
        
        Collections.sort ( list );
        for ( String key : list )
        {
            System.out.println ( key + "=" + System.getProperty ( key ) );
        }
    }
}
