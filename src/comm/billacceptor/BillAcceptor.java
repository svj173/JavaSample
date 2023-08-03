package comm.billacceptor;

import com.svj.utils.string.Conversion;
import com.svj.xml.TreeObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import java.io.*;
import java.util.Enumeration;

/**
 * <BR> Драйвер купюроприемника, подключенного по  RS232 (COM-port).
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 15:18:08
 */
public class BillAcceptor
{
    private static Logger logger = LogManager.getFormatterLogger ( BillAcceptor.class );

    private static BillAcceptor acceptor = new BillAcceptor ();

    private InputStream     inputStream;
    private OutputStream    outputStream;
    private SerialPort      serialPort;
    //private Thread          readThread;


    private BillAcceptor ()
    {
    }

    public static BillAcceptor getInstance ()
    {
        return acceptor;
    }

    /**
     * Инициализирует программу
     */
    public void init ( TreeObject config ) throws Exception
    {
        String str, portName;
        int portSpeed, portParity, portBits, portStopBit;
        CommPortIdentifier portId;

        logger.debug ( "Start" );

        portName = config.getString ( "port" );    // COM1, 9600,E,8,1
        str = config.getString ( "speed" );
        portSpeed = Integer.parseInt ( str );
        // parity. parity_even = 2
        str = config.getString ( "parity" );
        portParity = Integer.parseInt ( str );
        str = config.getString ( "bits" );
        portBits = Integer.parseInt ( str );
        str = config.getString ( "stop_bit" );
        portStopBit = Integer.parseInt ( str );

        logger.debug ( "Get ports list" );
        Enumeration portList;
        portList = CommPortIdentifier.getPortIdentifiers ();
        logger.debug ( "portList = " + portList );
        while ( portList.hasMoreElements () ) {
            portId = ( CommPortIdentifier ) portList.nextElement ();
            logger.debug ( "Port: Name = " + portId.getName () + ", Type = " + portId.getPortType () );
        }

        // Взять порт
        logger.debug ( "GET port. portName = '" + portName + "'" );
        portId = CommPortIdentifier.getPortIdentifier ( portName );

        // Открывает порт. Возвращает объект типа CommPort. параметры:
        // -	appname - Имя приложения вызывающего данный метод. (произвольная строка)
        // -	timeout - время в милисекундах, в течение которого, блокируется доступ к порту для его открытия.
        // Throws: PortInUseException если порт используется другим приложением.
        serialPort = ( SerialPort ) portId.open ( "BillAcceptor", 2000 );

        // Если порт не поддерживает посылку данных то данный метод вернет null.
        outputStream = serialPort.getOutputStream ();
        if ( outputStream == null ) {
            logger.error ( "outputStream == null" );
        }
        //outputStream.write ( 2 );
        // Если порт не поддерживает получение данных то данный метод вернет null.
        inputStream = serialPort.getInputStream ();
        if ( inputStream == null ) {
            logger.error ( "inputStream == null" );
        }
        logger.debug ( "inputStream = " + inputStream );

        // Слушатель
        PortListener pl = new PortListener ();
        serialPort.addEventListener ( pl );

        // Установить режим - сообщать если будут получены данные от порта
        serialPort.notifyOnDataAvailable ( true );

        // устанавливаем параметры порта
        logger.debug ( "SET port. portName = '" + portName + "', portSpeed = " + portSpeed
                + ", portBits = " + portBits + ", portStopBit = " + portStopBit
                + ", portParity = " + portParity );
        serialPort.setSerialPortParams ( portSpeed, portBits, portStopBit, portParity );

        logger.debug ( "Finish" );

    }

    public void sendCommand ( int command )
    {
        logger.debug ( "Send '" + command + "'" );
        //
        try
        {
            outputStream.write ( command );
            outputStream.flush ();
        } catch ( IOException e ) {
            logger.error ( "Send command '" + command + "' to BA error. ", e );
        }
    }

