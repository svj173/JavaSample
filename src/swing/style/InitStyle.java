package swing.style;

import com.svj.utils.string.StringTools;
import com.svj.utils.swing.SwingTools;
import com.svj.utils.NumberTools;
import com.svj.xml.TreeObject;

import javax.swing.text.StyleContext;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.Properties;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.*;

/**
 * Попытка создавать стиль, исходя из его строкового представления.
 * Примерный вид стиля (не все опции):
 *
 {family=arial,FirstLineIndent=3.0,Alignment=0,styleName=unknow,size=10,
 foreground=java.awt.Color[r=255,g=0,b=51],}

 * Исп алгоритм - парсим на пары и пытаемся по ключу вытащить сообвтествующий атрибут.
 *
 * <BR> User: Zhiganov
 * <BR> Date: 05.12.2007
 * <BR> Time: 14:50:11
 */
public class InitStyle
{
    private static String inputText =
            "{family=arial,FirstLineIndent=3.0,Alignment=0,styleName=unknow,size=10," +
            " foreground=java.awt.Color[r=255,g=0,b=51],}";


    /**
     * Создание стиля из текстовых свойств стиля.
     *
     * @param sattr
     * @return
     */
    private SimpleAttributeSet createStyleFromProperties ( Properties sattr )
    {
        SimpleAttributeSet result;
        int ic;
        float   fc;
        Color color;
        String  str;

        result  = new SimpleAttributeSet ();

        //logger.debug ( "attr = " + sattr );
        str     = sattr.getProperty ( "styleName" );
        if ( str != null )
            result.addAttribute ( "styleName" , str );

        str     = sattr.getProperty ( "family" );
        if ( str != null )
            StyleConstants.setFontFamily ( result, str );

        str     = sattr.getProperty ( "size" );
        if ( str != null )
        {
            try
            {
                ic      = Integer.parseInt ( str );
            } catch ( Exception e )            {
                ic      = 10;
            }
            StyleConstants.setFontSize ( result, ic );
        }

        str     = sattr.getProperty ( "foreground" );
        if ( str != null )
        {
            // java.awt.Color[r=255/g=0/b=51]
            color   = createColor ( str );
            //logger.debug ( "colorName = " + str + ", color = " + color );
            StyleConstants.setForeground ( result, color );
        }

        str     = sattr.getProperty ( "Alignment" );
        if ( str != null )
        {
            try
            {
                ic      = Integer.parseInt ( str );
            } catch ( Exception e )            {
                ic      = StyleConstants.ALIGN_LEFT;
            }
            StyleConstants.setAlignment ( result, ic );
        }

        // Начальный отступ абзаца если есть.
        str     = sattr.getProperty ( "FirstLineIndent" );
        if ( str != null )
        {
            fc = Float.parseFloat ( str );
            StyleConstants.setFirstLineIndent ( result, fc );
        }

        // TODO
        /*
        str     = sattr.getProperty ( "italic" );
        if ( str != null && str.equalsIgnoreCase ( WCons.YES ))
        {
            StyleConstants.setItalic ( result, true );
        }

        str     = sattr.getProperty ( "bold" );
        if ( str != null && str.equalsIgnoreCase ( WCons.YES ))
        {
            StyleConstants.setBold ( result, true );
        }
        */


        // Левый отступ всего текста если есть.
        str     = sattr.getProperty ( "left_margin" );
        if ( str != null )
        {
            ic = Integer.parseInt ( str );
            StyleConstants.setLeftIndent ( result, ic );
        }

        // Правый отступ абзаца если есть.
        str     = sattr.getProperty ( "right_margin" );
        if ( str != null )
        {
            ic = Integer.parseInt ( str );
            StyleConstants.setRightIndent ( result, ic );
        }

        return result;
    }

    /**
     * Исходная строка описания цвета: java.awt.Color[r=255/g=0/b=51]
     * @param color
     * @return
     */
    private Color createColor(String color)
    {
        Color   result;
        String  str;
        BufferedReader br;
        Enumeration en;
        Properties prop   = new Properties ( );
        int r,g,b;

        str = color.replace("java.awt.Color[","");
        str = str.replace('/', '\n' );
        str = str.trim();
        str = str.substring(0, str.length()-1);

        br  = StringTools.createReader(str);
        try {
            prop.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Prop = " + prop );

        str = prop.getProperty("r");
        r   = NumberTools.getInt (str, 0);
        str = prop.getProperty("g");
        g   = NumberTools.getInt (str, 0);
        str = prop.getProperty("b");
        b   = NumberTools.getInt (str, 0);

        result  = new Color(r,g,b);

        return result;
    }


    public static void main(String[] args)
    {
        SimpleAttributeSet style;

        try
        {
            //System.out.println("Prop = " + result );

            InitStyle is;
            is      = new InitStyle();
            style   = is.createStyle(inputText);
            System.out.println("Create style = " + style );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public SimpleAttributeSet createStyle(String styleText)
    {
        SimpleAttributeSet result;
        Properties prop;

        prop    = createProperties(styleText);
        result  = createStyleFromProperties(prop);

        return result;
    }

    private Properties createProperties(String styleText)
    {
        BufferedReader br;
        Properties result;
        String  str;

        // Парсим текст на пары
        // - for Color
        str = styleText.replace(",g", "/g" );
        str = str.replace(",b", "/b" );

        str = str.replace(',', '\n' );

        // Удаляем крайние фигурные скобки
        str = str.substring(1,str.length()-1);

        br  = StringTools.createReader(str);

        result   = new Properties ();
        try
        {
            result.load(br);
        } catch (Exception e) {
            result   = new Properties ();
            //e.printStackTrace();
        }

        return result;
    }

}
