package svj.string;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 10.06.2021 16:29
 */
public class UseGteTest {

    public static void main ( String[] args )
    {
        UseGteTest mng = new UseGteTest();

        mng.testA();
        //mng.testB();
        //mng.testC();

    }

    /*

    Было

   filter = Document{
    {
     _id=Document{
     {$gte=2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e,
      $lt=2021-06-11_ea1bca49-0700-4d17-9bf4-752357468c08}},                        -- next day
      houseId=ea1bca49-0700-4d17-9bf4-752357468c08}}


А)

1) Имеем лог-записи
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f|1
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08|1

2) Необходимо сообщить что для
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e
нет ничего

Б)

1) Имеем лог-записи
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f|1
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08|1
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e|1

2) Необходимо получить для запроса
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e
две последних записи

В)

1) Имеем лог-записи
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f|1
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08|1
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e|1

2) Необходимо получить для запроса
2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08
две средних записи


     */
    private void testA() {
        String      id, idLog;
        boolean     math;
        String[]    values;

        idLog = "2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4e";

        values  = new String[] { "2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f",
                "2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08_60c184fd8bf1872cd2c39c4f|1",
                "2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08",
                "2021-06-10_ea1bca49-0700-4d17-9bf4-752357468c08|1" };

        /*
        for ( String value : values )
        {
            System.out.println ( value );
            id    = parseHouseId ( value );
            System.out.println ( "\t---\t" + id );
            System.out.println ( "\t---\t" + idHasSection(value) );
        }
        */
    }



}
