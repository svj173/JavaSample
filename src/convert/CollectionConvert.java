package convert;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 13:35:29
 */
public class CollectionConvert
{
    public static <T> Collection<Collection<T>>  nestedArraysToNestedCollections ( T[][] source )
    {
        Collection<Collection<T>>   ret;
        Collection<T>               list;

        ret = new ArrayList<Collection<T>> ();
        for ( T[] aSource : source )
        {
            // could be ret.add(new ArrayList<T>(Arrays.asList(source[i]));
            list = new ArrayList<T> ( aSource.length );
            list.addAll ( Arrays.asList ( aSource ) );
            ret.add ( list );
        }
        return ret;
    }

    public static Object[][] nestedListsToNestedArrays ( Collection<? extends Collection<?>> source )
    {
        Object[][] result = new Object[source.size()][];
        int i = 0;
        for ( Collection<?> subCollection : source )
        {
            result[ i++ ] = subCollection.toArray ();
        }
        return result;
    }
}
