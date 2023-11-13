/**
 * This program simulates the movie clip:
 * <a href="https://www.youtube.com/watch?v=YzaWAEhFWpw">here</a>.
 * It creates n threads representing n-1 students and a master, each trying to retrieve
 * a pebble from the master's hand one at a time. At the end, it tallies the number of stones
 * each player holds (as a sanity check). <br>
 * Usage: java StoneGame [-nPlayers {int} -nRounds {int} -nPebbles {int}]
 * Default values:
 *      nPlayers - 5
 *      nRounds  - 3
 *      nPebbles - 3
 *
 * @filename StoneGame.java
 * @version 1.0
 *
 * @author Kyle Burke
 * @author Shreesh tripathi
 *
 * @date Nov 12, 2023
 */

import java.util.ArrayList;

public class StoneGame extends Thread{

    private static Integer pebbles = 1; //Static object which all threads will sync on
    public static boolean QUIET = false;

    /**
     * Prepare the list of players and the stones to be retrieved
     * @param numPlayers the number of players to be generated
     * @param numPebbles the number of pebbles the master is holding
     * @return a list of players in the game
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static ArrayList<Player> setup(int numPlayers, int numPebbles){
        // Create a list of player objects and return them
        ArrayList<Player> players = new ArrayList<>();
        pebbles = numPebbles;
        for (int x = 0; x < numPlayers; x++){
            players.add((new Player(x)));
        }
        return players;
    }

    /**
     * Play one round of the game.
     * @param players the list of players in the game
     */
    public static void playGame(ArrayList<Player> players){
        Thread[] threads = new Thread[players.size()];

        for (int x = 0; x < threads.length; x++){
            threads[x] = new Thread(players.get(x));
            // Create a thread object and instantly send it to the scheduler
            threads[x].start();
        }

        try{
            // Sleep to make reasonably sure that all player threads
            // will be waiting when notifyAll is eventually called
            sleep(100);
        }
        catch (InterruptedException e){

        }
        // wake up all threads
        Player.release_rock();
        for (Thread t: threads){
            try{
                // If a thread slipped through the cracks, ignore it
                t.join(200);
            }
            catch (InterruptedException e){

            }
        }
        // Display the results of the round
        if(!QUIET){
            for (Player p : players){
                System.out.println(p);
            }
        }
    }

    /**
     * Runs the game numRounds times and returns the total number
     * of stones caught by each player
     * @param numStones the number of stones the "master" holds each round
     * @param numRounds the number of times the game is played
     * @param numPlayers the number of players (includes the master)
     * @return the total number of stones each player has after all rounds
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static int simulate(int numStones, int numRounds, int numPlayers){
        ArrayList<Player> players = setup(numPlayers, numStones);
        int currRound, recoveredStones = 0;

        for (currRound = 0; currRound < numRounds; currRound++){
            Player.set_stones(numStones);
            if(!QUIET){
                System.out.printf("Round %d...\n", currRound+1);
            }
            playGame(players);
        }

        for (Player p : players) {
            recoveredStones += p.get_wins();
        }

        return recoveredStones;
    }
    public static void main(String[] args){
        int players = 5, init_stones = 3, rounds = 3, recovered_stones;

        // Parsing arguments
        for (int x =0; x< args.length; x++){
            if (args[x].compareTo("-nPlayers") == 0){
                try{
                    players = Integer.parseInt(args[x+1]);
                }
                // Generalized exception block to handle both NumberFormat and ArrayOutOfBounds
                catch (Exception e){
                    players = 5;
                }
            }
            if (args[x].compareTo("-nRounds") == 0){
                try{
                    rounds = Integer.parseInt(args[x+1]);
                }
                // Generalized exception block to handle both NumberFormat and ArrayOutOfBounds
                catch (Exception e){
                    rounds = 5;
                }
            }
            if (args[x].compareTo("-nPebbles") == 0){
                try{
                    init_stones = Integer.parseInt(args[x+1]);
                }
                // Generalized exception block to handle both NumberFormat and ArrayOutOfBounds
                catch (Exception e){
                    init_stones = 3;
                }
            }
            if (args[x].compareTo("-quiet") == 0){
                QUIET = true;
            }
        }

        // Simulate the game
        recovered_stones = simulate(init_stones, rounds, players);

        // Ensure no stones were lost
        if (recovered_stones == init_stones*rounds){
            System.out.println("All stones accounted for.");
        }
        else{
            System.out.printf("%d stones recovered.\n", recovered_stones);
        }
    }
}
