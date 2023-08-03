package svj.obj;


import svj.tools.CommonTools;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.TreeSet;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 18.10.2016 16:11
 */
public class DomainObj  implements Serializable, Comparable<DomainObj>, Cloneable
{
    private int         id, parentId;
    private String      name;
    private String      descr;
    /** Чилдрены. Сортируются по имени автоматом. */
    private final Collection<DomainObj> childs;
    private DomainObj   parentObj;

    /** Дополнительные атрибуты домена */
    //private Map<DomainParam, String> params;

    /** Костыль, т.к. во многих местах в БД остался домен в виде строки. Т.е. псевдо-дерево. */
    private String fullName;

    /** это родитель узла, на которые есть права. Прав нет, но показывается в дереве */
    private boolean containsRoleDomain = false;

    /** DOMAIN_PARAMS content - type, еще что-то. */
    Properties properties = null;

    /**
     *
     * @param name Имя данного домена. Не путать с полным именем домена.
     */
    public DomainObj ( String name )
    {
        this.name   = name;
        parentObj   = null;
        fullName    = null;
        childs      = new TreeSet<> ();
        //params      = new EnumMap<> (DomainParam.class);
    }

    public String toString()
    {
        StringBuilder result;

        result = new StringBuilder();

        result.append ( "[ DomainObj : name = " );
        result.append ( getName() );
        result.append ( "; descr = " );
        result.append ( getDescr () );
        result.append ( "; id = " );
        result.append ( getId () );
        result.append ( "; parentId = " );
        result.append ( getParentId () );
        result.append ( "; fullName = " );
        result.append ( fullName );
        result.append ( "; child size = " );
        result.append ( getChilds().size() );
        result.append ( "; properties = " );
        result.append ( getProperties() );

        result.append ( " ]" );

        return result.toString();
    }

    public String getFullName ()
    {
        StringBuilder    result;
        DomainObj        treeObj;
        String           str;

        if ( fullName != null )  return fullName;

        result = new StringBuilder(128);

        //Logger.getInstance().debug ( "-- CommonTreeObjTools.getFullPath: Start" );
        //LogWriter.snmp.debug ( "-->> CommonTreeObjTools.getFullPath: object = " + object );

        // Пробежать вверх от узла
        treeObj = this;

        while ( treeObj != null )
        {
            //LogWriter.snmp.debug ( "--|-- CommonTreeObjTools.getFullPath: treeObj = " + treeObj );
            //result.insert ( 0, treeObj.getName() );
            //result.insert ( 0, separator );
            result.append ( treeObj.getName () );
            result.append ( '.' );
            treeObj = treeObj.getParent();    // Исп. фиксированное дерево Cons.MANAGER_TREE
        }
        // Удалить последнюю точку
        //LogWriter.snmp.debug ( "--<< CommonTreeObjTools.getFullPath: result = " + result );

        str = result.toString();
        return str.substring ( 0, str.length () - 1 );
    }

