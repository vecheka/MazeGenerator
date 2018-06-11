import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

/*
 * TCSS 342 - HW 5 
 */

/**
 * @author Vecheka Chhourn
 * @version 1.0 - 
 * A class that implements Prim's Algorithm to generate a maze.
 * ********************* Note ********************* :
 * I have also included a maze's graphic representation of the maze for this project.
 */
public class Maze extends Observable {
	
	/** Wall's Character.*/
	private static final char WALL = 'X';
	/** Space's Character.*/
	private static final char SPACE = ' ';
	/** Solution's Character.*/
	private static final char SOLUTION = '+';
	/** Start Character.*/
	private static final char START = 'S';
	/** End Character.*/
	private static final char END = 'E';
	
	/** Width of the Maze.*/
	private final int myRow;
	/** Depth of the Maze.*/
	private final int myCol;
	/** Debug's Status.*/
	private boolean myDebug;
	/** Maze's 2D Grid.*/
	private final char[][] myMaze;
	/** Walls List.*/
	private final List<int[]> myWallList;
	/** Starting Position.*/
	private Point myStartPos;
	/** End Position.*/
	private Point myEndPos;
	
	
	
	/** 
	 * Constructor that initialize the width and depth of a maze,
	 * set the debugger on/off.
	 * @param width of the maze
	 * @param depth of the maze
	 * @param debug if it is on shows the maze generated steps.
	 */
	public Maze(final int width, final int depth, 
			final boolean debug) {
		if (width % 2 == 0) {
			myRow = width + 1;
			myCol = depth + 1;
		} else {
			myRow = width;
			myCol = depth;
		}
		myDebug = debug;
		myMaze = new char[myRow][myCol];
		myWallList = new LinkedList<>();
		MazeGraphics maze = new MazeGraphics(myRow, myCol);
		addObserver(maze);
		initializeMaze();
		generateMaze();
		solveMaze();
		maze.setUpComponents();
	}
	

	
	



	/**
	 * Displays the maze in string form using 'X' and ' '.
	 */
	public void display() {
		final StringBuilder maze = new StringBuilder();
		for (int r = 0; r < myRow; r++) {
			for (int c = 0; c < myCol; c++) {
				maze.append(myMaze[r][c] + " ");
			}
			maze.append("\n");
		}
		System.out.println(maze.toString());
	}
	
	
	// helper methods
	/**
	 * Initialize maze with walls.
	 */
	private void initializeMaze() {
		for (int r = 0; r < myRow; r++) {
			for (int c = 0; c < myCol; c++) {
				myMaze[r][c] = WALL;
			}
		}
//		setChanged();
//		notifyObservers(myMaze);
	}
	
	/**
	 * Generate maze using Prim's algorithm.
	 */
	private void generateMaze() {
		final Random r = new Random();
		
		int row = 0;
		int col = 1;
		myStartPos = new Point(row, col);
		myWallList.add(new int[]{row, col, row + 1, col});
		while (!myWallList.isEmpty()) {
			final int[] temp = myWallList.remove(r.nextInt(myWallList.size()));
			row = temp[2];
			col = temp[3];
			if (myMaze[row][col] == WALL) {
				myMaze[temp[0]][temp[1]] = SPACE;
				myMaze[row][col] = SPACE;
				
				if (row + 2 < myRow && col < myCol - 1 && myMaze[row + 2][col] == WALL) {
					myWallList.add(new int[]{row + 1, col, row + 2, col});
				}
				if (col + 2 < myCol && row < myRow - 1 && myMaze[row][col + 2] == WALL) {
					myWallList.add(new int[]{row, col + 1, row, col + 2});
				}
				if (col - 2 > 0 && row > 0 && myMaze[row][col -2] == WALL) {
					myWallList.add(new int[]{row, col - 1, row, col - 2});
				}
				if (row - 2 > 0 && col > 0 && myMaze[row - 2][col] == WALL) {
					myWallList.add(new int[]{row - 1, col, row - 2, col});
				}
			}
//			if (myDebug) display();
		}
		
		myMaze[myMaze.length - 1][myMaze.length - 2] = SPACE;
		myEndPos = new Point(myMaze.length - 1, myMaze.length - 2);
	
	}
	
