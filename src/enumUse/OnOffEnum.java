package enumUse;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.03.2012 9:22:00
 */
public enum OnOffEnum
{
	off,    // 2
	on;     // 1

	public static String fromInt ( int onoff )
	{
		if ( onoff == 1 )
			return on.toString();
        else
		    return off.toString();
	}

	public static int getSnmpValue( String source )
	{
		if (source.equalsIgnoreCase(on.toString()))
			return 1;
		else
			return 2;
	}

	public String getStrByIdx ( int idx )
    {
        OnOffEnum oo;

        oo = OnOffEnum.getByIdx ( idx );
        if ( oo == null )
            return null;
        else
            return oo.toString();
    }

	public static OnOffEnum getByIdx (int idx)
	{
		switch (idx)
		{
        	case 1: return on;
        	case 2: return off;
        	default:    return null;
		}
	}

	/**
	 * Применяется для селектора вкл/выкл
	 * @param compareTo
	 * @return
	 */
	public static OnOffEnum switchValue(OnOffEnum compareTo){
			if (compareTo.equals(off)){
					return on;
			}else{
					return off;
			}
	}

}
