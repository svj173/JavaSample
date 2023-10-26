package svj.db;

import db.SQLUtil;
import org.apache.xmlbeans.impl.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sqlite.JDBC;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Чтение базы ИК пультов от Xiaomi.
 * Трудность в том, что база представляет собой файл sqlite, в котором каждая запись упакована и зашифрована, правда ключ есть.
 * Это пилот, который будет извлекать все эти записи. Результат - в mongo - т.к. все расшифрованные записи имеют формат json
 * <br/>
 * <br/>
 * <br/>
 *             Имена таблиц - 20 шт
 *  - devicebrands
 *  - sqlite_sequence
 *  - mi_ir_tree
 *  - mi_remote_ircode
 *  - kk_remote_ircode
 *  - kk_remote_ircode_src
 *  - xm_remote_ircode
 *  - device
 *  - brand
 *  - sp
 *  - xm_sp
 *  - city
 *  - xm_city
 *  - lineup
 *  - xm_lineup
 *  - kk_match
 *  - xm_match
 *  - mi_ir_merge
 *  - country
 *  - version
 */
public class XiaomiIrDbToMongo {

    //public static final String DB_PATH = "jdbc:sqlite:/media/svj/_disk2/home/svj/Serg/stories/common/eltex/IR/Xiaomi/irlocaldata.db";
    public static final String DB_PATH = "jdbc:sqlite:/home/svj/Projects/SVJ/GitHub/stories/common/eltex/IR/Xiaomi/irlocaldata.db";

    // ключ для дешифрации записей
    public static final String KEY = "fd7e915003168929c1a9b0ec32a60788";

    private Map<String, XiaomiTableInfo> tableInfos = new HashMap<>();

    //private String selectCmd = "SELECT * FROM ?;";

