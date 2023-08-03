package string;


import java.io.IOException;
import java.util.Scanner;

/**
 * Задача - запрашивать введение данных, но ограничить их по времени, чтобы запрос долго не висел.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.10.2012 11:27
 */
public class InputStringWithTimer
{
    private static int timeout = 2000;

    public static void main ( String args[] ) throws IOException
    {
        String string;
        Input input = new Input ();
        input.start ();
        try
        {
            Thread.sleep ( timeout );
        } catch ( InterruptedException e )    {
            e.printStackTrace ();
        }
        input.interrupt ();
        string = input.getStr ();
        System.out.println ( "\nВремя вышло!" );
        System.out.println ( "Введенная строка: " + string );
    }

    static class Input extends Thread
    {
        private String str = "";

        public void run ()
        {
            System.out.print ( "Введите строку:\n> " );
            Scanner in = new Scanner ( System.in );
            str = in.nextLine ();
        }

        public String getStr ()
        {
            return str;
        }
    }

}
