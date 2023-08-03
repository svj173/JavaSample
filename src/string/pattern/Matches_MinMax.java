package string.pattern;


/**
 * Создаем целочисленные ограничения - мин, макс.
 * <BR/> Генератор правил: http://utilitymill.com/utility/Regex_For_Range
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.06.2011 17:13:55
 */
public class Matches_MinMax
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        values  = new String[] { "01", "60", "101", "62", "120000",
                                 "059", "2147483647", "3000000000", "3000000001",
                                 "-10", "0124", "4" };

        // от 60 до инт макс (2147483647 - округлили до 3000000000 для простоты)

        //match   = "\\b([6-9][0-9]|[1-9][0-9]{2,8}|[12][0-9]{9}|3000000000)\\b";    // первые нули (0124 - false)
        match   = "0*([6-9][0-9]|[1-9][0-9]{2,8}|[12][0-9]{9}|3000000000)";          // учет первых нулей (0124 - true)

        System.out.println ( "match = " + match );

        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( value + " --- " + math );
        }
    }

}
