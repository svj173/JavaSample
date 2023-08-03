package db.hibernate.oracleEms;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.07.2014 14:07
 */
public class HibernateUtil
{
    private static final SessionFactory sessionFactoryAcsMain = configureSessionFactoryAcsMain ();
    //private static final SessionFactory sessionFactoryEmsMain = configureSessionFactoryEmsMain ();
    //private static final SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;


    /*
    <property name="connection.driver_class">org.gjt.mm.mysql.Driver</property>
    <property name="connection.url">jdbc:mysql://localhost/eltex_ems</property>
    <property name="connection.username">javauser</property>
    <property name="connection.password">javapassword</property>
    <property name="connection.pool_size">10</property>
    <property name="dialect">org.hibernate.dialect.MySQLInnoDBDialect</property>
    <property name="show_sql">true</property>
    <property name="hbm2ddl.auto">update</property>
    <property name="hibernate.connection.autocommit">true</property>
    <property name="current_session_context_class">thread</property>

    <!--mapping class="db.hibernate.oracleEms.TreeObject" /-->
    <mapping resource="TreeObject.hbm.xml" /  
     */
    /**
     * Создание фабрики
     * @return {@link SessionFactory}
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactoryAcsMain()
            throws HibernateException
    {
            // Настройки hibernate
            Configuration configuration = new Configuration ()
                   .setProperty ( "hibernate.connection.driver_class", "org.gjt.mm.mysql.Driver" )
                   //.setProperty ( "hibernate.connection.url", "jdbc:mysql://192.168.16.166/acsmain?useUnicode=true&characterEncoding=utf8&relaxAutoCommit=true" )
                   .setProperty ( "hibernate.connection.url", "jdbc:mysql://192.168.16.166/acsmain" )
                   .setProperty ( "hibernate.connection.username", "javauser" )
                   .setProperty ( "hibernate.connection.password","javapassword" )
                   .setProperty ( "hibernate.connection.pool_size", "10" )
                   .setProperty ( "hibernate.connection.autocommit", "false" )
                   .setProperty ( "hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider" )
                   .setProperty ( "hibernate.cache.use_second_level_cache", "false" )
                   .setProperty ( "hibernate.cache.use_query_cache", "false" )
                   .setProperty ( "hibernate.dialect", "org.hibernate.dialect.MySQLDialect" )
                   .setProperty ( "hibernate.show_sql", "false" )          // выводить в лог (консоль) строку сгенерированного sql запроса.
                   .setProperty ( "hibernate.current_session_context_class", "thread" )
                   .setProperty( "hibernate.connection.zeroDateTimeBehavior", "convertToNull" )
                   //.addPackage ( "ru.miralab.db" )
                   .addAnnotatedClass ( db.hibernate.oracleEms.CpeObject.class )
                   .addAnnotatedClass ( db.hibernate.oracleEms.CpeInfoObject.class )
                   .addAnnotatedClass ( db.hibernate.oracleEms.HwModelObject.class )
                   ;

            //serviceRegistry = new ServiceRegistryBuilder ().applySettings( configuration.getProperties()).buildServiceRegistry();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build ();

            return configuration.buildSessionFactory(serviceRegistry);
    }

    // from xml
    private static SessionFactory configureSessionFactoryEmsMain () throws HibernateException
    {
        Configuration configuration = new Configuration();

        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build ();

        return configuration.buildSessionFactory ( serviceRegistry );
    }

    protected static void close ()
    {
            try
            {
                sessionFactoryAcsMain.close();
            } catch ( HibernateException e )         {
                e.printStackTrace ();
            }
        /*
            try
            {
                sessionFactoryEmsMain.close();
            } catch ( HibernateException e )         {
                e.printStackTrace ();
            }
        */
   	}

    /**
         * Создание фабрики
         * @return {@link SessionFactory}
         * @throws HibernateException
         */
    /*
    private static SessionFactory configureSessionFactory() throws HibernateException
    {
        Configuration configuration = new Configuration().configure();
        // StandardServiceRegistryBuilder
        serviceRegistry = new ServiceRegistryBuilder ().applySettings( configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }
    */
    /**
         * Получить фабрику сессий
         * @return {@link SessionFactory}
         */
    /*
    public static SessionFactory getSessionFactoryEmsMain()
    {
        return sessionFactoryEmsMain;
    }
    */

    public static SessionFactory getSessionFactoryAcsMain()
    {
        return sessionFactoryAcsMain;
    }

}
