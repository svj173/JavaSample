package socket.send;

import org.apache.logging.log4j.*;

import java.net.Socket;
import java.net.ServerSocket;
import java.util.zip.*;
import java.util.Hashtable;
import java.io.*;

import com.svj.utils.FileTools;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.06.2006
 * <BR> Time: 10:58:19
 */
public class Util
{
    private static Logger logger = LogManager.getFormatterLogger ( Util.class );

    public static void  sendFileToSocket ( Socket socket, int isize ) throws Exception
    {
        OutputStream         out;
        ObjectOutputStream   oos;
        TestObjectWithFile   obj;
        int     im;

        out     = socket.getOutputStream();
        oos     = new  ObjectOutputStream (out);
        for ( int ic = 0; ic<10; ic++ )
        {
            im  = ic + isize;
            obj = Util.createCmd ();
            //obj = new TestObject ( im, msg + im );
            obj.code    = im;
            logger.debug ( ic + ". Create object = " + obj );
            //ic++;
            //Util.sendObject ( socket, obj );
            oos.writeObject(obj);
            oos.flush();
            //oos.close();
            //out.close();

            logger.debug ( ic + ". Send object = " + obj );
            // pause
            Thread.yield ();
            Thread.sleep ( 1000 );
        }
    }

    public static void  sendToSocket ( Socket socket, int isize ) throws Exception
    {
        OutputStream         out;
        ObjectOutputStream   oos;
        String msg;
        TestObject  obj;
        int     im;

        out     = socket.getOutputStream();
        oos     = new  ObjectOutputStream (out);
        msg = "Text_";
        for ( int ic = 0; ic<10; ic++ )
        {
            im  = ic + isize;
            obj = new TestObject ( im, msg + im );
            logger.debug ( ic + ". Create object = " + obj );
            //ic++;
            //Util.sendObject ( socket, obj );
            oos.writeObject(obj);
            oos.flush();
            //oos.close();
            //out.close();

            logger.debug ( ic + ". Send object = " + obj );
            // pause
            Thread.yield ();
            Thread.sleep ( 1000 );
        }
    }

    public static void  sendZipToSocket ( Socket socket, int isize ) throws Exception
    {
        GZIPOutputStream     gzipout;
        ObjectOutputStream   oos;
        String      msg;
        TestObject  obj;
        int         im;

        gzipout = new  GZIPOutputStream(socket.getOutputStream());
        oos     = new  ObjectOutputStream(gzipout);

        msg = "Text_";
        for ( int ic = 0; ic<10; ic++ )
        {
            im  = ic + isize;
            obj = new TestObject ( im, msg + im );
            logger.debug ( ic + ". Create object = " + obj );
            //ic++;
            //Util.sendObject ( socket, obj );
            oos.writeObject(obj);
            oos.flush();

            gzipout.flush();
            //gzipout.finish();


            logger.debug ( ic + ". Send object = " + obj );
            // pause
            Thread.yield ();
            Thread.sleep ( 1000 );
        }
        gzipout.finish();
    }

    public static void sendObject ( Socket socket, Object object )
    {
        OutputStream         out;
        ObjectOutputStream   oos;

        logger.debug ( "Start" );
        logger.debug ( "Send object = " + object );
        // write to client
        try
        {
            out     = socket.getOutputStream();
            oos     = new  ObjectOutputStream (out);
            oos.writeObject(object);
            oos.flush();
            oos.close();
            out.close();
        } catch ( Exception e )
        {
            logger.error ( "Send object error.", e );
        } finally
        {
            logger.debug ( "Finish" );
        }
    }

    public static void readFromSocket ( Socket socket )  throws Exception
    {
        InputStream         is;
        ObjectInputStream   ois;
        Object              obj;
        is  = socket.getInputStream ();
        ois = new ObjectInputStream ( is );
        while ( true )
        {
            obj     = ois.readObject();
            logger.debug ( "Read object = " + obj );
        }
    }

