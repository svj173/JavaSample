package svj.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.lang.System.*;

/**
 * Example to read the elements of a Zip file sequentially.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2008-06-06
 * @since 2008-06-06
 */
public final class TestZipRead
{
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

        // specify the name of the directory where extracted data will go.
        final String targetdir = "targetdir";
        final FileInputStream fis = new FileInputStream ( zipFileName );
        final ZipInputStream zip = new ZipInputStream ( fis );

        // loop for each entry
        while ( true )
        {
            final ZipEntry entry = zip.getNextEntry ();
            if ( entry == null )
            {
                break;
            }

            // relative name with slashes to separate dirnames.
            final String elementName = entry.getName ();

            // This code won't work if ZipOutputStream
            // was used to create the zip file. ZipEntry.getSize will
            // return -1. You will have to read the element in chunks
            // or estimate a biggest possible size.
            // See http://mindprod.com/products.html#FILETRANSFER
            // Filetransfer.copy will handle the chunking and does
            // not need to know the length in advance.
            final int fileLength = ( int ) entry.getSize ();
            final byte[] wholeFile = new byte[ fileLength ];

            // read directly from ZIP after the zip entry.
            final int bytesRead = zip.read ( wholeFile, 0, fileLength );

            // checking bytesRead, and repeating if you don't get item all is not shown.
            out.println ( bytesRead + " bytes read from " + elementName );

            // were we will write the element as an external file
            final File elementFile = new File ( targetdir, elementName );

            // make sure dirs exist to hold the external file
            //noinspection ResultOfMethodCallIgnored
            elementFile.getParentFile ().mkdirs ();
            final FileOutputStream fos = new FileOutputStream ( elementFile );
            fos.write ( wholeFile, 0, fileLength );
            fos.close ();

            //noinspection ResultOfMethodCallIgnored
            elementFile.setLastModified ( entry.getTime() );
            zip.closeEntry ();
        }
        zip.close();
    }

}
