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
import java.util.Scanner; // Handles user input
import java.util.Random; // Generates random index for picking a word 

/**
 * Class contains the methods used to implement the text-based HANGMAN
 * clone.<br>
 * More about the game 
 * <a href="https://en.wikipedia.org/wiki/Hangman">here</a>
 */
public class BackToTheFuture {
    private static final String dictionaryFileName = "dictionary.txt";
    private static final String testDictionaryFileName = "test-dictionary.txt";
    private static final String asciiArtFileName = "ascii-art.txt";
    private static String[] dictionary;
    private static int numDictionary;
    private static boolean PRETTY_PRINT = false;
    private static final int NUM_TRIES = 9;
    private static String[] gamePicture = new String[NUM_TRIES * 2];
    private static int numberErrors = 0;
    // public static int WORD_LENGTH = 5;

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
    private static boolean isLoadAsciiArt () {
        try {
            File asciiObject = new File ( asciiArtFileName ) ;
            Scanner fileReader = new Scanner ( asciiObject ) ;
            int indexPtr = 0;
            while ( fileReader.hasNextLine () ) {
                gamePicture[indexPtr++] = fileReader.nextLine () ;
            }
            if ( PRETTY_PRINT ) {
                // System.out.println ( "ASCII Art Loaded" ) ;
                // printGamePicture () ;
            }
            fileReader.close () ;
            return true;
        } catch ( Exception e ) {
            System.out.println ( "File not found" ) ;
            e.printStackTrace () ;
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
    private static void printGamePicture () {
        System.out.println () ;
        for ( int i = 0; i < gamePicture.length; i++ ) {
            System.out.println ( gamePicture[i] ) ;
        }
        System.out.println () ;
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
    private static void printGamePicture ( int numErrors ) {
        System.out.println () ;
        for ( int i = numErrors * 2; i < gamePicture.length; i++ ) {
            System.out.println ( gamePicture[i] ) ;
        }
        System.out.println () ;
    }

    /**
     * Ensure that the words were being read from file storage correctly.
     * Prints each entry in the <em>dictionary</em> array in a new line.
     *
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static void printDictionary () {
        System.out.print ( "Dictionary: " ) ;
        for ( int i = 0; i < numDictionary; i++ ) {
            if ( i != numDictionary - 1 ) {
                System.out.printf ( "%s, ", dictionary[i] ) ;
            } else {
                System.out.println ( dictionary[i] ) ;
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
    private static boolean isDictionaryEmpty () {
        return dictionary.length != 0;
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
    private static boolean isLoadDictionary ( String fileName ) {
        try {
            File dictionaryFileObject = new File ( fileName ) ;
            Scanner fileReader = new Scanner ( dictionaryFileObject ) ;

            // Get number of words in dictionary
            String numDictionaryStr = fileReader.nextLine () ;
            numDictionary = Integer.parseInt ( numDictionaryStr ) ;

            // Initialize dictionary of the given size
            dictionary = new String[numDictionary];

            // Populate dictionary
            int indexPtr = 0;
            while ( fileReader.hasNextLine () ) {
                if ( indexPtr < numDictionary ) {
                    String wordInFileLine = fileReader.nextLine () ;
                    dictionary[indexPtr++] = wordInFileLine;
                } else {
                    if ( PRETTY_PRINT ) {
                        System.out.println ( "More words than expected." ) ;
                    }
                    break;
                }
            }
            fileReader.close () ;
            if ( PRETTY_PRINT ) {
                // System.out.println ( "Dictionary loaded." ) ;
                // printDictionary () ;
            }
            return true;
        } catch ( FileNotFoundException e ) {
            System.out.println ( "File not found" ) ;
            e.printStackTrace () ;
        }
        return false;
    }

    /**
     * Removes the the word at a given index and updates the 
     * dictionary to prevent repetition of words.
     * 
     * @param removeIndex - The index of the word is to be removed from the
     *                      dictionary
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static void updateDictionary ( int removeIndex ) {
        String[] updatedDictionary = new String[numDictionary - 1];
        int indexPtr = 0;
        for ( int i = 0; i < numDictionary; i++ ) {
            if ( i != removeIndex ) {
                updatedDictionary[indexPtr++] = dictionary[i];
            }
        }
        numDictionary--;
        dictionary = updatedDictionary;
        System.out.println ( "\nUpdated dictionary: " ) ;
        printDictionary () ;
        System.out.println () ;
    }


    /**
     * Generates a random index within the current dictionary size.
     * 
     * @return A randomly generated index within the dictionary bounds.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static int generateRandomDictionaryIndex () {
        Random randomNumberGenerator = new Random () ;
        return randomNumberGenerator.nextInt ( numDictionary ) ;
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
    private static boolean isValidInput ( char userGuess ) {
        int aASCII = ( int ) 'a';
        int zASCII = ( int ) 'z';
        int AASCII = ( int ) 'A';
        int ZASCII = ( int ) 'Z';
        int userGuessASCII = ( int ) userGuess;
        if ( 
            ( userGuessASCII >= aASCII && userGuessASCII <= zASCII ) || 
            ( userGuessASCII >= AASCII && userGuessASCII <= ZASCII )
         ) {
            return true;
        } else {
            return false;
        }
    }

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
    private static char takeUserInput ( int attemptsLeft ) {
        Scanner usrInputScnnr = new Scanner ( System.in ) ;
        char userGuess;
        do {
            System.out.print ( "What's your guess? " ) ;
            userGuess = usrInputScnnr.next().toUpperCase().charAt ( 0 ) ;
            if ( !isValidInput ( userGuess ) ) {
                System.out.printf ( "Invalid input    | " ) ;
            }
        } while ( !isValidInput ( userGuess ) ) ;
        return userGuess;
    }

    /**
     * Generates an updated hint based on the user's guess and the current hint.
     * Replaces underscores '_' with the correct letter if the guess is correct.
     * 
     * @param guess - The letter guessed by the user.
     * @param answer - The target word or phrase to be guessed.
     * @param hint - The current hint, where underscores represent unknown 
     * letters.
     * 
     * @return The updated hint with correct guesses revealed.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static String generateHint ( 
        char guess, String answer, String hint ) {
        boolean flag = false;
        String newHint = "";
        for ( int i = 0; i < answer.length ()    ; i++ ) {
            if ( hint.charAt ( i ) != '_' ) {
                newHint += hint.charAt ( i ) ;
            } else if ( guess == answer.charAt ( i ) && flag == false ) {
                newHint += guess;
                // flag = true;
            } else {
                newHint += '_';
            }
        }
        return newHint;
    }

    /**
     * Updates the number of attempts left based on whether the guess was 
     * correct or not.
     * 
     * @param hint - The previous hint before the guess.
     * @param newHint - The updated hint after the guess.
     * @param attemptsLeft - The current number of attempts remaining.
     * 
     * @return The updated number of attempts left after evaluating the guess.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static int updateAttemptsLeft ( 
            String hint, String newHint, int attemptsLeft ) {
        if ( newHint.equals ( hint ) ) {
            System.out.printf ( "Nope, try again! " ) ;
            return attemptsLeft - 1;
        } else {
            System.out.printf ( "Good job!\t " ) ;
            return attemptsLeft;
        }
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
    private static boolean letUserGuess ( String wordToBeGuessed ) {
        int attemptsLeft = NUM_TRIES;
        char userGuess;
        String hint = "";
        for ( int i = 0; i < wordToBeGuessed.length (); i ++ ) {
            hint += "_";
        }
        String newHint;
        int numErrors = NUM_TRIES - attemptsLeft;
        while ( attemptsLeft > 0 ) {
            // New level
            System.out.printf ( "Attempts left: %d | ", attemptsLeft ) ;

            // Take user input
            userGuess = takeUserInput ( attemptsLeft ) ;

            // Check user input and generate hint
            newHint = generateHint ( userGuess, wordToBeGuessed, hint ) ;

            // Update progress and attempts left
            attemptsLeft = updateAttemptsLeft ( hint, newHint, attemptsLeft ) ;
            System.out.printf ( "| Progress: %s\n", newHint ) ;
            hint = newHint;

            // Display game picture
            numErrors = NUM_TRIES - attemptsLeft;
            printGamePicture ( numErrors ) ;

            if ( hint.equals ( wordToBeGuessed ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Randomly selects a word, allows the user to guess letters, and reports 
     * the outcome.
     * 
     * @author Kyle Burke, Shreesh Tripathi
     */
    private static void playGame () {
        int wordToBeGuessedIndex;
        String wordToBeGuessed;
        String hashes = "###########################";
        System.out.println ( hashes + hashes ) ;
        System.out.println ( "\t\t   Back To The Future" ) ;
        System.out.println ( hashes + hashes ) ;

        // Pick a random word from dictionary
        wordToBeGuessedIndex = generateRandomDictionaryIndex () ;
        wordToBeGuessed = dictionary[wordToBeGuessedIndex].toUpperCase () ;

        if ( PRETTY_PRINT ) {
            System.out.printf ( "\n[PRETTY_PRINT = true]" +
                    " Answer: %s\n", wordToBeGuessed ) ;
        } else {
            String hint = "";
            for ( int i = 0; i < wordToBeGuessed.length (); i ++ ) {
                hint += "_";
            }
            System.out.printf ( "\n[PRETTY_PRINT = false]" +
                    " Answer: %s\n", hint ) ;
        }

        // Game picture
        printGamePicture () ;

        // Let user guess for NUM_TRIES
        boolean isUserWin = letUserGuess ( wordToBeGuessed ) ;

        // Report outcome of game
        if ( isUserWin ) {
            System.out.println ( "\nYou win : ) " ) ;
        } else {
            System.out.println ( "\nYou lose ( :" ) ;
            System.out.println ( "The word was " + wordToBeGuessed ) ;
        }

        updateDictionary ( wordToBeGuessedIndex ) ;
    }

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
    public static void main ( String args[] ) {
        if ( args.length == 0 ) {
            System.out.println ( "Please enter the filename" ) ;
            return;
        }

        // String fileName = testDictionaryFileName;
        String fileName = args[0];
        Boolean isPrettyPrint = Boolean.parseBoolean ( args[1] ) ;
        PRETTY_PRINT = isPrettyPrint;

        if ( isLoadDictionary ( fileName ) && isLoadAsciiArt () ) {
            while ( isDictionaryEmpty () ) {
                playGame () ;
            }
        }
    }
}