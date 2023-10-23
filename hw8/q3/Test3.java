/**
 * Test3 class represents a set of test scenarios for sorting different types of data using MyCollections.
 * It includes methods to test sorting of strings, integers, and doubles in both natural and reverse order.
 *
 * @author Shreesh, Kyle
 */
import java.util.Comparator;

@SuppressWarnings("unchecked")
public class Test3 {

    public static void main(String args[]) {
        new Test3().testString();
        new Test3().testInteger();
        new Test3().testDouble();
    }

    MyList<String> aStringList = new MyList<String>();
    MyList<Integer> aIntegerList = new MyList<Integer>();
    MyList<Double> aDoubleList = new MyList<Double>();
    
    /**
     * Initialize a list of strings and add elements to it for testing.
     */
    public void initString() {
        String[] theStrings = { "a", "c", "b" };
        for (int index = 0; index < theStrings.length; index++) {
            aStringList.add(theStrings[index]);
        }
    }

    /**
     * Test sorting a list of strings in natural order using MyCollections.sort.
     */
    public void testNaturalString() {
        System.out.println("natural before sort - aStringList: " + aStringList);
        MyCollections.sort(aStringList);
        System.out.println("natural after  sort - aStringList: " + aStringList);
    }

    /**
     * Test sorting a list of strings in reverse order using MyCollections.sort and a custom comparator.
     */
    public void testReverseString() {
        System.out.println("reverse before sort - aStringList: " + aStringList);
        Comparator myComparator = new MyComparator();
        MyCollections.sort(aStringList, myComparator);
        System.out.println("reverse after sort - aStringList: " + aStringList);
    }

    /**
     * Initialize a list of integers and add elements to it for testing.
     */
    public void initInteger() {
        Integer[] theIntegers = { 1, 3, 2, 5, 4 };
        for (int index = 0; index < theIntegers.length; index++) {
            aIntegerList.add(theIntegers[index]);
        }
    }

    /**
     * Test sorting a list of integers in natural order using MyCollections.sort.
     */
    public void testNaturalInteger() {
        System.out.println("natural before sort - aIntegerList: " + aIntegerList);
        MyCollections.sort(aIntegerList);
        System.out.println("natural after  sort - aIntegerList: " + aIntegerList);
    }

    /**
     * Test sorting a list of integers in reverse order using MyCollections.sort and a custom comparator.
     */
    public void testReverseInteger() {
        System.out.println("reverse before sort - aIntegerList: " + aIntegerList);
        Comparator myComparator = new MyComparator();
        MyCollections.sort(aIntegerList, myComparator);
        System.out.println("reverse after sort - aIntegerList: " + aIntegerList);
    }

    /**
     * Initialize a list of doubles and add elements to it for testing.
     */
    public void initDouble() {
        Double[] theDoubles = { 1.0, 3.0, 2.0, 5.0, 4.0 };
        for (int index = 0; index < theDoubles.length; index++) {
            aDoubleList.add(theDoubles[index]);
        }
    }

    /**
     * Test sorting a list of doubles in natural order using MyCollections.sort.
     */
    public void testNaturalDouble() {
        System.out.println("natural before sort - aDoubleList: " + aDoubleList);
        MyCollections.sort(aDoubleList);
        System.out.println("natural after  sort - aDoubleList: " + aDoubleList);
    }

    /**
     * Test sorting a list of doubles in reverse order using MyCollections.sort and a custom comparator.
     */
    public void testReverseDouble() {
        System.out.println("reverse before sort - aDoubleList: " + aDoubleList);
        Comparator myComparator = new MyComparator();
        MyCollections.sort(aDoubleList, myComparator);
        System.out.println("reverse after sort - aDoubleList: " + aDoubleList);
    }

    /**
     * Run a set of tests for sorting strings in natural and reverse order.
     */
    public void testString() {
        System.out.println("\t\t===String===");
        Test3 natural = new Test3();
        natural.initString();
        natural.testNaturalString();
        this.printSep();

        Test3 reverse = new Test3();
        reverse.initString();
        reverse.testReverseString();
        this.printSep();
    }

    /**
     * Run a set of tests for sorting integers in natural and reverse order.
     */
    public void testInteger() {
        System.out.println("\t\t===Integer===");
        Test3 natural = new Test3();
        natural.initInteger();
        natural.testNaturalInteger();
        this.printSep();

        Test3 reverse = new Test3();
        reverse.initInteger();
        reverse.testReverseInteger();
        this.printSep();
    }

    /**
     * Run a set of tests for sorting doubles in natural and reverse order.
     */
    public void testDouble() {
        System.out.println("\t\t===Double===");
        Test3 natural = new Test3();
        natural.initDouble();
        natural.testNaturalDouble();
        this.printSep();

        Test3 reverse = new Test3();
        reverse.initDouble();
        reverse.testReverseDouble();
        this.printSep();
    }

    /**
     * Print a separator to visually separate different test cases.
     */
    public void printSep(){
        System.out.printf("%s", "-----------------------------");
        System.out.printf("%s", "-----------------------------\n");
    }

}