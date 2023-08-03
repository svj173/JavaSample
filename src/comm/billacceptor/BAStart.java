package comm.billacceptor;

import com.svj.xml.TreeObject;

import javax.swing.*;
import java.awt.*;

/**
 * <BR> Запустить программу тестовой работы с купюроприемником (Bill Acceptor - BA)
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 15:32:40
 */
public class BAStart implements Runnable
{
    private JFrame  frame;

    public BAStart ()   throws Exception
    {
        init();
    }

    private void init()   throws Exception
    {
        //
        JTextArea   text;
        JPanel  panel;
        BAActionListener    bal;

        frame = new JFrame ( "ICS. Bill Acceptor Test." );
        frame.setDefaultCloseOperation ( WindowConstants.EXIT_ON_CLOSE );
        frame.getContentPane ().setLayout ( new BorderLayout () );

        // Текстовый буфер
        text    = new JTextArea ();
        text.setText ( "Start\n---------------\n\n" );
        text.setMargin ( new Insets (10,10,10,10));
        frame.getContentPane ().add ( text, BorderLayout.EAST );

        bal = new BAActionListener ();

        // Панель
        JButton enable, disable, accept, reject, reset, mode;
        JLabel  titleBill, titleSumma, titleSize, valueBill, valueSumma, valueSize;
        panel   = new JPanel ();
        //panel.setMargin ( new Insets (10,10,10,10));
        panel.setLayout ( new GridLayout ( 6,2 ) );
        enable  = new JButton ( "Enable" );
        enable.setActionCommand ( "enable" );
        enable.addActionListener ( bal );
        panel.add ( enable );
        disable = new JButton ( "Disable" );
        disable.setActionCommand ( "disable" );
        disable.addActionListener ( bal );
        panel.add ( disable );
        //
        titleBill   = new JLabel ( "Получено" );
        panel.add ( titleBill );
        valueBill   = new JLabel ( "0", JLabel.RIGHT );
        panel.add ( valueBill );
        //
        titleSumma   = new JLabel ( "Сумма" );
        panel.add ( titleSumma );
        valueSumma   = new JLabel ( "0" );
        panel.add ( valueSumma );
        //
        titleSize   = new JLabel ( "Всего купюр" );
        panel.add ( titleSize );
        valueSize   = new JLabel ( "0" );
        panel.add ( valueSize );
        //
        accept  = new JButton ( "accept" );
        panel.add ( accept );
        reject = new JButton ( "reject" );
        panel.add ( reject );
        //
        reset = new JButton ( "reset" );
        reset.setActionCommand ( "reset" );
        reset.addActionListener ( bal );
        panel.add ( reset );
        //
        mode = new JButton ( "mode" );
        mode.setActionCommand ( "mode" );
        mode.addActionListener ( bal );
        panel.add ( mode );
        //
        frame.getContentPane ().add ( panel, BorderLayout.CENTER );

        frame.pack ();

        // Взять размер экрана чтобы разместить окно в середине экрана
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize ();
        frame.setLocation ( ( d.width - frame.getSize().width ) / 2,
                ( d.height - frame.getSize ().height ) / 2 );

        // Режим отображения фрейма. TRUE - показать, FALSE - скрыть
        frame.setVisible ( true );

        // BillAcceptor
        TreeObject config   = new TreeObject ();
        config.setString ( "port", "/dev/ttyS1" );
        config.setString ( "speed", "9600" );
        config.setString ( "parity", "2" );
        config.setString ( "bits", "8" );
        config.setString ( "stop_bit", "1" );
        BillAcceptor.getInstance().init ( config );
    }

     public void run ()
    {
       try
       {
          createAndShowGUI ();
       } catch ( Exception e )
       {
          System.err.println ( "BAStart.run() Error = " + e.getMessage () );
          e.printStackTrace ();
       }
    }

    private static void createAndShowGUI () throws Exception
    {
       JFrame.setDefaultLookAndFeelDecorated ( true );
    }


    public static void main ( String[] args )
    {
       System.setErr ( System.out );
        try {
            BAStart show = new BAStart ();
            // Запустить в отдельной самостоятельной нити
            SwingUtilities.invokeLater ( show );
        } catch ( Exception e ) {
            e.printStackTrace ();
        }
        System.err.println ( "BAStart. Finish " );
    }

}
