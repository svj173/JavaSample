package svj.db;

/**
 * Информация о таблицах ИК пультов БД sqllite Xiaomi
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.10.23 14:26
 */
public class XiaomiTableInfo {

    // номер колонки, которая содержит зашифрованные данные. (от 1)
    private final int cryptColumn;
    private final String jsonCryptParamName;

    public XiaomiTableInfo() {
        this(0, null);
    }

    public XiaomiTableInfo(int cryptColumn) {
        this(cryptColumn, null);
    }

    public XiaomiTableInfo(int cryptColumn, String jsonCryptParamName) {

        this.cryptColumn = cryptColumn;
        this.jsonCryptParamName  = jsonCryptParamName;
    }

    public int getCryptColumn() {
        return cryptColumn;
    }

}
