package xml.document;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2011 14:38:25
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class CreateDocumentFromFile {

    public static void main(String[] args) {
        try{
        // получаем xml парсер с настройками по умолчанию
        DocumentBuilder xml= DocumentBuilderFactory.
                               newInstance().newDocumentBuilder();

        // разбираем demo.xml и создаем Document
        Document doc=xml.parse(new File("demo.xml"));
        // корневой элемент документа
        Element rootel=doc.getDocumentElement();

        //-----------------------------------------
        // долее можно поиграться с методами
        // DOM объектов

        // имя корневого элемента
        System.out.println(rootel.getNodeName());

        // список имен дочерних элементов
        System.out.println("Child elements: ");
        NodeList lst= rootel.getChildNodes();
        for(int i=0; i<lst.getLength();i++  )
            System.out.println(lst.item(i).getNodeName()+" ");

        // список имен дочерних элементов и их содержимого
        System.out.println("Child elements: ");
        Node el=rootel.getFirstChild();
        do{
            System.out.println(el.getNodeName()+": "+ el.getTextContent());
            el=el.getNextSibling();
        }while(el!=null);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
