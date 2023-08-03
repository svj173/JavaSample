package comm.touchscreen;


import com.svj.utils.FileTools;
import com.svj.xml.TreeObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * <BR> Запустить программу тестовой работы с сенсорным экраном
 *
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 15:32:40
 */
public class TSStart implements Runnable
{
    private static Logger logger = LogManager.getFormatterLogger ( TSStart.class );

    private JFrame  frame;


    public TSStart ()
    {
        //init();
    }

    public void init ( String paramFile ) throws Exception
    {
        //
        JTextArea   text;
        JPanel  panel;
        JButton button;
        TSActionListener    bal;
        TSMouseListener     mal;
        int     i, iws, ihs, isize, ic;
        String  str;
        Properties prop;

        logger.debug ( "Start" );

        frame = new JFrame ( "ICS. Touch Screen Test." );
        frame.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
        //frame.getContentPane ().setLayout ( new BorderLayout () );
        iws = 10;
        ihs = 10;
        frame.getContentPane ().setLayout ( new GridLayout (iws,ihs) );

        bal = new TSActionListener (frame);
        mal = new TSMouseListener ();

        frame.addMouseListener ( mal );

        isize   = iws * ihs;
        for  ( i=0; i<isize; i++ )
        {
            button  = new JButton ();
            str = "" + i;
            button.setActionCommand ( str );
            button.addActionListener ( bal );
            button.addMouseListener ( mal );
            button.setText ( str );
            frame.getContentPane().add ( button );
        }


        frame.pack ();

        // Взять размер экрана чтобы разместить окно в середине экрана
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize ();
        frame.setSize ( d.width, d.height );

        //frame.setLocation ( ( d.width - frame.getSize().width ) / 2,
        //        ( d.height - frame.getSize ().height ) / 2 );

        // Режим отображения фрейма. TRUE - показать, FALSE - скрыть
        frame.setVisible ( true );

        //
        prop    = FileTools.loadProperties ( paramFile );
        TreeObject config   = new TreeObject ();
        str = prop.getProperty ( "port" );
        config.setString ( "port", str );
        str = prop.getProperty ( "speed" );
        config.setString ( "speed", str );
        str = prop.getProperty ( "parity" );
        config.setString ( "parity", str );
        str = prop.getProperty ( "bits" );
        config.setString ( "bits", str );
        str = prop.getProperty ( "stop_bit" );
        config.setString ( "stop_bit", str );
        //BillAcceptor.getInstance ().init ( config );
        for ( i=0; i<4; i++ )
        {
            str = prop.getProperty ( "x" + (i+1) );
            ic  = Integer.parseInt ( str );
            TSPar.xsector[i]    = ic;
            logger.debug ( "x" + (i+1) + " = " + ic );
        }
        for ( i=0; i<4; i++ )
        {
            str = prop.getProperty ( "y" + i );
            ic  = Integer.parseInt ( str );
            TSPar.ysector[i]    = ic;
            logger.debug ( "y" + i + " = " + ic );
        }
        str = prop.getProperty ( "dx" );
        if ( str != null )
        {
            ic  = Integer.parseInt ( str );
            TSPar.DX   = ic;
        }
        str = prop.getProperty ( "dy" );
        if ( str != null )
        {
            ic  = Integer.parseInt ( str );
            TSPar.DY   = ic;
        }


        // Порт экрана
        TouchScreen.getInstance().init ( config, frame );

        logger.debug ( "Start" );

    }

     public void run ()
    {
       try
       {
          createAndShowGUI ();
       } catch ( Exception e )
       {
          System.err.println ( "TSStart.run() Error = " + e.getMessage () );
          e.printStackTrace ();
       }
    }

    private static void createAndShowGUI () throws Exception
    {
       JFrame.setDefaultLookAndFeelDecorated ( true );
    }


    public static void main ( String[] args )
    {
        try
        {
            System.setErr ( System.out );

            // logger
            // PropertyConfigurator.configure ( args[0] );

            TSStart show = new TSStart ();
            show.init ( args[1] );

            // Запустить в отдельной самостоятельной нити
            SwingUtilities.invokeLater ( show );
            System.err.println ( "TSStart. Finish " );
        } catch ( Exception e ) {
            e.printStackTrace ();
            System.exit ( 12 );
        }
    }

}
