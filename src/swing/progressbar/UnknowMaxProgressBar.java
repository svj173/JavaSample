package swing.progressbar;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Неизвестна макс граница.
 * <BR/> Полоска бегает вправо-влево. Посередине - меняется текст.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.02.2011 10:06:36
 */
public class UnknowMaxProgressBar extends JFrame
{
    JProgressBar progress;
    int num = 0;

    public UnknowMaxProgressBar ()
    {
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        JPanel pane = new JPanel ();

        int min = 0;
        int max = 2000;

        progress = new JProgressBar ( min, max );

        // Play animation
        progress.setIndeterminate ( true );

        progress.setValue ( 0 );
        progress.setStringPainted ( true );
        
        pane.add ( progress );
        setContentPane ( pane );

        // листенер. ловит изменения в прогресс-баре
        progress.addChangeListener(new ChangeListener() {
          public void stateChanged( ChangeEvent evt) {
            JProgressBar comp = (JProgressBar) evt.getSource();
            int value = comp.getValue();
            int min = comp.getMinimum();
            int max = comp.getMaximum();
          }
        });

    }

    public void iterate ()
    {
        while ( num < 2000 )
        {
            progress.setValue ( num );
            try
            {
                Thread.sleep ( 1000 );
            } catch ( InterruptedException e )             {  }
            num += 95;
        }
    }

    public static void main ( String[] argv ) throws Exception
    {
        // Create a horizontal progress bar

        UnknowMaxProgressBar frame = new UnknowMaxProgressBar ();
        frame.pack ();
        frame.setVisible ( true );
        frame.iterate ();
    }

}
