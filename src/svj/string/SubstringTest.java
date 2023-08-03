package svj.string;

/**
 * Выделяем ИД дома
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.06.2021 14:39
 */
public class SubstringTest {

    public static final String ID_ANCHOR = "_";
    public static final String ZERO_HOUSE = "ZERO_HOUSE";

    public static void main ( String[] args )
    {
        SubstringTest mng = new SubstringTest();

        mng.test();

    }

    /*
     * Примеры исходных ИД
     * 2018-09-14_1
     * 2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef
     * 2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef|1
     * 2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef_light
     * 2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef_light|2
     * ...
     * 2021-06-01_ZERO_HOUSE
     * 2021-06-01_ZERO_HOUSE|1
     * 2021-06-01_ZERO_HOUSE_GUARD
     * 2021-06-01_ZERO_HOUSE_GUARD|1

     */
    private void test() {
        String      id;
        boolean     math;
        String[]    values;

        values  = new String[] { "2018-09-14_1", "2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef",
                "2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef_light", "2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef_light|2",
                "2018-09-14_3bd08969-d904-43dc-b1d6-904d266d31ef|1", "2021-06-01_ZERO_HOUSE",
                "2021-06-01_ZERO_HOUSE|1", "2021-06-01_ZERO_HOUSE_GUARD",
                                 "2021-06-01_ZERO_HOUSE_GUARD|1" };


        for ( String value : values )
        {
            System.out.println ( value );
            id    = parseHouseId ( value );
            System.out.println ( "\t---\t" + id );
            System.out.println ( "\t---\t" + idHasSection(value) );
        }
    }

    private String parseHouseId(String str) {
        if (org.apache.logging.log4j.util.Strings.isBlank(str)) {
            return "";
        }

        String result;
        int start = str.indexOf(ID_ANCHOR);
        start++;

        int end;
        if (str.contains(ZERO_HOUSE)) {
            // В ИД дома - дополнительное подчеркивание. Учитываем это.
            // Для учета ZERO_HOUSE второй символ подчеркивания
            end = str.indexOf(ID_ANCHOR, start + ZERO_HOUSE.length());
        } else {
            end = str.indexOf(ID_ANCHOR, start);
        }

        System.out.println ( "start = " + start + "; end = " + end );

        if (end < 0) {
            result = str.substring(start);
        } else {
            result = str.substring(start, end);
        }

        return result;
    }

    public static boolean idHasSection(String idLog) {
        if (idLog == null) {
            return false;
        } else {
            long count = idLog.codePoints().filter(ch -> ch == '_').count();
            if (idLog.contains(ZERO_HOUSE)) {
                // В ИД дома - дополнительное подчеркивание. Учитываем это.
                // Флаг наличия ИД сессии - больше двух подчеркиваний
                return count > 2;
            } else {
                // Обычные ИД дома (не содержат внутри символы подчеркивания)
                return count > 1;
            }
        }
    }

}
