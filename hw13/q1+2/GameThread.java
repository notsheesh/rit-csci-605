/**
 * GameThread - A thread class handling communication with a specific client in the word guessing game.
 * Version 1.0, Dec 3, 2023
 *
 * This class represents a thread that communicates with a specific client in the word guessing game.
 * It sends and receives messages related to word guessing, provides feedback, and handles various game events.
 *
 * @author Kyle, Shreesh
 * @version 1.0
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
public class GameThread extends Thread{

    private String usr_guess;
    private String answer;

    private Socket conn;

    private DataInputStream inp;
    private DataOutputStream out;
    private ArrayList<String> words;

    /**
     * Constructor for GameThread.
     *
     * @param answer - The word to be guessed by the client.
     * @param conn - The Socket for communication with the client.
     * @param words - The list of words available for the game.
     */
    public GameThread(String answer, Socket conn, ArrayList<String> words){
        this.answer = answer;
        this.conn = conn;
        this.words = words;
    }

    /**
     * The main execution logic of the GameThread.
     */
    public void run(){
        System.out.println("Answer: " + this.answer);
        String msg;
        int idx;
        try{
            inp = new DataInputStream(conn.getInputStream());
            out = new DataOutputStream(conn.getOutputStream());
        }
        catch(IOException e){

        }

        try{
            out.writeUTF(this.answer);
            sleep(100);
            msg = inp.readUTF();
            System.out.println(msg);
            while (msg.compareTo("Q") != 0){
                System.out.println("Waiting on input...");
                msg = inp.readUTF();
                System.out.println(msg);

                switch (msg.charAt(0)){
                    case 'G':
                        msg = process_guess(msg.substring(2));
                        System.out.println(msg);
                        out.writeUTF(msg);
                        break;
                    case 'N':
                        idx = new Random().nextInt(0, this.words.size());
                        this.answer = this.words.get(idx);
                        this.words.remove(idx);
                        out.writeUTF(this.answer);
                        break;
                    default:
                        break;
                }

            }
        }
        catch (Exception e){

        }
    }

    /**
     * Processes the client's guess and generates a hint.
     *
     * @param usrGuess - The user's guessed word.
     * @return The hint generated based on the guessed word.
     */
    public String process_guess(String usrGuess){
        usrGuess = usrGuess.toUpperCase();
        System.out.println(usrGuess);
        char[] hint = new char[5];



        int x = 0;
        int y = 0;

        for(x = 0; x < 5;x++){
            hint[x] = '#';
        }

        try{
            for (x = 0; x < usrGuess.length(); x++){
                for (y = 0; y < usrGuess.length(); y++){
                    if (usrGuess.charAt(x) == this.answer.charAt(y)){
                        if (x == y){
                            hint[x] = '*';
                        }
                        else{
                            if (hint[x] != '*'){
                                hint[x] = '^';
                            }
                        }
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Invalid guess.");
            return "INVALID";
        }

        return new String(hint);
    }
}
