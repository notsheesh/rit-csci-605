import java.util.Comparator;
import java.util.Iterator;

public class Test {
    public static void testInteger0(SortedStorage<Integer> aStorageInterfaceInteger)	{
        System.out.println("Test 0");

        try{
            aStorageInterfaceInteger.add(null);

            aStorageInterfaceInteger.add(Integer.valueOf("2"));
            aStorageInterfaceInteger.add(Integer.valueOf("3"));
            aStorageInterfaceInteger.add(Integer.valueOf("4"));
            System.out.println("3: " + aStorageInterfaceInteger);
            StorageIterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();
            while ( aIterator.hasNext() )
                System.out.println("in Test: " + aIterator.next());
            aStorageInterfaceInteger.add(Integer.valueOf("5"));
        }
        catch (ListCannotBeModified e){
            System.err.println(e);
        }
    }
    public static void testInteger1(SortedStorage<Integer> aStorageInterfaceInteger)	{
        System.out.println("Test 2");

        try{
            aStorageInterfaceInteger.add(Integer.valueOf("1"));
            aStorageInterfaceInteger.add(Integer.valueOf("2"));
            aStorageInterfaceInteger.add(Integer.valueOf("3"));
            System.out.println(aStorageInterfaceInteger);
            StorageIterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();
            aIterator.hasNext();
            aIterator.remove();
            System.out.println("in Test removed: " + aIterator.next());
            while ( aIterator.hasNext() )
                System.out.println("in Test: " + aIterator.next());
        }
        catch (ListCannotBeModified e){
            System.err.println(e);
        }
    }
    public static void testInteger2(SortedStorage<Integer> aStorageInterfaceInteger)	{
        System.out.println("Test 1");

        try{
            aStorageInterfaceInteger.add(Integer.valueOf("1"));
            aStorageInterfaceInteger.add(Integer.valueOf("2"));
            StorageIterator<Integer> aIterator =  aStorageInterfaceInteger.iterator();
            aIterator.hasNext();
            aStorageInterfaceInteger.add(Integer.valueOf("3"));
            System.out.println("in Test: " + aIterator.next());
            System.out.println("This line should not be printed");
        }
        catch (ListCannotBeModified e){
            System.err.println(e);
        }

    }
    public static void main(String args[] )	{
        SortedStorage<Integer> aStorageInterfaceInteger = new SortedStorage<Integer>();
        testInteger0(aStorageInterfaceInteger);

        aStorageInterfaceInteger = new SortedStorage<Integer>();
        testInteger1(aStorageInterfaceInteger);

        aStorageInterfaceInteger = new SortedStorage<Integer>();
        testInteger2(aStorageInterfaceInteger);
    }
}