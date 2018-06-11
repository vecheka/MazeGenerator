import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * TCSS 342 - HW5
 */

/**
 * A class to implements Graphic representation of the maze.
 * @author Vecheka Chhourn
 * @version 1.0
 */
public class MazeGraphics extends JComponent implements Observer {

	/** Generated serial number.*/
	private static final long serialVersionUID = 8424449732146027470L;
	/** Default dimension of the maze's map.*/
	private static final int DEFAULT_DIMENSION = 500;
	/** Wall's Character.*/
	private static final char WALL = 'X';
	/** Space's Character.*/
	private static final char SPACE = ' ';
	/** Start Character.*/
	private static final char START = 'S';
	/** End Character.*/
	private static final char END = 'E';
	
	
	/** Width of the maze.*/
	private int myWidth;
	/** Height of the maze.*/
	private int myHeight;
	/** Block's size.*/
	private int myBlockSize;
	/** Maze's Map.*/
	private char[][] myMaze;
	
	
	
	/** Constructor to take the width and height of 
	 *  the maze.
	 *  @param theWidth of the maze
	 *  @param theHeight of the maze
	 */
	public MazeGraphics(final int theWidth, final int theHeight) {
		myWidth = theWidth;
		myHeight = theHeight;
		myBlockSize = DEFAULT_DIMENSION / myWidth;
	}
	
	/**
	 * Set up frame to show the maze.
	 */
	public void setUpComponents() {
		final JFrame frame = new JFrame();
		
		frame.add(this);
		frame.setSize(new Dimension(DEFAULT_DIMENSION, DEFAULT_DIMENSION + myWidth));
		frame.setTitle("Maze Graphic");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
//		frame.pack();
	}
	
	
	@Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        final Graphics2D g2D = (Graphics2D) theGraphics;
        
        // for better graphics display
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
       
        drawMaze(g2D);
    }


	/**
	 * Draw the maze's board.
	 * @param theG graphic component to draw the maze.
	 */
	private void drawMaze(final Graphics2D theG) {
		
		int x = 0;
		int y = 0;
		final int r = 15;

		if (myMaze != null) {
			for (int i = 0; i < myWidth; i++) {
				for (int j = 0; j < myHeight; j++) {
					final Shape block = new Rectangle(x, y, x + myBlockSize, y + myBlockSize);
					if (myMaze[i][j] == SPACE) {
						fillRectangle(theG, Color.white, block);
					}
					else if (myMaze[i][j] == WALL) {
						fillRectangle(theG, Color.black, block);
					} else if(myMaze[i][j] == START || myMaze[i][j] == END) {
						fillRectangle(theG, Color.white, block);
						fillOval(theG, Color.blue, x , y  , r);
					} else {
						fillRectangle(theG, Color.white, block);
						fillOval(theG, Color.red, x , y  , r);
					}
					
					x += myBlockSize;
					
				}
				x = 0;
				y += myBlockSize;
			}
		}
	}
	
	/**
	 * Fill an oval on the GUI with the given coordinates, radius, and color.
	 * @param theG painter to fill the oval
	 * @param theColor color to fill with
	 * @param theX x coordinate
	 * @param theY y coordinate
	 * @param theRadius radius of the oval
	 */
	private void fillOval(final Graphics2D theG, final Color theColor, final int theX, 
			final int theY, final int theRadius) {
		theG.setColor(theColor);
		theG.fillOval(theX, theY, theRadius, theRadius);
		
	}

	/**
	 * Fill a rectangle on the GUI with the given color and block's size.
	 * @param theG painter to fill the rectangle
	 * @param theColor color to fill with
	 * @param theBlock block's size
	 */
	private void fillRectangle(final Graphics2D theG, final Color theColor, final Shape theBlock) {
		theG.setColor(theColor);
		theG.fill(theBlock);
	}

	@Override
	public void update(final Observable theObservable, final Object theData) {
		if (theData instanceof char[][]) {
			myMaze = (char[][]) theData;
			repaint();
		} 
	}
	
}
