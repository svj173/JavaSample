package convert;


import java.io.Serializable;


/**
 * Обьект версии ПО.
 * <BR/> Форма: dd.dddd.ddd-coments     -- versionNumber.build.subBuild-comments
 * <BR/> Три числовых поля неограничееной длины (в цифрах) - разделены точкой. Четвертое поле - символьное (комментарий), отделено символом 'тире'.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.04.2011 13:05:22
 */
public class Version  implements Serializable, Comparable
{
    private Integer     versionNumber;
    private Integer     build;
    private Integer     subBuild;
    private String      comments;


    public Version ()
    {
        versionNumber   = 4;
        build           = 0;
        subBuild        = 0;
        comments        = null;
    }

    public String toString()
    {
        StringBuilder   result;
        char            sep;

        sep     = '.';
        result  = new StringBuilder(32);

        result.append ( versionNumber );
        result.append ( sep );
        result.append ( build );
        result.append ( sep );
        result.append ( subBuild );

        if ( (comments != null) && ( ! comments.isEmpty() ) )
        {
            result.append ( '-' );
            result.append ( comments );
        }

        return result.toString();
    }

    public Integer getVersionNumber ()
    {
        return versionNumber;
    }

    public void setVersionNumber ( Integer versionNumber )
    {
        this.versionNumber = versionNumber;
    }

    public Integer getBuild ()
    {
        return build;
    }

    public void setBuild ( Integer build )
    {
        this.build = build;
    }

    public Integer getSubBuild ()
    {
        return subBuild;
    }

    public void setSubBuild ( Integer subBuild )
    {
        this.subBuild = subBuild;
    }

    public String getComments ()
    {
        return comments;
    }

    public void setComments ( String comments )
    {
        this.comments = comments;
    }

    @Override
    public int compareTo ( Object o )
    {
        int     result;
        Version v;

        result  = -1;
        if ( o instanceof Version )
        {
            v   = ( Version) o;
            result  = getVersionNumber().compareTo( v.getVersionNumber() );
            if ( result == 0 )
            {
                result  = getBuild().compareTo( v.getBuild() );
                if ( result == 0 )
                {
                    result  = getSubBuild().compareTo( v.getSubBuild() );
                    // комментарии здесь не сравниваем. svj, 2011-04-20
                }
            }
        }
        return result;
    }

    /* Версия неизвестна, если хоть одно из числовых полей - отрицательное. (Ошибка парсинга) */
    public boolean isUnknow ()
    {
        return ( getVersionNumber() < 0) || ( getBuild() < 0) || ( getSubBuild() < 0);
    }

}
