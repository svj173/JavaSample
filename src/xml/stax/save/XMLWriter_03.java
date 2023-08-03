package xml.stax.save;


import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 * НЕ работает.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.08.2011 11:37:49
 */
public class XMLWriter_03
{
    public static void main(String[] args) throws XMLStreamException
    {
        final String XHTML_NS = "http://www.w3.org/1999/xhtml";
        XMLOutputFactory f = XMLOutputFactory.newInstance();
        XMLStreamWriter w = f.createXMLStreamWriter(System.out);
        try {
              w.writeStartDocument();
              w.writeCharacters("\n");
              w.writeDTD("<!DOCTYPE html " +
                          "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
                          "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
              w.writeCharacters("\n");
              w.writeStartElement(XHTML_NS, "html");
              w.writeDefaultNamespace(XHTML_NS);
              w.writeAttribute("lang", "en");
              w.writeCharacters("\n");
              w.writeStartElement(XHTML_NS, "head");
              w.writeStartElement(XHTML_NS, "title");
              w.writeCharacters("Test");
              w.writeEndElement();
              w.writeEndElement();
              w.writeCharacters("\n");
              w.writeStartElement(XHTML_NS, "body");
              w.writeCharacters("This is a test.");
              w.writeEndElement();
              w.writeEndElement();
              w.writeEndDocument();
        } finally {
              w.close();
        }
    }
}
