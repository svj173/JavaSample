package db.hibernate.oracleExample;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.12.2013 15:26
 */
public class HibernateUtil
{
    private static SessionFactory sessionFactory = null;

       static {
           try {
                   //creates the session factory from hibernate.cfg.xml
                   sessionFactory = new Configuration().configure().buildSessionFactory();
           } catch (Exception e) {
                 e.printStackTrace();
           }
       }

       public static SessionFactory getSessionFactory() {
           return sessionFactory;
       }
}
