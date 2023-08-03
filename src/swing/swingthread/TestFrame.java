package swing.swingthread;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.12.2010 16:40:11
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TestFrame {
    public static void main(String[] args) {
        JFrame frame = new TestFrameBad();
        frame.show();
    }
}

class TestFrameBad extends JFrame {
    public TestFrameBad() {
        setTitle("Bad Thread Example");
        setSize(400,300);
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
        model = new DefaultListModel();

        JList list = new JList(model);
        JScrollPane scrollPane =
                new JScrollPane(list);

        JPanel p = new JPanel();
        p.add(scrollPane);
        getContentPane().add(p, "Center");

        JButton b = new JButton("Fill List");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(
                    ActionEvent event) {
                new WorkerThreadBad(model).start();
            }
        });
        p = new JPanel();
        p.add(b);
        getContentPane().add(p, "North");
    }

    private DefaultListModel model;
}

class WorkerThreadBad extends Thread {
    public WorkerThreadBad(DefaultListModel aModel) {
        model = aModel;
        generator = new Random();
    }

    public void run() {
        while (true) {
            Integer i =
                new Integer(generator.nextInt(10));

            if (model.contains(i))
                model.removeElement(i);
            else
                model.addElement(i);

            yield();
        }
    }

    private DefaultListModel model;
    private Random generator;
}
