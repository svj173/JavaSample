package swing.tree.path;


import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.01.2013 15:00
 */
public class TreeObjRenderer extends DefaultTreeCellRenderer
{
    @Override
    public Component getTreeCellRendererComponent ( JTree tree, Object value, boolean sel,  boolean expanded, boolean leaf, int row, boolean hasFocus )
    {
        String text;

        super.getTreeCellRendererComponent ( tree, value, sel,  expanded, leaf, row,  hasFocus );

        //setIcon ( getIconByObjType ( value ) );
        if ( value instanceof TreeObj )
        {
            TreeObj to = (TreeObj) value;
            text    = to.getName();
        }
        else
        {
            text = value.toString();
        }
        setText ( text );
        return this;
    }

}
