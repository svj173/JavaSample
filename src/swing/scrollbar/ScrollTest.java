package swing.scrollbar;




import java.awt.*;

import java.awt.event.*;

/**
 * Пример работы с ползунками Scrollbar - определяющими выбор значения из диапазона.
 * Выборка градаций цвета.
 * Создаются три полосы прокрутки - красная, зеленая, синяя.
 * Размер значений - от 0 до 255.
 * Шаг движения полосы - 10.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.05.2011 15:34:44
 */
class ScrollTest extends Frame
{
    Scrollbar sbRed     = new Scrollbar ( Scrollbar.VERTICAL, 127, 10, 0, 255 );
    Scrollbar sbGreen   = new Scrollbar ( Scrollbar.VERTICAL, 127, 10, 0, 255 );
    Scrollbar sbBlue    = new Scrollbar ( Scrollbar.VERTICAL, 127, 10, 0, 255 );
    Color mixedColor = new Color ( 127, 127, 127 );
    Label  lm = new Label ();
    Button b1 = new Button ( "Применить" );
    Button b2 = new Button ( "Отменить" );

    ScrollTest ( String s )
    {
        super ( s );

        setLayout ( null );

        setFont ( new Font ( "Serif", Font.BOLD, 15 ) );

        Panel p = new Panel ();

        p.setLayout ( null );

        p.setBounds ( 10, 50, 150, 260 );
        add ( p );

        Label lc = new Label ( "Подберите цвет" );

        lc.setBounds ( 20, 0, 120, 30 );
        p.add ( lc );

        Label lmin = new Label ( "0", Label.RIGHT );

        lmin.setBounds ( 0, 30, 30, 30 );
        p.add ( lmin );

        Label Imiddle = new Label ( "127", Label.RIGHT );

        Imiddle.setBounds ( 0, 120, 30, 30 );
        p.add ( Imiddle );

        Label Imax = new Label ( "255", Label.RIGHT );

        Imax.setBounds ( 0, 200, 30, 30 );
        p.add ( Imax );

        sbRed.setBackground ( Color.red );

        sbRed.setBounds ( 40, 30, 20, 200 );
        p.add ( sbRed );

        sbGreen.setBackground ( Color.green );

        sbGreen.setBounds ( 70, 30, 20, 200 );
        p.add ( sbGreen );

        sbBlue.setBackground ( Color.blue );

        sbBlue.setBounds ( 100, 30, 20, 200 );
        p.add ( sbBlue );

        Label lp = new Label ( "Образец:" );

        lp.setBounds ( 250, 50, 120, 30 );
        add ( lp );

        lm.setBackground ( new Color ( 127, 127, 127 ) );

        lm.setBounds ( 220, 80, 120, 80 );
        add ( lm );

        b1.setBounds ( 240, 200, 100, 30 );
        add ( b1 );

        b2.setBounds ( 240, 240, 100, 30 );
        add ( b2 );

        setSize ( 400, 300 );
        setVisible ( true );
    }

    public static void main ( String[] args )
    {
        Frame f = new ScrollTest ( " Выбор цвета " );
        f.addWindowListener ( new WindowAdapter()
        {

            public void windowClosing ( WindowEvent ev )
            {
                System.exit ( 0 );
            }
        } );
    }

}
