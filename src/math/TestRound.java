package math;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 16.09.2010 13:56:04
 */
public class TestRound
{
    public static void main ( String[] args )
    {
        //
        float  f1, f2;
        int    r1;
        double d1, d2, dr;

        f1   = 9.2345f;
        f2   = 9.7345f;

        d1   = 9.1345d;
        d2   = 9.8345d;

        // Округление до ближайшего целого
        r1  = Math.round ( f1 );
        System.out.println ( "Math.round: ish = " + f1 + ", result = " + r1 );   // 9   
        r1  = Math.round ( f2 );
        System.out.println ( "Math.round: ish = " + f2 + ", result = " + r1 );   // 10

        // Округление вниз до ближайшего целого.
        r1  = (int) Math.floor ( f1 );
        System.out.println ( "Math.floor: ish = " + f1 + ", result = " + r1 );    // 9
        r1  = (int) Math.floor ( f2 );
        System.out.println ( "Math.floor: ish = " + f2 + ", result = " + r1 );    // 9

        // целое вверх
        dr = Math.ceil ( d1 );
        System.out.println ( "Math.ceil: ish = " + d1 + ", result = " + dr );     // 10.0
        dr = Math.ceil ( d2 );
        System.out.println ( "Math.ceil: ish = " + d2 + ", result = " + dr );     // 10.0

        // ближайшее целое
        dr = Math.rint ( d1 );
        System.out.println ( "Math.rint: ish = " + d1 + ", result = " + dr );     // 9.0
        dr = Math.rint ( d2 );
        System.out.println ( "Math.rint: ish = " + d2 + ", result = " + dr );     // 10.0
    }
    
}
