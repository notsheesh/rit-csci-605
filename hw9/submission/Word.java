/**
 * Basic class Word representing a word from a file
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */

import java.io.Serializable;

public class Word implements Comparable<Word>, Serializable {

    private final int id;

    private static final long serialVersionID = 123094101230L;
    private final String value;
    private static int numWords = 0;

    /**
     * Basic constructor for a word class
     * @param value the string representing the word
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Word(String value){

        // Keep track of the total number of words
        numWords += 1;
        // Assign instance prarameters
        this.id = numWords;
        this.value = value;
    }

    /**
     * Compare two Words to each other based on ID
     * @param other the object to be compared.
     * @return the difference in IDs between the words
     */
    public int compareTo(Word other){
        return this.id - other.id;
    }

    public String get_value(){
        return this.value;
    }

    public String toString(){
        return this.value;
    }
}
