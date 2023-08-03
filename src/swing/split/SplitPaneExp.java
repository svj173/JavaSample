package swing.split;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.07.12 9:12
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SplitPaneExp extends JFrame
{
    public SplitPaneExp ()
    {
        setTitle ( "Example of Split Pane" );
        setSize ( 150, 150 );

        JPanel jsp1 = new JPanel ();
        JPanel jsp2 = new JPanel ();
        JLabel j1 = new JLabel ( "Area 1" );
        JLabel j2 = new JLabel ( "Area 2" );

        jsp1.add ( j1 );
        jsp2.add ( j2 );

        JSplitPane splitPane = new JSplitPane ( JSplitPane.VERTICAL_SPLIT, true, jsp1, jsp2 );

        splitPane.setOneTouchExpandable ( true );

        // location -- почему-то ничего на экране не меняется. ---- ???

        // Get current location; result is number of pixels from the left edge
        int loc = splitPane.getDividerLocation();

        // 1) Set a new location using an absolution location; center the divider
        //loc = (int)((splitPane.getBounds().getWidth()-splitPane.getDividerSize())/2);
        //splitPane.setDividerLocation(loc);

        // 2) Set a proportional location
        double propLoc = .8D;
        splitPane.setDividerLocation ( propLoc );

        getContentPane().add ( splitPane );
    }

    public static void main ( String[] args )
    {
        SplitPaneExp sp = new SplitPaneExp ();
        sp.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        sp.setVisible ( true );
    }

}

