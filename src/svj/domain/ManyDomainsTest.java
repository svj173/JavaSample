package svj.domain;


import svj.obj.DomainObj;
import svj.zip.javaobject.LoadObjectProcess;
import svj.zip.javaobject.SaveObjectProcess;
import svj.zip.javaobject.UnzipObjectProcess;
import svj.zip.javaobject.ZipObjectProcess;
import tools.DumpTools;

import java.util.Properties;

/**
 * Нагружаем дерево доменов. Ищем минмум памяти и минимум времени доступа.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.05.2017 16:07
 */
public class ManyDomainsTest
{
    private DomainContainer container = new DomainContainer();


    public static void main ( String[] args )
    {
        ManyDomainsTest manager;

        manager = new ManyDomainsTest();

        // Создать дерево 50000 доменов
        manager.createDomains();

        // Создать мапы

        // Выяснить занимаемую память

        // тесты (по ИД) - многопоточные.

        // - извлечение доменов из мапов

        // - извлечение доменов из дерева
    }

    private void createDomains ()
    {
        DomainObj dRoot, domain, d2, d3, d5;
        int       dId, size;

        //size    = 9;
        size    = 8;
        dId     = 10;

        // Создать корневой домен
        dRoot = createDomain ( 1, 0, "root", "Descr" );
        System.out.println ( "Empty map size    = " + DumpTools.sizeOfObject ( container.getDomainMapFullName() ) );
        System.out.println ( "1 Domain size     = " + DumpTools.sizeOfObject ( dRoot ) );

        // Создать домены первого уровня
        for ( int i=0; i<size; i++ )
        {
            domain = createDomain ( dId, 1, "Domain-1_"+i, "Description for domain "+dId );
            dId++;
            dRoot.addChild ( domain );
        }

        // Создать домены 2 уровня
        for ( DomainObj d : dRoot.getChilds () )
        {
            for ( int i=0; i<size; i++ )
            {
                domain = createDomain ( dId, d.getId(), "Domain-2_" + i, "Description for domain " + dId );
                dId++;
                d.addChild ( domain );
                // Создать домены 3 уровня
                for ( int i1=0; i1<size; i1++ )
                {
                    d2 = createDomain ( dId, domain.getId(), "Domain-3_" + i1, "Description for domain " + dId );
                    dId++;
                    domain.addChild ( d2 );
                    // Создать домены 4 уровня
                    for ( int i2=0; i2<size; i2++ )
                    {
                        d3 = createDomain ( dId, d.getId(), "Domain-4_" + i2, "Description for domain " + dId );
                        dId++;
                        d2.addChild ( d3 );
                        // Создать домены 5 уровня
                        for ( int i3=0; i3<size; i3++ )
                        {
                            d5 = createDomain ( dId, d.getId(), "Domain-5_" + i3, "Description for domain " + dId );
                            dId++;
                            d3.addChild ( d5 );
                        }
                    }
                }
            }
        }

        // здесь же все домены скинутся в мап.
        container.setRoot ( dRoot );

        // начало:   Domain-5_1.Domain-4_1.Domain-3_0.Domain-2_0.Domain-1_0.root
        // середина: Domain-5_5.Domain-4_5.Domain-3_5.Domain-2_5.Domain-1_5.root
        // конец:    Domain-5_9.Domain-4_9.Domain-3_9.Domain-2_9.Domain-1_9.root

        /*

Empty map size    = 82
1 Domain size     = 598

    Домены 5 уровня  (6 имен в полном имени домена)


-- Для 5 --
Total domains = 3906
Domains tree size = 1030438

-- 6 --
Total domains = 9331
Domains tree size = 2462638

-- Size : 8
Total domains = 37449
Domains tree size = 9913248

-- Size : 9
Total domains     = 66430
Domains tree size = 17593213
Domains map count = 66430
Domains map  size = 21952763
Domains full size = 21952808        - в 1.25 раз больше

-- Size: 10
Total domains = 111111

???
Числа сильно близкие. Не может дерево (одни только домены) и мап полных имен (домены + полные_их_имена) быть почти равными по размеру.

-- Здесь невозможно выяснить кто ссылаестя на обьект а кто его хранит.
Необходимо сохранять сразу два обьекта ??? -- НЕТ, также по ссылке выкачает подобьект.
-- Задача: выяснить парвильный размер обьектов в структуре - дерево и куча мапов, причем мапы ссылаются на обьект в дереве, а не хранят его.


Вывод (для 50000 доменов)
1) Наличие мапа
- увеличивает память на 4Мб (дерево доменов - 17 Мб, мап - 21 Мб)
- поиск по полному имени - независмо от положения в списке - не более 1 мсек.
2) Толкьо дерево
- память - меньше на 4 мБ.
- поиск по полному имени: в начале дерева - 1 мсек, в середине - 6 мсек, в конце - 13 мсек.

         */


        System.out.println ( "-- Size : " + size );
        System.out.println ( "Total domains     = " + (dId-10+1) );
        //System.out.println ( DumpTools.printDomainAsTree ( container.getRoot() ) );
        System.out.println ( "Domains tree size = " + DumpTools.sizeOfObject ( container.getRoot () ) );
        System.out.println ( "Domains map count = " + container.getDomainMapFullName().size() );
        System.out.println ( "Domains map  size = " + DumpTools.sizeOfObject ( container.getDomainMapFullName() ) );

        Object[] obj = new Object[2];
        obj[0] = container.getRoot();
        obj[1] = container.getDomainMapFullName();
        System.out.println ( "Domains full size = " + DumpTools.sizeOfObject ( obj ) );

        //System.out.println ( "Map               = " + DumpTools.printMap ( container.getDomainMapFullName() ) );

        // ------ Учитываем время ------
        String ds1, ds2, ds3, ds4;
        int    id1, id2, id3, id4;
        id4 = -1;

        ds1 = "Domain-5_1.Domain-4_1.Domain-3_0.Domain-2_0.Domain-1_0.root";   // id=33
        ds2 = "Domain-5_2.Domain-4_2.Domain-3_2.Domain-2_2.Domain-1_2.root";   // 16626
        // for size = 6
        ds3 = "Domain-5_5.Domain-4_5.Domain-3_5.Domain-2_5.Domain-1_5.root";   // 41532
        // for size = 9
        ds4 = "Domain-5_8.Domain-4_8.Domain-3_8.Domain-2_8.Domain-1_8.root";   // 66438

        // Извлечение доменов из мапа - по полному имени
        System.out.println ( "\n------- MAP -----------" );
        id1 = getDomainFromMap ( ds1 );        // 0 msec
        id2 = getDomainFromMap ( ds2 );
        id3 = getDomainFromMap ( ds3 );        // 1 msec
        if ( size >= 9 )  id4 = getDomainFromMap ( ds4 );        // 1 msec

        // Поиск доменов в дереве  - по полному имени
        System.out.println ( "\n------- TREE -----------" );
        getDomainFromTree ( ds1 );        // 1 msec
        getDomainFromTree ( ds2 );        // 6 msec
        getDomainFromTree ( ds3 );        // 13 msec
        if ( size >= 9 )  getDomainFromTree ( ds4 );        // 22 msec

        // Поиск доменов в дереве  - по ИД
        System.out.println ( "\n------- ID -----------" );
        getDomainFromTreeById ( id1 );        // 1 msec
        getDomainFromTreeById ( id2 );        // 1 msec
        getDomainFromTreeById ( id3 );        // 1 msec
        if ( size >= 9 )  getDomainFromTreeById ( id4 );        // 1 msec

        // Сохраняем дерево в файле применяя zip-unzip
        System.out.println ( "\n------- Zip-Unzip -----------" );
        String fileName;
        UnzipObjectProcess  unzipProcess;
        ZipObjectProcess    zipProcess;
        long startTime1, startTime2;

        fileName        = "/tmp/java.obj.bin";
        startTime1      = System.currentTimeMillis();
        zipProcess      = new ZipObjectProcess();
        unzipProcess    = new UnzipObjectProcess();
        zipProcess.zip ( container.getRoot(), fileName );
        startTime2 = System.currentTimeMillis();
        System.out.println ( "Zip (msec)   : " + (startTime2 - startTime1) );
        unzipProcess.unzip ( fileName );
        System.out.println ( "Unzip (msec) : " + (System.currentTimeMillis() - startTime2) );

        // Сохраняем дерево в файле применяя zip-unzip
        System.out.println ( "\n------- Simple Save-Load -----------" );
        LoadObjectProcess loadProcess;
        SaveObjectProcess saveProcess;

        fileName        = "/tmp/java.obj.bin";
        startTime1      = System.currentTimeMillis();
        saveProcess      = new SaveObjectProcess();
        loadProcess    = new LoadObjectProcess();
        saveProcess.zip ( container.getRoot(), fileName );
        startTime2 = System.currentTimeMillis();
        System.out.println ( "Save (msec) : " + (startTime2 - startTime1) );
        loadProcess.unzip ( fileName );
        System.out.println ( "Load (msec) : " + (System.currentTimeMillis() - startTime2) );

        /*
        Tree         : 10000 Kb
        Zip (msec)   : 1204
        File         : 320 Kb   - ужал в 30 раз
        Unzip (msec) : 2176

        Save (msec) : 2262
        Load (msec) : 2227

         */
    }

