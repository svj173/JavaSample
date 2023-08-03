package string.regexc;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Строковые регулярные выражения.
 *
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.11.2010 13:45:31
 */
public class Regexp4
{
    /**
     * Разбиваем значение текстового поля Фильтр на лексемы - по пробелам, символу плюс и скобкам. Значения, завернутые в двойные кавычки считать за одну лексему.
     * @param value  Исходное значение.
     * @return       Лексемы.
     */
    public Collection<String> createValues ( String value )
    {
        Collection<String> result;
        StringBuilder sb;
        StringReader reader;
        int ic;
        byte[] bytes;
        char ch;
        boolean needQuote;

        result  = new ArrayList<String> ();

        //bytes   = value.getBytes ( EmsConst.UTF8 );
        sb          = new StringBuilder ( 16 );
        needQuote   = false;
        for ( int i=0; i<value.length(); i++ )
        {
            ch = value.charAt ( i );
            if ( needQuote )
            {
                // ждем второй кавычки
                if ( ch == '\"' )
                {
                    // Появилась вторая кавычка
                    if ( sb.length() > 0 )  result.add ( sb.toString() );
                    sb          = new StringBuilder ( 16 );
                    needQuote   = false;
                }
                else
                {
                    sb.append ( ch );
                }
            }
            else
            {
                // ждем символов-разделителей
                if ( ch == '\"' )
                {
                    // Появилась первая кавычка
                    if ( sb.length() > 0 )  result.add ( sb.toString() );
                    sb          = new StringBuilder ( 16 );
                    needQuote   = true;
                }
                else if ( (ch != ' ') && (ch != '+') && (ch != '[') && (ch != ']') )
                {
                    // не разделитель - скидываем в буфер текущей лексемы
                    sb.append ( ch );
                }
                else
                {
                    // разделитель - скидываем лексему
                    if ( sb.length() > 0 )  result.add ( sb.toString() );
                    sb  = new StringBuilder ( 16 );
                }
            }
        }

        // сбрасываем остатки
        if ( sb.length() > 0 )  result.add ( sb.toString() );


        /*
        reader = new StringReader ( value );

        while ( ic = reader.read() != -1 )
        {

        }
        */

        return result;
    }



    public static void main ( String[] args )
    {
        Pattern pattern;
        Matcher matcher;
        boolean b;
        String regexp, reg1, reg2, str;
        Regexp4 handler;
        Collection<String> result;

        handler = new Regexp4();

        String[] stext = new String[] { "test1 test2 \"slot 3\"", "test1 \"slot 4\" test2", "\"slot 5\" test1+test2" };

        // (?=.*?(?i)slot)(?=.*?(?i)78).*
        // (?=.*?(?i)slot 68).*

        //System.out.println("regexp : " + regexp );
        for ( String text : stext )
        {
            //b = text.matches ( regexp );
            result = handler.createValues ( text );
            System.out.println ( "-- '"+text+"' : "+result);
        }

        reg1    = "(?=.*?(?i)slot)(?=.*?(?i)7).*";   // вхождение 'slot' и '7'
        reg2    = "(?=.*?(?i)slot 7).*";             // вхождение фразы 'slot 7'

        String[] rt = new String[] { "slot 7: проверка", "AA slot 2 : test 7", "Test 7 slot", "test aa slot 7", "aa bb slot" };

        System.out.println ( "\n============= regexp : "+reg1);
        for ( String text : rt )
        {
            b = text.matches ( reg1 );
            System.out.println ( "-- '"+text+"' : "+b);
        }

        System.out.println ( "\n============= regexp : "+reg2);
        for ( String text : rt )
        {
            b = text.matches ( reg2 );
            System.out.println ( "-- '"+text+"' : "+b);
        }

        regexp = "(?=.*?(?i)22).*";
        str    = "2891086;11.02.2015 11:31:43;CRITICAL;Авария;Системный;alert;Перезапуск EMS-сервера;0;1.3.6.1.4.1.35265.2.1.1.1.10;Новый;0;0;0;1;EMS restart;EMS restart;EMS_SERVER;;;0;AUTO;;;11.02.2015 11:31:43;;";
        System.out.println ( "\n============= regexp : "+regexp );
        b = str.matches ( regexp );
        System.out.println ( "-- "+str );
        System.out.println ( "-- result : "+b);

    }

}
