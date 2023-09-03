import java.nio.file.Paths;

public class FindShortestPathMaze {
	// maze with loop
	// static int[][] maze = {
	// 	{-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9},
	// 	{-9, -1, -1, -1, 00, -1, -1, -1, -1, -1, -1, -1, -1, -9},
	// 	{-9, -1, 00, 00, 00, 00, 00, 00, -1, 00, 00, 00, -1, -9}, 
	// 	{-9, -1, 00, -1, 00, -1, -1, -1, -1, 00, -1, 00, -1, -9},
	// 	{-9, -1, 00, 00, 00, 00, 00, 00, 00, 00, -1, 00, -1, -9},
	// 	{-9, -1, -1, 00, -1, -1, -1, 00, -1, 00, 00, 00, -1, -9},
	// 	{-9, -1, -1, 00, -1, -1, -1, 00, -1, -1, -1, -1, -1, -9},
	// 	{-9, -1, -1, 00, -1, -1, 00, 00, -1, -1, -1, -1, -1, -9},
	// 	{-9, -1, -1, -1, -1, -1, 00, -1, -1, -1, -1, -1, -1, -9},
	// 	{-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9}};
		
	// static int entryRow = 1;
	// static int entryColumn = 4;

	// static int[][] maze = {
	//     {-9, -9, -1, 00, -1, -9, -9, -9, -9},
	//     {-9, -1, -1, 00, 00, -1, -1, -1, -9},
	//     {-9, -1, -1, -1, -1, -1, -1, -1, -9},
	//     {-9, -1, -1, -1, -1, -1, -1, -1, -9},
	//     {-9, -1, -1, -1, -1, -1, -1, -1, -9},
	//     {-9, -9, -9, -9, -9, -9, -9, -9, -9},};
		
	// static int entryRow = 1;
	// static int entryColumn = 4;

	// static int[][] maze = {
	//     {-9, -9, -9, -9, -9, -9},
	//     {-9, -1, 00, -1, -1, -9},
	//     {-9, -1, 00, -1, -1, -9},
    //     {-9, -1, 00, 00, -1, -9},
	//     {-9, -1, -1, -1, -1, -9},
	//     {-9, -9, -9, -9, -9, -9},};

	// static int[][] maze = {
	//     {-9, -9, -9, -9, -9, -9, -9},
	//     {-9, -1, 00, -1, -1, -1, -9},
    //     {-9, 00, 00, 00, -1, -1, -9},
	//     {-9, -1, 00, 00, -1, -1, -9},
	// 	{-1, -1, -1, 00, 00, 00, -9},
	// 	{-1, -1, -1, -1, -1, -1, -9},
	//     {-9, -9, -9, -9, -9, -9, -9},};

	static int[][] maze = {
	    {-9, -9, -9, -9, -9, -9, -9},
	    {-9, -1, 00, -1, -1, -1, -9},
	    {-9, -1, 00, 00, -1, -1, -9},
	    {-9, 00, 00, 00, 00, 00, -9},
		{-1, -1, -1, -1, 00, -1, -9},
		{-1, -1, -1, -1, 00, 00, -9},
	    {-9, -9, -9, -9, -9, -9, -9},};

	static int entryRow = 1;
	static int entryColumn = 2;

	static int numRows = maze.length;
	static int numColumns = maze[0].length;
	static int[][] visitedCellTracker = new int[numRows][numColumns];
	static String allPathsStr = "";
	static String finalShortestPathStr = ""; 
	
	private static void printMaze ( int maze[][] ) {
		for ( int row = 0; row < maze.length; row++ ) {
			for ( int column = 0; column < maze[0].length; column++ ) {
				if ( isOutMaze(row, column) ) System.out.print('x');
				else {
					if ( isWalkable(row, column) ) {
						if ( isVisited(row, column) ) System.out.print("+");
						else System.out.print("."); 
					}
					else System.out.print("o"); 
				}
			}
			System.out.println();
		}
	}

	private static void printPath ( int maze[][] ) {
		for ( int row = 0; row < maze.length; row++ ) {
			for ( int column = 0; column < maze[0].length; column++ ) {
				if ( isOutMaze(row, column) ) System.out.print('x');
				else {
					if ( isWalkable(row, column) ) System.out.print(".");
					else System.out.print("o");
				}
			}
			System.out.println();
		}
	}

	private static void debugMaze(int row, int column) {
		System.out.println("State @ [" + row + ", " + column + "]");
		printMaze(maze);
		System.out.println("----------------------------------");
	}
    
	// Is the cell in bound
	private static boolean isWithinBounds ( int row, int column ) {
		boolean isValidRow = row >= 0 && row < numRows;
		boolean isValidColumn = column >= 0 && column <= numColumns;
		return isValidRow && isValidColumn;
	}

	// Is cell inside maze and not a wall? 
	private static boolean isWalkable ( int row, int column ) {
		return isWithinBounds(row, column) && maze[row][column] == 0;
	}
	
	// Has the cell been visited before 
	private static boolean isVisited ( int row, int column ) {
		return visitedCellTracker[row][column] == 1;
	}

	// Is the cell 1) in bound 2) not a wall 3) not visited
	private static boolean isValidCell ( int row, int column ) {
		if ( isWithinBounds(row, column) ) {
			if ( isWalkable(row, column) ) {
				if ( ! isVisited(row, column) ) {
					return true;
				}
			}
		}
		return false;
	}

	// Check if the given cell is valid and out of the maze
	private static boolean isOutMaze ( int row, int column ) {
		return isWithinBounds(row, column) && maze[row][column] == -9;
	}

