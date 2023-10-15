public class TestEnv {
    public static void main(String[] args) {
        // Test MusicStorageOfThePast
        SortedStorage<MusicStorageOfThePast> musicStorage = new SortedStorage<>();

        // Create some MusicStorageOfThePast objects
        MusicStorageOfThePast album1 = new MusicStorageOfThePast(1980, 10, "Artist A", "Album A", 45.5f);
        MusicStorageOfThePast album2 = new MusicStorageOfThePast(1990, 12, "Artist B", "Album B", 60.2f);
        MusicStorageOfThePast album3 = new MusicStorageOfThePast(1975, 8, "Artist C", "Album C", 35.0f);

        // Add objects to the storage
        musicStorage.add(album1);
        musicStorage.add(album2);
        musicStorage.add(album3);

        // Print the sorted music storage
        System.out.println("Sorted Music Storage:");
        System.out.println(musicStorage);

        // Test OldFashionedEmailAddress
        SortedStorage<OldFashionedEmailAddress> addressStorage = new SortedStorage<>();

        // Create some OldFashionedEmailAddress objects
        OldFashionedEmailAddress address1 = new OldFashionedEmailAddress(123, "Main St", "Town A", "State X", 56789);
        OldFashionedEmailAddress address2 = new OldFashionedEmailAddress(456, "Oak Ave", "Town B", "State Y", 67890);
        OldFashionedEmailAddress address3 = new OldFashionedEmailAddress(789, "Elm St", "Town C", "State Z", 78901);

        // Add objects to the storage
        addressStorage.add(address1);
        addressStorage.add(address2);
        addressStorage.add(address3);

        // Print the sorted address storage
        System.out.println("\nSorted Address Storage:");
        System.out.println(addressStorage);

        // Find an address by zip code
        int zipCodeToFind = 67890;
        OldFashionedEmailAddress addressToFind = new OldFashionedEmailAddress(0, "", "", "", zipCodeToFind);
        OldFashionedEmailAddress foundAddress = addressStorage.find(addressToFind);
        if (foundAddress != null) {
            System.out.println("\nFound Address with Zip Code " + zipCodeToFind + ":");
            System.out.println(foundAddress);
        } else {
            System.out.println("\nAddress with Zip Code " + zipCodeToFind + " not found.");
        }
    }
}
