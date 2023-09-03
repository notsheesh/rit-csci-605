public class FindAnyPathBruteForceWallBreak {

	static int[][] maze = {
	    {-1, -9, -9, -1, -9, -1, -9, -9, -9},
	    {-1, -1, -1, -1, 00, -1, -1, -1, -9},
	    {-1, -1, -1, -1, 00, -1, -1, -1, -9},
	    {-9, 00, 00, -1, 00, -1, -1, -1, -9},
	    {-1, 00, -1, 00, -1, -1, -1, -1, -9},
		{-1, 00, -1, 00, -1, -1, -1, -1, -9},
		{-1, 00, 00, 00, -1, -1, -1, -1, -9},
	    {-1, -9, -1, -1, -9, -9, -9, -9, -9},};

	static int entryRow = 1;
	static int entryColumn = 4;

	static int exitRow = 4;
	static int exitColumn = 0;

	static int numRows = maze.length;
	static int numColumns = maze[0].length;
	static int numSteps = 0;
	static int[][] visitedCellTracker = new int[numRows][numColumns];
	
	// Print Maze (o = out of maze, x = wall, . = can walk)
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

	// Print path solution (+ = walked path)
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

	private static void resetVisitedCellTracker() {
		for (int i = 0; i < visitedCellTracker.length; i++) {
			for (int j = 0; j < visitedCellTracker[0].length; j++) {
				visitedCellTracker[i][j] = 0;
			}
		}
	}
	
	// Is the cell in bound
	private static boolean isWithinBounds ( int row, int column ) {
		boolean isValidRow = row >= 0 && row < numRows;
		boolean isValidColumn = column >= 0 && column <= numColumns;
		return isValidRow && isValidColumn;
	}

	// Can you step on cell
	private static boolean isWalkable ( int row, int column ) {
		return isWithinBounds(row, column) && maze[row][column] == 0;
	}
	
	// Is the cell visited
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
	private static boolean isNeighbourGoalCell(int row, int column) {
		boolean _isUCellOutMaze = isOutMaze ( row-1, column ); // Check U cell
		boolean _isDCellOutMaze = isOutMaze ( row+1, column ); // Check D cell
		boolean _isLCellOutMaze = isOutMaze ( row, column-1 ); // Check L cell
		boolean _isRCellOutMaze = isOutMaze ( row, column+1 ); // Check R cell
		return _isUCellOutMaze || _isDCellOutMaze || _isLCellOutMaze || _isRCellOutMaze;
	}

	private static boolean isExitCell(int row, int column) {
		return row == exitRow && column == exitColumn;
	}
	
	private static boolean isNeighbourExitCell(int row, int column) {
		return (
			isExitCell ( row-1, column ) || // Check U cell
			isExitCell ( row+1, column ) || // Check D cell
			isExitCell ( row, column-1 ) || // Check L cell
			isExitCell ( row, column+1 )    // Check R cell
		);
	}
	
	// Is goal at UDLR of cell
	private static boolean isGoalFound ( int row, int column ) {
		// Edge case (when starting out)
		if ( row == entryRow && column == entryColumn ) {
			return false;
		}

		// Is any neighbouring cell validIndex and == -9
		return isNeighbourExitCell(row, column);
	}

	private static boolean isWall(int row, int column) {
		return isWithinBounds(row, column) && maze[row][column] == -1;
	}

	private static int getShortestPathLength(int entryRow, int entryColumn) {
		int shortestPathLength = Integer.MAX_VALUE;
		// Remove every wall one by one
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if ( isWall(i, j) ) {
					// Break wall
					maze[i][j] = 00;
					numSteps = 0;
					resetVisitedCellTracker();
					if ( isPathExist(entryRow, entryColumn) ) {
						printMaze(maze);
						System.out.println("Path found in steps = " + Integer.toString(numSteps));
						System.out.println();
						if (numSteps < shortestPathLength) {
							shortestPathLength = numSteps;
						}
					}
					// Fix wall
					maze[i][j] = -1;
				}
			}
		}
		if ( shortestPathLength == Integer.MAX_VALUE ) {
			return -1;
		} 
		else {
			return shortestPathLength;
		}
	}

	private static void solveMaze ( int startRow, int startColumn ) { 
		System.out.println("Attempting to solve the maze...");
		printMaze(maze); System.out.println();
		int shortestPathLength = getShortestPathLength(entryRow, entryColumn);
		if ( shortestPathLength == -1 ) {
			System.out.println("Path doesn't exist");
		}
		else {
			System.out.println("Path exists.");
			System.out.println("Shortest Path Length: " + shortestPathLength);
		}
	}

	private static void printCell ( String note, int row, int column ) {
		String noteString = note + ": ";
		String rowString = "Row: " + Integer.toString(row);
		String columnString = "Column: " + Integer.toString(column);
		String rowColumnString = noteString + rowString + ", ";
		rowColumnString += columnString;
		System.out.println(rowColumnString);
	}

	private static boolean isPathExist ( int row, int column ) {
		
		// Base Case
		if ( isGoalFound(row, column) ) {
			// System.out.println("Goal found!"); 
			visitedCellTracker[row][column] = 1;
			return true;
		}

		// Keep trying, go deeper
		else {

			visitedCellTracker[row][column] = 1;
			numSteps += 1;

			// Depth First Search
			// Why this method wont give shortest path with wall break logic
			// Because the algorithm terminates the instant goal is found, regardless of how optimal the path is
			// In other words the algorithm merely tries to find a path, with no concern for optimality
			// We need to go over all paths and then choose the shortest one, this is where the string parser method triumphs
			if ( isValidCell(row-1, column) && isPathExist(row-1, column) ) return true; // Check U cell
			if ( isValidCell(row+1, column) && isPathExist(row+1, column) ) return true; // Check D cell 
			if ( isValidCell(row, column-1) && isPathExist(row, column-1) ) return true; // Check L cell 
			if ( isValidCell(row, column+1) && isPathExist(row, column+1) ) return true; // Check R cell 
			// if ( isValidCell(row+1, column) && isPathExist(row+1, column) ) return true; // Check D cell 

			// Wrong branch, backtrack
			visitedCellTracker[row][column] = 0;
			numSteps -= 1;
			return false;
		}
	}


	public static void main ( String[] args ) {
		System.out.println();System.out.println();System.out.println();
		solveMaze(entryColumn, numColumns);
	}
}