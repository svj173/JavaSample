package svj.date;

import java.time.LocalTime;

/**
 * <BR/>
 */
public class LocalTimeTest {

    public static void main(String[] args)
    {
        // Нельзя 2022-09-01 13:17:22 - т.к. здесь только время
        String[] dates = new String[] {"10:15:45", "00:00", "0:0"};

        for (String date : dates) {
            // create an LocalTime object
            LocalTime lt = LocalTime.parse(date);

            // print result
            System.out.println("- " + date + "; LocalTime : " + lt);
        }
    }

}
