package swing.combobox;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Применяем акцию на выпадашке.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.09.2015 13:03
 */
public class ComboBoxActionDemo
{
    public static void main ( final String args[] )
    {
        final String labels[] = { "A", "B", "C", "D", "E" };
        JFrame frame = new JFrame ( "Selecting JComboBox" );
        frame.setDefaultCloseOperation ( JFrame.EXIT_ON_CLOSE );

        final JComboBox comboBox;

        comboBox = new JComboBox ( labels );
        comboBox.setEditable(false);
        comboBox.setEnabled(true);
		comboBox.setFont ( new Font ( "SANS_SERIF ", Font.BOLD, 10 ) );

        frame.add ( comboBox, BorderLayout.SOUTH );

        ActionListener actionListener = new ActionListener ()
        {
            public void actionPerformed ( ActionEvent actionEvent )
            {
                String value;
                System.out.println ( "Command: " + actionEvent.getActionCommand () );
                ItemSelectable is = ( ItemSelectable ) actionEvent.getSource ();
                value = selectedString ( is );
                System.out.println ( ", Selected: " + value );
                if ( value.equals ( "D" ) )
                {
                    // переиницаилизируем список
                    comboBox.removeAllItems();
                    comboBox.addItem ( "D" );
                    comboBox.addItem ( "F" );
                    comboBox.addItem ( "G" );
                    comboBox.addItem ( "H" );
                }
                if ( value.equals ( "G" ) )
                {
                    // переиницаилизируем список
                    comboBox.setSelectedItem ( "H" );
                }
            }
        };
        comboBox.addActionListener ( actionListener );
        frame.setSize ( 400, 200 );
        frame.setVisible ( true );

    }

    static private String selectedString ( ItemSelectable is )
    {
        Object selected[] = is.getSelectedObjects ();
        return ( ( selected.length == 0 ) ? "null" : ( String ) selected[ 0 ] );
    }

}
