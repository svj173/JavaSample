package socket.send;

import java.io.Serializable;

/**
 * <BR> Обьект содержит в себе файл, как массив байт.
 * <BR>
 * <BR> User: svj
 * <BR> Date: 16.06.2006
 * <BR> Time: 11:01:48
 */
public class TestObjectWithFile  implements Serializable
{
    public byte[]   file;

    /* Имя файла - обязательно в относительном пути. */
    public  String  fileName;

    /* Код команды. */
    public int      code;

    public int      fileSize;
    //public int      fileSendSize;


    public TestObjectWithFile ()
    {
    }

    public String toString()
    {
        StringBuffer    result  = new StringBuffer ( 128 );
        result.append ( "TestObjectWithFile. Code = " );
        result.append ( code );
        result.append ( ", fileName = " );
        result.append ( fileName );
        result.append ( ", fileSize = " );
        result.append ( fileSize );
        result.append ( ", fileSendSize = " );
        result.append ( file.length );
        return result.toString ();
    }

}
