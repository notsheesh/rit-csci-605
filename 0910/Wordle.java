
/**
 * This program implements the popular internet game WORDLE, where a random 
 * 5-letter English word is chosen
 * and users have 6 chances to guess it.
 * Filename: Wordle.java
 * Version: 1.0
 * Revision Log:
 *      Version 1.0 - Initial Creation. Date: Sep 5, 2023
 *      Version 2.0 - Minor changes in method signatures. Date: Sep 9, 2023
 *      Version 3.0 - Final changes in formatting. Date: Sep 10, 2023
 */

import java.util.Scanner;  // Handle user input
import java.util.Random;  // Handles randomization of solution
import java.io.File;  // Handles reading from file
import java.io.FileNotFoundException;  // Exception in case file can't be loaded

/**
 * This class contains the methods used to implement a text-based WORDLE
 * clone.<br>
 * Original game seen
 * <a href="https: // www.nytimes.com/games/wordle/index.html">here</a>
 */
public class Wordle {

    private static final int WORD_LENGTH = 5;
    private static final int NUM_TRIES = 5;
    private static String[] dictionary;
    private static String fileName = "dictionary.txt";
    private static boolean DEBUG = false;

    /**
     * This function loads the valid potential guesses from a file in secondary
     * storage into the static String array <em>dictionary</em>
     * 
     * @param filename - The location of the file containing the valid words the
     *                 user could enter.
     *
     * @return <u>true</u> if the dictionary was successfully loaded and
     *         <u>false</u> otherwise.
     * 
     * @exception FileNotFoundException: If the file specified by 
     *                                   <em>filename</em> does not exist, this 
     *                                   method will cease execution and the 
     *                                   static array <em>dictionary</em> will 
     *                                   remain empty.
     * @exception NumberFormatException: If the file specified by 
     *                                   <em>filename</em> is found, but the 
     *                                   first line does not contain a valid 
     *                                   integer, this method will cease 
     *                                   execution and the static array 
     *                                   <em>dictionary</em> will remain empty.
     *
     * @author Kyle Burke, Shreesh Tripathi
     *
     */
    public static boolean isLoadDictionary(String filename) {
        Scanner fileReader;
        int dictLength;
        int count = 0;

        try {
            // Attempt to create a new Scanner that points to the file specified
            // by filename
            fileReader = new Scanner(new File(filename));
            // Attempt to convert the first String in filename to an Integer
            dictLength = Integer.parseInt(fileReader.nextLine());
            // Allocate a memory block with dictLength locations to store the
            // list of valid

            // guesses
            dictionary = new String[dictLength];
            // If you have not reached the end of File filename...
            while (fileReader.hasNextLine()) {
                dictionary[count] = fileReader.nextLine(); // Read the next line 
                                                           // and store it in 
                                                           // the array
                count++; // Increase the index by one
                if (count >= dictLength) { // If more words exist in the file 
                                           // than specified in dictLength
                    break; // Exit the loop
                }
            }
            if (DEBUG) {
                System.out.println("Loaded dictionary: ");
                printDictionary();
            }
            fileReader.close(); // Close the file reader
            return true; // Dictionary loading is finished, return true to 
                         // calling function.
        } catch (FileNotFoundException e) { // If the file cannot be located...
            System.out.println(
                "Unable to find dictionary at " +
                "location specified."); // Tell the user that the file could 
                                        // not be loaded.
        } catch (NumberFormatException e) { // If the first line does not 
                                            // contain a valid integer...
            System.out.println(
                "Invalid dictionary file submitted.\n" +
                "Please ensure dictionary has number of words at the " + 
                "start of the file."); // Tell the user that the
                                       // dictionary file is not valid
        }
        return false; // Loading has failed, return false.
    }

