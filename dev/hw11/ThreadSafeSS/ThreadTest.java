public class ThreadTest {

    public static void main(String[] args) {
        // Create a SortedStorage object
        SortedStorage<Integer> sortedStorage = new SortedStorage<>();

        // Number of threads to run concurrently
        int numThreads = 5;

        // Create an array to hold threads
        Thread[] threads = new Thread[numThreads];

        // Concurrently insert elements
        for (int i = 0; i < numThreads; i++) {
            final int element = i;
            threads[i] = new Thread(() -> {
                try {sortedStorage.add(element);} catch (Exception e) {}
            });
            threads[i].start();
        }

        // Wait for all threads to finish insertion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check if the storage is correctly sorted after insertion
        if (isSorted(sortedStorage)) {
            System.out.println("Test Passed: Storage is sorted after insertion.");
        } else {
            System.out.println("Test Failed: Storage is not sorted after insertion.");
        }

        System.out.println();
        System.out.println(sortedStorage);
        System.out.println();

        // Concurrently delete elements
        for (int i = 0; i < numThreads; i++) {
            final int element = i;
            threads[i] = new Thread(() -> {
                try {sortedStorage.delete(element);} catch (Exception e) {}
            });
            threads[i].start();
        }

        // Wait for all threads to finish deletion
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check if the storage is empty after deletion
        if (sortedStorage.length == 0) {
            System.out.println("Test Passed: Storage is empty after deletion.");
        } else {
            System.out.println("Test Failed: Storage is not empty after deletion.");
        }

        System.out.println();
        System.out.println(sortedStorage);
        System.out.println();
        
    }

    private static boolean isSorted(SortedStorage<Integer> storage) {
        // Check if the storage is sorted
        Integer prev = null;
        for (Integer current : storage) {
            if (prev != null && current < prev) {
                return false;
            }
            prev = current;
        }
        return true;
    }
}
