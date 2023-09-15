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