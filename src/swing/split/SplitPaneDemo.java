package swing.split;


/**
 * The constructor used by this example takes three arguments. The first indicates the split direction. The other arguments are the two components to put in the split pane. Refer to Setting the Components in a Split Pane for information about JSplitPane methods that set the components dynamically.

 The split pane in this example is split horizontally — the two components appear side by side — as specified by the JSplitPane.HORIZONTAL_SPLIT argument to the constructor. Split pane provides one other option, specified with JSplitPane.VERTICAL_SPLIT, that places one component above the other. You can change the split direction after the split pane has been created with the setOrientation method.

 Two small arrows appear at the top of the divider in the example's split pane.
 These arrows let the user collapse (and then expand) either of the components with a single click. The current look and feel determines whether these controls appear by default. In the Java look and feel, they are turned off by default. (Note that not all look and feels support this.) The example turned them on using the setOneTouchExpandable method.

 To make your split pane work well, you often need to set the minimum sizes of components in the split pane, as well as the preferred size of either the split pane or its contained components. Choosing which sizes you should set is an art that requires understanding how a split pane's preferred size and divider location are determined. Before we get into details, let's take another look at SplitPaneDemo. Or, if you're in a hurry, you can skip to the list of rules.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.07.12 8:45
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

//SplitPaneDemo itself is not a visible component.
public class SplitPaneDemo extends JPanel
        implements ListSelectionListener
{
    private JLabel picture;
    private JList list;
    private JSplitPane splitPane;
    private String[] imageNames = {
            "Bird", "Cat", "Dog", "Rabbit", "Pig", "dukeWaveRed",
            "kathyCosmo", "lainesTongue", "left", "middle", "right", "stickerface" };

    public SplitPaneDemo ()
    {
        // Create the list of images and put it in a scroll pane.

        list = new JList ( imageNames );
        list.setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
        list.setSelectedIndex ( 0 );
        list.addListSelectionListener ( this );

        JScrollPane listScrollPane = new JScrollPane ( list );

        picture = new JLabel ();
        picture.setFont ( picture.getFont ().deriveFont ( Font.ITALIC ) );
        picture.setHorizontalAlignment ( JLabel.CENTER );

        JScrollPane pictureScrollPane = new JScrollPane ( picture );

        // Create a split pane with the two scroll panes in it.
        splitPane = new JSplitPane ( JSplitPane.HORIZONTAL_SPLIT,
                                     listScrollPane, pictureScrollPane );
        splitPane.setOneTouchExpandable ( true );
        splitPane.setDividerLocation ( 150 );

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension ( 100, 50 );
        listScrollPane.setMinimumSize ( minimumSize );
        pictureScrollPane.setMinimumSize ( minimumSize );

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize ( new Dimension ( 400, 200 ) );

        updateLabel ( imageNames[ list.getSelectedIndex () ] );
    }

    //Listens to the list
    public void valueChanged ( ListSelectionEvent e )
    {
        JList list = ( JList ) e.getSource ();
        updateLabel ( imageNames[ list.getSelectedIndex () ] );
    }

    //Renders the selected image
    protected void updateLabel ( String name )
    {
        ImageIcon icon;

        //icon = createImageIcon ( "/home/svj/projects/SVJ/JavaSample/img/" + name + ".gif" );
        icon = createImageIcon ( "../img/" + name + ".gif" );

        picture.setIcon ( icon );
        if ( icon != null )
        {
            picture.setText ( null );
        }
        else
        {
            picture.setText ( "Image not found" );
        }
    }

    //Used by SplitPaneDemo2
    public JList getImageList ()
    {
        return list;
    }

    public JSplitPane getSplitPane ()
    {
        return splitPane;
    }


    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected static ImageIcon createImageIcon ( String path )
    {
        java.net.URL imgURL = SplitPaneDemo.class.getResource ( path );
        if ( imgURL != null )
        {
            return new ImageIcon ( imgURL );
        }
        else
        {
            System.err.println ( "Couldn't find file: " + path );
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI ()
    {

        //Create and set up the window.
        JFrame frame = new JFrame ( "SplitPaneDemo" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        SplitPaneDemo splitPaneDemo = new SplitPaneDemo ();
        frame.getContentPane ().add ( splitPaneDemo.getSplitPane () );

        //Display the window.
        frame.pack ();
        frame.setVisible ( true );
    }

    public static void main ( String[] args )
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater ( new Runnable ()
        {
            public void run ()
            {
                createAndShowGUI ();
            }
        } );
    }


}