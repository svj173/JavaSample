package menu.menuExample.src.menuexample;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class MenuExampleMouseAdapter extends MouseAdapter
{
 String messageEntered;
 String messageExited;
 JLabel status;
 public MenuExampleMouseAdapter(String messageEntered,String messageExited, JLabel status)
 {
  this.messageEntered=messageEntered;
  this.messageExited=messageExited;
  this.status=status;

 }
 public void mouseEntered(MouseEvent e)
 {
  status.setText(messageEntered);
 }
 public void mouseExited(MouseEvent e)
 {
    status.setText(messageExited);
 }
}