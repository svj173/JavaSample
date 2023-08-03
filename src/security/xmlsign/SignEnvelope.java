package security.xmlsign;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

//import org.apache.ws.security.WSConstants;

/**
 * Подпись xml-файла. Сигнатуру вставляет в сам файл, в виде xml-тега.
 * Использует сторонню библиотеку org.apache.ws.security
 */
public class SignEnvelope {

	static boolean verbose = false;
	//static Options options = new Options();
	static File inputXMLFile = null; 
	static File outputXMLFile = null; 
	static File inputPropertiesFile = null; 
	static String keyAlias = null;
	static String keyPassword = null;

	public static void main(String[] cmdLineArgs)
    {
		handleArgs(cmdLineArgs);
		Document toBeSigned = parseInputXML(inputXMLFile);
		Document signedXML = signEnvelope(toBeSigned);
		writeOutput(signedXML);
		
		if (verbose)
			System.out.println("Complete!");
		
		System.exit(0);		
	}
	
	public static void usage(Exception e)
    {
		if (e != null){
			System.err.println("Error!: "); 
			e.printStackTrace();
			System.err.println();
		}
	
		//HelpFormatter formatter = new HelpFormatter();
		//formatter.printHelp("SignEnvelope", options, true);

		System.exit(-1);
	}
	
	public static void handleArgs(String[] args){
        /*
		Option verboseOption = new Option("v","verbose",false,"enable verbose logging");
		verboseOption.setOptionalArg(true);
		options.addOption(verboseOption);
		
		Option inputXMLOption = new Option("in",true,"input XML document for signature");
		inputXMLOption.setArgName("inputXML");
		inputXMLOption.setRequired(true);
		options.addOption(inputXMLOption);
		
		Option outputXMLOption = new Option("out",true,"the filename which should contain the signed XML");
		outputXMLOption.hasArg();
		outputXMLOption.setArgName("outputXML");
		outputXMLOption.setRequired(true);
		options.addOption(outputXMLOption);

		Option propertiesOption = new Option("prop",true,"the crypto.properties file which contains keystore info");
		propertiesOption.setArgName("propertiesFile");
		propertiesOption.setRequired(true);
		options.addOption(propertiesOption);
		
		Option aliasOption = new Option("alias",true,"the alias to assign to the entry");
		aliasOption.hasArg();
		aliasOption.setArgName("alias");
		aliasOption.setRequired(true);
		options.addOption(aliasOption);
		
		Option keyPasswordOption = new Option("keypass",true,"the password for the key");
		keyPasswordOption.hasArg();
		keyPasswordOption.setArgName("keyPassword");
		keyPasswordOption.setRequired(true);
		options.addOption(keyPasswordOption);
		
	    CommandLineParser parser = new GnuParser();
	   
	    try {
	        CommandLine line = parser.parse(options, args);
	        
	        if(line.hasOption("verbose"))
	        	verbose = true;
	        
	        if (verbose)
				System.out.println("Parsing command line arguments...");
				        
	        if(line.hasOption("in")) {
	        	if (verbose)
	        		System.out.println(" will load input XML from " + line.getOptionValue("in") + " ...");
	        	inputXMLFile = new File(line.getOptionValue("in"));	
				if (inputXMLFile.exists() == false)
					usage(new IOException("inputXML not found!"));
	        }
	        if(line.hasOption("out")) {
	        	if (verbose)
	        		System.out.println(" will output signed XML to " + line.getOptionValue("out") + " ...");
	        	outputXMLFile = new File(line.getOptionValue("out"));
	        }
	        if(line.hasOption("prop")) {
	        	if (verbose)
	        		System.out.println(" will load crypto properties from " + line.getOptionValue("prop") + " ...");
	        	
	        	inputPropertiesFile = new File(line.getOptionValue("prop"));	
				if (inputPropertiesFile.exists() == false)
					usage(new IOException("properties file not found!"));
	        }
	        if(line.hasOption("alias")) {
	        	if (verbose)
	        		System.out.println(" will use the entry with alias '" + line.getOptionValue("alias") + "' ...");
	        	keyAlias = line.getOptionValue("alias");
	        }
	        if(line.hasOption("keypass")) {
	        	if (verbose)
	        		System.out.println(" will use the key password '" + line.getOptionValue("keypass") + "' ...");
	        	keyPassword = line.getOptionValue("keypass");
	        }
	    }
	    catch(ParseException exp) {
	        usage(exp);
	    }
		*/
	}
	
	public static Document parseInputXML(File input){

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// The DOM parser must be namespace aware
	    factory.setNamespaceAware(true);

		Document parsedXML = null;
        try {
        	if (verbose)
    			System.out.println("Parsing input XML file ...");
    		
        	DocumentBuilder builder = factory.newDocumentBuilder();
           	parsedXML = builder.parse(input);
     	} 
		catch (Exception e) {
           	usage(e);
       	} 

		return parsedXML;
	}

	public static Document signEnvelope ( Document unsignedXML )
    {
        /*
		if (verbose){
			System.out.println("Signing XML document ...");
			System.out.println(" Creating Signature object ...");
		}
		
		WSSecSignature signer = new WSSecSignature();
		signer.setUserInfo(keyAlias,keyPassword);
		signer.setKeyIdentifierType(WSConstants.BST_DIRECT_REFERENCE);
			
		if (verbose)
			System.out.println(" Creating WS-Security Header object ...");
		WSSecHeader header = new WSSecHeader();
		header.insertSecurityHeader(unsignedXML);	

		Document signedXML = null;
		
		try{
			if (verbose)
				System.out.println(" Creating Crypto object from " + inputPropertiesFile.toString() + " ...");
						
			Crypto crypto = CryptoFactory.getInstance(inputPropertiesFile.toString());
			
			if (verbose)
				System.out.println(" Writing Signature to WS-Security Header ...");
			
			signedXML = signer.build(unsignedXML, crypto, header);
		}
		catch (Exception e){
			usage(e);
		}
		
		return signedXML;
        */
		return null;
	}

	public static void writeOutput(Document toBeWritten){

		try{
			TransformerFactory tFactory = TransformerFactory.newInstance();
	  		Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

  			DOMSource source = new DOMSource(toBeWritten);
  			StreamResult result = new StreamResult(outputXMLFile);
  			if (verbose)
  				System.out.println("Writing signed XML to " + outputXMLFile.toString() + " ...");
  			
  			transformer.transform(source, result); 
		}
		catch (Exception e) {
   			usage(e);
		} 
	}
}
