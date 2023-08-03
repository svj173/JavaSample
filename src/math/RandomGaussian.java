package math;


/**
 * Generate pseudo-random floating point values, with an
 * approximately Gaussian (normal) distribution.
 * <p/>
 * Many physical measurements have an approximately Gaussian
 * distribution; this provides a way of simulating such values.
 * <BR/>
 * <BR/> Пример:
Generated : 99.38221153454624
Generated : 100.95717075067498
Generated : 106.78740794978813
Generated : 105.57315286730545
Generated : 97.35077643206589
Generated : 92.56233774920052
Generated : 98.29311772993057
Generated : 102.04954815575822
Generated : 104.88458607780176
Generated : 97.11126014402141
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.06.2011 13:41:06
 */

import java.util.Random;

public final class RandomGaussian
{

    public static void main ( String... aArgs )
    {
        RandomGaussian gaussian = new RandomGaussian ();
        double MEAN = 100.0f;
        double VARIANCE = 5.0f;
        for ( int idx = 1; idx <= 10; ++idx )
        {
            log ( "Generated : " + gaussian.getGaussian ( MEAN, VARIANCE ) );
        }
    }

    private Random fRandom = new Random ();

    private double getGaussian ( double aMean, double aVariance )
    {
        return aMean + fRandom.nextGaussian () * aVariance;
    }

    private static void log ( Object aMsg )
    {
        System.out.println ( String.valueOf ( aMsg ) );
    }
}
