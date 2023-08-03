package method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Vector;

// Tries to use the reflection APIs to invoke Enumeration.hasMoreElements(),
// and fails miserably. Sample output:
//
// Получаем результат:
// >java test
//      java.lang.IllegalAccessException: java/util/VectorEnumerator
//              at test.main(test.java:46)

class test
{
    final static boolean HACK = false;

    public static void main(String[] argc)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {

        Vector myVector = new Vector();
        //foo myFoo = new foo();

        Enumeration myEnum;
        if (HACK)
            myEnum = new enumerationWrapper(myVector.elements());
        else
            myEnum = myVector.elements();

        // Create the Vector & Enumeration
        // If HACK, then we wrap it with out (accessible) Enumeration

        Class[] theArgTypes = new Class[0];
        // hasMoreElements() takes 0 parameters

        Class enumClass = myEnum.getClass();
        // get the class

        Method hasMoreElements = enumClass.getDeclaredMethod("hasMoreElements", theArgTypes);
        // create the Method from the class & param list

        Object[] theArgs = new Object[0];
        // create the parameters (there are none of them)

        Object result = hasMoreElements.invoke(myEnum, theArgs);
        // try to invoke Enumeration.hasMoreElements()

        System.out.println("m_Method.invoke(myEnum, theArgs); == " +  result );
        // we never get here unless HACK == true
    }

    public final static class enumerationWrapper implements Enumeration
    {
        Enumeration java_util_VectorEnumerator;
        // The Enumeration we're wrapping

        public enumerationWrapper(Enumeration
                java_util_VectorEnumerator) {
            this.java_util_VectorEnumerator =
                    java_util_VectorEnumerator;
        }

        public boolean hasMoreElements() {
            return java_util_VectorEnumerator.hasMoreElements();
        }

        public Object nextElement() {
            return java_util_VectorEnumerator.nextElement();

        }
    }

}
