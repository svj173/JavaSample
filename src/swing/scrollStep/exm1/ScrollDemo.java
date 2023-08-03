package swing.scrollStep.exm1;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


/**
 * пример кода использующего созданный компонент Rule.
 * <BR/> Загружает большую картинку. Отображает в скроллинге, причем сверху и слева - метрическая линейка в см.
 * <BR/> ПС. Косяк - линейка слева при продвижении вниз не отрисовывается (пусто).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.12.2011 12:06:35
 */
public class ScrollDemo
{
    public static void main ( String[] args )
    {
        JFrame jf;
        String imgFileName;
        final Rule viewRow, viewCol;
        final JScrollPane jscr;
        JLabel lab_img;

        imgFileName = "/home/svj/projects/SVJ/JavaSample/img/side.jpg";

        jf = new JFrame ( "scroller pic" );

        lab_img = new JLabel ( new ImageIcon ( imgFileName ) );

        jscr = new JScrollPane ( lab_img );

        viewCol = new Rule ( Rule.HORIZONTAL, true );
        // Устанавливаем размер линейки по высоте – 32 пикселя
        viewCol.setPreferredHeight ( 32 );
        // Устанавливаем линейку по горизонтали. Заготоловок для области прокрутки.
        jscr.setColumnHeaderView ( viewCol );

        // Здесь добавляется линейка по вертикали.
        // Она будет располжена по вертикали слева.
        viewRow = new Rule ( Rule.VERTICAL, true );
        viewRow.setPreferredWidth ( 32 );
        jscr.setRowHeaderView ( viewRow );


        jscr.getViewport().addChangeListener ( new ChangeListener()
        {
            // При изменениях размеров панели перерисовываем компоненты скроллинга.
            public void stateChanged ( ChangeEvent e )
            {
                viewCol.setPreferredWidth ( jscr.getViewport().getComponent(0).getWidth() );
                // todo а для viewRow я не выполнил изменения размера и поэтому там возникают баги отрисовки
            }
        } );

        // Здесь добавляются кнопки в уголки области прокрутки
        jscr.setCorner ( JScrollPane.UPPER_LEFT_CORNER, new JToggleButton ( "TL" ) );
        jscr.setCorner ( JScrollPane.LOWER_LEFT_CORNER, new JToggleButton ( "BL" ) );
        jscr.setCorner ( JScrollPane.UPPER_RIGHT_CORNER, new JToggleButton ( "TR" ) );
        jscr.setCorner ( JScrollPane.LOWER_RIGHT_CORNER, new JToggleButton ( "BR" ) );
        jf.getContentPane().add ( jscr, BorderLayout.CENTER );

        jf.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        jf.pack ();
        jf.setVisible ( true );
    }
    
}
