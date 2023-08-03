package tools;


import java.awt.*;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 02.12.2011 10:03:32
 */
public class GuiTools
{
    /**
     * Метод помещает указанный диалог в центр экрана
     * @param dlg диалог для размещения по центру
     */
    /*
    public static void setDialogScreenCenterPosition ( JDialog dlg )
    {
        if (dlg == null)             return;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        dlg.setLocation(screenSize.width / 2 - (dlg.getSize().width / 2),
                screenSize.height / 2 - (dlg.getSize().height / 2));
    }
    */

    /**
     * Метод помещает указанный диалог в центр экрана
     * @param dlg диалог для размещения по центру
     */
    public static void setDialogScreenCenterPosition ( Window dlg )
    {
        if (dlg == null)             return;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        dlg.setLocation(screenSize.width / 2 - (dlg.getSize().width / 2),
                screenSize.height / 2 - (dlg.getSize().height / 2));
    }

}
