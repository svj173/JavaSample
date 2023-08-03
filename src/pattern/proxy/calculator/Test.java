package pattern.proxy.calculator;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:55:55
 */
public class Test
{
       public static void main(String[] args)
       {
              CalculatorProxy cp = new CalculatorProxy();
               while(true)
                        System.out.println(cp.getResult());
       }
}
