package image.comparate;


/**
 * наложение одного изображения на другое.
 *
 * Режимы совмещения:
 *
CLEAR    - Не отображать ничего. Создает пустой вывод.
DST      - Отобразить только новое изображение.
SRC      - Отобразить только исходное изображение.
DST_ATOP - Отобразить исходное изображение. Там, где два изображения перекрываются, отобразить новое изображение.
SRC_ATOP - Отобразить новое изображение. Там, где два изображения перекрываются, отобразить исходное изображение.
DST_IN   - Отобразить часть нового изображения, которое перекрывает исходное.
SRC_IN   - Отобразить часть исходного изображения, которое перекрывает новое.
DST_OUT  - Отобразить часть нового изображения, которое не перекрывает исходное.
SRC_OUT  - Отобразить часть исходного изображения, которое не перекрывает новое.
DST_OVER - Отобразить новое изображение поверх исходного изображения.
SRC_OVER - Отобразить исходное изображение поверх нового изображения.
XOR      - Отобразить части нового и исходного изображения, которые не перекрываются.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 13:05:08
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Converge02 extends JFrame
{

    ImageIcon saloonIcon = new ImageIcon ( "/home/svj/projects/SVJ/JavaSample/img/mes3124-leaf.png" );
    ImageIcon coachIcon = new ImageIcon  ( "/home/svj/projects/SVJ/JavaSample/img/connected.png" );
    Image saloon = saloonIcon.getImage();
    Image coach = coachIcon.getImage();

    Insets insets;

    public Converge02 ()
    {
        super ( "Image Blending" );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        //dest = new BufferedImage ( 200, 200, BufferedImage.TYPE_INT_ARGB );
        setSize ( 200, 200 );

        repaint();
    }

    public void paint ( Graphics g )
    {
        int             width, height;
        Composite       mode;
        Graphics2D      g2d;
        Graphics2D      destG;
        BufferedImage   dest;

        // Без этого изображение НЕ выводится
        if ( insets == null )  insets = getInsets();
        g.translate ( insets.left, insets.top );


        g2d     = ( Graphics2D ) g;

        width   = saloonIcon.getIconWidth();
        height  = saloonIcon.getIconHeight();
        System.out.println ( "width = " + width + ", height = " + height );

        // Создать буфер изображения в оперативной памяти
        dest    = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );

        // Получить контекст Graphics
        destG   = dest.createGraphics();

        // Отобразить первое изображение в нем
        destG.drawImage ( saloon, 0, 0, this );     // this

        // -- Режим совмещения
        //mode    = AlphaComposite.getInstance ( AlphaComposite.SRC, sourcePercentage );
        //mode    = AlphaComposite.getInstance ( AlphaComposite.DST_OVER );   // Отобразить новое изображение поверх исходного изображения.
        mode    = AlphaComposite.getInstance ( AlphaComposite.SRC_OVER );   // Отобразить исходное изображение поверх нового изображения.

        // Скомбинировать их
        destG.setComposite ( mode );
        destG.drawImage ( coach, 0, 0, this );

        // Отобразить изображение на экране
        g2d.drawImage ( dest, 0, 0, this );
    }

    public static void main ( String args[] )
    {
        //new Converge ().show ();
        new Converge02().setVisible (true);
    }

}
