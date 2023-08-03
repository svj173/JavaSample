package svj.ldap.jianwikis;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:18
 */
public class LdapAuthentication
{
    /*
    protected boolean authCredential(WorkflowContext context, Principal principal, Credential credential)
    {
        boolean isSuccessful = false;
        LdapEmployeeProfile profile = ldapEmployeeProfileDAO.findUser(principal.getName());

        if(profile != null){ UsernamePasswordCredential userCredential = (UsernamePasswordCredential)credential;

            isSuccessful = ldapEmployeeProfileDAO.bindUser(profile.getDn(), userCredential.getValue());

            if(log.isDebugEnabled()){
                log.debug("Authenticate UsernamePasswordCredential for " + principal.getName()
                        + " against ldap is " + (isSuccessful ? " Successful" : " Failed"));
            }
        }else{
            if(log.isDebugEnabled()){
                log.debug("Cannot find the profile for " + principal.getName()
                        + " in ldap server");
            }
        }

        return isSuccessful;
    }
    */

}
