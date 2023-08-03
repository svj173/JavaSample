package swing.editor;

import javax.swing.text.SimpleAttributeSet;

/**
 * <BR> User: Zhiganov
 * <BR> Date: 25.09.2007
 * <BR> Time: 17:49:57
 */
public class StyleSet extends SimpleAttributeSet 
{
    private String name;

    public StyleSet(String name) {
        super();
        this.name   = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
