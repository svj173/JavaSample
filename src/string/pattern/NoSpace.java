package string.pattern;


/**
 * Запрещаем в строке наличие пробелов (и русских букв)
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.07.2011 17:13:55
 */
public class NoSpace
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        values  = new String[] { " 1", "ddd ff gg", "eee ", "01.Profile", "01. Profile", "3.Profile", "3. Profile", "13. Profile-02",
                                 "02.Профиль", "02. Профиль", "4.Профиль", "4. Профиль",
                                 "1.2.0.0", "1.2.0.0.0.0.0.0", "1.32.120.0" };

        //match   = "[^ ]*";
        //match   = "[^ а-яА-Я]*";  // * - 0 и более повторений
        match   = "[^ а-яА-Я]+";    // + - 1 и более повторений

        System.out.println ( "match = " + match );

        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( value + " --- " + math );
        }
    }

}