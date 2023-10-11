/**
 * This program reads an unknown number of lines from a file and processes them.
 * The lines are then sorted based on their lexographical content, ignoring whitespace.
 * Those lines are then printed to the user in their original form, based on their sorted order.
 *
 * Filename: SameNumberOfCharOnLine.java
 *
 * Version: 1.0
 * Revision Log:
 *              Version 1.0 - Initial creation. Sept 15, 2023
 */


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SameNumberOfCharOnLine {

    private static String[] wordsFromFile;
    private static String[] sortedWords;

    /**
     * This method attempts to read a text file containing alphanumeric strings
     * And store the lines into an array of Strings called <em>wordsFromFile</em>.
     * Those strings will also be processed using <b>processLine</b> and the
     * result stored in matching indeces in the <em>sortedWords</em> array.
     * @param filename - The file containing the lines to be processed
     *
     * @exception FileNotFoundException This method will throw a FileNotFoundException
     * if the file specified by <em>filename</em> is not accessible from the current
     * working directory.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void loadWords(String filename){
        String[] buffer = new String[1000];
        File obj;
        int x, numberOfWords = 0;
        Scanner reader;

        try{
            //Create a reference to the file containing the list of words
            obj = new File(filename);
            //Create a Scanner object to read each line
            reader = new Scanner(obj);
            while (reader.hasNext()){//While you have not exhausted the file
                buffer[numberOfWords] = reader.nextLine(); //Read the next line and buffer it
                numberOfWords++;//While keeping track of the total number of words read
            }
            reader.close();
            //Declare two new String arrays to accommodate the number
            //of words found in the file
            wordsFromFile = new String[numberOfWords];
            sortedWords = new String[numberOfWords];
            for(x = 0;x < numberOfWords; x++){
                //Store each word in the static wordsFromFile array
                wordsFromFile[x] = (buffer[x]);
                //And at the matching index store the transformed string
                //in the static sortedWords array
                sortedWords[x] = processLine(buffer[x]);
            }
        }
        //If the file could not be found, print the message to the user.
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        finally{
            //In any case, tell the user how many strings were loaded from file.
            System.out.printf("Number of strings loaded: %d\n\n", numberOfWords);
        }
    }

    /**
     * This method takes a lowercase word and employs the merge sort algorithm
     * to build a new string with the same letters in alphabetical order
     * @param word - A String representing the word to be sorted
     * @return newWord - A String made up of the sorted characters of <em>word.</em>
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static String sortWord(String word){

        int midPoint, count1, count2;
        String left, right, newWord = "";

        //If the word to be sorted is 1 character or shorter it is already sorted
        if (word.length() <= 1){
            //return it as is
            return word;
        }

        //Find the mid point of the word being processed
        midPoint = word.length()/2;

        //Sort the left and right halves of the word with this same function
        left = sortWord(word.substring(0, midPoint));
        right = sortWord(word.substring(midPoint));

        count1 = count2 = 0;

        //Go through the now sorted left and right halves character by character
        while ((count1 < left.length()) && (count2 < right.length())){
            //Compare the current character in each array
            //And add whichever one occurs first to the new word
            if (left.charAt(count1) <= right.charAt(count2)){
                newWord += left.charAt(count1);
                count1++;
            }
            else{
                newWord += right.charAt(count2);
                count2++;
            }
            //Increment the counter for whichever list the last
            //character added was chosen from.
        }

        //If the loop was broken before all characters from
        //The left half of the word were used
        while(count1 < left.length()){
            try {
                //Add the remaining characters to the new word
                newWord += left.charAt(count1);
                count1++;
            }
            catch (IndexOutOfBoundsException e){
                break;
            }
        }

        //Perform the same check for the right half
        while(count2 < right.length()){
            try {
                //And add the remaining characters to the new word
                newWord += right.charAt(count2);
                count2++;
            }
            catch (IndexOutOfBoundsException e){
                break;
            }
        }

        //Return the sorted string.
        return newWord;
    }

    /**
     * This method takes a line of input from the file and returns the line of characters
     * in alphabetical order with no whitespaces. It also converts all characters
     * to lower case.
     * @param line - A String representing the line read from file
     * @return res - A transformed lowercase string with no whitespace and sorted letters
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static String processLine(String line){
        String res = "";
        //Split the string received by white space
        //This will remove all whitespace characters from the final string
        String[] buffer = line.split(" ");
        int x;
        int numWords = buffer.length;
        //Convert each word in the string to lower case
        for (x=0;x<numWords;x++){
            buffer[x] = buffer[x].toLowerCase();
        }
        //And build a new larger string with each component word
        for (x = 0;x<numWords;x++){
            res += buffer[x];
        }
        //Perform a merge sort on the resulting "superstring"
        res = sortWord(res);

        //return the sorted superstring
        return res;
    }

    /**
     * Prints the contents of the two static arrays to the user.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void printWords(){
        int x;

        //Print each entry from the file in both its original
        //and its transformed form
        for(x = 0; x < wordsFromFile.length; x++){
            System.out.println(wordsFromFile[x] + " | " + sortedWords[x]);
        }
    }


    /*public static String[] mergeSortWordArray(String[] arr){
        int count1, count2, midpoint, count3;
        String[] res, left, right;

        if (arr.length <= 1){
            return arr;
        }

        midpoint = arr.length/2;
        left = mergeSortWordArray(Arrays.copyOfRange(arr, 0, midpoint));
        right = mergeSortWordArray(Arrays.copyOfRange(arr, midpoint, arr.length));

        count1 = count2 = count3 = 0;

        res = new String[arr.length];
        while ((count1 < left.length) && (count2 < right.length)){
            if (left[count1].compareTo(right[count2]) <= 0){
                res[count3] = left[count1];
                count1++;
            }
            else{
                res[count3] = right[count2];
                count2++;
            }
            count3++;
        }

        while(count1 < left.length){
            res[count3]  = left[count1];
            count1++;
            count3++;
        }

        while(count2 < right.length){
            res[count3]  = right[count2];
            count2++;
            count3++;
        }

        return res;
    }*/

    /**
     * Sort the contents of both static arrays wordsFromFile and sortedWords
     * based on the content of sortedWords array. Algorithm being employed is
     * an adaptation of selection sort.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void sortWordArray(){
        int count1, count2;
        String hold;

        //Starting from the beginning of the array
        for (count1 = 0; count1 < wordsFromFile.length; count1++){
            //And starting from the last index sorted
            for (count2 = count1+1; count2 < wordsFromFile.length; count2++){
                //If the entry being analysed comes alphabetically before the item
                //being checked in the outer for loop
                if (sortedWords[count2].compareTo(sortedWords[count1]) < 0){

                    //Swap the contents of the array at the given index
                    hold = sortedWords[count2];
                    sortedWords[count2] = sortedWords[count1];
                    sortedWords[count1] = hold;

                    //Perform the same swap in the wordsFromFile array
                    hold = wordsFromFile[count2];
                    wordsFromFile[count2] = wordsFromFile[count1];
                    wordsFromFile[count1] = hold;
                }
                //Otherwise do nothing.
            }
        }
    }

    /**
     * Driver function that calls the read from file, sort and print functions.
     * @param args No command line arguments
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void main(String[] args) {
        //Attempt to read the words from file.
        System.out.println("Loading...");
        loadWords("test.txt");

        //Display the initial contents of the array
        System.out.println("\nArray contents after first read:");
        printWords();
        //Sort the contents
        System.out.println("---SORTING---");
        sortWordArray();

        //Print the newly ordered contents of the file.
        System.out.println("\nArray contents after sorting:");
        printWords();


    }
}