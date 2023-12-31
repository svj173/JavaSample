package comm.ParallelBlackBox;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.comm.ParallelPort;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.5, 05/04/00
 */
public class ByteStatistics extends Panel {
    private BufferSize   buffer;
    private ByteCounter  counter, rate;
    private long	 lastTime, lastBaudRate, lastCount;
    private int		 bitsPerCharacter;
    private boolean      avgRate, inputBuffer;
    private ParallelPort port;

    /**
     * Constructor declaration
     *
     *
     * @param text
     * @param size
     * @param port
     * @param inputBuffer
     * @param average
     *
     * @see
     */
    public ByteStatistics(String text, int size, ParallelPort port, 
			  boolean inputBuffer, boolean average) {
	super();

	this.inputBuffer = inputBuffer;

	this.setLayout(new BorderLayout());

	counter = new ByteCounter(text, size);

	this.add("West", counter);

	rate = new ByteCounter("Baud Rate", 6);

	this.add("East", rate);

	buffer = new BufferSize(6, port, inputBuffer);

	this.add("Center", buffer);
	this.setBitsPerCharacter(10);
	this.setPort(port);

	this.lastTime = 0;
	this.lastCount = 0;
	this.lastBaudRate = 0;

	if (average) {
	    this.avgRate = true;
	} else {
	    this.avgRate = false;
	} 
    }

    /**
     * Constructor declaration
     *
     *
     * @param text
     * @param size
     * @param port
     * @param inputBuffer
     *
     * @see
     */
    public ByteStatistics(String text, int size, ParallelPort port, 
			  boolean inputBuffer) {
	this(text, size, port, inputBuffer, true);
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

	this.buffer.setPort(port);
    } 

    /**
     * Method declaration
     *
     *
     * @param val
     *
     * @see
     */
    public void setBitsPerCharacter(int val) {
	this.bitsPerCharacter = val;
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void showValues() {
	this.buffer.showValue();
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void clearValues() {
	this.buffer.setValue(0);
	this.counter.setValue(0);
	this.rate.setValue(0);
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
	return this.counter.getValue();
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
	if (val == 0) {
	    this.resetRate();
	} 

	this.counter.setValue(val);
	this.setRate();
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
	this.counter.incrementValue(val);
	this.setRate();
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void resetRate() {

	/*
	 * if (port != null)
	 * {
	 * if (inputBuffer)
	 * {
	 * System.out.println("Reseting receive baud rate for "
	 * + port.getName());
	 * }
	 * 
	 * else
	 * {
	 * System.out.println("Reseting transmit baud rate for "
	 * + port.getName());
	 * }
	 * }
	 */
	this.lastTime = 0;
	this.lastBaudRate = 0;
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    private void setRate() {
	long baudRate, time = System.currentTimeMillis(), 
	     val = this.counter.getValue();

	if ((this.rate.getValue() == 0) && (this.lastBaudRate != 0)) {
	    this.resetRate();
	} 

	if ((this.lastTime == 0) || (val == 0) || (val < this.lastCount)) {
	    this.lastTime = time;
	    this.lastCount = val;
	} else if (time > this.lastTime) {
	    baudRate = ((val - this.lastCount) * this.bitsPerCharacter * 1000) 
		       / (time - this.lastTime);

	    // System.out.println("((" + val + " - " + this.lastCount + ")"
	    // + " * " + this.bitsPerCharacter
	    // + " * 1000 ) / "
	    // + "(" + time + " - " + this.lastTime + ")");
	    if (this.avgRate) {

		/*
		 * This is a silly hack to auto-reset
		 * the counter if the user stops transmitting
		 */
		if (inputBuffer && (baudRate < 10)) {
		    this.resetRate();
		} 
	    } else {
		this.lastCount = val;
		this.lastTime = time;
	    } 

	    this.rate.setValue((int) baudRate);

	    this.lastBaudRate = baudRate;
	} 
    } 

}




