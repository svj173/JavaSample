package comm.ParallelBlackBox;
import java.awt.Choice;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.comm.ParallelPort;
import javax.comm.UnsupportedCommOperationException;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.7, 05/04/00
 */
class LPTMode extends Choice implements ItemListener {
    private ParallelPortDisplay owner;

    /**
     * Constructor declaration
     *
     *
     * @param owner
     *
     * @see
     */
    public LPTMode(ParallelPortDisplay owner) {
	super();

	this.add("Unknown");
	this.add("Any");
	this.add("ECP");
	this.add("EPP");
	this.add("NIBBLE");
	this.add("PS2");
	this.add("SPP");
	this.addItemListener(this);

	this.owner = owner;
    }

    /**
     * Method declaration
     *
     *
     * @see
     */
    protected void showValue() {
	ParallelPort port = this.owner.port;

	if (this.owner.open) {

	    /*
	     * Get the port mode
	     */
	    switch (this.owner.port.getMode()) {

	    case ParallelPort.LPT_MODE_ANY:
		this.select("Any");

		break;

	    case ParallelPort.LPT_MODE_ECP:
		this.select("ECP");

		break;

	    case ParallelPort.LPT_MODE_EPP:
		this.select("EPP");

		break;

	    case ParallelPort.LPT_MODE_NIBBLE:
		this.select("NIBBLE");

		break;

	    case ParallelPort.LPT_MODE_PS2:
		this.select("PS2");

		break;

	    case ParallelPort.LPT_MODE_SPP:
		this.select("SPP");

		break;

	    default:
		this.select("Unknown");

		break;
	    }
	} else {
	    this.select("Unknown");
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param ev
     *
     * @see
     */
    public void itemStateChanged(ItemEvent ev) {
	ParallelPort port;
	String       sel = (String) ev.getItem();
	int	     value = 0;

	if (sel.equals("Any")) {
	    value = ParallelPort.LPT_MODE_ANY;
	} else if (sel.equals("ECP")) {
	    value = ParallelPort.LPT_MODE_ECP;
	} else if (sel.equals("EPP")) {
	    value = ParallelPort.LPT_MODE_EPP;
	} else if (sel.equals("NIBBLE")) {
	    value = ParallelPort.LPT_MODE_NIBBLE;
	} else if (sel.equals("PS2")) {
	    value = ParallelPort.LPT_MODE_PS2;
	} else if (sel.equals("SPP")) {
	    value = ParallelPort.LPT_MODE_SPP;
	} else {
	    this.showValue();

	    return;
	} 

	port = this.owner.port;

	if (port != null) {

	    /*
	     * Set the mode.
	     */
	    try {
		port.setMode(value);
	    } catch (UnsupportedCommOperationException e) {
		System.out.println("Cannot set mode to " + sel + " for port " 
				   + port.getName());
	    } 
	} 

	this.owner.showValues();
    } 

}




