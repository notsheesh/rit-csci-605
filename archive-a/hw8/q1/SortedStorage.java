/**
 * SortedStorage LinkedList implementation using Generic Nodes
 * To achieve Storage of multiple classes.
 * Version 3.0 - Second Revision, Oct 8, 2023
 * Revision Log - 1.0 - Initial Creation
 *                2.0 - Redefined class to make use of Generic Nodes
 *                3.0 - Class definition extended to allow for duplicate values (in some cases)
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public class SortedStorage<T extends Comparable<T>> implements StorageAnalyser<T>, Iterable<T>, Comparable<SortedStorage<T>>{

    protected Node<T> head;
    protected int length;
    protected boolean hasNulls;
    private int numNulls;

    private boolean fixed = false;
    private boolean isSet = true;

    /**
     * Returns the head of the list.
     * @return the head of the list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node<T> getHead(){
        return this.head;
    }

    public int getLength(){
        return this.length;
    }

    /**
     * Set the head of a SortedStorage to be the Node specified
     * @param n The Node to be used as the new head of the Storage.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void setHead(Node<T> n){
        this.head = n;
    }

    public boolean isSet(){
        return this.isSet;
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

    public Node<T> findNodeInList(T val){
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
            Node<T> currNode = this.head;
            // Ensure that this value can be found in the list.
            if (StorageAnalyser.ofSameType(this, val)){
                // Continue searching until either the list has been exhausted or
                // The compare to returns a value greater than or equal to 0
                while(currNode.hasNext() && (currNode.compareTo(val) < 0)){
                    currNode = currNode.next_node();
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
     * @throws ListCannotBeModified when an attempt is made to modify a
     * SortedStorage being iterated through.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean delete(T val) throws ListCannotBeModified{
        Node<T> target = findNodeInList(val);
        Node<T> predecessor, successor;
        // If the list is empty, return false.
        if (this.fixed){
            throw new ListCannotBeModified();
        }
        if (this.length == 0){
            return false;
        }
        // If no node was found, return false
        if (target == null){
            return false;
        }
        else{
            // Remove a single occurrence of the target Node
            target.deleteOne();
            // If the last occurrence was removed, remove the Node from the list altogether
            if (target.getOccurrences() == 0){
                predecessor = target.prev_node();
                successor = target.next_node();
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
            }
            // Isolate the found node's predecessor and successor

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
    public boolean find(T val){
        if (val == null){
            // If searching for a null value, return value of hasNulls
            return this.hasNulls;
        }
        else{
            // Otherwise, starting from the head of the list
            Node<T> currNode = this.head;
            // Ensure the value could possibly be in the list
            if (StorageAnalyser.ofSameType(this, val)){
                // Inspect each node until either the list is exhausted
                // Or the compareTo method returns a value greater than or equal
                //to Zero
                while(currNode.hasNext() && (currNode.compareTo(val) < 0)){
                    currNode = currNode.next_node();
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
     * @throws ListCannotBeModified when an attempt is made to modify a
     * SortedStorage being iterated through.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean add(T new_val) throws ListCannotBeModified {
        // Start at the head of the list
        Node<T> temp, currNode = this.head;

        if (this.fixed){
            throw (new ListCannotBeModified());
        }
        // If the list is empty, or the value to be inserted is null
        // Make a node containing the new value the head of the list.
        if ((this.length == 0) && (new_val != null)){
            this.head = new Node<>(new_val);
            this.length++; // Increment length
            // Return true
            return true;
        }
        // The new payload is null don't actually add the node to the list,
        // just keep track of how many null values have been added.
        else if (new_val == null){
            this.hasNulls = true;
            this.numNulls++;
            return true;
        }
        // Otherwise, ensure homogeneity of List elements using the
        // StorageAnalyser class
        else if (StorageAnalyser.ofSameType(this, new_val)){
            // If the value can possibly be added to this list
            // Start at the head and continue until you find the insertion position
            while ((currNode.compareTo(new_val) < 0) && currNode.hasNext()){
                currNode = currNode.next_node();
            }
            // When the position is found
            temp = new Node<>(new_val);
            // Do not add the item if it already exists in the list
            if (currNode.compareTo(new_val) == 0){
                if (this.isSet){
                    return false;
                }
                else{
                    currNode.duplicate();
                    this.length++;
                    return true;
                }
            }
            // If the item does not exist in the list, retreat one step
            // to find the appropriate insertion position if possible
            if (currNode.compareTo(new_val) > 0){
                if (currNode.hasPrev()){
                    currNode = currNode.prev_node();
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
                temp.setNext(currNode.next_node());
                currNode.next_node().setPrev(temp);
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
     * Method used to make an instance of SortedStorage behave like
     * a set. Ensures that each node in the SortedStorage is only set
     * to record one instance of its value.
     *
     * @throws ListCannotBeModified when an attempt is made to modify a
     * SortedStorage being iterated through.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void makeASet() throws ListCannotBeModified {
        this.isSet = true;
        Node<T> currNode = this.head;

        // Traverse the entire list and set each node to
        // have one occurrence
        if (this.fixed){
            throw new ListCannotBeModified();
        }
        do{
            if (currNode != null){
                currNode.setToOne();
            }
        }
        while(currNode.hasNext());
    }

    /**
     * Turn off set behaviours for an instance of SortedStorage
     * (it will now allow duplicate values)
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void notASet(){
        this.isSet = false;
    }

    /**
     * Implementation of the Comparable.compareto() method.
     * @param s the object to be compared.
     * @return the integer difference of their two hashCodes.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int compareTo(SortedStorage<T> s){
        return this.hashCode() - s.hashCode();
    }


    public StorageIterator<T> iterator() {
        return new StorageIterator<T>(this);
    }

    public void fix(){
        this.fixed = true;
    }

    public void unfix(){
        this.fixed = false;
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
        String res = "HEAD --> ";
        Node<T> currNode = this.head;
        for (int x = 0; x< this.numNulls; x++){
            res += "NULL --> ";
        }
        while (currNode != null){
            res += currNode.toString();
            currNode = currNode.next_node();
        }

        res += "END.";

        return res;
    }

    public SortedStorage(){
        this.head = new Node<T>(null);
        this.length = 0;
    }
}
