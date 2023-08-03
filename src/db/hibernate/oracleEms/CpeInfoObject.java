package db.hibernate.oracleEms;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.07.2014 9:25
 */
@Entity
@Table ( name = "hostinfobean" )
public class CpeInfoObject   implements Serializable
{
    /*
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long   id;
    */
    @Id
    @Column(name = "hst_id", unique = true, nullable = false)
    @GeneratedValue(generator="gen")
    @GenericGenerator (name="gen", strategy="foreign", parameters=@Parameter(name="property", value="cpeObject"))
    private Long   cpeId;

    @Column(name = "url")
    private String url;
    @Column(name = "acsd_username")
    private String acsdUsername;
    @Column(name = "acsd_password")
    private String acsdPassword;
    @Column(name = "authresult")
    private String authresult;

    @Temporal ( TemporalType.TIMESTAMP)
    @Column (name = "lastcontact")
    private Date   lastcontact;

    //@PrimaryKeyJoinColumn
    //@Column (name = "hwid")
    //@OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hwid")     // имя поля в другой таблице БД
    private HwModelObject hwModelObject;

    //@OneToOne(mappedBy = "infoObject")     // обратная ссылка
    //@OneToOne
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private CpeObject cpeObject;



    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "[ CpeInfoObject : cpeId = " );
        result.append ( getCpeId () );
        result.append ( "; acsdUsername = " );
        result.append ( getAcsdUsername () );
        result.append ( "; acsdPassword = " );
        result.append ( getAcsdPassword () );
        result.append ( "; authresult = " );
        result.append ( getAuthresult () );
        result.append ( "; lastcontact = " );
        result.append ( getLastcontact () );
        result.append ( "; url = " );
        result.append ( getUrl () );
        result.append ( "; hwModelObject = " );
        result.append ( getHwModelObject() );

        result.append ( " ]" );

        return result.toString();
    }

    /*
    public Long getId ()
    {
        return id;
    }

    public void setId ( Long id )
    {
        this.id = id;
    }
    */

    public String getUrl ()
    {
        return url;
    }

    public void setUrl ( String url )
    {
        this.url = url;
    }

    public String getAcsdUsername ()
    {
        return acsdUsername;
    }

    public void setAcsdUsername ( String acsdUsername )
    {
        this.acsdUsername = acsdUsername;
    }

    public String getAcsdPassword ()
    {
        return acsdPassword;
    }

    public void setAcsdPassword ( String acsdPassword )
    {
        this.acsdPassword = acsdPassword;
    }

    public String getAuthresult ()
    {
        return authresult;
    }

    public void setAuthresult ( String authresult )
    {
        this.authresult = authresult;
    }

    public Date getLastcontact ()
    {
        return lastcontact;
    }

    public void setLastcontact ( Date lastcontact )
    {
        this.lastcontact = lastcontact;
    }

    public Long getCpeId ()
    {
        return cpeId;
    }

    public void setCpeId ( Long cpeId )
    {
        this.cpeId = cpeId;
    }

    public HwModelObject getHwModelObject ()
    {
        return hwModelObject;
    }

    public void setHwModelObject ( HwModelObject hwModelObject )
    {
        this.hwModelObject = hwModelObject;
    }

    public CpeObject getCpeObject ()
    {
        return cpeObject;
    }

    public void setCpeObject ( CpeObject cpeObject )
    {
        this.cpeObject = cpeObject;
    }
}
