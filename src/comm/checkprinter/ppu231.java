package comm.checkprinter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * <BR>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 04.07.2006
 * <BR> Time: 12:42:14
 */
public class ppu231     implements SerialPortEventListener
{
private static Logger logger = LogManager.getFormatterLogger ( ppu231.class );

private    InputStream     inputStream;
private    OutputStream    outputStream;
private    SerialPort      serialPort;


/* Инициализация устройства. */
public void init ( String name )
{
    String              str, portName;
    CommPortIdentifier  portId;
    int                 ic;
    int     portSpeed, portParity, portBits, portStopBit;

    logger.debug ( "Start" );

    portName    = "";

    try
    {
        portName    = "COM1";    // COM1, 9600,E,8,1
        portSpeed   = 9600;
        // parity. parity_even = 2
        portParity  = 2;
        portBits    = 8;
        portStopBit = 1;

        // --------- Инсталлировать порт ------------------

        portId          = CommPortIdentifier.getPortIdentifier(portName);
        // Открывает порт. Возвращает объект типа CommPort. параметры:
        // -	appname - Имя приложения вызывающего данный метод. (произвольная строка)
        // -	timeout - время в милисекундах, в течение которого, блокируется доступ к порту для его открытия.
        serialPort    = ( SerialPort ) portId.open ( "CheckPrinter", 2000 );

        // Если порт не поддерживает посылку данных то данный метод вернет null.
        outputStream    = serialPort.getOutputStream ();
        if ( outputStream == null )
        {
            logger.error ( "outputStream == null" );
            return;
        }
        // Если порт не поддерживает получение данных то данный метод вернет null.

        inputStream     = serialPort.getInputStream ();
        if ( inputStream == null )
        {
            logger.error ( "inputStream == null" );
            return;
        }


        // Слушатель
        serialPort.addEventListener ( this );

        // Установить режим - сообщать если будут получены данные от порта
        //System.out.println("notifyOnDataAvailable");
        serialPort.notifyOnDataAvailable( true );

        // TODO Это костыль, чтоб не вываливался эксепшн при установке параметров порта
        System.out.println("");

        //System.out.println("setSerialPortParams");
        serialPort.setSerialPortParams ( portSpeed, portBits, portStopBit, portParity );

    } catch ( Throwable e ) {
        logger.error ( "ERROR.", e );
    }
}

    public void serialEvent ( SerialPortEvent serialPortEvent )
    {
        logger.debug ( "Event = " + serialPortEvent );

        switch ( serialPortEvent.getEventType () )
        {
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[1];

                try
                {
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                    }

                } catch ( IOException e) {}

                switch (readBuffer[0]) {
                    case 1:
                        logger.debug ( "Paper near end..." );
                        break;
                }
                break;
        }
    }

    private void printTest ()
    {
        StringBuffer    text;
        logger.debug ( "Start" );

        text    = new StringBuffer ( 128 );
        text.append ( "Proverka");
        try
        {
            // ---------- Распечатать данные --------------
            logger.debug ( "\n"+text );

            outputStream.write(text.toString().getBytes());
            outputStream.write ( 0x0C );
            outputStream.write ( 0x0C );

            // ---------- Распечатать данные --------------

            //*
            // init printer (clear buffer) - НЕ надо
            //outputStream.write ( 0x1B );
            //outputStream.write ( 0x40 );

            // Set STANDARD PAGE mode   - ESC S
            outputStream.write ( 0x1B );
            outputStream.write ( 0x53 );


            // Set PC866  - ESC t 7 (17)
            outputStream.write ( 0x1B );
            outputStream.write ( 0x74 );
            outputStream.write ( 7 );

            outputStream.write ( 0x0C );

            //outputStream.write(text.toString().getBytes("Cp866"));
            outputStream.write(text.toString().getBytes());

            // протянуть бумагу
            outputStream.write ( 0x1B );
            outputStream.write ( 0x4A );
            outputStream.write ( 150 );

            // Cut - ESC i  -- Обрезал за 2 линии до последней выведенной строки
            outputStream.write ( 0x1B );
            outputStream.write ( 0x69 );

            outputStream.write ( 0x0C );

            // Запрос статуса датчика конца бумаги
            //outputStream.write ( 0x1B );
            //outputStream.write ( 0x76 );
            //*/

            logger.debug ( "Finish" );

        } catch ( Exception e ) {
            logger.error ( "Error", e );
            // check print error - ничего не делать, если мы разрешаем работать без чеков.
            // Выключить устройство и все функции, связанные с ним.
            //DeviceManager.getInstance().setWrongDevice ( getName () );
        } catch ( Throwable er )    {
            logger.error ( "Error", er );
        }
    }

    public static void main ( String[] args )
    {
        StringBuffer    text;
        ppu231  printer;

        try
        {
            // logger
            // PropertyConfigurator.configure ( args[0] );

            //logger.debug ( "Ports:\n" + Util.portList() );

            printer = new ppu231 ();
            printer.init ( "CP" );
            printer.printTest();

        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.exit ( 10 );
    }


}

