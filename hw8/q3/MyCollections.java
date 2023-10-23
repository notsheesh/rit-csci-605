import java.util.Comparator;
/**
 * MyCollections is a utility class that provides sorting functionality for MyList objects.
 * It contains overloaded methods to perform sorting, including sorting in natural order and
 * sorting with a custom comparator.
 */
public class MyCollections {
    /**
     * Sorts a MyList of elements in natural order using the bubble sort algorithm.
     *
     * @param <T> The type of elements in the MyList, which must implement the Comparable interface.
     * @param list The MyList to be sorted in natural order.
     */
    public static <T extends Comparable<T>> void sort(MyList<T> list) {
        // Collections.sort(list.getList(), null);
        MyCollections.bubbleSort(list, null);
    }

    /**
     * Sorts a MyList of elements using the provided comparator.
     *
     * @param <T> The type of elements in the MyList.
     * @param list The MyList to be sorted.
     * @param comparator A custom comparator for defining the sorting order.
     */
    public static <T> void sort(MyList<T> list, Comparator<T> comparator) {
        // Collections.sort(list.getList(), comparator);
        MyCollections.bubbleSort(list, comparator);
    }

    /**
     * Performs the bubble sort algorithm to sort a MyList in ascending order (natural order).
     *
     * @param <T> The type of elements in the MyList, which must implement the Comparable interface.
     * @param list The MyList to be sorted using the bubble sort algorithm.
     */
    private static <T extends Comparable<T>> void bubbleSort(MyList<T> list) {
        boolean flag;
        do {
            flag = false;
            for (int i = 1; i <list.getList().size(); i++) {
                if (list.getList().get(i - 1).compareTo(list.getList().get(i)) > 0) {
                    T temp = list.getList().get(i - 1);
                    list.getList().set(i - 1, list.getList().get(i));
                    list.getList().set(i, temp);
                    flag = true;
                }
            }
        } while (flag);
    }

    /**
     * Performs the bubble sort algorithm to sort a MyList using a custom comparator for element comparison.
     *
     * @param <T> The type of elements in the MyList.
     * @param list The MyList to be sorted using the bubble sort algorithm.
     * @param comparator A custom comparator for defining the sorting order. If null, natural order is used.
     */
    private static <T> void bubbleSort(MyList<T> list, Comparator<T> comparator) {
        boolean flag;
        do {
            flag = false;
            for (int i = 1; i <list.getList().size(); i++) {
                if (comparator == null) {
                    // Handle the case where the comparator is null
                    if (((Comparable<T>)list.getList().get(i - 1)).compareTo(list.getList().get(i)) > 0) {
                        // Swap list.getList()[i-1] and list.getList()[i]
                        T temp = list.getList().get(i - 1);
                        list.getList().set(i - 1, list.getList().get(i));
                        list.getList().set(i, temp);
                        flag = true;
                    }
                } else {
                    if (comparator.compare(list.getList().get(i - 1), list.getList().get(i)) > 0) {
                        // Swap list[i-1] and list[i]
                        T temp = list.getList().get(i - 1);
                        list.getList().set(i - 1, list.getList().get(i));
                        list.getList().set(i, temp);
                        flag = true;
                    }
                }
            }
        } while (flag);
    }

}