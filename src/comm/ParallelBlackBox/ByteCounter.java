package comm.ParallelBlackBox;

import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.5, 05/04/00
 */
public class ByteCounter extends Panel implements MouseListener {
    private long      value, defaultValue;
    private Label     countLabel;
    private TextField counter;

    /**
     * Constructor declaration
     *
     *
     * @param text
     * @param size
     * @param defaultValue
     *
     * @see
     */
    public ByteCounter(String text, int size, int defaultValue) {
	super();

	this.setLayout(new BorderLayout());

	this.countLabel = new Label(text);

	this.countLabel.addMouseListener(this);
	this.add("West", this.countLabel);

	this.counter = new TextField(new Integer(defaultValue).toString(), 
				     size);

	this.add("East", this.counter);

	this.value = defaultValue;
	this.defaultValue = defaultValue;
    }

    /**
     * Constructor declaration
     *
     *
     * @param text
     * @param size
     *
     * @see
     */
    public ByteCounter(String text, int size) {
	this(text, size, 0);
    }

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public long getValue() {
	return this.value;
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void setValue(long val) {
	this.value = val;

	this.counter.setText(new Long(this.value).toString());
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void setDefaultValue(long val) {
	this.defaultValue = val;
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void incrementValue(long val) {
	this.value += val;

	this.counter.setText(new Long(this.value).toString());
    } 

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void mouseExited(MouseEvent e) {}

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void mousePressed(MouseEvent e) {
	this.setValue(this.defaultValue);
    } 

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void mouseReleased(MouseEvent e) {}

}




