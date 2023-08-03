package swing.toolBar;


/**
 * Customizing Tool Bars.
 *
 *    1. Using setFloatable(false) to make a tool bar immovable.
 2. Using setRollover(true) to make the edges of the buttons invisible when mouse pointer is out.
 3. Adding a separator to a tool bar.
 4. Adding a non-button component to a tool bar.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 23.12.2011 11:24:14
 */

import javax.swing.*;

public class AddintJToolBarToJFrame
{

    public static void main ( String[] a )
    {
        JFrame frame;
        JButton button;

        frame = new JFrame ();
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        JToolBar toolBar = new JToolBar ( "Still draggable" );

        toolBar.setFloatable ( false );
        toolBar.setRollover ( true );

        button  = new JButton ( "New" );
        toolBar.add ( button  );

        button  = new JButton ( "2" );
        button.setHorizontalAlignment ( SwingConstants.RIGHT );
        toolBar.add ( button  );

        frame.add ( toolBar, "North" );

        frame.setSize ( 300, 200 );
        frame.setVisible ( true );
    }

}
