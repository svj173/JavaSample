package svj.ldap.jianwikis;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:03
 */
public class ContextSource
{
    private String contextFactory;

    private String providerUrl;

    private String authenticationType;

    private String managerDN;

    private String managerPassword;

    private String base;

        private String securityProtocol;

    public String getContextFactory ()
    {
        return contextFactory;
    }

    public void setContextFactory ( String contextFactory )
    {
        this.contextFactory = contextFactory;
    }

    public String getProviderUrl ()
    {
        return providerUrl;
    }

    public void setProviderUrl ( String providerUrl )
    {
        this.providerUrl = providerUrl;
    }

    public String getAuthenticationType ()
    {
        return authenticationType;
    }

    public void setAuthenticationType ( String authenticationType )
    {
        this.authenticationType = authenticationType;
    }

    public String getManagerDN ()
    {
        return managerDN;
    }

    public void setManagerDN ( String managerDN )
    {
        this.managerDN = managerDN;
    }

    public String getManagerPassword ()
    {
        return managerPassword;
    }

    public void setManagerPassword ( String managerPassword )
    {
        this.managerPassword = managerPassword;
    }

    public String getBase ()
    {
        return base;
    }

    public void setBase ( String base )
    {
        this.base = base;
    }

    public String getSecurityProtocol ()
    {
        return securityProtocol;
    }

    public void setSecurityProtocol ( String securityProtocol )
    {
        this.securityProtocol = securityProtocol;
    }
}
