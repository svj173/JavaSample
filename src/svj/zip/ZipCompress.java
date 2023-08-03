package svj.zip;


import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Использует компрессию Zip для компрессии любого числа файлов, переданных из командной строки.
 * <BR/> Подсчитывается и проверяется контрольная сумма. Алгоритмы - crc32, Adler.
 * <BR/> Adler более быстрый, но crc32 более качественный.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2012 9:23
 */
public class ZipCompress
{
    // Исключение выбрасывается на консоль:
    public static void main ( String[] args )     throws IOException
    {
        FileOutputStream    fOut;
        CheckedOutputStream csumOut;
        ZipOutputStream     zipOut;
        BufferedReader      in;
        int                 c, x;
        FileInputStream     fIn;
        CheckedInputStream  csumiIn;
        ZipInputStream      zipIn;
        ZipEntry            ze;

        // ------------------- Создать ZIP архив ----------------------------

        fOut       = new FileOutputStream ( "test.svj.zip" );
        csumOut    = new CheckedOutputStream ( fOut, new Adler32() );
        zipOut     = new ZipOutputStream ( new BufferedOutputStream ( csumOut ) );

        zipOut.setComment ( "A test of Java Zipping" );   // Хотя нет соответствующего getComment().

        // Цикл по именам файлов, заложенных в атрибуты командной строки.
        for ( String arg : args )
        {
            System.out.println ( "Writing file " + arg );
            in = new BufferedReader ( new FileReader ( arg ) );
            zipOut.putNextEntry ( new ZipEntry ( arg ) );

            while ( ( c = in.read() ) != -1 )   zipOut.write ( c );
            in.close();
        }
        zipOut.close();

        // Контрольная сумма действительна только после того, как файл будет закрыт!
        System.out.println ( "Checksum: " + csumOut.getChecksum().getValue() );


        // --------------------- Проверяем контрольную сумму ---------------------

        // Теперь вытянем файлы:
        System.out.println ( "Reading file" );

        fIn     = new FileInputStream ( "test.svj.zip" );
        csumiIn = new CheckedInputStream ( fIn, new Adler32() );
        zipIn   = new ZipInputStream ( new BufferedInputStream ( csumiIn ) );
        while ( ( ze = zipIn.getNextEntry() ) != null )
        {
            System.out.println ( "Reading file : " + ze );
            while ( ( x = zipIn.read () ) != -1 )    System.out.write ( x );
        }
        System.out.println ( "Checksum: " + csumiIn.getChecksum().getValue () );
        zipIn.close();


        // ---------------- Альтернативный способ для открытия и чтения ---------------------
        // svj.zip файлов:
        ZipFile zf = new ZipFile ( "test.svj.zip" );
        Enumeration e = zf.entries();
        while ( e.hasMoreElements () )
        {
            ZipEntry ze2 = ( ZipEntry ) e.nextElement ();
            System.out.println ( "File: " + ze2 );
            // ... и вытягиваем данные, как и раньше
        }
    }

}
