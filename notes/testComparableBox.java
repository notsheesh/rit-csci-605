class ComparableBox<T extends Comparable<T>> implements Comparable<ComparableBox<T>> {
    private T value;

    public ComparableBox(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public int compareTo(ComparableBox<T> other) {
        return this.value.compareTo(other.getValue());
    }

}

class Box<T extends Comparable<T>> implements Comparable<Box<T>> {
    T length;
    T width;

    public Box(T length, T width) {
        this.length = length;
        this.width = width;
    }

    public T getLength() {
        return this.length;
    }

    public T getWidth() {
        return this.width;
    }

    @Override
    public int compareTo(Box<T> other) {
        int lengthComparison = this.getLength().compareTo(other.getLength());
        if (lengthComparison != 0){
            return lengthComparison;
        } else {
            return this.getWidth().compareTo(other.getWidth());
        }
    }
}

public class testComparableBox {
    public static void main(String args[]) {

        ComparableBox<Box<Integer>> box1 = new ComparableBox<>(new Box<Integer>(1,1));
        ComparableBox<Box<Integer>> box2 = new ComparableBox<>(new Box<Integer>(5,1));

        System.out.println(box1.compareTo(box2));
    }
}
