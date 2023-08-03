package array.sort.verybig2;


import array.sort.verybig.FileSort;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Сортирвока очень больших массивов. Используются тмп-файлы.
 * Алгоритм:
 * 1. С помощью итератора читается часть данных, которая вмещается в память (количество записей задаётся).
 2. Прочитанный блок сортируется тем же quicksort (накладные расходы по памяти у него не велики).
 3. Далее отсортированный блок складывается в свой временный файл, где лежит до поры до времени.
 4. Пока есть данные, повторяются п1-3
 5. Здесь у нас имеется N файлов, каждый из которых содержит отсортированную порцию данных.
 6. Создаётся итератор, который элементарно производит слияние имеющихся файлов.

 При слиянии тоже используются итераторы (что поделать, нравятся мне они), которые выбирают очередную запись из каждого отдельного файла.
 Из выбранных записей выбирается минимальная, которая возвращается наружу.
 На место выданной наружу записи тут же подчитывается очередной объект из соответствующего файла.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:30
 */
public class BigSortTest2
{
    public static void main ( String[] args )
    {
        Iterator<DataObject>        sourceData;
        //FileSortObject<DataObject>  sort;
        FileSort<DataObject> sort;
        Comparator<DataObject>      comparator;
        String                      orderField, orderType;
        Collection<DataObject>      result;

        System.out.println ( "Test start" );

        // Создаем источник данных
        sourceData  = new DataSourceObject ();

        // создаем компаратор
        // - имя поля
        orderField  = "TX";
        // - направление сортировки
        orderType   = "DESC";
        comparator  = new DataObjectComparator ( orderField, orderType );

        // создаём класс-сортировщик
        //sort        = new FileSortObject<DataObject> ( sourceData, comparator, 5 );
        sort        = new FileSort<DataObject> ( sourceData );
        sort.setBufferSize ( 3 );

        int i = 0;
        // выводим отсортированный результат

        for ( DataObject res : sort )
        {
            if ( ++i % 10000 == 0 )
            {
                // когда результатов много имеет смысл их вывод отключить
                // и просто считать количество
                System.out.println ( i );
            }
            System.out.println ( " == " + i + ". " + res );
        }

        /*
        result      = sort.handle();

        int i = 1;
        for ( DataObject obj : result )
        {
            System.out.println ( " -- "+ i + ". " + obj );
            i++;
        }
        */
    }

}
