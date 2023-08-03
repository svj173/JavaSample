package svj.ldap.jianwikis;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:10
 */
public class LdapResourceImpl  /*extends BaseResourceImpl*/ implements LdapResource {

       private static final String CONNECTION_POOL_KEY = "com.sun.jndi.ldap.connect.pool";

       private static final String ERROR_INVALID_USERNAME_PASSWORD = "error.invalid.username.password";

       private static Log log = LogFactory.getLog ( LdapResourceImpl.class );

       private boolean useConnectionPool;

       private ContextSource contextSource;

       private LdapResultSetBuilder ldapResultSetBuilder;

       private String trustStore;

       private String trustStorePassword;

       private String trustStoreType;

       //@Required
       public final void setLdapResultSetBuilder(
               LdapResultSetBuilder ldapResultSetBuilder) {
           this.ldapResultSetBuilder = ldapResultSetBuilder;
       }

       //@Required
       public final void setContextSource(ContextSource contextSource) {
           this.contextSource = contextSource;
       }

       public DirContext bind(Hashtable env) throws NamingException
       {
           if (log.isDebugEnabled()) {

               log.debug("LdapResource bind Directory Context");
           }

           DirContext ctx = new InitialDirContext (env);

           return ctx;
       }

       public DirContext bind(String userDN, String userPassword) throws NamingException {
           if (userDN == null || userPassword == null)
               //throw new NamingException (getLocalMessage(ERROR_INVALID_USERNAME_PASSWORD));
               throw new NamingException ("Invalid password.");

           Hashtable env = this.getUserEnvironment(userDN, userPassword);

           return this.bind(env);
       }


       public List<LdapResultSet> search(String filter, String[] attributesToReturn) throws NamingException {

           return this.search(contextSource.getBase(), filter, attributesToReturn);
       }

       public List<LdapResultSet> search(String base, String filter, String[] attributesToReturn) throws NamingException {
           if (log.isDebugEnabled()) {
               StringBuffer sb = new StringBuffer();
               if (attributesToReturn != null) {
                   for (String attr : attributesToReturn) {
                       sb.append(attr).append(" ");
                   }
               }

               log.debug("LdapResource search(" + "base = " + base + ", filter = " + filter + " attributesToReturn = " + sb.toString());
           }

           Hashtable env = this.getManagerEnvironment();
           DirContext ctx = this.bind(env);

           List<LdapResultSet> list = new ArrayList<LdapResultSet> ();
           NamingEnumeration results = null;

           try {
               SearchControls controls = new SearchControls();
               controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
               controls.setReturningAttributes(attributesToReturn);
               results = ctx.search(base, filter, controls);

               while (results.hasMore()) {
                   SearchResult result = (SearchResult) results.next();
                   LdapResultSet resultSet = ldapResultSetBuilder.buildLdapResultSet(result, attributesToReturn);
                   list.add(resultSet);
               }
           } catch (NameNotFoundException e) {
               //base context not found
               log.error("Base Context " + base + " not found with Exception " + e.getLocalizedMessage());
           } finally {
               if (results != null) {
                   try {
                       results.close();
                   } catch (Exception e) {
                       //Do nothing here
                       log.warn("Exception during closing result sets " + e.getLocalizedMessage());
                   }
               }

               if (ctx != null) {
                   try {
                       ctx.close();
                   } catch (Exception e) {
                       //Do nothing here
                       log.warn("Exception during closing Directory Context " + e.getLocalizedMessage());
                   }
               }
           }

           return list;
       }


       public void unbind(DirContext ctx) {

           if (ctx != null) {
               try {
                   ctx.close();
               } catch (Exception e) {
                   //Do nothing here
                   log.warn("Exception during closing Directory Context " + e.getLocalizedMessage());
               }
           }
       }

       protected Hashtable getManagerEnvironment() {
           Hashtable<String, String> env = new Hashtable<String, String>();

           env.put(Context.INITIAL_CONTEXT_FACTORY, contextSource.getContextFactory());
           env.put( Context.PROVIDER_URL, contextSource.getProviderUrl());
           env.put(Context.SECURITY_AUTHENTICATION, contextSource.getAuthenticationType());
           env.put(Context.SECURITY_PRINCIPAL, contextSource.getManagerDN());
           env.put(Context.SECURITY_CREDENTIALS, contextSource.getManagerPassword());

           String protocol = contextSource.getSecurityProtocol();
           //use white list, ignore all others that are not "ssl"
           if (protocol != null && protocol.equalsIgnoreCase("ssl")) {
               env.put(Context.SECURITY_PROTOCOL, "ssl");
               if (this.trustStore != null)
                   System.setProperty("javax.net.ssl.trustStore", this.trustStore);
               if (this.trustStoreType != null)
                   System.setProperty("javax.net.ssl.trustStoreType", this.trustStoreType);
               if (this.trustStorePassword != null)
                   System.setProperty("javax.net.ssl.trustStorePassword", this.trustStorePassword);
           }

           if (useConnectionPool) {
               env.put(CONNECTION_POOL_KEY, "true");
           }

           return env;
       }

       protected Hashtable getUserEnvironment(String userDN, String userPassword) {
           Hashtable<String, String> env = new Hashtable<String, String> ();

           env.put(Context.INITIAL_CONTEXT_FACTORY, contextSource.getContextFactory());
           env.put(Context.PROVIDER_URL, contextSource.getProviderUrl());
           env.put(Context.SECURITY_AUTHENTICATION, contextSource.getAuthenticationType());
           env.put(Context.SECURITY_PRINCIPAL, userDN);
           env.put(Context.SECURITY_CREDENTIALS, userPassword);

           String protocol = contextSource.getSecurityProtocol();
           if (protocol != null && protocol.equalsIgnoreCase("ssl")) {
               env.put(Context.SECURITY_PROTOCOL, "ssl");
               if (this.trustStore != null)
                   System.setProperty("javax.net.ssl.trustStore", this.trustStore);
               if (this.trustStoreType != null)
                   System.setProperty("javax.net.ssl.trustStoreType", this.trustStoreType);
               if (this.trustStorePassword != null)
                   System.setProperty("javax.net.ssl.trustStorePassword", this.trustStorePassword);
           }

           return env;
       }

}
