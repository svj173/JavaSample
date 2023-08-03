package string.regexc;


/**
 * Валидация доменов.
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 15.03.2018 11:42:31
 */
public class RegexpDomains
{
    // Только на один домен. Нельзя перечислить несколько.
    public static final String DOMAIN_REGEX = "(?=^.{1,235}$)(^((?!-|_|.*(__|--).*)[a-zA-Z0-9_\\-]{1,63}\\.)*((?!-|_|.*(__|--).*)[a-zA-Z0-9_\\-]{1,63})$)";

    public static void main ( String[] args )
    {
        boolean b;
        String[] domains = { "root", "Ap.root", "Clients.root,Sibir.rt.root,Ap.root,Service.root", "Clients.root Sibir.rt.root Ap.root Service.root" };

        for ( String domain: domains )
        {
            b = domain.matches ( DOMAIN_REGEX );
            System.out.println ( domain + "\t -- " + b );
        }
    }

    /*
root	 -- true
Ap.root	 -- true
Clients.root,Sibir.rt.root,Ap.root,Service.root	 -- false
Clients.root Sibir.rt.root Ap.root Service.root	 -- false

     */
}
