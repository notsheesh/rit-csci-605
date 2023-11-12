public class SortedStorage {
    private int length;
    private Node head;
    private boolean hasNulls;
    private int numNulls;
    public boolean DEBUG;

    public SortedStorage(boolean DEBUG) {
        this.head = null;
        this.length = 0;
        this.DEBUG = DEBUG;
    }

    public SortedStorage() {
        this.head = null;
        this.length = 0;
        this.DEBUG = false;
    }

    private boolean isEmpty() {
        return this.length == 0 && this.head == null;
    }

    private boolean setHead(Node node) {
        boolean success = false;
        this.head = node;
        success = true;
        return success;
    }

    private Node getHead() {
        return this.head;
    }

    public void chk(String msg) {
        if (this.DEBUG == false)
            return;

        String sep = "-------------------------------------------------";
        System.out.println(sep);
        System.out.println(msg);
        System.out.println(sep);
    }

    public void chk(String msg, boolean isSep) {
        if (this.DEBUG == false)
            return;

        String sep = "-------------------------------------------------";
        if (isSep)
            System.out.println(sep);
        System.out.println(msg);
        if (isSep)
            System.out.println(sep);
    }

    private boolean prepend(String value) {
        boolean success = false;
        Node newNode = new Node(value);

        // If LL empty
        newNode.setPrevNode(this.getHead());
        this.setHead(newNode);

        return success;

    }

    public boolean delete(String value) {
        boolean success = false;

        Node cursor = this.getHead();

        while (cursor != null && cursor.compareTo(value) == 0) {
            cursor = cursor.getNextNode();
        }

        if (cursor.getValue().compareTo(value) == 0) {
            // If first node
            if (!cursor.hasPrev() && cursor == this.getHead()) {
                this.setHead(cursor.getNextNode());
                cursor.getNextNode().setPrevNode(null);
                success = true;
            }

            // If last node
            else if (!cursor.hasNext() && cursor.getPrevNode() == null) {
                cursor.getPrevNode().setPrevNode(null);
                success = true;
            }

            // If in between
            else {
                Node before = cursor.getPrevNode();
                Node after = cursor.getNextNode();
                before.setNextNode(after);
                after.setPrevNode(before);
                success = true;
            }

        } else {
            // Element doesn't exist
            success = false;
        }

        if (value == null) {
            chk("Deleted value was a null");
            this.numNulls--;
            this.hasNulls = false;
        }

        this.length--;
        return success;

    }

    public boolean add(String value) {

        boolean success = false;
        Node newNode = new Node(value);

        if (value == null) {
            chk("Adding 'null' value");
            newNode.setNextNode(this.getHead());
            this.setHead(newNode);
            success = true;

        } else {
            if (this.isEmpty()) {
                // Storage empty

                chk("Storage empty. Adding first node: " + newNode.toString());
                assert this.getHead() == null;
                this.setHead(newNode);
                success = true;
                chk("Node added: " + newNode.toPrettyString());

            } else {
                // Storage not empty
                assert this.getHead() != null && this.isEmpty() == false;

                chk("Storage not empty. Finding target node.");
                Node cursor = this.getHead().getNextNode();

                if (this.hasNulls) cursor = cursor.getNextNode();

                // head -> alpha -> beta -> gamma (*) -> delta
                while (cursor.compareTo(value) < 0 && cursor.hasNext()) {
                    cursor = cursor.getNextNode();
                }

                chk("Cursor: " + cursor.toString());
                if (cursor.compareTo(value) > 0) {
                    // Put behind cursor node if value < cursor

                    // If head is not behind
                    if (cursor.hasPrev()) { // before -> new -> after (cursor)
                        chk("Adding node behind '" + cursor.toString() + "' node");
                        newNode.setPrevNode(cursor.getPrevNode()); // before <- new
                        newNode.setNextNode(cursor); // new -> after
                        cursor.getPrevNode().setNextNode(newNode); // before -> new
                        cursor.setPrevNode(newNode); // new <- after
                        success = true;
                    }

                    // If first node, ie head is behind
                    else {
                        chk("Adding node behind first node: " + cursor.toString());
                        newNode.setNextNode(cursor);
                        cursor.setPrevNode(newNode);
                        this.setHead(newNode);
                        success = true;
                    }

                } else if (cursor.compareTo(value) < 0) {
                    // Put after cursor node if value > cursor
                    // If not last node
                    if (cursor.hasNext()) { // before (cursor) -> new -> after
                        chk("Adding node after '" + cursor.toString() + "' node");
                        newNode.setPrevNode(cursor); // before <- new
                        newNode.setNextNode(cursor.getNextNode()); // new -> after
                        cursor.getNextNode().setPrevNode(newNode); // new <- after
                        cursor.setNextNode(newNode); // before -> new
                        success = true;
                    }

                    // If last node
                    else { // before -> new -> null
                        chk("Adding after last node: " + cursor.toString());
                        cursor.setNextNode(newNode);
                        newNode.setPrevNode(cursor);
                        success = true;
                    }

                } else {
                    // do nothing
                    success = false;
                    return success;
                }
            }

        }
        // Housekeeping if value is null
        if (value == null) {
            chk("Info: Added null value");
            this.numNulls++;
            this.hasNulls = true;
        }

        this.length++;
        return success;
    }

    public String toString() {
        String prettyStr = "SortedStorage: ";
        Node cursor = this.getHead();
        if (this.isEmpty()) {
            return prettyStr + "empty";
        } else {
            while (cursor != null) {
                prettyStr += cursor.toString();
                if (!cursor.hasNext()) {
                } else
                    prettyStr += " -> ";
                cursor = cursor.getNextNode();
            }
        }
        assert cursor.hasNext() == true;
        // prettyStr += "null";
        return prettyStr;
    }

    public static void main(String[] args) {
        SortedStorage ss;

        if (args.length > 0 && Integer.valueOf(args[0]).equals(1)) {
            ss = new SortedStorage(true);
        } else {
            ss = new SortedStorage(false);
        }

        ss.chk(ss.toString());

        String[] words = {
                null,
                "G",
                "F",
                "A",
                "B",
                "E",
                "D",
                "C",
                null,
                "B",
        };

        System.out.println("Adding words to SortedStorage");
        for (String word : words) {
            ss.chk("Word: " + word);
            boolean result = ss.add(word);
            ss.chk(ss.toString());
        }
        System.out.println("Task completed succesfully");
        ss.chk(ss.toString());
        ss.chk("Length: " + String.valueOf(ss.length));
        ss.chk("Has Nulls? " + String.valueOf(ss.hasNulls));
    }
}