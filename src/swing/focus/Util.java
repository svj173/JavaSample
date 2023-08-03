package swing.focus;


import javax.swing.*;
import java.awt.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:44:47
 */
public class Util {
  /**
   * Requests focus for a component. If that's not possible it's
   * {@link FocusTraversalPolicy}is checked. If that doesn't work all it's
   * children is recursively checked with this method.
   *
   * @param component the component to request focus for
   * @return the component which has focus or probably will obtain focus, null
   *         if no component will receive focus
   */
  public static Component smartRequestFocus(Component component) {
    if (requestFocus(component))
      return component;

    if (component instanceof JComponent) {
      FocusTraversalPolicy policy = ((JComponent) component).getFocusTraversalPolicy();

      if (policy != null) {
        Component focusComponent = policy.getDefaultComponent((Container) component);

        if (focusComponent != null && requestFocus(focusComponent)) {
          return focusComponent;
        }
      }
    }

    if (component instanceof Container) {
      Component[] children = ((Container) component).getComponents();

      for (int i = 0; i < children.length; i++) {
        component = smartRequestFocus(children[i]);

        if (component != null)
          return component;
      }
    }

    return null;
  }
  /**
   * Requests focus unless the component already has focus. For some weird
   * reason calling {@link Component#requestFocusInWindow()}when the
   * component is focus owner changes focus owner to another component!
   *
   * @param component the component to request focus for
   * @return true if the component has focus or probably will get focus,
   *         otherwise false
   */
  public static boolean requestFocus(Component component) {
    /*
     * System.out.println("Owner: " +
     * System.identityHashCode(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()) + ", " +
     * System.identityHashCode(component) + ", " +
     * (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() ==
     * component));
     */
    return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == component ||
           component.requestFocusInWindow();
  }
    
}

