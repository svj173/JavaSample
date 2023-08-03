package net.northbound;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 04.10.2014 15:22
 */
public class NbTestTools
{
    private static int allCounter, errCounter;

    public static void resetCounters ()
    {
        allCounter = 0;
        errCounter = 0;
    }

    public static void incAllCounter ()
    {
        allCounter++;
    }

    public static void incErrCounter ()
    {
        errCounter++;
    }

    public static int getAllCounter ()
    {
        return  allCounter;
    }

    public static int getErrCounter ()
    {
        return  errCounter;
    }

}
