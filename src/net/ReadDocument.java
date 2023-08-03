package net;


/**
 * читается содержимое HTML-файла по указанному адресу и выводится в окно консоли.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 15:08:11
 */

import java.net.*;
import java.io.*;

public class ReadDocument
{
    public static void main ( String[] args )
    {
        try
        {
            URL lab = new URL ( "http://www.bsu.by" );
            InputStreamReader isr = new InputStreamReader ( lab.openStream () );
            BufferedReader d = new BufferedReader ( isr );
            String line = "";

            while ( ( line = d.readLine () ) != null )
            {
                System.out.println ( line );
            }
        } catch ( MalformedURLException e )        {
            // некорректно заданы протокол, доменное имя или путь к файлу
            e.printStackTrace ();
        } catch ( IOException e )                  {
            e.printStackTrace ();
        }
    }

}
