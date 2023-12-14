/**
 * Counts till 'nThreads', 'soOften' times
 *
 * @filename PrintNumbers.java
 * @version 1.0
 *
 * @date Nov 12, 2023
 * @author Kyle, Shreesh
 */

public class PrintNumbers extends Thread {
    static int lockId;
    static Object[] locks;
    static int totalThreads;
    static int totalRounds;
    static Integer threadCount = 1;
    private String threadId;

    /**
     * Constructor for PrintNumbers thread.
     * @param threadId Identifier for the thread.
     */
    public PrintNumbers(String threadId) {
        this.threadId = threadId;
    }

    /**
     * Run method of the PrintNumbers class.
     */
    public void run() {

        for (int roundIdx = 1; roundIdx <= totalRounds; roundIdx++) {

            if (threadId.equals("0")) {
                System.err.println("--------------");
                System.err.println("# round = " + roundIdx);
                System.err.println("--------------");
            }

            synchronized (locks[lockId]) {
                Object threadSyncLock = locks[lockId];
                try {

                    threadSyncLock.notify();
                    System.out.println(threadId);

                    // Spawn another thread
                    if (threadCount != totalThreads) {
                        PrintNumbers th = new PrintNumbers(threadCount.toString());
                        th.start();
                        threadCount++;
                    }

                    // Set lock id
                    lockId = lockId >= totalThreads - 2 ? 0 : lockId + 1;

                    // Stop if last round
                    if (roundIdx == totalRounds) {
                        break;
                    }

                    threadSyncLock.wait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Main method for executing the PrintNumbers program.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {

        // Error handling
        if (args.length != 4 ||
                !("-nThreads").equals(args[0]) ||
                !("-soOften").equals(args[2])) {

            System.err.println("Incorrect usage.");
            System.err.println("Try: java PrintNumbers -nThreads 3 -soOften 4");
            System.exit(1);

        }

        // Init
        if (args.length == 4 &&
                ("-nThreads").equals(args[0]) &&
                ("-soOften").equals(args[2])) {

            PrintNumbers.totalThreads = Integer.parseInt(args[1]);
            PrintNumbers.totalRounds = Integer.parseInt(args[3]);

            PrintNumbers.locks = new Object[totalThreads - 1];

            for (int threadIdx = 0; threadIdx < totalThreads - 1; threadIdx++) {
                locks[threadIdx] = new Object();
            }

            PrintNumbers.lockId = 0;
            PrintNumbers pnThread = new PrintNumbers("0");
            pnThread.start();
        }

    }
}