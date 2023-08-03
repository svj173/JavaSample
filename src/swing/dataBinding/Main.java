package swing.dataBinding;

/*
import net.miginfocom.swing.MigLayout;
import no.tornado.databinding.BindingGroup;
import no.tornado.databinding.BindingStrategy;
import no.tornado.databinding.model.ListComboBoxModel;
import no.tornado.databinding.model.PropertyListCellRenderer;
import no.tornado.databinding.status.JLabelStatusMonitor;
import no.tornado.databinding.validator.ValidationStrategy;
import org.jdesktop.swingx.JXDatePicker;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
*/

import javax.swing.*;

/**
 * Пример использования гуи-биндинга.
 * Биндится параметр обьекта на гуи-компоненту.
 * При изменении параметра - гуи компонента автоматически перерисоывавается, и обратно.
 * Недостатки:
 * 1) Все делается в авт-потоке. Т.е. если вдруг поменяетс япараметрне в авт-потоке - биндинг не отработает.
 * 2) Валидация - просто ругается, без подсветки ошибочного параметра, с тулпитом причины ошибки.
 * 3) Пример - не рабочий, требует еще каких-то классов - net.miginfocom.swing.MigLayout, org.joda.time.DateTime и т.д.
 *
 * Вывод - для небольших несложных проектов.
 *
 */
