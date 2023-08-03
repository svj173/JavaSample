package swing.drag_drop;

/**
 * <BR> User: Zhiganov
 * <BR> Date: 17.10.2007
 * <BR> Time: 17:22:35
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class DragAndDrop extends Frame {
    JButton button = new JButton("Hello");
    JPanel panel = new JPanel();

    public DragAndDrop() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        this.add(panel);
        panel.add(button);
        setDrag();
    }

    public static void main(String args[]) {
        System.out.println("Starting Drag And Drop Example ...");
        DragAndDrop mainFrame = new DragAndDrop();
        mainFrame.setSize(400, 400);
        mainFrame.setLocation(100, 100);
        mainFrame.setTitle("Drag And Drop Example");
        mainFrame.setVisible(true);
    }

    void setDrag() {
        // Внутренние классы для поддержки Drag and Drop
        class ViewTransferable implements Transferable {
            DataFlavor[] flavors = new DataFlavor[]{DataFlavor.stringFlavor};

            public ViewTransferable() {
            }

            public DataFlavor[] getTransferDataFlavors() {
                return flavors;
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {
                if (flavor.equals(flavors[0])) {
                    return true;
                }
                return false;
            }

            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) {
                    System.out.println("unsuported flavor");
                    return null;
                }
                if (flavor.equals(flavors[0])) {
                    return (null);
                }
                return null;
            }
        }
        class CanvasDragSource implements DragGestureListener, DragSourceListener {
            CanvasDragSource() {
                DragSource dragSource = DragSource.getDefaultDragSource();
                dragSource.createDefaultDragGestureRecognizer(button, DnDConstants.ACTION_COPY_OR_MOVE, this);
            }

            public void dragGestureRecognized(DragGestureEvent dge) {
                Point p = dge.getDragOrigin();
                int x = (int) p.getX();
                int y = (int) p.getY();
                Transferable transferable = new ViewTransferable();
                dge.startDrag(null, transferable, this);
            }

            public void dropActionChanged(DragSourceDragEvent dsde) {
            }

            public void dragEnter(DragSourceDragEvent dsde) {
            }

            public void dragOver(DragSourceDragEvent dsde) {
            }

            public void dragExit(DragSourceEvent dse) {
            }

            public void dragDropEnd(DragSourceDropEvent dsde) {
            }
        }
        class CanvasDropTarget implements DropTargetListener {
            DropTarget dropTarget;
            boolean acceptableType;

            CanvasDropTarget() {
                dropTarget = new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this, true, null);
            }

            public void dragOver(DropTargetDragEvent dtde) {
                acceptOrRejectDrag(dtde);
            }

            public void dropActionChanged(DropTargetDragEvent dtde) {
                acceptOrRejectDrag(dtde);
            }

            public void dragExit(DropTargetEvent dte) {
            }

            public void drop(DropTargetDropEvent dtde) {
                if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
                    dtde.acceptDrop(dtde.getDropAction());
                    dtde.getDropTargetContext().dropComplete(true);
                    double x = dtde.getLocation().getX();
                    double y = dtde.getLocation().getY();
                    button.setLocation((int) x, (int) y);
                } else {
                    dtde.rejectDrop();
                }
            }

            public void dragEnter(DropTargetDragEvent dtde) {
                checkTransferType(dtde);
                acceptOrRejectDrag(dtde);
            }

            boolean acceptOrRejectDrag(DropTargetDragEvent dtde) {
                int dropAction = dtde.getDropAction();
                int sourceActions = dtde.getSourceActions();
                boolean acceptedDrag = false;
                if (!acceptableType || (sourceActions & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
                    dtde.acceptDrag(DnDConstants.ACTION_COPY);
                    acceptedDrag = true;
                } else if ((dropAction & DnDConstants.ACTION_COPY_OR_MOVE) == 0) {
                    dtde.acceptDrag(DnDConstants.ACTION_COPY);
                    acceptedDrag = true;
                }
                return acceptedDrag;
            }

            void checkTransferType(DropTargetDragEvent dtde) {
                acceptableType = dtde.isDataFlavorSupported(DataFlavor.stringFlavor);
            }
        }
        new CanvasDragSource();
        new CanvasDropTarget();
    }
}
