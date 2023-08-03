package svj.domain;


import svj.obj.DomainObj;

import java.util.HashMap;
import java.util.Map;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.10.2016 16:14
 */
public class DomainContainer
{
    private DomainObj root = null;

    /** Список ID всех устройств системы (исключая узлы) */
    //private  final Map<String, DomainObj> domainMapFullName     = new ConcurrentHashMap<String, DomainObj> ();
    private  final Map<String, DomainObj> domainMapFullName     = new HashMap<String, DomainObj> ();

    private final Object LOCK = new Object();


    /** Очистить рабочие массивы. */
    /*
    public void clear ()
    {
        getDomainMapFullName().clear();
    }
    */

    public Map<String, DomainObj> getDomainMapFullName ()
    {
        return domainMapFullName;
    }

    public DomainObj getObjectByFullName ( String fullName )
    {
        DomainObj result;
        if ( fullName != null )
        {
            synchronized ( LOCK )
            {
                result = domainMapFullName.get ( fullName );
            }
        }
        else
            result = null;
        //if ( result != null )  result = result.clone();
        return result;
    }

    public DomainObj getDomainById ( int id )
    {
        DomainObj result;

        synchronized ( LOCK )
        {
            result = null;
            if ( getRoot ().getId () == id )
            {
                return getRoot ();
            }
            else
            {
                for ( DomainObj dObj : getRoot ().getChilds () )
                {
                    result = getDomainById ( dObj, id );
                    if ( result != null ) break;
                }
            }
        }

        return result;
    }

    private DomainObj getDomainById ( DomainObj domainObj, int id )
    {
        DomainObj result;

        result = null;
        if ( domainObj.getId() == id )
        {
            return domainObj;
        }
        else
        {
            for ( DomainObj dObj : domainObj.getChilds() )
            {
                result = getDomainById ( dObj, id );
                if ( result != null ) break;
            }
        }

        return result;
    }

    public DomainObj getDomainByName ( DomainObj domainObj, String name )
    {
        DomainObj result;

        result = null;
        if ( domainObj.getName().equals ( name ) )
        {
            return domainObj;
        }
        else
        {
            for ( DomainObj dObj : domainObj.getChilds() )
            {
                result = getDomainByName ( dObj, name );
                if ( result != null ) break;
            }
        }

        return result;
    }

    public DomainObj getRoot ()
    {
        synchronized ( LOCK )
        {
            return root;
        }
    }

    public void setRoot ( DomainObj domainRoot )
    {
        synchronized ( LOCK )
        {
            root = domainRoot;

            domainMapFullName.clear ();

            // Перестроить мапы
            if ( domainRoot != null )
            {
                addDomainsToMap ( domainRoot );
            }
        }
    }

    private void addDomainsToMap ( DomainObj domain )
    {
        if ( domain != null )
        {
            //synchronized ( LOCK )
            //{
                domainMapFullName.put ( domain.getFullName(), domain );
                //domain.getChilds().forEach ( this::addDomainsToMap );
                for ( DomainObj dObj : domain.getChilds() )
                {
                    addDomainsToMap ( dObj );
                }
            //}
        }
    }


}
