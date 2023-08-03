package array.compare;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Сранвить коллекции - искать одинаковые элементы.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.01.2019 16:45
 */
public class CompareList
{

    //public static <T> boolean hasDuplicate(Iterable<T> all) {
    private <T> Collection<T> hasDuplicate(Set<T> set, Collection<T> all) {
        Collection<T> result = new ArrayList<>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (T each: all) {
            if (!set.add(each)) result.add(each);
        }
        return result;
    }

    public static void main ( String[] args )
    {
        CompareList handler;
        Collection<String> list, list1, list2, list3, result;

        list1 = new ArrayList<String>();
        list1.add ( "10.0.1.55" );
        list1.add ( "10.0.1.56" );
        list1.add ( "10.0.1.57" );
        list1.add ( "10.0.1.57" );
        list1.add ( "110.0.0.58" );
        list1.add ( "110.0.0.59" );

        list2 = new ArrayList<String>();
        list2.add ( "10.0.1.55" );
        list2.add ( "210.0.0.56" );
        list2.add ( "210.0.0.57" );
        list2.add ( "210.0.0.58" );
        list2.add ( "10.0.1.59" );

        list3 = new ArrayList<String>();
        list3.add ( "310.0.0.55" );
        list3.add ( "10.0.1.56" );
        list3.add ( "310.0.0.57" );
        list3.add ( "310.0.0.58" );
        list3.add ( "10.0.1.59" );

        //System.out.println ( "deviceMacAddressList = "+list1 );
        //System.out.println ( "dbMacAddresslist = "+list2 );

        //list.addAll(list1);
        //list.addAll(list2);
        //list.addAll(list3);

        handler = new CompareList();
        Set<String> set = new HashSet<>();

        list = handler.hasDuplicate ( set, list1 );
        result = new ArrayList<String>(list);
        list = handler.hasDuplicate ( set, list2 );
        result.addAll(list);
        list = handler.hasDuplicate ( set, list3 );
        result.addAll(list);

        System.out.println ( "-- result = "+result );
        // result = [10.0.1.57, 10.0.1.55, 10.0.1.56, 10.0.1.59]

    }
}
