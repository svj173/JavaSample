package svj.zip.javaobject;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.06.2017 14:48
 */
public class SaveObjectProcess
{
    public void zip ( Object object, String fileName )
    {
        FileOutputStream fos;
        ObjectOutputStream oos;

        try
        {
            // serialize the objects sarah and sam
            fos   = new FileOutputStream ( fileName );
            oos   = new ObjectOutputStream ( fos );

            oos.writeObject ( object );
            oos.flush();
            oos.close();
            fos.close();

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }

}
