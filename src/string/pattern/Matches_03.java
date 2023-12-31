package string.pattern;


/**
 * Запрещаем в строке наличие пробелов, русских букв и других символов.
 * <BR/> Но разрешаем использование скобок.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.07.2011 17:13:55
 */
public class Matches_03
{
    public static void main ( String[] args )
    {
        String      match;
        boolean     math;
        String[]    values;

        values  = new String[] { "OK_g3002-408001-29", "OK:g3002_Gty", "aaa.www-s1.ru:3030",
                "", "g3002-408001-(EL)29", "3294239048-efwf(333)3",
                "3294239048-efwf-333-быр",
                "test_!", "test_~", "test_`", "test_\"", "test_№", "test_#", "test_$", "test_\\", "test_|", "test_/", "test_,","test_^",
                "test_(", "test_)", "test_?", "test_*", "test_+", "test_[", "test_]", "test_'", "test_\""
        };

        // Спецсимволы: ? * + . [ ] ^             - но точку можно, а нельзя скобки
        // ^ - отрицание того что идет после нее
        // []+ - все это повторяется 1 или больше раз. (т.е. как минимум хоть раз да встретилось.
        //match   = "[^!~`\u0022№#$%&\\\\|/,{}()?*+^\\[\\] а-яА-Я]+";   // OK

        // Здесь запрещается пустая строка. 1 и более повторений
        //match   = "[^!~`\"№#$%&\\\\|/,{}()?*+^\\[\\] а-яА-Я]+";   // OK

        // 0 и более повторений
        //match   = "[^!~`\"№#$%&\\\\|/,{}()?*+^\\[\\] а-яА-Я]*";     // ОК

        // host - ALPHA, DIGIT, ".", "-", ":"  - либо отсутствует
        //match   = "[^!~`\"№#$%&\\\\|/,{}()?*+^\\[\\] а-яА-Я]*";     // ОК

        match   = "[^`№#\\\\|{}*\\[\\] а-яА-Я]*";     // ОК

        //  надо : ? * + [ ] ^ ( ) \
        // - ( - Ux0028
        // - ) - Ux0029
        // - ? - Ux003F
        // - * - Ux002a
        // - + - Ux002b
        // - ^ - Ux005e
        // - [ - Ux005b   - надо как \\[
        // - ] - Ux005d   - надо как \\]
        // - \ - Ux005c   - не проходит - надо как \\\\
        // - " - Ux0022
        // - & - Ux0026

        System.out.println ( "match = " + match );

        // Здесь TRUE - значит не найдены. FALSE - нашли
        for ( String value : values )
        {
            math    = value.matches ( match );
            System.out.println ( value + " --- " + math );
        }
    }

}
