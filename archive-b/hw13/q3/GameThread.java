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
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class GameThread extends Thread {

    private String answer;
    private DatagramSocket socket;
    private InetAddress clientAddress;
    private int clientPort;
    private ArrayList<String> words;

    /**
     * Constructor for GameThread.
     *
     * @param answer - The word to be guessed by the client.
     * @param socket - The DatagramSocket used for communication with the client.
     * @param clientAddress - The InetAddress of the client.
     * @param clientPort - The port number of the client.
     * @param words - The list of words available for the game.
     */
    public GameThread(String answer, DatagramSocket socket, InetAddress clientAddress, int clientPort, ArrayList<String> words) {
        this.answer = answer;
        this.socket = socket;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.words = words;
    }

    /**
     * The main execution logic of the GameThread.
     */
    public void run() {
        System.out.println("Answer: " + this.answer);
        String msg;

        try {
            // Send the answer to the client
            byte[] sendData = this.answer.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);

            // Sleep for a short time to ensure packets don't get mixed up
            sleep(100);

            // Receive a dummy packet from the client
            byte[] receiveData = new byte[1024];
            DatagramPacket dummyReceivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(dummyReceivePacket);

            while (true) {
                // Receive the actual message from the client
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(msg);

                if (msg.equals("Q")) {
                    // If the client sends a quit message, exit the loop
                    break;
                }

                switch (msg.charAt(0)) {
                    case 'G':
                        // Process guess and send the hint back to the client
                        String processedGuess = processGuess(msg.substring(2));
                        System.out.println(processedGuess);

                        sendData = processedGuess.getBytes();
                        DatagramPacket hintPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        socket.send(hintPacket);
                        break;

                    case 'N':
                        // Choose a new word and send it to the client
                        int idx = new Random().nextInt(0, this.words.size());
                        this.answer = this.words.get(idx);
                        this.words.remove(idx);

                        sendData = this.answer.getBytes();
                        DatagramPacket newWordPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        socket.send(newWordPacket);
                        break;

                    default:
                        break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes the client's guess and generates a hint.
     *
     * @param usrGuess - The user's guessed word.
     * @return The hint generated based on the guessed word.
     */
    private String processGuess(String usrGuess) {
        usrGuess = usrGuess.toUpperCase();
        System.out.println(usrGuess);
        char[] hint = new char[5];

        int x = 0;
        int y = 0;

        for (x = 0; x < 5; x++) {
            hint[x] = '#';
        }

        try {
            for (x = 0; x < usrGuess.length(); x++) {
                for (y = 0; y < usrGuess.length(); y++) {
                    if (usrGuess.charAt(x) == this.answer.charAt(y)) {
                        if (x == y) {
                            hint[x] = '*';
                        } else {
                            if (hint[x] != '*') {
                                hint[x] = '^';
                            }
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid guess.");
            return "INVALID";
        }

        return new String(hint);
    }
}
