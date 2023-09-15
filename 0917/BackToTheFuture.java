import java.io.File; // File Class
import java.io.FileNotFoundException; // File not found handlers
import java.util.Scanner; // Scanner class to read text files
import java.util.Random;

import javax.annotation.processing.FilerException;
import javax.print.attribute.standard.NumberOfDocuments;

public class BackToTheFuture {
    private static final String dictionaryFileName = "dictionary.txt";
    private static final String testDictionaryFileName = "test-dictionary.txt";
    private static final String asciiArtFileName = "ascii-art.txt";
    private static String[] dictionary;
    private static int numDictionary;
    private static final boolean PRETTY_PRINT = false;
    private static final int NUM_TRIES = 9;
    private static String[] gamePicture = new String[NUM_TRIES * 2];
    private static int numberErrors = 0;
    public static int WORD_LENGTH = 5;

    private static boolean isLoadAsciiArt(){
        try {
            File asciiObject = new File(asciiArtFileName);
            Scanner fileReader = new Scanner(asciiObject);
            int indexPtr = 0;
            while(fileReader.hasNextLine()){
                gamePicture[indexPtr++] = fileReader.nextLine();
            }
            if(PRETTY_PRINT){
                System.out.println("ASCII Art Loaded");
                printGamePicture();
            }
            fileReader.close();
            return true;
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return false;
    }
    
    private static void printGamePicture(){
        for(int i = 0; i < gamePicture.length; i++){
            System.out.println(gamePicture[i]);
        }
    }

    private static void printGamePicture(int numErrors){
        for(int i = numErrors*2; i < gamePicture.length; i++){
            System.out.println(gamePicture[i]);
        }
    }

    private static void printDictionary(){
        System.out.print("Dictionary: ");
        for(int i = 0; i<numDictionary; i++){
            if(i != numDictionary-1){
                System.out.printf("%s, ", dictionary[i]);
            }
            else{
                System.out.println(dictionary[i]);
            }
        }
    }

    private static boolean isWordLeft(){
        return dictionary.length != 0;
    }

    private static boolean isLoadDictionary(String fileName){
        try{
            File dictionaryFileObject = new File(fileName);
            Scanner fileReader = new Scanner(dictionaryFileObject);

            // Get number of words in dictionary 
            String numDictionaryStr = fileReader.nextLine();
            numDictionary = Integer.parseInt(numDictionaryStr);

            // Initialize dictionary of the given size
            dictionary = new String[numDictionary];

            // Populate dictionary 
            int indexPtr = 0;
            while(fileReader.hasNextLine()){
                if(indexPtr < numDictionary){
                    String wordInFileLine = fileReader.nextLine();
                    dictionary[indexPtr++] = wordInFileLine;
                } else{
                    if(PRETTY_PRINT){
                        System.out.println("More words than expected.");
                    }
                    break;
                }
            }
            fileReader.close();
            if(PRETTY_PRINT){
                System.out.println("Dictionary loaded.");
                printDictionary();
            }
            return true;
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
        }
        return false;
    }

    private static String[] updateDictionary(int removeIndex){
        String[] updatedDictionary = new String[numDictionary-1];
        int indexPtr = 0;
        for(int i=0; i < numDictionary; i++){
            if(i != removeIndex){
                updatedDictionary[indexPtr++] = dictionary[i];
            }
        }
        numDictionary--;
        System.out.println("Updated dictionary: ");
        dictionary = updatedDictionary;
    }

    private static int generateRandomDictionaryIndex(){
        Random randomNumberGenerator = new Random();
        return randomNumberGenerator.nextInt(numDictionary);
    }

    private static boolean isValidWord(String userGuess){
        return true;
    }

    private static String takeUserInput(int attemptsLeft){
        Scanner userInputScanner = new Scanner(System.in);
        String userGuess;
        do{
            System.out.print("What's your guess? ");
            userGuess = userInputScanner.nextLine().toUpperCase();
            if(!isValidWord(userGuess)){
                System.out.printf(
                    "Please enter a %d letter word", WORD_LENGTH
                    );
            }
        } while (!isValidWord(userGuess));
        return userGuess;
    }

    private static String evaluate(String userGuess, String wordToBeGuessed){
        String matchString = "";
        for (int i = 0; i < WORD_LENGTH; i++) {
            if(userGuess.charAt(i) == wordToBeGuessed.charAt(i)){
                matchString += wordToBeGuessed.charAt(i);
            }
        }
        return matchString;
    }



    
    public static void main(String args[]){
        String fileName = testDictionaryFileName;
        if(isLoadDictionary(fileName) && isLoadAsciiArt()){
            // while(isWordLeft()){
            //     playGame();
            // }
            playGame();
        }
    }
}