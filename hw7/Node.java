/**
 * Helper class representing individual nodes along the SortedStorage linked list.
 * Updated with Generic class parameter T to facilitate sotring of Strings, Integers,
 * SortedStorage objects.
 * @param <T> The Class of Objects this particular Node will store.
 *
 * Version 2.0 - Date Oct 1, 2023
 * Revision Log -
 *          Version 1.0 - Initial Creation. Nodes only capable of storing Strings
 *          Version 2.0 - First revision. Nodes use Generics to achiever Storage of
 *                        multiple different classes.
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */
public class Node<T extends Comparable<T>>{

    private T payload;
    private Node<T> prev;
    private Node<T> next;

    private int occurrences;

    /**
     * Basic Constructor for the Node class.
     * @param payload - The data being stored.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node(T payload){
        this.payload = payload;
        this.prev = null;
        this.next = null;
        this.occurrences = 1;
    }

    /**
     * Increase the number of occurrences recorded in this Node
     * by One. Used for non-set SortedStorages.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void duplicate(){
        this.occurrences++;
    }

    /**
     * Getter for the number of occurrences in a single node.
     * @return this.occurrences - An int representing the number of times
     * this particular vallue has been stored.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int getOccurrences(){
        return this.occurrences;
    }

    /**
     * Remove a single occurrnece of this Node value from the SortedStorage.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void deleteOne(){
        this.occurrences--;
    }

    /**
     * Remove ALL duplicates of this value. Used to ensure uniqueness
     * in set-type SortedStorages.
     */
    public void setToOne(){
        this.occurrences = 1;
    }

    /**
     * Returns the value stored in the Node
     * @return on Object of type T stored in the Node.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public T getValue(){
        return this.payload;
    }

    /**
     * Returns the Node linked ahead of this specific Node.
     * Will return <em>null</em> if there is no next node.
     *
     * @return the next Node in the sorted list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node<T> next(){
        return this.next;
    }

    /**
     * Removes a Node from the linked list by unlinking it from
     * its predecessor and its successor.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void unlink(){
        this.prev = null;
        this.next = null;
    }

    /**
     * Returns the Node linked behind of this specific Node.
     * Will return <em>null</em> if there is no previous node.
     *
     * @return the previous Node in the sorted list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public Node<T> prev(){
        return this.prev;
    }

    /**
     * Implementation of the compareTo method for this Class. Has different implementations
     * For the different classes Node can store.
     * @param other the object to be compared.
     * @return an integer representing the comparison between two objects.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int compareTo(T other){
        // Highest precedence is given to null objects, so they are always sorted to the
        // head of the list.
        if (other == null){
            return 999;
        }

        else{
            if (this.payload == null){
                return -999;
            }
            else{
                return this.payload.compareTo(other);
            }
        }
    }

    /**
     * Boolean to check whether or not a Node is at the end of the larger list.
     * @return true if the Node has a successor, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean hasNext(){
        return this.next != null;
    }

    /**
     * Assigns a Node as the successor of this Node.
     * @param newNext - The new successor Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void setNext(Node<T> newNext){
        this.next = newNext;
    }

    /**
     * Assigns a Node as the predecessor of this Node.
     * @param newPrev - The new predecessor Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void setPrev(Node<T> newPrev){
        this.prev = newPrev;
    }

    /**
     * Boolean checker to see if a Node is at the head of the larger list.
     * @return - True if the Node has a predecessor, false otherwise
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean hasPrev(){
        return this.prev != null;
    }

    /**
     * Implementation of the toString method for Nodes
     * @return A String representing the data stored in this Node
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public String toString(){
        String rVal = "";
        // If the payload is null, return the string null
        if (this.occurrences > 0){
            if (this.payload == null){
                rVal = "NULL --> ";
            }
            // If the payload is a SortedStorage, print its hashcode
            // and its items

            else{
                // For each stored occurrence
                for (int x = 0; x < this.occurrences; x++ ){
                    // If the Node contains a SortedStorage, print its ID and items
                    if(this.payload instanceof SortedStorage){
                        rVal += "\nID: " + this.payload.hashCode() + "\nItems: [" + this.payload + "]\n";
                    }
                    // Otherwise use the toString implementation in payload's class
                    else{
                        rVal += this.payload.toString();
                        rVal += " --> ";
                    }

                }
            }
            return rVal;
        }
        else{
            return "";
        }
    }
}
