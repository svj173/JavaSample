package string.pattern;


/**
 * Проверка e-mail
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.07.2011 14:13:55
 */
public class CheckEmail
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        values  = new String[] { "svj173@yahoo.com", "svj173@yahoo.nsk.com",  "svj_173@yahoo.nsk.com",  "svj.173@yahoo.nsk.com", "sss@ddd.rr",
                "a s@d.rr", "ss@ddd.r", "svj%ggg.ru", "3.Profile" };

        match   = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";    // + - 1 и более повторений

        System.out.println ( "match = " + match );

        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( value + " --- " + math );
        }
    }

}
