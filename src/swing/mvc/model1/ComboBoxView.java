package swing.mvc.model1;


import javax.swing.*;

public class ComboBoxView extends JComboBox {

  private final String[] m_data = {"one", "two", "three", "four"};

  private final DefaultComboBoxModel m_model = new DefaultComboBoxModel();

  public ComboBoxView() {

    super();

    for (int i = 0; i < m_data.length; i++) {

      m_model.addElement(m_data[i]);

    }

    this.setModel(m_model);

    setEditable(false);

  }

  public void addData(String p_str) {

    m_model.addElement(p_str);

  }

}
