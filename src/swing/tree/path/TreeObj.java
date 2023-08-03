package swing.tree.path;


import javax.swing.tree.DefaultMutableTreeNode;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.01.2013 14:50
 */
public class TreeObj extends DefaultMutableTreeNode
{
    private String name;

    public TreeObj ( String name )
    {
        this.name = name;
    }

    public String getName ()
    {
        return name;
    }

    public TreeObj getChildByName ( String name )
    {
        TreeObj treeObj;

        if ( children != null )
        {
            for ( Object node : children )
            {
                if ( node instanceof TreeObj )
                {
                    treeObj = (TreeObj) node;
                    if ( name.equals ( treeObj.getName() ) )   return treeObj;
                }
            }
        }
        return null;
    }

    public String toString()
    {
        return getName();
    }

}
