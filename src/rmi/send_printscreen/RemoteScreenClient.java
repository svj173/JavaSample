package rmi.send_printscreen;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.rmi.*;
import java.rmi.server.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Программа-клиент, которая вызывает удаленные методы:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:30:55
 */
public class RemoteScreenClient
{
   private static String url = "rmi://localhost/";//или адрес удаленной машины
   private static JLabel imageLabel = new JLabel();

   public static void main(String[] args)
   {
      System.setProperty("java.security.policy", "client.policy");
      System.setSecurityManager(new RMISecurityManager());
      JFrame f = new JFrame("Remote Screen:"+url);
      JToolBar toolBar = new JToolBar();
      JButton b = new JButton("View");
      toolBar.add(b);
      b.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
              view();
          }
      });
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JScrollPane scrollPane = new JScrollPane(imageLabel);
      f.getContentPane().add(scrollPane,BorderLayout.CENTER);
      f.getContentPane().add(toolBar,BorderLayout.NORTH);
      f.setSize(600, 440);
      f.setLocationRelativeTo(null);
      f.setVisible(true);
   }

    private static void view(){
      try{
         RemoteScreen remoteScreen = (RemoteScreen)Naming.lookup(url +
                                                            "getRemoteScreen");
         imageLabel.setIcon(remoteScreen.getIcon());
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }
}
