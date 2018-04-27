import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
/** An instance is a chess board. */
public class Board extends Box {

	private Tile[][] tiles;	// tile grid
	
	/** Constructor: creates a chess board with empty tiles. */
	public Board() {
		super(BoxLayout.Y_AXIS);
		
		// Create the tile grid
		tiles = new Tile[8][8];
		
		// inv: rows 0..i-1 have been set up
		for (int i = 0; i != 8; i++) {
			// Create the ith row of tiles
			Box row = new Box(BoxLayout.X_AXIS);
			
			// inv: tiles 0..i-1 have been set up
			for (int j = 0; j != 8; j++) {
				// Create the tiles for each row
				tiles[i][j] = new Tile(i,j);
				row.add(tiles[i][j]);
				// TODO: add a mouse event listener to the tile
			}
			
			this.add(row);	// add the row of tiles to this board
		}
		
	}
	
	public Board(boolean shouldSetUp) {
		this();
		if (shouldSetUp) setUpPieces();
	}
	
	/** Set up the chess board for the beginning of a game. 
	 * 	Precondition: every tile has no piece on it. */
	public void setUpPieces() {
		// White
		setUpBackRank(true);
		setUpPawns(true);
		// Black
		setUpBackRank(false);
		setUpPawns(false);
	}
	
	/** Set up the standard isWhite back rank. */
	private void setUpBackRank(boolean isWhite) {
		int y = (isWhite ? 7 : 0);
		placePiece(new Piece(isWhite, PieceType.ROOK, this, new Point(0,y)), new Point(0,y));
		placePiece(new Piece(isWhite, PieceType.KNIGHT, this, new Point(1,y)), new Point(1,y));
		placePiece(new Piece(isWhite, PieceType.BISHOP, this, new Point(2,y)), new Point(2,y));
		placePiece(new Piece(isWhite, PieceType.QUEEN, this, new Point(3,y)), new Point(3,y));
		placePiece(new Piece(isWhite, PieceType.KING, this, new Point(4,y)), new Point(4,y));
		placePiece(new Piece(isWhite, PieceType.BISHOP, this, new Point(5,y)), new Point(5,y));
		placePiece(new Piece(isWhite, PieceType.KNIGHT, this, new Point(6,y)), new Point(6,y));
		placePiece(new Piece(isWhite, PieceType.ROOK, this, new Point(7,y)), new Point(7,y));
	}
	
	/** Set up the standard pawn line. */
	private void setUpPawns(boolean isWhite) {
		int y = (isWhite ? 6 : 1);
		for (int x = 0; x != 8; x++) {
			placePiece(new Piece(isWhite, PieceType.PAWN, this, new Point(x,y)), new Point(x,y));
		}
	}
	
	/** Return the tile on this board at (x,y).
	 * 	Precondition: (x,y) is on the board. */
	public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
		return tiles[x][y];
	}
	
	/** Places piece p on point xy. */
	public void placePiece(Piece p, Point xy) {
		// Remove the piece from the original tile
		Point orig = p.getLocation();
		int oldX = (int) orig.getX();	int oldY = (int) orig.getY();
		tiles[oldX][oldY].updatePiece(null);
		
		// Place the piece on the new tile
		int newX = (int) xy.getX();	int newY = (int) xy.getY();
		tiles[newX][newY].updatePiece(p);
		p.updateLocation(xy);
	}
}