    private void getDomainFromTreeById ( int id )
    {
        DomainObj domainObj, rootDn;
        long    startTime;

        startTime = System.currentTimeMillis();
        /*
        domainObj   = null;
        rootDn      = container.getRoot();

        if ( rootDn.getId() == id )
        {
            domainObj = rootDn;
        }
        else
        {
            // смотрим в чилдренах
            for ( DomainObj domain : rootDn.getChilds()  )
            {
                domainObj = getDomainFromTreeById ( domain, id );
            }
        }
        */
        domainObj = getDomainFromTreeById ( container.getRoot(), id );

        System.out.println ( "\nDomain id: " + id );
        System.out.println ( "-- time   =  " + (System.currentTimeMillis() - startTime) );
        System.out.println ( "-- result =  " + domainObj );
    }

    private DomainObj getDomainFromTreeById ( DomainObj domainObj, int id )
    {
        DomainObj result = null;

        /*
        if ( (id > 0) && (domainObj != null) )
        {
            if ( domainObj.getId() == id )
            {
                result = domainObj;
            }
            else
            {
                // смотрим в чилдренах
                for ( DomainObj domain : domainObj.getChilds() )
                {
                    result = getDomainFromTreeById ( domain, id );
                    if ( result != null )  break;  // нашли
                }
            }
        }
        return result;
        */

        if ( (id <= 0) || (domainObj == null) )  return null;

        if ( domainObj.getId() == id )  return domainObj;

        // смотрим в чилдренах
        for ( DomainObj domain : domainObj.getChilds() )
        {
            result = getDomainFromTreeById ( domain, id );
            if ( result != null )  return result;  // нашли
        }

        return null;
    }

