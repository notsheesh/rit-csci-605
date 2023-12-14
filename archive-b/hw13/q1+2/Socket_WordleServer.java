/**
 * Socket_WordleServer - A server for the word guessing game using Socket communication.
 * Version 1.0, Dec 3, 2023
 *
 * This program represents a server for the word guessing game using Socket communication.
 * It listens for client connections, assigns a word to each client, and starts a thread to handle each game.
 *
 * @author Kyle, Shreesh
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Socket_WordleServer {

    private String hostname = "localhost";
    private ServerSocket s_sock;
    private boolean ready = false;
    private ArrayList<String> dictionary;

    /**
     * Getter for the ServerSocket.
     *
     * @return The ServerSocket used by the server.
     */
    public ServerSocket getServerSocket(){
        return this.s_sock;
    }

    /**
     * Constructor for Socket_WordleServer.
     *
     * @param port - The port on which the server listens for client connections.
     */
    public Socket_WordleServer(int port){
        try{
            this.s_sock = new ServerSocket(port);
            load_words("dictionary.txt");
        }
        catch (Exception e){
            System.err.println("Unknown error occurred...");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Loads words from a dictionary file into the server's dictionary list.
     *
     * @param filename - The name of the dictionary file.
     */
    public void load_words(String filename){
        this.dictionary = new ArrayList<>();
        String word;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((word = reader.readLine()) != null){
                this.dictionary.add(word.toUpperCase());
            }

        }
        catch (FileNotFoundException e){
            System.err.println("Cannot find dictionary file...");
        }
        catch (IOException e){
            System.err.println("Error when reading from dictionary file");
        }
        finally{
            System.out.printf("Loaded %d words from file", dictionary.size());
            if (!this.dictionary.isEmpty()){
                this.ready = true;
            }
        }
    }


    /**
     * Chooses a word randomly from the server's dictionary.
     *
     * @return The chosen word.
     */
    public String choose_word(){
        Random rng = new Random();
        String rVal = "";
        int idx = rng.nextInt(0, this.dictionary.size());

        try {
            rVal = this.dictionary.get(idx);
            this.dictionary.remove(idx);
            if (this.dictionary.isEmpty()){
                throw new EmptyDictException();
            }
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return rVal;
    }

    /**
     * Accepts client connections and starts a new thread to handle each game.
     */
    public void play(){
        Socket temp;
        while (true){
            try{
                temp = s_sock.accept();
                new GameThread(this.choose_word(), temp, this.dictionary).start();
                Thread.sleep(100);
            }
            catch (Exception e){

            }
        }
    }

    /**
     * The main method for running the Socket_WordleServer.
     *
     * @param args - Command-line arguments (not used in this example).
     */
    public static void main (String[] args){
        new Socket_WordleServer(5000).play();
    }
}
