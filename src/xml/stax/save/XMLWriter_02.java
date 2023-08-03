package xml.stax.save;


import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 * Должен вывести в консоль текст:
 * < ?xml version=’1.0’ encoding=’utf-8’?>
< a b="blah" xmlns:c="http://c" xmlns="http://c">
  < d:d d:chris="fry" xmlns:d="http://c"/>
  Jean Arp
< /a>
 * <BR/>
 * <BR/> Работает.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.08.2011 11:24:37
 */
public class XMLWriter_02
{
    public static void main(String[] args) throws XMLStreamException
    {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter( System.out );
        writer.writeStartDocument();
        writer.setPrefix("c","http://c");
        writer.setDefaultNamespace("http://c");
        writer.writeStartElement("http://c","a");
        writer.writeAttribute("b","blah");
        writer.writeNamespace("c","http://c");
        writer.writeDefaultNamespace("http://c");
        writer.setPrefix("d","http://c");
        writer.writeEmptyElement("http://c","d");
        writer.writeAttribute("http://c","chris","fry");
        writer.writeNamespace("d","http://c");
        writer.writeCharacters("Jean Arp");
        writer.writeEndElement();
        writer.flush();
    }
}
