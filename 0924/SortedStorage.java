/**
 * Description: SortedStorage class implementing a doubly linked list. All items
 * in this list will always be sorted.
 *
 * Filename: SortedStorage.java
 *
 * Version: 1.0
 * Revision Log:
 *      Version 1.0 - Initial creation. Date: Sept 23, 2023.
 */
public class SortedStorage {
    private int length;
    private boolean hasNulls;
    private int numNulls;
    private Node head;

    /**
     * Default constructor for SortedStorage. Initializes all attributes
     * to their default values and this.hasNulls to false.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public SortedStorage(){
        this.hasNulls = false;
        this.numNulls = 0;
        this.length = 0;
        this.head = null;
    }


    /**
     * Returns true if the SortedStorage has null values and false otherwise
     * @return this.hasNulls attribute
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean includesNull(){
        return this.hasNulls;
    }

    /**
     * Method used to handle insertion into the SortedStorage
     * @param value - A String representing the value to be added.
     * @return true or false depending on the success of the operation.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean add (String value){
        boolean success = false;
        //Make a buffer node, and start at the head of the list.
        Node temp, currNode = this.head;

        // If the list is empty, simply make this new Node the head.
        if (this.length == 0){
            temp = new Node(value);
            this.head = temp;
            this.length++;
            if (value == null){
                this.hasNulls = true;
                this.numNulls++;
            }
            return true;
        }
        // If the list is not empty but the value is null
        if (value == null){
            // Insert a null node at the head of the list
            temp = new Node(null);
            currNode.setPrev(temp);
            this.head = temp;
            // Indicate that the list has null values and
            // increment the number of null values.
            this.hasNulls = true;
            this.numNulls++;
            this.length++;
            temp.setNext(currNode);
            return true;
        }
        else{ // For all non-null values
            // Traverse the list until the value being added is lexographically before
            // or equivalent to the value at the current position
            while(currNode.compareTo(value) < 0){
                // If the end of the list has not been reached, progress
                if (currNode.hasNext()){
                    currNode = currNode.nextNode();
                }
                else{ //Otherwise break the loop
                    break;
                }
            }
            // If the value is already in the list, do nothing and return false
            if (currNode.compareTo(value) == 0){
                return false;
            }
            // Otherwise create a new node
            temp = new Node(value);
            if (currNode.compareTo(value) > 0){
                // If the proper insertion position has been passed
                if (currNode.hasPrev()){ // And you're not at the head of the list
                    currNode = currNode.prevNode(); // Retreat one space
                }
                else{ // If you are at the head of the list
                    // Insert the new position at the head of the list
                    currNode.setPrev(temp);
                    temp.setNext(currNode);
                    this.head = temp;
                    this.length++; // And increment the length
                    return true; // Indicate a successful insertion
                }
            }
            if (currNode.hasNext()){ // If you are not at the end of the list
                // Create a link between the current Node successor and the new node
                temp.setNext(currNode.nextNode());
                currNode.nextNode().setPrev(temp);
            }
            // Create a link between the current Node and the temp node.
            currNode.setNext(temp);
            temp.setPrev(currNode);
            this.length++; // Increment the length
            return true; // And indicate a successful insertion
        }
    }

    /**
     * Implementation of the Object.toString() method for SortedStorage
     * @return a String representation of the SortedStorage
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public String toString(){
        Node currNode = this.head;
        String res = String.format("\n=========SortedStorage==========\n" +
                                   "Length: %d\n" +
                                   "Has Nulls: %b\n"+
                                   "Elements:\n", this.length, this.hasNulls);
        // Traverse through each Node in the list and add it's value to the string
        for (int x = 0; x < this.length; x++){
            res += currNode.getValue() + " ";
            if (currNode.hasNext()){
                currNode = currNode.nextNode();
            }
        }

        return res;
    }

    /**
     * Search for a given value in the list.
     * @param val - The value being searched for
     * @return true if the value was found, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean find(String val){
        if (val == null){
            // Attempting to compare null values causes problems.
            // Leverage the hasNulls property of the class to avoid this.
            if (this.hasNulls){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            // Starting from the head
            Node currNode = this.head;
            while (currNode.hasNext()){
                // If the value has been found, return true
                if (currNode.compareTo(val) == 0){
                    return true;
                }
                // If the value is passed but not found, it is not in the list
                if (currNode.compareTo(val) > 0){
                    return false; // Return false
                }
                currNode = currNode.nextNode(); //Proceed to the next node
            }
        }
        // If the end of the list is reached then the item has not been found
        // return false
        return false;
    }

    /**
     * Repeats method logic from SortedStorage.find(), but this time returns the
     * Node found.
     * @param val - The value to be searched for
     * @return the Node containing the value being searched for and null otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node findNodeInList(String val){
        if (this.length == 0){
            return null;
        }
        if (val == null){
            if (this.hasNulls){
                return this.head;
            }
            else{
                return null;
            }
        }
        else{
            Node currNode = this.head;
            while (currNode.hasNext()){
                if (currNode.compareTo(val) == 0){
                    return currNode;
                }
                if (currNode.compareTo(val) > 0){
                    return null;
                }
                currNode = currNode.nextNode();
            }
        }
        return null;
    }

    /**
     * Removes an item from the SortedStorage
     * @param val - the value to be deleted
     * @return - true or false based on the success of the operation
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean delete(String val){
        // Attempt to find the value to be deleted in the list
        Node target = this.findNodeInList(val);
        Node predecessor, successor;
        // If the list is empty, return false
        if (this.length == 0){
            return false;
        }
        // If no node was found, return false
        if (target == null){
            return false;
        }
        else{
            // Otherwise mark the current node's predecessor and successor
            predecessor = target.prevNode();
            successor = target.nextNode();
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
     * Driver function for basic testing
     * @param args No command line arguments processed.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public static void main (String args[]){
        SortedStorage list = new SortedStorage();

        System.out.println(list.add(null));
        System.out.println(list.add("8"));
        System.out.println(list.add(null));
        System.out.println(list);  
        System.out.println(list.delete(null));
        System.out.println(list.delete(null));
        System.out.println(list);

    }
}
