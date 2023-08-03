package comm.touchscreen;


import com.svj.xml.TreeObject;

import javax.comm.*;
import javax.swing.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import java.util.Date;
import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;

import org.apache.logging.log4j.*;

/**
 * <BR> Контроллер. Обрабатывает полученные данные. Разбирает ситуации:
 * <LI> Клиент ведет пальцем по экрану. Никак на это не реагирвоать. </LI>
 * <LI> Клиент отпустил экран. Активирвоать действие.</LI>
 * <LI> Поступают одни и те же координаты. Клиент держит палец на экране. Ждать когда он отпустит экран.</LI>
 * <BR>
 * <BR> User: svj
 * <BR> Date: 15.03.2006
 * <BR> Time: 16:51:18
 */
public class TouchScreen
{
    private static Logger logger = LogManager.getFormatterLogger ( TouchScreen.class );

    private static TouchScreen screen = new TouchScreen ();

    InputStream     inputStream;
    OutputStream    outputStream;
    SerialPort      serialPort;
    //Thread          readThread;

    private JFrame  frame;

    private int     xc, yc;

    private TimerListener   timerListener;

    private Timer   timer;


    private TouchScreen ()
    {
        int step, max;
        // Нет никаких координат
        xc  = yc    = -1;
        step    = 20;
        max     = 200;
        timerListener   = new TimerListener ( step, max );
        timer           = new Timer ( step, timerListener );
    }

    public static TouchScreen getInstance ()
    {
        return screen;
    }

    /**
     * Инициализирует программу
     */
    public void init ( TreeObject config, JFrame  frame ) throws Exception
    {
        String str, portName;
        int portSpeed, portParity, portBits, portStopBit;
        CommPortIdentifier  portId;
        Enumeration         portList;

        logger.debug ( "Start" );

        portList = CommPortIdentifier.getPortIdentifiers ();
        while ( portList.hasMoreElements () )
        {
            portId = ( CommPortIdentifier ) portList.nextElement ();
            logger.debug ( "Port: Name = " + portId.getName () + ", Type = " + portId.getPortType () );
        }

        logger.debug ( "Params:\n" + config );

        this.frame  = frame;

        // COM1 (/dev/ttyS0), 9600, E (NO), 8, 1
        portName    = config.getString ( "port" );
        str         = config.getString ( "speed" );
        portSpeed   = Integer.parseInt ( str );
        // parity. parity_even = 2
        str         = config.getString ( "parity" );
        portParity  = Integer.parseInt ( str );
        str         = config.getString ( "bits" );
        portBits    = Integer.parseInt ( str );
        str         = config.getString ( "stop_bit" );
        portStopBit = Integer.parseInt ( str );

        try
        {
            // Взять порт
            portId  = CommPortIdentifier.getPortIdentifier ( portName );
            logger.debug ( "Port = " + portId );

            // Открывает порт. Возвращает объект типа CommPort. параметры:
            // -	appname - Имя приложения вызывающего данный метод. (произвольная строка)
            // -	timeout - время в милисекундах, в течение которого, блокируется доступ к порту для его открытия.
            // Throws: PortInUseException если порт используется другим приложением.
            serialPort      = ( SerialPort ) portId.open ( "TouchScreen", 2000 );

            // Если порт не поддерживает посылку данных то данный метод вернет null.
            outputStream    = serialPort.getOutputStream ();
            if ( outputStream == null )
                    logger.error ( "outputStream == null" );
            // Если порт не поддерживает получение данных то данный метод вернет null.
            inputStream     = serialPort.getInputStream ();
            if ( inputStream == null )
                    logger.error ( "inputStream == null" );



            // Слушатель
            SerialPortListener    pl  = new SerialPortListener ();
            serialPort.addEventListener ( pl );

            // Установить режим - сообщать если будут получены данные от порта
            serialPort.notifyOnDataAvailable ( true );

            // устанавливаем параметры порта
            logger.debug ( "portSpeed = " + portSpeed + ", portBits = " + portBits
                + ", portStopBit = " + portStopBit +  ", portParity = " + portParity );
            serialPort.setSerialPortParams ( portSpeed, portBits, portStopBit, portParity );

            //sendCommand ( 0x30 );   // RESET
            //sendCommand ( 0x02 );
            //sendCommand ( 12 );

         /*
        } catch ( NoSuchPortException e ) {
            // Нет такого порта
            logger.error ( "NoSuchPortException", e );
        } catch ( PortInUseException e ) {
            // Порт уже занят
            logger.error ( "PortInUseException", e );
        } catch ( IOException e ) {
            // Какой-то из потоков не поддерживается портом
            logger.error ( "IOException", e );
        } catch ( TooManyListenersException e ) {
            // Ошибка добавления слушателя на порт
            logger.error ( "TooManyListenersException", e );
        } catch ( UnsupportedCommOperationException e ) {
            // Ошибка установки параметров порта
            logger.error ( "UnsupportedCommOperationException", e );
            */
        } catch ( Exception e ) {
            logger.error ( "Exception = ", e );
            throw e;
        } finally {
            logger.debug ( "Finish" );
        }
            //*/
        logger.debug ( "Finish" );

    }

