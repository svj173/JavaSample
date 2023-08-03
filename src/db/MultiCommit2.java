package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Пример использования нескольких разных SQL запросов в одном коммите.
 * <BR/>
 * <BR/>  Lets walk through the example to understand the effects of various methods. We first set auto-commit off, i
 * ndicating that the following statements need to be considered as a unit. We attempt to insert into the Sells table the
 * ('Bar Of Foo', 'BudLite', 1.00) tuple. However, this change has not been made final (committed) yet.
 * When we invoke rollback, we cancel our insert and in effect we remove any intention of inserting the above tuple.
 * Note that Sells now is still as it was before we attempted the insert.
 * We then attempt another insert, and this time, we commit the transaction.
 * It is only now that Sells is now permanently affected and has the new tuple in it. Finally, we reset the connection to auto-commit again.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.02.2012 11:27:42
 */
public class MultiCommit2
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

            con.setAutoCommit ( false );

            stmt = con.createStatement();

            stmt.executeUpdate ( "INSERT INTO Sells VALUES('Bar Of Foo', 'BudLite', 1.00)" );
            con.rollback();

            stmt.executeUpdate ( "INSERT INTO Sells VALUES('Bar Of Joe', 'Miller', 2.00)" );

            con.commit();

            con.setAutoCommit ( true );

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
