package svj.except;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.09.2016 10:12
 */
public class MyDefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
{
    public static void main ( String[] args )
    {
        MyDefaultUncaughtExceptionHandler handler = new MyDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler ( handler );

        // деление на 0 - без try-catch
        int a, b, c;
    //    c = 100 / 0;            // Throwable = java.lang.ArithmeticException: / by zero

        // переполнение стека

        // OutOfMemory
        File file;
        InputStream stream;
        String fileName;
        byte[] bb;
        int ic = -1;

        //fileName = "/home/svj/suse_iso/openSUSE-Leap-42.1-DVD-x86_64.iso";         // 4Гб
        fileName = "/home/svj/Serg/Photo/Eltex/Корпоратив_лето_2013/IMG_0781.JPG";   // 8Mb == 12 for OutOfMemoryError

        try
        {
            file    = new File ( fileName );

            /*
            bb      = new byte[file.length ()];
            stream  = new FileInputStream ( file );
            stream.read ( bb );
            stream.close();
            */
            Collection<BufferedImage> list = new ArrayList<BufferedImage> ();
            BufferedImage img = null;
            for ( int i=0; i<Integer.MAX_VALUE; i++ )
            {
                // Null - если не изображение
                img = ImageIO.read ( file );
                //System.out.println ( "-- img = " + img );
                list.add ( img );
                ic = i;
            }
            /*
        } catch ( Exception e )    {
            // OutOfMemoryError здесь не ловится.
            System.out.println ( "Error" );
            e.printStackTrace();
            */
        } catch ( Throwable te )    {
            // OutOfMemoryError здесь ловится. Но тогда не отрабатывает наш хандлер ошибок.
            System.out.println ( "Fatal Error. ic = "+ic );
            te.printStackTrace();
        }

        System.out.println ( "Finish" );  // при ошибках сюда не доходит.

    }

    @Override
    public void uncaughtException ( Thread t, Throwable e )
    {
        System.out.println ( "thread = "+t );
        System.out.println ( "Throwable = " + e );
    }

}
