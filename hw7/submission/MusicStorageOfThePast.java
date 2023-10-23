public class MusicStorageOfThePast implements Comparable<MusicStorageOfThePast>{
    private int year;
    private String artist;
    private String title;
    float length;
    int tracks;

    /**
     * Default constructor for a MusicStorageOfThePast object.
     * @param year The year the album came out
     * @param tracks the number of tracks on the album
     * @param artist the artist who created the album
     * @param title the name of the album
     * @param length the length in time units of the album
     */
    public MusicStorageOfThePast(int year, int tracks, String artist, String title, float length){
        this.year = year;
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.tracks = tracks;
    }

    public String getArtist(){
        return this.artist;
    }
    public String getTitle(){
        return this.title;

    }
    public int getYear(){
        return this.year;

    }
    public int getTracks(){
        return this.tracks;
    }
    public float getLength(){
        return this.length;
    }

    /**
     * Pretty Print of the form {1969, 14, Artist 3, Album C, 23.2}
     * @return String of the form {1969, 14, Artist 3, Album C, 23.2}
     */
    public String toPrettyString(){
        String cma = ", ";
        String returnStr = "{";

        returnStr += String.valueOf(this.getYear()) + cma;
        returnStr += String.valueOf(this.getTracks()) + cma;
        returnStr += String.valueOf(this.getArtist()) + cma;
        returnStr += String.valueOf(this.getTitle()) + cma;
        returnStr += String.valueOf(this.getLength()) + "}";
        return returnStr;
    }

    /**
     * Pretty Print 
     * @return String of the form "title by artist"
     */
    public String toString(){
        return this.getTitle() + " by " + this.getArtist();
    }

    /**
     * Compares two MusicStorage of the Past objects. The "natural" sorting order
     * is by album title
     * @param other the object to be compared.
     * @return an integer representing the result of the comparison.
     */
    public int compareTo(MusicStorageOfThePast other){
        return this.title.compareTo(other.title);
    }
}
