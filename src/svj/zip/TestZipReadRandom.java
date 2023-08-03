package svj.zip;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Example to read the elements of a Zip file randomly by name lookup.
 * <p>
 * Пример извлечения из архива одного конкретного файла.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2008-06-06
 * @since 2008-06-06
 */
public final class TestZipReadRandom
{

    /**
     * check that all contents of the jar are also in the ZIP and up to date.
     *
     * @param jar jar file containing class files
     * @param zip zip file containing class and other files to distribute
     */
    static void checkAllJarInZip ( File jar, File zip )
    {
        try
        {
            if ( !jar.exists () )
            {
                err.println ( "Warning: missing jar " + jar.toString () );
                return;
            }
            if ( !zip.exists () )
            {
                err.println ( "Warning: missing zip " + zip.toString () );
                return;
            }
            ZipFile j = new ZipFile ( jar );
            ZipFile z = new ZipFile ( zip );

            // loop for each jar entry
            for ( Enumeration<? extends ZipEntry> e = j.entries (); e.hasMoreElements (); )
            {
                final ZipEntry jarEntry = e.nextElement ();

                final String elementName = jarEntry.getName ();
                // zip won't have matching manifest
                if ( !elementName.startsWith ( "META-INF/" ) )
                {
                    final ZipEntry zipEntry = z.getEntry ( elementName );
                    if ( zipEntry == null )
                    {
                        err.println ( "Warning: " + zip.toString () + "/" + elementName + " missing" );
                    }
                    else if ( zipEntry.getSize () != jarEntry.getSize () )
                    {
                        err.println ( "Warning: " + zip.toString () + "/" + elementName + " size mismatch "
                                              + ( zipEntry.getSize () - jarEntry.getSize () ) );
                    }
                    // we can't do anything with timestamps since zip matches the corresponding element,
                    // where jar is the time the element was added to the jar, i.e. jar create time.
                }
            }// end for
            j.close ();
            z.close ();
        } catch ( IOException e )         {
            e.printStackTrace ( System.err );
            err.println ( "jar or zip i/o problem" );
        }
    }

    /**
     * read the elements of a Zip file sequentially.
     *
     * @param args not used
     * @throws java.io.IOException if problems writing file a.zip
     */
    public static void main ( String[] args ) throws IOException
    {
        // specify the name of the zip we are going to read
        final String zipFileName = "in.zip";
        // specify internal directory structure and file name to read from zip.
        final String elementName = "com/mindprod/batik/run.bat";
        // specify the name of the directory where extracted data will go.
        final String targetdir = "targetdir";
        // look up that entry in the zip
        final ZipFile zip = new ZipFile ( zipFileName );
        final ZipEntry entry = zip.getEntry ( elementName );
        // length should be ok, even for stream-created zips and jars.
        final int fileLength = ( int ) entry.getSize ();
        final byte[] wholeFile = new byte[ fileLength ];
        // read element contents.
        final InputStream is = zip.getInputStream ( entry );
        final int bytesRead = is.read ( wholeFile, 0, fileLength );
        // checking bytesRead, and repeating if you don't get item all is not shown.
        out.println ( bytesRead + " bytes read from " + elementName );
        // write contents of element from RAM to a file.
        // Where we will put the external file.
        final File elementFile = new File ( targetdir, elementName );
        // make sure dirs exist to hold the external file we are about to create.
        //noinspection ResultOfMethodCallIgnored
        elementFile.getParentFile ().mkdirs ();
        final FileOutputStream fos = new FileOutputStream ( elementFile );
        fos.write ( wholeFile, 0, fileLength );
        fos.close ();
        elementFile.setLastModified ( entry.getTime () );
        // no need for a zip.closeEntry()
        zip.close ();
    }

}
