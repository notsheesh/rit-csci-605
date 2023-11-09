import java.util.Iterator;

public class StorageIterator<E extends Comparable<E>> implements Iterator<E> {


    private SortedStorage<E> parentList;
    private Node<E> cursor = new Node<E>();
    private int position = 0;

    /**
     * Default constructor for a StorageIterator object
     * @param s The SortedStorage object to be iterated over.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public StorageIterator (SortedStorage<E> s){
        try{
            this.parentList = s;
            this.cursor.setNext(s.getHead());
        }
        catch(NullPointerException e){
            System.err.println("List is empty;");
        }
    }

    /**
     * Checks to see if there are more elements left
     * in the SortedStorage object.
     * @return true if the end of the list has not been reached.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public boolean hasNext(){

        if (this.cursor.hasNext()){
            this.parentList.fix();
            return true;
        }
        else{
            this.parentList.unfix();
            return false;
        }
    }

    /**
     * Moves the iterator one stage forward in the list
     * and returns that value.
     * @return The value in the node connected to the current cursor position.<br>
     * Returns null if the end of the list has been reached.
     *
     * @throws NullPointerException this method's return value may be null if the end of
     * the list has been reached.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public E next() throws NullPointerException{
        this.cursor = this.cursor.next_node();
        this.position ++;
        if (this.cursor != null){
            E val = cursor.getValue();
            if (val == null){
                return this.next();
            }
            else{
                return val;
            }
        }
        else{
            return null;
        }
    }

    /**
     * Moves the iterator one stage forward in the list
     * and returns that value.
     * @return The value in the node connected to the current cursor position.<br>
     * Returns null if the end of the list has been reached.
     *
     * @throws NullPointerException this method's return value may be null if the end of
     * the list has been reached.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public E prev() throws NullPointerException{
        this.cursor = this.cursor.prev_node();
        this.position ++;
        if (this.cursor != null){
            E val = cursor.getValue();
            if (val == null){
                return this.prev();
            }
            else{
                return val;
            }
        }
        else{
            return null;
        }
    }

    public E get(int index) throws NullPointerException{
        int hops = index - this.position;
        int counter = 0;
        E rVal;

        if (hops > 0){
            for (counter = 0; counter < hops; counter++){
                if (this.position < this.parentList.getLength()){
                    this.next();
                }
                else{
                    System.err.println("Invalid index.");
                    return null;
                }
            }
            rVal = this.cursor.getValue();
            return rVal;
        }
        else if (hops < 0){
            hops *= -1;
            for (counter = 0; counter < hops; counter++){
                if (this.position > 0){
                    this.prev();
                }
                else{
                    System.err.println("Invalid index.");
                    return null;
                }

            }
            rVal = this.cursor.getValue();
            return rVal;
        }
        else if (hops == 0){
            return this.cursor.getValue();
        }
        System.err.println("Invalid index.");
        return null;
    }


    /**
     * Repositions the cursor to the start of the list.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void reset(){
        this.cursor = this.parentList.getHead();
        this.position = 0;
    }

    /**
     * Helper method that prints all the elements in the SortedStorage object.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public void printList(){
        System.out.println("-----printing list with iterator----");
        while(this.cursor != null){
            System.out.print(this.cursor.getValue());
            System.out.print("-->");
            this.next();
        }
        System.out.println("End.");
    }

    /**
     *
     */
    public void remove(){
        try{
            this.cursor.prev_node().setNext(this.cursor.next_node());
        }
        catch (NullPointerException e){
            this.parentList.head = this.cursor.next_node();
        }
        try{
            this.cursor.next_node().setPrev(this.cursor.prev_node());
        }
        catch (NullPointerException e){

        }
        this.parentList.length--;

        this.cursor = this.cursor.next_node();
    }

}
