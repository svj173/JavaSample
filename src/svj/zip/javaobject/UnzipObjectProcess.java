package svj.zip.javaobject;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.zip.GZIPInputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.06.2017 14:48
 */
public class UnzipObjectProcess
{
    public void unzip ( String fileName )
    {
        FileInputStream     fis;
        GZIPInputStream     gs;
        ObjectInputStream   ois;
        Object              object;

        try
        {
            //deserialize objects sarah and sam
            fis     = new FileInputStream ( fileName );
            gs      = new GZIPInputStream ( fis );
            ois     = new ObjectInputStream ( gs );
            object   = ( Object ) ois.readObject ();

            //print the records after reconstruction of state
            System.out.println ( "Read object = " + object.getClass().getName() );

            ois.close ();
            fis.close ();

        } catch ( Exception e )        {
            e.printStackTrace ();
        }
    }

}
