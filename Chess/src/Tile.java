import java.awt.Point;

/** An instance is a chess tile. 
 * 	Contains information for the tile's location, color, and what piece (if 
 * 	any) is on it. */
public class Tile {
	private Point location;	// the (x,y) location of this tile on the board
	
	/** Constructor: A tile on the chess board. 
	 * 	Point xy represents the (x,y) coordinate of the board.
	 * 	i.e. (0,1) would be b8. */
	public Tile (Point xy) {
		location = xy;
	}
	
	/** Constructor: A tile on the chess board. 
	 * 	x and y represent the (x,y) coordinate of the board.
	 * 	i.e. (0,1) would be b8. */
	public Tile(int x, int y) {
		this(new Point(x,y));
	}
	
}