    /**
     * This function checks a word to see if it is a valid guess. Valid guesses
     * match the word length specified by <b>WORD_LENGTH</b>
     * and can be found in the larger list of words maintained in the static 
     * array <em>dictionary</em>.
     * 
     * @param word - The word to be analyzed
     * 
     * @return - <u>true</u> if the word is <b>WORD_LENGTH</b> characters long 
     *           and exists in the <em>dictionary</em> array. 
     *           Returns <u>false</u> otherwise.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static boolean isValidWord(String word) {
        boolean valid = false;

        // If the word does not have WORD_LENGTH characters, it cannot be a 
        // valid guess
        if (word.length() != WORD_LENGTH) {
            return false;
        }

        if (DEBUG) { // Skip second check wihle debugging
            return true;
        }

        for (String str : dictionary) { // Check each word in the dictionary 
                                        // array
            if (str.equalsIgnoreCase(word)) { // If the word has been found...
                valid = true; // The guess is valid, exit the loop.
                break;
            }
        }
        return valid; // Return the value stored in valid.
    }

    /**
     * This function was solely used to ensure that the words were being read 
     * from file storage correctly.
     * Prints each entry in the <em>dictionary</em> array in a new line.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void printDictionary() {
        int count;
        for (count = 0; count < dictionary.length; count++) {
            System.out.println(dictionary[count]);
        }
    }

    /**
     * This is the driver function that manages the game. It randomly selects a 
     * word from the <em>dictionary</em>
     * and gives the user <b>NUM_TRIES</b> to guess it. Each guess is
     * <em>evaluated</em> and a hint provided to the user.
     * If the user successfully guesses the word within <b>NUM_TRIES</b> 
     * attempts, tell them they've won and exit the method.
     * If they do not successfully guess the word, tell them they've lost and 
     * print the answer to them.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void playWordle() {
        Scanner usrInput = new Scanner(System.in);
        int tries = NUM_TRIES; // Maximum number of attempts per game
        int solutionIndex; // Position of the word in the dictionary array
        String solution, result, guess; // Solution: final answer, guess
        boolean solved = false;

        solutionIndex = new Random().nextInt(dictionary.length);
        solution = dictionary[solutionIndex].toUpperCase();

        if (DEBUG) {
            System.out.printf("\nThe word is %s\n\n", solution);
        }

        // Display the game instructions to the user.
        printGameInstructions();

        // While the user still has tries left...
        while (tries > 0) {

            do {
                // Accept input from the user
                System.out.printf("\nTries left: %d" + 
                                  "\nEnter your guess: ", tries);
                // Convert the user's guess to all upper case
                // to ensure case-insensitivity
                guess = usrInput.nextLine().toUpperCase();
                // If the guess is invalid...
                if (!isValidWord(guess)) {
                    // Let the user know that their guess was not accepted
                    System.out.printf(
                        "Please ensure you enter a valid %d-letter" + 
                        " English word.\n", WORD_LENGTH);
                }
            } while (!isValidWord(guess)); // Prompt the user to guess a word 
                                           // until its valid,
                                           // however, don't penalize (tries--) 
                                           // for invalid words

            result = evaluateGuess(solution, guess); // Test the user's guess 
                                                     // against the solution

            if (result.equals("*****")) { // If the user has guessed 
                                                    // the correct word
                System.out.println(
                    "\n" + guess + " | " + result + 
                    "\n----You win!!----\n\n\n"); // Tell the user

                solved = true; // Mark the puzzle as solved
                break;
            } else { // Otherwise, print out the user's guess and their hint
                System.out.println(guess + " | " + result);
                tries--; // Reduce the number of tries they have by 1
            }
        }

        if (!solved) { // If the user is out of tries, but they have not 
                       // guessed correctly
            System.out.println(
                "----You lose :(----\nAnswer: " + 
                solution + 
                "\n\n\n"); // Tell the user they lost.
        }

        updateDictionary(solutionIndex); // Remove the current solution word to 
                                         // prevent repetition
        return;
    }

    /**
     * This function removes the the word at a given index and updates the
     * dictionary to prevent
     * repetition of words.
     * 
     * @param removeIndex - The index of the word is to be removed from the
     *                    dictionary
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void updateDictionary(int removeIndex) {
        String[] newDictionary = new String[dictionary.length - 1];
        int newDictionaryIndex = 0;
        for (int i = 0; i < dictionary.length; i++) {
            if (i == removeIndex) {
                continue;
            } else {
                newDictionary[newDictionaryIndex] = dictionary[i];
                newDictionaryIndex++;
            }
        }
        dictionary = newDictionary;

        if (DEBUG) {
            System.out.println("Updated dictionary: ");
            printDictionary();
        }
    }

    /**
     * This function gives the user instructions on how to play WORDLE.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void printGameInstructions() {
        System.out.println("                 ----Welcome to WORDLE----");
        System.out.printf("We're thinking of an English %d-letter" + 
                          "word\n", WORD_LENGTH);
        System.out.printf("Enter an English %d letter word and see if" + 
                          " you can get it in %d tries!\n", 
                          WORD_LENGTH, NUM_TRIES);
        System.out.println("|     ----HINTS---- (pretend the answer is" + 
                           " 'AUDIO')     |");
        System.out.println("| # - This letter is not in the word at all." + 
                           "            | WHALE -> #####");
        System.out.println("| + - This letter is correct but in the wrong" + 
                           " position. | OMITS -> +#+##");
        System.out.println("| * - This letter is correct and in the right" + 
                           " position. | HELLO -> ####*");
        System.out.println("|          (Guesses are case-insensitive)" + 
                           "               |");
    }

    /**
     * This function tests a given word against the solution chosen in the 
     * calling instance of Wordle, and returns a
     * string denoting the hint that should be printed to the user.
     * 
     * @param solution - The correct answer as generated by the
     *                 <em>playWordle()</em> method.
     * @param guess    - The user's guess
     * @return - A <b>WORD_LENGTH</b> character string composed of *, + or #
     *         representing a hint for the user.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static String evaluateGuess(String solution, String guess) {
        char[] res = new char[WORD_LENGTH];
        int count1, count2;

        // Initialize the hint as a string consisting solely of '#', denoting a
        // completely wrong guess.
        for (count1 = 0; count1 < WORD_LENGTH; count1++) {
            res[count1] = '#';
        }
        // If the user has entered the correct word
        if (solution.equals(guess)) {
            for (count1 = 0; count1 < WORD_LENGTH; count1++) {
                res[count1] = '*';
            }
            // Return a WORD_LENGTH character string consisting solely of '*', 
            // denoting a correct solution.
            return new String(res);
        }

        // For each character in the correct word...
        for (count1 = 0; count1 < solution.length(); count1++) {
            // For each character in the user's guess
            for (count2 = 0; count2 < guess.length(); count2++) {
                // If the current character being checked in the solution is
                // equal to the character being checked in the user's guess
                if ((solution.charAt(count1) == guess.charAt(count2))) {
                    if (count1 == count2) { // If the positions being checked 
                                            // are the same
                        res[count2] = '*'; // Set the nth character in the hint 
                                           // to be a '*'
                        break;
                    } else { // If the positions are not the same
                        if ((res[count2] != '*')) { // And the nth character is 
                                                    // not already marked as 
                                                    // correct
                            res[count2] = '+'; // Set the nth character in the 
                                               // hint to be a '+'
                        }
                    }
                }
            }
        }
        return new String(res); // Return new String constructed from res array
    }

    /**
     * Driver method. This method attempts to load a dictionary from a hardcoded
     * file. If the file is loaded successfully
     * the WORDLE game is started
     * 
     * @param args - Handler for the command-line arguments.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            DEBUG = Boolean.parseBoolean(args[0]);
        }

        if (DEBUG) {
            fileName = "test-dictionary.txt";
        }

        if (isLoadDictionary(fileName)) {
            while (dictionary.length > 0) {
                playWordle();
            }
            if (DEBUG) {
                System.out.println("All words exhausted");
            }
        }
    }
}