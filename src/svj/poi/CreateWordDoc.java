package svj.poi;


import org.apache.poi.xwpf.usermodel.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Amit
 */
public class CreateWordDoc {

     public static void main(String[] args) throws IOException {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraphOne = document.createParagraph();
        paragraphOne.setAlignment(ParagraphAlignment.CENTER);
        paragraphOne.setBorderBottom(Borders.SINGLE);
        paragraphOne.setBorderTop(Borders.SINGLE);
        paragraphOne.setBorderRight(Borders.SINGLE);
        paragraphOne.setBorderLeft(Borders.SINGLE);
        paragraphOne.setBorderBetween(Borders.SINGLE);

        XWPFRun paragraphOneRunOne = paragraphOne.createRun();
        paragraphOneRunOne.setBold(true);
        paragraphOneRunOne.setItalic(true);
        paragraphOneRunOne.setText("Hello world! This is paragraph one!");
        paragraphOneRunOne.addBreak();

        XWPFRun paragraphOneRunTwo = paragraphOne.createRun();
        paragraphOneRunTwo.setText("Run two!");
        paragraphOneRunTwo.setTextPosition(100);

        XWPFRun paragraphOneRunThree = paragraphOne.createRun();
        paragraphOneRunThree.setStrike(true);
        paragraphOneRunThree.setFontSize(20);
        paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
        paragraphOneRunThree.setText(" More text in paragraph one...");

        XWPFParagraph paragraphTwo = document.createParagraph();
        paragraphTwo.setAlignment(ParagraphAlignment.DISTRIBUTE);
        paragraphTwo.setIndentationRight(200);
        XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
        paragraphTwoRunOne.setText("And this is paragraph two.");

        FileOutputStream outStream = null;
        try {
           String fileName;
           //fileName = "C:/Users/Amit/Downloads/Amit.doc";
           fileName = "/home/svj/tmp/testDoc.doc";
            outStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
           e.printStackTrace ();
        }

        try {
            document.write(outStream);
            outStream.close();
        } catch (Exception e) {
           e.printStackTrace ();
        }
    }

}