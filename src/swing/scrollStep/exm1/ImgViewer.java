package swing.scrollStep.exm1;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;


/**
 * Главный класс.
 * <BR/> Читает директорию, фильтрую только файлы с изображением.
 * <BR/> Создаем фрейм. Слева - табики с уменьшенными иконками изображени. Справа - собственно иконки.
 * <BR/> Везде - скроллинг.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.12.2011 12:15:27
 */
public class ImgViewer
{
    public static void main ( String[] args )
    {
        String imgDir;
        final JFrame jf = new JFrame ( "scroller pic" );

        JTabbedPane jtabs = new JTabbedPane ( JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT );
        // Здесь мы создаем набор закладок. В качестве параметра указывается то,
        // где будут расположены ярлычки этих закладок.
        // Возможны варианты ориентации по всем 4-ем сторонам света.
        // Второй параметр управляет тем, что будет происходить
        // когда закладки не будут помещаться в одну линию.
        // В примере используется режим WRAP_TAB_LAYOUT.
        // Это значит, что закладки будут располагаться в несколько линий.
        // Возможен также и вариант JTabbedPane.SCROLL_TAB_LAYOUT
        // в этом случае появятся кнопки прокрутки.
        jf.getContentPane().add ( jtabs, BorderLayout.CENTER );

        imgDir  = "/home/svj/projects/SVJ/JavaSample/img/icon";
        File fs[] = new File ( imgDir ).listFiles (
                new FileFilter()
                {
                    public boolean accept ( File pathname )
                    {
                        String fileName = pathname.getName ().toLowerCase ();
                        System.out.println ( "-- fileName = " + fileName );
                        boolean b = ( fileName.endsWith ( "jpg" ) ) || ( fileName.endsWith ( "gif" ) ) || ( fileName.endsWith ( "png" ) );
                        System.out.println ( "---- b = " + b );
                        return b;
                    }
                }
        );

        if ( fs != null )
        {
            // создаем табики
            for ( int i = 0; i < fs.length; i++ )
            {
                try
                {
                    File f = fs[ i ];
                    // При добавлении новой закладки следует указать ее название и то какой
                    // Компонент будет к ней привязан
                    ImageIcon image = new ImageIcon ( f.getCanonicalPath() );
                    jtabs.add ( f.getCanonicalPath(), new JScrollPane ( new JLabel ( image ) )
                    );
                    // Теперь я создаю иконку привязанную к ярлыку закладки.
                    // Это уменьшенная до 32*32 px основная картинка
                    jtabs.setIconAt ( i, new ImageIcon ( image.getImage().getScaledInstance ( 32, 32, Image.SCALE_AREA_AVERAGING ) ) );
                } catch ( IOException e )    {
                    e.printStackTrace ();
                }
            }

            jf.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
            jf.pack ();
            SwingUtilities.invokeLater ( new Runnable()
            {
                public void run ()
                {
                    jf.setVisible ( true );
                }
            } );
        }
        else
        {
            System.out.println ( "Img dir '" + imgDir + "' is empty.");
        }
    }

}
