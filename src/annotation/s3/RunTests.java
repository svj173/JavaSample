package annotation.s3;


/**
 * Результат
 * <BR/>
 $ java RunTests Foo
 Test public static void Foo.m3() failed: java.lang.RuntimeException: Boom
 Test public static void Foo.m7() failed: java.lang.RuntimeException: Crash
 Passed: 2, Failed 2

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:04:10
 */

import java.lang.reflect.*;

public class RunTests
{
    public static void main ( String[] args ) throws Exception
    {
        int passed = 0, failed = 0;
        for ( Method m : Class.forName ( args[ 0 ] ).getMethods () )
        {
            if ( m.isAnnotationPresent ( Test.class ) )
            {
                try
                {
                    m.invoke ( null );
                    passed++;
                } catch ( Throwable ex )    {
                    System.out.printf ( "Test %s failed: %s %n", m, ex.getCause () );
                    failed++;
                }
            }
        }
        System.out.printf ( "Passed: %d, Failed %d%n", passed, failed );
    }
}

