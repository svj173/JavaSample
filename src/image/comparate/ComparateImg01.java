package image.comparate;


import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;


/**
 * Наложение одной картинки на другую.
 *
 * Есть 2 картинки. Нажно наложить картинку №2 на картинку №1.
 * При этом на №2 все пикселы определенного цвета должны быть прозрачными (то есть они должны замениться на цвета картинки №1).
 * Приложение представляет из себя апплет.
 * Картинка №2 хранится в виде объекта класса Image. Картинка №1 на момент выполнения наложения уже нанесена на форму.
 * Как это можно сделать? Если это необходимо, то можно допустить наличие маски картинки №2 (то есть другого объекта Image,
 * у котого все "прозрачные" пикселы картинки №2 черные, а непрозрачные белые).

Все, что я пока разыскал — метод setXORMode класса Graphics. Благодаря его помощи, кажется, проблему можно разрешить в 3 шага:
1. Наложение картинки №2 с использованием XOR
2. Наложение описанной выше маски с использованием AND
3. Наложение картинки №2 с использованием XOR

Но мне пока не удается найти, как можно выполнить шаг 2: наложение картинок с использованием логического AND.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 11:27:41
 */
public class ComparateImg01
{
    public static void main ( String[] args )
    {
        int width, height, some_color, transparent_color;
        Image image;

        width = height = 16;
        some_color  = 5000;
        transparent_color  = 1000;
        image   = null;

        //Мне кажется, в данном случае не обойтись без работы с массивом пикселей. Делается это примерно так:

        try
        {
            int[] pixelArray = new int[width * height];
            // (Image img, int x, int y, int w, int h, int[] pix, int off, int scansize)
            PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixelArray, 0, width );
            grabber.grabPixels();
            ColorModel colorModel = grabber.getColorModel();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < width; y++) {
                    if (pixelArray[x*y] == some_color) {
                        pixelArray[x*y] = transparent_color;
                    }
                }
            }
            MemoryImageSource memSource = new MemoryImageSource(width, height, colorModel, pixelArray, 0, width);
            Image transparentImage = createImage(memSource);
            
        } catch ( Exception e )        {
            e.printStackTrace();
        }
    }

    private static Image createImage ( MemoryImageSource memSource )
    {
        return null;
    }

}
