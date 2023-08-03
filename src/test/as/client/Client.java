package test.as.client;

import com.svj.utils.NumberTools;
import com.svj.utils.SvjException;
import com.svj.utils.Utils;
import org.apache.logging.log4j.*;
import test.as.Request;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 16.02.2007
 * Time: 16:52:11
 * To change this template use File | Settings | File Templates.
 */
public class Client implements IClient
{
    private static Logger logger = LogManager.getFormatterLogger ( Client.class );

    private String host;
    private int port;



    public Client()
    {
    }

    public void init ( String host, int port ) throws Exception
    {
        this.host   = host;
        this.port   = port;
    }

    public Object remoteCall ( String serviceName, String methodName, Object[] params )
            throws Exception
    {
        Request request;
        Object  result;
        Socket  socket;
        int     num;

        // Создать сокет
        socket  = new Socket(host,port);
        // Если убрать логгер - возникает ошибка для первого сокета 
        logger.info("Create socket: " + socket );
        // Взять уникальный ИД
        num     = NumberManager.getInstance().getNumber();
        // сформирвоать обьект запроса
        request = new Request(num, serviceName, methodName, params );
        logger.info("Create Request: " + request );
        // Передать в сокет
        sendObject ( socket, request );
        // Прочитать ответ
        result  = readObject ( socket);
        // Закрыть сокет
        socket  = Utils.closeSocket(socket);
        logger.info("Get Response: " + result );
        return result;
    }

    private void sendObject ( Socket socket, Object object ) throws SvjException
    {
        ObjectOutputStream oos;

        //logger.debug ( "Start" );
        logger.info ( "Send object = " + object + ", socket = " + socket );
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

    private Object readObject ( Socket socket ) throws SvjException
    {
        Object result;

        logger.info ( "Start. socket = " + socket );
        try
        {
            ObjectInputStream ois     = new ObjectInputStream(socket.getInputStream());

            // Прочитать обьект.
            result  = ois.readObject();
        } catch ( EOFException e )        {
            result  = null;
        } catch ( Exception e )        {
            logger.error ( "Read object error.", e );
            throw new SvjException("Read object error: " + e.getMessage() );
        } finally
        {
            //logger.debug ( "Finish" );
        }
        return result;
    }

//=================================================================================
    
    public static void main ( String[] args )
    {
        String      str;
        int         port, i, ic;
        Client      client;

        System.out.println ( "Start" );
        if ( args.length == 0 )
        {
            System.out.println ( "Use: Client port" );
            System.exit ( 1 );
        }

        try
        {
            str     = args[0];
            port    = NumberTools.getInt ( str, "Invalid Port" );
            logger.info("Port = " + port );
            client  = new Client();
            client.init ( "localhost", port );

            ic  = 10;
            //ic  = 3;
            Thread[]  thread    = new Thread[ic];
            for ( i=0;i<ic;i++ )
            {
                thread[i]   = new Thread(new Caller(client));
                thread[i].start();
                logger.info("Run thread = " + i );
            }

            // Ожидать завершения
            for ( i=0;i<ic;i++ )
            {
                thread[i].join();
            }

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.out.println ( "Finish" );
        System.exit ( 0 );
    }


}
