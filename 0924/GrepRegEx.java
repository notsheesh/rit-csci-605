import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern; // Pattern matching

public class GrepRegEx {
    public String vowelsConsonants;
    public String palindrome4Or5;
    public String palindrome1Or30;
    public String ddmmyy;
    public String nknl;
    private String fileName = "input.txt";
    private final String patternsFileName = "patterns.txt";
    private int numStrings = 10000;
    private String delimiter = "";
    private String[] wordsArr = new String[numStrings];

    private void parseConsoleArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                delimiter = args[i + 1];
            } else if (args[i].equals("-f")) {
                fileName = args[i + 1];
            }
        }
    }

    private void printPatterns() {
        System.out.println("Patterns: ");
        System.out.println(vowelsConsonants);
        System.out.println(palindrome1Or30);
        System.out.println(palindrome4Or5);
        System.out.println(ddmmyy);
        System.out.println(nknl);
    }

    private boolean loadPatternsFromFile() {
        try {
            Scanner sc = new Scanner(new File(patternsFileName));
            while (sc.hasNextLine()) {
                vowelsConsonants = sc.nextLine();
                palindrome1Or30 = sc.nextLine();
                palindrome4Or5 = sc.nextLine();
                ddmmyy = sc.nextLine();
                nknl = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private int countNumWords(String delimiter) {
        int count = 0;
        try {
            Scanner fileSc = new Scanner(new File(fileName));
            if (delimiter.length() > 0) {
                // System.out.printf("\nUsing delimiter: %s\n", delimiter);
                fileSc.useDelimiter(delimiter);
            }
            while (fileSc.hasNext()) {
                String word = fileSc.next().trim();
                // System.out.printf("\nWord read: %s", word);
                count++;
            }
            fileSc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No such file exists ");
            // e.printStackTrace();
        }
        return count;
    }

    private void printWordsArr(){
        System.out.print("Words Array: ");
        for (int i = 0; i < wordsArr.length; i++) {
            System.out.printf("%s, ", wordsArr[i]);
        }
    }

    private boolean readWordsFromStdin() {
        try {
            System.out.print("Enter words: ");
            Scanner stdinSc = new Scanner(System.in);   
            wordsArr = stdinSc.nextLine().split(",");
            for (int i = 0; i < wordsArr.length; i++) {
                wordsArr[i] = wordsArr[i].trim();
            }
            stdinSc.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean readWordsFromFile(String delimiter){
        numStrings = countNumWords(delimiter);
        // System.out.println("Number of words " + Integer.toString(numStrings));
        wordsArr = new String[numStrings];
        try {
            Scanner fileSc = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void matchAllPatterns() {
        System.out.println("\nPattern Matching: ");
        for (int i = 0; i < wordsArr.length; i++) {
            String word = wordsArr[i];
            System.out.printf("\n--------{%s}---------\n", word);
            if (Pattern.matches(vowelsConsonants, word)) {
                System.out.println("Ordered Vowels\n");
            }
            if (Pattern.matches(palindrome4Or5, word)) {
                System.out.println("Palindrome {4,5}\n");
            }
            if (Pattern.matches(palindrome1Or30, word)) {
                System.out.println("Palindrome {1,30}\n");
            }
            if (Pattern.matches(ddmmyy, word)) {
                System.out.println("DD/MM/YY or MM/DD/YY\n");
            }
            if (Pattern.matches(nknl, word)) {
                System.out.println("[n-k]|(nl)\n");
            }
        }
    }

    public static void main(String[] args) {
        GrepRegEx grex = new GrepRegEx();
        // Load patterns
        if(grex.loadPatternsFromFile()){
            // grex.printPatterns();
            if (args.length == 1 && Boolean.parseBoolean(args[0])) {
                if(grex.readWordsFromStdin()){
                    grex.matchAllPatterns();
                } 
            } else if (args.length == 4) {
                // Set delimiter and file name
                grex.parseConsoleArguments(args);
                if(grex.readWordsFromFile(null)){
                    grex.matchAllPatterns();
                }
            } else {
                System.out.println("Enter valid arguments.");
                return;
            }
        }
    }
}
