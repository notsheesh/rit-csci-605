/**
 * DiskDump - A simple utility to copy data from an input file to an output file, with options to skip blocks and specify block size.
 * Version 1.0, Oct 28, 2023
 * Version 2.0, Oct 29, 2023
 *
 * This program allows you to copy data from an input file to an output file, providing options to skip a certain number of blocks and define the block size.
 *
 * @author Kyle Burke, Shreesh Tripathi
 * @version 2.0
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class DiskDump {
    static boolean DEBUG = false;

    /**
     * Parses an argument of the form "key=value" and returns the value part.
     *
     * @param arg - The argument in the form "key=value".
     * @return The value part extracted from the argument.
     */
    private String parseArg(String arg) {
        String[] parts = arg.split("=");
        return parts[1];
    }

    /**
     * Copies data from an input file to an output file while allowing skipping a specified number of blocks and defining the block size.
     *
     * @param inputFile - The path to the input file.
     * @param outputFile - The path to the output file.
     * @param skip - The number of blocks to skip.
     * @param blockSize - The size of each data block.
     * @return The number of blocks successfully copied.
     * @throws IOException if an I/O error occurs during the copy process.
     */
    private long copyAndPaste(
            String inputFile,
            String outputFile,
            int skip,
            int blockSize
            ) throws IOException {

        long numBlockCopied = 0;
        try (
                // Read
                BufferedReader reader = inputFile.equals("-") ? 
                new BufferedReader(new InputStreamReader(System.in)) : 
                new BufferedReader(new FileReader(inputFile));
            
                // Write
                BufferedWriter writer = outputFile.equals("-") ? 
                new BufferedWriter(new OutputStreamWriter(System.out)) : 
                new BufferedWriter(new FileWriter(outputFile));
                
            ) {

            int lenBlockSizeBuffer = 0;
            char[] blockSizeBuffer = new char[blockSize];
            long numBlockSkipped = 0;

            // Skip <skip> blocks of size <blockSize>
            while (numBlockSkipped < skip) {
                long numBlockLeftToSkip = skip - numBlockSkipped;
                long skipped;
                if (numBlockLeftToSkip < blockSize) {
                    skipped = reader.skip(numBlockLeftToSkip);
                } else {
                    skipped = reader.skip(blockSize);
                }
                if (skipped == 0) {
                    break;
                }
                numBlockSkipped += skipped;
            }

            // Take reader -> [blockSizeBuffer] = lenBlockSizeBuffer -> write
            while ((lenBlockSizeBuffer = reader.read(blockSizeBuffer)) > 0) {
                writer.write(blockSizeBuffer, 0, lenBlockSizeBuffer);
                numBlockCopied++;
                if (DiskDump.DEBUG == true){
                    return numBlockCopied;
                }
            }

        }
        return numBlockCopied;
    }

    /**
     * The main method for running the DiskDump utility.
     *
     * @param args - Command-line arguments in the form "key=value".
     */
    public static void main(String[] args) {
        // Check args
        if (args.length != 4) {
            for (String arg : args) {
                if (arg.contains("=") == false) {
                    System.out.println("Usage: java DiskDump <inputFile> <outputFile> <skip> <blockSize>");
                    System.exit(0);
                }
            }
        } else {
            // Get and parse args
            DiskDump dd = new DiskDump();

            String inputFile = dd.parseArg(args[0]);
            String outputFile = dd.parseArg(args[1]);
            int skip = Integer.parseInt(dd.parseArg(args[2]));
            int blockSize = Integer.parseInt(dd.parseArg(args[3]));

            System.out.printf(
                "dd if=%s of=%s skip=%d bs=%d",
                inputFile, outputFile, skip, blockSize
            );

            try {

                long startTime = System.currentTimeMillis();
                long numBlockCopied = dd.copyAndPaste(inputFile, outputFile, skip, blockSize);
                long endTime = System.currentTimeMillis();

                long timeTaken = (endTime - startTime);
                int recordsIn = (int)(skip + numBlockCopied);
                int recordsOut = (int)(numBlockCopied);
                int bytesTransferred = (int)(numBlockCopied * blockSize);
                int bytePerSec;

                if (timeTaken == 0){
                    bytePerSec = (int)(bytesTransferred);
                } else { 
                    bytePerSec = (int)(bytesTransferred / timeTaken);
                }
                 

                System.out.printf("\n%d+0 records in\n", recordsIn);
                System.out.printf("%d+0 records out\n", recordsOut);
                System.out.printf(
                    "%d bytes transferred in %d secs (%d byte/sec)\n",
                    bytesTransferred, timeTaken, bytePerSec
                );

            } catch (IOException e) {

                e.printStackTrace();

            }
        }
    }
}