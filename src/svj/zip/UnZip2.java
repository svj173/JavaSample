package svj.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:22:00
 */
public class UnZip2
{
    static final int BUFFER = 2048;

    public static void main ( String argv[] )
    {
        try
        {
            BufferedOutputStream dest = null;
            BufferedInputStream is = null;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile ( argv[0] );
            Enumeration e = zipfile.entries ();
            while ( e.hasMoreElements () )
            {
                entry = ( ZipEntry ) e.nextElement ();
                System.out.println ( "Extracting: " + entry );
                is = new BufferedInputStream
                        ( zipfile.getInputStream ( entry ) );
                int count;
                byte data[] = new byte[BUFFER];
                FileOutputStream fos = new
                        FileOutputStream ( entry.getName () );
                dest = new
                        BufferedOutputStream ( fos, BUFFER );
                while ( ( count = is.read ( data, 0, BUFFER ) )
                        != -1 )
                {
                    dest.write ( data, 0, count );
                }
                dest.flush ();
                dest.close ();
                is.close ();
            }
        } catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }
}
