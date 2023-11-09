public class OldFashionedEmailAddress implements Comparable<OldFashionedEmailAddress>{
    private int houseNumber;
    private String streetName;
    private String nameOfTown;
    private String state;
    private int zipCode;

    public int getZipCode(){
        return this.zipCode;
    }

    public String getState(){
        return this.state;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public String getNameOfTown() {
        return this.nameOfTown;
    }

    /**
     * Default constructor for an OldFashionedEmailAddress
     * @param houseNumber the house number of the Object
     * @param streetName the street on which the Object exists
     * @param nameOfTown the town in which the Object exists
     * @param zipCode the ZipCode in which the object exists
     * @param state the State in which the object exists
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public OldFashionedEmailAddress(int houseNumber, String streetName, String nameOfTown, String state, int zipCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.nameOfTown = nameOfTown;
        this.state = state;
        this.zipCode = zipCode;
    }

    /**
     * Implementation of the compareTo method for the OldFashionedEmailAddress class
     * Compares two Addresses with the hierarchical order:
     *  State -> Zip code -> Town -> Street -> House Number
     * and only returns 0 if all of these values are equivalent.
     * @param other the object to be compared.
     * @return and integer representing the comparative difference between two addresses.
     *
     * @author Kyle Burke
     * @author Shreesh Tripathi
     */
    public int compareTo(OldFashionedEmailAddress other){

         int test = this.state.compareTo(other.getState());
         if (test == 0){
             test = this.zipCode - other.getZipCode();
             if (test == 0){
                 test = this.nameOfTown.compareTo(other.getNameOfTown());
                 if (test == 0){
                     test = this.streetName.compareTo(other.getStreetName());
                     if (test == 0){
                         return this.houseNumber - other.getHouseNumber();
                     }
                 }
             }
         }
         return test;
    }

    /**
     * Pretty Print 
     * @return String of the form {house number, street name}
     */
    public String toString(){
        return "{" + this.getHouseNumber() + ", " + this.getStreetName() + "}";
    }

}
