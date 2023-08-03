package key;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.07.2011 12:53:26
 */
public class PasswordDialog extends JDialog
{
    private JTextField      jNameField;
    private JPasswordField  jPasswordField1;

    public static final int RET_CANCEL = 0;
    public  static final int RET_OK = 1;

    private int returnStatus =  RET_CANCEL;

    public PasswordDialog ( Frame parent, boolean modal ) {
        super(parent, modal);
        initComponents();
        jPasswordField1.requestFocusInWindow();
    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    @SuppressWarnings("unchecked")
    private void initComponents()
    {
        JButton okButton, cancelButton;
        JLabel  jLabel1, jLabel2;
        PasswordDialogKeyAdapter keyAdapter;

        setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
        setTitle("Авторизация");        // old - Регистрация в системе
        setModal(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing( WindowEvent evt) {
                doClose ( RET_CANCEL );
            }
        });

        keyAdapter  = new PasswordDialogKeyAdapter(this);

        okButton = new JButton();
        okButton.setName("Auth");
        okButton.setText("Авторизация");
        okButton.addKeyListener( keyAdapter );
        // Нажатие Пробела на кнопке воспринимается как Акция. -- Особенность Java !!!
        okButton.addActionListener ( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent evt )
            {
                int key;
                long when;
                String cmd;
                Object source;
                JButton button;

                source  = evt.getSource ();
                System.out.println ( "-- source = " + source );

                cmd  = evt.getActionCommand ();
                System.out.println ( "-- cmd = " + cmd );

                key  = evt.getModifiers ();
                System.out.println ( "-- Modifiers = " + key );

                when  = evt.getWhen();
                System.out.println ( "-- when = " + when );
                
                doClose( RET_OK);
            }
        });

        cancelButton = new JButton ();
        cancelButton.setName("Cancel");
        cancelButton.setText("Закрыть апплет");
        cancelButton.addKeyListener( keyAdapter );
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                doClose ( RET_CANCEL );
            }
        });

        jLabel1 = new JLabel();
        jLabel1.setText("Имя:");

        jNameField = new JTextField();
        jNameField.addKeyListener( keyAdapter );
        /*
        jNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed( KeyEvent evt) {
                jNameFieldKeyPressed(evt);
            }
        });
        */

        jLabel2 = new JLabel ();
        jLabel2.setText("Пароль:");

        jPasswordField1 = new JPasswordField();
        jPasswordField1.addKeyListener( keyAdapter );
        /*
        jPasswordField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                jPasswordField1KeyPressed(evt);
            }
        });
        */

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent( okButton, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent( cancelButton )
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent( jLabel1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                            .addComponent( jLabel2 ))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField1, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jNameField, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .addGap(11, 11, 11))))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { cancelButton, okButton });

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent( jLabel1 )
                    .addComponent(jNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent( jLabel2 )
                    .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent( cancelButton )
                    .addComponent( okButton ))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }


    private void jPasswordField1KeyPressed ( KeyEvent evt ) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            doClose( RET_OK);
        }
    }

    private void jNameFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            doClose( RET_OK);
        }
    }

    public void doClose(int retStatus)
    {
        System.out.println ( "----- doClose. retStatus = " + retStatus );
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    public void setNameValue(String name) {
        jNameField.setText(name);
    }

    public String getNameValue() {
        return jNameField.getText();
    }

    /**
     * Метод для выключения поля ввода имени
     */
    public void disableName(){
        jNameField.setEnabled(false);
    }

    // Convert password to string
    private static String convertPassword ( char[] cPassword )
    {
        // Declare variables
        String strRet;

        strRet  = new String ( cPassword );
        /*
        strRet = new String("");

        // Go through each character
        for (int i = 0; i < cPassword.length; i++) {
            strRet += cPassword[i];
        }
        */

        return strRet;
    }

    public String getPasswordValue() {
        return (convertPassword(jPasswordField1.getPassword()));
    }

}
