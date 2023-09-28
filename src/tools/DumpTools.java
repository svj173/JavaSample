package tools;


import svj.obj.DomainObj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.07.2011 13:22:17
 */
public class DumpTools
{
    public static String printIntArray(int[] array) {
        return printIntArray(array, array.length);
    }

    public static String printIntArray(int[] array, int size) {
        if (array == null) {
            return "empty";
        } else {
            StringBuffer result = new StringBuffer(512);
            if (size > array.length) {
                size = array.length;
            }

            for(int i = 0; i < size; ++i) {
                result.append(array[i]);
                result.append(" ");
            }

            return result.toString();
        }
    }

    /**
     * Перечислить содержимое  массива через символ CH.
     * В конце строки символа CH - нет
     */
    public static String listArray ( Object[] array, char ch )
    {
        String result = "";
        StringBuilder sb = new StringBuilder ( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return result;

        int ic = array.length;
        for ( int i = 0; i < ic; i++ )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( array[ i ] );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString ();
        result = result.substring ( 0, result.length () - 2 );
        return result;
    }

    public static String listArray ( byte[] array, char ch )
    {
        String result = "";
        StringBuilder sb = new StringBuilder ( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return result;

        int ic = array.length;
        for ( int i = 0; i < ic; i++ )
        {
            //result = result + array[i] + ch + " ";
            sb.append ( array[i] );
            sb.append ( ch );
            sb.append ( " " );
        }
        // удалить последнюю запятую (символ CH)
        result = sb.toString ();
        result = result.substring ( 0, result.length () - 2 );
        return result;
    }

    public static String printArray ( Object[] array )
    {
        StringBuilder sb;

        sb = new StringBuilder ( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return "";

        int ic = array.length;
        for ( int i = 0; i < ic; i++ )
        {
            sb.append ( '\n' );
            sb.append ( array[ i ] );
        }
        return sb.toString();
    }

    public static String printArray ( short[] array )
    {
        StringBuilder sb;

        sb = new StringBuilder ( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return "";

        int ic = array.length;
        for ( int i = 0; i < ic; i++ )
        {
            sb.append ( '\n' );
            sb.append ( array[ i ] );
        }
        return sb.toString();
    }

    public static String printArray ( Object[] array, String sep )
    {
        StringBuilder sb;

        sb = new StringBuilder ( 128 );
        if ( ( array == null ) || ( array.length == 0 ) ) return "";

        int ic = array.length;
        for ( int i = 0; i < ic; i++ )
        {
            sb.append ( sep );
            sb.append ( array[ i ] );
        }
        return sb.toString();
    }

    public static String printDomainAsTree ( DomainObj domain )
    {
        StringBuilder   result;
        String          level = SCons.SP;

        if ( domain == null ) return SCons.SP;

        result = new StringBuilder(1024);

        result.append ( SCons.END_LINE );
        result.append ( level );
        result.append ( domain.getFullName() );

        level = level + "\t";
        for ( DomainObj to : domain.getChilds() )
        {
            printDomainAsTree ( result, to, level );
        }

        return result.toString();
    }

    private static void printDomainAsTree ( StringBuilder buffer, DomainObj domain, String level )
    {
        if ( domain == null ) return;

        buffer.append ( SCons.END_LINE );
        buffer.append ( level );
        buffer.append ( domain.getFullName() );

        level = level + "\t";
        for ( DomainObj to : domain.getChilds() )
        {
            printDomainAsTree ( buffer, to, level );
        }
    }

    /**
     * метод позволяет определить размер любого объекта, в байтах
     * @param obj  искомый обьект.
     * @return     размер обьекта в байт (-1 - какая-то ошибка).
     */
    public static long sizeOfObject ( Object obj )
    {
        long                    result;
        ByteArrayOutputStream byteObject;
        ObjectOutputStream objectOutputStream;

        try
        {
            byteObject          = new ByteArrayOutputStream();
            objectOutputStream  = new ObjectOutputStream ( byteObject );
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteObject.close();
            result  = byteObject.size();
        } catch ( IOException e )        {
            result  = -1;
        }

        byteObject          = null;
        objectOutputStream  = null;

        return result;
    }

    public static String printCollection ( Collection collection )
    {
        StringBuilder result = new StringBuilder ( 512 );

        //result.append ( "Collection [ " );
        if ( collection == null )
        {
            result.append ( "\n\tNull" );
        }
        else if ( collection.isEmpty() )
        {
            result.append ( "\n\tEmpty" );
        }
        else
        {
            int ic = 0;
            for ( Object obj : collection )
            {
                result.append ( "\n\t- " );
                result.append ( ic );
                result.append ( "). " );
                result.append ( obj );
                ic++;
            }
        }
        //result.append ( CCons.END_INFO_LINE );

        return result.toString();
    }

    public static String printMap ( Map map )
    {
        StringBuilder result = new StringBuilder ( 512 );

        result.append ( "Map [ " );
        if ( map == null )
        {
            result.append ( "\n\tNull" );
        }
        else if ( map.isEmpty() )
        {
            result.append ( "\n\tEmpty" );
        }
        else
        {
            int ic = 0;
            for ( Object key : map.keySet () )
            {
                result.append ( "\n\t- " );
                result.append ( ic );
                result.append ( "). " );
                result.append ( key );
                result.append ( "\t::\t" );
                result.append ( map.get ( key ) );
                ic++;
            }
        }
        result.append ( "\n]" );

        return result.toString();
    }


}
