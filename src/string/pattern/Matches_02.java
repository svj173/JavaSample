package string.pattern;


/**
 * Проверка МАС адреса
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.06.2011 17:13:55
 */
public class Matches_02
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        // 1) MAC
        //values  = new String[] { "02:00:2A:00:00:18", "02:00:2A:00:00:18" };
        //match   = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";

        // 2) Имена перемнных в Java
        values = new String[] {"STARTED", "started", "SS_1"};
        match = "^[a-z][a-zA-Z0-9]*$";

        System.out.println ( "match = " + match );

        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( "- " + value + " --- " + math );
        }
    }

}