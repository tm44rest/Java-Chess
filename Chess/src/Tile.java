import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

/** An instance is a chess tile. 
 * 	Contains information for the tile's location, color, and what piece (if 
 * 	any) is on it. */
@SuppressWarnings("serial")
public class Tile extends Canvas {
	private static final int HEIGHT = 70;
	private static final int WIDTH = 70;
	
	private Point location;	// the (x,y) location of this tile on the board
	
	private Piece piece;	// the piece on this tile (null if no piece)
	
	/** Constructor: A tile on the chess board. 
	 * 	Point xy represents the (x,y) coordinate of the board.
	 * 	i.e. (0,1) would be b8. */
	public Tile (Point xy) {
		location = xy;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		piece = null;
	}
	
	/** Constructor: A tile on the chess board. 
	 * 	x and y represent the (x,y) coordinate of the board.
	 * 	i.e. (0,1) would be b8. */
	public Tile(int x, int y) {
		this(new Point(x,y));
	}
	
	/** Update the piece on this tile. */
	public void updatePiece(Piece p) {
		piece = p;
	}
	
	/** Return the piece on this tile. */
	public Piece getPiece() {
		return piece;
	}
	
	/** Paint this tile using g. */
	public @Override void paint(Graphics g) {
		// Paint the empty tile
		if ((location.getX() + location.getY()) % 2 == 0) g.setColor(Color.white);
		else g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH-1, HEIGHT-1);
		
		// Paint the piece on this tile
		if (piece != null) {
			// TODO: Paint the piece
		}
	}
	
}
