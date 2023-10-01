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
public class Node<T> implements Comparable<T>{

    private T payload;
    private Node<T> prev;
    private Node<T> next;

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
            try{
                // If this Node is storing Strings, use the String.compareTo()
                if ((this.payload instanceof String)){
                    return this.payload.toString().compareTo(other.toString());
                }
                // If this Node is storing Integers, use the Integer.compareTo()
                else if ((this.payload instanceof Integer)){
                    return ((Integer)this.payload).compareTo((Integer)other);
                }
                // If this Node is storing SortedStorage,
                // use the SortedStorage.compareTo
                else if ((this.payload instanceof SortedStorage)){
                    return ((SortedStorage)this.payload).compareTo((SortedStorage)other);
                }
                // If this Node has a null object, give it highest precedence.
                else if ((this.payload == null)){
                    return -999;
                }
                else{
                    // If the value being compared is not of any of the above types
                    // throw an exception
                    throw new InvalidPayloadType();
                }
            }
            // If an exception was thrown, print a useful error message.
            catch (InvalidPayloadType e){
                System.out.printf("Error: Objects of type %s cannot be compared" +
                                  " to Nodes of class <%s>.",
                                  other.getClass(), this.payload.getClass());
            }
        }
        // In all other cases, return a high value.
        return 999;
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
        // If the payload is null, return the string null
        if (this.payload == null){
            return "NULL";
        }
        // If the payload is a SortedStorage, print its hashcode
        // and its items
        else if(this.payload instanceof SortedStorage){
            return "ID: " + this.payload.hashCode() + "\nItems: [" + this.payload + "]\n";
        }
        // Otherwise use the toString implementation in payload's class
        else{
            return this.payload.toString();
        }
    }
}
