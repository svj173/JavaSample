package image.comparate.label;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Выводим набор иконок у которых по разному совмещены изображения. С текстом метки.
 * <BR/>
 * <BR/> Здесь мы смешиваем две картинки и создаем новую третью, которую и заносим в метку.
 * <BR/>
 * <BR/> Рабочий вариант. Метки выводятся друг под другом с текстом.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 15:20:38
 */
public class ConvergeLabelTest02 extends JFrame
{
    public ConvergeLabelTest02 ()
    {
        super ( "Image Converge 2" );

        JLabel      label;
        ImageIcon   mesIcon, okIcon, errorIcon, wrongIcon;
        JPanel      panel;
        Icon        icon;

        System.out.println ( "Start" );
        try
        {
            setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
            setLocationRelativeTo(null);    // левый верхний угол выводит по центру экрана

            panel   = new JPanel();
            //panel.setLayout ( new FlowLayout ( FlowLayout.CENTER, 10,10) );
            panel.setLayout ( new BorderLayout ( 10,10) );

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

            label   = new JLabel ();
            icon    = createIcon ( mesIcon, okIcon  );
            label.setIcon ( icon );
            label.setText ( "ok" );
            panel.add ( label, BorderLayout.NORTH );

            label   = new JLabel ();
            icon    = createIcon ( mesIcon, errorIcon  );
            label.setIcon ( icon );
            label.setText ( "error" );
            panel.add ( label, BorderLayout.CENTER );

            label   = new JLabel ();
            icon    = createIcon ( mesIcon, wrongIcon  );
            label.setIcon ( icon );
            label.setText ( "wrong" );
            panel.add ( label, BorderLayout.SOUTH );

            add ( panel, BorderLayout.CENTER );

            System.out.println ( "ok" );
            //pack();
            //repaint();
        } catch ( Exception e )        {
            e.printStackTrace();
        }
        System.out.println ( "Finish" );
    }

    private Icon createIcon ( ImageIcon mesIcon, ImageIcon okIcon )
    {
        int             width, height;
        Composite       mode;
        Graphics2D      destG;
        BufferedImage dest;
        Image  image;

        width   = mesIcon.getIconWidth();
        height  = mesIcon.getIconHeight();
        System.out.println ( "width = " + width + ", height = " + height );

        // Создать буфер изображения в оперативной памяти
        dest    = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );

        // Получить контекст Graphics
        destG   = dest.createGraphics();

        // Отобразить первое изображение в нем
        destG.drawImage ( mesIcon.getImage(), 0, 0, null );     // this

        // -- Режим совмещения
        //mode    = AlphaComposite.getInstance ( AlphaComposite.SRC, sourcePercentage );
        //mode    = AlphaComposite.getInstance ( AlphaComposite.DST_OVER );   // Отобразить новое изображение поверх исходного изображения.
        mode    = AlphaComposite.getInstance ( AlphaComposite.SRC_OVER );   // Отобразить исходное изображение поверх нового изображения.

        // Скомбинировать их
        destG.setComposite ( mode );
        destG.drawImage ( okIcon.getImage(), 0, 0, this );

        image   = Toolkit.getDefaultToolkit().createImage(dest.getSource());
        return new ImageIcon (image);
    }

    public Image toImage ( BufferedImage bufferedImage ) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }

    public static void main ( String args[] )
    {
        new ConvergeLabelTest02 ().setVisible (true);
    }

}
