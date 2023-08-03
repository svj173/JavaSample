package svj.zip;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.System.*;

/**
 * Example to create a Zip file with one element.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.0 2008-06-06
 * @since 2008-06-06
 */
public final class TestZipWrite
{
    /**
     * create a Zip file with one element.
     *
     * @param args not used
     * @throws java.io.IOException if problems writing file out.zip
     */
    @SuppressWarnings ( { "UnusedAssignment" } )
    public static void main ( String[] args ) throws IOException
    {
        // create a zip file with one element
        // specify the name of the zip file.
        final String zipFilename = "out.zip";
        // get the element file we are going to add, using slashes in name.
        final String elementName = "mydir/test.txt";

        // Open the Zip
        final FileOutputStream fos = new FileOutputStream ( zipFilename );
        final ZipOutputStream zip = new ZipOutputStream ( fos );
        zip.setLevel ( 9 );
        zip.setMethod ( ZipOutputStream.DEFLATED );

        // external file
        final File elementFile = new File ( elementName );

        // create the entry
        final ZipEntry entry = new ZipEntry ( elementName );
        entry.setTime ( elementFile.lastModified () );

        // read contents of file external file we are going to put in the zip
        final int fileLength = ( int ) elementFile.length ();
        final FileInputStream fis = new FileInputStream ( elementFile );
        final byte[] wholeFile = new byte[ fileLength ];
        final int bytesRead = fis.read ( wholeFile, 0/* offset */, fileLength );

        // checking bytesRead to ensure all read not shown.
        out.println ( bytesRead + " bytes read from " + elementName );
        fis.close ();

        // no need to setCRC, or setSize as they are computed automatically.
        zip.putNextEntry ( entry );

        // write the contents directly into the zip just after the zip element
        zip.write ( wholeFile, 0, fileLength );
        zip.closeEntry ();

        // close the entire zip
        zip.close ();
    }

}