package svj.queue;

import tools.DumpTools;

/**
 * Взять из набора чисеол наименьшее.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.07.2018 16:25
 */
public class GetMinNumber
{
    public static void main(String[] args) {
        GetMinNumber manager;
        Integer[] array = new Integer[] { 217, 34, 67, 2036, 12, 5, 45 };

        System.out.println("array = " + DumpTools.printArray(array));

        manager = new GetMinNumber ();
        int result = manager.handle(array);

        System.out.println("\nresult = " + result);
    }

    private int handle(Integer[] array) {
        int result;

        result = Integer.MAX_VALUE;
        for ( int i : array )
        {
            //if ( i < result )  result = i;
            if ( result > i )  result = i;
        }

        return result;
    }
}
