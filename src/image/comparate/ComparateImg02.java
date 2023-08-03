package image.comparate;


import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Наложение одной картинки на другую.
 *
 * изображение Jpeg которое формируется из Oracle базы. В базе оно лижит в виде Blobа.
 * надо на это изображение сверху наложить ещё одно изображение, ну или хотя бы просто сверху нарисовать полоску, так чтобы первоночальное изображение было целиком видно.

 1. Прочитать из базы Blob в виде потока.
2. Прочитать и преобразовать поток в картинку с помощью ImageIO.read()
3. Нарисовать все что нужно на полученной картинке
4. Сохранить данные картинки в виде потока
5. Созранить поток в базу в виде Blob (обычно preparedStatement.setBytes())
 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 11:27:41
 */
public class ComparateImg02
{
    public static void main ( String[] args )
    {
        BufferedImage bimg;

        int width, height, some_color, transparent_color;
        Image image;

        width = height = 16;
        some_color  = 5000;
        transparent_color  = 1000;
        image   = null;

        //Мне кажется, в данном случае не обойтись без работы с массивом пикселей. Делается это примерно так:

        try
        {
            /*
            response.setContentType("image/jpeg");
            InputStream in = photo.getBinaryStream( );
            BufferedImage bimg = ImageIO.read(in);
            Graphics2D g2d = bimg.createGraphics();
            g2d.setColor(Color.red);
            g2d.drawLine(0, 0, bimg.getWidth(), bimg.getHeight());
            g2d.drawLine(0,bimg.getHeight(),bimg.getWidth(),0);
            g2d.dispose();
            ImageIO.write(bimg,"jpeg",out);
            in.close( );
            out.flush( );
            out.close();
                        */

            //ImageIO.
            //bimg.
        } catch ( Exception e )        {
            e.printStackTrace();
        }
    }

}
