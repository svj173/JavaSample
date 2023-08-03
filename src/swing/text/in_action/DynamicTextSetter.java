package swing.text.in_action;


import javax.swing.text.JTextComponent;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 19.07.2010 13:53:26
 */
public class DynamicTextSetter implements Runnable
{
    private JTextComponent textComponent;
    private String text;

    public DynamicTextSetter ( JTextComponent textComponent, String text )
    {
        this.textComponent = textComponent;
        this.text = text;
    }

    public void run ()
    {
        textComponent.setText ( text );
    }

}
