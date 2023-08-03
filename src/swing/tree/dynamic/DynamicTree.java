package swing.tree.dynamic;


import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;

/**
 * Здесь также принудительно меняем название любого но не выбранного узла.
 */
public class DynamicTree extends JPanel
{
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit ();

    public DynamicTree ()
    {
        super ( new GridLayout ( 1, 0 ) );

        rootNode = new DefaultMutableTreeNode ( "Root Node" );
        treeModel = new DefaultTreeModel ( rootNode );
        treeModel.addTreeModelListener ( new MyTreeModelListener () );
        tree = new JTree ( treeModel );
        tree.setEditable ( true );
        tree.getSelectionModel().setSelectionMode ( TreeSelectionModel.SINGLE_TREE_SELECTION );
        tree.setShowsRootHandles ( true );

        JScrollPane scrollPane = new JScrollPane ( tree );
        add ( scrollPane );
    }

    /**
     * Remove all nodes except the root node.
     */
    public void clear ()
    {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /**
     * Remove the currently selected node.
     */
    public void removeCurrentNode ()
    {
        TreePath currentSelection = tree.getSelectionPath ();
        if ( currentSelection != null )
        {
            DefaultMutableTreeNode currentNode = ( DefaultMutableTreeNode )
                    ( currentSelection.getLastPathComponent () );
            MutableTreeNode parent = ( MutableTreeNode ) ( currentNode.getParent () );
            if ( parent != null )
            {
                treeModel.removeNodeFromParent ( currentNode );
                return;
            }
        }

        // Either there was no selection, or the root was selected.
        toolkit.beep ();
    }

    /**
     * Add child to the currently selected node.
     */
    public DefaultMutableTreeNode addObject ( Object child )
    {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath ();

        if ( parentPath == null )
        {
            parentNode = rootNode;
        }
        else
        {
            parentNode = ( DefaultMutableTreeNode ) ( parentPath.getLastPathComponent () );
        }

        return addObject ( parentNode, child, true );
    }

    public void rename ()
    {
        DefaultMutableTreeNode parentNode, currentNode;

        TreePath parentPath = tree.getSelectionPath ();
        System.out.println ( "path = " + parentPath );

        if ( parentPath != null )
        {
            currentNode = ( DefaultMutableTreeNode ) ( parentPath.getLastPathComponent () );
            parentNode = (DefaultMutableTreeNode) currentNode.getParent ();

            String name = (String) parentNode.getUserObject ();
            name = name + "_111";
            parentNode.setUserObject ( name );

            treeModel.reload();
            // высветить узел в дереве
            //tree.scrollPathToVisible ( parentPath );
            // сделать этот узел текущим (выбранным)
            tree.setSelectionPath ( parentPath );
        }
    }

    public DefaultMutableTreeNode addObject ( DefaultMutableTreeNode parent, Object child )
    {
        return addObject ( parent, child, false );
    }

    public DefaultMutableTreeNode addObject ( DefaultMutableTreeNode parent, Object child,  boolean shouldBeVisible )
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode ( child );

        if ( parent == null )
        {
            parent = rootNode;
        }

        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto ( childNode, parent, parent.getChildCount() );

        // Make sure the user can see the lovely new node.
        if ( shouldBeVisible )
        {
            // По моему здесь просто раскрываются узлы, если выбранный обьект находится глубоко в закрытых узлах.
            tree.scrollPathToVisible ( new TreePath ( childNode.getPath() ) );
        }
        return childNode;
    }

    class MyTreeModelListener implements TreeModelListener
    {
        public void treeNodesChanged ( TreeModelEvent e )
        {
            DefaultMutableTreeNode node;
            node = ( DefaultMutableTreeNode ) ( e.getTreePath ().getLastPathComponent() );

            /*
             * If the event lists children, then the changed
             * node is the child of the node we've already
             * gotten.  Otherwise, the changed node and the
             * specified node are the same.
             */

            int index = e.getChildIndices ()[ 0 ];
            node = ( DefaultMutableTreeNode ) ( node.getChildAt ( index ) );

            System.out.println ( "The user has finished editing the node." );
            System.out.println ( "New value: " + node.getUserObject () );
        }

        public void treeNodesInserted ( TreeModelEvent e )
        {
        }

        public void treeNodesRemoved ( TreeModelEvent e )
        {
        }

        public void treeStructureChanged ( TreeModelEvent e )
        {
        }
    }
    
}
