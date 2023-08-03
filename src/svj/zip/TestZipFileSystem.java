package svj.zip;


import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Example to manipulate a Zip file via FileSystem class.
 *
 * @author Roedy Green, Canadian Mind Products
 * @version 1.1 2013-03-26 add more env parms.
 * @since 2012-04-05
 */
public final class TestZipFileSystem
{
    public static void main ( String[] args ) throws Throwable
    {
        // creates a file called C:\temp\azip.zip, if it does not exist already,
        // and adds file C:\temp\afile.txt to it as \SomeTextFile.txt
        // build the local configuration Map for the FileSystem.
        // Arrange to have zip created if it does not exist already.
        final Map<String, String> env = new HashMap<> ();
        env.put ( "create", "true" );
        env.put ( "blockSize", "128k" );
        // create a link to the zip
        final URI uri = URI.create ( "jar:file:///C:/temp/bzip.zip" );
        // using the new Java 7 try resource syntax
        try (final FileSystem zipfs = FileSystems.newFileSystem ( uri, env ))
        {
            // get handle to external file
            final Path source = Paths.get ( "C:/temp/afile.txt" );
            // get handle to member internal to zip
            final Path target = zipfs.getPath ( "/SomeTextFile.txt" );
            // copy afile.txt as /Sometextfile.txt file into the zip file
            Files.copy ( source, target, StandardCopyOption.REPLACE_EXISTING );
            // note we don't absolutely have to flush or close the FileSystem, though it is a wise practice.
            zipfs.close ();
        }
    }

}
