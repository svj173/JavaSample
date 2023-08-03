package comm.touchscreen;

import org.apache.logging.log4j.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <BR> Таймер ожидания отпускания клавиши клиентом на сенсорном экране.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 20.03.2006
 * <BR> Time: 11:24:30
 */
public class TimerListener  implements ActionListener
{
    private static Logger logger = LogManager.getFormatterLogger ( TimerListener.class );

    private int counter;

    private int step;
    private int max;


    public TimerListener ( int step, int max )
    {
        this.step = step;
        this.max = max;
    }

    public void clearCounter()
    {
        counter = 0;
    }

    public void actionPerformed ( ActionEvent e )
    {
        logger.debug ( "Start. counter = " + counter + ", max = " + max );
        counter = counter + step;
        if ( counter > max )
        {
            // Прошло время после отпускания кнопки клиентом. Запустить процесс обработки нажатия.
            TouchScreen.getInstance().processPressed();
            clearCounter();
        }
        logger.debug ( "Finish. " );
    }

}
