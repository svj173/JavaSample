package swing.fon_img.grafic_panel;


import comm.touchscreen.TSActionListener;
import comm.touchscreen.TSMouseListener;
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
public class Start implements Runnable
{
    private static Logger logger = LogManager.getFormatterLogger ( Start.class );

    private JFrame  frame;


    public Start ()
    {
        //init();
    }

    public void init ( String paramFile ) throws Exception
    {
        //
        JTextArea   text;
        JPanel  panel;
        PaintPanel  paintPanel;
        JButton button;
        TSActionListener    bal;
        TSMouseListener     mal;
        int     i, iws, ihs, isize, ic;
        String  str, iconFile;
        Properties prop;
        //ImageIcon   icon;
        Icon   icon;
        Font    font;
        Dimension point, frameSize;

        logger.debug ( "Start" );

        frame = new JFrame ( "ICS. Touch Screen Test." );
        frame.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
        frame.getContentPane().setLayout ( new BorderLayout () );
        /*
        iws = 10;
        ihs = 10;
        frame.getContentPane().setLayout ( new GridLayout (iws,ihs) );

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
        */
        //*
        //iconFile    = "c:/Serg/Projects/SVJ/JavaSample/img/beeline.gif";
        iconFile    = "c:/Serg/Projects/SVJ/JavaSample/img/bsel.gif";
        icon    = new ImageIcon ( iconFile );

        font = new Font ( "arial", Font.BOLD,  32 );

        button  = new JButton ();
        str     = "1";
        button.setActionCommand ( str );
        //button.addActionListener ( bal );
        //button.addMouseListener ( mal );
        button.setText ( str );
        button.setFont ( font );
        button.setForeground ( Color.WHITE );
        button.setIcon ( icon );
        button.setHorizontalTextPosition ( SwingConstants.CENTER  );

        button.setOpaque ( true );   // ничего не дает
        button.setBorderPainted ( false );
        button.setContentAreaFilled(false);


/*
frame.getContentPane().add ( button, BorderLayout.CENTER );
*/

        paintPanel  = new PaintPanel ();
        paintPanel.setLayout ( new BorderLayout());
        paintPanel.add ( button, BorderLayout.CENTER );
        //paintPanel.setBackground ( Color.GREEN );
        //paintPanel.setOpaque ( false );


        frame.getContentPane().add ( paintPanel, BorderLayout.CENTER );

        //panel   = new JPanel ();
        //panel.add ( button );
        //frame.getContentPane().add ( panel, BorderLayout.CENTER );


        frame.pack ();

        // Взять размер экрана чтобы разместить окно в середине экрана
        point = Toolkit.getDefaultToolkit().getScreenSize ();
        //frame.setSize ( d.width, d.height );
        frame.setLocation ( ( point.width - frame.getSize().width ) / 2,
                ( point.height - frame.getSize().height ) / 2 );

        frameSize   = new Dimension ( 300, 200 );
        //frame.setPreferredSize ( frameSize );
        //frame.setMinimumSize ( frameSize );
        frame.setSize ( frameSize );

        // Режим отображения фрейма. TRUE - показать, FALSE - скрыть
        frame.setVisible ( true );

        logger.debug ( "Finish" );

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

            Start show = new Start ();
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
