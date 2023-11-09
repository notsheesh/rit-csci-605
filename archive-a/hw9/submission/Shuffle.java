import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.util.Iterator;
import java.util.regex.Pattern;
public class Shuffle {

    /**
     * Read a file from memory and store each word in an Object
     * @param filename the source file being read from
     * @return a List of Words representing the contents of the file.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static ArrayList<Word> loadFile(String filename){
        String buffer;
        int n;
        Word tmp;
        ArrayList<Word> data = new ArrayList<>();
        try{
            Scanner in = new Scanner(new FileInputStream(filename));
            in.useDelimiter(Pattern.compile(" "));
            while (in.hasNext()){
                buffer = in.next();
                if (!(buffer.isEmpty())){
                    tmp = new Word(buffer);
                    data.add(tmp);
                }
            }
        }
        catch (FileNotFoundException e){
            System.err.printf("File %s could not be found.\n", filename);
        }
        catch (Exception e){
            System.err.println("Unknown error occurred with loading, details: " + e.getMessage());
        }
        finally{
            System.out.printf("Loaded %d words from file.\n", data.size());
        }
        return data;
    }

    /**
     * Record a list of Words to file
     * @param src the list of Words
     * @param filename the output file to store the list in
     * @return The number of bytes written successfully
     * @throws IOException If an error occurs when reading from file
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static int writeToFile(ArrayList<Word> src, String filename) throws IOException{
        int bytesWritten = 0;
        Iterator<Word> cursor = src.iterator();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        Word temp;

        while(cursor.hasNext()){
            temp = cursor.next();
            bytesWritten += temp.get_value().length();
            out.writeObject(temp);
        }
        out.close();
        return bytesWritten;
    }

    /**
     * Attempt to read the file containing the scrambled data
     * @param filename the name of the file to be unscrambled
     * @return A SortedStorage representing the words of the file in their
     * original order
     * @throws IOException if an error occurs while reading from the file
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static SortedStorage<Word> unscrambleFile(String filename) throws IOException{

        SortedStorage<Word> sorted = new SortedStorage<Word>();
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
        boolean cont = true;
        while(cont){
            try{
                sorted.add((Word)input.readObject());
            }
            catch (ClassNotFoundException e){
                cont = false;
                System.err.println("Cannot recognize class written to file.");
            }
            catch (EOFException e){
                System.out.println("End of file reached.");
                break;
            }
            catch (IOException e){
                System.err.println("Something went wrong. " +
                        "Details: " + e);
                cont = false;
            }
            catch (ListCannotBeModified e){
                cont = false;
                System.err.println("List is being used by another method.");
            }
        }
        return sorted;
    }

    /**
     *
     * Randomizes the order of the contents of an array
     *
     * @param data the list to be randomized
     * @return an ArrayList of words in random order
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static ArrayList<Word> shuffle(ArrayList<Word> data){
        Random rng = new Random();
        Word tmp;
        int nxtIndex;
        ArrayList<Word> shuffled = new ArrayList<>();
        // While the original list is not empty
        while (!(data.isEmpty())){
            // Chose a random index from 0 to the length of the array
            nxtIndex = Math.max(rng.nextInt(), 0) % data.size();
            // Remove that item from the list
            tmp = data.remove(nxtIndex);
            // And add it to the shuffled list
            shuffled.add(tmp);
        }
        System.out.println("Size of shuffled array: " + shuffled.size());
        return shuffled;
    }

    /**
     * Print a list to Std.out
     * @param src the list to be printed.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void printList(ArrayList<Word> src){

        for (Word word : src) {
            System.out.print((word.toString() + "-->"));
        }
        System.out.println("\n------------------");
    }

    /**
     * Test the functionality of the Shuffle class
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void test(){
        ArrayList<Word> original = loadFile("test.txt");
        SortedStorage<Word> storage;
        int bytes = 0;

        // Load a text file and shuffle its content.
        // Output both lines
        System.out.println("Original: ---");
        printList(original);
        ArrayList<Word> output = shuffle(original);
        System.out.println("Shuffled: ---");
        printList(output);

        //Attempting to write the shuffled list to file
        try{
            bytes = writeToFile(output, "output.ser");
        }
        catch (IOException e){
            System.err.println("Problem writing to file.");
        }
        finally{
            // Let the user know how many bytes were written to file successfully.
            System.out.printf("Bytes written: %d\n\n", bytes);
        }

        // Printing the shuffled list in sorted order.
        try{
            storage = unscrambleFile("output.ser");
            StorageIterator<Word> iter = storage.iterator();
            iter.printList();
        }
        catch (IOException e){
            System.err.println("Error when attempting to read from file.");
        }
    }

    public static void main(String[] args){
        String filename = "";
        ArrayList<Word> data;
        char op = '\0';
        if (args.length != 2){
            System.err.println("Usage: java Shuffle -operation {input_file}");
        }
        for (String arg : args){
            // Attempt to find the required parameters from the
            // function call
            if (ArgumentParser.isFilename(arg)){
                filename = arg;
            }
            if (ArgumentParser.isOption(arg)){
                if (arg.compareTo("-descramble") == 0){
                    // Set the program to descramble
                    op = 'd';
                }
                else if (arg.compareTo("-scramble") == 0){
                    // Set the program to scramble
                    op = 's';
                }
            }
        }
        if (filename.isEmpty()){
            System.err.println("Invalid parameters.");
            System.err.println("Usage: java Shuffle -operation {input_file}");
        }
        switch(op){

            case 's':
                System.out.println("Scrambling");
                data = loadFile(filename);
                if(!data.isEmpty()){
                    // Shuffle and record the list
                    printList(shuffle(data));
                }
                else{
                    System.err.println("Empty list.");
                }
                break;
            case 'd':
                System.out.println("Unscrambling");
                try{
                    // Take advantage of the "always-sorted" property to
                    // resort the list
                    SortedStorage<Word> output = unscrambleFile(filename);
                    StorageIterator<Word> iter = output.iterator();
                    // Print the sorted list to the user
                    iter.printList();
                }
                catch (IOException e){
                    System.err.println("An error occurred while attempting" +
                            " to read from file.");
                }
                break;
            default:
                System.err.println("Invalid options presented.");
                break;
        }
    }
}
