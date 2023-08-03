package swing.font;

import java.awt.*;

/**
 * <BR> Вывести список фонтов, доступных системе.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 26.04.2006
 * <BR> Time: 14:49:33
 */
public class ListFont
{
    public ListFont ()
    {
    }

    public void list ()
    {
        GraphicsEnvironment ge;
        Font[]  fonts;

        ge  = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts   = ge.getAllFonts ();
        System.out.println ( "font size =  " + fonts.length );

        for ( int i=0; i<fonts.length; i++ )
        {
            System.out.println ( " " + i + ". " + fonts[i] );
        }
    }

    public static void main ( String[] args )
    {
        ListFont    font;
        font = new ListFont ();
        font.list();
    }

}
