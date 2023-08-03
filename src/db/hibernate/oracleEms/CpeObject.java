package db.hibernate.oracleEms;


import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.07.2014 9:12
 */
@Entity
@Table ( name = "hostsbean" )
public class CpeObject  implements Serializable
{
    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "serial")
    private String serial;

    // ссылаемся на таблицу hostinfobean на поле hst_id  своим полем id
    //@PrimaryKeyJoinColumn
    //@OneToOne (cascade = CascadeType.ALL)
    //@JoinColumn(name = "id")
    //@Column (name = "id")
    //@JoinColumn(name = "hst_id")       // это поле в этой таблице - ссылается на другую таблицу (на ИД?)
    //@JoinTable ( name="hostinfobean", joinColumns = @JoinColumn(name="hst_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    @OneToOne(fetch = FetchType.LAZY, mappedBy="cpeObject", cascade=CascadeType.ALL)
    private CpeInfoObject infoObject;


    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "\n - [ CpeObject : id = " );
        result.append ( getId () );
        result.append ( "; serial = " );
        result.append ( getSerial () );
        result.append ( "; infoObject = " );
        result.append ( getInfoObject () );

        result.append ( " ]" );

        return result.toString();
    }

    public Long getId ()
    {
        return id;
    }

    public void setId ( Long id )
    {
        this.id = id;
    }

    public String getSerial ()
    {
        return serial;
    }

    public void setSerial ( String serial )
    {
        this.serial = serial;
    }

    //*
    public CpeInfoObject getInfoObject ()
    {
        return infoObject;
    }

    public void setInfoObject ( CpeInfoObject infoObject )
    {
        this.infoObject = infoObject;
    }
   //*/
}
