public class FindShortestPathCleaned {
    static int[][] maze = {
        { -9, -9, -9, -9, -9, -9, -9 },
        { -9, -1, 00, -1, -1, -1, -9 },
        { -9, -1, -1, -1, -1, -1, -9 },
        { -9, -1, -1, -1, -1, -1, -9 },
        { -1, -1, -1, -1, -1, -1, -9 },
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

    // Viz functions
    // Print maze
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
            System.out.println();
        }
    }

    // Print path to show visited cells
    private static void printPath( int maze[][] ) {
        for ( int row = 0; row < maze.length; row++ ) {
            for ( int column = 0; column < maze[0].length; column++ ) {
                if ( isOutMaze( row, column ) ) {
                    System.out.print( 'g' );
                } else {
                    if ( isWalkable( row, column ) ) {
                        System.out.print( '.' );
                    } else {
                        System.out.print( 'x' );
                    }
                }
            }
            System.out.println();
        }
    }

    // Show maze state at row, column
    private static void debugMaze( int row, int column ) {
        System.out.println( "State @ [" + row + ", " + column + "]" );
        printMaze( maze );
        System.out.println();
    }

    // Current cell checkpoint
    private static void printCell( String note, int row, int column ) {
        System.out.println( note + " @ [" + row + ", " + column + "]" );
    }

    // Helper functions
    // Is the cell in bound
    private static boolean isWithinBounds( int row, int column ) {
        boolean isValidRow = row >= 0 && row < numRows;
        boolean isValidColumn = column >= 0 && column <= numColumns;
        return isValidRow && isValidColumn;
    }

    // Is cell inside maze and not a wall?
    private static boolean isWalkable( int row, int column ) {
        return isWithinBounds( row, column ) && maze[row][column] == IS_EMPTY;
    }

    // Has the cell been visited before
    private static boolean isVisited( int row, int column ) {
        return visitedCellTracker[row][column] == 1;
    }

    // Is the cell 1 ) in bound 2 ) not a wall 3 ) not visited
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

    // Check if the given cell is valid and out of the maze
    private static boolean isOutMaze( int row, int column ) {
        if ( isWithinBounds( row, column ) ) {
            return maze[row][column] == IS_OUTSIDE_MAZE;
        } else {
            return false;
        }
    }

    // Check if any neighbouring cell is the goal
    private static boolean isNeighbourCellGoal( int row, int column ) {
        boolean isUCellOutMaze = isOutMaze( row - 1, column ); // Check U cell
        boolean isDCellOutMaze = isOutMaze( row + 1, column ); // Check D cell
        boolean isLCellOutMaze = isOutMaze( row, column - 1 ); // Check L cell
        boolean isRCellOutMaze = isOutMaze( row, column + 1 ); // Check R cell
        boolean orOutput = isUCellOutMaze || isDCellOutMaze;
        orOutput = orOutput || isLCellOutMaze || isRCellOutMaze;
        return orOutput;
    }

    // Is goal at UDLR of cell
    private static boolean isGoalFound( int row, int column ) {
        // Edge case (when starting out )
        if ( row == ENTRY_ROW && column == ENTRY_COLUMN ) {
            return false;
        }
        // Is any neighbouring cell validIndex and IS_OUTSIDE_MAZE
        return isNeighbourCellGoal( row, column );
    }

    // Adds move on pathStr
    private static String makeMove( String pathStr, char move ) {
        return pathStr + move;
    }

    // Undo move on pathStr
    private static String undoLastMove( String beforeUndoPathStr ) {
        String afterUndoPathStr = "";
        int lengthMinusOne = beforeUndoPathStr.length() - 1;
        for ( int i = 0; i < lengthMinusOne; i++ ) {
            afterUndoPathStr += beforeUndoPathStr.charAt(i );
        }
        return afterUndoPathStr;
    }

    private static void solveMaze( int startRow, int startColumn ) {
        System.out.println( "Attempting to solve the maze..." );
        printCell("Start ", startRow, startColumn);
        debugMaze( startRow, startColumn );
        findAllPaths( "", startRow, startColumn );
        if ( allPathsStr.length() > 0 ) {
            System.out.println( "All Paths: " + allPathsStr );
            System.out.println( "Shortest path: " + shortestPathStr );
            int shortestPathLen = shortestPathStr.length();
            System.out.println( "Shortest path length: " +  shortestPathLen );
        } else {
            System.out.println( "Path doesn't exist." );
        }
    }

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
            debugMaze( row, column );
            printCell("Goal found [" + pathStr + "]", row, column);
            return;
        }

        visitedCellTracker[row][column] = 1;
        printCell( "findAllPaths", row, column );
        debugMaze( row, column );

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

    public static void main(String[] args ) {
        solveMaze( ENTRY_ROW, ENTRY_COLUMN );
    }
}