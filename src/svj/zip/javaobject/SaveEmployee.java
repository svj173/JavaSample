package svj.zip.javaobject;

/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:29:55
 */
import java.io.*;
import java.util.zip.*;

public class SaveEmployee
{
   public static void main(String argv[]) throws     Exception
   {
       Employee             sarah, sam;
       FileOutputStream     fos;
       GZIPOutputStream     gz;
       ObjectOutputStream   oos;

      // create some objects
      sarah = new Employee("S. Jordan", 28, 56000);
      sam   = new Employee("S. McDonald", 29, 58000);
      // serialize the objects sarah and sam
      fos   = new FileOutputStream("empl.db");
      gz    = new GZIPOutputStream(fos);
      oos   = new ObjectOutputStream(gz);

      oos.writeObject(sarah);
      oos.writeObject(sam);
      oos.flush();
      oos.close();
      fos.close();
   }
}