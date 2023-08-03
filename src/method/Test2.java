package method;


import java.lang.reflect.Method;


/**
 * Проверка вызхова методов по рефлекшн. -- ОК.
 */
public class Test2
{
    public void m ( String a, Integer b )
    {
        System.out.println ( "m(String,Integer) with " + a.getClass().getName() + ", " + b.getClass().getName() );
    }

    public void m ( Object a, Integer b )
    {
        System.out.println ( "m(Object,Integer) with " +  a.getClass().getName() + ", " + b.getClass().getName() );
    }

    public void m ( String a, Object b )
    {
        System.out.println ( "m(String,Object) with " + a.getClass().getName() + ", " + b.getClass().getName() );
    }

    public void m ( Object a, Object b )
    {
        System.out.println ( "m(Object,Object) with " + a.getClass().getName() + ", " + b.getClass().getName() );
    }

    public static void main ( String[] args )
    {
        String strClass;
        Class procHandlerClass;
        Object obj;
        Method m;
        Class[] theArgTypes;

        try
        {
            /*
            Test2 t = new Test2();

            // Здесь вызывается именно "m ( String a, Object b )"  а не "m ( String a, Integer b )"
            theArgTypes = new Class[] { String.class, Object.class };
            procHandlerClass = t.getClass();
            m = procHandlerClass.getDeclaredMethod ( "m", theArgTypes );
            // Правильный вызов. Дергает none_static методы. Здесь первым параметром идет сам обьект а не его класс.
            m.invoke ( t, "a", 1 );
            // Только для static методов. Если в классе пишем NULL, то генерится NullPointerException, если метод - НЕ static. Работает только для static методов.
            //m.invoke ( null, "a", 1 );
            // Только для static методов. IllegalArgumentException: object is not an instance of declaring class
            //m.invoke ( m.getDeclaringClass(), "a", 1 );
            */
            
            //
            strClass            = "method.SimpleMethod";
            theArgTypes         = new Class[] { String.class, Integer.class };
            obj                 = Class.forName ( strClass ).newInstance ();
            procHandlerClass    = obj.getClass();
            m = procHandlerClass.getDeclaredMethod ( "process", theArgTypes );
            // Правильный вызов. Дергает none_static методы.
            m.invoke ( obj, "sss", 12 );

        } catch ( Exception e )     {
            e.printStackTrace ();
        }
    }

}
