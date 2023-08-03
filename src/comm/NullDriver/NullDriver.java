package comm.NullDriver;


import java.net.*;
import java.io.*;
import java.util.*;
import javax.comm.*;

/**
 * Class declaration
 *
 * @author
 * @version 1.8, 05/04/00
 */
public class NullDriver implements CommDriver
{

    /*
     * initialize() will be called by the CommPortIdentifier's static
     * initializer. The responsibility of this method is:
     * 1) Ensure that that the hardware is present.
     * 2) Load any required native libraries.
     * 3) Register the port names with the CommPortIdentifier.
     */

    /**
     * Method declaration
     *
     * @see
     */
    public void initialize ()
    {

        /*
      * Do native initialization here.
      * This may include loading any native shared libraries necessary.
      */
        /* Register port names with CommPortIdentifier */
        CommPortIdentifier.addPortName ( "SerialPort1",
                CommPortIdentifier.PORT_SERIAL, this );
        CommPortIdentifier.addPortName ( "SerialPort2",
                CommPortIdentifier.PORT_SERIAL, this );
    }

    /*
    * getCommPort() will be called by CommPortIdentifier from its open()
    * method. portName is a string that was registered earlier using the
    * CommPortIdentifier.addPortName() method. getCommPort() returns an
    * object that extends either SerialPort or ParallelPort.
    */

    /**
     * Method declaration
     *
     * @param portName
     * @param portType
     * @return
     * @see
     */
    public CommPort getCommPort ( String portName, int portType )
    {
        CommPort port = null;

        try {
            switch ( portType ) {

                case CommPortIdentifier.PORT_SERIAL:
                    port = ( CommPort ) new NullSerialPort ( portName );
                    break;

                case CommPortIdentifier.PORT_PARALLEL:
                    break;
            }
        } catch ( IOException ex ) {

            /*
            * We failed to construct the port object.
            * Most likely the port is owned by another application.
            */
        }

        return port;
    }

}




