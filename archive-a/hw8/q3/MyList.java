import java.util.ArrayList;
import java.util.List;

/**
 * MyList class is a generic container for storing a list of items of type T.
 * It provides methods to add items to the list and retrieve the underlying list.
 *
 * @param <T> The type of elements stored in the list.
 */
public class MyList<T> {
    private List<T> myList;

    /**
     * Default constructor to initialize the internal list.
     */
    public MyList() {
        this.myList = new ArrayList<>();
    }

    /**
     * Add an item of type T to the list.
     *
     * @param item The item to be added to the list.
     */
    public void add(T item) {
        this.myList.add(item);
    }

    /**
     * Get the underlying list of items.
     *
     * @return The list of items stored in this MyList instance.
     */
    public List<T> getList() {
        return this.myList;
    }

    /**
     * Override of the toString method to provide a custom string representation of the list.
     * The elements are concatenated with spaces, and the count of elements is appended.
     *
     * @return A string representation of the list and its size.
     */
    @Override
    public String toString() {
        String myListToString = "";
        for (int i = 0; i < this.myList.size(); i++) {
            if(i == this.myList.size()-1){
                myListToString += this.myList.get(this.myList.size()-1);
                myListToString += "/" + String.valueOf(this.myList.size());
            } else {
                myListToString += this.myList.get(i) + " ";
            }
        }

        return myListToString;
    }
}
