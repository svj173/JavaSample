package db.hibernate.oracleEms;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 09.07.2014 14:02
 */
public class TreeObject
{
    /*
    <id name="id" column="ID">
    </id>
    <property name="pid" type="int" column="PID"/>
    <property name="type" type="char" column="ETYPE"/>
    <property name="name" type="char" column="ENAME"/>
    <property name="subtype" type="char" column="ESUBTYPE"/>
    <property name="version" type="char" column="EVERSION"/>

     */

    private Long id;
    private Long pid;
    private String type, name, subtype, version, title;

    public Long getId ()
    {
        return id;
    }

    public void setId ( Long id )
    {
        this.id = id;
    }

    public Long getPid ()
    {
        return pid;
    }

    public void setPid ( Long pid )
    {
        this.pid = pid;
    }

    public String getType ()
    {
        return type;
    }

    public void setType ( String type )
    {
        this.type = type;
    }

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name = name;
    }

    public String getSubtype ()
    {
        return subtype;
    }

    public void setSubtype ( String subtype )
    {
        this.subtype = subtype;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion ( String version )
    {
        this.version = version;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle ( String title )
    {
        this.title = title;
    }

    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "[ TreeObject : title = " );
        result.append ( getTitle() );
        result.append ( "; id = " );
        result.append ( getId () );
        result.append ( "; pid = " );
        result.append ( getPid () );
        result.append ( "; type = " );
        result.append ( getType () );
        result.append ( "; name = " );
        result.append ( getName () );
        result.append ( "; subtype = " );
        result.append ( getSubtype () );
        result.append ( "; version = " );
        result.append ( getVersion () );

        result.append ( " ]" );

        return result.toString();
    }

}
