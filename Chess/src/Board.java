import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
/** An instance is a chess board.
 * 	This object is the "brains" of the chess game. */
public class Board extends Box {

	private Tile[][] tiles;	// tile grid
	
	// Map that corresponds every piece on the board to its set of possible
	// moves it can travel to.
	private Map<Piece, Set<Point>> whiteMoves;	// every piece isWhite
	private Map<Piece, Set<Point>> blackMoves;	// every piece !isWhite
	
	// Sets of every piece of a certain color
	private Set<Piece> whitePieces;	// every piece isWhite
	private Set<Piece> blackPieces;	// every piece !isWhite
	
	private MouseEvents mouseEvent = new MouseEvents();	// object that processes mouse clicks
	
	/** Constructor: creates a chess board with empty tiles. */
	public Board() {
		super(BoxLayout.X_AXIS);
		
		// Create the tile grid
		tiles = new Tile[8][8];
		
		// inv: rows 0..i-1 have been set up
		for (int i = 0; i != 8; i++) {
			// Create the ith row of tiles
			Box row = new Box(BoxLayout.Y_AXIS);
			
			// inv: tiles 0..i-1 have been set up
			for (int j = 0; j != 8; j++) {
				// Create the tiles for each row
				tiles[i][j] = new Tile(i,j);
				row.add(tiles[i][j]);
				// Add a mouse event listener to the tile
				tiles[i][j].addMouseListener(mouseEvent);
			}
			
			this.add(row);	// add the row of tiles to this board
		}
		
		// Create the movement maps
		whiteMoves = new HashMap<Piece, Set<Point>>();
		blackMoves = new HashMap<Piece, Set<Point>>();
		
		// Create the sets of pieces
		whitePieces = new HashSet<Piece>();
		blackPieces = new HashSet<Piece>();
		
	}
	
	/** Constructor: creates a board and set up pieces if shouldSetUp is true.*/
	public Board(boolean shouldSetUp) {
		this();
		if (shouldSetUp) setUpPieces();
	}
	
	/** Return the tile on this board at (x,y).
	 * 	Precondition: (x,y) is on the board. */
	public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
		return tiles[x][y];
	}

	/** Return the current map of all white moves if white is true and the
	 * 	current map of all black moves if white is false. */
	public Map<Piece, Set<Point>> getMoves(boolean white) {
		return (white ? whiteMoves : blackMoves);
	}
	
	/** Return whitePieces if white is true and blackPieces if white is false.*/
	public Set<Piece> getPieces(boolean white) {
		return (white ? whitePieces : blackPieces);
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
		
		// Update the movement maps
		updateMap(true);	// White moves
		updateMap(false);	// Black moves
	}
	
	/** Set up the standard isWhite back rank. */
	private void setUpBackRank(boolean isWhite) {
		int y = (isWhite ? 7 : 0);
		placeNewPiece(new Piece(isWhite, PieceType.ROOK, this, new Point(0,y)));
		placeNewPiece(new Piece(isWhite, PieceType.KNIGHT, this, new Point(1,y)));
		placeNewPiece(new Piece(isWhite, PieceType.BISHOP, this, new Point(2,y)));
		placeNewPiece(new Piece(isWhite, PieceType.QUEEN, this, new Point(3,y)));
		placeNewPiece(new Piece(isWhite, PieceType.KING, this, new Point(4,y)));
		placeNewPiece(new Piece(isWhite, PieceType.BISHOP, this, new Point(5,y)));
		placeNewPiece(new Piece(isWhite, PieceType.KNIGHT, this, new Point(6,y)));
		placeNewPiece(new Piece(isWhite, PieceType.ROOK, this, new Point(7,y)));
	}
	
	/** Set up the standard pawn line. */
	private void setUpPawns(boolean isWhite) {
		int y = (isWhite ? 6 : 1);
		for (int x = 0; x != 8; x++) {
			placeNewPiece(new Piece(isWhite, PieceType.PAWN, this, new Point(x,y)));
		}
	}
	
	/** Places p on the board with all its given properties.
	 * 	Precondition: the board that p is constructed on is this board. */
	public void placeNewPiece(Piece p) {
		// Update tile to add this piece
		Point xy = p.getLocation();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		tiles[x][y].updatePiece(p);
		
		// Add this piece to its pieces set
		if (p.isWhite()) whitePieces.add(p);
		else blackPieces.add(p);
	}
	
	/** Places piece p on point xy and updates the movement maps. */
	public void placePiece(Piece p, Point xy) {
		// Remove the piece from the original tile
		Point orig = p.getLocation();
		int oldX = (int) orig.getX();	int oldY = (int) orig.getY();
		tiles[oldX][oldY].updatePiece(null);
		
		// Place the piece on the new tile
		int newX = (int) xy.getX();	int newY = (int) xy.getY();
		capturePiece(newX, newY);	// capture piece if necessary
		tiles[newX][newY].updatePiece(p);
		p.updateLocation(xy);
		
		// Update the movement maps
		updateMap(true);	// white moves
		updateMap(false);	// black moves
	}
	
	/** Removes the piece on (x,y) from the board and updates the score. */
	private void capturePiece(int x, int y) {
		Piece p = tiles[x][y].getPiece();
		if (p == null) return;	// no capture if no piece
		
		// Remove the piece from the set of pieces
		if (p.isWhite()) whitePieces.remove(p);
		else blackPieces.remove(p);
		
		// Remove the piece from (x,y)
		tiles[x][y].updatePiece(null);
		
		// TODO: update the score
		
	}
	
	/** Updates the white movement map if white is true and black movement map
	 * 	if white is false. */
	private void updateMap(boolean white) {
		Map<Piece, Set<Point>> moves = new HashMap<Piece, Set<Point>>();
		Set<Piece> pieceSet = (white ? whitePieces : blackPieces);
		
		// inv: pieces [0..n-1] have been added to the map
		for (Piece p : pieceSet) {
			moves.put(p, p.tileMovement());
		}
		
		if (white) whiteMoves = moves;
		else blackMoves = moves;
	}
}