	/**
	 * Find the solution path of the generated maze.
	 */
	private void solveMaze() {
		final List<Point> myVisitedPoints = new ArrayList<>();
		myMaze[myStartPos.x + 1][myStartPos.y] = SOLUTION;
		myVisitedPoints.add(new Point(myStartPos.x + 1, myStartPos.y));
		
		// Go down if it is possible, then right if possible
		// up and left are the two other options.
		// backtrack when hits a dead-end. 
		int backTrackStep = 1;
		Point temp =  myVisitedPoints.get(myVisitedPoints.size() - 1);
		solveMaze(backTrackStep, temp, myVisitedPoints);
//		while (!myVisitedPoints.isEmpty()) {
//			
//			final int x = temp.x;
//			final int y = temp.y;
//			
//			if (x + 1 == myEndPos.x && y == myEndPos.y) break;
//			
//			if (isSpace(x + 1, y) && !hasVisited(myVisitedPoints, new Point(x + 2, y))) { // Down Direction
//				temp = updateMaze(x + 2, y, myVisitedPoints);
//				backTrackStep = 1;
//			} else if (isSpace(x, y + 1) && !hasVisited(myVisitedPoints, new Point(x, y +  2))) { // Right Direction
//				temp = updateMaze(x, y + 2, myVisitedPoints);
//				backTrackStep = 1;
//			} else if (x - 2 > 0 && isSpace(x - 1, y) && !hasVisited(myVisitedPoints, new Point(x - 2, y))) { // Up Direction
//				temp = updateMaze(x - 2, y, myVisitedPoints);
//				backTrackStep = 1;
//			} else if (y - 2 > 0 && isSpace(x, y - 1) && !hasVisited(myVisitedPoints, new Point(x, y -2))) { //  Left Direction
//				temp = updateMaze(x, y - 2, myVisitedPoints);
//				backTrackStep = 1;
//			} else { // Backtracking
//				myMaze[temp.x][temp.y] = SPACE;
//				backTrackStep++;
//				if (myVisitedPoints.size() - backTrackStep < 0) temp = myVisitedPoints.get(0);
//				else temp = myVisitedPoints.get(myVisitedPoints.size() - backTrackStep);
//
//			}
//			
//			if(myDebug) display();
//			
//		}
		myMaze[myStartPos.x][myStartPos.y] = START;
		myMaze[myEndPos.x][myEndPos.y] = END;
		setChanged();
		notifyObservers(myMaze);
		
	}
	
	/**
	 * Recursive method to solve the maze.
	 * @param theStep back track step
	 * @param theCurrent current point/location on the maze
	 * @param theVisitedList list of visited points on the maze
	 */
	private void solveMaze(int theStep, Point theCurrent, final List<Point> theVisitedList) {
		final int x = theCurrent.x;
		final int y = theCurrent.y;
		if (x + 1 == myEndPos.x && y == myEndPos.y) return;
		
		if (isSpace(x + 1, y) && !hasVisited(theVisitedList, new Point(x + 2, y))) { // Down Direction
			theCurrent = updateMaze(x + 2, y, theVisitedList);
			theStep = 1;
			solveMaze(theStep, theCurrent, theVisitedList);
		} else if (isSpace(x, y + 1) && !hasVisited(theVisitedList, new Point(x, y +  2))) { // Right Direction
			theCurrent = updateMaze(x, y + 2, theVisitedList);
			theStep = 1;
			solveMaze(theStep, theCurrent, theVisitedList);
		} else if (x - 2 > 0 && isSpace(x - 1, y) && !hasVisited(theVisitedList, new Point(x - 2, y))) { // Up Direction
			theCurrent = updateMaze(x - 2, y, theVisitedList);
			theStep = 1;
			solveMaze(theStep, theCurrent, theVisitedList);
		} else if (y - 2 > 0 && isSpace(x, y - 1) && !hasVisited(theVisitedList, new Point(x, y -2))) { //  Left Direction
			theCurrent = updateMaze(x, y - 2, theVisitedList);
			theStep = 1;
			solveMaze(theStep, theCurrent, theVisitedList);
		} else { // Backtracking
			myMaze[theCurrent.x][theCurrent.y] = SPACE;
			theStep++;
			if (theVisitedList.size() - theStep < 0) theCurrent = theVisitedList.get(0);
			else theCurrent = theVisitedList.get(theVisitedList.size() - theStep);
			solveMaze(theStep, theCurrent, theVisitedList);

		}
		if (myDebug) display();
		
	}







	/**
	 * Update the maze, and draw the solution path.
	 * @param theX x coordinate on the maze
	 * @param theY y coordinate on the maze
	 * @param theVisitedList list of visited points
	 * @return next point to move to in the maze
	 */
	private Point updateMaze(final int theX, final int theY, final List<Point> theVisitedList) {
		myMaze[theX][theY] = SOLUTION;
		theVisitedList.add(new Point(theX, theY));
		return theVisitedList.get(theVisitedList.size() - 1);
	}


	/**
	 * Determine if the space has already been visited.
	 * @param thePoint coordinates to check
	 * @return true if has visited
	 */
	private boolean hasVisited(final List<Point> theVisitedList, final Point thePoint) {
		return theVisitedList.contains(thePoint);
	}



	/**
	 * Determine if it is a space.
	 * @param theX x coordinate on the map
	 * @param theY y coordinate on the map
	 * @return true if it is a space
	 */
	private boolean isSpace(final int theX, final int theY) {
		return myMaze[theX][theY] == SPACE;
	}
	
}
