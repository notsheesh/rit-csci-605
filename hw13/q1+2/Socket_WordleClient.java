/**
 * Socket_WordleClient - A client for the word guessing game using Socket communication.
 * Version 1.0, Dec 3, 2023
 *
 * This program represents a client for the word guessing game using Socket communication.
 * It connects to the server, plays rounds of the game, and allows the user to continue or quit.
 *
 * @author Kyle, Shreesh
 * @version 1.0
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Socket_WordleClient {

    private static String HOSTNAME = "localhost";
    private String word_to_guess;
    private Socket conn;
    private DataInputStream in;
    private DataOutputStream out;

    private int num_lives = 5;

    private boolean won = false;
    /**
     * Default constructor for Socket_WordleClient.
     */
    public Socket_WordleClient(){

    }

    /**
     * Connects to the server using a specified host and port.
     *
     * @param host - The host to connect to.
     * @param port - The port on which the server is running.
     */
    public void connect(String host, int port){
        try{
            conn = new Socket(host, port);
            System.out.println("Connection successful...");
            out = new DataOutputStream(conn.getOutputStream());
            in = new DataInputStream(conn.getInputStream());
        }
        catch (UnknownHostException e){
            System.err.println("Unknown host. closing...");
            System.exit(-1);
        }
        catch (IOException e){
            System.err.println("Unexpected Exception during IO. Closing...");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        catch (Exception e){
            System.err.printf("Unexpected exception %s. Closing...", e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Sends a guess to the server and receives the hint in response.
     *
     * @param word - The guessed word.
     * @return The hint received from the server.
     */
    public String guess(String word){
        String hint = "_____";
        try{
            this.out.writeUTF(("G|" + word));
            hint = in.readUTF();

            if (hint.compareTo("*****") == 0){
                System.out.println("You win!");
                this.won = true;
            }
            else{
                this.num_lives--;
                if (this.num_lives == 0){
                    System.out.println("You lose :(, word was: " + this.word_to_guess);
                }
                System.out.println(hint + String.format(" - Num Lives %d", this.num_lives));
            }
        }
        catch (IOException e){
            System.err.println("Error while sending data...");
        }
        return hint;
    }

    /**
     * Initiates a new game with the server.
     */
    public void new_game(){
        String resp;
        try{
            this.out.writeUTF(("N|"));
            this.won = false;
            resp = this.in.readUTF();
            word_to_guess = resp;
            System.out.println(resp);
            this.num_lives = 5;
        }
        catch (IOException e){
            System.err.println("Error while contacting server...");
        }
    }

    /**
     * Plays a round of the game, allowing the user to input guesses.
     */
    public void play_round(){
        Scanner sc = new Scanner(System.in);
        String usrGuess;
        while (num_lives > 0){
            System.out.println("Input your guess: ");
            System.out.print("> ");
            usrGuess = sc.nextLine();
            this.guess(usrGuess);
            if (this.won){
                break;
            }
        }
        System.out.println("Go again? Y/N");
        System.out.print("> ");
        usrGuess = sc.nextLine().toUpperCase();
        if (usrGuess.compareTo("Y") == 0){
            this.new_game();
            this.play_round();
        }
        else if(usrGuess.compareTo("N") == 0){
            System.out.println("Thanks for playing!");
            try{
                this.out.writeUTF("Q");
            }
            catch (IOException e){
                System.err.println("Error contacting server...");
            }
            System.exit(0);
        }
        else{
            System.err.println("Invalid option. Terminating...");
            System.exit(-1);
        }
    }

    /**
     * The main method for running the Socket_WordleClient.
     *
     * @param args - Command-line arguments (not used in this example).
     */
    public static void main(String[] args){
        Socket_WordleClient player = new Socket_WordleClient();
        player.connect("localhost", 5000);
        player.new_game();
        System.out.println(player.word_to_guess);
        player.play_round();
    }

}
