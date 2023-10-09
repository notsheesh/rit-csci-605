public class Test {
    public static void testItWithString(SortedStorage<String> aSortedStorage)	{

        String[] toInsert = { "8", null, "1", "2"};
        String[] toDelete = { "1", null, "1"};
        String[] toFind   = { "1", null, "1"};


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toString: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public static void testItWithIntegers(SortedStorage<Integer> aSortedStorage)	{

        Integer[] toInsert = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};
        Integer[] toDelete = {  Integer.valueOf(8), null,  Integer.valueOf(1) };
        Integer[] toFind   = {  Integer.valueOf(8), null,  Integer.valueOf(1),  Integer.valueOf(2)};

        aSortedStorage.notASet();
        System.out.println("---SortedStorage will NOT BEHAVE LIKE A SET---");
        System.out.println("- add(" + 8 + "): "  + aSortedStorage.add(8));
        System.out.println("- add(" + 8 + "): "  + aSortedStorage.add(8));
        System.out.println("- add(" + 8 + "): "  + aSortedStorage.add(8));


        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }


        System.out.println("- delete(8): " + aSortedStorage.delete(8));
        System.out.println("- delete(8): " + aSortedStorage.delete(8));
        System.out.println("- delete(8): " + aSortedStorage.delete(8));

        System.out.println("- toIntegers: "  + aSortedStorage.toString());

    }
    public static void testItWithSortedStorage(SortedStorage aSortedStorage,  SortedStorage[] theSortedStorages)	{
        SortedStorage[] toInsert = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };
        SortedStorage[] toDelete = {  theSortedStorages[0], theSortedStorages[1], null };
        SortedStorage[] toFind   = {  theSortedStorages[0], theSortedStorages[1], null, theSortedStorages[1] };



        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedStorage.includesNull());
        System.out.println("- toIntegers: "  + aSortedStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedStorage.delete(toDelete[index]));
        }
    }
    public void test()	{
        System.out.println("----Test with Strings----\n");
        SortedStorage<String> aSortedStringStorage = new SortedStorage<String>();
        testItWithString(aSortedStringStorage);

        System.out.println("----Test with Integers----\n");
        SortedStorage<Integer> aSortedIntegerStorage = new SortedStorage<Integer>();
        testItWithIntegers(aSortedIntegerStorage);

        System.out.println("----Test with SortedStorage----\n");
        SortedStorage aSortedSortedStorageStorage = new SortedStorage();
        SortedStorage[] theSortedStorages = { aSortedStringStorage, aSortedIntegerStorage, aSortedSortedStorageStorage };
        testItWithSortedStorage(aSortedSortedStorageStorage, theSortedStorages);
    }
    public static void main(String args[] )	{
        new Test().test();
    }
}