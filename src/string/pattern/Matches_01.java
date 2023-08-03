package string.pattern;


/**
 * Шаблон - первые два символа - цифры, потом - точка, потом - символы с исп пробелов и точек
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.06.2011 17:13:55
 */
public class Matches_01
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        values  = new String[] { "01.Profile", "01. Profile", "3.Profile", "3. Profile", "13. Profile-02",
                                 "02.Профиль", "02. Профиль", "4.Профиль", "4. Профиль",
                                 "1.2.0.0", "1.2.0.0.0.0.0.0", "1.32.120.0" };

        //String pattern = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";
        //match   = "[0-9]{1,2}\\..{0,255}";
        //match   = "[0-9]{1,2}\\u002E[a-fA-Fа-яА-Я]+";
        //match   = "[0-9]{1,2}\\u002E";
        //match   = "([0-9]+\\u002E){1,10}";
        //match   = "([0-9]+\\u002E)+";
        //match   = "([0-9]+\\u002E){1,14}[0-9]+";

        //match   = "([0-9]+\\u002E)+[0-9]+";       // OK
        
        match   = "[0-9]{1,2}\\u002E[ \\u002D0-9a-zA-Zа-яА-Я]+";

        System.out.println ( "match = " + match );

        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( value + " --- " + math );
        }
    }

}
