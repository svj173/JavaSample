package fop;


import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.OutputStream;

/**
 * Генерить номера страниц в многостраничных документах - Номер страницы ставить внизу справа.
 * Если страница всего одна - нумерацию не выводить (Основная проблема которая решается при задании
 * принудительно двух проходв FOPa чтобы подсчитать кол-во страниц).
 * <BR>
 * <BR> Use: PageNumberTest.main xml_file xsl_file  pdf_file
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.01.2009
 * <BR> Time: 12:05:14
 */
public class PageNumberTest
{
  private static FopFactory         fopFactory = FopFactory.newInstance();
  private static TransformerFactory tFactory   = TransformerFactory.newInstance();

  public static void main ( String args[] )
  {
    OutputStream out;
    String       xml_file, xsl_file, pdf_file, pageCount;
    FOUserAgent  foUserAgent;
    Fop          fop;  // fopDriver
    Templates    templates;
    Transformer  transformer;

    xml_file = "/home/svj/IdeaProjects/JavaSample/test/fop/111.xml";        // args[0]
    //xml_file = "/home/svj/IdeaProjects/JavaSample/test/fop/page1.xml";      // args[0]

    //xsl_file = "/home/svj/IdeaProjects/JavaSample/test/fop/ish.xsl";        // args[1]
    xsl_file = "/home/svj/IdeaProjects/JavaSample/test/fop/111.xsl";        // args[1]

    pdf_file = "/home/svj/IdeaProjects/JavaSample/test/fop/pdf_111.pdf";    // args[2]

    try
    {
      // Load the stylesheet - XSL
      templates   = tFactory.newTemplates ( new StreamSource(new File(xsl_file)) );

      // First run (to /dev/null)
      out         = new org.apache.commons.io.output.NullOutputStream();
      foUserAgent = fopFactory.newFOUserAgent();
      fop         = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

      transformer = templates.newTransformer();
      transformer.setParameter ("page-count", "#");
      transformer.transform ( new StreamSource(new File(xml_file)), new SAXResult(fop.getDefaultHandler()) );

      // Get total page count
      pageCount   = Integer.toString ( fop.getResults().getPageCount() );

      // Second run (the real thing)
      out = new java.io.FileOutputStream(pdf_file);
      out = new java.io.BufferedOutputStream(out);
      try
      {
        foUserAgent = fopFactory.newFOUserAgent();
        fop         = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
        transformer = templates.newTransformer();
        transformer.setParameter ("page-count", pageCount);
        transformer.transform ( new StreamSource(new File(xml_file)), new SAXResult(fop.getDefaultHandler()) );
      } finally {
        out.close();
      }

    } catch( Exception e) {
      e.printStackTrace();
    }
  }
}
