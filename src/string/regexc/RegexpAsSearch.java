package string.regexc;


import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Поиск строк по шаблону.
 * В нашем случае:
 * - % - любой символ
 * - * - любые символы
 *
 * Matcher (функции поиска):
 * - matches() просто указывает, соответствует ли вся входная последовательность шаблону.
 * - start() указывает значение индекса в строке, где начинается соответствующая шаблону строка.
 * - end() указывает значение индекса в строке, где заканчивается соответствующая шаблону строка плюс единица.
 * - lookingAt() ищет соответствие шаблону подстроки.
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.11.2010 13:47:44
 */
public class RegexpAsSearch
{
    private Pattern pattern;


    public RegexpAsSearch ( String str )
    {
        System.out.println("-- pattern 1 = '"+str + "'" );
        str     = parsePattern ( str );
        System.out.println("-- pattern 2 = '"+str + "'" );
        pattern = Pattern.compile(str);
    }

    private String parsePattern ( String str )
    {
        String result;

        // Приводим к верхнему регистру - для поиска без учета регистра
        result  = str.toUpperCase ( Locale.getDefault() );
        // заменяем все точки на [.]
        result  = result.replace ( ".", "[.]" );
        // заменяем %  на точку
        result  = result.replace ( "%", "." );
        // заменяем все звездочки на  .*
        result  = result.replace ( "*", ".*" );

        return result;
    }

    private boolean find ( String name )
    {
        Matcher matcher;
        String  str;

        str     = name.toUpperCase ( Locale.getDefault() );
        matcher = pattern.matcher(str);

        return matcher.lookingAt();
    }

    public static void main ( String[] args )
    {
        RegexpAsSearch reg;
        String[] source;
        boolean find;
        String  patt;

        //patt    = ".*8.*";
        //patt    = ".*lte.*";
        //patt    = ".*[.].*";
        //patt    = "*-*";
        //patt    = "LTE*";
        //patt    = "*.*";
        patt    = "%t%-8*";

        source = new String[] { "LTE8", "LTE-8-st", "LTP-8-st", "elc-8-st", "lte", "LTE-20.26" };
        reg = new RegexpAsSearch ( patt );

        for ( String name : source )
        {
            find = reg.find(name);
            System.out.println("-- name = '"+name+"', find = " + find );
        }
    }

}
