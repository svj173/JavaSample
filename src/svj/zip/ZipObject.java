package svj.zip;

import java.util.zip.Inflater;
import java.util.zip.Deflater;

/**
 * <BR> Пример упаковки и распаковки обьекта. Работает только с массивами байт.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 19.06.2006
 * <BR> Time: 11:39:21
 */
public class ZipObject
{
    public static void main ( String argv[] )
    {
        String      inputString, outputString;
        byte[]      input, output, result;
        Deflater    compresser;
        Inflater    decompresser;
        int         compressedDataLength, resultLength;

        try
        {
            // Encode a String into bytes
            inputString     = "blahblahblah??";
            input           = inputString.getBytes("UTF-8");

            // Compress the bytes
            output          = new byte[100];
            compresser      = new Deflater();
            compresser.setInput(input);
            compresser.finish();
            compressedDataLength = compresser.deflate(output);

            // Decompress the bytes
            decompresser    = new Inflater ();
            decompresser.setInput(output, 0, compressedDataLength);
            result          = new byte[100];
            resultLength    = decompresser.inflate(result);
            decompresser.end();

            // Decode the bytes into a String
            outputString    = new String ( result, 0, resultLength, "UTF-8" );

            System.out.println ( "Result = " + outputString );

        } catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }

}
