
/**
 *This is my code!  It’s goal is to represent a search engine.
 *CS312 - Assignment 9
 *
 * @author Ian Gifford
 * @version 1.0 12/10/2018
 */
import java.util.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.Paths;

public class InvertedIndex
{

    /**
     * Stoplist for the search index.
     */
    protected HashSet<Word> stopList;

    /**
     * Actual data contained by the documents.
     */
    protected HashMap<Word, HashSet<Document>> documents;

    /**
     * Constructor for the index. MUST provide a stopList.
     *
     * @param stopLoc - Location of the stopList
     */
    public InvertedIndex(String stopLoc)
    {
        documents = new HashMap<>();
        stopList = new HashSet<>();
        String[] data = readFile(stopLoc).split("\\W+");
        ArrayList<Word> temp = new ArrayList<>();
        for (String a : data)
        {
            a = a.trim();
            if (a.length() > 0)
            {
                Word val = new Word(a);
                temp.add(val);
            }
        }
        stopList.addAll(temp);
    }

    /**
     * reads the data from a file.
     *
     * @param location - Location to read from.
     * @return - A string of all of the data of the file.
     */
    private String readFile(String location)
    {
        Path file = Paths.get(location);
        try
        {
            List<String> lines = Files.readAllLines(file);
            String sum = "";
            for (String a : lines)
            {
                sum += a + "\n";
            }
            return sum;
        } catch (Exception e)
        {
            System.err.println("Invalid file path at " + location);
            System.err.println(e);
            System.exit(1);
        }
        return "";
    }

    /**
     * Adds a document to the list, indexed properly.
     *
     * @param d - Document to add to the list.
     */
    public void addDocument(Document d)
    {
        for (String a : d.getWords())
        {
            Word data = new Word(a);
            if (!stopList.contains(data))
            {
                if (!documents.containsKey(data))
                {
                    documents.put(data, new HashSet<>());
                }
                documents.get(data).add(d);
            }
        }
    }

    /**
     * Prints out the indexes of the hashmap.
     */
    public void dumpIndex()
    {
        System.out.println("Keys:");
        for (Word a : documents.keySet())
        {
            System.out.println(a);
        }
        System.out.println();
    }

    /**
     * Returns the results of a single query.
     * @param query - query word to look for.
     * @return set of documents containing that word.
     */
    private HashSet<Document> singleWordQuery(Word query)
    {
        return documents.get(query);
    }

    /**
     * Returns the documents that contain all of the words of the query.
     * @param line - Words to search for
     * @return - Set of documents containing all of the words.
     */
    public HashSet<Document> multiWordQuery(String line)
    {
        HashSet<Document> total = new HashSet<>();

        String[] temp = line.split("\\s+");
        ArrayList<String> holder = new ArrayList<>();
        for (String a : temp)
        {
            if (!stopList.contains(a))
            {
                holder.add(a);
            }
        }
        String[] words = holder.toArray(new String[holder.size()]);

        if (words.length == 0)
        {
            return total;
        }
        total = singleWordQuery(new Word(words[0]));
        for (int i = 1; i < words.length; i++)
        {
            total.retainAll(singleWordQuery(new Word(words[i])));
        }

        return total;
    }

}
