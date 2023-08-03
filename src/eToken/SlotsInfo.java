package eToken;


import iaik.pkcs.pkcs11.*;

/**
 * Инфа о всех воткнутых eToken
 * <BR/>
 * <BR/> Порядок слотов не соовтествует номерам USB.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 03.10.2012 14:08
 */
public class SlotsInfo extends EtokenManager
{
    public static void main ( String[] args )
    {
        SlotsInfo manager;
        Token token;
        Module module;
        String  pin, libName, label;
        Slot[] slots;
        int ic;

        module      = null;

        libName     = "libeTPkcs11.so";
        ic         = 0;   // USB-0
        pin         = null;

        manager     = new SlotsInfo ();

        try
        {
            //System.out.println ( slotInfo.getSlotInfo() );

            module  = manager.getModule ( libName );


            // Module.SlotRequirement.TOKEN_PRESENT   - поиск только по представителям брелков.
            //slots   = module.getSlotList ( Module.SlotRequirement.ALL_SLOTS );      -- все вообще слоты
            slots   = module.getSlotList ( Module.SlotRequirement.TOKEN_PRESENT );
            System.out.println ( "slots.length = " + slots.length );
            if ( slots.length == 0 ) throw new Exception ( "Tokens is absent." );

            for ( Slot slot : slots )
            {
                System.out.println ( "-------------------------------------------------\nslot = " + slot );
                token = slot.getToken();
                if ( token == null )
                    System.out.println ( "Token for slot #" + ic + " = NULL" );
                else
                    System.out.println ( "Token for slot #" + ic + " = " + token.getTokenInfo() );
                ic++;
            }

        } catch ( Exception e )         {
            e.printStackTrace();
        } finally        {
            manager.closeModule ( null, module );
        }
    }

}
