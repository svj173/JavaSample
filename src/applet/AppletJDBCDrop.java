package applet;


/**
 * Обращение из палета прямо к MySQL.
 * <BR/>

 <html>
 <applet code="Drop.class" width=200 height=200>
 </applet>
 </html>

 * <BR/> User: svj
 * <BR/> Date: 14.11.2014 14:24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class AppletJDBCDrop extends JApplet implements ActionListener
{
    private Connection connection;

    private JList tableList;

    private JButton dropButton;

    public void init ()
    {
        Connection connection;
        try
        {
            Class.forName ( "com.mysql.jdbc.Driver" ).newInstance();
            connection = DriverManager.getConnection ( "jdbc:mysql://192.168.1.25/accounts?user=spider&password=spider" );
        } catch ( Exception connectException )   {
            connectException.printStackTrace ();
        }

        Container c = getContentPane ();
        tableList = new JList ();
        loadTables ();
        c.add ( new JScrollPane ( tableList ), BorderLayout.NORTH );

        dropButton = new JButton ( "Drop Table" );
        dropButton.addActionListener ( this );
        c.add ( dropButton, BorderLayout.SOUTH );
    }

    public void actionPerformed ( ActionEvent e )
    {
        try
        {
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery ( "DROP TABLE "
                                                            + tableList.getSelectedValue () );
        } catch ( SQLException actionException )
        {
        }
    }

    private void loadTables ()
    {
        Vector v = new Vector ();
        try
        {
            Statement statement = connection.createStatement ();
            ResultSet rs = statement.executeQuery ( "SHOW TABLES" );

            while ( rs.next () )
            {
                v.addElement ( rs.getString ( 1 ) );
            }
            rs.close ();
        } catch ( SQLException e )         {
        }
        v.addElement ( "acc_acc" );
        v.addElement ( "acc_add" );
        v.addElement ( "junk" );
        tableList.setListData ( v );
    }
}

