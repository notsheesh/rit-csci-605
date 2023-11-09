/**
 * Java implementation of the UNIX wc command.
 * Counts the lines, words, characters and total bytes in a file.
 * Using options -l [lines], -c [bytes], -m [chars] and -w[words] will
 * omit the associated option from the output.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */

import java.io.*;
import java.util.Scanner;
public class WordCount {

    /**
     * Perform the word count operation and return output to the user
     * @param filename the file being counted
     * @param lines a true/false value determining whether or not lines
     *              should be counted.
     * @param words a true/false value determining whether or not words
     *      *              should be counted.
     * @param chars a true/false value determining whether or not chars
     *      *              should be counted.
     * @param bytes a true/false value determining whether or not bytes
     *      *              should be counted.
     *
     * @return A String denoting the results of the WordCount
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static String count_file(String filename, boolean lines, boolean words,
                                    boolean chars, boolean bytes)
    {
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;
        int byteCount = 0;
        char temp, lastChar = 'a';

        try{
            Scanner sc = new Scanner(new FileInputStream(filename));
            // use an empty string as the delimiter so
            // every character will be read by the scanner
            sc.useDelimiter("");
            if(sc.hasNext()){
                // If the file isn't empty then there must be at least one line
                lineCount += 1;
            }
            while(sc.hasNext()){
                temp = sc.next().toCharArray()[0];
                byteCount++;
                switch(temp){
                    case '\n':
                        lineCount += 1;
                        break;
                    case ' ':
                        // Ensure that multiple consecutive spaces
                        // Are not counted as individual words.
                        if (lastChar != ' '){
                            wordCount += 1;
                        }
                        // no break statement since the default
                        // will also be executed in this case
                    default:
                        // If the char was not a new line, add it
                        // to the char count
                        charCount += 1;
                        break;
                }
                lastChar = temp;
            }
        }
        catch (FileNotFoundException e){
            System.err.printf("File {%s} could not be found.\n", filename);
        }

        String res = "";

        // Build the result string conditionally
        res += lines ? String.format("%d lines ", lineCount) : "";
        res += words ? String.format("%d words ", wordCount) : "";
        res += chars ? String.format("%d chars ", charCount) : "";
        res += bytes ? String.format("%d bytes ", byteCount) : "";
        res += String.format("in: {%s}.", filename);
        return res;

    }

    /**
     * Test the properties of the WordCount class
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void test(){
        System.out.println("wc test.txt -l -w -m -c");
        System.out.println(count_file("test.txt", true, true, true, true));
        System.out.println("wc test.txt -l -w");
        System.out.println(count_file("test.txt", true, true, false, false));
        System.out.println("wc test.txt -w -c");
        System.out.println(count_file("test.txt", false, true, false, true));
        System.out.println("wc test.txt -l -m");
        System.out.println(count_file("test.txt", true, false, true, false));
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        char[] tmp;
        boolean words = true;
        boolean bytes = true;
        boolean chars = true;
        boolean lines = true;
        String filename = null;

        if (args.length >= 1){
            for (String arg : args){
                if(ArgumentParser.isFilename(arg)){
                    filename = arg;
                }
                if(ArgumentParser.isOption(arg)){
                    tmp = arg.toCharArray();
                    switch (tmp[1]) {
                        // Check to see which data values should be omitted
                        case 'w' -> words = false;
                        case 'l' -> lines = false;
                        case 'm' -> chars = false;
                        case 'c' -> bytes = false;
                        default -> {
                        }
                    }
                }
            }
            if (filename == null){
                System.err.println("Error, no filename provided. Please provide" +
                        "a file to count.");
                System.exit(2);
            }
        }
        else{
            System.err.println("Usage: WordCount.java {filename} -options");
            System.exit(1);
        }

        // Print word count to the user.
        System.out.println(count_file(filename, lines, words, chars, bytes));
    }
}