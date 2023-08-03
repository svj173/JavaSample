package svj.zip;

import java.io.*;
import java.util.zip.*;

/**
 * <BR> Читает ZIP файл и извлекает из него файлы.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:19:25
 */
public class UnZipFiles
{
    static final int BUFFER = 2048;

    public static void main ( String argv[] )
    {
        try
        {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream ( argv[0] );
            ZipInputStream zis = new ZipInputStream ( new BufferedInputStream ( fis ) );
            ZipEntry entry;
            while ( ( entry = zis.getNextEntry () ) != null )
            {
                System.out.println ( "Extracting: " + entry );
                int count;
                byte data[] = new byte[BUFFER];
                // write the files to the disk
                FileOutputStream fos = new FileOutputStream ( entry.getName () );
                dest = new BufferedOutputStream ( fos, BUFFER );
                while ( ( count = zis.read ( data, 0, BUFFER ) )
                        != -1 )
                {
                    dest.write ( data, 0, count );
                }
                dest.flush ();
                dest.close ();
            }
            zis.close ();
        } catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }
}
