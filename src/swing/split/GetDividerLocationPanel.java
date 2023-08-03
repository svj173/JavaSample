package swing.split;


/**
 * Листенер реагирует на любые изменения разделительной линии - руками, либо по кнопкам-стрелкам.
 * <BR/> При расширении размеров окна - генерится много событий.
 * <BR/>
 * <BR/> ONE_TOUCH_EXPANDABLE_PROPERTY - по идее, выскакиваем параметр события при складывании-развертывании разделителя кнопками.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.07.12 10:49
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GetDividerLocationPanel
{
    public static void main ( String args[] ) throws Exception
    {
        JFrame frame = new JFrame ( "Property Split" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );
        final JSplitPane splitPane = new JSplitPane ( JSplitPane.VERTICAL_SPLIT );

        splitPane.setTopComponent ( new JLabel ( "www.jexp.ru" ) );
        splitPane.setBottomComponent ( new JLabel ( "www.jexp.ru" ) );

        splitPane.setOneTouchExpandable ( true );

        PropertyChangeListener propertyChangeListener =
                new PropertyChangeListener ()
                {
                    public void propertyChange ( PropertyChangeEvent changeEvent )
                    {
                        //System.out.println ( "\n\nchangeEvent = " + changeEvent );
                        JSplitPane sourceSplitPane = ( JSplitPane ) changeEvent.getSource ();
                        String propertyName = changeEvent.getPropertyName ();
                        System.out.println ( "propertyName = " + propertyName );
                        if ( propertyName.equals (
                                JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY ) )
                        {
                            int current = sourceSplitPane.getDividerLocation ();
                            System.out.println ( "Current: " + current );
                            Integer last = ( Integer ) changeEvent.getNewValue ();
                            System.out.println ( "Last: " + last );
                            Integer priorLast = ( Integer ) changeEvent.getOldValue ();
                            System.out.println ( "Prior last: " + priorLast );
                        }
                    }
                };

        /*
        // Attach listener
        splitPane.addPropertyChangeListener ( propertyChangeListener );

        // отслеживает перемещение фрейма по монитору
        splitPane.addAncestorListener ( new AncestorListener () {

            @Override
            public void ancestorAdded ( AncestorEvent event )
            {
                System.out.println ( "ancestorAdded" );
            }

            @Override
            public void ancestorRemoved ( AncestorEvent event )
            {
                System.out.println ( "ancestorRemoved" );
            }

            @Override
            public void ancestorMoved ( AncestorEvent event )
            {
                System.out.println ( "ancestorMoved" );
            }
        });

        // ???
        splitPane.addVetoableChangeListener ( new VetoableChangeListener ()
        {
            @Override
            public void vetoableChange ( PropertyChangeEvent evt ) throws PropertyVetoException
            {
                System.out.println ( "--- vetoableChange" );
            }
        } );

        // ничего не происходит
        splitPane.addPropertyChangeListener ( JSplitPane.ONE_TOUCH_EXPANDABLE_PROPERTY, new PropertyChangeListener ()
        {
            @Override
            public void propertyChange ( PropertyChangeEvent evt )
            {
                System.out.println ( "--- propertyChange" );
            }
        } );
        */

        // Вызывается толкьо раз на каждое изменение разделителя (даже при изменении экрана)
        ( ( ( BasicSplitPaneUI ) splitPane.getUI () ).getDivider() ).addComponentListener ( new ComponentAdapter ()    // привязываемся к конкретной реализации. При смене декоратора - это пропадет.
        //splitPane.addComponentListener ( new ComponentAdapter ()     // реагирует только на изменение размера. Разделитель - не реагирует.
        {
            /**
             * Invoked when the component's position changes.
             */
            public void componentMoved ( ComponentEvent ce )
            {
                // source - MetalSplitPaneDivider
                System.out.println ( "--- componentMoved. event = " + ce );
                Component comp = splitPane.getBottomComponent ();
                if ( comp.getHeight () == 0 )
                {
                    // show top component help file
                    System.out.println ( "--- TOP" );
                }
                else
                {
                    //show bottom component help file
                    System.out.println ( "--- BOTTOM" );
                }
            }

            /**
             * Invoked when the component's size changes.
             */
            public void componentResized(ComponentEvent ce)
            {
                System.out.println ( "--- componentResized. event = " + ce );

            }


            /**
             * Invoked when the component has been made visible.
             */
            public void componentShown(ComponentEvent ce)
            {
                System.out.println ( "--- componentShown. event = " + ce );

            }

            /**
             * Invoked when the component has been made invisible.
             */
            public void componentHidden(ComponentEvent ce)
            {
                System.out.println ( "--- componentHidden. event = " + ce );

            }

        } );

        frame.add ( splitPane, BorderLayout.CENTER );
        frame.setSize ( 300, 150 );
        frame.setVisible ( true );
    }

}
