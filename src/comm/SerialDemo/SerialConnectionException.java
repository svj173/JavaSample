package comm.SerialDemo;


/**
 * Class declaration
 *
 *
 * @author
 * @version 1.7, 05/04/00
 */
public class SerialConnectionException extends Exception {

    /**
     * Constructs a <code>SerialConnectionException</code>
     * with the specified detail message.
     * 
     * @param   s   the detail message.
     */
    public SerialConnectionException(String str) {
	super(str);
    }

    /**
     * Constructs a <code>SerialConnectionException</code>
     * with no detail message.
     */
    public SerialConnectionException() {
	super();
    }

}




