package annotation.s5;


import java.io.Serializable;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:15:35
 */
//@Entity                                           // Declares this an entity bean
//@Table(name = "people")                           // Maps the bean to SQL table "people"
class Person implements Serializable
{
    //@Id
    // Map this to the primary key column.
    //@GeneratedValue ( strategy = GenerationType.AUTO )
    // Database will generate new primary keys, not us.
    private Integer id;

    //@Column ( length = 32 )
    // Truncate column values to 32 characters.
    private String name;

    public Integer getId ()
    {
        return id;
    }

    public void setId ( Integer id )
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name = name;
    }
}

