package swing.label;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This example demonstrates adding drag and drop (DnD) support to a JLabel.
 * <BR/> Нажимаем мышкой на метке и тянем ее в текстовое поле. Весь текст метки перемещается в текстовое поле.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.01.2012 10:52:08
 */
public class JLabelDragNDrop {
    JFrame aFrame;
    JPanel aPanel;
    JTextField tf;
    JLabel tl;

    // Constructor
    public JLabelDragNDrop() {
        // Create the frame and container.
        aFrame = new JFrame("JLabel Drag and Drop Demo");
        aFrame.setSize(100, 10);
        aPanel = new JPanel();
        aPanel.setLayout(new GridLayout(2, 2));

        // Add the widgets.
        addWidgets();

        // Add the panel to the frame.
        aFrame.getContentPane().add(aPanel, BorderLayout.CENTER);

        // Exit when the window is closed.
        aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Show the panel.
        aFrame.pack();
        aFrame.setVisible(true);
    }

    // Create and add the widgets to the panel.
    private void addWidgets() {
        // Create widgets.
        tf = new JTextField(100);
        tf.setDragEnabled(true);
        tl = new JLabel("Drop Here", SwingConstants.LEFT);
        // text - задаем что перетаскиваем - т.е. здесь это текст. Также можно перетаскивать цвета фона и текста и т.д.
        tl.setTransferHandler(new TransferHandler("text"));
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent)e.getSource();
                TransferHandler th = c.getTransferHandler();
                th.exportAsDrag(c, e, TransferHandler.COPY);
            }
        };
        tl.addMouseListener(ml);

        // Add widgets to container.
        aPanel.add(tf);
        aPanel.add(tl);

        tl.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    // main method
    public static void main(String[] args)
    {
        // Set the look and feel.
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {}

        JLabelDragNDrop example = new JLabelDragNDrop();
    }

}
