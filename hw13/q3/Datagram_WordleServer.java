/**
 * Datagram_WordleServer - A simple word guessing game server using DatagramSocket for communication with clients.
 * Version 1.0, Dec 3, 2023
 *
 * This program implements a server for a word guessing game using DatagramSocket to communicate with clients.
 * The server loads a dictionary of words, listens for incoming client requests, and assigns a new word to each client.
 * Each client communication is handled in a separate thread for concurrent gameplay.
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

public class Datagram_WordleServer {

    private int port;
    private DatagramSocket serverSocket;
    private ArrayList<String> dictionary;

    /**
     * Constructor for Datagram_WordleServer.
     *
     * @param port - The port number on which the server will listen for client connections.
     */
    public Datagram_WordleServer(int port) {
        this.port = port;
        loadWords("dictionary.txt");
        startServer();
    }

    /**
     * Loads words from a specified file into the server's dictionary.
     *
     * @param filename - The name of the file containing the dictionary words.
     */
    private void loadWords(String filename) {
        this.dictionary = new ArrayList<>();
        String word;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((word = reader.readLine()) != null) {
                this.dictionary.add(word.toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find dictionary file...");
        } catch (IOException e) {
            System.err.println("Error when reading from dictionary file");
        } finally {
            System.out.printf("Loaded %d words from file%n", dictionary.size());
        }
    }

    /**
     * Creates a DatagramSocket and starts the server on the specified port.
     */
    private void startServer() {
        try {
            this.serverSocket = new DatagramSocket(port);
            System.out.println("Server started on port " + port);
        } catch (SocketException e) {
            System.err.println("Error creating DatagramSocket...");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Chooses a random word from the server's dictionary, removing it to avoid repetition.
     *
     * @return A randomly chosen word from the dictionary.
     */
    private String chooseWord() {
        Random rng = new Random();
        String rVal = "";
        int idx = rng.nextInt(this.dictionary.size());

        try {
            rVal = this.dictionary.get(idx);
            this.dictionary.remove(idx);
            if (this.dictionary.isEmpty()) {
                throw new EmptyDictException();
            }
        } catch (EmptyDictException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return rVal;
    }

    /**
     * Listens for incoming client requests, assigns a new word to each client, and handles client communication in separate threads.
     */
    public void play() {
        while (true) {
            try {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                new GameThread(chooseWord(), serverSocket, clientAddress, clientPort, this.dictionary).start();
                Thread.sleep(100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The main method for running the Datagram_WordleServer.
     *
     * @param args - Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        new Datagram_WordleServer(5000).play();
    }
}
