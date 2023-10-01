/**
 * This program implements the popular internet game HANGMAN with a slight twist
 * Instead of a hangman its theme is inspired by the famous movie "Back To The 
 * Future". 
 * Filename: BackToTheFuture.java
 * Version: 3.0
 * Revision Log:
 *      Version 1.0 - Initial Creation. Date: Sep 11, 2023
 *      Version 2.0 - Add logic for word matching. Date: Sep 14, 2023
 *      Version 3.0 - Final changes in formatting. Date: Sep 16, 2023
 */

import java.io.File; // Handles reading from file
import java.io.FileNotFoundException; // Exception in case file can't be loaded
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.util.Scanner; // Handles user input
import java.util.Random; // Generates random index for picking a word 


/**
 * This class represents the player in the game and manages his/her actions.
 */
class Player {
    /**
     * Takes user input for a guessed letter and ensures it is a valid letter
     * (uppercase or lowercase).
     * 
     * @param attemptsLeft - The number of attempts remaining.
     * 
     * @return The valid letter guessed by the user.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static char takeUserInput(int attemptsLeft) {
        Scanner usrInputScnnr = new Scanner(System.in);
        char userGuess;
        do {
            System.out.print("What's your guess? ");
            userGuess = usrInputScnnr.next().toUpperCase().charAt(0);
            if (!Game.isValidInput(userGuess)) {
                System.out.printf("Invalid input    | ");
            }
        } while (!Game.isValidInput(userGuess));
        return userGuess;
    }

    /**
     * Allows the user to guess letters in a word or phrase and tracks their
     * progress.
     * 
     * @param wordToBeGuessed - The target word or phrase to be guessed.
     * 
     * @return true if the user successfully guesses the word, otherwise false.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static boolean letUserGuess(String wordToBeGuessed) {
        int attemptsLeft = Game.NUM_TRIES;
        char userGuess;
        String hint = "";
        for (int i = 0; i < wordToBeGuessed.length(); i++) {
            hint += "_";
        }
        String newHint;
        int numErrors = Game.NUM_TRIES - attemptsLeft;
        while (attemptsLeft > 0) {
            // New level
            System.out.printf("Attempts left: %d | ", attemptsLeft);

            // Take user input
            userGuess = Player.takeUserInput(attemptsLeft);

            // Check user input and generate hint
            newHint = Game.generateHint(userGuess, wordToBeGuessed, hint);

            // Update progress and attempts left
            attemptsLeft = Player.updateAttemptsLeft(hint, newHint, attemptsLeft);
            System.out.printf("| Progress: %s\n", newHint);
            hint = newHint;

            // Display game picture
            numErrors = Game.NUM_TRIES - attemptsLeft;
            Picture.printGamePicture(numErrors);

            if (hint.equals(wordToBeGuessed)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the number of attempts left based on whether the guess was
     * correct or not.
     * 
     * @param hint         - The previous hint before the guess.
     * @param newHint      - The updated hint after the guess.
     * @param attemptsLeft - The current number of attempts remaining.
     * 
     * @return The updated number of attempts left after evaluating the guess.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static int updateAttemptsLeft(
            String hint, String newHint, int attemptsLeft) {
        if (newHint.equals(hint)) {
            System.out.printf("Nope, try again! ");
            return attemptsLeft - 1;
        } else {
            System.out.printf("Good job!\t ");
            return attemptsLeft;
        }
    }
}


/**
 * This class manages the ASCII art used for visual representation in the game.
 */
class Picture {
    public static final String asciiArtFileName = "ascii-art.txt";
    public static String[] gamePicture = new String[Game.NUM_TRIES * 2];

