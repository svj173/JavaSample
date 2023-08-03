package method;



 import java.io.*;
 //import mm4j.*;

 interface Idiom {
   public void m(Object... a) throws NoSuchMethodException, IOException;
 }

 public class IdiomImpl implements Idiom {
   // Alternatives
   public void m(Integer a) {  }
   public void m(String a, Object b) throws IOException {  }
   public void m(Integer a, String b, Object c) {  }

   // Wrapper
   public void m(Object... args) throws NoSuchMethodException, IOException {
       /*
     try {
       mm.invoke(this, args);
     } catch(InvocationTargetException e) {
       Throwable target=e.getTargetException();
       if (target instanceof IOException)
         throw (IOException)target;
       else if (target instanceof RuntimeException)
         throw (RuntimeException)target;

       target.printStackTrace();
     } catch(IllegalAccessException e) {
       e.printStackTrace();
     }
     */
   }
   //private static MultiMethod mm=MultiMethod.getMultiMethod(IdiomImpl.class, "m");

   // Sample usage
   public static void main(String[] args) {
     try {
       Idiom t=new IdiomImpl();

       t.m(3);
       t.m("a", "b");
       t.m(1, "b", 3f);
     } catch(Exception e) {e.printStackTrace();}
   }

 }
