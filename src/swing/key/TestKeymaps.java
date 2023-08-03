package swing.key;


import javax.swing.*;

/**
 * Для всех основных компонент Swing можно получить их InputMap и ActionMap. Методы getInputMap и getActionMap есть в классе JComponent. Проведем небольшое исследование при помощи следующей программы.
 * <BR/> Программа предполагает, что в качестве параметра вызова ей передается имя класса - наследника JComponent. Она выдает список имен действий, имеющихся в ActionMap для объекта этого класса.
 * <p/>
 * Вызвав ее для класса JButton
 * <p/>
 * java.exe TestKeymaps javax.swing.JButton
 * <p/>
 * мы получим такой список имен действий:
 * <p/>
 * pressed
 * released
 * <p/>
 * Для JTextField мы получим более длинный список.
 * В нем, в частности, будут такие имена действий, как copy-to-clipboard, paste- from-clipboard, select-word.
 * Эти имена достаточно мнемоничны и по ним можно определить, какие действия им соответствуют.
 * <BR/>
 * <BR/>
 * Вариант WHEN_FOCUSED наиболее простой и понятный. Соответствующий InputMap определяет комбинации клавиш, действующие тогда, когда компонента имеет фокус.

 Вариант WHEN_ANCESTOR_OF_FOCUSED_COMPONENT означает, что данный InputMap распространяет свое действие на все дочерние компоненты данной компоненты. Его следует использовать тогда, когда нам нужно задать некоторое абстрактное действие, не связанное непосредственно с какой-то компонентой, или действие общее для всех подкомпонент данной компоненты.

 И, наконец, вариант WHEN_IN_FOCUSED_WINDOW означает, что данный InputMap действует для всех компонент окна, в котором расположена данная компонента. Он предназначен для определения каких-то действий с компонентой, не зависящих от того, где находится фокус. Примером являются "горячие клавиши".

 Обычно, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT используют для компонент-контейнеров, например, для панелей, а WHEN_IN_FOCUSED_WINDOW для элементарных компонент — кнопок, текстовых полей и т.п.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.09.2015 10:41
 */
public class TestKeymaps
{
    public static void main ( String[] args )
    {
        if ( args.length == 0 )
        {
            System.out.println ( " Вызов: java TestKeymaps <имя_класса>" );
            return;
        }
        try
        {
            Class cls = Class.forName ( args[ 0 ] );
            JComponent comp = ( JComponent ) cls.newInstance ();
            ActionMap actionMap = comp.getActionMap ();
            System.out.println ( "ActionMap: allKeys =" );
            Object akeys[] = actionMap.allKeys ();
            for ( int i = 0; i < akeys.length; i++ )
            {
                System.out.println ( " - " + akeys[ i ] );
            }
        } catch ( Exception ex )
        {
            ex.printStackTrace ();
        }
        System.exit ( 0 );
    }
}
