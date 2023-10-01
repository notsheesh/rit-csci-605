/**
 * SortedStorage LinkedList implementation using Generic Nodes
 * To achieve Storage of multiple classes.
 *
 * Version 2.0 - First Revision
 * Revision Log - 1.0 - Initial Creation
 *                2.0 - Redefined class to make use of Generic Nodes
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public class SortedStorage implements Comparable<SortedStorage>{

    private Node<Object> head;
    private int length;
    private boolean hasNulls;
    private int numNulls;

    /**
     * Returns the head of the list.
     * @return the head of the list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node<Object> getHead(){
        return this.head;
    }

    /**
     * Boolean checker to see whether or not a list contains null values.
     * @return true if a null value exists in the list, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */

    public boolean includesNull(){
        return this.hasNulls;
    }

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
    public Node<Object> findNodeInList(Object val){
        if (val == null){
            if (this.hasNulls){
                return this.head;
            }
            else{
                return null;
            }
        }
        else{
            // Starting from the head of the list
            Node<Object> currNode = this.head;
            // Ensure that this value can be found in the list.
            if (StorageAnalyser.ofSameType(this, val)){
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
    public boolean delete(Object val){
        Node<Object> target = findNodeInList(val);
        Node<Object> predecessor, successor;
        // If the list is empty, return false.
        if (this.length == 0){
            return false;
        }
        // If no node was found, return false
        if (target == null){
            return false;
        }
        else{
            // Isolate the found node's predecessor and successor
            predecessor = target.prev();
            successor = target.next();
            try{
                // Link the current node's predecessor to
                // The current node's successor, effectively skipping it
                predecessor.setNext(successor);
            }
            // If the node has no predecessors, it is at the head of the list
            catch (NullPointerException e){
                // Make the SortedStorage.head attribute the current node's successor
                this.head = successor;
            }
            try{
                // Backwards-Link the current node's successor to
                // The current node's predecessor
                successor.setPrev(predecessor);
            }
            // If the node has no successors, it is at the end of the list
            catch (NullPointerException e){
                // Do nothing
            }
            // Unlink the target node.
            target.unlink();
            // Decrement the length
            this.length--;
            // If a null value was removed
            if (val == null){
                // Decrement the number of nulls
                this.numNulls--;
                // If the last null was removed, update the hasNulls attribute
                if(this.numNulls == 0){
                    this.hasNulls = false;
                }
            }
            // If deletion was successful, return true
            return true;
        }

    }

    /**
     * Search for a value in the SortedStorage list.
     * @param val - The value to be searched for
     * @return - True if the value was found, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean find(Object val){
        if (val == null){
            // If searching for a null value, return value of hasNulls
            return this.hasNulls;
        }
        else{
            // Otherwise, starting from the head of the list
            Node<Object> currNode = this.head;
            // Ensure the value could possibly be in the list
            if (StorageAnalyser.ofSameType(this, val)){
                // Inspect each node until either the list is exhausted
                // Or the compareTo method returns a value greater than or equal
                //to Zero
                while(currNode.hasNext() && (currNode.compareTo(val) < 0)){
                    currNode = currNode.next();
                }
                // If the compareTo returns zero, the item has been found
                if (currNode.compareTo(val) == 0){
                    return true;
                }
                // Otherwise, the item does not exist in the list
                else{
                    return false;
                }
            }
            else{
                // If an invalid type is being searched for
                // Print a useful error message and return false.
                System.out.println("Wrong type");
                return false;
            }
        }
    }

    /**
     * Method used to handle addition of new items to the list.
     * @param new_val - The value to be added
     * @return True if addition was successful, false otherwise.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean add(Object new_val){
        // Start at the head of the list
        Node<Object> temp, currNode = this.head;

        // If the list is empty, or the value to be inserted is null
        // Make a node containing the new value the head of the list.
        if (this.length == 0){
            this.head = new Node<Object>(new_val);
            this.length++; // Increment length
            if (new_val == null){ // And update the value of hasNulls
                this.hasNulls = true;
                this.numNulls++;
            }
            // Return true
            return true;
        }
        // Perform the same operations if the list is not empty but
        // The new payload is null
        else if (new_val == null){

            temp = new Node<Object>(null);
            currNode.setPrev(temp);
            this.head = temp;
            this.hasNulls = true;
            this.numNulls++;
            temp.setNext(currNode);
            return true;
        }
        // Otherwise, ensure homogeneity of List elements using the
        // StorageAnalyser class
        else if (StorageAnalyser.ofSameType(this, new_val)){
            // If the value can possibly be added to this list
            // Start at the head and continue until you find the insertion position
            while ((currNode.compareTo(new_val) < 0) && currNode.hasNext()){
                currNode = currNode.next();
            }
            // When the position is found
            temp = new Node<Object>(new_val);
            // Do not add the item if it already exists in the list
            if (currNode.compareTo(new_val) == 0){
                return false;
            }
            // If the item does not exist in the list, retreat one step
            // to find the appropriate insertion position if possible
            if (currNode.compareTo(new_val) > 0){
                if (currNode.hasPrev()){
                    currNode = currNode.prev();
                }
                else{
                    // If the node has no predecessor, make the newNode the
                    // head of the SortedStorage list
                    currNode.setPrev(temp);
                    temp.setNext(currNode);
                    this.head = temp;
                    // Increment length and return true
                    this.length++;
                    return true;
                }
            }
            // Otherwise insert the Node in its appropriate location
            if (currNode.hasNext()){
                temp.setNext(currNode.next());
                currNode.next().setPrev(temp);
            }
            currNode.setNext(temp);
            temp.setPrev(currNode);
            this.length++; // Increment the length
            return true; // And indicate a successful insertion
        }
        else{
            // In all other cases, return false
            return false;
        }
    }

    /**
     * Implementation of the Comparable.compareto() method.
     * @param s the object to be compared.
     * @return the integer difference of their two hashCodes.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int compareTo(SortedStorage s){
        return this.hashCode() - s.hashCode();
    }

    /**
     * Implementation of the Object.toString() method.
     * @return A string in the format: "HEAD:->[items]-->END."
     * Representing all the items contained in this SortedStorage object.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public String toString(){
        String res = "HEAD -->";
        Node<Object> currNode = this.head;

        while (currNode != null){
            res += currNode + " --> ";
            currNode = currNode.next();
        }

        res += "END.";

        return res;
    }
}
