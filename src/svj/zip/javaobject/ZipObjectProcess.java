package svj.zip.javaobject;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.06.2017 14:48
 */
public class ZipObjectProcess
{
    public void zip ( Object object, String fileName )
    {
        FileOutputStream fos;
        GZIPOutputStream gz;
        ObjectOutputStream oos;

        try
        {
            // serialize the objects sarah and sam
            fos   = new FileOutputStream ( fileName );
            gz    = new GZIPOutputStream(fos);
            oos   = new ObjectOutputStream(gz);

            oos.writeObject ( object );
            oos.flush();
            oos.close();
            fos.close();

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }

}