    public void sendCommand ( int command )
    {
        logger.debug ( "Send '" + command + "'" );
        //
        try
        {
            outputStream.write ( command );
        } catch ( IOException e ) {
            logger.error ( "Send command '" + command + "' to BA error. ", e );
        }
    }

    public void processEvent()
    {
        int[]   point;
        int     x, y;

        logger.debug ( "Start" );


        // Получить данные по нажатой точке
        point   = readData();
        x   = point[0];
        y   = point[1];

        // Проверить - полученные данные - координаты? Или что-то другое.
        if ( x == -10 )
        {
            logger.debug ( "Finish. Its not X/Y." );
            return;
        }

        if ( xc == -1 && yc == -1 )
        {
            logger.debug ( "New press. Run Timer. Old: x = " + xc + ", y = " + yc );
            // Это новое нажатие клиентом
            // - Запустить таймер
            xc  = x;
            yc  = y;
            timer.start();
            processPress ( xc, yc );
        }
        else
        {
            // Нажатие уже было.
            if ( x != xc && y != yc )
            {
                logger.debug ( "Forward press. Its error. Old: x = " + xc + ", y = " + yc );
                // Клиент ведет пальцем
                // - TODO Вывести строку внизу - Не надо водить пальцем. Просто прикоснитесь к кнопке.
                // - Изменить текущие координаты
                xc  = x;
                yc  = y;
                timerListener.clearCounter();
            }
            else
            {
                logger.debug ( "Has pressed. White release." );
                // Клиент держит палец на одном месте. Повтор координат. Обнулить таймер отлова отпускания точки экрана.
                timerListener.clearCounter();
            }
        }

        logger.debug ( "Finish" );

    }

    /**
     * Читает данные из последовательного порта. -10 - если это не координаты экрана, а что-то другое.
     * @return  X Y
     */
    public int[] readData ()
    {
        int[]   data = new int[1000];
        int[]   result = new int[2];
        int ic, bb, dX, nnX, dY, nnY, x, y, ii;

        logger.debug ( "Start" );

        result[0] = -10;
        result[1] = -10;

        try {
            // Читаем данные в буфер
            ic = 0;
            while ( inputStream.available () > 0 )
            {
                bb = inputStream.read ();
                data[ic] = bb;
                ic++;
                if ( ic > 10 ) break;
            }

            logger.debug ( "Finish. ic = " + ic );

            if ( ic != 8 ) return result;

            if ( data[0] == 85 && data[1] == 84 )
            {
                dX  = data[3];
                ii  = data[4] - 1;
                nnX = 0;
                if ( ii > 3 )
                    logger.error  ( "Unknow NNX = " + data[4] );
                else
                    nnX = TSPar.xsector[ii];
                /*
                switch ( data[4] )
                {
                    case 1:
                        //nnX = 0;
                        nnX = TSPar.xsector[0];
                        break;
                    case 2:
                        //nnX = 256;
                        nnX = TSPar.xsector[1];
                        break;
                    case 3:
                        nnX = 512;
                        break;
                    case 4:
                        nnX = 768;
                        break;
                    default:
                        logger.error ( "Unknow X sector = " + data[4] );
                        break;
                }
                */
                dY  = data[5];
                ii  = data[6];
                nnY = 0;
                if ( ii > 3 )
                    logger.error  ( "Unknow NNX = " + data[6] );
                else
                    nnY = TSPar.ysector[ii];
                /*
                switch ( data[6] )
                {
                    case 0:
                        nnY = 0;
                        break;
                    case 1:
                        nnY = 0;
                        break;
                    case 2:
                        nnY = 256;
                        break;
                    case 3:
                        nnY = 512;
                        break;
                    default:
                        logger.error ( "Unknow Y sector = " + data[6] );
                        break;
                }
                */
                result[0] = dX + nnX + TSPar.DX;
                result[1] = dY + nnY + TSPar.DY;
                logger.debug ( "x = " + result[0] + ", y " + result[1] );
            }

        } catch ( Exception e ) {
            logger.error ( "Error", e );
            result[0] = -10;
            result[1] = -10;
        }

        return result;
    }


