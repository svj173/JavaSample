package swing.list;


/**
 * Adding and Removing an Item in a JList Component
 * <BR/>  The default model for a list does not allow the addition and removal of items. The list must be created with a DefaultListModel.
 *
 * <BR/> User: svj
 * <BR/> Date: 30.07.2010 18:26:41
 */
public class ListDemo1
{
    /*

// Create a list that allows adds and removes
DefaultListModel model = new DefaultListModel();
JList list = new JList(model);
// Initialize the list with items
String[] items = {"A", "B", "C", "D"};
for (int i=0; i<items.length; i++)
{
 model.add(i, items[i]);
 }
 
 // Append an item
 int pos = list.getModel().getSize();
 model.add(pos, "E");
 // Insert an item at the beginning pos = 0; model.add(pos, "a");

This method replaces an item:
COPY
// Replace the 2nd item
pos = 1;
model.set(pos, "b");

These methods are used to remove items:
COPY
// Remove the first item
pos = 0;
model.remove(pos);
// Remove the last item
pos = model.getSize()-1;
if (pos >= 0) { model.remove(pos); }
// Remove all items
model.clear();

     */
}
