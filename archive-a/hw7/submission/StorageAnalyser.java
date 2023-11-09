/**
 * Interface representing an arbitrary Storage class. Defines methods that
 * must be implemented as well as some static methods of all Storage type
 * classes which will implement this interface.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public interface StorageAnalyser {

    /**
     * Returns the head of the list.
     * @return the head of the list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    static Node getHead(SortedStorage s){
        return s.head;
    }

    static int getLength(SortedStorage s){
        return s.length;
    }

    /**
     * Boolean checker to see whether or not a list contains null values.
     * @return true if a null value exists in the list, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    static boolean includesNull(SortedStorage s){
        return s.hasNulls;
    }

    /**
     * Set the head of a SortedStorage to be the Node specified
     * @param n The Node to be used as the new head of the Storage.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void setHead(Node n);

    /**
     * Removes duplicate values from a sorted storage and prevent it
     * from storing other duplicate values.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void makeASet();

    /**
     * Allow a SortedStorage to store duplicate values.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    void notASet();

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
    static Node findNodeInList(SortedStorage s, Comparable val){
        if (val == null){
            if (StorageAnalyser.includesNull(s)){
                return StorageAnalyser.getHead(s);
            }
            else{
                return null;
            }
        }
        else{
            // Starting from the head of the list
            Node currNode = StorageAnalyser.getHead(s);
            // Ensure that this value can be found in the list.
            if (StorageAnalyser.ofSameType(s, val)){
                // Continue searching until either the list has been exhausted or
                // The compare to returns a value greater than or equal to 0
                while(currNode.hasNext() && (currNode.compareTo(val) < 0)){
                    currNode = currNode.next();
                }
                // If compareTo returns 0, the node has been found, return it
                if (currNode.compareTo(val) == 0){
                    return currNode;
                }
                // Otherwise no such value exists in the list, return null
                else{
                    return null;
                }
            }
            else{
                // If an invalid type is being searched for,
                // Give a valid error message.
                System.out.println("Wrong type");
                return null;
            }
        }
    }

    /**
     * Attempt to remove an object from the SortedStorage
     * @param val - The value to be removed
     * @return - True if deletion was successful, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean delete(Comparable val);

    /**
     * Search for a value in the SortedStorage list.
     * @param val - The value to be searched for
     * @return - True if the value was found, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    boolean find(Comparable val);

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
    boolean add(Comparable new_val);

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
        Node curr = StorageAnalyser.getHead(s);
        // Null values can always be inserted
        if (new_val == null){
            return true;
        }
        // If the value is non-null, search the list for the first
        // non-null payload Node
        while ((curr.getValue() == null) && curr.hasNext()){
            curr = curr.next();
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
