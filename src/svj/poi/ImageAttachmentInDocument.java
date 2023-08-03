package svj.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class ImageAttachmentInDocument {
    /**
     * @param args
     */
        public static void main(String[] args) {
        // TODO Auto-generated method stub
            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date=dateFormat.format(cal.getTime());
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraphOne = document.createParagraph();
            paragraphOne.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphOneRunOne = paragraphOne.createRun();
            paragraphOneRunOne.setBold(true);
            paragraphOneRunOne.setFontSize(20);
            paragraphOneRunOne.setFontFamily("Verdana");
            paragraphOneRunOne.setColor("000070");
            paragraphOneRunOne.setText("Daily Status Report");
            //XWPFRun paragraphOneRunTwo = paragraphOne.createRun();
            //paragraphOneRunTwo.setText(" More text in paragraph one...");
            XWPFParagraph paragraphTwo = document.createParagraph();
            paragraphTwo.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
            //paragraphTwoRunOne.setBold(true);
            paragraphTwoRunOne.setFontSize(12);
            paragraphTwoRunOne.setFontFamily("Verdana");
            paragraphTwoRunOne.setColor("000070");
            paragraphTwoRunOne.setText(date);
            paragraphTwoRunOne.addBreak();
            XWPFParagraph paragraphThree = document.createParagraph();
            paragraphThree.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun paragraphThreeRunOne = paragraphThree.createRun();
            //paragraphThreeRunOne.setBold(true);
            paragraphThreeRunOne.setFontSize(14);
            paragraphThreeRunOne.setFontFamily("Verdana");
            paragraphThreeRunOne.setColor("000070");
            paragraphThreeRunOne.setText("5.30 AM PST");
            paragraphThreeRunOne.addBreak();
            XWPFParagraph paragraphFour = document.createParagraph();
            paragraphFour.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun paragraphFourRunOne = paragraphFour.createRun();
            paragraphFourRunOne.setBold(true);
            paragraphFourRunOne.setUnderline(UnderlinePatterns.SINGLE);
            paragraphFourRunOne.setFontSize(10);
            paragraphFourRunOne.setFontFamily("Verdana");
            paragraphFourRunOne.setColor("000070");
            paragraphFourRunOne.setText("ABCD");
            /*up to this the code is working fine,and it is preparing the doc as expected,but after adding the rest of the part, that is adding the image,the doc got corrupted*/
            InputStream pic=null;
            String imgName = "/home/svj/Serg/Photo/snow_queen.jpg";
            try {
                pic = new FileInputStream(imgName);
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            XWPFParagraph paragraphFive = document.createParagraph();
            paragraphFive.setAlignment(ParagraphAlignment.CENTER);

            // Перейти на след страницу
            //paragraphFive.setPageBreak ( true);

            XWPFRun paragraphFiveRunOne = paragraphFive.createRun();

            paragraphFiveRunOne.addCarriageReturn ();                 //separate previous text from break
            paragraphFiveRunOne.addBreak(BreakType.PAGE);
            //paragraphFiveRunOne.addBreak(BreakType.WORD_WRAPPING);   //cancels effect of page break

            try {
                paragraphFiveRunOne.addPicture(pic, XWPFDocument.PICTURE_TYPE_JPEG, imgName, Units.toEMU(200), Units.toEMU(200));
            } catch (InvalidFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            FileOutputStream outStream = null;
            try {
                String fileName = "/home/svj/tmp/testDocImg.doc";
                outStream = new FileOutputStream( fileName );
            } catch (FileNotFoundException e) {
                System.out.println("First Catch");
                e.printStackTrace();
            }
            try {
                document.write(outStream);
                outStream.close();
            } catch (FileNotFoundException e) {
                System.out.println("Second Catch");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Third Catch");
                e.printStackTrace();
            }
        }
}