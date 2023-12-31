package string.regexc;


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
public class RegexpCircle
{
    public static void main ( String[] args )
    {
        boolean  b;
        String   regexp, str;
        String[] stext;

        // 1) может принимать значения [1..4094] либо unused
        //regexp = "^(unused|[1-9][0-9]{0,2}|[1-3][0-9]{3}|40[0-8][0-9]|409[0-4])$";
        //stext = new String[] { "1", "99", "512", "1999", "4009", "4094", "4098", "unused" };

        // 2) только цифры
        //regexp = "^[0-9]+$";
        //stext = new String[] { "1234", "Asd99d", "51-2", "1999.456", "Фыва4009", "409__4ю", "4098", "unused", "0", "01" };

        // 3) латинские буквы и цифры
        //regexp = "^[a-zA-Z0-9]+$";
        //stext = new String[] { "1234", "Asd99d", "51-2", "1999.456", "Фыва4009", "409__4ю", "4098", "unused", "0" };

        // 4) латинские буквы, цифры и некоторые символы
        //regexp = "^[a-zA-Z0-9#+ //._//-]+$";
        //stext = new String[] { "1234", "Asd99d", "51-2", "1999.456", "Фыва4009", "409__4ю", "4098", "unused", "0" };

        // 5) Домен - длина менее 253, латинские буквы и цифры, запрещенные символы
        //regexp = "(?!-|_|.*(__|--).*)[a-zA-Z0-9_\\-]{1,253}";
        //stext = new String[] { "1234", "", "Asd99d", "51-2", "1999.456", "Фыва4009", "409__4ю", "4098", "40--98", "40__98", "_unused", "-0" };

        // 6) Логин. от 1 до 64 символов (только латинские и символы точки, подчеркивание и тире).
        //regexp = "[\\w \\.-]{1,64}";;
        //stext = new String[] { "1234", "", "Asd99d", "ddd fff", "51-2", "1999.456", "Фыва4009", "409__4ю", "4098", "40--98", "40__98", "_unused", "-0" };

        //regexp    = "^[1-9][0-9]{0,16}";    // тоже самое
        //regexp    = "[1-9][0-9]{0,16}";     // от 1 до 17-ти 9.
        //regexp    = "[1-9][0-9]{0,7}";         // от 1 до 8-ми 9.
        //regexp    = "^(6[0-9]{1,15}|[1-9][0-9]{2,16})$";    // больше 600 ?
        //regexp  = "[1-1234567892]";    // только однозначные цифры = true

        /*
        stext = new String[] { "0", "1", "2", "59", "71", "99", "512", "1999", "4009", "4094", "4098", "1234567890", "32000000", "320000009", "99000001123457699", "unused" };

        stext = new String[] { "0", "1", "24", "59", "71", "99", "512", "723", "1999", "4009", "4094", "4098", "1234567890", "32000000", "320000009",
                 "99000001123457699", "unused" };
        regexp = "1[0-9]|[2-9][0-9]|[1-9][0-9][0-9]|1000";
        */

        stext = new String[] {"https://www.example.com", "http://www.example.com", "www.example.com",
                 "example.com", "http://blog.example.com", "http://www.example.com/product/aa",
                 "http://www.example.com/products?id=1&page=2", "http://www.example.com#up",
                 "http://255.255.255.255", "255.255.255.255",
                 "http://invalid.com/perl.cgi?key= | http://web-site.com/cgi-bin/perl.cgi?key1=value1&key2",
                 "http://www.site.com:8008", "mongodb://123.1.1.1:27317/netconf222", "mongodb://123.1.1.1/",
                 "mongodb://localhost:27017/netconf", "mongodb://dd.ee.rr:27017/netconf",
                 "mongodb://localhost/", "https://net.tutsplus.com/about"};

        //regexp = "(mongodb://)(?:\\([-A-Z0-9+&@#/%=~_|$?!:,.]*\\)|[-A-Z0-9+&@#/%=~_|$?!:,.])*(?:\\"
        //        + "([-A-Z0-9+&@#/%=~_|$?!:,.]*\\)|[A-Z0-9+&@#/%=~_|$])";

        regexp = "(mongodb://)(?:\\([-A-Za-z0-9+&@#/%=~_|$?!:,.]*\\)|[-A-Za-z0-9+&@#/%=~_|$?!:,.])*(?:\\("
                + "[-A-Za-z0-9+&@#/%=~_|$?!:,.]*\\)|[A-Za-z0-9+&@#/%=~_|$])";

        //pattern = Pattern.compile(regexp);
        System.out.println("regexp : " + regexp );
        for ( String text : stext )
        {
            b = text.matches ( regexp );
            System.out.println ( "-- '"+text+"' : "+b);
        }

        //regexp = "^(6[0-9]{1,15}|[1-9][0-9]{2,16})$";     // 60..69, 100..
        //regexp = "^[1-9][0-9]{0,16}";     // от 1 ...
        //regexp = "^(6[0-9]{1,2}|7[0-9]{1,2}|8[0-9]{1,2}|9[0-9]{1,2}|[1-9][0-9]{2,16})$";     // от 1 ...

        /*
        // - от 1048576 и выше
        regexp = "^([2-9]\\d{6,31}|1[1-9]\\d{5,30}|10[5-9]\\d{4,29}|1049\\d{3,28}|1048[6-9]\\d{2,27}|10485[8-9]\\d{1,26}|104857[6-9]\\d{0,25}|[1-9]\\d{7,31})$";

        for ( int i=0; i<200; i++ )
        {
            str = Integer.toString ( i );
            b = str.matches ( regexp );
            System.out.println ( "-- '"+str+"' : "+b);
        }
        */
    }

}
