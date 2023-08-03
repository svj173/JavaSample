package eToken;


import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;

/**
 * Инфа о воткнутом eToken
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.12 12:08
 */
public class Slot0Info extends EtokenManager
{
    public static void main ( String[] args )
    {
        Slot0Info manager;
        Token token;
        Module module;
        String  pin, libName, label;
        int slot;
        TokenInfo   tokenInfo;

        module      = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        pin         = null;

        manager     = new Slot0Info ();

        try
        {
            //System.out.println ( slotInfo.getSlotInfo() );

            module  = manager.getModule ( libName );
            token   = manager.getToken ( module, slot );
            System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            tokenInfo   = token.getTokenInfo();
            System.out.println ( "--- tokenInfo = " + tokenInfo );

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( null, module );
        }
    }

}
