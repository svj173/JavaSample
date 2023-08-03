package test.as.server;

import com.svj.utils.socket.SocketHandler;
import com.svj.utils.SvjException;
import org.apache.logging.log4j.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Обработчик входящих сокетов.
 * User: svj
 * Date: 16.02.2007
 * Time: 15:28:34
 */
public class Handler  extends SocketHandler
{
    private static Logger logger = LogManager.getFormatterLogger ( Handler.class );

    private ObjectInputStream ois;


    public void init ()
    {
    }

    /**
     * Обработка сокета. При выходе из данного метода SocketHandler сам закрывает сокет.
     * Возможно, Throwable здесь обрабатывать и не надо - вверху все ловится.
     * @param socket
     */
    public void handle ( Socket socket )
    {
        Object              request, response;
        //GZIPInputStream     gzipin;

        //logger.debug ( "Start" );

        try
        {
            ois     = new ObjectInputStream (socket.getInputStream());

            // Прочитать обьект.
            request  = ois.readObject();
            logger.info ( "request = " + request );

            // Обработать обьект
            // Взять соотвествующий сервис. Вызвать сервис. Получить ответ.
            response   = ServiceManager.getInstance().processService ( request );

            logger.info ( "response = " + response );

            // Выдать в сокет ответ.
            sendObject ( socket, response );

        } catch ( Throwable ex )
        {
            logger.error ( "Socket handle error.", ex );
        } finally
        {
            //logger.debug ( "Finish" );
        }
    }

    private void sendObject ( Socket socket, Object object ) throws SvjException
    {
        ObjectOutputStream  oos;

        //logger.debug ( "Start" );
        //logger.debug ( "Send object = " + object );
        // write to client
        try
        {
            oos     = new  ObjectOutputStream (socket.getOutputStream());
            oos.writeObject(object);
            oos.flush ();
        } catch ( Exception e )        {
            logger.error ( "Send object error.", e );
            throw new SvjException("Send object error:" + e.getMessage() );
        } finally
        {
            //logger.debug ( "Finish" );
        }
    }


}
