package jython;

/**
 * Created by IntelliJ IDEA.
 * User: svj
 * Date: 23.01.2007
 * Time: 18:09:34
 */
public class MyObject 
{
    private String  name;
    private int code;


    public MyObject ( String name, int code ) {
        this.name = name;
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toString ()
    {
        return "MyObject : name = " + name + ", code = " + code;
    }

}
