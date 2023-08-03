package comm.ParallelBlackBox;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.comm.ParallelPort;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.5, 05/04/00
 */
public class ReceiveOptions extends Panel {
    private ReceiveTimeout   timeout;
    private ReceiveThreshold threshold;
    private ReceiveFraming   framing;

    /**
     * Constructor declaration
     *
     *
     * @param port
     *
     * @see
     */
    public ReceiveOptions(ParallelPort port) {
	super();

	this.setLayout(new FlowLayout());

	timeout = new ReceiveTimeout(6, port);

	this.add(timeout);

	threshold = new ReceiveThreshold(6, port);

	this.add(threshold);

	framing = new ReceiveFraming(6, port);

	this.add(framing);
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
	this.timeout.setPort(port);
	this.threshold.setPort(port);
	this.framing.setPort(port);
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void showValues() {
	this.timeout.showValue();
	this.threshold.showValue();
	this.framing.showValue();
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void clearValues() {
	this.timeout.setValue(0);
	this.threshold.setValue(0);
	this.framing.setValue(0);
    } 

}




