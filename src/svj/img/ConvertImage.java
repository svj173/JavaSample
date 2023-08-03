package svj.img;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Преобразовать изображение в строку base64 и обратно.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 14.07.2017 12:22
 */
public class ConvertImage
{
    public String imgToStr ( BufferedImage image, String imgType )  throws IOException
    {
        String result;
        ByteArrayOutputStream baos;

        baos = new ByteArrayOutputStream(1000);
      	//	BufferedImage image = ImageIO.read(new File(directory, "imageFile.jpg"));

        // явно указываем расширение файла для простоты реализации
        ImageIO.write(image, imgType, baos);
        baos.flush ();

        // byte[] bb = baos.toByteArray();

        //result = Base64.encode(baos.toByteArray());         // com.sun.org.apache.xerces.internal.impl.dv.util
        //result = Base64Utils.encode(baos.toByteArray());     com.google
        result = java.util.Base64.getEncoder().encodeToString ( baos.toByteArray() );
        baos.close();

        // декодируем полученную строку в массив байт
        //byte[] resByteArray = Base64.decode(base64String);

        return result;
    }

    public String imgToStr ( Image image, String imgType ) throws IOException
    {
        BufferedImage bi;

        bi = createBufferedImageFrom ( image );
        return imgToStr ( bi, imgType );
    }

    public Image strToImg ( String base64String )   throws IOException
    {
                   // декодируем полученную строку в массив байт
   		byte[] resByteArray = Base64.decode(base64String);

                   // считываем полученный массив в объект BufferedImage
   		BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));

                   // сохраняем объект BufferedImage в виде нового изображения
   		//ImageIO.write(resultImage, "jpg", new File(directory,"resultImage.jpg"));

        return resultImage;
    }

    private BufferedImage createBufferedImageFrom ( final Image image )
    {
        if ( image instanceof BufferedImage )
        {
            return ( BufferedImage ) image;
        }
        else
        {
            final BufferedImage bi = new BufferedImage ( image.getWidth ( null ), image.getHeight ( null ),
                                                         BufferedImage.TYPE_INT_RGB );
            final Graphics2D g2 = bi.createGraphics ();
            g2.drawImage ( image, 0, 0, null );
            return bi;
        }
    }

    public static void main(String[] args) throws IOException
    {
        /*
   		String directory = "F:\\";
   		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
   		BufferedImage image = ImageIO.read(new File(directory, "imageFile.jpg"));

                   // явно указываем расширение файла для простоты реализации
   		ImageIO.write(image, "jpg", baos);
   		baos.flush();

   		String base64String = Base64.encode(baos.toByteArray());
   		baos.close();

                   // декодируем полученную строку в массив байт
   		byte[] resByteArray = Base64.decode(base64String);

                   // считываем полученный массив в объект BufferedImage
   		BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(resByteArray));

                   // сохраняем объект BufferedImage в виде нового изображения
   		ImageIO.write(resultImage, "jpg", new File(directory,"resultImage.jpg"));
        */

        ConvertImage converter;
        String fileName, base64, imgType;
        BufferedImage image;

        fileName = "/home/svj/Serg/Photo/Novosibirsk/6mkr/6mkr_2010-06-24_02.JPG";   //2.1mB
        //fileName = "/home/svj/Serg/Photo/Novosibirsk/panoram_holodilnay_vladimirskaia.jpg";   // 87112 символов в строке base64    -- file size = 96053
        //fileName = "/home/svj/projects/Eltex/eltex-web-ems/gui/src/main/resources/org/eltex/ems/web/gui/images/xml.png";   // 1kB -  1168 символов

        imgType = "jpg";
        //imgType = "png";

        File file = new File (fileName);
        System.out.println ( "fileName = " + fileName );
        System.out.println ( "file size = " + file.length() );
        image = ImageIO.read ( file );

        converter = new ConvertImage();
        base64    = converter.imgToStr ( image, imgType );

        System.out.println ( "length = " + base64.length() );
        System.out.println ( base64 );

        // 87112 - jpg

        // Обратное преобразование
        byte[]  imageBytes;
        //imageBytes = Base64.decode ( base64 );       // com.sun.org.apache.xml.internal.security.utils - вместо 87112 возвращает 65333 - т.е. обрезка до 65333
        imageBytes = java.util.Base64.getDecoder().decode ( base64 );       // вместо 87112 возвращает 65333 - т.е. обрезка до 65333
        System.out.println ( "imageBytes length = " + imageBytes.length );   // 65333 - т.е. обрезка до 65333

        converter.saveImg ( imageBytes, "/home/svj/tmp/123img.jpg", imgType );
   	}

    private void saveImg ( byte[] imageBytes, String fileName, String imgType )  throws IOException
    {
                   // считываем полученный массив в объект BufferedImage
   		BufferedImage resultImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

                   // сохраняем объект BufferedImage в виде нового изображения
   		ImageIO.write(resultImage, "jpg", new File(fileName) );
    }

}
