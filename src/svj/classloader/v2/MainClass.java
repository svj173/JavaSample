package svj.classloader.v2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create main class to test Custom ClassLoader
 * Now its time to test our custom class loader. Using reflection API, we will be able to read our test class with
 * the help of our custom class loader as below. 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.05.2019 16:51
 */
public class MainClass {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                     NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

    	      CustomClassLoaderDemo loader = new CustomClassLoaderDemo();
                  Class<?> c = loader.findClass("svj.classloader.v2.Test");
                  Object ob = c.newInstance();
                  Method md = c.getMethod("show");
                  md.invoke(ob);
           }
}
