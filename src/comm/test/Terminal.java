package comm.test;

/**
 * <BR>
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 12:16:06
 */

import javax.comm.*;
import java.io.*;
import java.util.TooManyListenersException;

public class Terminal implements Runnable, SerialPortEventListener
{
    /*  */
    InputStream     inputStream;
    OutputStream    outputStream;
    SerialPort      serialPort;
    Thread          readThread;
    String[]        messageString = {"AT\n", "ATI1\n", "ATI3\n"};

    public Terminal ( CommPortIdentifier  portId )
    {
        try {
            // Открывает порт. Возвращает объект типа CommPort. параметры:
            // -	appname - Имя приложения вызывающего данный метод. (произвольная строка)
            // -	timeout - время в милисекундах, в течение которого, блокируется доступ к порту для его открытия.
            // Throws: PortInUseException если порт используется другим приложением.
            serialPort = ( SerialPort ) portId.open ( "TerminalApp", 2000 );
        } catch ( PortInUseException e ) {
        }

        try {
            // Если порт не поддерживает посылку данных то данный метод вернет null.
            outputStream = serialPort.getOutputStream ();
            // Если порт не поддерживает получение данных то данный метод вернет null.
            inputStream = serialPort.getInputStream ();
        } catch ( IOException e ) {
        }
        try {
            serialPort.addEventListener ( this );
        } catch ( TooManyListenersException e ) {
        }

        // Установить режим - сообщать если будут получены данные от порта
        serialPort.notifyOnDataAvailable ( true );

        try
        {
            // устанавливаем параметры порта
            serialPort.setSerialPortParams ( 9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE );
        } catch ( UnsupportedCommOperationException e ) {
        }
        
        // Запустить нить записи данных в порт
        readThread = new Thread ( this );
        readThread.start ();
    }

    /**
     * Запись данных в порт.
     */
    public void run ()
    {
        for ( int i = 0; i < 3; i++ )
        {
            try
            {
                outputStream.write ( messageString[i].getBytes () );
            } catch ( IOException e ) {
            }

            // Пауза
            try
            {
                Thread.sleep ( 5000 );
            } catch ( InterruptedException e ) {
                //
            }
        }
        System.exit ( 1 ); // выход из программы
    }

    /**
     * Генерится при получении данных от порта.
     * @param event
     */
    public void serialEvent ( SerialPortEvent event )
    {
        int numBytes;

        switch ( event.getEventType () )
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
                // Читать данные
                byte[] readBuffer = new byte[20];
                try
                {
                    while ( inputStream.available () > 0 )
                    {
                        numBytes = inputStream.read ( readBuffer );
                    }
                    System.out.print ( new String ( readBuffer ) );
                } catch ( IOException e ) {
                }
                break;
        }
    }

}