    /**
     * Loads the ascii art from a file in secondary storage into the static
     * String array <em>gamePicture</em>
     *
     * @return <u>true</u> if the ascii art was successfully loaded and
     *         <u>false</u> otherwise.
     * 
     * @exception FileNotFoundException: If the file specified by
     *                                   <em>filename</em> does not exist, this
     *                                   method will cease execution and the
     *                                   static array <em>gamePicture</em> will
     *                                   remain empty.
     *
     * @author Kyle Burke, Shreesh Tripathi
     *
     */
    public static boolean isLoadAsciiArt() {
        try {
            File asciiObject = new File(Picture.asciiArtFileName);
            Scanner fileReader = new Scanner(asciiObject);
            int indexPtr = 0;
            while (fileReader.hasNextLine()) {
                Picture.gamePicture[indexPtr++] = fileReader.nextLine();
            }
            if (Game.PRETTY_PRINT) {
                // System.out.println ( "ASCII Art Loaded" ) ;
                // Picture.printGamePicture () ;
            }
            fileReader.close();
            return true;
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Ensures that the ascii art is being read from file storage correctly.
     * Prints each entry in the <em>gamePicture</em> array in a new line.
     *
     * @author Kyle Burke, Shreesh Tripathi
     * 
     */
    public static void printGamePicture() {
        System.out.println();
        for (int i = 0; i < Picture.gamePicture.length; i++) {
            System.out.println(Picture.gamePicture[i]);
        }
        System.out.println();
    }

    /**
     * Displays the current state of ascii art as a function of number of errors
     * by the user so far.
     * Prints each entry in the <em>gamePicture</em> array in a new line.
     *
     * @param numErrors - The number of mistakes commited by the user
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void printGamePicture(int numErrors) {
        System.out.println();
        for (int i = numErrors * 2; i < Picture.gamePicture.length; i++) {
            System.out.println(Picture.gamePicture[i]);
        }
        System.out.println();
    }

}

/**
 * This class manages the dictionary of words that can be used as answers in the game.
 */
class Dictionary {
    public static final String testDictionaryFileName = "test-dictionary.txt";
    public static String[] dictionary;
    public static int numDictionary;

    /**
     * Ensure that the words were being read from file storage correctly.
     * Prints each entry in the <em>dictionary</em> array in a new line.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void printDictionary() {
        System.out.print("Dictionary: ");
        for (int i = 0; i < Dictionary.numDictionary; i++) {
            if (i != Dictionary.numDictionary - 1) {
                System.out.printf("%s, ", Dictionary.dictionary[i]);
            } else {
                System.out.println(Dictionary.dictionary[i]);
            }
        }
    }

    /**
     * Reports if the dictionary is empty
     * 
     * @return true if no words are left
     *         false otherwise
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static boolean isDictionaryEmpty() {
        return Dictionary.dictionary.length == 0;
    }

    /**
     * Loads the valid potential guesses from a file in secondary storage into
     * the static String array <em>dictionary</em>
     * 
     * @param fileName - The location of the file containing the valid words the
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
     *
     * @author Kyle Burke, Shreesh Tripathi
     *
     */
    public static boolean isLoadDictionary(String fileName) {
        try {
            File dictionaryFileObject = new File(fileName);
            Scanner fileReader = new Scanner(dictionaryFileObject);

            // Get number of words in dictionary
            String numDictionaryStr = fileReader.nextLine();
            Dictionary.numDictionary = Integer.parseInt(numDictionaryStr);

            // Initialize dictionary of the given size
            Dictionary.dictionary = new String[Dictionary.numDictionary];

            // Populate dictionary
            int indexPtr = 0;
            while (fileReader.hasNextLine()) {
                if (indexPtr < Dictionary.numDictionary) {
                    String wordInFileLine = fileReader.nextLine();
                    Dictionary.dictionary[indexPtr++] = wordInFileLine;
                } else {
                    if (Game.PRETTY_PRINT) {
                        System.out.println("More words than expected.");
                    }
                    break;
                }
            }
            fileReader.close();
            if (Game.PRETTY_PRINT) {
                // System.out.println ( "Dictionary loaded." ) ;
                // Dictionary.printDictionary () ;
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Removes the the word at a given index and updates the
     * dictionary to prevent repetition of words.
     * 
     * @param removeIndex - The index of the word is to be removed from the
     *                    dictionary
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void updateDictionary(int removeIndex) {
        String[] updatedDictionary = new String[Dictionary.numDictionary - 1];
        int indexPtr = 0;
        for (int i = 0; i < Dictionary.numDictionary; i++) {
            if (i != removeIndex) {
                updatedDictionary[indexPtr++] = Dictionary.dictionary[i];
            }
        }
        Dictionary.numDictionary--;
        Dictionary.dictionary = updatedDictionary;
        if (Game.PRETTY_PRINT) {
            System.out.println("\nUpdated dictionary: ");
            Dictionary.printDictionary();
        }
        System.out.println();
    }

    /**
     * Generates a random index within the current dictionary size.
     * 
     * @return A randomly generated index within the dictionary bounds.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static int generateRandomDictionaryIndex() {
        Random randomNumberGenerator = new Random();
        return randomNumberGenerator.nextInt(Dictionary.numDictionary);
    }

}


/**
 * Main game class to clone the text-based game - HANGMAN<br>
 * More about the game
 * <a href="https://en.wikipedia.org/wiki/Hangman">here</a>
 */
class Game {
    public static final int NUM_TRIES = 9;
    public static boolean PRETTY_PRINT = false;

    /**
     * Randomly selects a word, allows the user to guess letters, and reports
     * the outcome.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void playGame() {
        int wordToBeGuessedIndex;
        String wordToBeGuessed;
        String hashes = "###########################";
        System.out.println(hashes + hashes);
        System.out.println("\t\t   Back To The Future");
        System.out.println(hashes + hashes);

        // Pick a random word from dictionary
        wordToBeGuessedIndex = Dictionary.generateRandomDictionaryIndex();
        wordToBeGuessed = Dictionary.dictionary[wordToBeGuessedIndex].toUpperCase();

        if (Game.PRETTY_PRINT) {
            System.out.printf("\n[PRETTY_PRINT = true]" +
                    " Answer: %s\n", wordToBeGuessed);
        } else {
            String hint = "";
            for (int i = 0; i < wordToBeGuessed.length(); i++) {
                hint += "_";
            }
            System.out.printf("\n[PRETTY_PRINT = false]" +
                    " Answer: %s\n", hint);
        }

        // Game picture
        Picture.printGamePicture();

        // Let user guess for NUM_TRIES
        boolean isUserWin = Player.letUserGuess(wordToBeGuessed);

        // Report outcome of game
        if (isUserWin) {
            System.out.println("\nYou win :)");
        } else {
            System.out.println("\nYou lose :(");
            System.out.println("The word was " + wordToBeGuessed);
        }

        Dictionary.updateDictionary(wordToBeGuessedIndex);
    }

    /**
     * Checks if the provided character is a valid input by verifying if it is
     * a letter (uppercase or lowercase).
     * 
     * @param userGuess - The character to be checked.
     * 
     * @return true if the character is a valid letter, otherwise false.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static boolean isValidInput(char userGuess) {
        int aASCII = (int) 'a';
        int zASCII = (int) 'z';
        int AASCII = (int) 'A';
        int ZASCII = (int) 'Z';
        int userGuessASCII = (int) userGuess;
        if ((userGuessASCII >= aASCII && userGuessASCII <= zASCII) ||
                (userGuessASCII >= AASCII && userGuessASCII <= ZASCII)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates an updated hint based on the user's guess and the current hint.
     * Replaces underscores '_' with the correct letter if the guess is correct.
     * 
     * @param guess  - The letter guessed by the user.
     * @param answer - The target word or phrase to be guessed.
     * @param hint   - The current hint, where underscores represent unknown
     *               letters.
     * 
     * @return The updated hint with correct guesses revealed.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static String generateHint(
            char guess, String answer, String hint) {
        boolean flag = false;
        String newHint = "";
        for (int i = 0; i < answer.length(); i++) {
            if (hint.charAt(i) != '_') {
                newHint += hint.charAt(i);
            } else if (guess == answer.charAt(i) && flag == false) {
                newHint += guess;
                // flag = true;
            } else {
                newHint += '_';
            }
        }
        return newHint;
    }
}

/**
 * Driver class for HANGMAN clone
*/ 
public class BackToTheFuture{
    /**
     * Driver method for hangman
     * Attempts to load a dictionary of words and the ascii art
     * Starts the game
     * 
     * @param args[0] - File name containing dictionary
     * @param args[1] - Mode of execution
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("Please enter the filename");
            return;
        }

        // String fileName = Dictionary.testDictionaryFileName;
        String fileName = args[0];
        if (args.length == 2) {
            Boolean isPrettyPrint = Boolean.parseBoolean(args[1]);
            Game.PRETTY_PRINT = isPrettyPrint;
        }

        if (Dictionary.isLoadDictionary(fileName) && Picture.isLoadAsciiArt()) {
            while (!Dictionary.isDictionaryEmpty()) {
                Game.playGame();
                if (!Dictionary.isDictionaryEmpty()) {
                    char isPlayAgain = 'n';
                    Scanner usrInpSc = new Scanner(System.in);
                    System.out.println("Do you want to continue? (Y/N):");
                    isPlayAgain = usrInpSc.next().toUpperCase().charAt(0);
                    if (isPlayAgain == 'N') {
                        break;
                    }
                } else {
                    if (Game.PRETTY_PRINT) {
                        System.out.println("No words left.");
                    }
                }
            }
        }
    }

}