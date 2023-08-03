package image;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Как загрузить или сохранить картинку в jpanel?
 *
 * Ответы:
 *
 1) Этот Класс

 2)
Надо рисовать не на панели, а на BufferedImage.

Frame f = ...;

BufferedImage image = new BufferedImage(f.getWidth(),f.getHeight(),BufferedImage.TYPE_INT_RGB);

Graphics g = image.getGraphics();
f.printAll(g);

try {
 ByteArrayOutputStream out = new ByteArrayOutputStream();
 JPEGImageEncoderImpl j = new JPEGImageEncoderImpl(out);
 j.encode(image);
 out.close();
}
catch (Exception e) {
 e.printStackTrace();
}
 
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.11.2010 17:47:34
 */
public class PicturePanel extends javax.swing.JPanel
{
        private static final long serialVersionUID = 1L;
    private BufferedImage originalImage = null;
    private Image image = null;
    public float suSize;

    public PicturePanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

    }

    private void formComponentResized(java.awt.event.ComponentEvent evt) {
        int w = this.getWidth();
        int h = this.getHeight();
        if ((originalImage != null) && (w > 0) && (h > 0)) {
            image = originalImage.getScaledInstance(w, h, Image.SCALE_DEFAULT);
            this.repaint();
        }
    }

    public void paint(Graphics g) {

        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
        super.paintChildren(g);
        super.paintBorder(g);
    }


    public BufferedImage getImage() {
        return originalImage;
    }

    public void setImage(BufferedImage image) {
        this.originalImage = image;
        suSize = (float)(image.getWidth())/(float)(image.getHeight());
        formComponentResized(null);
    }


        public void setImageFile(File iF) {
        if(iF==null)originalImage=null;
        else{
                try {
                        BufferedImage bi;
                        bi = ImageIO.read(iF);
                        originalImage = bi;
            } catch (IOException ex) {
                System.err.println("Неудалось загрузить картинку!");
                ex.printStackTrace();
            }
            formComponentResized(null);
            suSize = (float)(originalImage.getWidth())/(float)(originalImage.getHeight());
            repaint();
        }
    }

}