package db;


import java.sql.*;

/**
 * Пример использования нескольких разных SQL запросов в одном коммите.
 * <BR/>
 * <BR/> Здесь исп разные статементы.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.02.2012 11:27:42
 */
public class MultiCommit3
{
    public static void main ( String[] args ) throws Exception
    {
        String url = "jdbc:mysql://localhost/testdb";
        String username = "root";
        String password = "";
        Class.forName ( "com.mysql.jdbc.Driver" );
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection ( url, username, password );
            conn.setAutoCommit ( false );

            Statement st = conn.createStatement ();
            st.execute ( "INSERT INTO orders (username, order_date) VALUES ('java', '2007-12-13')", Statement.RETURN_GENERATED_KEYS );

            ResultSet keys = st.getGeneratedKeys();
            int id = 1;
            while ( keys.next () )
            {
                id = keys.getInt ( 1 );
            }
            
            PreparedStatement pst = conn.prepareStatement ( "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)" );
            pst.setInt ( 1, id );
            pst.setString ( 2, "1" );
            pst.setInt ( 3, 10 );
            pst.setDouble ( 4, 100 );
            pst.execute ();

            conn.commit ();
            System.out.println ( "Transaction commit..." );

        } catch ( SQLException e )
        {
            if ( conn != null )
            {
                conn.rollback ();
                System.out.println ( "Connection rollback..." );
            }
            e.printStackTrace ();
        } finally
        {
            if ( conn != null && !conn.isClosed () )
            {
                conn.close ();
            }
        }
    }

}
