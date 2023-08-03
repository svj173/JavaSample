package applet;


import java.applet.Applet;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2010 11:33:50
 */
public class LoadSaveFile extends Applet
{
    public void loadFile (String fileName )
    {
        // Метод getCodeBase() возвращает URL-адрес каталога, в котором лежит файл класса апплета.
        // Метод getDocumentBase() возвращает URL-адрес каталога, в котором лежит HTML-файл, вызвавший апплет.

        try
        {
            URL url = new URL(getCodeBase(), fileName);
            URLConnection urlConn = url.openConnection();
            urlConn.setDoInput(true);
            BufferedInputStream istream = new
            BufferedInputStream(urlConn.getInputStream());
            // ... читаем поток
            istream.close();
        } catch ( IOException e )        {
        }
    }

    public void saveFile (String fileName )
    {
        try
        {
            //а код записи примерно так:
            URL url = new URL(getCodeBase(), fileName);
            URLConnection urlConn = url.openConnection();
            urlConn.setDoOutput(true);
            BufferedOutputStream ostream = new BufferedOutputStream(urlConn.getOutputStream());
            //... записываем
            ostream.close();
        } catch ( IOException e )        {
        }
    }

    public static void main ( String[] args )
    {
    }

}
