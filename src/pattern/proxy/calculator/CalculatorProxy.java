package pattern.proxy.calculator;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:57:11
 */
public class CalculatorProxy
{
    private Calculator calculator;
    public CalculatorProxy()
    {
           calculator = new Calculator();
           calculator.calculate();
    }
    public String getResult()
    {
          if (calculator.done)
                  return calculator.getResult();
          else return "Still calculating, sir";
    }
}
