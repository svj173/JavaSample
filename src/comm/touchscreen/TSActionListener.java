package comm.touchscreen;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.util.Date;

import org.apache.logging.log4j.*;

import javax.swing.*;

/**
 * <BR> Слушатель на нажатие кнопок мышью. Вешается на кнокпи, и потом вызывается нашей программой.
 * после анализа нажатия клиентом.
 *
 * <BR> User: svj
 * <BR> Date: 01.03.2006
 * <BR> Time: 17:07:10
 */
public class TSActionListener implements ActionListener
{
    private static Logger logger = LogManager.getFormatterLogger ( TSActionListener.class );

    private Component component;


    public TSActionListener ( Component component )
    {
        this.component = component;
    }


    public void actionPerformed ( ActionEvent e )
    {
        String command;
        int     ic;
        boolean rezEvent;

        logger.debug ( "ActionEvent " + e );
        command = e.getActionCommand ();

        logger.debug ( "Press " + command + " button." );
        //ic  = Integer.parseInt ( command );
        //switch ( )

        try {
            if ( command.equals ( "45" ) ) {
                throw new Exception ( "45" );
            }
        } catch ( Exception e1 ) {
            e1.printStackTrace ();
        }


        try
        {
            if ( command.equals ( "55" ) )
            {
                int x, y, clicks;
                boolean popupMenu;
                long    when    = new Date ().getTime ();
                int type    = MouseEvent.MOUSE_CLICKED;
                // Указывает, какие модификаторы были нажаты когда произошло событие
                int modifiers   = 1; // ?
                x   = 203;
                y   = 507;
                // Количество щелчков мыши
                clicks  = 1;
                // TRUE - приводит ли это событие к появлению раскрыващегося меню или нет
                popupMenu    = false;
                MouseEvent  me  = new MouseEvent ( component, type, when, modifiers, x, y, clicks, popupMenu );
                logger.debug ( "Create event = " + me );
                // java.awt.event.MouseEvent[MOUSE_CLICKED,(203,507),button=0,modifiers=Shift,extModifiers=Shift,clickCount=1] on frame0
                //component.processMouseEvent ( me );
                component.dispatchEvent ( me );
                logger.debug ( "Finish mouse event." );

                //
                logger.debug ( "Start EQ event." );
                EventQueue  eq  = new EventQueue ();
                eq.postEvent ( me );
                logger.debug ( "Finish EQ event." );

                //*
                // Event(Object target, long when, int id, int x, int y, int key, int modifiers)
                logger.debug ( "Start AWTEvent event." );
                type    = Event.ACTION_EVENT;
                Event   event   = new Event ( component, when, type, x, y, Event.F1, modifiers );
                AWTEvent    av  = new AWTEvent(event)
                {
                    void dispatched ()
                    {
                        logger.debug ( "dispatcher" );
                    }
                };
                component.dispatchEvent ( av );
                logger.debug ( "Finish AWTEvent event." );
                //*/

                //
                Component c, c2, c3, c4;
                c = component.getComponentAt ( x, y );
                //c = component.getContentPane
                logger.debug ( " Component 1 = " + c );

                JRootPane   jp  = ((JFrame) component).getRootPane ();
                //c2   = jp.getComponentAt ( x, y );
                //JGlassPane   gp;
                c2   = jp.getContentPane ();
                logger.debug ( " Component 2 = " + c2 );

                c3 = c2.getComponentAt ( x, y );
                logger.debug ( " Component 3 = " + c3 );

                if ( c3 instanceof JButton )
                {
                    JButton button;
                    String  cmd;
                    ActionListener ass[];
                    ActionEvent act;
                    //
                    logger.debug ( " It is JButton" );
                    button  = (JButton) c3;
                    cmd = button.getActionCommand ();
                    logger.debug ( " Cmd = " + cmd );
                    //
                    ass = button.getActionListeners ();
                    logger.debug ( " Ass[0] = " + ass[0] );
                    // ActionEvent(Object source, int id, String command)
                    act = new ActionEvent ( button, ActionEvent.ACTION_PERFORMED, cmd );
                    ass[0].actionPerformed ( act );
                }

            }
        } catch ( Exception e1 ) {
            e1.printStackTrace ();
        }
    }

}
