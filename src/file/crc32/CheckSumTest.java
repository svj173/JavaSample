package file.crc32;


import exception.SvjException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

/**
 * Проверка контрольной суммы.
 * <BR/> Алгоритмы - crc32, Adler.
 * <BR/> Adler более быстрый, но crc32 более качественный.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2012 9:48
 */
public class CheckSumTest
{
    public static void main ( String[] args )
    {
        CheckSumTest    manager;
        String          fileName, copyFileName;

        try
        {
            fileName        = "/home/svj/pkilinux.svj.zip";
            copyFileName    = "/home/svj/pkilinux.svj.zip.copy";

            manager = new CheckSumTest();

            // читаем файл и копируем его в другое место
            manager.copyFile ( fileName, copyFileName );

        } catch ( Exception e )       {
            e.printStackTrace();
        }
    }

    public void copyFile ( String fileName, String copyFileName )  throws SvjException
    {
        FileOutputStream    fOut;
        CheckedOutputStream csumOut;
        FileInputStream     fIn;
        CheckedInputStream  csumiIn;
        byte[]              buf;
        int                 ic;
        long                chSummIn, chSummOut;


        buf = new byte[16384];

        try
        {
            // in
            fIn     = new FileInputStream ( fileName );
            csumiIn = new CheckedInputStream ( fIn, new Adler32() );
            // out
            fOut    = new FileOutputStream ( copyFileName );
            csumOut = new CheckedOutputStream ( fOut, new Adler32() );

            // copy
            while ( (ic = csumiIn.read ( buf )) >= 0 )
            {
                csumOut.write ( buf, 0, ic );
            }

            csumiIn.close();
            csumOut.close();

            // check sum
            chSummIn    = csumiIn.getChecksum().getValue();
            chSummOut   = csumOut.getChecksum().getValue();
            System.out.println ( "Checksum IN  : " + chSummIn );
            System.out.println ( "Checksum OUT : " + chSummOut );

        } catch ( Exception e )         {
            System.out.println ( "error" );
            e.printStackTrace ();
            throw new SvjException ( e, "error: ", e );
        }

    }

}
