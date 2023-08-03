package swing.mvc.model1;


import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.event.*;

public class StartUp extends JFrame {

  private ListView m_lstItems = new ListView();

  private JButton m_btnAdd = new JButton();

  private JButton m_btnExit = new JButton();

  private JTextField m_txtField = new JTextField();

  private ComboBoxView m_cmbList = new ComboBoxView();

  private DataModel m_dm = new DataModel();

  //Construct the application

  public StartUp() {

    super();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Center the window

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    Dimension frameSize = new Dimension(405, 325);

    this.setSize(frameSize); this.setTitle("MVC Example");

    this.setLocation(
    (screenSize.width - frameSize.width) / 2,
    (screenSize.height - frameSize.height) / 2
    );

    this.getContentPane().setLayout(null);

    JPanel a_pnl1 = new JPanel(); JPanel a_pnl2 = new JPanel();

    JPanel a_pnl3 = new JPanel(); JPanel a_pnl4 = new JPanel();

    JLabel lblComboBox = new JLabel();

    a_pnl1.setBounds(new Rectangle(3, 5, 393, 290));

    a_pnl1.setLayout(new BoxLayout(a_pnl1, BoxLayout.Y_AXIS));

    a_pnl2.setBorder(BorderFactory.createEtchedBorder());

    a_pnl2.setLayout(new BorderLayout());

    a_pnl3.setBorder(BorderFactory.createEtchedBorder());

    a_pnl4.setBorder(BorderFactory.createEtchedBorder());

    m_btnAdd.setText("Add"); m_btnExit.setText("Exit");

    lblComboBox.setText("Items:");

    m_txtField.setMaximumSize(new Dimension(4, 21));

    this.getContentPane().add(a_pnl1, null);

    a_pnl1.add(a_pnl2, null);    a_pnl2.add(m_lstItems, BorderLayout.CENTER);

    a_pnl1.add(a_pnl3, null);    a_pnl3.add(lblComboBox, null);

    a_pnl3.add(m_cmbList, null);  a_pnl1.add(a_pnl4, null);

    a_pnl4.add(m_txtField, null); a_pnl4.add(m_btnAdd, null);

    a_pnl4.add(m_btnExit, null);

    // Registration of listeners

    AdapterEditFieldToModel a_eftm =
    new AdapterEditFieldToModel(m_btnAdd, m_txtField, m_dm);

    m_btnAdd.addActionListener(a_eftm);

    AdapterModelToList a_mtl = new AdapterModelToList(m_lstItems, m_dm);

    m_dm.addChangeListener(a_mtl);

    AdapterModelToComboBox a_mtcb =
    new AdapterModelToComboBox(m_cmbList, m_dm);

    m_dm.addChangeListener(a_mtcb);

    m_btnExit.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        System.exit(1);

      }

    });

    this.setVisible(true);

  }

  // Adapter for controller to model

  private static class AdapterEditFieldToModel implements ActionListener {

    private DataModel m_mod;

    private JButton m_btnAdd;

    private JTextField m_txtField;

    public AdapterEditFieldToModel(JButton p_btn, JTextField p_txt, DataModel p_mod) {

      m_mod = p_mod;

      m_btnAdd = p_btn;

      m_txtField = p_txt;

    }

    public void actionPerformed(ActionEvent e) {

      m_mod.addData(m_txtField.getText());

    }

  }

  // Adapter for model to view

  private static class AdapterModelToList implements ChangeListener {

    private DataModel m_mod;

    private ListView m_lst;

    public AdapterModelToList(ListView p_lst, DataModel p_mod) {

      m_mod = p_mod;

      m_lst = p_lst;

    }

    public void stateChanged(ChangeEvent e) {

      m_lst.addData(m_mod.getData());

    }

  }

  // Adapter for model to view

  private static class AdapterModelToComboBox implements ChangeListener {

    private DataModel m_mod;

    private ComboBoxView m_cmb;

    public AdapterModelToComboBox(ComboBoxView p_cmb, DataModel p_mod) {

      m_mod = p_mod;

      m_cmb = p_cmb;

    }

    public void stateChanged(ChangeEvent e) {

      m_cmb.addData(m_mod.getData());

    }

  }

  //Main method

  public static void main(String[] args) {

    try {

      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    }

    catch(Exception e) {

      e.printStackTrace();

    }

    new StartUp();

  }
}