    /**
     * Рекурсивный поиск домена в дереве.
     *
     * @param id    ID домена
     * @return      домен с заданным {@code id} или {@code null}
     */
    public DomainObj find(int id) {
        if (id == this.id) {
            return this;
        } else {
            for (DomainObj child : childs) {
                final DomainObj found = child.find(id);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }
    }

    /**
     * Смотрим, содержит ли наш домен указанный домен. Либо они равны.
     * <BR/> Сравнение доменов: по лексемам, справа налево, т.к. в доменах корни прописаны справа, а детализация идет влево (eltex.nsk.ru).
     * <br/> Примеры:
     * <br/> - наш: sberbank.nsk.ru
     * <br/> - их:  sberbank.spb.ru, sberbank.omsk.ru,  macdomalds.nsk.ru, sberbank.nsk.ru, dep1.sberbank.nsk.ru, nsk.ru, ru.
     * <br/> Вхождение - если наши лексемы полностью совпадают с ихними (с конца). (sberbank.nsk.ru, dep1.sberbank.nsk.ru)
     * <br/>
     * @param obj
     * @return
     */
    public boolean contains ( DomainObj obj ) {
        // Ищем текущий домен среди родителей заданного
        while (obj != null) {
            if (obj.id == this.id) {
                return true;
            }
            obj = obj.getParent();
        }
        return false;
    }

    public boolean remove ( DomainObj child )
    {
        boolean result = false;
        if ( child != null )
        {
            synchronized ( childs )
            {
                result = childs.remove ( child );
            }
        }
        return result;
    }

    /**
     * Возвращает максимальную длину полного имени поддомена.
     * <p>
     *     Например, для дерева
     *     <p>
     *     - ru
     *     <br/>--- nsk
     *     <br/>----- eltex
     *     <br/>----- example
     *     </p>
     *     она равна 14 (длина "example.nsk.ru")
     * </p>
     *
     * @return максимальная длина полного имени домена в данном дереве
     */
    public int getMaxLength() {
        if (childs.isEmpty()) {
            return name.length();
        } else {
            int maxChildLength = 0;
            for (DomainObj child : childs) {
                maxChildLength = Math.max(maxChildLength, child.getMaxLength());
            }
            return name.length() + 1 + maxChildLength;
        }
    }

    @Override
    public DomainObj clone () // throws CloneNotSupportedException
    {
        DomainObj result;

        result = new DomainObj ( getName() );
        result.setDescr ( getDescr() );
        result.setId ( getId() );
        result.setParentId ( getParentId() );
        // childs
        for ( DomainObj dom : getChilds() )
        {
            result.addChild ( dom.clone() );
        }
        // parent
        result.setParent ( getParent() );
        result.setFullName ( fullName );

        return result;
    }

    public int getId ()
    {
        return id;
    }

    public void setId ( int id )
    {
        this.id = id;
    }

    public int getParentId ()
    {
        return parentId;
    }

    public void setParentId ( int parentId )
    {
        this.parentId = parentId;
    }

    public String getName ()
    {
        return name;
    }

    public void setName ( String name )
    {
        this.name = name;
    }

    public String getDescr ()
    {
        return descr;
    }

    public void setDescr ( String descr )
    {
        this.descr = descr;
    }

    public void addChild ( DomainObj childObj )
    {
        if ( childObj != null )
        {
            childs.add ( childObj );
            childObj.setParent ( this );
        }
    }

    public Collection<DomainObj> getChilds ()
    {
        return childs;
    }

    /**
     * Сравнение - по имени.
     * @param obj  Домен для сравнение.
     * @return 0 - равно; 1 - мы больше; -1 - мы меньше.
     */
    @Override
    public int compareTo ( DomainObj obj )
    {
        if ( obj == null )
            return 1;
        else
            return CommonTools.compareToWithNull ( getFullName(), obj.getFullName() );
    }

    @Override
    public boolean equals ( Object obj )
    {
        boolean result;

        result = false;

        if ( obj != null )
        {
            if ( obj instanceof DomainObj )
            {
                DomainObj domainObj = (DomainObj) obj;
                result = CommonTools.compareToWithNull ( getFullName (), domainObj.getFullName () ) == 0;
            }
        }

        return result;
    }

    /**
     * Проверяем, является ли домен domain родителем для текущего домена.
     * @param domain   Домен.
     * @return  TRUE если domain является родителем (любой глубины вложенности) для данного домена.
     */
    public boolean isParent ( DomainObj domain )
    {
        DomainObj domainObj;

        // для текущего домена бежим по родителям и сравниваем с domain.
        domainObj = this;
        while ( domainObj != null )
        {
            if ( domainObj.equals ( domain ) ) return true;  // нашли
            domainObj   = domainObj.getParent();
        }

        return false;
    }

    /**
     * Проверяем на чистого (ближайшего) парента. Связки обьектов вида  root -- sb.nsk.root - недопустимы
     * @param domain  Анализируемый домен на родительство к текущему.
     * @return   TRUE - domain является прямым родителем для текущего домена.
     */
    public boolean isNextParent ( DomainObj domain )
    {
        DomainObj domainObj;

        domainObj   = getParent();
        if ( ( domainObj != null ) && domainObj.equals ( domain ) )
            return true;  // domain является прямым родителем для текущего домена.
        else
            return false;
    }

    public void setParent ( DomainObj domainParent )
    {
        parentObj = domainParent;
    }

    public DomainObj getParent ()
    {
        return parentObj;
    }

    public void setFullName ( String fullName )
    {
        this.fullName = fullName;
    }
    /*
    public Map<DomainParam, String> getParams() {
        return params;
    }

    public void setParams(Map<DomainParam, String> params) {
        if (params != null) {
            this.params = params;
        } else {
            this.params.clear();
        }
    }
    */
    public static void main (String[] args )
    {
        DomainObj   d1, dRoot, dNsk, dOur, dSbOmsk, dMNsk, dDep1, dSbSpb;
        boolean     b;

        //ourDomain   = "sberbank.nsk.ru";
        //domains     = new String[] { "sberbank.spb.ru", "sberbank.omsk.ru", "macdomalds.nsk.ru", "sberbank.nsk.ru", "dep1.sberbank.nsk.ru", "nsk.ru", "ru" };

        // Создаем дерево
        dRoot = new DomainObj ( "ru" );
        // nsk
        dNsk    = new DomainObj ( "nsk" );
        dRoot.addChild ( dNsk );
        dOur    = new DomainObj ( "sberbank" );
        dNsk.addChild ( dOur );
        dMNsk = new DomainObj ( "macdomalds" );
        dNsk.addChild ( dMNsk );
        dDep1 = new DomainObj ( "dep1" );
        dOur.addChild ( dDep1 );
        // omsk
        d1    = new DomainObj ( "omsk" );
        dRoot.addChild ( d1 );
        dSbOmsk  = new DomainObj ( "sberbank" );
        d1.addChild ( dSbOmsk );
        // spb
        d1    = new DomainObj ( "spb" );
        dRoot.addChild ( d1 );
        dSbSpb = new DomainObj ( "sberbank" );
        d1.addChild ( dSbSpb );

        System.out.println ( "domain = " + dOur );

        b   = dOur.contains ( dSbSpb );
        System.out.println ( "--> d: " + dSbSpb + "; contains = " + b );

        b   = dOur.contains ( dSbOmsk );
        System.out.println ( "--> d: " + dSbOmsk + "; contains = " + b );

        b   = dOur.contains ( dMNsk );
        System.out.println ( "--> d: " + dMNsk + "; contains = " + b );

        b   = dOur.contains ( dOur );
        System.out.println ( "--> d: " + dOur + "; contains = " + b );

        b   = dOur.contains ( dDep1 );
        System.out.println ( "--> d: " + dDep1 + "; contains = " + b );

        b   = dOur.contains ( dNsk );
        System.out.println ( "--> d: " + dNsk + "; contains = " + b );

        b   = dOur.contains ( dRoot );
        System.out.println ( "--> d: " + dRoot + "; contains = " + b );
    }

    public boolean isRoot ()
    {
        return getParentId() == 0;
    }

    public boolean isContainsRoleDomain() {
        return containsRoleDomain;
    }

    public void setContainsRoleDomain(boolean parent) {
        this.containsRoleDomain = parent;
    }

    public void setProperty(String property, String value) {
        if (properties==null)
            properties = new Properties();
        properties.setProperty(property, value);
    }

    public String getProperty(String property) {
        return properties!=null ?
                properties.getProperty(property) :
                null;
    }

    public DomainObj getPropertyContainer(String property) {
        String value = getProperty(property);
        if (value!=null)
            return this;

        if (parentObj!=null)
            return parentObj.getPropertyContainer(property);
        else
            return null;
    }

    public int sumPropertyValues(String property, DomainObj editDomain ) {
        int result = 0;

        if (!childs.isEmpty()) {
            for (DomainObj child : childs) {
                if (editDomain!=null &&
                        child.getId()==editDomain.getId())
                    continue;

                final String value = child.getProperty(property);
                if (value!=null && !value.isEmpty()) {
                    try {
                        result+= Integer.parseInt(value);
                        //logger.error(null, "Sum ", child.getFullName()," limit ", value);
                    } catch (NumberFormatException e) {
                        //logger.error(e);
                        e.printStackTrace ();
                    }
                } else {
                    result+=child.sumPropertyValues(property, editDomain );
                }
            }
        }

        return result;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public Properties getProperties ()
    {
        return properties;
    }


}
