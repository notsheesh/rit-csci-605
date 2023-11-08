/**
 * file: ImageGenerator.java
 * Description: This program takes a random file of unknown length and
 * generates a mosaic image based on the content found in the file.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 *
 * @date November 5, 2023
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class ImageGenerator{

    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color RED = new Color(255, 0, 0);

    private static final int SQUARE_SIZE = 3;

    private static BufferedImage img = new BufferedImage(330, 330, BufferedImage.TYPE_INT_RGB);


    /**
     * Writes the painted image to file.
     * @param img The BufferedImage object being saved to file
     * @param file_ext the file type (png, jpeg, jpg)
     * @param fileName The name of the output file
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void saveImg(BufferedImage img, String file_ext, String fileName){
        try{
            String filepath = fileName + "." + file_ext;
            File output = new File(filepath);
            ImageIO.write(img, "png", output);
            System.out.println("Saved image to: " + filepath);
        }
        catch (IOException e){
            System.err.println("File could not be created.");
            System.exit(1);
        }

    }

    /**
     * Creates the DummyThread objects that will be responsible for painting
     * the image
     * @param sc The scanner that has the source information used to pain the image
     * @return An ArrayList of DummyThread objects
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static ArrayList<DummyThread> threadCreation(Scanner sc, boolean from_sys_in){
        try{
            ArrayList<DummyThread> threads = new ArrayList<>();

            // Used to check the value provided from sc.next() for even or oddness
            int currPixel = 0;

            // Keep track of current position within the image
            int x = 0;
            int y = 0;

            while (sc.hasNext()){

                try{
                    // Fill even squares with red and odd with blue
                    currPixel = Integer.parseInt(sc.next());
                    if (currPixel % 2 == 0){
                        threads.add(new DummyThread(img, x, y, RED, threads.size()));
                    }
                    else{
                        threads.add(new DummyThread(img, x, y, BLUE, threads.size()));
                    }
                }

                // If there's bad input (like a letter or something) fill it with blue
                catch (NumberFormatException e){
                    threads.add(new DummyThread(img, x, y, BLUE, threads.size()));
                }
                // Skip SQUARE_SIZE pixels
                y += SQUARE_SIZE;

                // If you've reached the end of the image, reset the row
                if (y >= img.getWidth()-SQUARE_SIZE){
                    y = 0;
                    x += SQUARE_SIZE;
                }

                // If you've reached the final edge of the image, break the loop
                if (x > img.getHeight()-SQUARE_SIZE){
                    break;
                }
                if (from_sys_in){
                    System.out.print("> ");
                }
            }
            // Return the list of threads
            return threads;
        }
        catch (Exception e){
            System.err.println("An unknown exception occurred.");
            System.err.printf("Error details: %s", e);
            System.exit(1);
        }
        return null;
    }

    /**
     * Dispatch all the created threads and use them to fill an image
     * @param threads The ArrayList of threads being used to fill the object
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void fillImage(ArrayList<DummyThread> threads){
        if (threads == null){
            System.err.println("Threads not loaded successfully.\nEnding execution...");
            System.exit(500);
        }
        // Send all thread objects to the scheduler
        // And let them run in any order
        for (DummyThread t : threads){
            t.start();
        }

        // Ensure that every thread has been completed or interrupted
        for (DummyThread t: threads){
            try{
                t.join();
            }
            catch (InterruptedException e){
                System.err.println("Thread has been interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Scanner sc;
        ArrayList<DummyThread> threads;
        boolean from_sys_in = false;
        if (args.length > 0){
            try{
                sc = new Scanner(new File(args[0]));
                sc.useDelimiter("");
            }
            catch (FileNotFoundException e){
                System.err.println("File could not be found, input will be taken from STD_IN");
                System.out.print("> ");
                from_sys_in = true;
                sc = new Scanner(System.in);
            }
        }
        else{
            System.err.println("No file found, input will be taken from STD_IN");
            System.out.print("> ");
            sc = new Scanner(System.in);
            from_sys_in = true;
        }

        // Attempt to fill an image using digits from the Scanner
        threads = threadCreation(sc, from_sys_in);
        fillImage(threads);

        //If all goes well, save the image to file
        saveImg(img, "png", "output3");
    }
}