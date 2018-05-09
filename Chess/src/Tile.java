import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/** An instance is a chess tile. 
 * 	Contains information for the tile's location, color, and what piece (if 
 * 	any) is on it. */
@SuppressWarnings("serial")
public class Tile extends JPanel {
	private static final int HEIGHT = 70;
	private static final int WIDTH = 70;
	
	private Point location;	// the (x,y) location of this tile on the board
	
	private Piece piece;	// the piece on this tile (null if no piece)
	
	private Color color;
	
	/** Constructor: A tile on the chess board. 
	 * 	Point xy represents the (x,y) coordinate of the board.
	 * 	i.e. (0,1) would be b8. */
	public Tile (Point xy) {
		location = xy;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setColorDefault();
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
	
	/** Return this tile's location point. */
	public Point getLocation() {
		return location;
	}
	
	/** Return the piece on this tile. */
	public Piece getPiece() {
		return piece;
	}
	
	/** Set this tile to its default color (white or black). */
	public void setColorDefault() {
		if ((location.getX() + location.getY()) % 2 == 0) color = Color.white;
		else color = Color.black;
	}
	
	/** Set this tile to the color for "selected" (dark blue). */
	public void setColorSelected() {
		color = Color.yellow;
	}
	
	/** Paint this tile using g. */
	public @Override void paint(Graphics g) {
		// Paint the empty tile
		g.setColor(color);
		g.fillRect(0, 0, WIDTH-1, HEIGHT-1);
		
		// Paint the piece on this tile
		if (piece != null) {
			String s = (piece.isWhite() ? "W" : "B") + piece.getType().getAbrreviation();
			g.setColor(g.getColor() == Color.black ? Color.white : Color.black);
			g.drawString(s, WIDTH/2, HEIGHT/2);
		}
	}
	
}
