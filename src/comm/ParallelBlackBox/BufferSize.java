package comm.ParallelBlackBox;


import java.awt.Panel;
import java.awt.Label;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.comm.ParallelPort;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.5, 05/04/00
 */
public class BufferSize extends Panel implements MouseListener, 
	ActionListener {
    private int		 value, defaultValue;
    private Label	 label;
    private TextField    data;
    private ParallelPort port = null;
    private boolean      inputBuffer;

    /**
     * Constructor declaration
     *
     *
     * @param size
     * @param port
     * @param inputBuffer
     *
     * @see
     */
    public BufferSize(int size, ParallelPort port, boolean inputBuffer) {
	super();

	this.setPort(port);

	this.inputBuffer = inputBuffer;

	this.setLayout(new BorderLayout());

	this.label = new Label("Buffer Size");

	this.label.addMouseListener(this);
	this.add("West", this.label);

	this.data = new TextField(new Integer(defaultValue).toString(), size);

	this.data.addActionListener(this);
	this.add("East", this.data);
	this.showValue();

	this.defaultValue = this.value;
    }

    /**
     * Method declaration
     *
     *
     * @param port
     *
     * @see
     */
    public void setPort(ParallelPort port) {
	this.port = port;
    } 

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public int getValue() {
	if (this.port != null) {

	    /*
	     * Get the buffer size.
	     */
	    if (inputBuffer) {
		this.value = port.getInputBufferSize();
	    } else {
		this.value = port.getOutputBufferSize();
	    } 

	    return this.value;
	} else {
	    return (0);
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void showValue() {
	this.data.setText(new Integer(this.getValue()).toString());
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void setValue(int val) {
	if (this.port != null) {

	    /*
	     * Set the new buffer size.
	     */
	    if (inputBuffer) {
		port.setInputBufferSize(val);
	    } else {
		port.setOutputBufferSize(val);
	    } 
	} 

	this.showValue();
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void setDefaultValue(int val) {
	this.defaultValue = val;
    } 

    /**
     * Method declaration
     *
     *
     * @param e
     *
     * @see
     */
    public void actionPerformed(ActionEvent e) {
	String s = e.getActionCommand();

	try {
	    Integer newValue = new Integer(s);

	    this.setValue(newValue.intValue());
	} catch (NumberFormatException ex) {
	    System.out.println("Bad value = " + e.getActionCommand());
	    this.showValue();
	} 
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




