package file;


import java.io.File;
import java.io.FileInputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.11.2016 10:26
 */
public class PrintFileAsBytes
{
    public static void main ( String[] args )
    {
        String fileName;
        PrintFileAsBytes handler;

        fileName = "/var/lib/tomcat7/webapps/licence-webapp-4.0-SNAPSHOT/WEB-INF/classes/eltex_ca_cert.crt";

        handler = new PrintFileAsBytes();
        handler.printFileAsBytes ( fileName );
    }

    public void printFileAsBytes ( String fileName )
    {
        StringBuilder sb;
        int ic;

        System.out.println ( "fileName  = "+fileName );
        try
        {
            // Загрузить файл
            File f = new File ( fileName );
            FileInputStream fis = new FileInputStream ( f );
            byte[] buf = new byte [ (int) f.length() ];
            ic = fis.read ( buf );
            System.out.println ( "-- read "+ic+" bytes" );

            sb = new StringBuilder ( 2048 );
            for ( byte b : buf )
            {
                sb.append ( b );
                sb.append ( ", " );
            }
            System.out.println ( sb );

        } catch ( Exception e )        {
            //LogWriter.file.error ( e, "Load file ERROR. codePage = '", codePage, "', fileName = '", fileName, "'" );
            //throw new SvjException ( e, "Ошибка чтения файла '", fileName, "' с кодировкой '", codePage, "' :\n", e );
            e.printStackTrace ();
        }
    }

}
