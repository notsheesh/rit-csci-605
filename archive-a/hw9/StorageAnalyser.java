/**
 * Interface representing an arbitrary Storage class. Defines methods that
 * must be implemented as well as some static methods of all Storage type
 * classes which will implement this interface.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public interface StorageAnalyser<T extends Comparable<T>> {

    /**
     * Returns the head of the list.
     * @return the head of the list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    Node<T> getHead();

    int getLength();

    /**
     * Boolean checker to see whether or not a list contains null values.
     * @return true if a null value exists in the list, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean includesNull();

    /**
     * Set the head of a SortedStorage to be the Node specified
     * @param n The Node to be used as the new head of the Storage.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void setHead(Node<T> n);

    /**
     * Removes duplicate values from a sorted storage and prevent it
     * from storing other duplicate values.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void makeASet() throws ListCannotBeModified;

    /**
     * Allow a SortedStorage to store duplicate values.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void notASet() throws ListCannotBeModified;

    /**
     * Finds a particular Node in the list. Helper function to
     * SortedStorage.delete()
     * @param val - The value to be deleted
     * @return - The Node containing the value if found in the list.
     *           null if no such Node was found.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    Node<T> findNodeInList(T val);

    /**
     * Attempt to remove an object from the SortedStorage
     * @param val - The value to be removed
     * @return - True if deletion was successful, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean delete(T val) throws ListCannotBeModified;

    /**
     * Search for a value in the SortedStorage list.
     * @param val - The value to be searched for
     * @return - True if the value was found, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean find(T val);

    /**
     * Boolean checker for the Storage's behaviour
     * @return  true if the Storage behaves like a set and false otherwise
     */
    boolean isSet();



    /**
     * Method used to handle addition of new items to the list.
     * @param new_val - The value to be added
     * @return True if addition was successful, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean add(T new_val) throws ListCannotBeModified;

    /**
     * Checks to ensure that the object one is attempting to store/find in this SortedStorage
     * Object is of the same type as other objects currently being stored.
     * @param s - The SortedStorage object being analysed.
     * @param new_val - The value being added/searched for
     * @return - True if the object in question is of the same type as the objects
     * currently in the SortedStorage, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    static boolean ofSameType(SortedStorage s, Comparable new_val){
        // Starting at the head of the list
        Node curr = s.getHead();
        // Null values can always be inserted
        if (new_val == null){
            return true;
        }
        // If the value is non-null, search the list for the first
        // non-null payload Node
        while ((curr.getValue() == null) && curr.hasNext()){
            curr = curr.next_node();
        }
        // If the list already has non-null values
        if (curr.getValue() != null){
            // Ensure that those values and the new object share the same class
            return curr.getValue().getClass().equals(new_val.getClass());
        }
        else{
            // If the list contains entirely null values,
            // return true
            return true;
        }
    }
}
