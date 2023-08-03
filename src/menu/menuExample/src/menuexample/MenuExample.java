package menu.menuExample.src.menuexample;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class MenuExample extends JFrame
{
 JPanel contentPane;
 BorderLayout borderLayout1 = new BorderLayout();
 JLabel status = new JLabel(" ");//������ �������
 //�����������
 public MenuExample()
 {
  enableEvents(AWTEvent.WINDOW_EVENT_MASK);
  try
  {
   jbInit();
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }
 //���������� �������������
 private void jbInit() throws Exception
 {
  contentPane = (JPanel) this.getContentPane();
  //������������� �������� ���������� �����������
  contentPane.setLayout(borderLayout1);
  //������ ����� ��� ������ �������
  Border border = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
  status.setBorder(border);
  //������ ��� ������ ������������
  status.setOpaque(true);
  //��������� ����� ������� �� ������
  contentPane.add(status,BorderLayout.SOUTH);
  //������������� ������ ����
  this.setSize(new Dimension(400, 300));
  //������������� ��������� ����
  this.setTitle("Menu example");
  //������� �������� ����
  JMenuBar menuBar = new JMenuBar();
  //������� ��� �������
  JMenu menuFile = new JMenu("File");
  JMenu menuView = new JMenu("View");
  JMenu menuThread = new JMenu("Thread");
  //��������� � ����� ������� ����������� �������(� ��� �����)
  menuFile.addMouseListener(new MenuExampleMouseAdapter("Menu file"," ",status));
  menuView.addMouseListener(new MenuExampleMouseAdapter("Menu view"," ",status));
  menuThread.addMouseListener(new MenuExampleMouseAdapter("Thread"," ",status));
  //������� �������� ������� File � ������������� �������
  JMenuItem item1 = new JMenuItem("New");
  item1.addMouseListener(new MenuExampleMouseAdapter ("New file"," ",status));
  JMenuItem item2 = new JMenuItem("Open");
  item2.addMouseListener(new MenuExampleMouseAdapter("Open file"," ",status));
  JMenuItem item3 = new JMenuItem("Save");
  item3.addMouseListener(new MenuExampleMouseAdapter("Save file"," ",status));
  JMenuItem item4 = new JMenuItem("Save as..");
  item4.addMouseListener(new MenuExampleMouseAdapter("Save file as.."," ",status));
  JMenuItem item5 = new JMenuItem("Close");
  item5.addMouseListener(new MenuExampleMouseAdapter("Close file"," ",status));
  JMenuItem item6 = new JMenuItem("Exit");
  item6.addMouseListener(new MenuExampleMouseAdapter("Exit prorgamm"," ",status));
  //��������� ��������� �������� ������� File
  menuFile.add(item1);
  menuFile.add(item2);
  menuFile.addSeparator();
  menuFile.add(item3);
  menuFile.add(item4);
  menuFile.addSeparator();
  menuFile.add(item5);
  menuFile.addSeparator();
  menuFile.add(item6);
  //������� �������� ������� View � ������������� �������
  JMenuItem itemv1 = new JMenuItem("BGColor");
  itemv1.addMouseListener(new MenuExampleMouseAdapter("Change background color"," ",status));
  itemv1.setAccelerator(KeyStroke.getKeyStroke(67,Event.CTRL_MASK));
  //������ ���������� ������� �� ������� �� ������� itemv1
  itemv1.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    //��������� ������� ������ ���� ���� ������
    int r=(int)(Math.random()*255);
    int g=(int)(Math.random()*255);
    int b=(int)(Math.random()*255);
    contentPane.setBackground(new Color(r,g,b));
   }
  });
  //������� ������� � ������� View
  JMenu menuUI = new JMenu("Look & Feel");
  menuUI.addMouseListener(new MenuExampleMouseAdapter("Change Look & Feel"," ",status));
  //������ �������� ������� View � ���� JRadioButton
  JMenuItem itemui1 = new JRadioButtonMenuItem("Windows Look & Feel");
  JMenuItem itemui2 = new JRadioButtonMenuItem("Metal Look & Feel");
  JMenuItem itemui3 = new JRadioButtonMenuItem("Motif Look & Feel");
  //���������� �� � ������
  ButtonGroup bg = new ButtonGroup();
  bg.add(itemui1);
  bg.add(itemui2);
  bg.add(itemui3);
  itemui1.addMouseListener(new MenuExampleMouseAdapter("Windows Look & Feel"," ",status));
  itemui2.addMouseListener(new MenuExampleMouseAdapter("Metal Look & Feel"," ",status));
  itemui3.addMouseListener(new MenuExampleMouseAdapter("Motif Look & Feel"," ",status));
  itemui1.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    setLookAndFeel(1);
   }
  });
  itemui2.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    setLookAndFeel(2);
   }
  });
  itemui3.addActionListener(new ActionListener()
  {
   public void actionPerformed(ActionEvent e)
   {
    setLookAndFeel(3);
   }
  });
  itemui2.setSelected(true);
  menuUI.add(itemui1);
  menuUI.add(itemui2);
  menuUI.add(itemui3);
  itemv1.addMouseListener(new MenuExampleMouseAdapter("Change background color"," ",status));
  itemv1.setAccelerator(KeyStroke.getKeyStroke(67,Event.CTRL_MASK));
  menuView.add(itemv1);
  menuView.add(menuUI);
  //������� �������� ������� Thread � ������������� �������
  JMenuItem itemT1 = new JMenuItem("Add");
  JMenuItem itemT2 = new JMenuItem("Run");
  JMenuItem itemT3 = new JMenuItem("Pause");
  JMenuItem itemT4 = new JMenuItem("Delete");
  itemT1.addMouseListener(new MenuExampleMouseAdapter("Add thread"," ",status));
  itemT2.addMouseListener(new MenuExampleMouseAdapter("Run thread"," ",status));
  itemT3.addMouseListener(new MenuExampleMouseAdapter("Pause thread"," ",status));
  itemT4.addMouseListener(new MenuExampleMouseAdapter("Delete thread"," ",status));
  ImageIcon image1 = new ImageIcon(getToolkit().createImage("images/add.gif"));
  ImageIcon image2 = new ImageIcon(getToolkit().createImage("images/play.gif"));
  ImageIcon image3 = new ImageIcon(getToolkit().createImage("images/pause.gif"));
  ImageIcon image4 = new ImageIcon(getToolkit().createImage("images/delete.gif"));
  itemT1.setIcon(image1);
  itemT2.setIcon(image2);
  itemT3.setIcon(image3);
  itemT4.setIcon(image4);
  menuThread.add(itemT1);
  menuThread.addSeparator();
  menuThread.add(itemT2);
  menuThread.add(itemT3);
  menuThread.addSeparator();
  menuThread.add(itemT4);
  menuBar.add(menuFile);
  menuBar.add(menuView);
  menuBar.add(menuThread);
  this.setJMenuBar(menuBar);
 }
 public void setLookAndFeel(int type)
 {
  String laf= "javax.swing.plaf.metal.MetalLookAndFeel";
  switch(type)
  {
   case 1: laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
   break;
   case 2: laf = "javax.swing.plaf.metal.MetalLookAndFeel";
   break;
   case 3: laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
   break;
  }
  try
  {
   UIManager.setLookAndFeel(laf);
   SwingUtilities.updateComponentTreeUI(this);
  }
  catch(Exception e)
  {
   e.printStackTrace();
  }
 }
 protected void processWindowEvent(WindowEvent e)
 {
  super.processWindowEvent(e);
  if (e.getID() == WindowEvent.WINDOW_CLOSING)
  {
   System.exit(0);
  }
 }
}


