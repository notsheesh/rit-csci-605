import java.util.Comparator;
/**
 * MyComparator is a generic class that implements the Comparator interface for comparing objects of type T.
 * It reverses the natural order of comparison for elements of type T.
 *
 * @param <T> The type of elements to be compared, which must implement the Comparable interface.
 */
public class MyComparator<T extends Comparable<T>> implements Comparator<T> {
    /**
     * Compares two objects of type T in reverse order.
     *
     * @param a The first object to be compared.
     * @param b The second object to be compared.
     * @return a negative integer if a is greater than b, a positive integer if b is greater than a, or 0 if they are equal.
     */
    @Override
    public int compare(T a, T b) {
        // Reverse the order
        return b.compareTo(a);
    }
}