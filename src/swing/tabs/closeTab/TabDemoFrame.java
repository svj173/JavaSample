package swing.tabs.closeTab;


/**
 * Иконка закрытия табика.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.01.2013 18:13
 */

import com.jgoodies.looks.common.RGBGrayFilter;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A demo class for showing how to add close buttons to tabs.
 *
 * Кнопка "Добавить новый таб". Добавляется табик с двумя иконками.
 * Иконка с крестиком - неактивная (серая), но когда наводишь мышкой - становится активной (краснеет) и можно нажиамть независимо от того, текущий это табик или нет.
 *
 * @author Tad Harrison
 */
public class TabDemoFrame extends javax.swing.JFrame
{

    private static final Icon CLOSE_TAB_ICON = new ImageIcon ( TabDemoFrame.class.getResource ( "closeTabButton.png" ) );
    private static final Icon PAGE_ICON = new ImageIcon ( TabDemoFrame.class.getResource ( "1353549278_page-edit.png" ) );
    private int tabCount = 0;

    /**
     * Creates new TabDemoFrame
     */
    public TabDemoFrame ()
    {
        initComponents ();
    }

    /**
     * Adds a component to a JTabbedPane with a little "close tab" button on the
     * right side of the tab.
     *
     * Static - т.е. общий фуекционал и можно пользовать извне, для разных таб-панелей.
     *
     * @param tabbedPane the JTabbedPane
     * @param c          any JComponent
     * @param title      the title for the tab
     * @param icon       the icon for the tab, if desired
     */
    public static void addClosableTab ( final JTabbedPane tabbedPane, final JComponent c, final String title, final Icon icon )
    {
        // Add the tab to the pane without any label
        tabbedPane.addTab ( null, c );
        int pos = tabbedPane.indexOfComponent ( c );

        // Create a FlowLayout that will space things 5px apart
        FlowLayout f = new FlowLayout ( FlowLayout.CENTER, 5, 0 );

        // Make a small JPanel with the layout and make it non-opaque
        JPanel pnlTab = new JPanel ( f );
        pnlTab.setOpaque ( false );

        // Add a JLabel with title and the left-side tab icon
        JLabel lblTitle = new JLabel ( title );
        lblTitle.setIcon ( icon );

        // Create a JButton for the close tab button
        JButton btnClose = new JButton ();
        btnClose.setOpaque ( false );

        // Configure icon and rollover icon for button
        btnClose.setRolloverIcon ( CLOSE_TAB_ICON );
        btnClose.setRolloverEnabled ( true );
        btnClose.setIcon ( RGBGrayFilter.getDisabledIcon ( btnClose, CLOSE_TAB_ICON ) );

        // Set border null so the button doesn't make the tab too big
        btnClose.setBorder ( null );

        // Make sure the button can't get focus, otherwise it looks funny
        btnClose.setFocusable ( false );

        // Put the panel together
        pnlTab.add ( lblTitle );
        pnlTab.add ( btnClose );

        // Add a thin border to keep the image below the top edge of the tab
        // when the tab is selected
        pnlTab.setBorder ( BorderFactory.createEmptyBorder ( 2, 0, 0, 0 ) );

        // Now assign the component for the tab
        tabbedPane.setTabComponentAt ( pos, pnlTab );

        // Add the listener that removes the tab
        ActionListener listener = new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                // The component parameter must be declared "final" so that it can be
                // referenced in the anonymous listener class like this.
                tabbedPane.remove ( c );
            }
        };
        btnClose.addActionListener ( listener );

        // Optionally bring the new tab to the front
        tabbedPane.setSelectedComponent ( c );

        //-------------------------------------------------------------
        // Bonus: Adding a <Ctrl-W> keystroke binding to close the tab
        //-------------------------------------------------------------
        AbstractAction closeTabAction = new AbstractAction ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                tabbedPane.remove ( c );
            }
        };

        // Create a keystroke
        KeyStroke controlW = KeyStroke.getKeyStroke ( "control W" );

        // Get the appropriate input map using the JComponent constants.
        // This one works well when the component is a container.
        InputMap inputMap = c.getInputMap ( JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );

        // Add the key binding for the keystroke to the action name
        inputMap.put ( controlW, "closeTab" );

        // Now add a single binding for the action name to the anonymous action
        c.getActionMap ().put ( "closeTab", closeTabAction );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings ( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents ()
    {

        tabbedPane = new javax.swing.JTabbedPane ();
        jScrollPane1 = new javax.swing.JScrollPane ();
        jTextArea1 = new javax.swing.JTextArea ();
        buttonPanel = new javax.swing.JPanel ();
        createTabButton = new javax.swing.JButton ();

        setDefaultCloseOperation ( javax.swing.WindowConstants.EXIT_ON_CLOSE );
        setPreferredSize ( new java.awt.Dimension ( 800, 400 ) );

        jTextArea1.setEditable ( false );
        jTextArea1.setColumns ( 20 );
        jTextArea1.setLineWrap ( true );
        jTextArea1.setRows ( 5 );
        jTextArea1.setText ( "This tab cannot be closed.\n\nClick on the \"Add New Tab\" button a few times.\n\nTry closing the tabs with the small buttons.\nTry closing the tabs with <Ctrl-W>." );
        jScrollPane1.setViewportView ( jTextArea1 );

        tabbedPane.addTab ( "About", jScrollPane1 );

        getContentPane ().add ( tabbedPane, java.awt.BorderLayout.CENTER );

        createTabButton.setText ( "Add New Tab" );
        createTabButton.addActionListener ( new java.awt.event.ActionListener ()
        {
            public void actionPerformed ( java.awt.event.ActionEvent evt )
            {
                createTabButtonActionPerformed ( evt );
            }
        } );
        buttonPanel.add ( createTabButton );

        getContentPane ().add ( buttonPanel, java.awt.BorderLayout.SOUTH );

        pack ();
    }// </editor-fold>

    private void createTabButtonActionPerformed ( java.awt.event.ActionEvent evt )
    {
        System.out.println ( "Add new tab!" );
        tabCount++;
        JScrollPane scrollPane = new JScrollPane ( new JTextArea ( "New tab number " + tabCount ) );
        Icon icon = PAGE_ICON;
        addClosableTab ( tabbedPane, scrollPane, "Tab " + tabCount, icon );
    }

    /**
     * @param args the command line arguments
     */
    public static void main ( String args[] )
    {
        try
        {
            PlasticLookAndFeel laf = new Plastic3DLookAndFeel ();
            PlasticLookAndFeel.setCurrentTheme ( new ExperienceBlue () );
            UIManager.setLookAndFeel ( laf );
        } catch ( javax.swing.UnsupportedLookAndFeelException ex )
        {
            ex.printStackTrace ();
        }

        java.awt.EventQueue.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                new TabDemoFrame ().setVisible ( true );
            }
        } );
    }

    // Variables declaration - do not modify
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton createTabButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration
}
