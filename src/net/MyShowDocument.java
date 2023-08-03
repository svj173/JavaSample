package net;


/**
 * В примере объект URL используется для доступа к HTML-файлу, на который он указывает, и отображает его в окне браузера с помощью метода showDocument ()
 * <BR/>
 * <BR/>
 Метод showDocument () может содержать параметры для отображения страницы различными способами:
    “_self” – выводит документ в текущий фрейм,
    “_blank” – в новое окно, “_top” – на все окно,
    “_parent” – в родительском окне,
 “имя_окна” – в окне с указанным именем.

 Для корректной работы данного примера апплет следует запускать из браузера, используя следующий HTML-документ:

<html>
<body align=center>
<applet code=chapt15.MyShowDocument.class></applet>
</body></html>
 
 * <BR/> User: svj
 * <BR/> Date: 23.12.2010 15:03:53
 */

import java.awt.Graphics;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JApplet;

public class MyShowDocument extends JApplet
{

    private URL bsu = null;

    public String getMyURL ()
    {
        return "http://www.bsu.by";
    }

    public void paint ( Graphics g )
    {
        int timer = 0;
        g.drawString ( "Загрузка страницы", 10, 10 );

        try
        {
            for (; timer < 30; timer++ )
            {
                g.drawString ( ".", 10 + timer * 5, 25 );
                Thread.sleep ( 100 );
            }

            bsu = new URL ( getMyURL () );
            getAppletContext().showDocument ( bsu, "_blank" );

        } catch ( InterruptedException e )        {
            e.printStackTrace ();
        } catch ( MalformedURLException e )        {
            // некорректно задан протокол, доменное имя или путь к файлу
            e.printStackTrace ();
        }
    }

}
