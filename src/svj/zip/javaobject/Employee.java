package svj.zip.javaobject;

/**
 * <BR> Обьект - Служащий
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.06.2006
 * <BR> Time: 11:29:17
 */
import java.io.*;

public class Employee implements Serializable
{
   String name;
   int age;
   int salary;

   public Employee(String name, int age, int salary)
   {
      this.name = name;
      this.age = age;
      this.salary = salary;
   }

   public void print()
   {
      System.out.println("Record for: "+name);
      System.out.println("Name: "+name);
      System.out.println("Age: "+age);
      System.out.println("Salary: "+salary);
   }

}