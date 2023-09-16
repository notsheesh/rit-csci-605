import java.io.File; // File Class
import java.io.FileNotFoundException; // File not found handlers
import java.util.Scanner; // Scanner class to read text files
import java.util.Random;

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

    private static boolean isLoadAsciiArt () {
        try {
            File asciiObject = new File ( asciiArtFileName ) ;
            Scanner fileReader = new Scanner ( asciiObject ) ;
            int indexPtr = 0;
            while ( fileReader.hasNextLine ()  ) {
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

    private static void printGamePicture () {
        System.out.println () ;
        for ( int i = 0; i < gamePicture.length; i++ ) {
            System.out.println ( gamePicture[i] ) ;
        }
        System.out.println () ;
    }

    private static void printGamePicture ( int numErrors ) {
        System.out.println () ;
        for ( int i = numErrors * 2; i < gamePicture.length; i++ ) {
            System.out.println ( gamePicture[i] ) ;
        }
        System.out.println () ;
    }

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

    private static boolean isWordLeft () {
        return dictionary.length != 0;
    }

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
            while ( fileReader.hasNextLine ()  ) {
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

    private static int generateRandomDictionaryIndex () {
        Random randomNumberGenerator = new Random () ;
        return randomNumberGenerator.nextInt ( numDictionary ) ;
    }

    private static boolean isValidInput ( char userGuess ) {
        return true;
    }

    private static char takeUserInput ( int attemptsLeft ) {
        Scanner userInputScanner = new Scanner ( System.in ) ;
        char userGuess;
        do {
            System.out.print ( "What's your guess? " ) ;
            userGuess = userInputScanner.next () .toUpperCase () .charAt ( 0 ) ;
            if ( !isValidInput ( userGuess )  ) {
                System.out.printf ( "Please enter a valid letter" ) ;
            }
        } while ( !isValidInput ( userGuess )  ) ;
        return userGuess;
    }

    private static String generateHint ( char guess, String answer, String hint ) {
        boolean flag = false;
        String newHint = "";
        for ( int i = 0; i < answer.length()    ; i++ ) {
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

    private static int updateAttemptsLeft ( 
            String hint, String newHint, int attemptsLeft ) {
        if ( newHint.equals ( hint )  ) {
            System.out.printf ( "Nope, try again!" ) ;
            return attemptsLeft - 1;
        } else {
            System.out.printf ( "Good job!" ) ;
            return attemptsLeft;
        }
    }

    private static boolean letUserGuess ( String wordToBeGuessed ) {
        int attemptsLeft = NUM_TRIES;
        char userGuess;
        String hint = "";
        for ( int i = 0; i < wordToBeGuessed.length(); i ++ ) {
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
            System.out.printf ( " | Progress: %s\n", newHint ) ;
            hint = newHint;

            // Display game picture
            numErrors = NUM_TRIES - attemptsLeft;
            printGamePicture ( numErrors ) ;

            if ( hint.equals ( wordToBeGuessed )  ) {
                return true;
            }
        }
        return false;
    }

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
            for ( int i = 0; i < wordToBeGuessed.length(); i ++ ) {
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

    public static void main ( String args[] ) {
        if ( args.length == 0 ) {
            System.out.println ( "Please enter the filename" ) ;
            return;
        }

        // String fileName = testDictionaryFileName;
        String fileName = args[0];
        Boolean isPrettyPrint = Boolean.parseBoolean ( args[1] ) ;
        PRETTY_PRINT = isPrettyPrint;

        if ( isLoadDictionary ( fileName ) && isLoadAsciiArt ()  ) {
            while ( isWordLeft ()  ) {
                playGame () ;
            }
        }
    }
}