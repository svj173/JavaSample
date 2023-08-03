package convert;


import tools.Convert;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 20.04.2011 13:28:31
 */
public class VersionTools
{
    /**
     * Парсим строку и преобразуем в Version.
     * <BR/> Формат: versionNumber.build.subBuild-comments
     * <BR/>
     * @param strVersion  Строковое значение версии.
     * @return            Версия как обьект.
     */
    public static Version createVersion ( String strVersion )
    {
        Version     result;
        int         ic;
        String[]    ss;
        String      comments;

        System.out.println ( "Start. strVersion = '" + strVersion + "'" );

        result  = new Version();

        if ( (strVersion == null) || strVersion.isEmpty() )
        {
            // Ошибка в версии - отмечаем что версия неизвестна
            result.setVersionNumber ( -1 );
        }
        else
        {
            strVersion  = strVersion.trim();
            // Выделяем комментарий - по первому дефису (т.к. в комментарии могут быть и др дефисы).
            ic  = strVersion.indexOf ( '-' );
            System.out.println ( "ic comments = " + ic);
            if ( ic > 0 )
            {
                comments    = strVersion.substring ( ic+1 );
                result.setComments ( comments );
                // обрезаем комментарий
                strVersion  = strVersion.substring ( 0, ic );
            }
            System.out.println ( "strVersion = '" + strVersion + "'" );
            // Выделяем числа
            ss  = strVersion.split ( "[.]" );
            System.out.println ( "decimal array = " + Convert.listArray ( ss, ',' ));
            ic  = ss.length;
            System.out.println ( "ic tochka = " + ic);
            switch ( ic )
            {
                case 0:
                    result.setVersionNumber ( -1 );
                    break;
                case 1:
                    result.setBuild ( Convert.getInt(ss[0], -1) );
                    break;
                case 2:
                    result.setBuild ( Convert.getInt(ss[0], -1) );
                    result.setSubBuild ( Convert.getInt(ss[1], -1) );
                    break;
                default:
                case 3:
                    result.setVersionNumber ( Convert.getInt(ss[0], -1) );
                    result.setBuild ( Convert.getInt(ss[1], -1) );
                    result.setSubBuild ( Convert.getInt(ss[2], -1) );
                    break;
            }
        }

        return result;
    }

    public static void main ( String[] args )
    {
        String  str;
        Version version, oldVersion;
        int     ic;

        str     = "1999";
        version = VersionTools.createVersion ( str );
        System.out.println ( "str = '" + str + "', version = '" + version + "'\n" );

        str     = "1999.22";
        version = VersionTools.createVersion ( str );
        System.out.println ( "str = '" + str + "', version = '" + version + "'\n" );

        str     = "5.19.22";
        version = VersionTools.createVersion ( str );
        System.out.println ( "str = '" + str + "', version = '" + version + "'\n" );

        str     = "5.19.22-d45";
        version = VersionTools.createVersion ( str );
        System.out.println ( "str = '" + str + "', version = '" + version + "'\n" );

        str     = "2000";
        version = VersionTools.createVersion ( str );
        System.out.println ( "str = '" + str + "', version = '" + version + "'\n" );

        oldVersion = VersionTools.createVersion ( null );
        System.out.println ( "oldVersion = '" + oldVersion + "'\n" );

        System.out.println ( "Compare: oldVersion = '" + oldVersion + "' && '" + version + "'" );
        ic  = oldVersion.compareTo ( version );
        System.out.println ( "oldVersion.compareTo ( version ) = " + ic  );

        ic  = oldVersion.compareTo ( oldVersion );
        System.out.println ( "oldVersion.compareTo ( oldVersion ) = " + ic  );
    }
    
}
