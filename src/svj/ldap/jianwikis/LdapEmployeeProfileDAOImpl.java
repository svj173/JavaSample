package svj.ldap.jianwikis;


/**
 * Again, most setters and getters are skipped to make the code more clear.
 * The @Required annotation is used for Spring wiring and the system variables such as "javax.net.ssl.trustStore", "javax.net.ssl.trustStoreType",
 * and "javax.net.ssl.trustStorePassword" are used for Ldaps support.
 * Another way to support the trust store is to add trusted Ldap server CA cert into JDK/jre/lib/security/cacerts,
 * but which may not be preferable. Be aware that you should put the LDAP server CA cert into the trust store instead of individual node cert in a production cluster environment.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:14
 */
public class LdapEmployeeProfileDAOImpl //extends I18NSupportImpl implements LdapEmployeeCredentialDAO, LdapEmployeeAttributes, LdapEmployeeFilter
{
/*
private static Log log = LogFactory.getLog ( LdapEmployeeProfileDAOImpl.class );

private LdapResource ldapResource;

private LdapEmployeeProfileBuilder ldapEmployeeProfileBuilder;

private String[] attributesToReturn;

public final LdapEmployeeProfileBuilder getLdapEmployeeProfileBuilder() {
    return ldapEmployeeProfileBuilder;
}

//@Required
public final void setLdapEmployeeProfileBuilder(
        LdapEmployeeProfileBuilder ldapEmployeeProfileBuilder) {
    this.ldapEmployeeProfileBuilder = ldapEmployeeProfileBuilder;
}

//@Required
public final void setLdapResource(LdapResource ldapResource) {
    this.ldapResource = ldapResource;
}

public boolean bindUser(String userDN, String userPassword) {
    boolean isSuccessful = false;
    DirContext ctx = null;

    try {
        ctx = ldapResource.bind(userDN, userPassword);
        isSuccessful = true;
    } catch (NamingException e) {
        log.warn("Cannot bind user with userDN = " + userDN + " with exception " + e.getLocalizedMessage());
    } finally {

        //unbind and close connection
        if (ctx != null)
            ldapResource.unbind(ctx);
    }

    return isSuccessful;
}

public LdapEmployeeProfile findUser(String userId) {
    if (userId == null) {
        if (log.isDebugEnabled())
            log.debug("Cannot find user with Null userId = ");

        return null;
    }

    //TODO: add filter builders
    String filter = FILTER.replaceFirst(FILTER_TOKEN, userId);
    LdapEmployeeProfile profile = null;

    try {
        List<LdapResultSet> list = ldapResource.search(filter, attributesToReturn);
        if (list != null) {
            if (list.size() == 0) {
                if (log.isDebugEnabled())
                    log.debug("Cannot find user with userId = " + userId);

                return null;
            }

            if (list.size() > 1) {
                log.warn("Duplicated records found for user with userId = " + userId);
                //if duplicated, only return the first record
            } else {
                if (log.isDebugEnabled())
                    log.debug("One record found for user with userId = " + userId);
            }

            LdapResultSet resultSet = list.get(0);

            profile = ldapEmployeeProfileBuilder.buildLdapEmployeeProfile(resultSet);
        }
    } catch (NamingException e) {
        log.warn("Cannot find user with userId = " + userId
                + " with exception " + e.getLocalizedMessage());
    }

    if (log.isDebugEnabled()) {
        if (profile != null) {
            log.debug("Found user: " + profile.toString());
        }
    }

    return profile;
}

@Override
public void afterPropertiesSet() throws Exception {

    this.attributesToReturn = new String[5];
    this.attributesToReturn[0] = FIRST_NAME;
    this.attributesToReturn[1] = LAST_NAME;
    this.attributesToReturn[2] = USER_ID;
    this.attributesToReturn[3] = DN;
    this.attributesToReturn[4] = ACCOUNT_EXPIRES;
}
*/
}
