package swing.tree.path;


/**
 * Строки вида word1/word2/.../wordn - в файле.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 22.01.2013 13:14
 */
import java.util.*;
import java.io.*;

public class WordNodes
{
    Map<String, List<String>> map = new HashMap<String, List<String>>();

    void readInputFile ( String filename) throws IOException, FileNotFoundException
    {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try
        {
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
            {
                for (String word: line.split("/"))
                {
                    List<String> list = map.get(word);
                    if (list == null)
                    {
                        list = new ArrayList<String>();
                        map.put(word, list);
                    }
                    list.add(line);
                }
            }
        } finally {
            bufferedReader.close();
        }
    }

    void run() throws IOException, FileNotFoundException
    {
        readInputFile("file.txt");

        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        try
        {
            while (true)
            {
                String word = bufferedReader.readLine();
                List<String> lines = map.get(word);
                if (lines == null)
                {
                    System.out.println("Word not found.");
                }
                else
                {
                    for (String line: lines)
                    {
                        System.out.println(line);
                    }
                }
            }
        } finally {
            bufferedReader.close();
        }
    }

    public static void main(String[] args) throws Exception
    {
        new WordNodes().run();
    }

}