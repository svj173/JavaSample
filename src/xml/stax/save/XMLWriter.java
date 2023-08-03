package xml.stax.save;


/**
 * <BR/>
 * This tip has demonstrated the use of the cursor-based API of StAX for writing XML documents efficiently.
 * In the next tip, I will show how to merge two XML documents using the event-based API.
 * <BR/>
 * Note that StAX does not guarantee well-formed documents.
 * It is still possible to produce a document that violates the XML recommendation, such as a document with several root elements
 * or several XML prologues, or tag and attribute names containing whitespace or characters not supported by XML.
 * StAX implementations may check these issues but they are not required to do so (the reference implementation doesn't).
 * Nevertheless, the StAX XMLStreamWriter is a big improvement over outputting raw XML data, and it does
 * this at a fraction of the cost of using DOM.
 * <BR/>
 * <BR/> Результат должен вывести в консоль.
 * <BR/>  Не работает.
 * <BR/> Строка
 * xmlof.setProperty("javax.xml.stream.isPrefixDefaulting",Boolean.TRUE);
 * <BR/> - генерит сиключение.
 * <BR/> Если ее закоментарить то исключение на строке -
 * xmlw.writeStartElement(XHTML,"description");
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.08.2011 11:18:47
 */
import javax.xml.stream.*;

public class XMLWriter {

   // Namespaces
   private static final String GARDENING = "http://com.bdaum.gardening";
   private static final String XHTML = "http://www.w3.org/1999/xhtml";

   public static void main(String[] args) throws XMLStreamException
   {
      // Create an output factory
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();

      // Set namespace prefix defaulting for all created writers  -- java.lang.IllegalArgumentException: Property javax.xml.stream.isPrefixDefaultingis not supported
      xmlof.setProperty("javax.xml.stream.isPrefixDefaulting",Boolean.TRUE);

      // Create an XML stream writer
      XMLStreamWriter xmlw = xmlof.createXMLStreamWriter(System.out);

      // Write XML prologue
      xmlw.writeStartDocument();
      // Write a processing instruction
      xmlw.writeProcessingInstruction(
         "xml-stylesheet href='catalog.xsl' type='text/xsl'");
      // Now start with root element
      xmlw.writeStartElement("product");
      // Set the namespace definitions to the root element
      // Declare the default namespace in the root element
      xmlw.writeDefaultNamespace(GARDENING);
      // Writing a few attributes
      xmlw.writeAttribute("productNumber","3923-1");
      xmlw.writeAttribute("name","Nightshadow");
      // Declare XHTML prefix
//    xmlw.setPrefix("xhtml",XHTML);
      // Different namespace for description element
      xmlw.writeStartElement(XHTML,"description");
      // Declare XHTML namespace in the scope of the description element
//    xmlw.writeNamespace("xhtml",XHTML);
      xmlw.writeCharacters(
         "A tulip of almost black color. \nBlossoms in April & May");
      xmlw.writeEndElement();
      // Shorthand for empty elements
      xmlw.writeEmptyElement("supplier");
      xmlw.writeAttribute("name","Floral22");
//    xmlw.writeEndElement();
      // Write document end. This closes all open structures
      xmlw.writeEndDocument();
      // Close the writer to flush the output
      xmlw.close();
   }

}

