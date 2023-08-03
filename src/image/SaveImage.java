package image;


import java.io.*;
import java.net.URL;
import java.util.TreeSet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * http://www3.msiu.ru/~roganov/2sem/tutorial/2d/images/examples/SaveImage.java
 *
 * Еще пример, как рисовать в панели а потом сохранить это в файле.
 * Здесь получается что рисуешь однвоременно и в панель (и показывается интерактивно) и в BufferedImage
 *  (который показывает наверное окончательный вариант, инчае использвоали бы только его одного)
 * Загружает картинку, фильтрует на ней изображение (по выпадашке).
 * По выпадашке типов изображений сохраняет в файле.
 *
            BufferedImage image =(BufferedImage)
 	        panel.createImage(panel.getWidth(), panel.getHeight());
 	        Graphics2D g2 = image.createGraphics();
 	        panel.paint(g2);
 	        g2.dispose();
 	        try {
	            ImageIO.write(image, "jpeg", new File("MyImg.jpeg"));
	        }
	        catch(IOException io) { io.printStackTrace(); }

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.11.2010 17:39:55
 */
public class SaveImage extends Component implements ActionListener {

    // фильтры, налагаемые на загруженное изображение.
    String descs[] = {
        "Original",
        "Convolve : LowPass",
        "Convolve : Sharpen",
        "LookupOp",
    };

    int opIndex;
    private BufferedImage bi, biFiltered;
    int w, h;

    public static final float[] SHARPEN3x3 = { // sharpening filter kernel
        0.f, -1.f,  0.f,
       -1.f,  5.f, -1.f,
        0.f, -1.f,  0.f
    };

    public static final float[] BLUR3x3 = {
        0.1f, 0.1f, 0.1f,    // low-pass filter kernel
        0.1f, 0.2f, 0.1f,
        0.1f, 0.1f, 0.1f
    };

    public SaveImage() {
        try {
            //bi = ImageIO.read(new File("rocks_waves.jpg"));
            bi = ImageIO.read(getClass().getResource("rocks_waves.jpg") );
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 =
                    new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                biFiltered = bi = bi2;
            }
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }

    protected static Image getImage( URL imageURL) {
        Image image = null;

        try {
            // use ImageIO to read in the image
            image = ImageIO.read(imageURL);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

        return image;
    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }

    String[] getDescriptions() {
        return descs;
    }

    void setOpIndex(int i) {
        opIndex = i;
    }

    public void paint(Graphics g) {
        filterImage();
        g.drawImage(biFiltered, 0, 0, null);
    }

    int lastOp;
    public void filterImage() {
        BufferedImageOp op = null;

        if (opIndex == lastOp) {
            return;
        }
        lastOp = opIndex;
        switch (opIndex) {

        case 0: biFiltered = bi; /* original */
                return;
        case 1:  /* low pass filter */
        case 2:  /* sharpen */
            float[] data = (opIndex == 1) ? BLUR3x3 : SHARPEN3x3;
            op = new ConvolveOp(new Kernel(3, 3, data),
                                ConvolveOp.EDGE_NO_OP,
                                null);

            break;

        case 3 : /* lookup */
            byte lut[] = new byte[256];
            for (int j=0; j<256; j++) {
                lut[j] = (byte)(256-j);
            }
            ByteLookupTable blut = new ByteLookupTable(0, lut);
            op = new LookupOp(blut, null);
            break;
        }

        /* Rather than directly drawing the filtered image to the
         * destination, filter it into a new image first, then that
         * filtered image is ready for writing out or painting.
         */
        biFiltered = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        op.filter(bi, biFiltered);
    }

    /* Return the formats sorted alphabetically and in lower case */
    public String[] getFormats() {
        String[] formats = ImageIO.getWriterFormatNames();
        TreeSet<String> formatSet = new TreeSet<String>();
        for (String s : formats) {
            formatSet.add(s.toLowerCase());
        }
        return formatSet.toArray(new String[0]);
    }


     public void actionPerformed(ActionEvent e) {
         // cb - источник акции - какой-то из двух комбобоксов
         JComboBox cb = (JComboBox)e.getSource();
         if (cb.getActionCommand().equals("SetFilter")) {
             // на панели выбран тип прилагаемого к изображению фильтра - обработать
             setOpIndex(cb.getSelectedIndex());
             repaint();
         } else if (cb.getActionCommand().equals("Formats")) {
             // На панели выбран формат сохраняемого файла - перейти в режим сохранения (файловый диалог)
             /* Save the filtered image in the selected format.
              * The selected item will be the name of the format to use
              */
             String format = (String)cb.getSelectedItem();
             /* Use the format name to initialise the file suffix.
              * Format names typically correspond to suffixes
              */
             File saveFile = new File("savedimage."+format);
             JFileChooser chooser = new JFileChooser();
             chooser.setSelectedFile(saveFile);
             int rval = chooser.showSaveDialog(cb);
             if (rval == JFileChooser.APPROVE_OPTION) {
                 saveFile = chooser.getSelectedFile();
                 /* Write the filtered image in the selected format,
                  * to the file chosen by the user.
                  */
                 try {
                     ImageIO.write(biFiltered, format, saveFile);
                 } catch (IOException ex) {
                 }
             }
         }
    };

    public static void main(String s[]) {
        JFrame f = new JFrame("Save Image Sample");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        SaveImage si = new SaveImage();
        f.add("Center", si);
        JComboBox choices = new JComboBox(si.getDescriptions());
        choices.setActionCommand("SetFilter");
        choices.addActionListener(si);
        JComboBox formats = new JComboBox(si.getFormats());
        formats.setActionCommand("Formats");
        formats.addActionListener(si);
        JPanel panel = new JPanel();
        panel.add(choices);
        panel.add(new JLabel("Save As"));
        panel.add(formats);
        f.add("South", panel);
        f.pack();
        f.setVisible(true);
    }
}
