package string.regexc;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Строковые регулярные выражения.
 *
 * Результат:
 * true
 * 0
 * 8
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.11.2010 13:45:31
 */
public class Regexp1
{
    public static void main ( String[] args )
    {
        Pattern pattern = Pattern.compile("a.*string");
        Matcher matcher = pattern.matcher("a string");

        boolean didMatch = matcher.matches();
        System.out.println(didMatch);

        int patternStartIndex = matcher.start();
        System.out.println(patternStartIndex);

        int patternEndIndex = matcher.end();
        System.out.println(patternEndIndex);
    }

}
