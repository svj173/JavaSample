package enumUse;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.03.2012 9:08:23
 */
public enum EnumNumber  implements INameNumber
{
    ON ( 1, "on" ), OFF ( 2, "off" );

    private int number;
    private String name;

    EnumNumber ( int number, String name )
    {
        this.number = number;
        this.name = name;
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    public int getNumber ()
    {
        return number;
    }
    
}
