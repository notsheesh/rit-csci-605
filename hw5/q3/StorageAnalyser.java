/**
 * Helper class used to Analyse certain attributes of a particular SortedStorage object.
 * Version Log - 1.0 - Initial Creation (Oct 1, 2023)
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public class StorageAnalyser {

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
    public static boolean ofSameType(SortedStorage s, Object new_val){
        // Starting at the head of the list
        Node<Object> curr = s.getHead();
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
