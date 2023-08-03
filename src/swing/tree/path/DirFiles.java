package swing.tree.path;


import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Дерево из директорий.
 * <BR/> Здесь уровень дерева определяется уровнем поддиректории, а не структурой слова.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.01.2013 13:34
 */
public class DirFiles
{
    protected MutableTreeNode buildTree ( String path)
    {
        JTree treeObj;

            File file = new File (path);

            if (!file.exists() || !file.isDirectory()) return null;

        MutableTreeNode treeItem = new DefaultMutableTreeNode ();
            treeItem.setUserObject ( file.getName() );

        // Стек строковых path
            List<String> pathsStack = new LinkedList<String> ();
        // Массив уровней - замаплен на строковый path
            Map<String,Integer> pathsIndexes = new HashMap<String,Integer>();
        // Массив строковых path - замаплен на соовтетсвующие обьекты дерева.
            Map<String,MutableTreeNode> pathsTrees = new HashMap<String,MutableTreeNode> ();

            pathsStack.add(path);
            pathsTrees.put(path,treeItem);

            do {
                String _path = pathsStack.get(pathsStack.size()-1);
                MutableTreeNode _tree = pathsTrees.get(_path);

                File dir = new File(_path);
                File[] files =  dir.listFiles();

                Integer index=pathsIndexes.get(_path);
                if (index == null) index=0;

                Boolean breaked = false;

                if (index < files.length) {
                    for (int i=index;i<files.length;i++) {
                        if (files[i].isDirectory()) {
                            MutableTreeNode _treeItem = new DefaultMutableTreeNode();
                            _treeItem.setParent ( _tree );
                            _treeItem.setUserObject(files[i].getName());

                            pathsStack.add(files[i].getAbsolutePath());
                            pathsTrees.put(files[i].getAbsolutePath(), _treeItem);
                            pathsIndexes.put(_path, ++i);

                            breaked = true;
                            break;
                        }
                    }
                }

                if (breaked) continue;

                pathsStack.remove(pathsStack.size()-1);
                pathsTrees.remove(_path);
                pathsIndexes.remove(_path);
                if (pathsStack.size()==0) {
                    break;
                }
            } while(true);

            //treeItem.setExpanded(true);

            pathsStack = null;
            pathsIndexes = null;
            pathsTrees = null;

        return treeItem;
    }

    public static void main(String[] args) throws Exception
    {
    }

}
