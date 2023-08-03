    Скроллинг в дереве.
    
Суть    - извне выбираем элемент дерева, но он находистя вне видимости экрана.
Задача  - при выборке чтобы скроллинг перемещал дерево, чтобы выбранный элемент стало видно.

In addition to using a JScrollPane for scrolling, you can manually scroll the visible content
in the scrolling region. Use the public void scrollPathToVisible(TreePath path) and public
void scrollRowToVisible(int row) methods to move a particular tree path or row into some
part of the visible area. The row of a node indicates the number of nodes above the current
node to the top of the tree. This differs from the level of the tree, which is the number of ancestors
(or parent nodes) a node has.

scrollPathToVisible(TreePath path)
 -- Помогло. Отображает в самом низу окна скроллинга. Только если имя - длинное, то происходит и сдвиг дерева вправо.
