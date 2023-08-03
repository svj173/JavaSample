package comm.billacceptor;

import org.apache.logging.log4j.*;

import javax.comm.SerialPortEventListener;
import javax.comm.SerialPortEvent;
import java.io.IOException;

/**
 * <BR> Слушатель СОМ порта.
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 16:32:11
 */
public class PortListener   implements SerialPortEventListener
{
    private static Logger logger = LogManager.getFormatterLogger ( PortListener.class );

    public void serialEvent ( SerialPortEvent event )
    {
        int type    = event.getEventType ();
        logger.debug ( "Start. Read event type = " + type );
        switch ( type )
        {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;

            case SerialPortEvent.DATA_AVAILABLE:
                BillAcceptor.getInstance().readData();
                break;

            default:
                logger.error ( "Unknow event type = " + type );
        }
        logger.debug ( "Finish" );
    }

}
