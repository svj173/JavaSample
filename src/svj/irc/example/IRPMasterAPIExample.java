package svj.irc.example;

/*
import org.harctoolbox.IrpMaster.IrSignal;
import org.harctoolbox.IrpMaster.IrpMaster;

import java.util.*;
//*/
/**
 * Использует другую библиотеку т.к. IrSignal здесь находится совсем в другом месте.
 * <BR/>
 * <BR/> http://www.harctoolbox.org/IRPMasterAPIExample.java
 * <BR/>
 * Не проще первого метода
 */
public class IRPMasterAPIExample {

    // Parameters used to communicate with the particular GC
    public static final String globalcacheIP = "192.168.1.70";

    private static void usage() {
        System.err.println("Usage:");
        System.err.println("\tIRPMasterAPIExample IrpMaster.ini-path protocolname parameterassignments...");
        System.exit(1);
    }

    // Usage: IRPMasterAPIExample <configfilename> <protocolname> <device> <function>
    // Example: IRPMasterAPIExample /usr/local/irscrutinizer/IrpProtocols.ini rc5 D=0 F=12
    public static void main(String[] args) {

        /*
        // This hashmap holds the actual parameter values used to render the signal
        HashMap<String, Long> parameters = new HashMap<>();

        //if (args.length < 3)
        //    usage();

        String configFile = "/usr/local/irscrutinizer/IrpProtocols.ini";

        try {
            // Create an IrpMaster instance from the configuration file
            IrpMaster irpMaster = new IrpMaster(configFile);

            // Протокол=Samsung20, D=1, S=8, F=63
            String protocolName = "Samsung20";

            parameters.put("D", 1L);
            parameters.put("S", 8L);
            parameters.put("F", 63L);


            // Render the signal using current parameters
            IrSignal irSignal = new IrSignal(irpMaster, protocolName, parameters);
            System.out.println("irSignal = " + irSignal);

            // Create a GlobalCache object
            //GlobalCache globalCache = new GlobalCache(globalcacheIP, true);

            // ... and send it the recently rendered IR signal
            //globalCache.sendIr(irSignal);
            // ... done!!

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        //*/
    }

}
