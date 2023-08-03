package db;


import java.sql.*;

/**
 * Пример сообщений об ошибках SQL.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.02.2012 11:27:42
 */
public class Warning
{
    public static void main ( String[] args ) throws Exception
    {
        Connection  con;
        Statement   stmt;

        con = null;

        try
        {
            Class.forName ( "org.apache.derby.jdbc.ClientDriver" );
            con         = DriverManager.getConnection ( "jdbc:derby://localhost:1527/testDb", "name", "pass" );

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT bar FROM Sells") ;
            
            SQLWarning warn = stmt.getWarnings() ;
            if (warn != null)
               System.out.println("Message: " + warn.getMessage()) ;

            SQLWarning warning = rs.getWarnings() ;

            if (warning != null)
               warning = warning.getNextWarning() ;

            if (warning != null)
               System.out.println("Message: " + warn.getMessage()) ;

        } catch ( ClassNotFoundException e )         {
            System.err.println ("ClassNotFoundException: " + e.getMessage()) ;
        } catch ( SQLException ex )        {
            System.err.println ("SQLException: " + ex.getMessage()) ;
            if ( con != null )
            {
                con.rollback();
                con.setAutoCommit ( true );
            }
        }

        System.out.println ( "No. of rows after commit statement = " );
    }

}