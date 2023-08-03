package swing.toolBar;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.10.2011 17:43:41
 */
import javax.swing.*;
import java.awt.*;

public class ToolBarButton extends JButton {
  private static final Insets margins =
    new Insets(0, 0, 0, 0);

  public ToolBarButton(Icon icon) {
    super(icon);
    setMargin(margins);
    setVerticalTextPosition(BOTTOM);
    setHorizontalTextPosition(CENTER);
  }

  public ToolBarButton(String imageFile) {
    this(new ImageIcon(imageFile));
  }

  public ToolBarButton(String imageFile, String text) {
    this(new ImageIcon(imageFile));
    setText(text);
  }
}
