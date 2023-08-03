package swing.toolBar;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.10.2011 17:44:35
 */
import javax.swing.*;
import java.awt.*;

public class BrowserToolBar extends JToolBar {
  public BrowserToolBar() {
    String[] imageFiles =
      { "Left.gif", "Right.gif", "RotCCUp.gif",
        "TrafficRed.gif", "Home.gif", "Print.gif", "Help.gif" };
    String[] toolbarLabels =
      { "Back", "Forward", "Reload", "Stop", "Home", "Print", "Help" };
    Insets margins = new Insets(0, 0, 0, 0);
    for(int i=0; i<toolbarLabels.length; i++) {
      ToolBarButton button =
        new ToolBarButton("images/" + imageFiles[i]);
      button.setToolTipText(toolbarLabels[i]);
      button.setMargin(margins);
      add(button);
    }
  }

  public void setTextLabels(boolean labelsAreEnabled) {
    Component c;
    int i = 0;
    while((c = getComponentAtIndex(i++)) != null) {
      ToolBarButton button = (ToolBarButton)c;
      if (labelsAreEnabled)
        button.setText(button.getToolTipText());
      else
        button.setText(null);
    }
  }
}
