package file.showImg;


/**
 * Открываем диалог выборки файла с доп функциями.
 * <BR/> 1) Показывать выбранную картинку справа.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.06.2011 12:17:28
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/*
 * FileChooserDemo2.java is a 1.4 example that requires these files:
 *   ImageFileView.java
 *   ImageFilter.java
 *   ImagePreview.java
 *   Utils.java
 *   images/jpgIcon.gif (required by ImageFileView.java)
 *   images/gifIcon.gif (required by ImageFileView.java)
 *   images/tiffIcon.gif (required by ImageFileView.java)
 *   images/pngIcon.png (required by ImageFileView.java)
 */
public class FileChooserDemo2 extends JPanel
        implements ActionListener
{
    static private String newline = "\n";
    private JTextArea log;
    private JFileChooser fc;

    public FileChooserDemo2 ()
    {
        //Create the log first, because the action listener
        //needs to refer to it.
        log = new JTextArea ( 5, 20 );
        log.setMargin ( new Insets ( 5, 5, 5, 5 ) );
        log.setEditable ( false );
        JScrollPane logScrollPane = new JScrollPane ( log );

        JButton sendButton = new JButton ( "Attach..." );
        sendButton.addActionListener ( this );

        setLayout ( new BorderLayout () );
        add ( sendButton, BorderLayout.PAGE_START );
        add ( logScrollPane, BorderLayout.CENTER );
    }

    public void actionPerformed ( ActionEvent e )
    {
        // Set up the file chooser.
        if ( fc == null )
        {
            fc = new JFileChooser ();

            // Add a custom file filter and disable the default
            // (Accept All) file filter.
            fc.addChoosableFileFilter ( new ImageFilter() );
            fc.setAcceptAllFileFilterUsed ( false );

            // Add custom icons for file types.
            fc.setFileView ( new ImageFileView() );

            // Add the preview pane. -- Показывать выбранную картинку справа.
            fc.setAccessory ( new ImagePreview ( fc ) );
        }

        // Show it.
        int returnVal = fc.showDialog ( FileChooserDemo2.this,
                                        "Attach" );

        // Process the results.
        if ( returnVal == JFileChooser.APPROVE_OPTION )
        {
            File file = fc.getSelectedFile ();
            log.append ( "Attaching file: " + file.getName ()
                    + "." + newline );
        }
        else
        {
            log.append ( "Attachment cancelled by user." + newline );
        }

        // Reset the file chooser for the next time it's shown.
        fc.setSelectedFile ( null );
    }

    public static void main ( String[] args )
    {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated ( true );
        JDialog.setDefaultLookAndFeelDecorated ( true );

        //Create and set up the window.
        JFrame frame = new JFrame ( "FileChooserDemo2" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane ( new FileChooserDemo2 () );

        //Display the window.
        frame.pack ();
        frame.setVisible ( true );
    }
}
