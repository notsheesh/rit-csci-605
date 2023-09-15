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