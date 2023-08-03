package convert.docx;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 30.11.2017 12:22
 */
public class CreateWordHeaderFooter
{
    public static void main(String[] args) throws Exception {

     XWPFDocument doc= new XWPFDocument();

     // the body content
     XWPFParagraph paragraph = doc.createParagraph();
     XWPFRun run=paragraph.createRun();
     run.setText("The Body:");

     paragraph = doc.createParagraph();
     run=paragraph.createRun();
     run.setText("Lorem ipsum.... page 1");

     paragraph = doc.createParagraph();
     run=paragraph.createRun();
     run.addBreak(BreakType.PAGE);
     run.setText("Lorem ipsum.... page 2");

     paragraph = doc.createParagraph();
     run=paragraph.createRun();
     run.addBreak(BreakType.PAGE);
     run.setText("Lorem ipsum.... page 3");

     // create header-footer
     XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
     if (headerFooterPolicy == null) headerFooterPolicy = doc.createHeaderFooterPolicy();

     // -------- Верхний колонтитул . В нем только надпись (смещенная влево) -- The Header:
     // create header start
     XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

     paragraph = header.getParagraphArray(0);
     if (paragraph == null) paragraph = header.createParagraph();
     paragraph.setAlignment(ParagraphAlignment.LEFT);

     run = paragraph.createRun();
     run.setText("The Header:");

     // create footer start
     XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

     paragraph = footer.getParagraphArray(0);
     if (paragraph == null) paragraph = footer.createParagraph();
     paragraph.setAlignment(ParagraphAlignment.CENTER);

        // Внизу каждой страницы пишем : Page 1 of 3
     run = paragraph.createRun();
     run.setText("Page ");
     paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* MERGEFORMAT");     // ??? что это?
     run = paragraph.createRun();
     run.setText(" of ");
     paragraph.getCTP().addNewFldSimple().setInstr("NUMPAGES \\* MERGEFORMAT");

     doc.write(new FileOutputStream ("/home/svj/tmp/CreateWordHeaderFooter.docx"));

    }
}
