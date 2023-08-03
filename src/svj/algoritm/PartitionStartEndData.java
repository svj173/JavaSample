package svj.algoritm;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Есть списко имен партиций. Вычисляить дату начала и дату конца.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.04.2016 16:12
 */
public class PartitionStartEndData
{
    public static void main ( String[] args )
    {
        List<String> partList;
        Date startPeriod, endPeriod;

        partList = new ArrayList<String> ();
        partList.add ( "p2015_11" );
        partList.add ( "p2015_12" );
        partList.add ( "p2016_01" );
        partList.add ( "p2016_02" );
        partList.add ( "p2016_03" );
        partList.add ( "p2016_04" );
        partList.add ( "p2016_05" );
        partList.add ( "p2016_06" );

        startPeriod = createStartForCsv ( partList );  // начало месяца

        endPeriod   = createEndForCsv ( partList );    // конец месяца

        System.out.println ( "startPeriod = "+startPeriod+"; endPeriod = "+ endPeriod );

    }

    private static Date createEndForCsv ( List<String> partList )
    {
        Date result;
        String name;
        Calendar calendar;
        int ic;

        // Берем последнюю запись.
        name    = partList.get ( partList.size() - 1 );

        calendar = createDate ( name );

        // Установить последнйи день данного месяца. И часы-секунды - по максимуму.
        ic = calendar.getActualMaximum ( Calendar.DAY_OF_MONTH );
        System.out.println ( "name = "+name+"; calendar = "+ calendar +"; maxDay = "+ic );

        calendar.set ( Calendar.DAY_OF_MONTH, ic );
        calendar.set ( Calendar.HOUR_OF_DAY, 23 );
        calendar.set ( Calendar.MINUTE, 59 );
        calendar.set ( Calendar.SECOND, 59 );
        calendar.set ( Calendar.MILLISECOND, 999 );

        return calendar.getTime();
    }

    private static Calendar createDate ( String partName )
    {
        Calendar calendar;
        Matcher matcher;
        Pattern partitionPattern;

        partitionPattern        = Pattern.compile("^p(\\d{4})_(\\d{2})$");
        matcher = partitionPattern.matcher(partName);
        if (!matcher.matches())
        {
            //throw new MessageException("Partition \"", latestPartitionName, "\" is not in correct format \"", partitionRegExpFormat, "\"");
        }
        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));

        calendar = Calendar.getInstance();
        // int dateOfMonth, int hourOfDay, int minute, int second
        calendar.set ( year, month-1, 1 );
        //result = calendar.getTime();

        return calendar;
    }

    private static Date createStartForCsv ( List<String> partList )
    {
        Date result;
        String name;
        Calendar calendar;

        // Берем последнюю запись.
        name    = partList.get ( 0 );

        calendar = createDate ( name );

        // Скинуть часы, секунды в 0
        calendar.set ( Calendar.HOUR_OF_DAY, 0 );
        calendar.set ( Calendar.MINUTE, 0 );
        calendar.set ( Calendar.SECOND, 0 );
        calendar.set ( Calendar.MILLISECOND, 0 );

        return calendar.getTime();
    }

}
