package annotation.s3;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:03:34
 */
public class Foo
{
    @Test
    public static void m1 ()
    { }

    public static void m2 () { }

    @Test
    public static void m3 ()
    {
        throw new RuntimeException ( "Boom" );
    }

    public static void m4 () { }

    @Test
    public static void m5 ()
    { }

    public static void m6 () { }

    @Test
    public static void m7 ()
    {
        throw new RuntimeException ( "Crash" );
    }

    public static void m8 () { }
}

