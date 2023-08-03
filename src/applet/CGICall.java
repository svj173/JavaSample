package applet;


/**
 * Получение данных от программы CGI и их обработка

 После TextEdit напишем второй аплет с названием CGICall, который будет решать более сложную задачу: вызвать расширение сервера Web, сделанное нами в виде программы CGI, получить от нее десять случайных чисел и отобразить эти числа в виде цветной столбчатой диаграммы.

 Сразу после запуска аплета в его окно выводится сообщение "Click me!". Если сделать щелчок левой клавишей мыши внутри окна, аплет запустит на сервере Web программу CGI, получит от нее случайные числа и нарисует диаграмму. Каждый раз, когда вы будете щелкать мышью на окне аплета, он будет обращаться к программе CGI за новыми случайными числами. Заметим, что цвета для отображения столбцов диаграммы выбираются аплетом также случайно. Исходный текст аплета CGICall представлен в листинге 2. Текст документа HTML, в который встроен аплет, приведен в листинге 3.
 * <BR/>
 * Листинг 3. Текст документа HTML, в который встроен аплет<html> <head> <title>CGICall</title> </head> <body> <hr> <applet code=CGICall.class name=CGICall width=360 height=240 > </applet> <hr> <a href="1821/CGICall.java">The source.</a> </body> </html>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2010 11:25:41
 */

import java.applet.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class CGICall extends Applet
{
    // u - ссылка на адрес URL программы CGI
    URL u;
    // c - ссылка на объект класса URLConnection, // используемый как канал передачи данных при // взаимодействии аплета и программы CGI
    URLConnection c;
// is - ссылка на входной поток класса DataInputStream,
    // по которому аплет получает данные от программы CGI
    DataInputStream is;
    // str - рабочее поле для хранения текстовой строки  со случайными числами, принятой от программы CGI
    String str = "";
    String val[] = new String[11];

    public void init ()
    {
        for ( int i = 0; i < 10; i++ ) val[ i ] = "0";
// вызываем метод repaint для перерисовки окна аплета до обмена данными с программой CGI; при перерисовке
// метод paint выводит в окне сообщение "Click me!"
        repaint ();
    }

    public void paint ( Graphics g )
    {
        Integer iInteger;
        int maxWidth = 0;
        int curWidth;
// устанавливаем желтый цвет фона окна, определяем размеры окна и обводим его черной рамкой
        setBackground ( Color.yellow );
        Dimension dimAppWndDimension = size ();
        g.setColor ( Color.black );
        g.drawRect ( 0, 0, dimAppWndDimension.width - 1, dimAppWndDimension.height - 1 );
// для преобразования строк к типу int  создаем объект класса Integer i
        iInteger = new Integer ( val[ 0 ] );
        maxWidth = iInteger.intValue ();
        if ( maxWidth == 0 ) g.drawString ( "Click me!", 10, 100 );
        else
        {
// выводим строку, полученную от программы CGI, в исходном виде
            g.drawString ( str, 10, 20 );
            for ( int i = 1; i < 11; i++ )
            {
// выбираем случайным образом компоненты цвета  для изображения очередного столбца диаграммы
                int rColor = ( int ) ( 255 * Math.random () );
                int gColor = ( int ) ( 255 * Math.random () );
                int bColor = ( int ) ( 255 * Math.random () );
                g.setColor ( new Color ( rColor, gColor, bColor ) );
// извлекаем случайное значение, определяющее ширину столбца диаграммы
                iInteger = new Integer ( val[ i ] );
                curWidth = iInteger.intValue () + 1;
                g.fillRect ( 1, 10 + 20 * i, ( 300 * curWidth ) / maxWidth, 20 );
// справа от столбцов диаграммы отображаем их ширину в численном виде
                g.drawString ( val[ i ], 10 + ( 300 * curWidth ) / maxWidth, 24 + 20 * i );
            }
        }
    }

    public boolean mouseDown ( Event evt, int x, int y )
    {
        try
        {
// создаем объект u класса URL для загрузочного файла программы CGI
            u = new URL ( "http://frolov/scripts/random.exe" );
            c = u.openConnection ();
// после соединения создаем поток для форматированного ввода-вывода данных от программы CGI
            is = new DataInputStream ( c.getInputStream () );
// читаем строку из входного форматированного потока
            str = is.readLine ();
            is.close ();
// создаем объект класса StringTokenizer, передавая конструктору через первый параметр ссылку на разбираемую строку, а через второй -
// строку разделителей
            StringTokenizer st = new StringTokenizer ( str, ",rn" );
            int i = 0;
            while ( st.hasMoreElements () )
            {
// разбираем строку и записываем токены в массив val
                val[ i ] = ( String ) st.nextElement ();
                i++;
            }
            repaint ();
        } catch ( Exception ioe )
        {
            showStatus ( ioe.toString () );
        }
        return true;
    }
}
