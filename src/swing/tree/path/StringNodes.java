package swing.tree.path;


import javax.swing.*;
import java.awt.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.01.2013 14:29
 */
public class StringNodes
{
    public static void main ( String[] args ) throws Exception
    {
        String[]    buf;
        JFrame      frame;
        JTree       tree;

        buf    = new String[5];
        buf[0] = "IGW.node1.node1_2.node123.";
        buf[1] = "IGW.node1.node1_2.node123.aaa0";
        buf[2] = "IGW.node2.node1_2.aaa20";
        buf[3] = "IGW.node2.node2_2.aaa30";
        buf[4] = "IGW.node2.node2_2.aaa40";

        tree   = createTree ( buf, "IGW" );
        frame  = createFrame ( tree );

        frame.pack();

        frame.setVisible ( true );
    }

    private static JTree createTree ( String[] buf, String rootName )
    {
        JTree    result;
        TreeObj  treeItem, root, to;
        String[] path;

        root = new TreeObj ( rootName );

        try
        {
            for ( String str : buf )
            {
                System.out.println ( "- name = " + str );
                path    = str.split ( "\\." );      // split("\u002e");   split("\0x2e");   split("\\.");
                System.out.println ( "-- path size = " + path.length );
                if ( path != null )
                {
                    treeItem = root;
                    System.out.println ( "----------------- " );
                    for ( String s : path )
                    {
                        System.out.println ( "--- subnode name = " + s );
                        if ( s.equals ( rootName )) continue;
                        //treeItem.getChildAt (  )
                        to  = treeItem.getChildByName ( s );
                        System.out.println ( "----- subnode to = " + to );
                        if ( to == null )
                        {
                            // Создать новый
                            to = new TreeObj ( s );
                            treeItem.add ( to );
                            System.out.println ( "------- add new to = " + to );
                            System.out.println ( "------- to item = " + treeItem );
                        }
                        treeItem = to;
                    }
                }
            }
        } catch ( Exception e )         {
            e.printStackTrace();
        }

        result  = new JTree ( root );
        result.setCellRenderer ( new TreeObjRenderer() );

        return result;
    }

    private static JFrame createFrame ( JTree tree )
    {
        JFrame result;

        result = new JFrame();
        result.setLayout ( new BorderLayout() );

        result.add ( tree, BorderLayout.WEST );

        result.setPreferredSize ( new Dimension ( 400, 300 ) );

        return result;
    }

}
