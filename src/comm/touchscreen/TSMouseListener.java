package comm.touchscreen;

import org.apache.logging.log4j.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * <BR> Для Теста. НЕ нужен.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.03.2006
 * <BR> Time: 12:44:45
 */
public class TSMouseListener  implements MouseListener
{
    private static Logger logger = LogManager.getFormatterLogger ( TSMouseListener.class );


    public void mouseClicked ( MouseEvent e )
    {
       logger.debug ( "Press " );
    }

    public void mousePressed ( MouseEvent e )
    {
        logger.debug ( "Press mouse" );

    }

    public void mouseReleased ( MouseEvent e )
    {
        logger.debug ( "Press " );

    }

    public void mouseEntered ( MouseEvent e )
    {
        //logger.debug ( "Press " );

    }

    public void mouseExited ( MouseEvent e )
    {
        //logger.debug ( "Press " );

    }

}
