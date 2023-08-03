package array.sort.verybig;


import java.io.*;
import java.util.*;

/**
 * Класс для сортировки больших объёмов данных с использованием файловой подкачки.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:24
 */
public class FileSort<T extends Comparable<T>> implements Iterable<T>
{
    private int bufferSize = 10000;
    private List<FileSortStorage> partFiles = new LinkedList<FileSortStorage> ();
    private Iterator<T> source;
    // хранит последнией пакет записей - который не попал в файлы.
    private List<T> part = new LinkedList<T> ();

    /**
     * Конструктор по умолчанию, ничего не делает
     */
    public FileSort ()
    {
    }

    /**
     * Конструктор с параметром - источником
     */
    public FileSort ( Iterator<T> newSource )
    {
        setSource ( newSource );
    }

    /**
     * Конструктор с двумя параметрами - источником и количеством объектов на файл
     */
    public FileSort ( Iterator<T> newSource, Integer newSize )
    {
        this ( newSource );
        setBufferSize ( newSize );
    }

    /**
     * Установка количества объектов на один файл
     */
    public void setBufferSize ( int newSize )
    {
        bufferSize = newSize;
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
        Collections.sort ( part );
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
                        items.add ( item.next () );
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
                if ( minIdx == null )
                {
                    for ( int i = 0; i < items.size (); i++ )
                    {
                        if ( items.get ( i ) != null &&
                                ( minIdx == null ||
                                        items.get ( i ).compareTo ( items.get ( minIdx ) ) < 0 ) )
                        {
                            minIdx = i;
                        }
                    }
                }
                return minIdx != null;
            }

            /**
             * Если есть доступный объект - возвращает,
             * замещая его в доступных на очередной из файла
             */
            public T next ()
            {
                T res = null;
                if ( hasNext () )
                {
                    // взял требуемый параметр по его вычисленному номеру.
                    res = items.get ( minIdx );
                    // вместо взятого занесли следующий из того же файла из которого и был взят этот параметр.
                    if ( iterators.get ( minIdx ).hasNext () )
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

    /**
     * Производит чтение исходных данных с сохранением блоков во временные файлы
     */
    void sortParts () throws IOException
    {
        while ( source.hasNext () )
        {
            part.add ( ( T ) source.next () );
            if ( part.size () >= bufferSize && source.hasNext () )
            {
                Collections.sort ( part );
                partFiles.add ( new FileSortStorageObject ( part ) );
                part.clear();
            }
        }
    }

}
