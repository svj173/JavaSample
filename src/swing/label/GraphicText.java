package swing.label;


/**
 * Проблема правильного определения длины текста в пикселях, согласно фонам или применения HTML.
 * <BR/>
 * <BR/> необходимость иметь graphics не случайна - именно он управляет отрисовкой текста.
 * В том числе и расстоянием между символами. Таким образом разные graphics отрисовывая один и тот же текст, могут дать разную длину.

Вывод: надо получать объект graphics у того компонента, на котором собираешься рисовать текст.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 14:33:23
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GraphicText
{

    private static BufferedImage hiddenImg; //будущий объект изображения, который любезно предоставит объект Graphics
    private static Font gfont;    //шрифт
    private static Graphics gs;   //то что нужно =)

    //установка шрифта
    public static void setFont(Font font){
        gfont = font;
    }

    //установка объекта Graphics от компонена на котором будет выводиться текст
    public static void setGraphics(Graphics g){
        if(g != null){
            gs = g;
        }
    }

    //статичный метод 1
    public static Dimension getTextPxSize(Font font, String text){
        //создаем Graphics из BufferedImage если он не был установлен методом setGraphics
        if(gs == null){
            if(hiddenImg == null){
                hiddenImg = new BufferedImage(100,100,BufferedImage.TYPE_INT_ARGB);
                gs = hiddenImg.getGraphics();
            }
        }
        FontMetrics fm = gs.getFontMetrics(font);
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        Dimension dm = new Dimension(width,height);
        return dm;
    }//end method

    //статичный метод 2 для предустановленного шрифта
    public static Dimension getTextPxSize(String text){
        if(gfont != null){
            return getTextPxSize(gfont, text);
        }
        return null;
    }//end method

    //статичный метод 3 для предустановленного шрифта
    public static int getTextPxHeight(){
        if(gfont != null){
            return getTextPxSize(gfont, "A").height;
        }
        return 0;
    }

    //статичный метод 4 для предустановленного шрифта
    public static int getTextPxWidth(String text){
        if(gfont != null){
            return getTextPxSize(gfont, text).width;
        }
        return 0;
    }

}//end class
