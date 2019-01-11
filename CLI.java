
/**
 * This is my code! It provides the CLI for the inverted index.
 * CS 312 - Assignment 9
 *
 * @author Dave Binkley
 * @version 2.0 11/18/2018
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * The command line interface for the search engine.
 */
public class CLI
{

    private static final String DEBUG_COMMAND = "@@debug";

    /**
     * Build an inverted index based on the command line arguments and then
     * accept queries from the standard in.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args)
    {
        if (args.length < 2 || ("-d".equals(args[0]) && args.length < 3))
        {
            System.out.println("Usage:java CLI [-d] stoplist documents");
            System.exit(-1);
        }

        Boolean includeDocs = "-d".equals(args[0]);
        int start = includeDocs ? 2 : 1;
        InvertedIndex ii = new InvertedIndex(args[start - 1]);
        long startTime = System.currentTimeMillis();

        for (int i = start; i < args.length; i++)
        {
            Document d = new Document(args[i]);
            ii.addDocument(d);

        }
        long buildStop = System.currentTimeMillis();
        long buildTime = buildStop - startTime;
        System.out.println("@@build time" + buildTime + "ms");
        try
        {
            BufferedReader br = new BufferedReader
        (new InputStreamReader(System.in));

            for (String line = br.readLine(); 
                    line != null; line = br.readLine())
            {
                if (DEBUG_COMMAND.equals(line))
                {
                    ii.dumpIndex();
                } else
                {
                    HashSet<Document> docs = ii.multiWordQuery(line);
                    System.out.println("--- found in " + 
                            (docs==null? 0 : docs.size()) + " documents");
                    if(docs!=null){
                        for(Document d : docs)
                            System.out.print(d.toString() + ",");
                        System.out.println("");
                        if(includeDocs){
                            for(Document d: docs)
                                d.printText();
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception ex){
            System.err.println("ah sorry but " + ex);
            ex.printStackTrace();
        }
        
        long stopTime = System.currentTimeMillis();
        long queryTime = stopTime - buildStop;
        System.out.println("@@query time " + queryTime + "ms");
    }
}
