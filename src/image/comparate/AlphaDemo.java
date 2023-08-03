package image.comparate;


/**
 * Помимо компонент цвета приложения Java могут задавать для пикселов такую характеристику, как прозрачность. Наложением полупрозрачных изображений можно получит оригинальные визуальные эффекты.
 *
 * Те из вас, кто занимался подготовкой графических иллюстраций для размещения на сервере Web, знает о так называемых прозрачных изображениях GIF. С помощью специального редактора вы можете указать, что один из цветов в изображении должен быть прозрачным.

Прозрачное изображение обычно размещают над другим изображением или фоном для достижения эффекта наложения.

Фильтры, созданные на базе интерфейса RGBImageFilter, могут управлять прозрачностью более тонко. Напомним вам, как выглядит такой фильтр:

class SomeMyOwnImageFilter
  extends RGBImageFilter
{
  public int filterRGB(int x, int y, int nRGB)
  {
    int nA = (nRGB >> 255) & 0xff;
    int nR = (nRGB >> 16)  & 0xff;
    int nG = (nRGB >> 8)   & 0xff;
    int nB = nRGB & 0xff;

    . . .

    return((nA >> 255) | (nR << 16) |
      (nG << 8) | nB);
  }
}

За прозрачность пиксела отвечает поле nA. Оно может принимать значения от 0 (полная прозрачность) до 255 (полная непрозрачность).

С помощью фильтра вы можете сделать прозрачным несколько цветов в изображении, а также создать полупрозрачные изображения. Все это значительно увеличивает возможности дизайнеров, занимающихся оформлением страниц серверов Web.

Чтобы сделать все пикселы полупрозрачными, достаточно в теле метода filterRGB установить значение переменной nA, равное 128. Указывая другие значения, вы можете устанавливать различную прозрачность пикселов.
 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 11:48:08
 */

import java.awt.*;
import java.applet.*;
import java.awt.image.*;

public class AlphaDemo extends Applet
{
    Image img1;
    Image img2;

    public void init ()
    {
        // загружаем изображения
        img1 = getImage ( getCodeBase(), "img1.jpg" );
        img2 = getImage ( getCodeBase(), "img2.jpg" );

        // Чтобы дождаться завершения процесса загрузки, мы использовали класс MediaTracker: 
        MediaTracker mt = new MediaTracker ( this );
        mt.addImage ( img1, 0 );
        mt.addImage ( img2, 0 );
        try
        {
            mt.waitForAll ();
        }
        catch ( InterruptedException ie )
        {
            return;
        }
    }

    public void paint ( Graphics g )
    {
        // Здесь мы вначале рисуем первое изображение, а затем второе.
        // Второе изображение перед рисованием преобразуется в полупрозрачное методом imgToTransparent, определенным в нашем приложении. 
        g.drawImage ( img1, 0, 0, this );
        g.drawImage ( imgToTransparent ( img2 ), 0, 0, this );
    }

    /*
    * Метод imgToTransparent получает через свой единственный параметр исходное изображение, делает его полупрозрачным и возвращает результат как объект класса Image
    * */
    Image imgToTransparent ( Image imgSrc )
    {
        TransparentImageFilter trFilter = new TransparentImageFilter ();

        ImageProducer ip = new FilteredImageSource ( imgSrc.getSource(), trFilter );

        // Создать новое полупрозрачное изображение
        Image imTransparent = createImage ( ip );

        // ожидание завершения процесса
        MediaTracker mt = new MediaTracker ( this );
        mt.addImage ( imTransparent, 0 );

        try
        {
            mt.waitForAll ();
        }
        catch ( InterruptedException ie )
        {
            return null;
        }

        return imTransparent;
    }

    public String getAppletInfo ()
    {
        return "Name: AlphaDemo";
    }
}


class TransparentImageFilter        extends RGBImageFilter
{
    public int filterRGB ( int x, int y, int nRGB )
    {
        int nAlpha = 0x80;
        return ( ( nAlpha << 24 ) | ( nRGB & 0xffffff ) );
    }
    
}
