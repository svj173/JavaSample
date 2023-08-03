package swing.converter.iText;


/**
 * iText.
 * Читает существующий pdf-файл в java-обьект. Копирует его в другой файл, попутно что-то изменяя.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.02.2013 13:02
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class ReadAndUsePdf
{
    private static String INPUTFILE     = "/home/svj/tmp/FirstPdf.pdf";
    private static String OUTPUTFILE    = "/home/svj/tmp/ReadPdf.pdf";

    public static void main ( String[] args ) throws DocumentException,
            IOException
    {
        Document document = new Document ();

        PdfWriter writer = PdfWriter.getInstance ( document, new FileOutputStream ( OUTPUTFILE ) );
        document.open ();
        PdfReader reader = new PdfReader ( INPUTFILE );
        int n = reader.getNumberOfPages ();

        PdfImportedPage page;
        // Go through all pages
        for ( int i = 1; i <= n; i++ )
        {
            // Only page number 2 will be included
            if ( i == 2 )
            {
                page = writer.getImportedPage ( reader, i );
                Image instance = Image.getInstance ( page );
                document.add ( instance );
            }
        }
        document.close ();
    }

}
