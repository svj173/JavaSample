package svj.zip.javaobject;

/**
 * <BR> Читает из файла упакованный в svj.zip java-object.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:30:23
 */

import java.io.*;
import java.util.zip.*;

public class ReadEmployee
{
    public static void main ( String argv[] ) throws Exception
    {
        Employee            sarah, sam;
        FileInputStream     fis;
        GZIPInputStream     gs;
        ObjectInputStream   ois;

        //deserialize objects sarah and sam
        fis     = new FileInputStream ( "empl.db" );
        gs      = new GZIPInputStream ( fis );
        ois     = new ObjectInputStream ( gs );
        sarah   = ( Employee ) ois.readObject ();
        sam     = ( Employee ) ois.readObject ();
        //print the records after reconstruction of state
        sarah.print ();
        sam.print ();
        ois.close ();
        fis.close ();
    }

}