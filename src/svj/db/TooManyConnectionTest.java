package svj.db;


import db.SQLUtil;
import tools.DBTools;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Попытка отловить переполнение пула коннектов в БД и очистить старые коннекты.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.07.2016 11:29
 */
public class TooManyConnectionTest
{
    private static final String DB_DRIVER       = "org.gjt.mm.mysql.Driver";
    private static final String DB_CONNECTION   = "jdbc:mysql://localhost/eltex_alert?useUnicode=true&characterEncoding=utf8&relaxAutoCommit=true&connectTimeout=5000";
    private static final String DB_USER         = "javauser";
    private static final String DB_PASSWORD     = "javapassword";

    public static void main ( String[] argv ) throws SQLException
    {
        Statement stmt;
        ResultSet rs;
        Connection dbConnection, conn;
        String str;
        int maxConn, ic;
        Collection<Connection> pool;

        stmt = null;
        rs = null;
        str = null;
        maxConn = -1;
        dbConnection = null;
        pool = new ArrayList<Connection> ( 200 );

        try
        {
            Class.forName ( DB_DRIVER );

            dbConnection = getDBConnection ();

            // Получить инфу о макс коннектах
            SQLUtil.printDriverInfo ( dbConnection );

            // Время его простоя
            System.out.println ( "ClientInfo = "+dbConnection.getClientInfo() );  // пусто

            stmt        = dbConnection.createStatement ();

            rs          = stmt.executeQuery ( "SELECT @@max_connections;" );

            //System.out.println ( "RS\n"+SQLUtil.printResultSet(rs) );
            //SQLUtil.printResultSet ( rs );


            if ( rs.next() )
            {
                str = rs.getString ( 1 );
                maxConn = Integer.parseInt ( str );
            }
            System.out.println ( "Max =  "+ maxConn );

            ic = maxConn + 5;
            for ( int i=0; i<ic; i++ )
            {
                conn = getDBConnection ();
                pool.add ( conn );
            }


            System.out.println ( "Done!" );

        } catch ( SQLException se )  {
            System.out.println ( "SQL error = " + se );
            se.printStackTrace ();
            // com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Too many connections
            //SQLState: 08004
            //Message:  Too many connections
            //Vendor:   1040

            SQLUtil.printSQLExceptions ( se );
        } catch ( Exception e )  {
            //System.out.println ( se.getMessage () );
            e.printStackTrace ();
        } finally        {
            DBTools.closeSql ( stmt, rs );
            DBTools.closeConn ( dbConnection );
            for ( Connection c : pool )
                DBTools.closeConn ( c );
        }
    }

    private static Connection getDBConnection ()  throws Exception
    {
        /*
        Connection dbConnection = null;

        try
        {
            Class.forName ( DB_DRIVER );
        } catch ( ClassNotFoundException e )         {
            System.out.println ( e.getMessage () );
        }

        try
        {
            dbConnection = DriverManager.getConnection ( DB_CONNECTION, DB_USER, DB_PASSWORD );
            return dbConnection;
        } catch ( SQLException e )       {
            System.out.println ( e.getMessage () );
            e.printStackTrace ();
        }

        return dbConnection;
        */
        return DriverManager.getConnection ( DB_CONNECTION, DB_USER, DB_PASSWORD );
    }


}