    /**
     * Считаем что клиент отпустил палец и время таймаута вышло - обработать нажатие.
     */
    public void processPressed ()
    {
        logger.debug ( "Start. X = " + xc + ", Y = " + yc );
        //
        //Component c, c2, c3, c4;
        Container c, c2, c4;
        Component[] cc;
        Component c3;
        int i;
        JButton button;
        String cmd, str;
        ActionListener ass[];
        ActionEvent act;
        MouseListener ml[];
        MouseEvent me;
        TSMouseListener tml;
        int i1;


        // Остановить таймер.
        timer.stop ();
        timerListener.clearCounter ();

        // Найти обьект, к которому прикоснулся клиент на экране.
        button = getButton ( xc, yc );
        if ( button == null ) {
            logger.debug ( "Finish. Its not Button." );
            return;
        }

        //
        logger.debug ( "It is JButton" );
        cmd = button.getActionCommand ();
        logger.debug ( " Cmd = " + cmd );
        //
        ass = button.getActionListeners ();
        logger.debug ( " Ass[0] = " + ass[0] );
        // ActionEvent(Object source, int id, String command)
        act = new ActionEvent ( button, ActionEvent.ACTION_PERFORMED, cmd );
        ass[0].actionPerformed ( act );

        // mouse
        ml = button.getMouseListeners ();
        // MouseEvent(Component source, int id, long when, int modifiers, int x, int y, int clickCount, boolean popupTrigger)
        me = new MouseEvent ( button, MouseEvent.MOUSE_PRESSED, new Date ().getTime (),
                InputEvent.BUTTON1_DOWN_MASK, xc, yc, 1, false );
        logger.debug ( " ME[..] = " + ml.length );
        ml[1].mousePressed ( me );
        //tml = (TSMouseListener) ml[0];
        //tml.mousePressed ( me );

        Color bg;
        str = button.getText ();
        i1 = Integer.parseInt ( str );
        i1 = -i1;
        button.setText ( "" + i1 );
        button.setBackground ( Color.GREEN );
        /*
        bg  = button.getBackground ();
        if ( bg == Color.RED )
                button.setBackground ( Color.GREEN );
        else
                button.setBackground ( Color.RED );
        */

        // reset
        xc = -1;
        yc = -1;

        logger.debug ( "Finish" );
    }

    /**
     * Клиент нажал кнопку, но еще не отпустил
     * @param x
     * @param y
     */
    private void   processPress ( int x, int y )
    {
        JButton button;

        logger.debug ( "Start" );

        button  = getButton ( x, y );
        if ( button == null )
        {
            logger.debug ( "Finish. Its not Button." );
            return;
        }
        button.setBackground ( Color.YELLOW );

        logger.debug ( "Finish" );
    }

    /**
     * Найти обьект, к которому прикоснулся клиент на экране.
     * @param x
     * @param y
     * @return button
     */
    private JButton getButton ( int x, int y )
    {
        Container c, c2, c4;
        Component[] cc;
        Component   c3;
        JButton result;
        //

        result  = null;

        c2   = frame.getContentPane ();
        logger.debug ( "ContentPane = " + c2 );
        //
        //cc  = c2.getComponents ();
        //for ( i = 0; i<cc.length; i++ )
        //        logger.debug ( " " + i + ". " + cc[i] );
        // Здесь мы уже получаем требуемый обьект - должна быть кнопка
        c3   = c2.getComponentAt ( xc, yc );
        logger.debug ( "Our object = " + c3 );

        if ( c3 instanceof JButton ) result = (JButton) c3;

        return result;
    }
}
