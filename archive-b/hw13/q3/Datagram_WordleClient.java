/**
 * Datagram_WordleClient - A simple word guessing game client using DatagramSocket for communication with a server.
 * Version 1.0, Dec 3, 2023
 *
 * This program implements a client for a word guessing game using DatagramSocket to communicate with a server.
 * The client connects to the server, guesses words, and receives feedback on the guessed word's correctness.
 * It supports starting a new game, making guesses, and handling game outcomes such as winning or losing.
 *
 * @author Kyle, Shreesh
 * @version 1.0
 */

import java.io.IOException;
import java.net.*;

public class Datagram_WordleClient {

    private static String HOSTNAME = "localhost";
    private String wordToGuess;
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;

    private int numLives = 5;
    private boolean won = false;

    /**
     * Constructor for Datagram_WordleClient.
     *
     * @param serverPort - The port number of the server to connect to.
     */
    public Datagram_WordleClient(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Establishes a connection to the server using DatagramSocket.
     */
    public void connect() {
        try {
            this.socket = new DatagramSocket();
            this.serverAddress = InetAddress.getByName(HOSTNAME);
            System.out.println("Connection successful...");
        } catch (UnknownHostException | SocketException e) {
            System.err.println("Error during connection setup...");
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Sends a word guess to the server and receives feedback.
     *
     * @param word - The word guessed by the player.
     * @return The hint received from the server.
     */
    public String guess(String word) {
        String hint = "_____";
        try {
            byte[] sendData = ("G|" + word).getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            hint = new String(receivePacket.getData(), 0, receivePacket.getLength());

            if (hint.equals("*****")) {
                System.out.println("You win!");
                this.won = true;
            } else {
                this.numLives--;
                if (this.numLives == 0) {
                    System.out.println("You lose :(, word was: " + this.wordToGuess);
                }
                System.out.println(hint + String.format(" - Num Lives %d", this.numLives));
            }
        } catch (IOException e) {
            System.err.println("Error while sending/receiving data...");
        }
        return hint;
    }


    /**
     * Starts a new game by requesting a new word from the server.
     */
    public void newGame() {
        String resp;
        try {
            byte[] sendData = "N|".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            this.won = false;

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            resp = new String(receivePacket.getData(), 0, receivePacket.getLength());
            this.wordToGuess = resp;
            System.out.println(resp);
            this.numLives = 5;
        } catch (IOException e) {
            System.err.println("Error while contacting server...");
        }
    }


    /**
     * Placeholder for the logic to play a round. Currently, it receives a packet from the server.
     */
    public void playRound() {
        try {
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // rest of your playRound() logic remains unchanged
    }

    /**
     * The main method for running the Datagram_WordleClient.
     *
     * @param args - Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        Datagram_WordleClient player = new Datagram_WordleClient(5000);
        player.connect();
        player.newGame();
        System.out.println(player.wordToGuess);
        player.playRound();
    }
}
