package svj.ldap;


import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Properties;

/**
 * Following Code authenticates from LDAP using pure Java JNDI. The Principle is:-

     1) First Lookup the user using a admin or DN user.
     2) The user object needs to be passed to LDAP again with the user credential
     3) No Exception means - Authenticated Successfully. Else Authentication Failed.

 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 14:35
 */
public class LdapAuth02
{
    public static boolean authenticateJndi(String username, String password) throws Exception
    {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, "ldap://LDAPSERVER:PORT");
        props.put(Context.SECURITY_PRINCIPAL, "uid=adminuser,ou=special users,o=xx.com");//adminuser - User with special priviledge, dn user
        props.put(Context.SECURITY_CREDENTIALS, "adminpassword");//dn user password


        InitialDirContext context = new InitialDirContext(props);

        SearchControls ctrls = new SearchControls();
        ctrls.setReturningAttributes(new String[] { "givenName", "sn","memberOf" });
        ctrls.setSearchScope( SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> answers = context.search("o=xx.com", "(uid=" + username + ")", ctrls);
        javax.naming.directory.SearchResult result = answers.nextElement();

        String user = result.getNameInNamespace();

        try {
            props = new Properties ();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, "ldap://LDAPSERVER:PORT");
            props.put(Context.SECURITY_PRINCIPAL, user);
            props.put( Context.SECURITY_CREDENTIALS, password);

            context = new InitialDirContext (props);

        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
