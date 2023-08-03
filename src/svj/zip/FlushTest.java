package svj.zip;


import org.supercsv.cellprocessor.ConvertNullTo;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Создаем несколкьо потоков - каждый заниамется совим делом. Пытаемся казать принудительно flush каждому потоку - будет ли работать?
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.06.2016 17:19
 */
public class FlushTest
{
    public static void main ( String[] args )
    {
        FileOutputStream dest;
        CheckedOutputStream checksum;
        ZipOutputStream zipOut;
        ZipEntry entry;
        CsvMapWriter writer;
        Writer streamWriter;
        Map<String, Object> alertData;
        CsvPreference csvPreference;
        int                 ic;
        File file;
        String[]            header, srcData;
        CellProcessor[]     cellProcessors;
        String fileName;

        writer = null;

        try
        {
            // Создать имя файла архива
            fileName        = "/home/svj/tmp/alerts_test.zip";
            file            = new File ( fileName );

            dest            = new FileOutputStream ( file );
            checksum        = new CheckedOutputStream ( dest, new Adler32 () );
            zipOut          = new ZipOutputStream ( new BufferedOutputStream ( checksum ) );
            entry           = new ZipEntry ( fileName + ".csv" );
            zipOut.putNextEntry ( entry );

            streamWriter    = new OutputStreamWriter ( zipOut, "UTF-8" );
            csvPreference   = new CsvPreference ( '"', ';', "\n" );
            writer          = new CsvMapWriter ( streamWriter, csvPreference );

            // Занести заголовок в файл
            header          = new String[] { "1", "2", "3", "4" };

            cellProcessors  = new CellProcessor[header.length];
            for ( int i=0; i<header.length; i++ )
                cellProcessors[i]  = new ConvertNullTo ("");


            writer.writeHeader ( header );

            // Мап
            //srcData = new String[] {}

            // Получить список алертов
            for ( int i=0; i<10; i++ )
            {
                // создать строку CSV
                alertData   = createData ( i, header );
                //getLogger().debug ( "alertData = %s", alertData );

                // Сохранить запись в файле
                    writer.write ( alertData, header, cellProcessors );   // скидывает данные в streamWriter
                    // борьба с накоплением данных в памяти явы в ArrayList CsvMapWriter. Такое ощущение что не успевает скидывать в файл и накапливает данные до 700Мб. #62488/32
                    streamWriter.flush();           // скидывает данные в zipOut
                    zipOut.flush();                 // скидывает данные в checksum
                    checksum.flush();               // скидывает данные в dest
                    dest.flush();                   // скидывает данные в файл
            }

        } catch ( Throwable oe )        {
            oe.fillInStackTrace();
        } finally {
            try
            {
                // закрываем файл
                if ( writer != null )  writer.close();
            } catch ( Exception e )        {
                e.fillInStackTrace();
            }
        }
    }

    private static Map<String, Object> createData ( int number, String[] header )
    {
        Map<String, Object>  result;

        result = new HashMap<String, Object> ();

        for ( int i=0; i<header.length; i++ )
        {
            result.put ( header[i], "Text_"+number+"__"+i );
        }
        return result;
    }

}
