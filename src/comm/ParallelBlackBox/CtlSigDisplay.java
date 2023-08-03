package comm.ParallelBlackBox;
import java.awt.Panel;
import java.awt.Label;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.comm.ParallelPort;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.5, 05/04/00
 */
public class CtlSigDisplay extends Panel {
    ParallelPort  port = null;
    Label	  SELLabel, BSYLabel, TOLabel, PELabel, POLabel, BELabel;
    boolean       DA, BE;
    private Color onColor, offColor;

    /**
     * Constructor declaration
     *
     *
     * @param port
     *
     * @see
     */
    public CtlSigDisplay(ParallelPort port) {
	super();

	this.setPort(port);
	this.setLayout(new FlowLayout());

	SELLabel = new Label("SEL");

	this.add(SELLabel);

	BSYLabel = new Label("BSY");

	this.add(BSYLabel);

	TOLabel = new Label("TO");

	this.add(TOLabel);

	PELabel = new Label("PE");

	this.add(PELabel);

	POLabel = new Label("PO");

	this.add(POLabel);

	DA = false;
	BELabel = new Label("BE");

	this.add(BELabel);

	BE = true;
	onColor = Color.green;
	offColor = Color.black;
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
     * @see
     */
    public void showValues() {
	if (this.port != null) {

	    /*
	     * Get the state of the control signals for this port
	     */
	    SELLabel.setForeground(port.isPrinterSelected() ? onColor 
				   : offColor);
	    BSYLabel.setForeground(port.isPrinterBusy() ? onColor : offColor);
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void showErrorValues() {
	if (this.port != null) {

	    /*
	     * Get the state of the error conditions for this port
	     */
	    TOLabel.setForeground(port.isPrinterTimedOut() ? onColor 
				  : offColor);
	    PELabel.setForeground(port.isPrinterError() ? onColor : offColor);
	    POLabel.setForeground(port.isPaperOut() ? onColor : offColor);
	    BELabel.setForeground(BE ? onColor : offColor);
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void clearValues() {
	SELLabel.setForeground(offColor);
	BSYLabel.setForeground(offColor);
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    public void clearErrorValues() {
	TOLabel.setForeground(offColor);
	PELabel.setForeground(offColor);
	POLabel.setForeground(offColor);
	BELabel.setForeground(offColor);
    } 

}




