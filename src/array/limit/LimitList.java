package array.limit;


import java.util.*;

/**
 * Обрезать коллекции до указанного размера.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.01.2019 16:45
 */
public class LimitList
{

    private void limit (Collection<String> list, int newSize ) {
        // Здесь исходный не режется, а просто формируется другой лист.
        //return list.subList(0, newSize - 1);

        // 1)
        // Минус - если два одинаковых, то тогда будет не 3 а 4 элемента - они идут за один элемент.
        //List<String> newList = new ArrayList<>(list);
        //list.retainAll(newList.subList(0, newSize));

        // 2)
        /*
        ArrayList<String> newList = new ArrayList<>(list);
        newList.removeRange (0, list.size() - newSize );
        newList.trimToSize();
        list.retainAll(newList);
        */

        // 3) создаем новый список а потом запихиваем его в старый
        Collection<String> newList = new ArrayList<>();
        int ic = newSize;
        for ( String str : list ) {
            newList.add(str);
            ic = ic - 1;
            if ( ic == 0 ) break;
        }
        list.clear();
        list.addAll(newList);
    }

    public static void main ( String[] args )
    {
        LimitList handler;
        List<String> list, list1, list2, list3;

        list1 = new ArrayList<String>();
        list1.add ( "10.0.1.55" );
        list1.add ( "10.0.1.56" );
        list1.add ( "10.0.1.57" );
        list1.add ( "10.0.1.57" );
        list1.add ( "110.0.0.58" );
        list1.add ( "110.0.0.59" );
        list1.add ( "110.0.0.60" );
        list1.add ( "110.0.0.61" );

        list2 = new ArrayList<String>();
        list2.add ( "10.0.1.55" );
        list2.add ( "210.0.0.56" );
        list2.add ( "210.0.0.57" );
        list2.add ( "210.0.0.58" );
        list2.add ( "10.0.1.59" );
        list2.add ( "210.0.1.59" );

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

        int newSize = 3;
        handler = new LimitList();

        System.out.println ( "newSize = "+newSize );

        /*
        list = handler.limit ( list1 , newSize);
        System.out.println ( "-- list1 = "+list1 + "\nlist = " + list );

        list = handler.limit ( list2 , newSize);
        System.out.println ( "-- list2 = "+list2 + "\nlist = " + list );

        list = handler.limit ( list3 , newSize);
        System.out.println ( "-- list3 = "+list3 + "\nlist = " + list );
        */

        handler.limit ( list1 , newSize);
        System.out.println ( "-- list1 = "+list1 );

        handler.limit ( list2 , newSize);
        System.out.println ( "-- list2 = "+list2 );

        handler.limit ( list3 , newSize);
        System.out.println ( "-- list3 = "+list3 );


        //System.out.println ( "-- result = "+result );

    }
}
