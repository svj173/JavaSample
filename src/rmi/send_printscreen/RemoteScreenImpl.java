package rmi.send_printscreen;

import java.rmi.*;
import java.rmi.server.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Класс-сервер, реализующий этот интерфейс:
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.07.2010 11:30:00
 */
public class RemoteScreenImpl extends UnicastRemoteObject
                                                     implements RemoteScreen {
    private ImageIcon icon;
    private GraphicsEnvironment environment;
    private GraphicsDevice screen;
    private Robot robot;
    private BufferedImage image;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /** Конструктор */
    public RemoteScreenImpl() throws RemoteException, AWTException {
        environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screen = environment.getDefaultScreenDevice();
        robot = new Robot(screen);
    }

    public ImageIcon getIcon() throws RemoteException {
        image = robot.createScreenCapture(new Rectangle(0, 0,
                                         screenSize.width, screenSize.height));
        icon = new ImageIcon(image);
        return icon;
    }
}
