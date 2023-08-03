package tools;


import db.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 07.12.2013 16:23
 */
public class DBTools
{
    public static void closeConn ( Connection conn )
    {
        try
        {
            // Close the connection
            if ( conn != null )            conn.close();
        }  catch ( SQLException e2 )            {
            SQLUtil.printSQLExceptions ( e2 );
        }  catch ( Exception e2 )            {
            e2.printStackTrace();
        }
    }

    public static void rollbackConn ( Connection conn )
    {
        if ( conn != null )
        {
            try
            {
                conn.rollback();
                System.out.println ( "Connection rollback..." );
            } catch ( SQLException ex )   {
                SQLUtil.printSQLExceptions ( ex );
            } catch ( Exception ex )   {
                ex.printStackTrace();
            }
        }
    }

    public static void closeSql ( Statement stmt, ResultSet rs )
    {
        if ( rs != null )
        {
            try
            {
                rs.close();
            } catch ( SQLException ex )   {
                SQLUtil.printSQLExceptions ( ex );
            } catch ( Exception ex )   {
                ex.printStackTrace ();
            }
        }

        if ( stmt != null )
        {
            try
            {
                stmt.close();
            } catch ( SQLException ex )   {
                SQLUtil.printSQLExceptions ( ex );
            } catch ( Exception ex )   {
                ex.printStackTrace ();
            }
        }
    }

}
