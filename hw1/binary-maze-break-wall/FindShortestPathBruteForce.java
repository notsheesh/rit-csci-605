public class FindShortestPathBruteForce {
	static int[][] maze = {
	    {-1, -9, -9, -1, -9, -1, -9, -9, -9},
	    {-1, 00, -1, -1, 00, -1, -1, -1, -9},
	    {-9, 00, -1, -1, 00, -1, -1, -1, -9},
	    {-1, 00, -1, -1, 00, -1, -1, -1, -9},
	    {-1, 00, -1, -1, -1, 00, 00, 00, -9},
		{-1, 00, -1, 00, -1, -1, -1, 00, -9},
		{-1, 00, 00, 00, -1, 00, 00, 00, -9},
	    {-1, -9, -1, -1, -9, -9, -9, -9, -9},};
    
        // static int[][] maze = {
        //     {-1, -9, -9, -1, -9, -1, -9, -9, -9},
        //     {-1, 00, -1, 00, 00, -1, -1, -1, -9},
        //     {-9, 00, -1, -1, 00, -1, -1, -1, -9},
        //     {-1, 00, 00, -1, 00, -1, -1, -1, -9},
        //     {-1, 00, -1, 00, -1, 00, 00, 00, -9},
        //     {-1, 00, -1, 00, -1, -1, -1, 00, -9},
        //     {-1, 00, 00, 00, 00, 00, 00, 00, -9},
        //     {-1, -9, -1, -1, -9, -9, -9, -9, -9},};

        // static int[][] maze = {
        //     {-1, -9, -9, -1, -9, -1, -9, -9, -9},
        //     {-1, -1, -1, -1, 00, -1, -1, -1, -9},
        //     {-1, -1, -1, -1, 00, -1, -1, -1, -9},
        //     {-9, 00, 00, -1, 00, -1, -1, -1, -9},
        //     {-1, 00, -1, 00, -1, -1, -1, -1, -9},
        //     {-1, 00, -1, 00, -1, -1, -1, -1, -9},
        //     {-1, 00, 00, 00, -1, -1, -1, -1, -9},
        //     {-1, -9, -1, -1, -9, -9, -9, -9, -9},};

    static int entryRow = 1;
    static int entryColumn = 4;

    static int exitRow = 2;
    static int exitColumn = 0;

    static int numRows = maze.length;
    static int numColumns = maze[0].length;
    static int[][] visitedCellTracker = new int[numRows][numColumns];
    static String allPathsStr = "";
    static String shortestPathStr = ""; 
    static int brokenWallRow = -1;
    static int brokenWallColumn = -1;
    
    // Viz functions
    // Print maze
    private static void printMaze(int maze[][]) {
        for(int row = 0; row < maze.length; row++) {
            for(int column = 0; column < maze[0].length; column++){
                if(isOutMaze(row, column)){
                    if(row == exitRow && column == exitColumn){
                    System.out.print("g");
                    }
                    else{
                        System.out.print('x');
                    }
                }
                else {
                    if(isWalkable(row, column)){
                        if(isVisited(row, column)){
                            System.out.print("+");
                        }
                        if(!isVisited(row, column)){
                            System.out.print("."); 
                        }
                    }
                    if(isWall(row, column)){
                        System.out.print("o");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Print path to show visited cells
    private static void printPath(int maze[][]) {
        for(int row = 0; row < maze.length; row++) {
            for(int column = 0; column < maze[0].length; column++) {
                if(isOutMaze(row, column)){
                    if(row == exitRow && column == exitColumn){
                        System.out.print("g");
                        }
                        else{
                            System.out.print('x');
                        }
                }
                else {
                    if(isWalkable(row, column)) System.out.print(".");
                    else System.out.print("o");
                }
            }
            System.out.println();
        }
    }

    // Show maze state at row, column 
    private static void debugMaze(int row, int column) {
        System.out.println("State @ [" + row + ", " + column + "]");
        printMaze(maze);
        System.out.println("----------------------------------");
    }
    
    // Current cell checkpoint
    private static void printCell(String note, int row, int column) {
        System.out.println(note + " @ [" + row + ", " + column + "]");
    }
    
    // Helper functions
    // Is the cell in bound
    private static boolean isWithinBounds(int row, int column) {
        boolean isValidRow = row >= 0 && row < numRows;
        boolean isValidColumn = column >= 0 && column <= numColumns;
        return isValidRow && isValidColumn;
    }

    // Is cell inside maze and not a wall? 
    private static boolean isWalkable(int row, int column) {
        return isWithinBounds(row, column) && maze[row][column] == 0;
    }
    
    // Has the cell been visited before 
    private static boolean isVisited(int row, int column) {
        return visitedCellTracker[row][column] == 1;
    }

    // Is the cell 1) in bound 2) not a wall 3) not visited
    private static boolean isValidCell(int row, int column) {
        if(isWithinBounds(row, column)) {
            if(isWalkable(row, column)) {
                if(! isVisited(row, column)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Check if the given cell is valid and out of the maze
    private static boolean isOutMaze(int row, int column) {
        return isWithinBounds(row, column) && maze[row][column] == -9;
    }

    // Is it exit cell?
	private static boolean isExitCell(int row, int column) {
		return row == exitRow && column == exitColumn;
	}

    // Is cell a wall? 
    private static boolean isWall(int row, int column) {
		return isWithinBounds(row, column) && maze[row][column] == -1;
	}
	
    // Check if any neighbouring cell is the exit cell?
	private static boolean isNeighbourExitCell(int row, int column) {
		return (
			isExitCell(row-1, column) || // Check U cell
			isExitCell(row+1, column) || // Check D cell
			isExitCell(row, column-1) || // Check L cell
			isExitCell(row, column+1)    // Check R cell
		);
	}
    
    // Is goal at UDLR of cell
    private static boolean isGoalFound(int row, int column) {
        // Edge case (when starting out)
        if(row == entryRow && column == entryColumn) {
            return false;
        }

        // Is any neighbouring cell validIndex and == -9
        return isNeighbourExitCell(row, column);
    }

    // Adds move on pathStr
    private static String makeMove(String pathStr, char move) {
        return pathStr + move;
    }

    // Undo move on pathStr
    private static String undoLastMove(String beforeUndoPathStr) {
        String afterUndoPathStr = "";
        int lengthMinusOne = beforeUndoPathStr.length()-1;
        for (int i = 0; i < lengthMinusOne; i++) {
            afterUndoPathStr += beforeUndoPathStr.charAt(i);
        }
        return afterUndoPathStr;
    }

	private static void resetVisitedCellTracker() {
		for (int i = 0; i < visitedCellTracker.length; i++) {
			for (int j = 0; j < visitedCellTracker[0].length; j++) {
				visitedCellTracker[i][j] = 0;
			}
		}
	}

    private static void breakWallFindShortestPath(int entryRow, int entryColumn){
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[0].length; j++){
                if(isWall(i, j)){
                    // Break wall 
                    maze[i][j] = 00;
                    brokenWallRow = i;
                    brokenWallColumn = j;
                    resetVisitedCellTracker();
                    findAllPaths("", entryRow, entryColumn);
                    // Fix wall 
                    maze[i][j] = -1;
                }
            }
        }
    }

    private static void solveMaze(int startRow, int startColumn) { 
        System.out.println("Attempting to solve the maze...");
        printMaze(maze);
        breakWallFindShortestPath(startRow, startColumn);
        if (allPathsStr.length() > 0) {
            System.out.println("Shortest path: " + shortestPathStr);
            System.out.println("Shortest path length: " + shortestPathStr.length());
        }
        else {
            System.out.println("Path doesn't exist.");
        }
    }


    private static void findAllPaths(String pathStr, int row, int column) {
        // Base Case
        if(isGoalFound(row, column)) { 
            visitedCellTracker[row][column] = 1;
            allPathsStr += pathStr + ",";

            // Update shortestPathStr is shorter path found
            if (shortestPathStr.length() == 0){
                shortestPathStr = pathStr;
            }
            else{
                if(pathStr.length() < shortestPathStr.length()){
                    shortestPathStr = pathStr;
                }
            }
            
            String brokenWallStr = "[" + Integer.toString(brokenWallRow) + ", " + Integer.toString(brokenWallColumn) + "]";
            System.out.println("Broken wall: " + brokenWallStr);
            System.out.println("Path found: " + pathStr);
            System.out.println("Shortest path so far: " + shortestPathStr);
            System.out.println("All paths: " + allPathsStr);
            debugMaze(row, column);
            // System.out.println("----------------------------------");
            printCell("findAllPaths", row, column);
            debugMaze(row, column);
            // printCell(pathStr, row, column);
            System.out.println("Goal found!");
            // System.out.println("----------------------------------");
            return;
        }

        // Keep trying, go deeper
        visitedCellTracker[row][column] = 1;
        // Uncomment for debugging
        // System.out.println("----------------------------------");
        // printCell("findAllPaths", row, column);
        // debugMaze(row, column);
        // System.out.println("----------------------------------");

        if (isValidCell(row-1, column)) { // Check U cell
            // printCell("Moving to", row-1, column);
            pathStr = makeMove(pathStr, 'u');
            // System.out.println("pathStr: " + pathStr);
            findAllPaths(pathStr, row-1, column);
            pathStr = undoLastMove(pathStr);
            visitedCellTracker[row-1][column] = 0;
        }
        if (isValidCell(row+1, column)) { // Check D 
            // printCell("Moving to", row+1, column);
            pathStr = makeMove(pathStr, 'd');
            // System.out.println("pathStr: " + pathStr);
            findAllPaths(pathStr, row+1, column);
            pathStr = undoLastMove(pathStr);
            visitedCellTracker[row+1][column] = 0;
        }
        if (isValidCell(row, column+1)) { // Check R cell
            // printCell("Moving to", row, column+1);
            pathStr = makeMove(pathStr, 'r');
            // System.out.println("pathStr: " + pathStr);
            findAllPaths(pathStr, row, column+1);
            pathStr = undoLastMove(pathStr);
            visitedCellTracker[row][column+1] = 0;
        }
        if (isValidCell(row, column-1)) { // Check L cell
            // printCell("Moving to", row, column-1);
            pathStr = makeMove(pathStr, 'l');
            // System.out.println("pathStr: " + pathStr);
            findAllPaths(pathStr, row, column-1);
            pathStr = undoLastMove(pathStr);
            visitedCellTracker[row][column-1] = 0;
        }
        // System.out.println("Covered all options");
        // printCell("Nowhere else to go from", row, column);
        return; 
    }

    public static void main(String[] args) {
        System.out.println();System.out.println();
        // System.out.println();
        printMaze(maze);
        solveMaze(entryRow, entryColumn);
    }
}