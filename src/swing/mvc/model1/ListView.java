package swing.mvc.model1;


import javax.swing.*;

public class ListView extends JList {

  private final String[] m_data = {"one", "two", "three", "four"};

  private final DefaultListModel m_model = new DefaultListModel();

  public ListView() {

    super();

    for (int i = 0; i < m_data.length; i++) {

      m_model.addElement(m_data[i]);

    }

    this.setModel(m_model);

  }

  public void addData(String p_str) {

    m_model.addElement(p_str);

  }

}
