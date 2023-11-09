import java.util.Arrays;

public class StringIntegerArrays {
    static boolean firstTime = true;

    static String resturnsAstring(String arg)	{
        String rValue;

        if ( firstTime )
            rValue = "";
        else
            rValue = arg;
        firstTime = false;
        return rValue;
    }
    public static void main( String args[] ) {
        String a, b;
        String aString= null;
        String bString= null;
        String cString= null;
        String dString= null;
        String eString= null;
        String fString= null;
        String gString= null;
        String hString= null;
        String iString= null;

        if ( args.length == 1 ) {
            aString = "Ab" + "ba";
            bString = "Abba";
            cString = "A";
            dString = cString + "b" + "b" + aString.substring(aString.length() - 1); //"Abba"
            fString = "Pink FLoyd";
            gString = "Abba" + resturnsAstring("");
            hString = "Ab" + resturnsAstring("ba");

            /* --FOR args.length == 1--
                I. True
                II. True
                III. False
                IV. False
                V. True
                VI. False
             */
        }

        else {
            aString = "Ot" + "to";
            bString = "Otto";
            cString = "O";
            dString = cString + "t" + "t" + aString.substring(aString.length() - 1); //"Otto"
            fString = "Led ZeppeLin";
            gString = "Otto" + resturnsAstring("");
            hString = "Ot" + resturnsAstring("to");

            /* --FOR args.length != 1--
                I. True
                II. True
                III. False
                IV. False
                V. True
                VI. False
             */
        }

        // your code here
        //authors: Kyle Burke, Shreesh Tripathi
        int count1, count2;
        char hold;
        //Since strings are immutable, convert fString to an array of chars
        char[] fArray = fString.toCharArray();

        //Go through fArray charactar by character
        for (count1 = 0; count1 < fString.length(); count1++){
            //If an uppercase 'L' is encountered
            if (fArray[count1] == 'L'){
                //replace it with a lowercase one
                fArray[count1] = 'l';
            }
        }

        //Perform a standard selection sort on fArray
        //Merge Sort was considered, but since the answer
        //Had to exist within the main function, it was inviable.
        for(count1 = 0; count1 < fArray.length; count1++){
            for (
                count2 = count1 + 1;
                count2 < fArray.length;
                count2++
            ){
                //If the character being analysed is lexographically before
                //The sorted portion of the array
                if (fArray[count2] < fArray[count1]){
                    //Swap the two values
                    hold = fArray[count2];
                    fArray[count2] = fArray[count1];
                    fArray[count1] = hold;
                }
            }
        }

        //Rebuild fString in its sorted order
        fString = new String(fArray);

        //Output the sorted string to the user
        System.out.println(fString);
    }
}