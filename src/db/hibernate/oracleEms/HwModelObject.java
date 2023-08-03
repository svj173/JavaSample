package db.hibernate.oracleEms;


import javax.persistence.*;
import java.io.Serializable;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.07.2014 9:23
 */
@Entity
@Table ( name = "hardwaremodelbean" )
public class HwModelObject    implements Serializable
{
    @Id
    @Column (name="id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long    id;
    @Column (name="hwc_id")
    private Integer hwcId;
    @Column (name="ProductClass")
    private String  productClass;

    //@OneToOne(mappedBy = "hwModelObject")
    //private CpeInfoObject cpeInfo;


    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "[ HwModelObject : id = " );
        result.append ( getId() );
        result.append ( "; hwcId = " );
        result.append ( getHwcId () );
        result.append ( "; productClass = " );
        result.append ( getProductClass () );

        result.append ( " ]" );

        return result.toString();
    }

    public Integer getHwcId ()
    {
        return hwcId;
    }

    public void setHwcId ( Integer hwcId )
    {
        this.hwcId = hwcId;
    }

    public String getProductClass ()
    {
        return productClass;
    }

    public void setProductClass ( String productClass )
    {
        this.productClass = productClass;
    }

    public Long getId ()
    {
        return id;
    }

    public void setId ( Long id )
    {
        this.id = id;
    }
}
