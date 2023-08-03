package swing.swingthread;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.12.2010 16:44:33
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GoodThread {
    public static void main(String[] args) {
        JFrame frame = new TestFrameGood();
        frame.show();
    }
}

class TestFrameGood extends JFrame {
    public TestFrameGood() { 
        setTitle("Good Thread Example");
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
                new WorkerThreadGood(model).start();
            }
        });
        p = new JPanel();
        p.add(b);
        getContentPane().add(p, "North");
    }

    private DefaultListModel model;
}

class WorkerThreadGood extends Thread {
    public WorkerThreadGood(DefaultListModel aModel) {
        model = aModel;
        generator = new Random();
    }

    public void run() {
        while (true) {
            final Integer i =
                new Integer(generator.nextInt(10));
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    if (model.contains(i))
                        model.removeElement(i);
                    else
                        model.addElement(i);
                }
            });
            yield();
        }
    }

    private DefaultListModel model;
    private Random generator;
}

