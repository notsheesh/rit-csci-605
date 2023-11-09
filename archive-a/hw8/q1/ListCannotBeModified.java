/**
 * Custom exception thrown when someone tries to modify a list that is
 * being iterated through by adding or deleting an element.
 */
public class ListCannotBeModified extends Exception{

    public String toString(){
        return "Exception - storage has been modified.";
    }
}
