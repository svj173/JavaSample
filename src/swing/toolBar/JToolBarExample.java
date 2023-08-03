package swing.toolBar;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Тулбар с кнопками.
 * <BR/> Есть возможность передвигать тул-бар по фрейму - вверху, внизу, сбоку.
 * <BR/> Естиь возможность отключать текст на кнопках - толкьо иконки.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.10.2011 17:44:09
 */
public class JToolBarExample extends JFrame   implements ItemListener
{
    private BrowserToolBar toolbar;
    private JCheckBox labelBox;

    public static void main ( String[] args )
    {
        new JToolBarExample ();
    }

    public JToolBarExample ()
    {
        super ( "JToolBar Example" );

        WindowUtilities.setNativeLookAndFeel();

        addWindowListener ( new ExitListener () );
        Container content = getContentPane ();
        content.setBackground ( Color.white );
        toolbar = new BrowserToolBar ();
        content.add ( toolbar, BorderLayout.NORTH );
        labelBox = new JCheckBox ( "Show Text Labels?" );
        labelBox.setHorizontalAlignment ( SwingConstants.CENTER );
        labelBox.addItemListener ( this );
        content.add ( new JTextArea ( 10, 30 ), BorderLayout.CENTER );
        content.add ( labelBox, BorderLayout.SOUTH );
        pack ();
        setVisible ( true );
    }

    public void itemStateChanged ( ItemEvent event )
    {
        toolbar.setTextLabels ( labelBox.isSelected () );
        pack ();
    }

}
