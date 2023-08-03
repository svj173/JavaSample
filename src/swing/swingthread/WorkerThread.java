package swing.swingthread;


import java.awt.*;
import java.io.File;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.12.2010 16:45:16
 */
abstract class WorkerThread extends Thread {

    /*
  protected SwingGuiFrame ui;
  private boolean terminateRequested = false;

  public WorkerThread(SwingGuiFrame ui) {
    this.ui = ui;
  }

  public void terminate() {
    terminateRequested = true;
  }

  public boolean isTerminateRequestd() {
    return terminateRequested;
  }

    class DeleteThread extends WorkerThread {
      private static final String PREFIX =
          "e:/temp/test";

      DeleteThread(SwingGuiFrame ui) {
        super(ui);
      }

      public void run() {
        File a = new File(PREFIX);
        String fileList[] = a.list();
        ui.initDeleteProgress(fileList.length);
        int numFiles = fileList.length;
        for (int i = 0; i < fileList.length; i++) {
          File f = new File (PREFIX + '/' +
              fileList[i]);
          if (f.delete()) {
            System.out.println("Deleted file" +
                fileList[i]);
          }
          EventQueue.invokeLater(new Runnable() {
            public void run() {
              ui.updateDeleteProgress();
            }
          });
          if (isTerminateRequestd()) {
            return;
          }
          yield();
        } //for
        ui.copyThread = new CopyThread(ui);
        ui.copyThread.start();
      }
    }


class CopyThread extends WorkerThread {
  String filesToCopy[];
  private static final String PATH =
      "d:/jdk1.3/demo/jfc/SwingSet2/src/";
  private static final String OUTPUT_PATH =
      "e:/temp/test/";

  CopyThread(SwingGuiFrame ui) {
    super(ui);
    File srcDir = new File(PATH);
    filesToCopy =
        new String[srcDir.list().length];
    filesToCopy = srcDir.list();
    ui.initCopyProgress(filesToCopy.length);
  }

  public void run() {
    int numFiles = filesToCopy.length;
    for (int i = 0; i < numFiles; i++) {
      System.out.println("Copying file:" +
          filesToCopy[i]);
      copyFile(filesToCopy[i]);
      EventQueue.invokeLater(new Runnable() {
        public void run() {
          ui.updateCopyProgress();
        }
      });
      if (isTerminateRequestd()) {
        return;
      }
      yield();
    }
  }

  private void copyFile(String fileToCopy) {
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      File in = new File(PATH + fileToCopy);
      File out = new File(
          OUTPUT_PATH + fileToCopy);
      FileInputStream fis =
          new FileInputStream(in);
      bis = new BufferedInputStream(fis, 256);
      FileOutputStream fos =
          new FileOutputStream(out);
      bos = new BufferedOutputStream(fos);
      while (bis.available() > 0) {
        bos.write(bis.read());
      }
      bos.flush();
    } catch (IOException ioe) {
      System.out.println(ioe.getMessage());
      JOptionPane.showMessageDialog(
          new Frame(), ioe.getMessage(),
          "File Error",
          JOptionPane.ERROR_MESSAGE);
    } finally {
      try {
        bis.close();
        bos.close();
      } catch (IOException ignored) {
      } //catch
    } //finally
  }
*/
}


