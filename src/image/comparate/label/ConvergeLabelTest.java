package image.comparate.label;


import javax.swing.*;
import java.awt.*;


/**
 * Выводим набор иконок у которых по разному совмещены изображения.
 * <BR/>
 * <BR/> Плохой подход:
 * <BR/> Здесь label рисуется только с иконкой - без текста, т.к. мы подменили механизм отрисовки метки.
 * <BR/> Метку надо встряхивать - setText() - проходит. Либо super(text);
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 15:20:38
 */
public class ConvergeLabelTest   extends JFrame
{
    public ConvergeLabelTest ()
    {
        super ( "Image Converge" );

        CLabel      label;
        ImageIcon   mesIcon, okIcon, errorIcon, wrongIcon;
        JPanel      panel;

        System.out.println ( "Start" );
        try
        {
            setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
            setLocationRelativeTo(null);    // левый верхний угол выводит по центру экрана

            panel   = new JPanel();
            //panel.setLayout ( new FlowLayout ( FlowLayout.CENTER, 10,10) );

            //setLayout ( new FlowLayout ( FlowLayout.CENTER, 10,10) );
            setLayout ( new BorderLayout ( 10,10) );

            panel.setSize ( 400, 200 );
            panel.setBackground ( Color.LIGHT_GRAY );

            setSize ( 400, 200 );

            // грузим изображения
            mesIcon     = new ImageIcon  ( "/home/svj/projects/SVJ/JavaSample/img/mes3124-leaf.png" );
            okIcon      = new ImageIcon  ( "/home/svj/projects/SVJ/JavaSample/img/connected.png" );
            errorIcon   = new ImageIcon  ( "/home/svj/projects/SVJ/JavaSample/img/not-avalaible.png" );
            wrongIcon   = new ImageIcon  ( "/home/svj/projects/SVJ/JavaSample/img/device-warning.png" );
            System.out.println ( "wrongIcon = " + wrongIcon );

            label   = new CLabel ( mesIcon, okIcon );
            //label.setText ( "ok" );
            panel.add ( label );
            //add ( label, BorderLayout.CENTER );
            //add ( label );

            label   = new CLabel ( mesIcon, errorIcon );
            //label.setText ( "error" );
            panel.add ( label );
            //add ( label, BorderLayout.WEST );
            //add ( label, BorderLayout.NORTH );
            //add ( label );

            label   = new CLabel ( mesIcon, wrongIcon );
            label.setText ( "wrong" );
            //label.revalidate();
            //label.repaint ();
            panel.add ( label );
            //add ( label, BorderLayout.EAST );
            //add ( label );
            
            //getContentPane().add ( panel );
            add ( panel, BorderLayout.CENTER );

            System.out.println ( "ok" );
            //pack();
            repaint();
        } catch ( Exception e )        {
            e.printStackTrace();
        }
        System.out.println ( "Finish" );
    }

    public static void main ( String args[] )
    {
        new ConvergeLabelTest().setVisible (true);
    }

}
