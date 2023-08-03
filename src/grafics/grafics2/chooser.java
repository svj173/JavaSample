package grafics.grafics2;


    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import javax.swing.filechooser.FileFilter;
    import java.io.File;

    // filter for gif/jpg/jpeg files
    class ImageFileFilter extends FileFilter {
        // return true if should accept a given file
        public boolean accept(File f) {
            if (f.isDirectory())
                return true;
            String path = f.getPath().toLowerCase();
            if (path.endsWith(".gif"))
                return true;
            if (path.endsWith(".jpg"))
                return true;
            if (path.endsWith(".jpeg"))
                return true;
            return false;
        }
        // return a description of files
        public String getDescription() {
            return "Image file (*.gif,*.jpg,*.jpeg)";
        }
    }

    public class chooser {
        public static void main(String args[]) {
            JFrame frame = new JFrame("File Chooser demo");
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            // label to attach image icon to
            final JLabel label =
                new JLabel("", SwingConstants.CENTER);

            JPanel panel1 = new JPanel();
            // start filechooser in current directory
            String cwd = System.getProperty("user.dir");
            final JFileChooser fc = new JFileChooser(cwd);
            fc.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // set label's icon to
                    // the current image
                    String state =
                        (String)e.getActionCommand();
                    if (!state.equals(
                        JFileChooser.APPROVE_SELECTION))
                        return;
                    File f = fc.getSelectedFile();
                    if (f == null || !f.isFile())
                        return;
                    ImageIcon icon = new ImageIcon(f.getPath());
                    label.setIcon(icon);
                }
            });
            // set the file filter for the filechooser
            fc.setFileFilter(new ImageFileFilter());
            panel1.add(fc);

            JPanel panel2 = new JPanel();
            // max size of label whose icon displays image
            label.setPreferredSize(new Dimension(500, 300));
            panel2.add(label);

            frame.getContentPane().add("North", panel1);
            frame.getContentPane().add("South", panel2);
            frame.pack();
            frame.setVisible(true);
        }
    }

