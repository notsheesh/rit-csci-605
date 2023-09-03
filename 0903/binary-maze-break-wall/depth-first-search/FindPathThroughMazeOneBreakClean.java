public class FindPathThroughMazeOneBreakClean {
	// static int[][] maze = {
	// 	{-9, -9, -9, -9, -9, -9, -9},
	// 	{-9, -1, -1, 00, -1, -1, -9},
	// 	{-9, -1, -1, 00, -1, -1, -9},
	// 	{-9, -1, 00, 00, -1, -1, -9},
	// 	{-9, -1, -1, -1, -1, -1, -9},
	// 	{-9, -1, -1, -1, -1, -1, -9},
	// 	{-9, -9, -9, -9, -9, -9, -9},};

	static int[][] maze = {
		{-1, -1, -1, -1, -1, -9, -9},
		{-1, -1, 00, 00, -1, -1, -9},
		{-1, -1, 00, 00, -1, -1, -9},
		{-9, -1, -1, 00, -1, -1, -9},
		{-9, -1, -1, 00, -1, -1, -9},
		{-9, -1, -1, -1, -1, -1, -9},
		{-9, -9, -9, -1, -9, -9, -9},};

	static int entryRow = 1;
	static int entryColumn = 2;
	static boolean haveBrokenWall = false;
	static boolean triedAllFour = false;

	static int numRows = maze.length;
	static int numColumns = maze[0].length;
	static int numSteps = 0;
	static int[][] visitedCellTracker = new int[numRows][numColumns];
	
	// Print Maze (o = out of maze, x = wall, . = can walk)
	private static void printMaze(int maze[][]) {
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
	private static void printPath(int maze[][]) {
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
	
	// Is the cell in bound
	private static boolean isWithinBounds(int row, int column) {
		boolean isValidRow = row >= 0 && row < numRows;
		boolean isValidColumn = column >= 0 && column < numColumns;
		return isValidRow && isValidColumn;
	}

	// Is the cell a wall 
	private static boolean isWalkable(int row, int column) {
		return isWithinBounds(row, column) && maze[row][column] == 0;
	}
	
	// Is the cell visited
	private static boolean isVisited(int row, int column) {
		return visitedCellTracker[row][column] == 1;
	}

	// Is the cell 1) in bound 2) not a wall 3) not visited
	private static boolean isValidCell(int row, int column) {
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
		boolean _isUCellOutMaze = isOutMaze(row-1, column); // Check U cell
		boolean _isDCellOutMaze = isOutMaze(row+1, column); // Check D cell
		boolean _isLCellOutMaze = isOutMaze(row, column-1); // Check L cell
		boolean _isRCellOutMaze = isOutMaze(row, column+1); // Check R cell
		return _isUCellOutMaze || _isDCellOutMaze || _isLCellOutMaze || _isRCellOutMaze;
	}
	
	// Is goal at UDLR of cell
	private static boolean isGoalFound(int row, int column) {
		// Edge case (when starting out)
		if ( row == entryRow && column == entryColumn ) {
			return false;
		}

		// Is any neighbouring cell validIndex and == -9
		return isNeighbourCellGoal(row, column);
	}

	private static boolean solveMaze(int startRow, int startColumn) { 

		System.out.println("Attempting to solve the maze...");
		return isPathExist(startRow, startColumn);

	}

	private static void printCell(String note, int row, int column) {
		String noteString = note + ": ";
		String rowString = "Row: " + Integer.toString(row);
		String columnString = "Column: " + Integer.toString(column);
		String rowColumnString = noteString + rowString + ", ";
		rowColumnString += columnString;
		System.out.println(rowColumnString);
	}

	private static void debugMaze(String here) {
		System.out.println(here);
		printMaze(maze);
		System.out.println();
	}

	private static void debugMaze() {
		printMaze(maze);
		System.out.println();
	}

	private static boolean isWall(int row, int column) {
		return isWithinBounds(row, column) && maze[row][column] == -1;
	}

	private static boolean isPathExist(int row, int column) {		
		// Base Case
		if ( isGoalFound(row, column) ) { 
			System.out.println("Goal found!");
			visitedCellTracker[row][column] = 1;
			return true;
		}

		// Keep trying, go deeper
		else {

			visitedCellTracker[row][column] = 1;
			numSteps += 1;

			if ( isValidCell(row-1, column) && isPathExist(row-1, column) ) return true; // Check U cell
			if ( isValidCell(row+1, column) && isPathExist(row+1, column) ) return true; // Check D cell 
			if ( isValidCell(row, column-1) && isPathExist(row, column-1) ) return true; // Check L cell 
			if ( isValidCell(row, column+1) && isPathExist(row, column+1) ) return true; // Check R cell 
			if ( haveBrokenWall == false ) {
				if ( isWall(row-1, column) ) {
					// Break U cell
					maze[row-1][column] = 0;
					haveBrokenWall = true;
					if( isPathExist(row-1, column) ) {
						numSteps += 1;
						return true;
					}
					maze[row-1][column] = -1;
					haveBrokenWall = false;
				}

				if ( isWall(row, column-1) ) {
					// Break L cell
					maze[row][column-1] = 0;
					haveBrokenWall = true;
					if( isPathExist(row, column-1) ) {
						numSteps += 1;
						return true;
					}
					maze[row][column-1] = -1;
					haveBrokenWall = false;
				}

				if ( isWall(row, column+1) ) {
					// Break R cell
					maze[row][column+1] = 0;
					haveBrokenWall = true;
					if( isPathExist(row, column+1) ) {
						numSteps += 1;
						return true;
					}
					maze[row][column+1] = -1;
					
					haveBrokenWall = false;
				}

				if ( isWall(row+1, column) ) {
					// Break D cell
					maze[row+1][column] = 0;
					haveBrokenWall = true;
					if( isPathExist(row+1, column) ) {
						numSteps += 1;
						return true;
					}
					maze[row+1][column] = -1;
					haveBrokenWall = false;
				}
			}

			// Wrong branch, backtrack
			visitedCellTracker[row][column] = 0;
			numSteps -= 1;
			return false;
		}
	}


	public static void main(String[] args) {
		System.out.println();
		// printMaze(maze);
		// System.out.println();
		boolean pathExist = solveMaze(entryRow, entryColumn);
		if ( pathExist == true ) {
			System.out.println("Path exists.");
			System.out.println("Path Length: " + numSteps);
		}
		else {
			System.out.println("Path doesn't exist.");
		}
		System.out.println();
		printMaze(maze);
	}
}