    public static void readFileFromSocket ( Socket socket )  throws Exception
    {
        InputStream         is;
        ObjectInputStream   ois;
        Object              obj;
        TestObjectWithFile  cmd;
        FileOutputStream    fos;
        String              str;
        byte[]              data;

        is  = socket.getInputStream ();
        ois = new ObjectInputStream ( is );
        while ( true )
        {
            obj     = ois.readObject();
            logger.debug ( "Read object = " + obj );
            // Распкаковать
            cmd     = (TestObjectWithFile) obj;
            data    = decompress ( cmd );
            // сохранить на диске
            str     = "c:/temp/zip2/" + cmd.fileName;
            fos     = new FileOutputStream ( str );
            fos.write ( data );
            fos.flush ();
            fos.close ();
            logger.debug ( "Save file = " + str + ", size = " + data.length );
        }
    }

    private static byte[] decompress ( TestObjectWithFile cmd )
    {
        String      inputString, outputString;
        byte[]      input, output, result;
        Deflater    compresser;
        Inflater    decompresser;
        int         compressedDataLength, resultLength;

        result  = null;

        try
        {
            // Decompress the bytes
            decompresser    = new Inflater ();
            decompresser.setInput(cmd.file, 0, cmd.file.length );
            result          = new byte[cmd.fileSize];
            resultLength    = decompresser.inflate(result);
            decompresser.end();
        } catch ( Exception e )
        {
            logger.error ( "Decompress error", e );
        }

        return result;
    }

    public static void readZipFromSocket ( Socket socket )  throws Exception
    {
        //InputStream         is;
        GZIPInputStream     gzipin;
        ObjectInputStream   ois;
        Object              obj;
        gzipin  = new GZIPInputStream (socket.getInputStream());
        //is  = socket.getInputStream ();
        ois = new ObjectInputStream ( gzipin );
        while ( true )
        {
            obj     = ois.readObject();
            logger.debug ( "Read object = " + obj );
        }
    }

    public static TestObjectWithFile createCmd ()
    {
        TestObjectWithFile result;
        FileInputStream fi;
        File        file;
        byte[]  date;
        int     ic, i;
        String  str;

        //result  = null;
        str     = "c:/temp/svj.zip/common.log";
        try
        {
            result  = new TestObjectWithFile ();
            //logger.debug ( "fileName = " + fileName );
            //str     = FileTools.createFileName ( fileName, path );
            logger.debug ( "file = " + str );
            file    = new File ( str );
            ic      = (int) file.length ();
            date    = new byte[ic];
            fi      = new FileInputStream ( file );
            i       = fi.read ( date );
            logger.debug ( "File size = " + ic + ", read size = " + i );
            result.fileName = "common.log";
            result.fileSize = ic;
            // ZIP
            result.file         = compress ( date );
            //result.fileSendSize = result.file.length;
            //result.id       = "10";

            /*
            // Сохранить на диске в ZIP виде.
            FileOutputStream     fos;
            GZIPOutputStream     gz;
            ObjectOutputStream   oos;
            fos   = new FileOutputStream(str+".svj.zip");
            gz    = new GZIPOutputStream(fos);
            oos   = new ObjectOutputStream(gz);
            oos.writeObject(result);
            oos.flush();
            oos.close();
            fos.close();
            */

        } catch ( Exception e )
        {
            logger.error ("Create Command error.", e );
            result  = null;
        }

        return result;
    }

    public static byte[] compress ( byte[] data )
    {
        String      outputString;
        byte[]      output, result;
        Deflater    compresser;
        Inflater    decompresser;
        int         ic, resultLength;

        result  = null;
        try
        {
            // Encode a String into bytes
            //input           = inputString.getBytes("UTF-8");

            // Compress the bytes
            output          = new byte[data.length];
            compresser      = new Deflater ();
            compresser.setInput(data);
            compresser.finish();
            ic              = compresser.deflate(output);
            result          = new byte[ic];
            System.arraycopy ( output, 0, result, 0, ic );

        } catch ( Exception e )
        {
            logger.error ( "Compress error.", e );
        }
        return  result;
    }

}
