package math;


/**
 * Применение генератора случайных чисел  - целое число. От 0 до заданного значения.
 * <BR/>
 * <BR/>
 Generating 10 random integers in range 0..99.
 Generated : 44
 Generated : 81
 Generated : 69
 Generated : 31
 Generated : 10
 Generated : 64
 Generated : 74
 Generated : 57
 Generated : 56
 Generated : 93
 Done.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.06.2011 13:37:24
 */

import java.util.Random;

/**
 * Generate 10 random integers in the range 0..99.
 */
public final class RandomInteger
{

    public static final void main ( String... aArgs )
    {
        log ( "Generating 10 random integers in range 0..99." );

        //note a single Random object is reused here
        Random randomGenerator = new Random ();
        for ( int idx = 1; idx <= 10; ++idx )
        {
            int randomInt = randomGenerator.nextInt ( 100 );
            log ( "Generated : " + randomInt );
        }

        log ( "Done." );
    }

    private static void log ( String aMessage )
    {
        System.out.println ( aMessage );
    }

}
