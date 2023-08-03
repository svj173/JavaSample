package swing.focus;


/**
 * Здесь по таб бегаем по кнопкам - но в алфавитном порядке их названий, а не в порядке их расположения.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 28.06.2011 9:18:19
 */
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class FocusTraversalExample extends JPanel {

  public FocusTraversalExample() {
    setLayout(new GridLayout(6, 1));
    JButton button1 = new JButton("Texas");
    JButton button2 = new JButton("Vermont");
    JButton button3 = new JButton("Florida");
    JButton button4 = new JButton("Alabama");
    JButton button5 = new JButton("Minnesota");
    JButton button6 = new JButton("California");

    setBackground(Color.lightGray);
    add(button1);
    add(button2);
    add(button3);
    add(button4);
    add(button5);
    add(button6);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Alphabetized Button Focus Traversal");
    frame.setFocusTraversalPolicy(new AlphaButtonPolicy());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(new FocusTraversalExample());
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}

//AlphaButtonPolicy.java
//A custom focus traversal policy that uses alphabetical ordering of button
//labels to determine "next" and "previous" buttons for keyboard traversal.
//

class AlphaButtonPolicy extends FocusTraversalPolicy {

  private SortedMap getSortedButtons(Container focusCycleRoot) {
    if (focusCycleRoot == null) {
      throw new IllegalArgumentException("focusCycleRoot can't be null");
    }
    SortedMap result = new TreeMap(); // Will sort all buttons by text.
    sortRecursive(result, focusCycleRoot);
    return result;
  }

  private void sortRecursive(Map buttons, Container container) {
    for (int i = 0; i < container.getComponentCount(); i++) {
      Component c = container.getComponent(i);
      if (c instanceof JButton) { // Found another button to sort.
        buttons.put(((JButton) c).getText(), c);
      }
      if (c instanceof Container) { // Found a container to search.
        sortRecursive(buttons, (Container) c);
      }
    }
  }

  // The rest of the code implements the FocusTraversalPolicy interface.

  public Component getFirstComponent(Container focusCycleRoot) {
    SortedMap buttons = getSortedButtons(focusCycleRoot);
    if (buttons.isEmpty()) {
      return null;
    }
    return (Component) buttons.get(buttons.firstKey());
  }

  public Component getLastComponent(Container focusCycleRoot) {
    SortedMap buttons = getSortedButtons(focusCycleRoot);
    if (buttons.isEmpty()) {
      return null;
    }
    return (Component) buttons.get(buttons.lastKey());
  }

  public Component getDefaultComponent(Container focusCycleRoot) {
    return getFirstComponent(focusCycleRoot);
  }

  public Component getComponentAfter(Container focusCycleRoot,
      Component aComponent) {
    if (!(aComponent instanceof JButton)) {
      return null;
    }
    SortedMap buttons = getSortedButtons(focusCycleRoot);
    // Find all buttons after the current one.
    String nextName = ((JButton) aComponent).getText() + "\0";
    SortedMap nextButtons = buttons.tailMap(nextName);
    if (nextButtons.isEmpty()) { // Wrapped back to beginning
      if (!buttons.isEmpty()) {
        return (Component) buttons.get(buttons.firstKey());
      }
      return null; // Degenerate case of no buttons.
    }
    return (Component) nextButtons.get(nextButtons.firstKey());
  }

  public Component getComponentBefore(Container focusCycleRoot,
      Component aComponent) {
    if (!(aComponent instanceof JButton)) {
      return null;
    }

    SortedMap buttons = getSortedButtons(focusCycleRoot);
    SortedMap prevButtons = // Find all buttons before this one.
    buttons.headMap(((JButton) aComponent).getText());
    if (prevButtons.isEmpty()) { // Wrapped back to end.
      if (!buttons.isEmpty()) {
        return (Component) buttons.get(buttons.lastKey());
      }
      return null; // Degenerate case of no buttons.
    }
    return (Component) prevButtons.get(prevButtons.lastKey());
  }
}
