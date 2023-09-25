/**
 * Description: Grep class for searching patterns in text using regular 
 * expressions.
 *
 * Filename: Grep.java
 * 
 *
 * Version: 1.0
 * Revision Log:
 *      Version 1.0 - Initial creation. Date: Sept 23, 2023.
 */
import java.util.Scanner; // Scanner class for reading words
import java.io.File; // File class for reading from grep.txt
import java.io.FileNotFoundException; // Exception handler for file reading

public class Grep {
    private static final String fileName = "grep.txt";
    private static final int BUFFER = 10000;
    private String[] wordsArr = new String[BUFFER];
    int count = 0;

    /**
     * Counts the number of lines in the specified file.
     * 
     * @return The number of lines in the file.
     */
    private int countNumLines() {
        try {

            Scanner fileSc = new Scanner(new File(fileName));
            while (fileSc.hasNextLine()) {
                fileSc.nextLine();
                count++;
            }
            fileSc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Prints the data read from the file.
     */
    private void printFileData() {
        System.out.printf("File Data: ");
        for (int i = 0; i < wordsArr.length; i++) {
            System.out.printf("%s, ", wordsArr[i]);
        }
        System.out.println("\n");
    }

    /**
     * Reads data from the file and stores it in the wordsArr array.
     */
    private void readFromFile() {
        wordsArr = new String[countNumLines()];
        try {
            int i = 0;
            Scanner fileSc = new Scanner(new File(fileName));
            while (fileSc.hasNextLine()) {
                wordsArr[i++] = fileSc.nextLine();
            }
            fileSc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    /**
     * Takes user input for phrases to be checked, separated by commas.
     */
    private void takeUserInput() {
        System.out.println("Enter phrases to be checked, " +
                "each separated by a ',': ");
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);

        try {
            wordsArr = sc.nextLine().split(",");
            for (int i = 0; i < wordsArr.length; i++) {
                wordsArr[i] = wordsArr[i].trim();
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method for Grep class, responsible for reading user input and 
     * processing patterns.
     * 
     * @param args Command line arguments (optional).
     */
    public static void main(String[] args) {
        Grep g = new Grep();
        if (args.length > 0 && Boolean.parseBoolean(args[0])) {
            g.readFromFile();
            g.printFileData();
        } else {
            g.takeUserInput();
        }
        Pattern p = new Pattern();
        p.processStrings(g.wordsArr);
    }
}

/**
 * Class for defining pattern matching methods.
 */
class Pattern {
    boolean TEST_ONLY_ONE = false;

    public Pattern() {
    }

    /**
     * Converts a word to a character array.
     * 
     * @param word The word to convert.
     * @return The character array.
     */
    private char[] convertToCharArray(String word) {
        return word.toCharArray();

    }

    /**
     * Process the provided array of strings to check for patterns.
     * 
     * @param wordsArr The array of strings to process.
     */
    public void processStrings(String[] wordsArr) {
        System.out.println("Testing");
        for (int i = 0; i < wordsArr.length; i++) {
            System.out.printf("\n\n--------{%s}---------", wordsArr[i]);
            char[] charArr = wordsArr[i].toCharArray();
            boolean noMatch = true;
            if (ab(charArr)) {
                noMatch = false;
                System.out.printf("\n^ab$", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (xaabb(charArr)) {
                noMatch = false;
                System.out.printf("\n.a+b.", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (xaby(charArr)) {
                noMatch = false;
                System.out.printf("\n.ab.", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (ac1(charArr)) {
                noMatch = false;
                System.out.printf("\n^[ab]c$", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (ac2(charArr)) {
                noMatch = false;
                System.out.printf("\n^[ab]?c$", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (ac3(charArr)) {
                noMatch = false;
                System.out.printf("\n^[ab]?|c?$", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }

            if (abba(charArr)) {
                noMatch = false;
                System.out.printf("\n..\\2\\1", wordsArr[i]);
                if (TEST_ONLY_ONE) {
                    continue;
                }
            }
            if (noMatch) {
                System.out.println("\nNo match");
            }
            System.out.println();
        }
    }

    /**
     * FSM for the regex pattern: ^ab$
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean ab(char[] charArr) {
        // q0 = start state
        // qe = error state
        // q2 = final state
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                if (charArr[i] == 'a') {
                    state = "q1";
                } else {
                    state = "qe";
                    break;
                }
            } else if (state.equals("q1")) {
                if (charArr[i] == 'b') {
                    state = "q2";
                } else {
                    state = "qe";
                    break;
                }
            } else if (state.equals("q2")) {
                state = "qe";
                break;
            }
        }
        return state.equals("q2");
    }

    /**
     * FSM for the regex pattern: .a+b.
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean xaabb(char[] charArr) {
        // q0 = start state
        // qe = error state
        // q3 = final state
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                state = "q1";
            } else if (state.equals("q1")) {
                if (charArr[i] == 'a') {
                    state = "q2";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q2")) {
                if (charArr[i] == 'a') {
                    state = "q2";
                } else if (charArr[i] == 'b') {
                    state = "q3";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q3")) {
                state = "q4";
            } else if (state.equals("q4")) {
                state = "qe";
            }
        }
        return state.equals("q4");
    }

    /**
     * FSM for the regex pattern: .ab.
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean xaby(char[] charArr) {
        // q0 = start state
        // qe = error state
        // q4 = final state
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                state = "q1";
            } else if (state.equals("q1")) {
                if (charArr[i] == 'a') {
                    state = "q2";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q2")) {
                if (charArr[i] == 'b') {
                    state = "q3";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q3")) {
                state = "q4";
            } else if (state.equals("q4")) {
                state = "qe";
            }
        }
        return state.equals("q4");
    }

    /**
     * FSM for the regex pattern: ^[ab]c$
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean ac1(char[] charArr) {
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                if (charArr[i] == 'a' || charArr[i] == 'b') {
                    state = "q1";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q1")) {
                if (charArr[i] == 'c') {
                    state = "q2";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q2")) {
                state = "qe";
            }
        }
        return state.equals("q2");
    }

    /**
     * FSM for the regex pattern: ^[ab]?c$
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean ac2(char[] charArr) {
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                if (charArr[i] == 'a' || charArr[i] == 'b') {
                    state = "q1";
                } else if (charArr[i] == 'c') {
                    state = "q2";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q1")) {
                if (charArr[i] == 'c') {
                    state = "q2";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q2")) {
                state = "qe";
            }
        }
        return state.equals("q2");
    }

    /**
     * FSM for the regex pattern: ^[ab]?|c?$
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean ac3(char[] charArr) {
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                if (charArr[i] == 'a' || charArr[i] == 'b' || charArr[i] == 'c') {
                    state = "q1";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q1")) {
                state = "qe";
            }
        }
        return state.equals("q0") || state.equals("q1");
    }

    /**
     * FSM for the regex pattern: ^..\2\1$
     * 
     * @param wordsArr The array of strings to process.
     */
    public boolean abba(char[] charArr) {
        String state = "q0";
        for (int i = 0; i < charArr.length; i++) {
            if (state.equals("qe")) {
                return false;
            } else if (state.equals("q0")) {
                state = "q1";
            } else if (state.equals("q1")) {
                state = "q2";
            } else if (state.equals("q2")) {
                if (charArr[i] == charArr[1]) {
                    state = "q3";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q3")) {
                if (charArr[i] == charArr[0]) {
                    state = "q4";
                } else {
                    state = "qe";
                }
            } else if (state.equals("q4")) {
                state = "qe";
            }
        }
        return state.equals("q4");
    }
}