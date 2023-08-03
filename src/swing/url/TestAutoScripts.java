package swing.url;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.04.2022 12:03
 */
public class TestAutoScripts {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                TestAutoScripts test = new TestAutoScripts();
                test.createGUI();
            }
        });
    }

    private void createGUI()
    {
        final JFrame frame = new JFrame("Test frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // левая панель - запрос - json, заголовки авторизации и пр.
        JPanel leftPanel = createLeftPanel();

        // центр - весь функционал - список Сценариев, наполнение визарда
    //    JPanel centerPanel = createCenterPanel();

        // ответ от Сервера - json
    //    JPanel centerPanel = createRightPanel();


        // поля: хост, порт, логин, пароль, кнопка Атворизации, поле с токеном авторизации
        JPanel topPanel = createTopPanel();


        // расположение компонент - по вертикали
        //leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        frame.getContentPane().setLayout(new BorderLayout());

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        frame.getContentPane().add(leftPanel, BorderLayout.WEST);
    //    frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(460, 520));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JTextArea leftText;

    private JPanel createLeftPanel() {
        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());

        leftText = new JTextArea();
        // - опция - переносить текст
        leftText.setLineWrap(true);
        leftText.setPreferredSize(new Dimension(960, 620));

        result.add(new JScrollPane(leftText), BorderLayout.CENTER);  // сильно сжимается по ширине
        //result.add(leftText);

        return result;
    }

    private JPanel createTopPanel() {
        String sep = "            ";
        JPanel result = new JPanel();
        //result.setLayout(new BorderLayout());

        //resultArea = new JTextArea();
        // - опция - переносить текст
        //resultArea.setLineWrap(true);

        //result.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JLabel host = new JLabel("host");
        result.add(host);
        JTextField hostField = new JTextField();
        //hostField.setSize(100, 10);
        //hostField.setPreferredSize(new Dimension(50, 10));
        hostField.setText("localhost");
        result.add(hostField);

        JLabel port = new JLabel("port");
        result.add(port);
        JTextField portField = new JTextField();
        portField.setText("8071");
        result.add(portField);

        JLabel login = new JLabel("login");
        result.add(login);
        JTextField loginField = new JTextField();
        loginField.setText("svj");
        result.add(loginField);

        JLabel pwd = new JLabel("pwd");
        result.add(pwd);
        JTextField pwdField = new JTextField();
        pwdField.setText("svj");
        result.add(pwdField);

        // Кнопка Авторизация
        JButton button1 = new JButton(">>");
        button1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        ActionListener action = new AuthListener(hostField, portField, loginField, pwdField, leftText);
        button1.addActionListener(action);
        result.add(button1);

        //result.add(requestField, BorderLayout.NORTH);

        //JTextField responseSizeField = new JTextField();
        //result.add(responseSizeField, BorderLayout.SOUTH);
        return result;
    }


}
