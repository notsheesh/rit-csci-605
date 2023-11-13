/**
 * This class represents a player in the StoneGame.
 * Each player is used to create a thread object and manage the number of
 * stones this player successfully grabbed.
 *
 * @filename Player.java
 * @version 1.0
 *
 * @date Nov 12, 2023
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public class Player implements Runnable{
    private static Integer rocks;
    private int numWins = 0;
    private final int id;

    /**
     * Basic constructor for the players
     * @param id Player ID
     */
    public Player(int id){
        this.id = id;
        if (!StoneGame.QUIET){
            if (this.id == 0){
                System.out.println("Master ready.");
            }
            else{
                System.out.printf("Player %d ready.\n", this.id);
            }
        }
    }

    /**
     * Run method of the Player class.
     */
    public void run() {
        // Only one player can manipulate the rocks object
        // at a time
        synchronized(rocks){
            try{
                // Wait for notify()/notifyAll()
                rocks.wait();
                if (rocks > 0){
                    rocks --;
                    this.numWins ++;
                }
            }
            catch (InterruptedException e){

            }
        }
    }

    public static void release_rock(){

        synchronized(rocks){
            rocks.notifyAll();
        }
    }

    public static void set_stones(int val){
        rocks = val;
    }

    public int get_wins(){
        return this.numWins;
    }

    public String toString(){
        if (this.id == 0){
            return String.format("Player ID: M | Stones Grabbed: %d", this.numWins);
        }
        return String.format("Player ID: %d | Stones Grabbed: %d", this.id, this.numWins);
    }
}
