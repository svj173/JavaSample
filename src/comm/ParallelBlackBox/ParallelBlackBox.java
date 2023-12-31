package comm.ParallelBlackBox;
import java.io.*;
import java.awt.Frame;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.ParallelPort;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;

/**
 * Class declaration
 *
 *
 * @author
 * @version 1.10, 09/06/00
 */
public class ParallelBlackBox extends Frame implements WindowListener {
    static int			 portNum = 0, panelNum = 0, rcvDelay = 0;
    static ParallelPortDisplay[] portDisp;
    static ParallelBlackBox      win;
    static boolean		 threaded = true, friendly = false;

    /**
     * Constructor declaration
     *
     *
     * @see
     */
    public ParallelBlackBox() {
	super("Parallel Port Black Box Tester");

	addNotify();
	addWindowListener(this);
    }

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowIconified(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowDeiconified(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowOpened(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowClosed(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowActivated(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowDeactivated(WindowEvent event) {}

    /**
     * Method declaration
     *
     *
     * @param event
     *
     * @see
     */
    public void windowClosing(WindowEvent event) {
	cleanup();
	dispose();
	System.exit(0);
    } 

    /**
     * Method declaration
     *
     *
     * @param args
     *
     * @see
     */
    public static void main(String[] args) {
	Enumeration	   ports;
	CommPortIdentifier portId;
	boolean		   allPorts = true, lineMonitor = false;
	int		   idx = 0;

	win = new ParallelBlackBox();

	win.setLayout(new FlowLayout());
	win.setBackground(Color.gray);

	portDisp = new ParallelPortDisplay[4];

	while (args.length > idx) {
	    if (args[idx].equals("-h")) {
		printUsage();
	    } else if (args[idx].equals("-f")) {
		friendly = true;

		System.out.println("Friendly mode");
	    } else if (args[idx].equals("-n")) {
		threaded = false;

		System.out.println("No threads");
	    } else if (args[idx].equals("-l")) {
		lineMonitor = true;

		System.out.println("Line Monitor mode");
	    } else if (args[idx].equals("-d")) {
		idx++;
		rcvDelay = new Integer(args[idx]).intValue();

		System.out.println("Receive delay = " + rcvDelay + " msecs");
	    } else if (args[idx].equals("-p")) {
		idx++;

		while (args.length > idx) {

		    /*
		     * Get the specific port
		     */
		    try {
			portId = 
			    CommPortIdentifier.getPortIdentifier(args[idx]);

			System.out.println("Opening port " 
					   + portId.getName());
			win.addPort(portId);
		    } catch (NoSuchPortException e) {
			System.out.println("Port " + args[idx] 
					   + " not found!");
		    } 

		    idx++;
		} 

		allPorts = false;

		break;
	    } else {
		System.out.println("Unknown option " + args[idx]);
		printUsage();
	    } 

	    idx++;
	} 

	if (allPorts) {

	    /*
	     * Get an enumeration of all of the comm ports
	     * on the machine
	     */
	    ports = CommPortIdentifier.getPortIdentifiers();

	    if (ports == null) {
		System.out.println("No comm ports found!");

		return;
	    } 

	    while (ports.hasMoreElements()) {

		/*
		 * Get the specific port
		 */
		portId = (CommPortIdentifier) ports.nextElement();
		win.addPort(portId);
	    } 
	} 

	if (portNum > 0) {
	    if (lineMonitor) {
		if (portNum >= 2) {
		    portDisp[0].setLineMonitor(portDisp[1], true);
		} else {
		    System.out.println("Need 2 ports for line monitor!");
		    System.exit(0);
		} 
	    } 
	} else {
	    System.out.println("No parallel ports found!");
	    System.exit(0);
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param portId
     *
     * @see
     */
    private void addPort(CommPortIdentifier portId) {

	/*
	 * Is this a parallel port?
	 */
	if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL) {

	    // Is the port in use?
	    if (portId.isCurrentlyOwned()) {
		System.out.println("Detected " + portId.getName() 
				   + " in use by " 
				   + portId.getCurrentOwner());
	    } 

	    /*
	     * Open the port and add it to our GUI
	     */
	    try {
		portDisp[portNum] = new ParallelPortDisplay(portId, threaded, 
							    friendly, 
							    rcvDelay, win);
		this.portNum++;
	    } catch (PortInUseException e) {
		System.out.println(portId.getName() + " in use by " 
				   + e.currentOwner);
	    } 
	} 
    } 

    /**
     * Method declaration
     *
     *
     * @param panel
     *
     * @see
     */
    public void addPanel(ParallelPortDisplay panel) {
	Dimension dim;
	Insets    ins;

	win.add(panel);
	win.validate();

	dim = panel.getSize();
	ins = win.getInsets();
	dim.height = 
	    ((this.panelNum + 1) * (dim.height + ins.top + ins.bottom)) + 10;
	dim.width = dim.width + ins.left + ins.right + 20;

	win.setSize(dim);
	win.setVisible(true);

	panelNum++;
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    static void printUsage() {
	System.out.println("Usage: ParallelBlackBox [-h] | [-f] [-l] [-n] [-d receive_delay] [-p ports]");
	System.out.println("Where:");
	System.out.println("\t-h	this usage message");
	System.out.println("\t-f	friendly - relinquish port if requested");
	System.out.println("\t-l	run as a line monitor");
	System.out.println("\t-n	do not use receiver threads");
	System.out.println("\t-d	sleep for receive_delay msecs after each read");
	System.out.println("\t-p	list of ports to open (separated by spaces)");
	System.exit(0);
    } 

    /**
     * Method declaration
     *
     *
     * @see
     */
    private void cleanup() {
	ParallelPort p;

	while (portNum > 0) {
	    portNum--;
	    panelNum--;

	    /*
	     * Close the port
	     */
	    p = portDisp[portNum].getPort();

	    if (p != null) {
		System.out.println("Closing port " + portNum + " (" 
				   + p.getName() + ")");
		portDisp[portNum].closePBBPort();
	    } 
	} 
    } 

}




