/**
 * FindPathThroughMaze.java
 *
 * Version:
 *     Java 19, 2022-09-20
 *
 */

/**
 * This program solves a maze, finding all possible paths and thus the
 * shortest path. It utilizes a recursive algorithm to explore the maze 
 * in a depth first search manner while avoiding walls and tracking visited 
 * cells.
 *
 * @author: Shreesh Tripathi
 * @author: Prateek Sharma
 */

public class FindPathThroughMaze {
    static int[][] maze = {
        { -9, -9, -9, -9, -9, -9, -9 },
        { -9, -1, 00, -1, -1, -1, -9 },
        { -9, -1, 00, 00, 00, -1, -9 },
        { -9, -1, 00, -1, 00, -1, -9 },
        { -9, 00, 00, 00, 00, -1, -9 },
        { -1, -1, -1, -1, -1, -1, -9 },
        { -9, -9, -9, -9, -9, -9, -9 }, };

    static int ENTRY_ROW = 1;
    static int ENTRY_COLUMN = 2;

    static int IS_WALL = -1;
    static int IS_OUTSIDE_MAZE = -9;
    static int IS_EMPTY = 0;

    static int numRows = maze.length;
    static int numColumns = maze[0].length;

    static int[][] visitedCellTracker = new int[numRows][numColumns];

    static String allPathsStr = "";
    static String shortestPathStr = "";

    static boolean PRETTY_PRINT = false;

    /**
     * Prints the maze with the current state of cells,
     * where 'g' represents the goal,
     * '+' represents visited empty cells, '.' represents unvisited empty cells,
     * and 'x' represents walls.
     *
     * @param maze The 2D maze array to print.
     */

    private static void printMaze( int maze[][] ) {
        for ( int row = 0; row < maze.length; row++ ) {
            for ( int column = 0; column < maze[0].length; column++ ) {
                if ( isOutMaze( row, column ) ) {
                    System.out.print( 'g' );
                } else {
                    if ( isWalkable( row, column ) ) {
                        if ( isVisited( row, column ) ) {
                            System.out.print( '+' );
                        } else {
                            System.out.print( '.' );
                        }
                    } else {
                        System.out.print( 'x' );
                    }
                }
            }
            System.out.println( );
        }
    }

    /**
     * Displays the current state of the maze at a given row and column,
     * including the surrounding cells.
     *
     * @param row     The row of the cell to display.
     * @param column  The column of the cell to display.
     */

    private static void debugMaze( int row, int column ) {
        System.out.println( "State @ [" + row + ", " + column + "]" );
        printMaze( maze );
        System.out.println( );
    }

    /**
     * Prints a checkpoint message with the current cell's coordinates.
     *
     * @param message    The checkpoint message.
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     */

    private static void printCell( String message, int row, int column ) {
        System.out.println( message + " @ [" + row + ", " + column + "]" );
    }

    /**
     * Prints a checkpoint message and the current state of maze, 
     * with the current cell's coordinates if PRETTY_PRINT == true
     *
     * @param message    The checkpoint message.
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     */

    private static void prettyPrint( String message, int row, int column ) {
        if ( PRETTY_PRINT ) {
            printCell( message, row, column );
            debugMaze( row, column );
        }
    }

    /**
     * Checks if a given cell is within the bounds of the maze.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the cell is within bounds, otherwise false.
     */

    private static boolean isWithinBounds( int row, int column ) {
        boolean isValidRow = row >= 0 && row < numRows;
        boolean isValidColumn = column >= 0 && column <= numColumns;
        return isValidRow && isValidColumn;
    }

    /**
     * Checks if a cell is walkable (not a wall) and within the bounds of the maze.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the cell is walkable, otherwise false.
     */

    private static boolean isWalkable( int row, int column ) {
        return isWithinBounds( row, column ) && maze[row][column] == IS_EMPTY;
    }

    /**
     * Checks if a cell has been visited before.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the cell has been visited, otherwise false.
     */

    private static boolean isVisited( int row, int column ) {
        return visitedCellTracker[row][column] == 1;
    }

    /**
     * Checks if a cell is a valid cell (within bounds, not a wall, and not visited).
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the cell is valid, otherwise false.
     */

