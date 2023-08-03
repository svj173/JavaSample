package pattern.proxy.calculator;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:57:54
 */
public class Calculator
{
       public boolean done;
        private String result;
    
        public void calculate()
        {
              // супердолгие вычисления
               done = true;
        }
         public String getResult()
         {
                return result;
         }
}
