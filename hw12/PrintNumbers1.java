class NumberPrinter implements Runnable {
    private static int id;
    private static int round;
    private static int soOften;
    private static Object lock = new Object();

    public NumberPrinter(int id, int round, int soOften) {
        this.id = id;
        this.round = round;
        this.soOften = soOften;
    }

    @Override
    public void run() {
        for (int i = 0; i < round; i++) {
            synchronized (lock) {
                while (id != i % soOften) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(id);
                id = (id + 1) % soOften;
                lock.notifyAll();
            }
        }
    }
}

public class PrintNumbers1 {
    public static void main(String[] args) {
        int nThreads = Integer.parseInt(args[1]);
        int soOften = Integer.parseInt(args[3]);
        int rounds = 2; // Fixed number of rounds for simplicity

        Thread[] threadArr = new Thread[nThreads];

        for (int i = 0; i < nThreads; i++) {
            threadArr[i] = new Thread(new NumberPrinter(i, rounds, soOften));
            threadArr[i].start();
        }

        for (Thread thread : threadArr) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
