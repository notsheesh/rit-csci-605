import java.io.File; // File Class
import java.io.FileNotFoundException; // File not found handlers
import java.util.Scanner; // Scanner class to read text files
import java.util.Random;

import javax.annotation.processing.FilerException;
import javax.print.attribute.standard.NumberOfDocuments;

public class BackToTheFuture {
    public static void main(String args[]){
        String fileName = testDictionaryFileName;
        if(isLoadDictionary(fileName) && isLoadAsciiArt()){
            playGame();
        }
    }
}