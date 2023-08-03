package svj.ldap.jianwikis;


import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import java.util.Hashtable;
import java.util.List;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2016 15:09
 */
public interface LdapResource
{
    public DirContext bind(Hashtable env) throws NamingException;

    public DirContext bind(String userDN, String userPassword) throws NamingException;

    public List<LdapResultSet> search(String filter, String[] attributesToReturn) throws NamingException;

    public List<LdapResultSet> search(String base, String filter, String[] attributesToReturn) throws NamingException;

    public void unbind(DirContext ctx);

}
