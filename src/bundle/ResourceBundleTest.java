package bundle;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 01.04.2013 9:43
 */
import java.util.Enumeration;
import java.util.ResourceBundle;


public class ResourceBundleTest {

	public static void main(String[] args) {

		ResourceBundle rb = ResourceBundle.getBundle ( "bundle.mybundle"); // путь где лежат файлы bundle
		Enumeration <String> keys = rb.getKeys();
		while ( keys.hasMoreElements() )
        {
			String key = keys.nextElement();
			String value = rb.getString(key);
			System.out.println(key + ": " + value);
		}
	}

}