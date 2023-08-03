package swing.label.html;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

/**
 * Вввести HTML текст и сохранить его в файле в виде картинки.
 * 
 * Показать HTML в виде картинки. Например, чтобы текст не скопировать было. Или вставить изображение в файл.
 * Такое изображение конечно не должно быть большое, но тем не менее.
 *
 * Тут важно отметить вот какой момент - сначала создается объект JLabel и ему устанавливается HTML-текст.
 * После этого получают предпочтительные размеры (через getPreferredSize) и после этого создается новый объект класса JLabel и ему задаются размеры через setBounds.
 * А дальше все как в ранее указанной теме. В итоге получится файл, который точно соответствует по размеру HTML разметке.
 *
 * Вы можете увеличить/уменьшить/видоизменить HTML-текст, который создается в примере и посмотреть, как все будет.
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 11:15:46
 */
public class SaveHtmlAsImage
{
    public JLabel lbl = new JLabel();
    public int w = 0;
    public int h = 0;

    public void saveFile ()
    {
        try
        {
            BufferedImage bi = new BufferedImage ( w, h, BufferedImage.TYPE_INT_RGB );
            Graphics g = bi.getGraphics ();
            g.setColor ( Color.WHITE );
            g.fillRect ( 0, 0, w, h );
            JLabel l = new JLabel ();
            l.setBounds ( 0, 0, w, h );
            l.setText ( lbl.getText () );
            l.paint ( g );
            FileOutputStream os1 = new FileOutputStream ( "test.png" );
            ImageIO.write ( bi, "png", os1 );
            os1.close ();

        } catch ( Exception ex )        {
            ex.printStackTrace();
        }
    }

    public static void main ( String[] args )
    {
        StringBuffer sb = new StringBuffer ();
        for ( int i = 0; i < 50; i++ )
        {
            sb.append ( i );
            sb.append ( " -  SOME TEXT SOME TEXT SOME TEXT  <BR>" );
        }
        SaveHtmlAsImage t = new SaveHtmlAsImage ();
        t.lbl.setText ( "<HTML><BODY>" + sb.toString() + "<BODY></HTML>" );
        Dimension d = t.lbl.getPreferredSize();
        t.w = d.width;
        t.h = d.height;
        System.out.println ( "h=" + t.w + ", h=" + t.h );
        t.saveFile ();
    }

}
