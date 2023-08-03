package svj.ldap;


import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Usage (Windows AD)
 * I have run this in Windows7 and Linux machines against WinAD directory service. Application prints username and member groups.
 * <p>
 * $ java -cp classes test.LoginLDAP url=ldap://1.2.3.4:389 username=myname@company.fi password=mypwd
 * <p>
 * This is my LDAP Java login test application supporting LDAP:// and LDAPS:// self-signed test certificate. Code is taken from few SO posts, simplified implementation and removed legacy sun.java.* imports.
 * <p>
 * Test application supports temporary self-signed test certificates for ldaps:// protocol, this DummySSLFactory accepts any server cert so man-in-the-middle is possible. Real life installation should import server certificate to a local JKS keystore file and not using dummy factory.
 * <p>
 * Application uses enduser's username+password for initial context and ldap queries, it works for WinAD but don't know if can be used for all ldap server implementations. You could create context with internal username+pwd then run queries to see if given enduser is found.
 * <p>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 14:41
 */
public class LoginLDAP
{
    public static void main ( String[] args ) throws Exception
    {
        Map<String, String> params;

        //params = createParams ( args );
        params = createParams ();

        String url = params.get ( "url" ); // ldap://1.2.3.4:389 or ldaps://1.2.3.4:636
        String principalName = params.get ( "username" ); // firstname.lastname@mydomain.com
        String domainName = params.get ( "domain" ); // mydomain.com or empty

        if ( domainName == null || "".equals ( domainName ) )
        {
            int delim = principalName.indexOf ( '@' );
            domainName = principalName.substring ( delim + 1 );
        }

        Properties props = new Properties ();
        props.put ( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
        props.put ( Context.PROVIDER_URL, url );
        props.put ( Context.SECURITY_PRINCIPAL, principalName );
        props.put ( Context.SECURITY_CREDENTIALS, params.get ( "password" ) ); // secretpwd

        if ( url.toUpperCase().startsWith ( "LDAPS://" ) )
        {
            props.put ( Context.SECURITY_PROTOCOL, "ssl" );
            props.put ( Context.SECURITY_AUTHENTICATION, "simple" );
            // Мое самописное фактори.
            props.put ( "java.naming.ldap.factory.socket", "svj.ldap.DummySSLSocketFactory" );
        }

        InitialDirContext context = new InitialDirContext ( props );

        try
        {
            SearchControls ctrls = new SearchControls ();
            ctrls.setSearchScope ( SearchControls.SUBTREE_SCOPE );
            NamingEnumeration<SearchResult> results = context.search ( toDC ( domainName ), "(& (userPrincipalName=" + principalName + ")(objectClass=user))", ctrls );
            if ( !results.hasMore () )
                throw new AuthenticationException ( "Principal name not found" );

            SearchResult result = results.next ();
            System.out.println ( "distinguisedName: " + result.getNameInNamespace() ); // CN=Firstname Lastname,OU=Mycity,DC=mydomain,DC=com

            Attribute memberOf = result.getAttributes().get ( "memberOf" );
            if ( memberOf != null )
            {
                for ( int idx = 0; idx < memberOf.size (); idx++ )
                {
                    System.out.println ( "memberOf: " + memberOf.get ( idx ).toString () ); // CN=Mygroup,CN=Users,DC=mydomain,DC=com
                    //Attribute att = context.getAttributes(memberOf.get(idx).toString(), new String[]{"CN"}).get("CN");
                    //System.out.println( att.get().toString() ); //  CN part of groupname
                }
            }
        } finally      {
            try
            {
                context.close();
            } catch ( Exception ex )
            {
            }
        }
    }

    /**
     * Create "DC=sub,DC=mydomain,DC=com" string
     *
     * @param domainName sub.mydomain.com
     * @return
     */
    private static String toDC ( String domainName )
    {
        StringBuilder buf = new StringBuilder ();
        for ( String token : domainName.split ( "\\." ) )
        {
            if ( token.length () == 0 ) continue;
            if ( buf.length () > 0 ) buf.append ( "," );
            buf.append ( "DC=" ).append ( token );
        }
        return buf.toString ();
    }

    // url=ldap://1.2.3.4:389 username=myname@company.fi password=mypwd
    private static Map<String, String> createParams ( String[] args )
    {
        Map<String, String> params = new HashMap<String, String> ();
        for ( String str : args )
        {
            int delim = str.indexOf ( '=' );
            if ( delim > 0 ) params.put ( str.substring ( 0, delim ).trim (), str.substring ( delim + 1 ).trim () );
            else if ( delim == 0 ) params.put ( "", str.substring ( 1 ).trim () );
            else params.put ( str, null );
        }
        return params;
    }

    private static Map<String, String> createParams ()
    {
        Map<String, String> params = new HashMap<String, String> ();

        params.put ( "url", "ldap://ldap.eltex.loc:389" );
        params.put ( "username", "sergey.zhiganov@eltex.loc" );
        params.put ( "password", "1q2w3e" );

        return params;
    }

}
