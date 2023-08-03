package eToken.basic;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 21.09.12 14:05
 */

import eToken.EtokenManager;
import iaik.pkcs.pkcs11.*;
import iaik.pkcs.pkcs11.objects.Data;

/**
 * This demo program can be used to download data to the card.
 * <p/>
 * WriteDataObjects <PKCS#11 module> <data file> <data object label> [<slot>] [<pin>]");
 * e.g.: WriteDataObjects gclib.dll data.dat \"Student Data\"");
 *
 * 1) libName
 * 2) file
 * 3) fileLabel
 * 4) [slot]
 * 5) [pin]
 *
 *   DESSecretKey desKeyTemplate = new DESSecretKey();
 *   // the key type is set by the DESSecretKey's constructor, so you need not do it
 *   desKeyTemplate.setValue(myDesKeyValueAs8BytesLongByteArray);
 *   desKeyTemplate.setToken(Boolean.TRUE);
 *   desKeyTemplate.setPrivate(Boolean.TRUE);
 *   desKeyTemplate.setEncrypt(Boolean.TRUE);
 *   desKeyTemplate.setDecrypt(Boolean.TRUE);
 *   ...
 *   DESSecretKey theCreatedDESKeyObject = (DESSecretKey) userSession.createObject(desKeyTemplate);
 *
 * userSession.createObject return A new PKCS#11 Object (this is not a java.lang.Object!) that serves
 *         holds all the (readable) attributes of the object on the token. In
 *         contrast to the templateObject, this object might have certain
 *         attributes set to token-dependent default-values.
 *
 * Т.е. вроде как обьект который записываем в eToken должен соджержать в себе некоторые token-зависимости.
 *  -- методы прописаны в обьекте Data, который и заносится в брелок.
 */
public class WriteToEtoken  extends EtokenManager
{
    public static void main ( String[] args )
    {
        WriteToEtoken manager;
        Token token;
        Module module;
        String  pin, libName, fileName, label;
        Session session;
        int slot;
        byte[] data;
        Data dataObj;

        module      = null;
        session     = null;

        libName     = "libeTPkcs11.so";
        slot        = 0;   // USB-0
        fileName    = "/home/svj/Eltex/key/eltex_ems_master.p12";
        pin         = null;
        label       = "EltexEMS";

        manager     = new WriteToEtoken();

        try
        {
            module  = manager.getModule ( libName );
            token   = manager.getToken ( module, slot );
            System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            data    = manager.loadFile ( fileName );    // as PKCS12 key store
            //System.out.println ( "Token for slot #" + slot + " = " + token.getTokenInfo() );

            session = manager.createSession ( token, pin );

            dataObj = manager.createDataObj ( data, label );

            session.createObject ( dataObj );

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( session, module );
        }
    }

}
