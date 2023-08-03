package applet;


/**
 * Загрузка файлов с сервера Web
 * <BR/>
 * Рассмотрим более подробно процесс получения файла. Прочитанный блок данных записывается в рабочий массив buf.
 * Преобразуем его в строку класса String, вызвав соответствующий конструктор. Во втором параметре передаем этому
 * конструктору нулевое значение, которое записывается в старшие байты формируемой строки UNICODE. Этот способ,
 * к сожалению, подходит только для работы с латинскими символами. Что же касается кириллицы, то для нее содержимое
 * старшего байта должно быть равно 4. Заметим также, что кодировка младшего байта кириллических символов UNICODE не
 * соответствует кодировке символов ANSI, привычной для программистов, создающих приложения Windows.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.12.2010 11:17:36
 */

import java.applet.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class TextEdit extends Applet
{
    // tl - ссылка на объект класса Label
    Label tl;
    // txtURLAddress - ссылка на однострочное поле редактирования текста
    // используется для ввода адреса ресурса URL
    TextField txtURLAddress;
    // txt - ссылка на многострочное поле редактирования класса
    // TextArea, где после
    // загрузки будет отображено содержимое файла

    TextArea txt;
    // btnGetText - ссылка на кнопку класса Button
    Button btnGetText;
    String str;
    byte buf[] = new byte[100];

    public void init ()
    // добавляем однострочное поле для ввода адреса URL,
    // кнопку, инициирующую процесс загрузки файла,
    // и многострочное поле редактирования
    {
        tl = new Label ( "Введите адрес URL:" );
        add ( tl );
        txtURLAddress = new TextField ( "http://", 50 );
        add ( txtURLAddress );
        txt = new TextArea ( "", 20, 70 );
        btnGetText = new Button ( "Получить файл" );
        // добавляем поля редактирования
        add ( btnGetText );
        add ( txt );
        // устанавливаем желтый цвет фона для окна аплета
        setBackground ( Color.yellow );
    }

    public boolean action ( Event evt, Object obj )
    // загрузка файла ресурса, заданного своим адресом URL,
    // и отображение его в многострочном поле редактирования
    // txt.
    {
        Button btn;
        // проверяем, вызвано ли это событие нашей кнопкой
        if ( evt.target instanceof Button )
        {
            btn = ( Button ) evt.target;
            if ( evt.target.equals ( btnGetText ) )
            {
                URL u;
                try
                {
                    // создаем объект класса URL
                    u = new URL ( txtURLAddress.getText () );
                    // открываем входной поток для записи ссылки в переменную u
                    InputStream is = u.openStream ();
                    // читаем данные из потока
                    while ( true )
                    {
                        // метод read возвращает количество прочитанных байт данных
                        // или -1, если был достигнут конец потока
                        int nReaded = is.read ( buf );
                        if ( nReaded == -1 ) break;
                        str = new String ( buf, 0 );
                        txt.appendText ( str.substring ( 0, nReaded ) );
                    }
                }
                // текст описания исключения записывается в строку состояния
                // браузера
                catch ( Exception ioe )
                {
                    showStatus ( ioe.toString () );
                }
            }
            else return false;
            return true;
        }
        return false;
    }

    public void paint ( Graphics g )
    {
        // определяем размеры окна
        Dimension dimAppWndDimension = size ();
        g.setColor ( Color.black );
        // рисуем прямоугольную рамку по периметру
        g.drawRect ( 0, 0, dimAppWndDimension.width - 1, dimAppWndDimension.height - 1 );
    }
    
}
