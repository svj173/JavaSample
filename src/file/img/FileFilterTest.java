package file.img;


import java.io.File;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 24.03.2011 12:09:05
 */

public class FileFilterTest
{
    public static void main ( String[] args )
    {
        new FileFilterTest ();
    }

    public FileFilterTest ()
    {
        File dir = new File ( "/Users/al" );

        // list the files using our FileFilter
        File[] files = dir.listFiles ( new ImageFileFilter () );
        for ( File f : files )
        {
            System.out.println ( "file: " + f.getName () );
        }
    }
    
}

