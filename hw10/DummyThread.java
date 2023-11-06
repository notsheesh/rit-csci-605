/**
 * Description: Thread subclass that will fill one 4x4 square out of the
 * BufferedImage object passed to it.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */

import java.awt.*;
import java.awt.image.BufferedImage;


public class DummyThread extends Thread{

    private static final int SQUARE_LENGTH = 3; //Size of the squares being filled
    private final BufferedImage img;
    private final int orig_x;
    private final int orig_y;
    private final Color paint; //Colour being filled

    private final int id;

    //Constructor
    public DummyThread(BufferedImage img, int orig_x, int orig_y, Color paint, int id){
        this.img = img;
        this.orig_x = orig_x;
        this.orig_y = orig_y;
        this.paint = paint;
        this.id = id;
    }

    /**
     * run() method which (when invoked) fills out a SQUARE_LENGTH*SQUARE_LENGTH grid with the
     * colour specified in the constructor
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void run(){
        int row = 0;
        int col = 0;

        for (row = this.orig_x; row < this.orig_x+SQUARE_LENGTH; row++){
            for (col = this.orig_y; col < this.orig_y+SQUARE_LENGTH; col++){
                try{
                    this.img.setRGB(row, col, this.paint.getRGB());
                }
                // Do not attempt to fill in squares outside the image
                catch(ArrayIndexOutOfBoundsException e){
                    System.err.printf("%d, %d\n", row, col);
                }
            }
        }
        // Used to display thread order
        System.err.printf("Thread %d complete\n", this.id);
    }
}