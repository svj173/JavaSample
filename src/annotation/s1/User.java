package annotation.s1;


import java.util.ArrayList;
import java.util.List;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:11:51
 */
public class User {
    public static enum Permission {
        USER_MANAGEMENT, CONTENT_MANAGEMENT
    }

    private List permissions;

    public List getPermissions() {
        return new ArrayList (permissions);
    }

    // ...
}