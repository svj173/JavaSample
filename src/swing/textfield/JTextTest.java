package swing.textfield;


import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * Необходимо отслеживать изменения текста в JTextField.
 * <BR/> Двумя способами:
 * <BR/> 1) Через акцию на изменение картеки-курсора - addCaretListener
 * <BR/> 2) Через акции на Документе - addDocumentListener
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.04.2015 14:55
 */
public class JTextTest
{
    public static void main ( String[] args )
    {
        JFrame jf = new JFrame ( "JTextTest" );
        jf.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        final JTextField jtf = new JTextField ( "Some text..." );

        jtf.addCaretListener ( new CaretListener ()
        {
            public void caretUpdate ( CaretEvent e )
            {
                System.out.println ( "Caret now at = " + e.getDot () );
            }
        } );

        DocumentListener dl = new DocumentListener ()
        {
            public void insertUpdate ( DocumentEvent e )
            {
                System.out.println ( "Was insert..." );
            }

            public void removeUpdate ( DocumentEvent e )
            {
                System.out.println ( "Was remove..." );
            }

            public void changedUpdate ( DocumentEvent e )
            {
                System.out.println ( "Was change..." );
            }
        };
        jtf.getDocument ().addDocumentListener ( dl );


        jf.add ( jtf );
        jf.setSize ( 150, 100 );
        jf.setVisible ( true );
    }
}
