package swing.split;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.07.12 10:47
 */

import javax.swing.JButton;
import javax.swing.JSplitPane;

public class GetBottomComponentPanel
{
    public static void main ( String[] argv ) throws Exception
    {
        JButton leftComponent = new JButton ( "left" );
        JButton rightComponent = new JButton ( "right" );
        JButton topComponent = new JButton ( "top" );
        JButton bottomComponent = new JButton ( "bottom" );
        JSplitPane hpane = new JSplitPane ( JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent );
        JSplitPane vpane = new JSplitPane ( JSplitPane.VERTICAL_SPLIT, topComponent, bottomComponent );
        leftComponent = ( JButton ) hpane.getLeftComponent ();
        rightComponent = ( JButton ) hpane.getRightComponent ();
        topComponent = ( JButton ) vpane.getTopComponent ();
        bottomComponent = ( JButton ) vpane.getBottomComponent ();
        hpane.setLeftComponent ( topComponent );
        hpane.setRightComponent ( bottomComponent );
        vpane.setTopComponent ( leftComponent );
        vpane.setBottomComponent ( rightComponent );
    }
}


