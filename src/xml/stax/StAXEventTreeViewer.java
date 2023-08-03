package xml.stax;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * Показывает во фрейме структуру XML документа - все атрибуты и т.д.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.11.2009 12:14:38
 */
public class StAXEventTreeViewer extends JFrame
{
    /**
     * The base tree to render
     */
    private JTree jTree;

    /**
     * Tree model to use
     */
    DefaultTreeModel defaultTreeModel;

    public StAXEventTreeViewer ()
    {
        // Handle Swing setup
        super ( "StAX Tree Viewer" );
        setSize ( 600, 450 );
    }

    public void init ( File file ) throws XMLStreamException,
            FileNotFoundException
    {
        DefaultMutableTreeNode base = new DefaultMutableTreeNode (
                "XML Document: " + file.getAbsolutePath () );

        // Build the tree model
        defaultTreeModel = new DefaultTreeModel ( base );
        jTree = new JTree ( defaultTreeModel );

        // Construct the tree hierarchy
        buildTree ( defaultTreeModel, base, file );

        // Display the results
        getContentPane ().add ( new JScrollPane ( jTree ), BorderLayout.CENTER );
    }

    // Swing-related variables and methods, including
    // setting up a JTree and basic content pane

    public void buildTree ( DefaultTreeModel treeModel, DefaultMutableTreeNode current, File file )
            throws XMLStreamException, FileNotFoundException
    {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance ();
        XMLEventReader reader = inputFactory.createXMLEventReader ( new FileInputStream ( file ) );

        while ( reader.hasNext () )
        {
            XMLEvent event = reader.nextEvent ();
            switch ( event.getEventType () )
            {
                case XMLStreamConstants.START_DOCUMENT:
                    StartDocument startDocument = ( StartDocument ) event;
                    DefaultMutableTreeNode version = new DefaultMutableTreeNode (
                            "XML Version: " + startDocument.getVersion () );
                    current.add ( version );

                    DefaultMutableTreeNode standalone = new DefaultMutableTreeNode (
                            "Standalone? " + startDocument.isStandalone () );
                    current.add ( standalone );

                    DefaultMutableTreeNode standaloneSet = new DefaultMutableTreeNode (
                            "Was Standalone Set? " + startDocument.standaloneSet () );
                    current.add ( standaloneSet );

                    DefaultMutableTreeNode encoding = new DefaultMutableTreeNode (
                            "Was Encoding Set? " + startDocument.encodingSet () );
                    current.add ( encoding );

                    DefaultMutableTreeNode declaredEncoding = new DefaultMutableTreeNode (
                            "Declared Encoding: " + startDocument.getCharacterEncodingScheme () );
                    current.add ( declaredEncoding );
                    break;

                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = ( StartElement ) event;
                    QName elementName = startElement.getName ();

                    DefaultMutableTreeNode element = new DefaultMutableTreeNode (
                            "Element: " + elementName.getLocalPart () );
                    current.add ( element );
                    current = element;

                    if ( !elementName.getNamespaceURI ().equals ( "" ) )
                    {
                        String prefix = elementName.getPrefix ();
                        if ( prefix.equals ( "" ) )
                        {
                            prefix = "[None]";
                        }
                        DefaultMutableTreeNode namespace = new DefaultMutableTreeNode (
                                "Namespace: prefix = '" + prefix + "', URI = '"
                                        + elementName.getNamespaceURI () + "'" );
                        current.add ( namespace );
                    }

                    for ( Iterator it = startElement.getAttributes (); it.hasNext (); )
                    {
                        Attribute attr = ( Attribute ) it.next ();
                        DefaultMutableTreeNode attribute = new DefaultMutableTreeNode (
                                "Attribute (name = '"
                                        + attr.getName ().getLocalPart ()
                                        + "', value = '" + attr.getValue () + "')" );
                        String attURI = attr.getName ().getNamespaceURI ();
                        if ( !attURI.equals ( "" ) )
                        {
                            String attPrefix = attr.getName ().getPrefix ();
                            if ( attPrefix.equals ( "" ) )
                            {
                                attPrefix = "[None]";
                            }
                            DefaultMutableTreeNode attNamespace = new DefaultMutableTreeNode (
                                    "Namespace: prefix = '" + attPrefix
                                            + "', URI = '" + attURI + "'" );
                            attribute.add ( attNamespace );
                        }
                        current.add ( attribute );
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    current = ( DefaultMutableTreeNode ) current.getParent ();
                    break;

                case XMLStreamConstants.CHARACTERS:
                    Characters characters = ( Characters ) event;
                    if ( !characters.isIgnorableWhiteSpace ()
                            && !characters.isWhiteSpace () )
                    {
                        String data = characters.getData ();
                        if ( data.length () != 0 )
                        {
                            DefaultMutableTreeNode chars = new DefaultMutableTreeNode (
                                    "Character Data: '" + characters.getData () + "'" );
                            current.add ( chars );
                        }
                    }
                    break;

                case XMLStreamConstants.DTD:
                    DTD dtde = ( DTD ) event;
                    DefaultMutableTreeNode dtd = new DefaultMutableTreeNode (
                            "DTD: '" + dtde.getDocumentTypeDeclaration () + "'" );
                    current.add ( dtd );

                default:
                    System.out.println ( event.getClass ().getName () );
            }
        }
    }

    public static void main ( String[] args )
    {
        String fileName;

        try
        {
            /*
              if ( args.length != 1 )
              {
                System.out.println ( "Usage: java javaxml3.StAXEventTreeViewer [XML Document]" );
                return;
              }
              fileName  = args[ 0 ] ;
            */
            fileName    = "/home/svj/projects/SVJ/WEdit-6/test/test_sb/proza/srok_avansom_ISH.book";

            StAXEventTreeViewer viewer = new StAXEventTreeViewer ();
            File f = new File ( fileName );

            viewer.init ( f );
            viewer.setVisible ( true );
        } catch ( Exception e )
        {
            e.printStackTrace ();
        }
    }

}
