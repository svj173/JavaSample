package jar;


import java.io.*;
import java.util.*;
import java.util.jar.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.10.2012 11:12
 */
public class JarDir
{
    public static void main ( String args[] )
            throws IOException
    {
        if ( args.length != 1 )
        {
            System.out.println ( "Please provide a JAR filename" );
            System.exit ( -1 );
        }
        // создадим экземпляр JarFile, передав месторасположение в конструктор. Это можно сделать в форме String или File:
        JarFile jarFile = new JarFile ( args[ 0 ] );

        // После получения ссылки на JAR-файл вы можете прочитать каталог его содержимого.
        // Метод entries класса JarFile возвращает Enumeration всех записей. Для каждой записи вы можете получить ее атрибуты из фала манифеста,
        // любую информацию о сертификатах и любую другую информацию, относящуюся к конкретной записи, например ее название или размер.
        // Каждая конкретная запись является объектом JarEntry. Этот класс имеет методы getName, getSize и getCompressedSize.
        Enumeration en = jarFile.entries();
        while ( en.hasMoreElements () )
        {
            process ( en.nextElement() );
        }
    }

    private static void process ( Object obj )
    {
        JarEntry entry = ( JarEntry ) obj;
        String name = entry.getName ();
        long size = entry.getSize ();
        long compressedSize = entry.getCompressedSize ();
        System.out.println ( name + "\t" + size + "\t" + compressedSize );
    }

}
