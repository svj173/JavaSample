package swing.popup;


/**
 * создает контекстное меню - только на текстовой компоненте.
 * <BR/> использует механизм стандартных акций.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2011 14:53:30
 */

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class TCPopupEventQueue extends EventQueue
{
    public JPopupMenu popup;
    JTable table;
    public BasicAction cut, copy, paste, selectAll;

    public TCPopupEventQueue ()
    {
        //createPopupMenu();
    }

    public void createPopupMenu ( JTextComponent text )
    {
        cut = new CutAction ( "Cut", null );
        copy = new CopyAction ( "Copy", null );
        paste = new PasteAction ( "Paste", null );
        selectAll = new SelectAllAction ( "Select All", null );
        cut.setTextComponent ( text );
        copy.setTextComponent ( text );
        paste.setTextComponent ( text );
        selectAll.setTextComponent ( text );

        popup = new JPopupMenu ();
        popup.add ( cut );
        popup.add ( copy );
        popup.add ( paste );
        popup.addSeparator ();
        popup.add ( selectAll );
    }

    public void showPopup ( Component parent, MouseEvent me )
    {
        popup.validate ();
        popup.show ( parent, me.getX (), me.getY () );
    }

    protected void dispatchEvent ( AWTEvent event )
    {
        super.dispatchEvent ( event );
        if ( !( event instanceof MouseEvent ) )
        {
            return;
        }
        MouseEvent me = ( MouseEvent ) event;
        if ( !me.isPopupTrigger () )
        {
            return;
        }
        if ( !( me.getSource () instanceof Component ) )
        {
            return;
        }
        Component comp = SwingUtilities.getDeepestComponentAt ( ( Component )
                me.getSource (), me.getX (), me.getY () );
        if ( !( comp instanceof JTextComponent ) )
        {
            return;
        }
        if ( MenuSelectionManager.defaultManager ().getSelectedPath ().length > 0 )
        {
            return;
        }
        createPopupMenu ( ( JTextComponent ) comp );
        showPopup ( ( Component ) me.getSource (), me );
    }

    public abstract class BasicAction extends AbstractAction
    {
        JTextComponent comp;

        public BasicAction ( String text, Icon icon )
        {
            super ( text, icon );
            putValue ( Action.SHORT_DESCRIPTION, text );
        }

        public void setTextComponent ( JTextComponent comp )
        {
            this.comp = comp;
        }

        public abstract void actionPerformed ( ActionEvent e );
    }

    public class CutAction extends BasicAction
    {
        public CutAction ( String text, Icon icon )
        {
            super ( text, icon );
            putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "ctrl X" ) );
        }

        public void actionPerformed ( ActionEvent e )
        {
            comp.cut ();
        }

        public boolean isEnabled ()
        {
            return comp != null && comp.isEditable () && comp.getSelectedText () != null;
        }
    }

    public class CopyAction extends BasicAction
    {
        public CopyAction ( String text, Icon icon )
        {
            super ( text, icon );
            putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "ctrl C" ) );
        }

        public void actionPerformed ( ActionEvent e )
        {
            comp.copy ();
        }

        public boolean isEnabled ()
        {
            return comp != null && comp.getSelectedText () != null;
        }
    }

    public class PasteAction extends BasicAction
    {
        public PasteAction ( String text, Icon icon )
        {
            super ( text, icon );
            putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "ctrl V" ) );
        }

        public void actionPerformed ( ActionEvent e )
        {
            comp.paste ();
        }

        public boolean isEnabled ()
        {
            Transferable content = Toolkit.getDefaultToolkit ().getSystemClipboard ()
                    .getContents ( null );
            return comp != null && comp.isEnabled () && comp.isEditable ()
                    && content.isDataFlavorSupported ( DataFlavor.stringFlavor );
        }
    }

    public class SelectAllAction extends BasicAction
    {
        public SelectAllAction ( String text, Icon icon )
        {
            super ( text, icon );
            putValue ( Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( "ctrl A" ) );
        }

        public void actionPerformed ( ActionEvent e )
        {
            comp.selectAll ();
        }

        public boolean isEnabled ()
        {
            return comp != null && comp.isEnabled () && comp.getText ().length () > 0
                    && ( comp.getSelectedText () == null ||
                    comp.getSelectedText ().length () < comp.getText ().length () );
        }
    }

    public static void main ( String[] args )
    {
        Toolkit.getDefaultToolkit ().getSystemEventQueue ().push ( new TCPopupEventQueue () );
        JTextField field = new JTextField ( 20 );
        JTextField field1 = new JTextField ( 20 );
        JPanel center = new JPanel ( new FlowLayout ( FlowLayout.LEFT ) );

        center.add ( new JLabel ( "cell1:" ) );
        center.add ( field );
        center.add ( new JLabel ( "cell2:" ) );
        center.add ( field1 );
        JPanel content = new JPanel ( new FlowLayout ( FlowLayout.LEFT ) );
        content.add ( center, BorderLayout.SOUTH );
        JFrame frame = new JFrame ( "cell copy past program" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        frame.setContentPane ( content );
        frame.setSize ( 550, 100 );
        frame.setVisible ( true );
    }
}