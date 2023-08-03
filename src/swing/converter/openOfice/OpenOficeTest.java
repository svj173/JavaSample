package swing.converter.openOfice;


import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 *  Библиотеки
 *   juh.jar
     jurt.jar
     ridl.jar
     unoil.jar

 You can find these jar files in the installation directory in the subfolder program/classes. On linux for example a folder similar to /opt/openoffice.org 2.4/program/classes (on OOo 1.x.x and OOo 2.x.x).

 The folder structure of OOo 3.0.0 changed a lot. The jars juh.jar, jurt.jar and ridl.jar are available at openoffice.org/ure/share/java
 and unoil.jar is available at openoffice.org/basis3.0/program/classes.

 You also need the program directory on your class path ( eg: /opt/openoffice.org3/program ), otherwise you’ll get the following error message: com.sun.star.comp.helper.BootstrapException: no office executable found!



  Необходим установленный OpenOfice - библиотека с ним взаимодействует. Т.е. без него работать не будет, что - минус (т.е. не создашь локальный файл .ODT)

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.02.2013 13:24
 */
public class OpenOficeTest
{
    public static void main ( String[] args ) throws DocumentException, MalformedURLException, IOException
    {
        /*
        try
        {
            // --------- First thing to do is to connect to the OpenOffice. They following code will start an instance of OpenOffice. ---------

            // Get the remote office component context
            XComponentContext xContext = Bootstrap.bootstrap();

            // Get the remote office service manager
            XMultiComponentFactory xMCF = xContext.getServiceManager();

            // Get the root frame (i.e. desktop) of openoffice framework.
            Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);

            // Desktop has 3 interfaces. The XComponentLoader interface provides ability to load components.
            XComponentLoader xCLoader = ( XComponentLoader ) UnoRuntime.queryInterface(XComponentLoader.class, oDesktop);


            // --------- Now, we have to create an empty document in the new writer window.

            // Create a document
            XComponent document = xCLoader.loadComponentFromURL("private:factory/swriter", "_blank", 0, new PropertyValue[0]);

            // Get the textdocument
            XTextDocument aTextDocument = ( XTextDocument )UnoRuntime.queryInterface(com.sun.star.text.XTextDocument.class, document);

            // Get its text
            XText xText = aTextDocument.getText();


            // -------- Now i can append my first piece text to the document

            // Adding text to document
            xText.insertString(xText.getEnd(), "My First OpenOffice Document", false);


            // ---------- To set font family, style and size, we should set the property values CharFontName, CharFontStyleName and CharHeight of the XTextRange

            XTextRange xTextRange = xText.createTextCursor();
            ((XTextCursor)xTextRange).gotoEnd(true);

            XPropertySet xTextProps = (XPropertySet) UnoRuntime.queryInterface(
            XPropertySet.class, xTextRange);
            xTextProps.setPropertyValue("CharFontName", "Times New Roman");
            xTextProps.setPropertyValue("CharFontStyleName", "Regular");
            xTextProps.setPropertyValue("CharHeight", new Float(size));


            // -------- To create a table we need to create an instance of XMultiServiceFactory. And also the table ahould be initialize with total rows and columns before it can be inserted into the document

            XMultiServiceFactory xMSF = ( XMultiServiceFactory ) UnoRuntime.queryInterface(XMultiServiceFactory.class, document);

            // Creating a table with 3 rows and 4 columns
            XTextTable xTextTable = ( XTextTable ) UnoRuntime.queryInterface(XTextTable.class, xMSF.createInstance( "com.sun.star.text.TextTable" ) );
            xTextTable.initialize( 2, 2); // rows, cols

            // insert table  in the xText
            xText.insertTextContent(xText.getEnd(), xTextTable, false);


            // ----- Now to add data to the cells in table

            XCellRange xCellRangeHeader = (XCellRange) UnoRuntime.queryInterface(XCellRange.class, table);
            XCell xCellHeader = null;
            XText xHeaderText = null;

            xCellHeader = xCellRangeHeader.getCellByPosition(0, 0); // cols, rows
            xHeaderText = (XText) UnoRuntime.queryInterface(XText.class, xCellHeader);
            xHeaderText.setString("A1");

            xCellHeader = xCellRangeHeader.getCellByPosition(1, 0); // cols, rows
            xHeaderText = (XText) UnoRuntime.queryInterface(XText.class, xCellHeader);
            xHeaderText.setString("A2");

            xCellHeader = xCellRangeHeader.getCellByPosition(0, 1); // cols, rows
            xHeaderText = (XText) UnoRuntime.queryInterface(XText.class, xCellHeader);
            xHeaderText.setString("B1");

            xCellHeader = xCellRangeHeader.getCellByPosition(1, 1); // cols, rows
            xHeaderText = (XText) UnoRuntime.queryInterface(XText.class, xCellHeader);
            xHeaderText.setString("B2");


            // --------- The default paper format and orientation is A4 and portrait. To change paper orientation

            XPrintable xPrintable = ( XPrintable ) UnoRuntime.queryInterface(XPrintable.class, document);
            PropertyValue[] printerDesc = new PropertyValue[2];

            // Paper Orientation
            printerDesc[0] = new PropertyValue();
            printerDesc[0].Name = "PaperOrientation";
            printerDesc[0].Value = PaperOrientation.LANDSCAPE;

            // Paper Format
            printerDesc[1] = new PropertyValue();
            printerDesc[1].Name = "PaperFormat";
            printerDesc[1].Value = PaperFormat.A3;

            xPrintable.setPrinter(printerDesc);


            // -------- Saving the Document

            // the url where the document is to be saved
            String storeUrl = "file:///tmp/OOo_doc.odt";

            // Save the document
            XStorable xStorable = ( XStorable )UnoRuntime.queryInterface(XStorable.class, document);
            PropertyValue[] storeProps = new PropertyValue[0];
            xStorable.storeAsURL(storeUrl, storeProps);


            // ------------------------------------- PDF -------------------------------------------------

            // ----- When you want to export it to PDF, you need to specify a filter name: writer_pdf_Export. Also note that you need to call storeToUrl instead of storeAsUrl, otherwise you’ll get the following exception: com.sun.star.task.ErrorCodeIOException.

            // export document to pdf
            storeProps = new PropertyValue[1];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = "FilterName";
            storeProps[0].Value = "writer_pdf_Export";

            xStorable.storeToURL("file:///tmp/OOo_doc.pdf", storeProps);


            // ---- We can also save the document in other formats by specifying the filter name. For example, to save to rich text format

            // the url where the document is to be saved
            String storeUrl = “file:///tmp/OOo_doc.rtf ";

            // Save the document
            XStorable xStorable = ( XStorable )UnoRuntime.queryInterface(XStorable.class, document);
            PropertyValue[] storeProps = new PropertyValue[0];
            storeProps[0] = new PropertyValue();
            storeProps[0].Name = "FilterName";
            storeProps[0].Value = "Rich Text Format";

            xStorable.storeAsURL(storeUrl, storeProps);


            // ----- Printing the Document

            XPrintable xPrintable = (XPrintable)UnoRuntime.queryInterface(XPrintable.class, document);
            PropertyValue[] printerDesc = new PropertyValue[1];
            printerDesc[0] = new PropertyValue();
            printerDesc[0].Name = "Name";
            printerDesc[0].Value = "PDFCreator";
            xPrintable.setPrinter(printerDesc);

            PropertyValue[] printOpts = new PropertyValue[1];
            printOpts[0] = new PropertyValue();
            printOpts[0].Name = "Pages";
            printOpts[0].Value = "1";
            xPrintable.print(printOpts);


            // ------- close the document

            // close document
            XCloseable xcloseable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document);
            xcloseable.close(false);



        } catch ( Exception e )         {
            e.printStackTrace ();
        }
        */
    }

}