    public void readData()
    {
        int bb, bn;
        //String  str;

        logger.debug ( "Start. inputStream = " + inputStream );

        if ( inputStream == null )
        {
            logger.error ( "inputStream == null" );
            return;
        }

        // Читать данные
        try
        {
            while ( inputStream.available() > 0 )
            {
                //numBytes = inputStream.read ( readBuffer );
                bb = inputStream.read ();
                logger.debug ( "Read date = " + bb + " -- " + Conversion.byteToHexString ( (byte)bb ) );

                switch ( bb )
                {
                    case 0x80:
                        // Питание включено - Отправить 0х02
                        //logger.debug ( "Read data = " + getData() );   // -> 8F
                        sendCommand ( 0x02 );
                        break;

                    case 0x8F:
                        // Отправить 0х02
                        sendCommand ( 0x02 );
                        break;

                    case 0x81:
                        // Получена купюра - читать ее номер
                        boolean bget    = true;
                        bn = inputStream.read ();
                        logger.debug ( "Read Bill number = " + bn );
                        switch ( bn )
                        {
                            case 0x40:
                                // 10 ryb
                                logger.debug ( "Get 10 ryb." );
                                break;
                            case 0x41:
                                // 10 ryb
                                logger.debug ( "Get 50 ryb." );
                                break;
                            case 0x42:
                                // 10 ryb
                                logger.debug ( "Get 100 ryb." );
                                break;
                            case 0x43:
                                // 10 ryb
                                logger.debug ( "Get 500 ryb." );
                                break;
                            case 0x44:
                                // 10 ryb
                                logger.debug ( "Get 1000 ryb." );
                                break;
                            default:
                                bget    = false;
                                logger.error ( "Unknow bill" );
                                break;
                        }
                        // Принять купюру
                        if ( bget ) sendCommand ( 0x02 );
                        else sendCommand ( 0x0F );    // НЕ принимать
                        break;

                    case 0x5E:
                        //logger.debug ( "Read data = " + getData() );
                        break;

                    case 0x13:
                        logger.debug ( "Read data = " + getData() );
                        break;

                        /*
                    case 0x0C:    // getData
                        while ( inputStream.available () > 0 )
                        {
                            bn = inputStream.read ();
                            logger.debug ( "Read date = " + bn );
                        }
                        break;
                        */

                    default:
                        logger.debug ( "Unknow data = " + bb );
                }
            }
            //str = Conversion.byteArrayToHexString ( readBuffer );
            //logger.debug ( "Read date = " + str );
        } catch ( IOException e ) {
            logger.error ( "Error.", e );
        }
    }

    private String getData () throws IOException
    {
        StringBuffer   result   = new StringBuffer ( 64 );
        int bb;
        while ( inputStream.available () > 0 )
        {
            bb = inputStream.read ();
            result.append ( bb );
            result.append ( ' ' );
            //logger.debug ( "Read date = " + bn );
        }
        return  result.toString ();
    }

    public static void main ( String[] args )
    {
        //BillAcceptor    ba;
        boolean bwork;
        int ic, radix;
        String  port, str;

        try
        {
            // logger
            // PropertyConfigurator.configure ( args[0] );

            port    = args[1];
            logger.debug ( "Port : " + port );
            //port    = "COM1";
            //port    = "/dev/ttyS1";

            // BillAcceptor
            TreeObject config   = new TreeObject ();
            config.setString ( "port", port );
            config.setString ( "speed", "9600" );
            config.setString ( "parity", "2" );
            config.setString ( "bits", "8" );
            config.setString ( "stop_bit", "1" );

            //ba  = new BillAcceptor ();
            BillAcceptor.getInstance().init ( config );

            bwork   = true;
            while ( bwork )
            {
                logger.debug ( "Command (help = -1) : " );

                InputStreamReader is  = new InputStreamReader ( System.in  );
                //StringReader    sr  = new StringReader ( System.in );
                BufferedReader br   = new BufferedReader ( is );
                str = br.readLine ();

                //ic  = System.in.read ();
                //ic  = 0;
                logger.debug ( "cmd = " + str );
                ic  = str.indexOf ( 'x');
                if ( ic >= 0 )
                {
                    radix   = 16;
                    str = str.substring ( ic+1 );
                }
                else    radix   = 10;
                ic  = Integer.parseInt ( str, radix );

                switch ( ic )
                {
                    case -1:
                        logger.debug ( "\n-1\t\thelp\n-100\texit\n>0\t\tcommand\n" +
                                " enable  - 0x3E\n" +
                                " disable - 0x5E\n" +
                                " reset   - 0x30\n" +
                                " getmode - 0x0C\n" );
                        break;
                    case -100:
                        bwork   = false;
                        break;
                    case -10:
                        for ( int i=0; i<256; i++ )
                        {
                            if ( i != 0x30 )   BillAcceptor.getInstance().sendCommand ( i );
                        }
                        break;
                    default:
                        if ( ic >= 0 )     BillAcceptor.getInstance().sendCommand ( ic );
                        break;
                }
            }
            logger.debug ( "Finish" );
        } catch ( Exception e ) {
            logger.error ( "Error", e );
            e.printStackTrace ();
        }
        System.exit ( 0 );
    }

}
