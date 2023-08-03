package swing.focus.scroll;


import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:55:36
 */
public class TabFocusAdapter extends FocusAdapter
{
    private JPanel panel;

    public TabFocusAdapter ( JPanel panel )
    {
        this.panel = panel;
    }

    public void focusGained ( FocusEvent evt )
    {
        java.awt.Component focusedComponent = evt.getComponent();
        panel.scrollRectToVisible(focusedComponent.getBounds(null));
        //repaint();
    }

}
