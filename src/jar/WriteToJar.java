package jar;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Следующий код приводится в качестве подтверждения того, что во время выполнения jar доступен для записи.
 * Не более. Он не сможет записать именно в ваш файл + сотрет все содержимое архива.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.10.2012 11:02
 */
public class WriteToJar
{
    /* Имя файла, находящегося в jar. */
    public static final String fileName = "test.txt";

    public static void main ( String[] args )
    {
        try
        {
            // Чтение файла из jar
            ZipFile zf = new ZipFile ( "fileFromJar.jar" );
            ZipEntry entry = zf.getEntry ( fileName );
            InputStream is = zf.getInputStream ( entry );
            int b;
            while ( ( b = is.read () ) != -1 )
            {
                System.out.print ( ( char ) b );
            }
            is.close();
            zf.close();

            // Запись нового jar
            FileOutputStream fout = new FileOutputStream ( "fileFromJar.jar" );
            ZipOutputStream zout = new ZipOutputStream ( fout );
            ZipEntry ze = new ZipEntry ( "fileName2.txt" );
            zout.putNextEntry ( ze );
            zout.write ( "Мы сделали это!".getBytes () );
            zout.closeEntry ();
            zout.close ();

        } catch ( Exception e )        {
            e.printStackTrace();
        }
    }

}
