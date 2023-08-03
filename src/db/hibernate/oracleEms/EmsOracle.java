package db.hibernate.oracleEms;


import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

/**
 * <BR/>
 * <BR/> Пример
 * SELECT DISTINCT a.id, a.hwc_id, a.serial, a.pfid, a.subscriber, a.username, a.password, a.conrequser, a.conreqpass, a.editor, a.comments,  b.oui, b.hwc_id, b.ProductClass, c.url, c.lastcontact, c.currentsoftware, c.cfg_updtime, c.sfw_updtime, c.cfg_updres, c.sfw_updres, c.hardware, c.cfgversion, c.acsd_username, c.acsd_password, c.authresult
 * FROM ( hostsbean a, hardwaremodelbean b, hostinfobean c ) WHERE (c.hst_id = a.id) and (b.id = c.hwid) and (a.hwc_id = 0) ORDER BY a.id LIMIT 500000;
 * <BR/>
 * <BR/> Замечание.
 * <BR/> 1) Оракл не любит
 * <BR/>  - точку с запятой в конце.
 * <BR/>  - скобки в конструкции FROM ( hostsbean a, hardwaremodelbean b, hostinfobean c )
 * <BR/>  - limit
 * <BR/>
 * <BR/>
 * <BR/> Запуск
 * <BR/> 1) IDEA
 * <BR/> - прописать в параметрах VM строку вида "-Duser.country=us -Duser.language=en"
 * <BR/>
 * <BR/>
 * <BR/> ACS запрос
 * <BR/>
 * <BR/> SELECT DISTINCT a.id, a.hwc_id, a.serial, a.pfid, a.subscriber, a.username, a.password, a.conrequser, a.conreqpass, a.editor, a.comments,  b.oui, b.hwc_id, b.ProductClass, c.url, c.lastcontact, c.currentsoftware, c.cfg_updtime, c.sfw_updtime, c.cfg_updres, c.sfw_updres, c.hardware, c.cfgversion, c.acsd_username, c.acsd_password, c.authresult
 FROM  hostsbean a, hardwaremodelbean b, hostinfobean c
 WHERE (c.hst_id = a.id) and (b.id = c.hwid) and (a.hwc_id = 0) ORDER BY a.id
 LIMIT 500000
 * <BR/>
 * <BR/> 1) создаем три мапинг класса
 * <BR/> 2) связываем их
 * <BR/> 3) создаем hibernate запрос
 * <BR/> 4) сравниваем результаты - старый и новый.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 04.07.2014 17:56
 */
public class EmsOracle
{
    //private SessionFactory sessionFactory;
    //com.mysql.jdbc.Driver driver;

    /*
    protected void setUp() throws Exception
    {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration ()
                .configure () // configures settings from hibernate.cfg.xml
                .buildSessionFactory ();

        // создать фактори для схемы acsmain
    }

    protected void tearDown()
    {
   		if ( sessionFactory != null )
        {
            try
            {
                sessionFactory.close();
            } catch ( HibernateException e )         {
                e.printStackTrace ();
            }
        }
   	}
    */

    /**
     * SELECT DISTINCT a.id, a.hwc_id, a.serial, a.pfid, a.subscriber, a.username, a.password, a.conrequser, a.conreqpass, a.editor, a.comments,  b.oui, b.hwc_id, b.ProductClass, c.url, c.lastcontact, c.currentsoftware, c.cfg_updtime, c.sfw_updtime, c.cfg_updres, c.sfw_updres, c.hardware, c.cfgversion, c.acsd_username, c.acsd_password, c.authresult
      FROM  hostsbean a, hardwaremodelbean b, hostinfobean c
      WHERE (c.hst_id = a.id) and (b.id = c.hwid) and (a.hwc_id = 0) ORDER BY a.id
      LIMIT 500000

     */
   	private void loadCpeList ()
    {
        Session session;
        List    result;

        System.out.println ( "------------------- CPE list --------------------" );

        //session = HibernateUtil.getSessionFactoryAcsMain().openSession();

        try
        {
            session = HibernateUtil.getSessionFactoryAcsMain().getCurrentSession();
            session.beginTransaction();

            // JOIN. Но почему-то для каждой полученной строки формируется свой sql, а не одним глобальным запросом.
            //result = session.createQuery( "from CpeObject" ).setMaxResults ( 10 ).list();
            //result = session.createQuery ( "from CpeObject" ).list();
            result = session.createCriteria ( CpeObject.class ).list();

            session.getTransaction().commit();
            //session.close();

        } catch ( HibernateException e )   {
            result = null;
            // для select в этом нет необходимости.
            HibernateUtil.getSessionFactoryAcsMain().getCurrentSession().getTransaction().rollback();
            e.printStackTrace();
        }

        if ( result != null )
        {
            // Отображать после коммита?  - можно.
            for ( CpeObject event : (List<CpeObject>) result )
            {
                System.out.println ( event );
            }
   		}
   	}

   	private void loadEmsTree ()
    {
        Session session;

        System.out.println ( "------------------- EMS objects --------------------" );

        /*
   		// create a couple of events...
   		Session session = sessionFactory.openSession();
   		session.beginTransaction();
   		session.save( new TreeObject( "Our very first event!", new Date() ) );
   		session.save( new TreeObject( "A follow up event", new Date() ) );
   		session.getTransaction().commit();
   		session.close();
        */

        /*
   		// now lets pull events from the database and list them
   		session = HibernateUtil.getSessionFactoryEmsMain ().openSession ();
        session.beginTransaction();
        List result = session.createQuery( "from TreeObject" ).list();
   		for ( TreeObject event : (List<TreeObject>) result )
        {
   			System.out.println( event );
   		}
        session.getTransaction().commit();
        session.close();
        */
   	}

    public static void main ( String[] args )
    {
        EmsOracle ems;

        ems = new EmsOracle();
        try
        {
            //ems.setUp();
            //ems.loadEmsTree ();
            ems.loadCpeList ();

        } catch ( Exception e )        {
            e.printStackTrace ();
        } finally   {
            HibernateUtil.close ();
        }
    }

    /*
Object[] params = { "Test" };
//Object[] params = { "Test[padded with 60 spaces]" };

    String hqlString = "from ParentObject where childObject.id = ?";
    List<ParentObject> found = getHibernateTemplate().findByQuery(hqlString, params);
    // returns an empty list

---------- foreign key ----------------

 Table Person
name char(64) primary key
age int

Table Car
car_registration char(32) primary key
car_brand (char 64)
car_model (char64)
owner_name char(64) foreign key references Person(name)

class Person{
   ...
}

class Car{
    ...
    @ManyToOne
    @JoinColumn(columnName="owner_name", referencedColumnName="name")
    private Person owner;
}


@OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@BatchSize(size = 30)

     */



}
