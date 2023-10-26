package svj.db;

/**
 * Информация о таблицах ИК пультов БД sqllite Xiaomi
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 25.10.23 14:26
 */
public class XiaomiTableInfo {

    // номер колонки, которая содержит зашифрованные данные. (от 1). 0 - нет зашифрованных данных.
    private final int cryptColumn;
    private final String jsonCryptParamName;
    private final String jsonCryptParamName2;

    public XiaomiTableInfo() {
        this(0, null, null);
    }

    public XiaomiTableInfo(int cryptColumn) {
        this(cryptColumn, null, null);
    }

    public XiaomiTableInfo(int cryptColumn, String jsonCryptParamName) {
        this(cryptColumn, jsonCryptParamName,null);
    }

    public XiaomiTableInfo(int cryptColumn, String jsonCryptParamName, String jsonCryptParamName2) {
        this.cryptColumn = cryptColumn;
        this.jsonCryptParamName  = jsonCryptParamName;
        this.jsonCryptParamName2  = jsonCryptParamName2;
    }

    public int getCryptColumn() {
        return cryptColumn;
    }

    public String getJsonCryptParamName() {
        return jsonCryptParamName;
    }

    public String getJsonCryptParamName2() {
        return jsonCryptParamName2;
    }
}
