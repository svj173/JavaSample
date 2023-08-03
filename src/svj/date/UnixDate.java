package svj.date;

import tools.Convert;

import java.util.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.08.2021 14:24
 */
public class UnixDate {

    public static void main ( String[] args )
    {
        //UnixDate mng = new UnixDate();

        //long time = 1630387370L;       // 31.08.2021 12:22:50
        //Date date = new Date(time * 1000);

        long time = 1461811993308L;       // 28.04.2016 08:53:13

        Date date = new Date(time);

        String result = Convert.getRussianDateTime(date);
        System.out.println("time = " + time + "; date = " + result);

        System.out.println("equals(null) = " + !result.equals(null));

    }


}
