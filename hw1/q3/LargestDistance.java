/**
 * LargestDistance.java
 *
 * Version:
 *     Java 19, 2022-09-20
 *
 */

/**
 * 
 * This program finds all possible closedFermatNumber sets
 * and outputs the largest closed fermat number set distance in a given range
 * @author: Shreesh Tripathi
 * @author: Prateek Sharma
 */

public class LargestDistance {

    static int MAXIMUM = 5;
    static int MINIMUM = 2;
    static int largestDistance = 0;

    /**
     * Testing method checks if the equation a^3 +b^3 = c^2 is true else false
     * It also compares it with the last largest fermat distance and updates 
     * if the current distance for the set (a,b,c) is bigger
     * 
     * @param  a  First parameter of the equation
     * @param  b  Second parameter of the equation 
     * @param  c  Third parameter which when squared should be the result
     * @return    True if equation is satisfied else return False
     */ 

    private static boolean testProperty( int a, int b, int c ) {
        if ( equationLogic(a, b) == c ) {
            return true;
        } else {
            return false;
        }
    }

    /**
	 * Method outputs the square of fermat distance 
	 * @param  a  First parameter of the equation
	 * @param  b  Second parameter of the equation 
     * 
     * @return    The squared value of fermat distance
	 */

    private static int equationLogic( int a, int b ) {
		int sumOfCubes = ( a * a * a ) + ( b * b * b );
        return sumOfCubes;
	}

    /**
     * Method runs 3 for loops from 1 to MAXIMUM on 2 different variables 
     * to find all possible values of c
     */ 

    private static void findAllNumbers() {
        for ( int a = 1; a <= MAXIMUM; a++ ) {
            for ( int b = 1; b <= MAXIMUM; b++ ) {
                int c2 = equationLogic( a, b );
                if ( 
                    c2 >= (MINIMUM * MINIMUM)
                    && c2 <= (MAXIMUM * MAXIMUM)
                    && testProperty( a, b, c2 ) ) {
                        if ( c2 > largestDistance ) {
                            largestDistance = c2;
                        }
                        print( a, b, c2, b );
                    }
            }
        }
        
    }

    /**
     * Method prints the values in a pretty print
     * @param  a    First parameter of the equation
     * @param  b    Second parameter of the equation 
     * @param  sum  Sum of the cubes of a and b
     * @param  c    Third parameter of the equation
     */

    private static void print(int a, int b, int sum, int c) {
        System.out.println( a + "^3 + " + b + "ˆ3 == " + c + "ˆ2" );
        System.out.println( a * a * a + " + " +	b * b * b + " == " + sum );
    }

    /**
     * The main entry point of the program. Solves the maze starting from the 
     * entry point.
     *
     * @param args  The command-line arguments (not used in this program).
     */
    
    public static void main(String[] args) {
        findAllNumbers();
        System.out.println( "Largest distance squared is: " + largestDistance );
        int largestDistanceSqrt = (int)Math.pow(largestDistance, 0.5);
        System.out.println( "Largest distance is: " + largestDistanceSqrt);
    }
}