	// Check if any neighbouring cell is the goal
	private static boolean isNeighbourCellGoal(int row, int column) {
		boolean _isUCellOutMaze = isOutMaze ( row-1, column ); // Check U cell
		boolean _isDCellOutMaze = isOutMaze ( row+1, column ); // Check D cell
		boolean _isLCellOutMaze = isOutMaze ( row, column-1 ); // Check L cell
		boolean _isRCellOutMaze = isOutMaze ( row, column+1 ); // Check R cell
		return _isUCellOutMaze || _isDCellOutMaze || _isLCellOutMaze || _isRCellOutMaze;
	}
	
	// Is goal at UDLR of cell
	private static boolean isGoalFound ( int row, int column ) {
		// Edge case (when starting out)
		if ( row == entryRow && column == entryColumn ) {
			return false;
		}

		// Is any neighbouring cell validIndex and == -9
		return isNeighbourCellGoal(row, column);
	}

	private static String makeMove(String pathStr, char move) {
		return pathStr + move;
	}

	private static String undoLastMove(String beforeUndoPathStr) {
		String afterUndoPathStr = "";
		int lengthMinusOne = beforeUndoPathStr.length()-1;
		for (int i = 0; i < lengthMinusOne; i++) {
			afterUndoPathStr += beforeUndoPathStr.charAt(i);
		}
		return afterUndoPathStr;
	}

	private static void printCell ( String note, int row, int column ) {
        System.out.println(note + " @ [" + row + ", " + column + "]");
	}

	// Delete function late 
	{
	// private static String getShortestPath(String allString) {
	// 	int i = 0;
	// 	int count = 0;
	// 	int shortestPathLength = Integer.MAX_VALUE;
	// 	String shortestPathStr = "";
	// 	while ( i < allPathsStr.length() ) {
	// 		String pathStr = "";
	// 		while(allPathsStr.charAt(i) != ',') {
	// 			i += 1;
	// 			pathStr += allPathsStr.charAt(i);
	// 			count += 1;
	// 		}
	// 		if ( count < shortestPathLength ) {
	// 			shortestPathStr = pathStr;
	// 			shortestPathLength = count;
	// 			count = 0;
	// 		} 
	// 		i += 1;
	// 	}
	// 	String shortestPath = "[" + shortestPathStr + ", " + shortestPathLength + "]";
	// 	return shortestPath;
	// }
	}
	

	private static void solveMaze ( int startRow, int startColumn ) { 
		System.out.println("Attempting to solve the maze...");
		findAllPaths("", startRow, startColumn);
		if (allPathsStr.length() > 0) {
			System.out.println("All Paths: " + allPathsStr);
			// String shortestPath = getShortestPath(allPathsStr);
			// System.out.println("Shortest Path: " + shortestPath);
			System.out.println("Shortest path: " + finalShortestPathStr);
			System.out.println("Shortest path length: " + finalShortestPathStr.length());
		}
		else {
			System.out.println("Path doesn't exist.");
		}
	}

	private static void findAllPaths ( String pathStr, int row, int column ) {
		// Base Case
		if ( isGoalFound(row, column) ) { 
			visitedCellTracker[row][column] = 1;
			allPathsStr += pathStr + ",";

			if (finalShortestPathStr.length() == 0){
				finalShortestPathStr = pathStr;
			}
			else{
				if(pathStr.length() < finalShortestPathStr.length()){
					finalShortestPathStr = pathStr;
				}
			}

			// System.out.println("----------------------------------");
			printCell("findAllPaths", row, column);
			debugMaze(row, column);
			printCell(pathStr, row, column);
			System.out.println("Goal found!");
			System.out.println("----------------------------------");
			return;
		}

		// Keep trying, go deeper
		visitedCellTracker[row][column] = 1;
		// System.out.println("----------------------------------");
		printCell("findAllPaths", row, column);
		debugMaze(row, column);

		if (isValidCell(row-1, column)) { // Check U cell
			// printCell("Moving to", row-1, column);
			pathStr = makeMove(pathStr, 'u');
			System.out.println("pathStr: " + pathStr);
			findAllPaths(pathStr, row-1, column);
			pathStr = undoLastMove(pathStr);
			visitedCellTracker[row-1][column] = 0;
		}
		if (isValidCell(row+1, column)) { // Check D 
			// printCell("Moving to", row+1, column);
			pathStr = makeMove(pathStr, 'd');
			System.out.println("pathStr: " + pathStr);
			findAllPaths(pathStr, row+1, column);
			pathStr = undoLastMove(pathStr);
			visitedCellTracker[row+1][column] = 0;
		}
		if (isValidCell(row, column-1)) { // Check L cell
			// printCell("Moving to", row, column-1);
			pathStr = makeMove(pathStr, 'l');
			System.out.println("pathStr: " + pathStr);
			findAllPaths(pathStr, row, column-1);
			pathStr = undoLastMove(pathStr);
			visitedCellTracker[row][column-1] = 0;
		}
		if (isValidCell(row, column+1)) { // Check R cell
			// printCell("Moving to", row, column+1);
			pathStr = makeMove(pathStr, 'r');
			System.out.println("pathStr: " + pathStr);
			findAllPaths(pathStr, row, column+1);
			pathStr = undoLastMove(pathStr);
			visitedCellTracker[row][column+1] = 0;
		}
		// System.out.println("Covered all options");
		// printCell("Nowhere else to go from", row, column);
		return; 
	}

	public static void main ( String[] args ) {
		// System.out.println();System.out.println();
		// System.out.println();
		solveMaze(entryRow, entryColumn);
	}
}