    public static void main(String[] args) {
        //connect();
        XiaomiIrDbToMongo handler = new XiaomiIrDbToMongo();
        handler.handle();

        /*
        String str = "tox8lCVn4ynewQesJtKAfADYgJ/LOzl7dwYxnMZ2fVoB2wa0OvwMQ2GL0IJuPzCwxRj96sRpTjWkZ/3PdFG9QxQkJFoR8kSCCgSM2vewRDY=";
        try {
            String result = handler.decryptData(str, KEY);
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    private void handle() {

        Connection conn = null;
         
        tableInfos.put("devicebrands", new XiaomiTableInfo(4));
        tableInfos.put("sqlite_sequence", new XiaomiTableInfo());

        // в расшифрованом массиве json обьектов есть параметр ir_zip_key, значение которого зашифровано (массив импульсов)
        tableInfos.put("mi_ir_tree", new XiaomiTableInfo(6, "ir_zip_key", "ir_zip_key_r"));
        tableInfos.put("mi_remote_ircode", new XiaomiTableInfo());
        tableInfos.put("kk_remote_ircode", new XiaomiTableInfo());
        tableInfos.put("kk_remote_ircode_src", new XiaomiTableInfo(2));

        // в json значения всех параметров (имена кнопок-функций) зашифрованы
        tableInfos.put("xm_remote_ircode", new XiaomiTableInfo(6, "all"));

        tableInfos.put("device", new XiaomiTableInfo(2));
        tableInfos.put("brand", new XiaomiTableInfo(2));
        tableInfos.put("sp", new XiaomiTableInfo());
        tableInfos.put("xm_sp", new XiaomiTableInfo());
        tableInfos.put("city", new XiaomiTableInfo());
        tableInfos.put("xm_city", new XiaomiTableInfo());
        tableInfos.put("lineup", new XiaomiTableInfo());
        tableInfos.put("xm_lineup", new XiaomiTableInfo());

        // в json значения всех параметров (имена кнопок-функций) зашифрованы
        // - json.keySet - получить имена параметров
        tableInfos.put("kk_match", new XiaomiTableInfo(6, "all2"));
        //tableInfos.put("kk_match", new XiaomiTableInfo(6));

        tableInfos.put("xm_match", new XiaomiTableInfo(6, "all"));

        tableInfos.put("mi_ir_merge", new XiaomiTableInfo());
        tableInfos.put("country", new XiaomiTableInfo());
        tableInfos.put("version", new XiaomiTableInfo());

        try {

            // Регистрируем JDBC. В нашем случае - Sqlite.JDBC
            DriverManager.registerDriver(new JDBC());

            conn = DriverManager.getConnection(DB_PATH);

            String tableName;
            Collection<JSONObject> list;
            int size = 0;
            for (Map.Entry<String, XiaomiTableInfo> entry : tableInfos.entrySet()) {
                tableName = entry.getKey();
                System.out.println("\n------- " + tableName + " ------------\n");

                // Create 'tableName' collection
                FileWriter fOut = createDbCollection(tableName);

                list = processTable(conn, tableName, entry.getValue());

                // Save json to DB 'tableName' collection
                saveToDb(fOut, list, tableName);

                size++;
            }

            System.out.println("\ntables = " + size);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(conn);
        }
    }

    private void saveToDb(FileWriter fOut, Collection<JSONObject> list, String tableName) throws Exception {
        for (JSONObject json: list) {
            fOut.write(json.toString());
            fOut.write("\n");
        }
        fOut.flush();
        fOut.close();
    }

    private static final String FILE_PREFIX = "/home/svj/tmp/xiaomi/";

    private FileWriter  createDbCollection(String tableName) throws Exception {
        String fileName = FILE_PREFIX + tableName + ".txt";
        FileWriter fOut = new FileWriter(fileName);

        return fOut;
    }

    private Collection<JSONObject> processTable(Connection conn, String tableName, XiaomiTableInfo tableInfo) {

        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData;
        Collection<JSONObject> result = new LinkedList<>();

        try {
            String str = "SELECT * FROM " + tableName + ";";
            //statement.setString(1, tableName);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(str);

            metaData = resultSet.getMetaData();

            int columnCount = metaData.getColumnCount();



            String content, jsonStr;
            int size = 0;
            int number = tableInfo.getCryptColumn();
            while (resultSet.next()) {

                size++;
                //if (size > 100) break;
                //if (size > 2) break;
                //if (size < 1740) continue;

                JSONObject json = new JSONObject();
                Object value;
                for (int ic=1; ic<=columnCount; ic++) {
                    if (ic != number) {
                        //System.out.print(resultSet.getObject(ic) + " | ");
                        value = getRealValue(ic, resultSet, metaData);
                    } else {
                        // это поле с шифрованными данными
                        content = resultSet.getString(number);
                        jsonStr = decryptData(content, KEY);
                        if (jsonStr.startsWith("[")) {
                            value = new JSONArray(jsonStr);
                        } else {
                            value = new JSONObject(jsonStr);
                        }

                        // проверяем наличие шифрации внутренних параметров расшифрованного json
                        if (tableInfo.getJsonCryptParamName() != null) {
                            checkCryptParam(value, tableInfo.getJsonCryptParamName());
                        }
                        if (tableInfo.getJsonCryptParamName2() != null) {
                            checkCryptParam(value, tableInfo.getJsonCryptParamName2());
                        }
                        //System.out.println(value);
                    }
                    json.put(metaData.getColumnName(ic), value);
                }

                result.add(json);
                //System.out.println();
                //System.out.println("json = " + json);

            }
            //System.out.println("\ntable size = " + size);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSql(statement, resultSet);
        }
        return result;
    }

    private void checkCryptParam(Object jsonObj, String jsonCryptParamName) throws Exception {
        String str, result,  value;
        JSONObject json;
        if (jsonCryptParamName.equals("all")) {
            // расшифровать значение всех параметров
            json = (JSONObject) jsonObj;
            Collection<String> params = json.keySet();
            for (String pName: params) {
                result = decryptData(json.getString(pName), KEY);
                json.put(pName, result);
            }
        } else if (jsonCryptParamName.equals("all2")) {
            // расшифровать значение только тех параметров, имена которых НЕ числа
            //System.out.println("  --- jsonObj = " + jsonObj);
            json = (JSONObject) jsonObj;
            Collection<String> params = json.keySet();
            for (String pName: params) {
                //System.out.println("   --- pName = " + pName);
                if (noneDigit(pName)) {
                    value = json.getString(pName);
                    result = decryptData(value, KEY);
                    //System.out.println("   --- value = " + value);
                    //System.out.println("   --- result = " + result);
                    json.put(pName, result);
                }
            }

        } else {
            // только один параметр
            if (jsonObj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonObj;
                Iterator it = jsonArray.iterator();
                Object obj;
                while (it.hasNext()) {
                    obj = it.next();
                    json = (JSONObject) obj;
                    str = (String) json.opt(jsonCryptParamName);
                    if (str != null) {
                        result = decryptData(str, KEY);
                        json.put(jsonCryptParamName, result);
                    }
                }
            } else {
                json = (JSONObject) jsonObj;
                str = json.getString(jsonCryptParamName);
                result = decryptData(str, KEY);
                json.put(jsonCryptParamName, result);
            }
        }
    }

    private boolean noneDigit(String pName) {
        boolean result;

        try {
            Integer it = Integer.decode(pName);
            //System.out.println("  --- pName = '" + pName + "'; it = " + it);
            result = false;
        } catch (Exception e) {
            result = true;
        }
        //System.out.println("  --- pName = '" + pName + "'; noneDigit = " + result);
        return result;
    }

    private Object getRealValue(int columnNumber, ResultSet resultSet, ResultSetMetaData metaData) throws Exception {
        Object result;
        switch (metaData.getColumnClassName(columnNumber)) {
            case("java.lang.String") :
                result = resultSet.getString(columnNumber);
                break;

            case("java.lang.Integer") :
                result = resultSet.getInt(columnNumber);
                break;

            default:
                result = resultSet.getObject(columnNumber);
                break;

        }
        return result;
    }

    private String parseContent(String content) {
        return null;
    }


    public String decompress(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(bArr));
        byte[] bArr2 = new byte[256];
        while (true) {
            int read = gZIPInputStream.read(bArr2);
            if (read < 0) {
                gZIPInputStream.close();
                return new String(byteArrayOutputStream.toByteArray());
            }
            byteArrayOutputStream.write(bArr2, 0, read);
        }
    }

    public String decryptData(String data, String key)  {
        if (data == null || key == null) {
            return null;
        }
        try {
            byte[] bytes = data.getBytes("UTF-8");
            byte[] bytes2 = key.getBytes("UTF-8");
            if (bytes != null && bytes2 != null) {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bytes2, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
                cipher.init(2, secretKeySpec);
                //byte[] decode = Base64.decode(bytes, 0);
                byte[] decode = Base64.decode(bytes);
                return decompress(cipher.doFinal(decode));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error. decryptData: data = " + data + "; key = " + key);
            return null;
        }
    }

    /**
     * Получить список имен таблиц в БД.
     */
    private Collection<String>  getTableNames(Connection conn) {

        Statement statement = null;
        ResultSet resultSet = null;

        Collection<String> result = new ArrayList<>();

        try {
            statement = conn.createStatement();

            // получаем записи из одной колонки - имя таблицы
            resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

            while (resultSet.next()) {
                result.add(resultSet.getString(1));
                //System.out.println(" - " + resultSet.getObject(1));
            }

            /*
            // Получить информацию о колонках
            metaData = resultSet.getMetaData();

            // - кол-во колонок
            int columnCount = metaData.getColumnCount();
            System.out.println("-- getColumnCount = " + columnCount);

            // - Название колонки и тип данных
            for (int ic=1; ic<=columnCount; ic++) {
                System.out.println(" - " + metaData.getColumnName(ic) + " / " + metaData.getColumnClassName(ic));
            }
            System.out.println();
            */

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSql(statement, resultSet);
        }
        return result;
    }


    private void closeConn ( Connection conn )
    {
        try
        {
            // Close the connection
            if ( conn != null )            conn.close();
        }  catch ( Exception e2 ) {
            e2.printStackTrace();
        }
    }

    private void closeSql ( Statement stmt, ResultSet rs )
    {
        if ( rs != null )
        {
            try
            {
                rs.close();
            } catch ( Exception ex )   {
                ex.printStackTrace ();
            }
        }

        if ( stmt != null )
        {
            try
            {
                stmt.close();
            } catch ( Exception ex )   {
                ex.printStackTrace ();
            }
        }
    }

}
