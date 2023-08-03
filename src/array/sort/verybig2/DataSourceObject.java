package array.sort.verybig2;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Источник данных дял сортирвоки - генератор случайных чисел (до 1000 шт).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:58
 */
public class DataSourceObject implements Iterator<DataObject>
{
    private int i = 0;
    private final Iterator<DataObject> iterator;

    public DataSourceObject ()
    {
        Collection<DataObject> list;

        list = new ArrayList<DataObject>(15);
        list.add ( new DataObject ( 1, "Name-1", 1000.9 ) );
        list.add ( new DataObject ( 2, "Name-2", 10.7 ) );
        list.add ( new DataObject ( 3, "Name-3", 100.9 ) );
        list.add ( new DataObject ( 4, "Name-4", 101.9 ) );
        list.add ( new DataObject ( 5, "Name-5", 1001.39 ) );
        list.add ( new DataObject ( 6, "Name-6", 11.9 ) );
        list.add ( new DataObject ( 7, "Name-7", 12.9 ) );
        list.add ( new DataObject ( 8, "Name-8", 102.9 ) );
        list.add ( new DataObject ( 9, "Name-9", 1200.9 ) );
        list.add ( new DataObject ( 10, "Name-10", 220.43 ) );
        list.add ( new DataObject ( 11, "Name-11", 200.24 ) );
        list.add ( new DataObject ( 12, "Name-12", 10.7 ) );

        iterator = list.iterator();
    }

    public boolean hasNext ()
    {
        return iterator.hasNext();
    }

    public DataObject next ()
    {
        return iterator.next();
    }

    public void remove ()
    {
        throw new UnsupportedOperationException ();
    }

}
