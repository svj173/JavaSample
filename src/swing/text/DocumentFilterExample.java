package swing.text;


/**
 * Преобразование текста в верхний регистр (автоматом).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2011 15:16:30
 */

import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.AbstractDocument;
import javax.swing.*;
import java.awt.*;

public class DocumentFilterExample extends JFrame
{
    public DocumentFilterExample () throws HeadlessException
    {
        initComponents ();
    }

    protected void initComponents ()
    {
        setSize ( 200, 200 );
        setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        getContentPane ().setLayout ( new FlowLayout ( FlowLayout.LEFT ) );

        DocumentFilter filter = new UppercaseDocumentFilter ();

        JTextField firstName = new JTextField ();
        firstName.setPreferredSize ( new Dimension ( 100, 20 ) );
        ( ( AbstractDocument ) firstName.getDocument () ).setDocumentFilter ( filter );

        JTextField lastName = new JTextField ();
        lastName.setPreferredSize ( new Dimension ( 100, 20 ) );
        ( ( AbstractDocument ) lastName.getDocument () ).setDocumentFilter ( filter );

        getContentPane ().add ( firstName );
        getContentPane ().add ( lastName );
    }

    public static void main ( String[] args )
    {
        SwingUtilities.invokeLater ( new Runnable()
        {
            public void run ()
            {
                new DocumentFilterExample ().setVisible ( true );
            }
        } );
    }

    class UppercaseDocumentFilter extends DocumentFilter
    {
        //
        // Override insertString method of DocumentFilter to make the text format
        // to uppercase.
        //
        public void insertString ( DocumentFilter.FilterBypass fb, int offset,
                                   String text, AttributeSet attr )
                throws BadLocationException
        {

            fb.insertString ( offset, text.toUpperCase (), attr );
        }

        //
        // Override replace method of DocumentFilter to make the text format
        // to uppercase.
        //
        public void replace ( DocumentFilter.FilterBypass fb, int offset, int length,
                              String text, AttributeSet attrs )
                throws BadLocationException
        {

            fb.replace ( offset, length, text.toUpperCase (), attrs );
        }
    }
}
