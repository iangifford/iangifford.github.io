/**
*This is my code!  It’s goal is to represent a search engine.
*CS312 - Assignment 9
*@author Ian Gifford
*@version 1.0  12/10/2018
*/
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author shado
 */
public class Document
{

    /**
     * Actual text of the document.
     */
    protected String text;

    /**
     * Name of the file.
     */
    protected String name;

    /**
     * Constructor for Document - MUST have a file to build from.
     * @param file - file for the document to represent.
     */
    public Document(String file){
        text = readFile(file);
    }
    
    /**
     * Reads from a file and returns the contents.
     * @param location - File location to read from.
     * @return - String contents of the file.
     */
    private String readFile(String location)
    {
        Path file = Paths.get(location);
        name = file.getFileName().toString();
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
     * Prints out the text of the file.
     */
    public void printText(){
        System.out.println(text);
    }

    /**
     * Returns the content of the file as a series of words.
     * @return An array of the words of the file.
     */
    public String[] getWords(){
        return text.split("\\s+");
    }
    /**
     * Overrides the toString with the name of the file.
     * @return the name of the file.
     */
    @Override
    public String toString(){
        return name;
    }
}
