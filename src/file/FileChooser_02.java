package file;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


/**
 * Применение настроек.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.06.2011 11:55:17
 */
public class FileChooser_02
{
    public static void main ( String[] args )
    {
        JFrame frame = new JFrame("File Chooser demo");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                System.exit(0);
            }
        });

        // label to attach image icon to
        final JLabel label =
            new JLabel("", SwingConstants.CENTER);

        JPanel panel1 = new JPanel();
        // start filechooser in current directory
        String cwd = System.getProperty("user.dir");
        final JFileChooser fc = new JFileChooser(cwd);

        //    JFileChooser fc = new JFileChooser ( lastDirectory );
            fc.setDialogType ( JFileChooser.OPEN_DIALOG );
            fc.setDialogTitle ( "Выберите директорию в которой произвести поиск JRE" );
            fc.setMultiSelectionEnabled ( false );
            fc.setAcceptAllFileFilterUsed ( true );
            fc.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
            fc.setFileFilter ( new FileFilter()
            {
                public boolean accept ( File f )
                {
                    return f.isDirectory ();
                }

                public String getDescription ()
                {
                    return "Директории";
                }
            } );

        fc.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e)
            {
                // set label's icon to
                // the current image
                String state = e.getActionCommand();
                if (!state.equals(  JFileChooser.APPROVE_SELECTION))                     return;
                /*
                // Сменить иконку на кнопке
                File f = fc.getSelectedFile();
                if (f == null || !f.isFile())
                    return;
                ImageIcon icon = new ImageIcon(f.getPath());
                label.setIcon(icon);
                */
            }
        });

        /*
            if ( fc.showDialog ( Designer.this, "Выбрать" ) == JFileChooser.APPROVE_OPTION )
            {
                File folder = fc.getSelectedFile();
                //processDir ( folder );
            }
            */

        panel1.add(fc);

        JPanel panel2 = new JPanel();
        // max size of label whose icon displays image
        label.setPreferredSize(new Dimension (500, 300));
        panel2.add(label);

        frame.getContentPane().add("North", panel1);
        frame.getContentPane().add("South", panel2);
        frame.pack();
        frame.setVisible(true);

    }

}
