package svj.ldap.jianwikis;


import javax.naming.NamingException;
import javax.naming.directory.SearchResult;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:06
 */
public interface LdapResultSetBuilder
{
    LdapResultSet buildLdapResultSet(SearchResult result, String[] attributesToReturn) throws NamingException;
}
