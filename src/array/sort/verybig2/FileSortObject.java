package array.sort.verybig2;


import array.sort.verybig.FileSortStorage;
import array.sort.verybig.FileSortStorageObject;

import java.io.IOException;
import java.util.*;

/**
 * Собственно сортировщик.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 15:59
 */
public class FileSortObject<T extends Comparable<T>>   implements Iterable<T>
{
    /** количества объектов на один файл. */
    private int bufferSize = 10000;
    private List<FileSortStorage> partFiles = new LinkedList<FileSortStorage> ();
    private Iterator<T> source;
    private SortComparator<T> comparator;
    private List<T> part = new LinkedList<T> ();

    public FileSortObject ( Iterator<T> sourceData, SortComparator<T> comparator, int bufferSize )
    {
        this.bufferSize = bufferSize;
        this.comparator = comparator;
        //source          = sourceData;
        setSource ( sourceData );
    }

    /**
     * Производит чтение исходных данных с сохранением блоков во временные файлы
     */
    void sortParts () throws IOException
    {
        while ( source.hasNext() )
        {
            part.add ( ( T ) source.next() );
            if ( (part.size() >= bufferSize) && source.hasNext() )
            {
                Collections.sort ( part, comparator );
                //System.out.println ( "++ sort part: "+ part );
                partFiles.add ( new FileSortStorageObject ( part ) );
                part.clear();
            }
        }

        // Последний пакет данных, который не вошел в файлы - также сортируем.
        Collections.sort ( part, comparator );
    }

    /**
     * Установка источника данных, используется итератор
     */
    public void setSource ( Iterator<T> newSource )
    {
        source = newSource;
        try
        {
            sortParts ();
        } catch ( IOException e )   {
            throw new RuntimeException ( e );
        }
        //Collections.sort ( part, comparator );
    }

    /**
     * Получение результата в виде итератора
     */
    public Iterator<T> iterator ()
    {
        if ( partFiles.size () == 0 )
        {
            // маленькая оптимизация, если всё уместилось в память
            return part.iterator ();
        }
        return new Iterator<T> ()
        {
            Long t = 0L;
            List<T> items = new ArrayList<T> ();
            List<Iterator<T>> iterators = new ArrayList<Iterator<T>> ();
            Integer minIdx = null;

            // динамическая инициализация итератора, вместо конструктора
            // делаем список итераторов по одному на файл, из которых будем извлекать элементы при слиянии
            {
                iterators.add ( part.iterator () );
                for ( FileSortStorage f : partFiles )
                {
                    iterators.add ( f.iterator () );
                }
                for ( Iterator<T> item : iterators )
                {
                    if ( item.hasNext () )
                    {
                        items.add ( item.next() );
                    }
                    else
                    {
                        throw new RuntimeException ( "failed to get first for iterator" );
                    }
                }
            }

            /**
             * Находит среди объектов минимальный, возвращает доступность очередного объекта
             */
            public boolean hasNext ()
            {
                T obj;
                if ( minIdx == null )
                {
                    for ( int i = 0; i < items.size(); i++ )
                    {
                        obj = items.get(i);
                        if ( obj != null )
                        {
                            if ( (minIdx == null) || comparator.compareIsOk ( obj,items.get ( minIdx )) )
                            {
                                minIdx = i;
                            }
                        }
                    }
                }
                return minIdx != null;
            }

            /**
             * Если есть доступный объект - возвращает, замещая его в доступных на очередной из файла.
             */
            public T next ()
            {
                T res = null;
                if ( hasNext() )
                {
                    // взял требуемый параметр по его вычисленному номеру.
                    res = items.get ( minIdx );
                    // вместо взятого занесли следующий из того же файла из которого и был взят этот параметр.
                    if ( iterators.get ( minIdx ).hasNext() )
                    {
                        items.set ( minIdx, iterators.get ( minIdx ).next() );
                    }
                    else
                    {
                        items.set ( minIdx, null );
                    }
                }
                minIdx = null;
                return res;
            }

            public void remove ()
            {
                throw new UnsupportedOperationException ();
            }
        };
    }

}
