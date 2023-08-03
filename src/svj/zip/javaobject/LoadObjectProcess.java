package svj.zip.javaobject;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.06.2017 14:48
 */
public class LoadObjectProcess
{
    public void unzip ( String fileName )
    {
        FileInputStream     fis;
        ObjectInputStream   ois;
        Object              object;

        try
        {
            //deserialize objects sarah and sam
            fis     = new FileInputStream ( fileName );
            ois     = new ObjectInputStream ( fis );
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
