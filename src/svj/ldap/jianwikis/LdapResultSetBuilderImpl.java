package svj.ldap.jianwikis;


import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import java.util.HashMap;
import java.util.Map;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:07
 */
public class LdapResultSetBuilderImpl /*extends I18NSupportImpl*/ implements LdapResultSetBuilder
{

    public LdapResultSet buildLdapResultSet ( SearchResult result, String[] attributesToReturn ) throws NamingException
    {

        if ( result == null )
            return null;

        LdapResultSet resultSet = new LdapResultSet ();
        Attributes attributes = result.getAttributes ();
        Map<String, Object> attributesMap = new HashMap<String, Object> ();

        if ( attributesToReturn == null )
        {
            resultSet.setAttributeMap ( null );
        }
        else
        {

            for ( String attribute : attributesToReturn )
            {
                Attribute attr = attributes.get ( attribute );
                if ( attr != null )
                    attributesMap.put ( attribute, attr.get () );
                else
                    attributesMap.put ( attribute, null );
            }

            resultSet.setAttributeMap ( attributesMap );
        }

        return resultSet;
    }

}
