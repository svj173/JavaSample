package db;


import java.sql.*;

/**
 * Пример использования нескольких разных SQL запросов в одном коммите.
 * <BR/>
 * <BR/> The above code sample will produce the following result.The result may vary.
 * <BR/>
 * <BR/> No. of rows before commit statement = 1
 * <BR/> No. of rows after commit statement = 3
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.02.2012 11:27:42
 */
public class MultiCommit
{
    public static void main ( String[] args ) throws Exception
    {
        Connection  con;
        Statement   stmt;
        String      query, query1, query2;
        ResultSet   rs;
        int         no_of_rows;

        Class.forName ( "org.apache.derby.jdbc.ClientDriver" );
        con         = DriverManager.getConnection ( "jdbc:derby://localhost:1527/testDb", "name", "pass" );

        stmt        = con.createStatement();

        query       = "insert into emp values(2,'name1','job')";
        query1      = "insert into emp values(5,'name2','job')";
        query2      = "select * from emp";

        rs          = stmt.executeQuery ( query2 );
        no_of_rows  = 0;
        while ( rs.next() )
        {
            no_of_rows++;
        }

        System.out.println ( "No. of rows before commit statement = " + no_of_rows );

        con.setAutoCommit ( false );
        stmt.execute ( query1 );
        stmt.execute ( query );
        con.commit();

        rs          = stmt.executeQuery ( query2 );
        no_of_rows  = 0;
        while ( rs.next() )
        {
            no_of_rows++;
        }

        System.out.println ( "No. of rows after commit statement = " + no_of_rows );
    }

}
