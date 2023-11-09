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

    public static void testItWithMusicStorage(SortedStorage<MusicStorageOfThePast> aSortedMusicStorage)	{
        
        MusicStorageOfThePast musicStorage1 = new MusicStorageOfThePast(1985, 10, "Artist 1", "Album A", 37.2f);
        MusicStorageOfThePast musicStorage2 = new MusicStorageOfThePast(1990, 12, "Artist 2", "Album B", 56.2f);
        MusicStorageOfThePast musicStorage3 = new MusicStorageOfThePast(1969, 14, "Artist 3", "Album C", 23.2f);

        MusicStorageOfThePast[] toInsert = { 
            musicStorage1, null, musicStorage3, musicStorage2, musicStorage3
        };

        MusicStorageOfThePast[] toDelete = { 
            musicStorage1, null, musicStorage2 
        };

        MusicStorageOfThePast[] toFind   = {  
            musicStorage1, null, musicStorage2, musicStorage3
        };

        aSortedMusicStorage.notASet();

        System.out.println("---SortedStorage will NOT BEHAVE LIKE A SET---");
        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedMusicStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedMusicStorage.includesNull());
        System.out.println("- toString: "  + aSortedMusicStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedMusicStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedMusicStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedMusicStorage.delete(toDelete[index]));
        }
    }

    public static void testItWithEmailStorage(SortedStorage<OldFashionedEmailAddress> aSortedEmailStorage)	{
        
        OldFashionedEmailAddress emailStorage1 = new OldFashionedEmailAddress(123, "Main Street", "Springfield", "Illinois", 62701);
        OldFashionedEmailAddress emailStorage2 = new OldFashionedEmailAddress(4567, "Elm Avenue", "Baltimore", "Maryland", 21201);
        OldFashionedEmailAddress emailStorage3 = new OldFashionedEmailAddress(789, "Oak Lane", "Los Angeles", "California", 90001);

        OldFashionedEmailAddress[] toInsert = { 
            emailStorage1, null, emailStorage3, emailStorage2, emailStorage3
        };

        OldFashionedEmailAddress[] toDelete = { 
            emailStorage1, null, emailStorage2 
        };

        OldFashionedEmailAddress[] toFind   = {  
            emailStorage1, null, emailStorage2, emailStorage3
        };

        aSortedEmailStorage.notASet();

        System.out.println("---SortedStorage will NOT BEHAVE LIKE A SET---");
        for (int index = 0; index < toInsert.length; index ++ )	{
            System.out.println("- add(" + toInsert[index] + "): "  + aSortedEmailStorage.add(toInsert[index]));
        }

        System.out.println("- includesNull: "  + aSortedEmailStorage.includesNull());
        System.out.println("- toString: "  + aSortedEmailStorage.toString());

        for (int index = 0; index < toFind.length; index ++ )	{
            System.out.println("- find(" + toFind[index] + "): "  + aSortedEmailStorage.find(toFind[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedEmailStorage.delete(toDelete[index]));
        }
        for (int index = 0; index < toDelete.length; index ++ )	{
            System.out.println("- delete(" + toDelete[index] + "): "  + aSortedEmailStorage.delete(toDelete[index]));
        }
    }

    public void test()	{
        System.out.println("\n----Test with Strings----\n");
        SortedStorage<String> aSortedStringStorage = new SortedStorage<String>();
        testItWithString(aSortedStringStorage);

        System.out.println("\n----Test with Integers----\n");
        SortedStorage<Integer> aSortedIntegerStorage = new SortedStorage<Integer>();
        testItWithIntegers(aSortedIntegerStorage);

        System.out.println("\n----Test with SortedStorage----\n");
        SortedStorage aSortedStorage = new SortedStorage();
        SortedStorage[] theSortedStorages = { aSortedStringStorage, aSortedIntegerStorage, aSortedStorage };
        testItWithSortedStorage(aSortedStorage, theSortedStorages);

        System.out.println("\n----Test with MusicStorageOfThePast----\n");
        SortedStorage<MusicStorageOfThePast> aSortedMusicStorage = new SortedStorage<MusicStorageOfThePast>();
        testItWithMusicStorage(aSortedMusicStorage);

        System.out.println("\n----Test with OldFashionedEmailAddress----\n");
        SortedStorage<OldFashionedEmailAddress> aSortedEmailStorage = new SortedStorage<OldFashionedEmailAddress>();
        testItWithEmailStorage(aSortedEmailStorage);

    }

    public static void main(String args[] )	{
        new Test().test();
    }
}