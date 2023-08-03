package swing.progressbar.wait;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.02.2011 11:36:03
 */
public class WaitingDialog extends JDialog
{
    public final static int ONE_SECOND = 1000;

    private int ic;
    private JProgressBar progressBar;
    private Timer timer;


    public WaitingDialog ( JFrame frame, String title )
    {
        super ( frame, title, true );

        Border border;

        setLayout ( new BorderLayout () );
        setLocationRelativeTo ( null );  // располагать диалог в центре экрана
        setSize ( 500, 80 );

        // крестик
        //setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE );

        // убрать служебные - Убрать вниз, Изменить размер, Закрыть
        // убирает всю рамку окна (титл и кнопки - Убрать вниз, Изменить размер, Закрыть. Остаются только созданные компоненты
        //setUndecorated(true);
        // убирается кнопка Изменения размера
        //setResizable(false);
        // ничего не меняется
        //setDefaultLookAndFeelDecorated(false);

        /*
     *         enum: NONE                   JRootPane.NONE
     *               FRAME                  JRootPane.FRAME
     *               PLAIN_DIALOG           JRootPane.PLAIN_DIALOG
     *               INFORMATION_DIALOG     JRootPane.INFORMATION_DIALOG
     *               ERROR_DIALOG           JRootPane.ERROR_DIALOG
     *               COLOR_CHOOSER_DIALOG   JRootPane.COLOR_CHOOSER_DIALOG
     *               FILE_CHOOSER_DIALOG    JRootPane.FILE_CHOOSER_DIALOG
     *               QUESTION_DIALOG        JRootPane.QUESTION_DIALOG
     *               WARNING_DIALOG         JRootPane.WARNING_DIALOG
         */
        // добавил еще одну рамку - с одним только крестиком
        //getRootPane().setWindowDecorationStyle ( JRootPane.PLAIN_DIALOG );
        // ничего не изменилось - рамка осталась
        //getRootPane().setWindowDecorationStyle ( JRootPane.NONE );
        // добавил еще одну рамку - с одним только крестиком
        //getRootPane().setWindowDecorationStyle ( JRootPane.FRAME );


        progressBar = new JProgressBar ( 0, 4000 );

        // рисуем бесконечный бегунок вправо-влево
        progressBar.setIndeterminate ( true );

        progressBar.setValue ( 0 );
        progressBar.setStringPainted ( true );
        // устанавливаем текст, который выводится вместо числа. null - выводится число как процент
        progressBar.setString ( "0" );

        // пустой бордюр - т.е. просто отступы по краям.
        //border = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        // бордюр вокруг бегунка - c текстом
        border = BorderFactory.createTitledBorder ( "Ожидание..." );

        progressBar.setBorder ( border );

        add ( progressBar, BorderLayout.CENTER );

        ic      = 0;
        // Create a timer.
        timer   = new Timer ( ONE_SECOND, new ActionListener()
        {
            public void actionPerformed ( ActionEvent evt)
            {
                //progressBar.setValue(ic);
                progressBar.setString ( String.valueOf(ic) );
                //if ( worker.isDone() ) close();
                ic++;
                System.out.println ( "-- ic   = " + ic );
                Date date = new Date ();
                System.out.println ( "-- date = " + date );
            }
        });
    }

    private void close ()
    {
        Toolkit.getDefaultToolkit().beep();
        setCursor(null); //turn off the wait cursor
        timer.stop();
        setVisible(false);
        dispose();
    }

    public void start ()
    {
        //worker.execute();
        setCursor ( Cursor.getPredefinedCursor ( Cursor.WAIT_CURSOR ) );
        timer.start();
        setVisible ( true );
    }

}