public class Main extends JFrame
{
    /*
    private BindingGroup bindings;

    private Person user;
    private ArrayList<Person> people;

    private JTextField idField;
    private JFormattedTextField usernameField;
    private JTextField passwordField;
    private JXDatePicker createdField;
    private JXDatePicker updatedField;
    private JComboBox parentField;
    private JCheckBox advancedField;
    private JLabel idMessageArea;
    private JButton bindingToggle;
    private boolean bound = false;
    private JLabel statusBar;
    private JComboBox validationStrategy;
    private JComboBox bindingStrategy;
    private JCheckBox pce;

    public static void main(String[] args) throws Exception {
        Main t = new Main();
        t.setTitle("Swing Data Binding Demo");
        t.setResizable(false);
        t.createModel();
        t.createGUI();
        t.setupBinding();
        t.setVisible(true);
        t.pack();
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.reconfigureStrategies();
        t.toggleBinding();
        t.user.getChangeSupport().firePropertyChange("updateSpyPanel", null, null);
    }

    private void createModel() {
        // Create a person object with sample data
        user = new Person("person@mycompany.com", "mypassword", true);
        user.setId(5);
        user.setCreated(new Date());
        user.setUpdated(new DateTime());

        // Create a list of people and assign the first as parent
        people = new ArrayList<Person>();
        people.add(new Person("person1@mycompany.com"));
        people.add(new Person("person2@mycompany.com"));
        people.add(new Person("person3@mycompany.com"));
        people.add(new Person("person4@mycompany.com"));

        user.setParent(people.get(0));
    }

    private void setupBinding() {
        // Create a new binding group
        bindings = new BindingGroup(user);

        // Bind the idField to the id property of the user. Set the field required and make the idMessageArea JLabel
        // display error messages from the binding.

        bindings.add(idField, "id").required().setStatusMonitor(JLabelStatusMonitor.create(idMessageArea));

        // Straight bindings between fields and properties of the user object
        bindings.add(usernameField, "username");
        bindings.add(passwordField, "password");
        bindings.add(advancedField, "advanced");
        bindings.add(createdField, "created");
        bindings.add(updatedField, "updated");
        bindings.add(parentField, "parent");
    }

    private void createGUI() {
        setLayout(new MigLayout());
        JPanel form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder("Sample Demo Form"));
        add(form, "span 1 2");

        form.setLayout(new MigLayout("ins 5 5 40 5, wrap 2, fill", "[][fill]"));

        idField = new JTextField();
        idMessageArea = new JLabel();
        idMessageArea.setForeground(Color.RED);

        usernameField = new JFormattedTextField();
        passwordField = new JTextField();
        createdField = new JXDatePicker();
        updatedField = new JXDatePicker();
        advancedField = new JCheckBox();

        parentField = new JComboBox();
        parentField.setModel(new ListComboBoxModel(people));
        parentField.setRenderer(new PropertyListCellRenderer("username"));

        form.add(new JLabel("ID"));
        form.add(idField);
        form.add(idMessageArea, "span");
        form.add(new JLabel("Username"));
        form.add(usernameField);
        form.add(new JLabel("Password"));
        form.add(passwordField);
        form.add(new JLabel("Created"));
        form.add(createdField);
        form.add(new JLabel("Updated"));
        form.add(updatedField);
        form.add(new JLabel("Advanced"));
        form.add(advancedField);
        form.add(new JLabel("Parent"));
        form.add(parentField);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (bound) {
                    bindings.flushUIToModel();
                    if (!bindings.isValid())
                        JOptionPane.showMessageDialog(Main.this, "Not all fields are valid", "Fix errors", JOptionPane.WARNING_MESSAGE);
                    else
                        user.getChangeSupport().firePropertyChange("updateSpyPanel", null, null);
                }
            }
        });
        form.add(save);

        JPanel config = new JPanel();
        config.setBorder(BorderFactory.createTitledBorder("Configure Data Binding"));
        add(config);

        config.setLayout(new MigLayout("wrap 2, fill", "[][fill]"));

        config.add(new Label("Binding strategy"));
        bindingStrategy = new JComboBox(BindingStrategy.values());
        bindingStrategy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reconfigureStrategies();
                if (bound)
                    unbind();
                bind();
            }
        });
        config.add(bindingStrategy);

        config.add(new Label("Validation strategy"));
        validationStrategy = new JComboBox(ValidationStrategy.values());
        validationStrategy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reconfigureStrategies();
                if (bound)
                    unbind();
                bind();
            }
        });
        config.add(validationStrategy);

        config.add(new Label("PropertyChangeSupport"));
        pce = new JCheckBox();
        pce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reconfigureStrategies();
                if (bound)
                    unbind();
                bind();
            }
        });
        config.add(pce);

        bindingToggle = new JButton();
        bindingToggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleBinding();
            }
        });
        config.add(bindingToggle);

        JPanel spyPanel = new JPanel(new MigLayout("ins 0, fill"));
        JTextArea spyField = new JTextArea();
        spyPanel.add(spyField, "grow");
        spyField.setOpaque(false);
        spyPanel.setBorder(BorderFactory.createTitledBorder("Model object spy"));
        user.addPropertyChangeListener(new SpyListener(spyField));
        add(spyPanel, "grow, newline");

        statusBar = new JLabel();
        JPanel statusPanel = new JPanel();
        statusPanel.add(statusBar);
        add(statusPanel, "newline");
    }

    private void reconfigureStrategies() {
        bindings.setDefaultBindingStrategy(BindingStrategy.values()[bindingStrategy.getSelectedIndex()]);
        bindings.setDefaultValidationStrategy(ValidationStrategy.values()[validationStrategy.getSelectedIndex()]);
        bindings.setUsePropertyChangeSupport(pce.isSelected());
    }

    private void toggleBinding() {
        if (bound) {
            unbind();
        } else {
            bind();
        }
        bound = !bound;
    }

    private void bind() {
        bindings.bind();
        bindingToggle.setText("Unbind model");
        statusBar.setText("The model is bound to the form");
    }

    private void unbind() {
        bindings.unbind();
        bindingToggle.setText("Bind model");
        statusBar.setText("The model is not bound to the form");
    }

    private class SpyListener implements PropertyChangeListener {
        private JTextArea spyField;

        public SpyListener(JTextArea spyField) {
            this.spyField = spyField;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            spyField.setText(user.toString());
        }
    }
    */
}
