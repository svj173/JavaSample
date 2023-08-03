package svj.classloader.v2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * To create a custom class loader, we will create a class that will extend ClassLoader.
 * There is a method findClass() that must be overridden.
 * Create a method that will load your given class from the class path.
 * In our case we have created the method loadClassData() that will return byte[].
 * This byte array will be returned as Class by defineClass() method.
 * <p>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.05.2019 16:24
 */
public class CustomClassLoaderDemo extends ClassLoader {
    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        //read class
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/") + ".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        //write into byte
        int len = 0;
        try {
            while ((len = is.read()) != -1) {
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convert into byte array
        return byteSt.toByteArray();
    }

}

