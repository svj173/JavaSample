package swing.label;


import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.DEFAULT_SIZE;

/**
 * Проблема правильного определения длины текста в пикселях, согласно фонам или применения HTML.
 * <BR/>
 * <BR/> Выводится многострочное текстовое поле и кнопка PACK.
 * Размер фрейма можно изментяь как угодно, но по нажатию кнопки PACK - фрейм строк по тексту.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.03.2011 14:30:40
 */
public class TextSizeProblem02 extends JFrame
{
    public TextSizeProblem02()
    {

    	String dummyString = "";
    	for (int i = 0; i < 100; i++) {
    		dummyString += " word" + i; // Create a long text
    	}
    	JLabel text = new JLabel();
    	text.setText("<html>" + dummyString + "</html>");

    	Dimension prefSize = getPreferredSize(text.getText(), true, 400);

    	JButton packMeButton = new JButton("pack");
    	packMeButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			pack();
    		}
    	});



    	GroupLayout layout = new GroupLayout(this.getContentPane());
    	getContentPane().setLayout(layout);
    	layout.setVerticalGroup(layout.createParallelGroup().addComponent(packMeButton)
    			.addComponent(text,DEFAULT_SIZE, prefSize.height, prefSize.height));
    	layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(packMeButton)
    			.addComponent(text, DEFAULT_SIZE, prefSize.width, prefSize.width) // Lock the width to 400
    			);

    	pack();
    }

    public static void main(String args[]) {
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			JFrame frame = new TextSizeProblem();
    			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    			frame.setVisible(true);
    		}
    	});
    }

    private static final JLabel resizer = new JLabel();

    /**
     * Returns the preferred size to set a component at in order to render an html string. You can
     * specify the size of one dimension.
     */
    public static java.awt.Dimension getPreferredSize(String html, boolean width, int prefSize) {

    	resizer.setText(html);

    	View view = (View) resizer.getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey);

    	view.setSize ( width ? prefSize : 0, width ? 0 : prefSize);

    	float w = view.getPreferredSpan(View.X_AXIS);
    	float h = view.getPreferredSpan(View.Y_AXIS);

    	return new java.awt.Dimension((int) Math.ceil(w), (int) Math.ceil(h));
    }
}
