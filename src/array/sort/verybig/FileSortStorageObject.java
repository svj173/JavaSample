package array.sort.verybig;


import java.io.*;
import java.util.*;

/**
 * Реализация сохранения на диске сериализованных объектов:
 * <BR/>  Класс сохраняет список в хранилище и предоставляет к нему доступ через итератор.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.09.2015 12:27
 */
public class FileSortStorageObject<T> implements FileSortStorage<T>
{
    private final File file;

    /**
     * Конструктор, создаёт временный файл и сохраняет в него объекты
     */

    public FileSortStorageObject ( List<T> objects ) throws IOException
    {
        file = File.createTempFile ( "FileSort", ".dat", new File("/home/svj/tmp/sort") );
        //System.out.println ( "- Create file: "+ file );
        setObjects ( objects );
    }

    /**
     * Сохраняем объекты в файл
     */
    public void setObjects ( List<T> objects ) throws IOException
    {
        ObjectOutputStream wr = new ObjectOutputStream ( new FileOutputStream ( file ) );
        for ( T item : objects )
        {
            wr.writeObject ( item );
            //System.out.println ( "--- "+ item );
        }
        wr.close();
    }

    /**
     * Итератор по файлу-хранилищу объектов
     */
    public Iterator<T> iterator ()
    {
        try
        {
            return new Iterator<T> ()
            {
                private ObjectInputStream fr = new ObjectInputStream ( new FileInputStream ( file ) );
                T obj;

                public boolean hasNext ()
                {
                    if ( obj == null )
                    {
                        try
                        {
                            obj = ( T ) fr.readObject ();
                        } catch ( ClassNotFoundException e )         {
                            throw new RuntimeException ( e );
                        } catch ( IOException e )       {
                            obj = null;
                        }
                    }
                    return obj != null;
                }

                public T next ()
                {
                    hasNext ();
                    T res = obj;
                    obj = null;
                    return res;
                }

                public void remove ()
                {
                    throw new UnsupportedOperationException ();
                }
            };
        } catch ( IOException e )     {
            throw new RuntimeException ( e );
        }
    }

    /**
     * Зачищаем
     */
    protected void finalize ()
    {
        //file.delete();
        file.deleteOnExit ();
    }

}