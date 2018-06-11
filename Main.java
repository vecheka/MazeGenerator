/*
 * TCSS 342- HW5
 */

/**
 * Main class to execute the Maze program.
 * @author Vecheka Chhourn
 * @version 1.0
 */
public class Main {
	
	/**
	 * Main method to call the Maze's class functionalities.
	 * @param theArgs command line arguments
	 */
	public static void main(final String[] theArgs) {
		// creating a 20x20 maze
		Maze maze = new Maze(20, 20, false);
		maze.display();
		
		// creating a 5x5 maze
//		maze = new Maze(5, 5, true);
//		maze.display();
		
		// creating a 10x10 maze
//		maze = new Maze(10, 10, false);
//		maze.display();
	}



}
