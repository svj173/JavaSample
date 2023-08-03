package svj.ldap;


import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 17:24
 */
public class SimpleLdapAuthentication
{
    public static void main ( String[] args )
    {
        String username = "sergey.zhiganov";
        String password = "1q2w3e";
        //String base     = "ou=People,dc=objects,dc=com,dc=au";
        //String base     = "ou=Users,dc=eltex,dc=loc";
        String base     = "ou=Users,o=domains,dc=eltex,dc=loc";    // Invalid Credentials] -- Надо Шифровать пароль? - см в конфиге натсройки шифрования.
        String dn       = "uid=" + username + "," + base;
        //String ldapURL  = "ldap://ldap.example.com:389";
        String ldapURL  = "ldap://ldap.eltex.loc:389";

        // Setup environment for authenticating

        Hashtable<String, String> environment =
                new Hashtable<String, String> ();
        environment.put ( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        environment.put ( Context.PROVIDER_URL, ldapURL );
        environment.put ( Context.SECURITY_AUTHENTICATION, "simple" );
        environment.put ( Context.SECURITY_PRINCIPAL, dn );
        environment.put ( Context.SECURITY_CREDENTIALS, password );

        try
        {
            DirContext authContext = new InitialDirContext ( environment );
            System.out.println ( "authContext = "+authContext );

            // user is authenticated

        } catch ( AuthenticationException ex )        {
            // Authentication failed
            ex.printStackTrace ();
        } catch ( Exception ex )         {
            ex.printStackTrace ();
        }
    }

}