    private static boolean isValidCell( int row, int column ) {
        if ( isWithinBounds( row, column ) ) {
            if ( isWalkable( row, column ) ) {
                if ( !isVisited( row, column ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the given cell is valid and outside the maze.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the cell is outside the maze, otherwise false.
     */

    private static boolean isOutMaze( int row, int column ) {
        if ( isWithinBounds( row, column ) ) {
            return maze[row][column] == IS_OUTSIDE_MAZE;
        } else {
            return false;
        }
    }

    /**
     * Checks if any neighboring cell is the goal.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if a neighboring cell is the goal, otherwise false.
     */

    private static boolean isNeighbourCellGoal( int row, int column ) {
        boolean isUCellOutMaze = isOutMaze( row - 1, column ); // Check U cell
        boolean isDCellOutMaze = isOutMaze( row + 1, column ); // Check D cell
        boolean isLCellOutMaze = isOutMaze( row, column - 1 ); // Check L cell
        boolean isRCellOutMaze = isOutMaze( row, column + 1 ); // Check R cell
        boolean orOutput = isUCellOutMaze || isDCellOutMaze;
        orOutput = orOutput || isLCellOutMaze || isRCellOutMaze;
        return orOutput;
    }

    /**
     * Checks if the goal is found at the neighboring cells.
     *
     * @param row     The row of the cell.
     * @param column  The column of the cell.
     * @return        True if the goal is found, otherwise false.
     */

    private static boolean isGoalFound( int row, int column ) {
        // Edge case (when starting out )
        if ( row == ENTRY_ROW && column == ENTRY_COLUMN ) {
            return false;
        }
        // Is any neighbouring cell validIndex and IS_OUTSIDE_MAZE
        return isNeighbourCellGoal( row, column );
    }

    /**
     * Adds a move to the path string.
     *
     * @param pathStr  The current path string.
     * @param move     The move to add (e.g., 'u' for up).
     * @return         The updated path string with the added move.
     */

    private static String makeMove( String pathStr, char move ) {
        return pathStr + move;
    }

    /**
     * Undoes the last move in the path string.
     *
     * @param beforeUndoPathStr  The path string before undoing the move.
     * @return                   The updated path string after undoing the move.
     */

    private static String undoLastMove( String beforeUndoPathStr ) {
        String afterUndoPathStr = "";
        int lengthMinusOne = beforeUndoPathStr.length() - 1;
        for ( int i = 0; i < lengthMinusOne; i++ ) {
            afterUndoPathStr += beforeUndoPathStr.charAt( i );
        }
        return afterUndoPathStr;
    }

    /**
     * Solves the maze starting from a given cell.
     *
     * @param entryRow     The starting row.
     * @param entryColumn  The starting column.
     */

    private static void solveMaze( int entryRow, int entryColumn ) {
        System.out.println( "Attempting to solve the maze..." );
        prettyPrint("Start", entryRow, entryColumn );
        findAllPaths( "", entryRow, entryColumn );
        if ( allPathsStr.length() > 0 ) {
            System.out.println( "All Paths: " + allPathsStr );
            System.out.println( "Shortest path: " + shortestPathStr );
            int shortestPathLen = shortestPathStr.length( );
            System.out.println( "Shortest path length: " +  shortestPathLen );
        } else {
            System.out.println( "Path doesn't exist." );
        }
    }

    /**
     * Recursively finds all paths in the maze from a given cell.
     *
     * @param pathStr  The current path string.
     * @param row      The current row.
     * @param column   The current column.
     */

    private static void findAllPaths( String pathStr, int row, int column ) {
        // Base Case
        if ( isGoalFound( row, column ) ) {
            visitedCellTracker[row][column] = 1;
            allPathsStr += pathStr + ",";

            // Update shortestPathStr is shorter path found
            if ( shortestPathStr.length() == 0 ) {
                shortestPathStr = pathStr;
            } else {
                if ( pathStr.length() < shortestPathStr.length() ) {
                    shortestPathStr = pathStr;
                }
            }
            prettyPrint( "Goal found [" + pathStr + "]", row, column );
            return;
        }

        visitedCellTracker[row][column] = 1;
        prettyPrint( "findAllPaths", row, column );

        if ( isValidCell( row - 1, column ) ) { // Check Move: U
            pathStr = makeMove( pathStr, 'u' );
            findAllPaths( pathStr, row - 1, column );
            pathStr = undoLastMove( pathStr );
            visitedCellTracker[row - 1][column] = 0;
        }
        if ( isValidCell( row + 1, column ) ) { // Check Move: D
            pathStr = makeMove( pathStr, 'd' );
            findAllPaths( pathStr, row + 1, column );
            pathStr = undoLastMove( pathStr );
            visitedCellTracker[row + 1][column] = 0;
        }
        if ( isValidCell( row, column - 1 ) ) { // Check Move: L
            pathStr = makeMove( pathStr, 'l' );
            findAllPaths( pathStr, row, column - 1 );
            pathStr = undoLastMove( pathStr );
            visitedCellTracker[row][column - 1] = 0;
        }
        if ( isValidCell( row, column + 1 ) ) { // Check Move: R
            pathStr = makeMove( pathStr, 'r' );
            findAllPaths( pathStr, row, column + 1 );
            pathStr = undoLastMove( pathStr );
            visitedCellTracker[row][column + 1] = 0;
        }
        return;
    }

    /**
     * The main entry point of the program. Solves the maze starting from the entry point.
     *
     * @param args  The command-line arguments (not used in this program).
     */

    public static void main(String[] args ) {
        solveMaze( ENTRY_ROW, ENTRY_COLUMN );
    }
}