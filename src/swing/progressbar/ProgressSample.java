package swing.progressbar;


/**
 * Стоячая картинка прогресс бара - 25%. Бордюр с текстом - Reading...
 * <BR/> Изменение цветовой гаммы.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.02.2011 10:03:11
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ProgressSample
{
    public static void main ( String args[] )
    {
        JFrame          f;
        Container       content;
        JProgressBar    progressBar;
        Border          border;

        // Изменение цветовой гаммы прогресс-бара
        // цвет текста, не покрытого бегунком
        UIManager.put ( "ProgressBar.selectionBackground", Color.black );
        // цвет текста, покрытого бегунком
        UIManager.put ( "ProgressBar.selectionForeground", Color.white );
        // цвет бегунка
        UIManager.put ( "ProgressBar.foreground", new Color ( 8, 32, 128 ) );

        f       = new JFrame ( "JProgressBar Sample" );
        f.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        content = f.getContentPane ();

        progressBar = new JProgressBar ();
        progressBar.setValue ( 25 );
        progressBar.setStringPainted ( true );

        // бордюр вокруг бегунка
        border = BorderFactory.createTitledBorder ( "Reading..." );
        progressBar.setBorder ( border );

        content.add ( progressBar, BorderLayout.NORTH );

        f.setSize ( 300, 100 );
        f.setVisible ( true );
    }

}
