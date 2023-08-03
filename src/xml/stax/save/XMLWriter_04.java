package xml.stax.save;


import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;


/**
 * <BR/>
 * <BR/> Выводит
 * < ?xml version="1.0" encoding="UTF-8"?>
< !DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
< html xmlns="http://www.w3.org/1999/xhtml" lang="en">
< head><title>Test</title></head>
< body>This is a test.</body></html>
 * <BR/>
 * <BR/> PS
 * <BR/> 1) Символы & перекодирует в &amp;
 * <BR/> 2) Текст &lt; перобразуетк виду &amp;lt;
 * <BR/> 3) Текст <> -- &lt;&gt;
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.08.2011 11:37:49
 */
public class XMLWriter_04
{
    public static void main(String[] args) throws XMLStreamException
    {
        final String XHTML_NS = "http://www.w3.org/1999/xhtml";
        final QName HTML_TAG = new QName(XHTML_NS, "html");
        final QName HEAD_TAG = new QName(XHTML_NS, "head");
        final QName TITLE_TAG = new QName(XHTML_NS, "title");
        final QName BODY_TAG = new QName (XHTML_NS, "body");

        XMLOutputFactory f = XMLOutputFactory.newInstance();
        XMLEventWriter w = f.createXMLEventWriter(System.out);
        XMLEventFactory ef = XMLEventFactory.newInstance();
        try {
              w.add(ef.createStartDocument());
              w.add(ef.createIgnorableSpace("\n"));
              w.add(ef.createDTD("<!DOCTYPE html " +
                          "PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" " +
                          "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"));
              w.add(ef.createIgnorableSpace("\n"));
              w.add(ef.createStartElement(HTML_TAG, null, null));
              w.add(ef.createNamespace(XHTML_NS));
              w.add(ef.createAttribute("lang", "en"));
              w.add(ef.createIgnorableSpace("\n"));
              w.add(ef.createStartElement(HEAD_TAG, null, null));
              w.add(ef.createStartElement(TITLE_TAG, null, null));
              w.add(ef.createCharacters("Test"));
              w.add(ef.createEndElement(TITLE_TAG, null));
              w.add(ef.createEndElement(HEAD_TAG, null));
              w.add(ef.createIgnorableSpace("\n"));
              w.add(ef.createStartElement(BODY_TAG, null, null));
              w.add(ef.createCharacters("This is a &test&lt; <>."));
              w.add(ef.createEndElement(BODY_TAG, null));
              w.add(ef.createEndElement(HTML_TAG, null));
              w.add(ef.createEndDocument());
        } finally {
              w.close();
        }
    }
}