    private void getDomainFromTree ( String domainFullName )
    {
        DomainObj domainObj, df;
        long    startTime;
        String[] dn;

        startTime = System.currentTimeMillis ();

        domainObj = container.getRoot ();
        // лексемы
        dn = domainFullName.split ( "\\." );

        // root
        if ( domainObj.getName().equals ( dn[dn.length - 1] ) )
        {
            // смотрим в чилдренах
            for ( int i = ( dn.length - 2 ); i >= 0; i-- )
            {
                df = container.getDomainByName ( domainObj, dn[i] );
                if ( df != null )
                {
                    // переходим к следующей лексеме
                    domainObj = df;
                }
                else
                {
                    // не нашли - выходим.
                    domainObj = null;
                    break;
                }
            }
        }

        System.out.println ( "\nDomain: " + domainFullName );
        System.out.println ( "-- time   =  " + (System.currentTimeMillis() - startTime) );
        System.out.println ( "-- result =  " + domainObj );
    }

    private int getDomainFromMap ( String domainFullName )
    {
        DomainObj domainObj;
        long    startTime;

        startTime = System.currentTimeMillis ();
        domainObj = container.getObjectByFullName ( domainFullName );
        System.out.println ( "\nDomain: " + domainFullName );
        System.out.println ( "-- time   =  " + (System.currentTimeMillis () - startTime) );
        System.out.println ( "-- result =  " + domainObj );
        if ( domainObj != null )
            return domainObj.getId();
        else
            return -1;
    }

    private DomainObj createDomain ( int id, int parentId, String name, String descr )
    {
        DomainObj  result;
        Properties props;

        result = new DomainObj ( name );
        result.setId ( id );
        result.setParentId ( parentId );
        result.setDescr ( descr );

        // Создать текстовые проперти - 5 штук.
        props = new Properties ();
        for ( int i=0; i<5; i++ )
            props.setProperty ( "prop_"+i, "Properties value = "+i );
        result.setProperties ( props );

        return result;
    }

}
