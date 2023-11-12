public class Node implements Comparable<String> {
    private String value;
    private Node prev;
    private Node next;

    public Node(String val) {
        this.value = val;
        this.next = null;
        this.prev = null;
    }

    public boolean setNextNode(Node ptrNextNode) {
        boolean success = false;

        this.next = ptrNextNode;
        success = true;

        return success;
    }

    public boolean setPrevNode(Node ptrPrevNode){
        boolean success = false;
        
        this.prev = ptrPrevNode;
        success = true;

        return success;
    }

    public boolean setValue(String value) {
        boolean success = false;

        this.value = value;
        success = true;

        return success;
    }

    public Node getNextNode() {
        return this.next;
    }

    public Node getPrevNode() {
        return this.prev;
    }

    public String getValue() {
        return this.value;
    }

    public boolean hasNext() {
        return this.next != null;
    }

    public boolean hasPrev() {
        return this.prev != null;
    }

    @Override
    public int compareTo(String otherValue) {
        if (otherValue == null){
            return -999999;
        } else {
            return this.getValue().compareTo(otherValue);
        }
    }

    public String toPrettyString(){
        String prevStr = this.hasPrev() ? this.getPrevNode().getValue() : "None";
        String nextStr = this.hasNext() ? this.getNextNode().getValue() : "None";
        String prettyStr = "{prev: " + prevStr +
                ", value: " + this.getValue() +
                ", next: " + nextStr + "}";
        return prettyStr;
    }

    public String toString() {
        return this.getValue();
    }

    public static void main(String[] args) {
        return;
    }

}
