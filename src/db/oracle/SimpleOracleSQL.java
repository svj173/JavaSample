package db.oracle;


import db.SQLUtil;
import tools.DBTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Простой пример. Коннект к БД и выполнение простого запроса.
 * <BR/>
 * SQLState: 60000
 Message:  ORA-00604: error occurred at recursive SQL level 1
 ORA-12705: Cannot access NLS data files or invalid environment specified

 Vendor:   604

Причины ошибки
 1) An attempt was made to issue an ALTER SESSION statement with an invalid NLS parameter or value.
 2) The NLS_LANG environment variable contains an invalid language, territory, or character set.
 *
 *
 * java : -Duser.country=us -Duser.language=en     -- помогло - заработало
 *
 * <BR/> User: svj
 * <BR/> Date: 07.12.2013 16:12
 */
public class SimpleOracleSQL
{
    public static void main ( String[] args ) throws ClassNotFoundException
    {
        String          dbDriver, dbUrl, userName, password, sql;
        SimpleOracleSQL manager;

        //Locale.setDefault ( Locale.getDefault() );

        //System.setProperty ( "NLS_LANG", "ru_RU.UTF-8" );
        System.setProperty ( "user.region", "us" );      // todo to java
        System.setProperty ( "user.language", "en" );

        System.setProperty ( "LC_ALL", "ru_RU.UTF-8" );
        System.setProperty ( "LANG", "ru_RU.UTF-8" );
        //System.setProperty ( "NLS_LANG", "AMERICAN_AMERICA.AL32UTF8" );
        System.setProperty ( "NLS_LANG", "RUSSIAN_RUSSIA.ALL32UTF8" );

        dbDriver    = "oracle.jdbc.driver.OracleDriver";
        dbUrl       = "jdbc:oracle:thin:@192.168.26.217:1521:XE";
        //dbUrl       = "jdbc:oracle:thin:@192.168.26.217:1521:X1";           // ORA-12505, TNS:listener does not currently know of SID given in connect descriptor
        //dbUrl       = "jdbc:oracle:thin:@192.168.26.217:1521:PLSExtProc"; // java.sql.SQLRecoverableException: Данные для считывания из сокета отсутствуют
        //dbUrl       = "jdbc:oracle:thin:@192.168.26.217:1521:XE:SYSTEM";  // err format
        userName    = "system";
        password    = "root";

        //sql         = "SELECT * from hostsbean;";
        //sql         = "SELECT * from datamodelbean;";
        sql         = "SELECT * from datamodelbean";

        manager = new SimpleOracleSQL ();
        manager.processSql ( dbDriver, dbUrl, userName, password, sql );

    }

    public void processSql ( String dbDriver, String dbUrl, String userName, String password, String sql )
    {
        Statement   stat    = null;
        Connection  conn    = null;
        ResultSet   rs      = null;

        System.out.println ( "------------------------ Start --------------------------- " );

        try
        {
            // Load the Oracle Driver
            Class.forName ( dbDriver );

            // Get a connection from the connection factory
            conn    = DriverManager.getConnection ( dbUrl, userName, password );
            System.out.println ( "---- conn = " + conn );

            // Show some database/driver metadata
            SQLUtil.printDriverInfo ( conn );

            stat    = conn.createStatement();

            System.out.println ( "---- sql = " + sql );
            rs      = stat.executeQuery ( sql );
            System.out.println ( "---- getFetchSize = " + rs.getFetchSize() );  // 10
            System.out.println ( "---- getRow = " + rs.getRow () );
            if ( rs.next() )
            {
                System.out.println ( "------ rs = " + rs );
            } 

        //} catch ( SQLException e )        {
        //    SQLUtil.printSQLExceptions ( e );
        } catch ( Exception e )        {
            e.printStackTrace();
        } finally         {
            DBTools.closeSql ( stat, rs );
            DBTools.closeConn ( conn );
        }
    }
}
