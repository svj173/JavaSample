package svj.ldap;


import exception.SvjException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 *
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 05.02.2016 10:40
 */
public class LdapAuth01
{
    public Attributes getList ( String ldapAttr, String[] authAttr )   throws SvjException
    {
        Attributes attrs;
        DirContext ctx;

        try
        {
            // узнать список механизмов SASL, которые поддерживает сервер LDAP.
            // 1)
            // - Create initial context - Только если есть anonymous
            ctx = new InitialDirContext();

            /*
            // 2)
            Hashtable<String, Object> env = new Hashtable<String, Object>();
            env.put ( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
            env.put ( Context.PROVIDER_URL, ldapAttr );
            //env.put(Context.SECURITY_PROTOCOL, "ssl");
            // Authenticate as C. User and password "mysecret"
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5 NTLM CRAM-MD5" );    // User not found
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5, NTLM, CRAM-MD5" );    // User not found
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5" );
            env.put ( Context.SECURITY_AUTHENTICATION, "simple" );      // OK
            ctx     = new InitialDirContext( env );
            */

            // - Read supportedSASLMechanisms from root DSE
            attrs   = ctx.getAttributes ( ldapAttr, authAttr );

        } catch ( Exception e )      {
            e.printStackTrace();
            throw new SvjException ( e, "error" );
        }

        return attrs;
    }

    /**
     *
     * [LDAP: error code 49 - SASL(-13): user not found: no secret in database]
     *
     * @param ldapAuthAttr
     * @param principal
     * @param pwd
     * @return
     * @throws SvjException
     */
    public DirContext auth ( String ldapAuthAttr, String principal, String pwd )    throws SvjException
    {
        DirContext ctx;

        try
        {
            // Set up the environment for creating the initial context
            Hashtable<String, Object> env = new Hashtable<String, Object>();
            env.put ( Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory" );
            env.put ( Context.PROVIDER_URL, ldapAuthAttr );

            //env.put(Context.SECURITY_PROTOCOL, "ssl");

            // Authenticate as C. User and password "mysecret"
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5 NTLM CRAM-MD5" );    // User not found
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5, NTLM, CRAM-MD5" );    // User not found
            //env.put ( Context.SECURITY_AUTHENTICATION, "DIGEST-MD5" );
            env.put ( Context.SECURITY_AUTHENTICATION, "simple" );      // OK

            env.put ( Context.SECURITY_PRINCIPAL, principal );
            env.put ( Context.SECURITY_CREDENTIALS, pwd );

            // Create the initial context
            ctx = new InitialDirContext(env);
            System.out.println ( "User context = " + ctx );

        } catch ( Exception e )       {
            throw new SvjException ( e, "error" );
        }
        return ctx;
    }

    public static void main ( String[] args )
    {
        String     ldapAttr, ldapAuthAttr, principal, pwd;
        String[]   authAttr;
        Attributes attrs;
        LdapAuth01 handler;
        DirContext ctx;

        ctx = null;

        //ldapAttr = "ldap://localhost:389";
        //ldapAttr = "ldap://172.16.0.245:389";
        ldapAttr = "ldap://ldap.eltex.loc:389";
        authAttr = new String[] { "supportedSASLMechanisms" };

        handler  = new LdapAuth01();

        //System.out.println ( "ldapAttr = " + ldapAttr );

        // auth
        //ldapAuthAttr = "ldap://localhost:389/o=JNDITutorial";
        //ldapAuthAttr = "ldap://172.16.0.245:389/o=domains";
        //ldapAuthAttr = "ldap://172.16.0.245:389";
        //ldapAuthAttr = "ldap://ldap.eltex.loc:389";
        ldapAuthAttr = "ldap://ldap.eltex.loc:389/o=domains";

        //principal    = "cn=C. User, ou=NewHires, o=JNDITutorial";
        //principal    = "cn=sergey.zhiganov, ou=Users, o=domains";
        //principal    = "mail=sergey.zhiganov@eltex.loc,ou=Users,domainName=eltex.loc,o=domains,dc=eltex,dc=loc";
        //principal    = "cn=admin,dc=eltex,dc=loc";      // OK for Simple auth
        //principal    = "uid=sergey.zhiganov,o=domains,dc=eltex,dc=loc";
        //principal    = "uid=sergey.zhiganov,ou=Users,domainName=eltex.loc,o=domains,dc=eltex,dc=loc";
        //principal    = "uid=sergey.zhiganov,ou=Users,o=domains";
        principal    = "sergey.zhiganov@eltex.loc";             //  - invalid DN
        //principal    = "eltex\\sergey.zhiganov";              //  invalid DN
        //principal    = "cn=admin";
        //principal    = "cn=admin,ou=Users,domainName=eltex.loc,o=domains,dc=eltex,dc=loc";

        //pwd          = "admin";
        pwd          = "1q2w3e";    // redmain
        //pwd          = "a2ep8m";    // kmail, vpn


        try
        {
            //attrs   = handler.getList ( ldapAttr, authAttr );
            // Пример ответа: supportedsaslmechanisms=supportedSASLMechanisms: DIGEST-MD5, NTLM, CRAM-MD5
            //System.out.println ( "Auth attrs = " + attrs );

            ctx = handler.auth ( ldapAuthAttr, principal, pwd );
            System.out.println ( "Auth ctx = " + ctx );

            System.out.println ( "Auth ctx params = " + ctx.getEnvironment() );


        } catch ( Exception e )         {
            e.printStackTrace();
        } finally      {
            if ( ctx != null )
            {
                try
                {
                    ctx.close();
                } catch ( NamingException e )      {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
Eltex
1) Получение спсика SASL - anonymous bind disallowed

     */
}
