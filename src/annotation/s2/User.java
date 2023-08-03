package annotation.s2;


import java.util.Date;
import java.util.List;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:22:54
 */
public class User {
    Integer id;
    String fio;
    Sex sex;
    Date birthday;
    List cats = null;
    /// и много-много методов getter-ов и setter-ов для указанных выше полей

    public int hashCode() {
        if (id != null) return id;
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        if (id != null && user.id != null)
            return id.equals(user.id);
        return super.equals(obj);
    }

    public String toString() {
        String scats= "none";
        if (cats != null && cats.size() > 0)
            scats = cats.toString();
        return "<"+getClass().getName()+">: id="+id+"; fio="+ fio +
               "; birthday="+ birthday +"; sex="+sex+"; cats="+ scats;
    }
}