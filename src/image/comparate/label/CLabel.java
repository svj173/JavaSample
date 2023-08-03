package image.comparate.label;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 14:54:39
 */
public class CLabel extends JLabel
{
    private Insets      insets;
    private ImageIcon   icon1, icon2;

    public CLabel ( ImageIcon icon1, ImageIcon icon2 )
    {
        //super("--");
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    public void paint ( Graphics g )
    {
        int             width, height;
        Composite       mode;
        Graphics2D      g2d;
        Graphics2D      destG;
        BufferedImage dest;

        // Без этого изображение НЕ выводится
        if ( insets == null )  insets = getInsets();
        g.translate ( insets.left, insets.top );


        g2d     = ( Graphics2D ) g;

        width   = icon1.getIconWidth();
        height  = icon1.getIconHeight();
        System.out.println ( "width = " + width + ", height = " + height );

        // Создать буфер изображения в оперативной памяти
        dest    = new BufferedImage ( width, height, BufferedImage.TYPE_INT_ARGB );

        // Получить контекст Graphics
        destG   = dest.createGraphics();

        // Отобразить первое изображение в нем
        destG.drawImage ( icon1.getImage(), 0, 0, this );     // this

        // -- Режим совмещения
        //mode    = AlphaComposite.getInstance ( AlphaComposite.SRC, sourcePercentage );
        //mode    = AlphaComposite.getInstance ( AlphaComposite.DST_OVER );   // Отобразить новое изображение поверх исходного изображения.
        mode    = AlphaComposite.getInstance ( AlphaComposite.SRC_OVER );   // Отобразить исходное изображение поверх нового изображения.

        // Скомбинировать их
        destG.setComposite ( mode );
        destG.drawImage ( icon2.getImage(), 0, 0, this );

        // Отобразить изображение на экране
        g2d.drawImage ( dest, 0, 0, this );
    }
    
}
