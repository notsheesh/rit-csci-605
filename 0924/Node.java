/**
 * Description: Helper class representing an individual entry in the larger SortedStorage class.
 *
 * Filename: Node.java
 *
 * Version: 1.0
 * Revision Log:
 *      Version 1.0 - Initial creation. Date: Sept 23, 2023.
 */
public class Node implements Comparable<String>{

    /**
     * <em>Node</em> is a recursively defined data structure. It possesses two
     * references to other nodes, <u>prev</u> and <u>next</u> which keep the instances
     * in the larger SortedStorage linked to each other.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    private String value;
    private Node next;
    private Node prev;

    /**
     * Basic constructor for the Node class. Creates a node that
     * stores the value provided for eventual storage in the SortedStorage
     * class.
     * @param val - A String representing the value to be stored.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node(String val){
        this.value = val;
        this.prev = null;
        this.next = null;
    }

    /**
     * Method used to progress from one Node to its successor.
     * @return The next Node linked to this node.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node nextNode(){
        return this.next;
    }

    /**
     * Method used to progress from one Node to its predecessor.
     * @return The previous Node linked to this node.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node prevNode(){
        return this.prev;
    }

    /**
     * Method used to check to see if a Node is at the end of the list.
     * @return true or false depending on whether or not this Node has
     *          a Node after it in the SortedStorage
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean hasNext(){
        return this.next != null;
    }

    /**
     * Method used to check to see if a Node is at the head of the list.
     * @return true or false depending on whether or not this Node has
     *          a Node before it in the SortedStorage
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean hasPrev(){
        return this.prev != null;
    }

    /**
     * Helper method used to assist the insertion of elements into the
     * SortedStorage.
     * @param newPrev - Node to be placed before this Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void setPrev(Node newPrev){
        this.prev = newPrev;
    }

    /**
     * Helper method used to assist the insertion of elements into the
     * SortedStorage.
     * @param newNext - Node to be placed after this Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void setNext(Node newNext){
        this.next = newNext;
    }

    /**
     * Returns the value stored in this Node
     * @return value - The value stored in this Node.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public String getValue(){
        return this.value;
    }

    /**
     * Removes a Node from the SortedStorage by removing its links
     * to its previous and next Nodes.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void unlink(){
        this.prev = null;
        this.next = null;
    }

    /**
     * Implementation of the Object.toString() method for Node
     * @return a String representation of this Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public String toString(){
        return "NODE: " + this.getValue();
    }

    /**
     * Implementation of compareTo<String> to enable comparison to
     * assist maintaining the sorted property of the Storage.
     * @param val the object to be compared.
     * @return an integer representing the precedence of the String
     * being assessed when compared to the contents of the Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int compareTo(String val){
        // Nodes storing null values are always given the highest precedence
        if (this.value == null){
            return -999;
        }
        else{
            // Otherwise use the String.compareTo provided by the value stored.
            return this.value.compareTo(val);
        }
    }

}
