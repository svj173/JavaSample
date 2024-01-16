package svj.db;

import org.apache.xmlbeans.impl.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.zip.GZIPInputStream;

/**
 * Чтение базы ИК пультов от Xiaomi.
 * Трудность в том, что база представляет собйо файл sqlite, в котором каждая запись упакована и зашифрована, парвда ключ есть.
 * Это пилот, который будет извлекать все эти записи. Результат - в json или csv.
 */
public class XiaomiIrDb {

    public static final String DB_PATH = "jdbc:sqlite:/media/svj/_disk2/home/svj/Serg/stories/common/eltex/IR/Xiaomi/irlocaldata.db";

    public static final String KEY = "fd7e915003168929c1a9b0ec32a60788";

    public static void main(String[] args) {
        //connect();
        XiaomiIrDb handler = new XiaomiIrDb();
        handler.handle();

        String str;
        /*
        // Дешифрация текстов внутри шифра

        //str = "tox8lCVn4ynewQesJtKAfADYgJ/LOzl7dwYxnMZ2fVoB2wa0OvwMQ2GL0IJuPzCwxRj96sRpTjWkZ/3PdFG9QxQkJFoR8kSCCgSM2vewRDY=";
        //str = "IjWQ0qwMnmYwQ1lsXW69NwKoghKEhM7MHtk19Ucqdiz98CoN0KqtvqAUYRcjnzZMuOe2Nb72nCtuXWsaHSaHtq3hnLQLiABLfBH2F5yo1SJ5K3VKh2zVPWXZ9LPWEN1698Z9yDMGQjmoZm07aRvADIX22ZWW5+IQxXR3ynEHssWHE8bjSTiQ2+UhQUYXdyAnjZcae7M6rqFbm/x6t52xzg==";

        //str = "tox8lCVn4ynewQesJtKAfN9j3aatfFPi68F2s1aA+IpXmhanTxC0ompop++zmtkhyKSNAbfV3NiJMbEOWXzT9iHrNTcabWAcnyNmXIBNBPY=";

        str = "tox8lCVn4ynewQesJtKAfADYgJ/LOzl7dwYxnMZ2fVoB2wa0OvwMQ2GL0IJuPzCwxRj96sRpTjWkZ/3PdFG9QxQkJFoR8kSCCgSM2vewRDY=";

        try {
            String result = handler.decryptData(str, KEY);
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //*/
    }

    private void handle() {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData;

        try {
            conn = DriverManager.getConnection(DB_PATH);

            statement = conn.createStatement();

            /*
            // получаем список таблиц - записи из однйо колонки - имя таблицы
            resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");

            while (resultSet.next()) {
                System.out.println(" - " + resultSet.getObject(1));
            }
            */

            /*
            Имена таблиц - 20 шт
 - devicebrands
 - sqlite_sequence
 - mi_ir_tree
 - mi_remote_ircode
 - kk_remote_ircode
 - kk_remote_ircode_src
 - device
 - brand
 - sp
 - xm_sp
 - city
 - xm_city
 - lineup
 - xm_lineup
 - kk_match
 - xm_match
 - xm_remote_ircode
 - mi_ir_merge
 - country
 - version
             */

            String str = "SELECT * FROM kk_match;";
            resultSet = statement.executeQuery(str);
            metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("-- getColumnCount = " + columnCount);
            System.out.println();

            // колонки и тип данных в них
            for (int ic=1; ic<=columnCount; ic++) {
                System.out.println(" - " + metaData.getColumnName(ic) + " / " + metaData.getColumnClassName(ic));
            }
            System.out.println();
            System.out.println();

            // header - имена колонок одной строкой
            for (int ic=1; ic<=columnCount; ic++) {
                System.out.print(metaData.getColumnName(ic) + " | ");
            }
            System.out.println();

            String content;
            int size = 0;
            int number = 6;
            while (resultSet.next()) {
                size++;
                //if (size > 5) break;
                //if (size < 1740) continue;

                for (int ic=1; ic<=columnCount; ic++) {
                    if (ic != number) {
                        System.out.print(resultSet.getObject(ic) + " | ");
                    } else {
                        content = resultSet.getString(number);
                        System.out.println(decryptData(content, KEY));
                    }
                }
                System.out.println();
            }
            System.out.println("\ntable size = " + size);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    private String decompress(byte[] bArr) throws IOException {
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

    private String decryptData(String data, String key) throws Exception {
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
