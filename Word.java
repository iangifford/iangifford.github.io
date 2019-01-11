
/**
 *This is my code!  It’s goal is to represent a search engine.
 *CS312 - Assignment 9
 *
 * @author Ian Gifford
 * @version 1.0 12/10/2018
 */
import java.util.Objects;

public class Word
{

    /**
     * Cleaned up version of the value.
     */
    protected String word;

    /**
     * Unclean, original value that this represents.
     */
    protected String trueString;

    /**
     * Default constructor MUST provide a word.
     *
     * @param value - Word for this to represent.
     */
    public Word(String value)
    {
        trueString = value;
        word = cleanString(value);

    }

    /**
     * Removes punctuation from a string.
     *
     * @param s String to remove punctuation from.
     * @return Cleaned version.
     */
    private String cleanString(String s)
    {
        return s.toLowerCase().replaceAll("[^a-zA-Z]", "");
    }

    /**
     * Override on the default hashcode. This MUST happen or else the hashmap
     * and hashset are completely meaningless, as the java default hashcode does
     * not work for an inverted index and I would fail the assignment.
     *
     * @return Hashcode of the object, that fulfills the requirements.
     */
    @Override
    public int hashCode()
    {
        int sum = 0;
        for (int i = 0; i < word.length(); i++)
        {
            sum += word.toLowerCase().charAt(i);
        }
        return sum;
    }

    /**
     * Override on .equals. Says two strings are equal if the cleaned versions
     * are equal.
     *
     * @param obj Word to check if equal to.
     * @return True if the cleaned versions are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() == this.getClass())
        {
            Word temp = (Word) obj;
            if (temp.word.equals(this.word))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Override on toString
     *
     * @return the true, unclean version of the word.
     */
    @Override
    public String toString()
    {
        return trueString;
    }
}
