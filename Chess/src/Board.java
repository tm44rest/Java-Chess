import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings("serial")
/** An instance is a chess board.
 * 	This object is the "brains" of the chess game. */
public class Board extends Box {

	private Tile[][] tiles;	// tile grid
	
	// Chess players
	private Player player1;	// plays white
	private Player player2;	// plays black
	
	// Map that corresponds every piece on the board to its set of possible
	// moves it can travel to.
	private Map<Piece, Set<Point>> whiteMoves;	// every piece isWhite
	private Map<Piece, Set<Point>> blackMoves;	// every piece !isWhite
	
	// Sets of every piece of a certain color
	private Set<Piece> whitePieces;	// every piece isWhite
	private Set<Piece> blackPieces;	// every piece !isWhite
	
	private MouseEvents mouseEvent = new MouseEvents(this);	// object that processes mouse clicks
	
	// The two king objects (only 1 king of each color per board)
	private Piece whiteKing;
	private Piece blackKing;
	private boolean whiteKingInCheck;	// "the white king is in check"
	private boolean blackKingInCheck;	// "the black king is in check"
	
	// Pawns that double stepped last turn. null if no pawn double stepped.
	private Piece whiteDoubleStepped;
	private Piece blackDoubleStepped;
	
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
		
		// Create the players
		player1 = new Player(true);
		player2 = new Player(false);
		
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
	
	/** Return the double stepped isWhite pawn. */
	public Piece getDoubleStep(boolean isWhite) {
		return (isWhite ? whiteDoubleStepped : blackDoubleStepped);
	}
	
	/** Update the double stepped pawn. 
	 * 	Precondition: p is a pawn*/
	private void updateDoubleStep(Piece p) {
		if (p.isWhite()) whiteDoubleStepped = p;
		else blackDoubleStepped = p;
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
		updateMaps(false);
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
		
		// If it's a king, add it to the field
		if (p.getType() == PieceType.KING) {
			if (p.isWhite()) whiteKing = p;
			else blackKing = p;
		}
	}
	
	/** Places piece p on point xy and updates the movement maps. 
	 * 	Precondition: p is not null. */
	public void placePiece(Piece p, Point xy) {
		boolean doEP = enPassantApplies(p, xy);	// Check if en passant applies
		
		// Remove the piece from the original tile
		Point orig = p.getLocation();
		int oldX = (int) orig.getX();	int oldY = (int) orig.getY();
		tiles[oldX][oldY].updatePiece(null);
		
		// Place the piece on the new tile
		int newX = (int) xy.getX();	int newY = (int) xy.getY();
		capturePiece(newX, newY);	// capture piece if necessary
		tiles[newX][newY].updatePiece(p);
		p.updateLocation(xy);
		
		// Perform en passant if necessary
		if (doEP) {
			int f = (!p.isWhite() ? -1 : 1);
			capturePiece(newX, newY+f);
		}
		
		// TODO: Promote if necessary
		
		// If the piece is a pawn that just double stepped, update the double
		// step piece for this board
		if (p.getType() == PieceType.PAWN && newY == (p.isWhite() ? 
				oldY-2 : oldY+2)) updateDoubleStep(p);
		
		// Remove the double step piece for the opposing color
		if (p.isWhite()) blackDoubleStepped = null;
		else whiteDoubleStepped = null;
		
		// Update the movement maps
		updateMaps(p.isWhite());
		
		// Add 1 to the turn count for p's player
		updateTurnCount(p.isWhite());
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
	
	/** "the piece moving to xy qualifies as en passant" 
	 * 	i.e. xy has null piece on it and (x,y+f) has the double stepped pawn. */
	private boolean enPassantApplies(Piece p, Point xy) {
		if (p.getType() != PieceType.PAWN) return false;
		
		int f = (!p.isWhite() ? -1 : 1);	// opponent's forward direction
		Piece ds = (!p.isWhite() ? whiteDoubleStepped : blackDoubleStepped);
		int x = (int) xy.getX();	int y = (int) xy.getY();
		
		return (getTile(x,y).getPiece() == null &&
				getTile(x,y+f).getPiece() == ds);
	}
	
	/** Updates the movement maps. */
	private void updateMaps(boolean isWhite) {
		updateMapNoKing(true);
		updateMapNoKing(false);
		updateMapKing(true);
		updateMapKing(false);
		// if the updated movement maps have a king in check, update the field
		// and run updateMaps() again
		// TODO
		if (updateInCheck(!isWhite)) updateMaps(isWhite);
	}
	
	/** Helper Method: Updates the white movement map if white is true and 
	 * 	black movement map if white is false. */
	private void updateMapNoKing(boolean white) {
		Map<Piece, Set<Point>> moves = new HashMap<Piece, Set<Point>>();
		Set<Piece> pieceSet = (white ? whitePieces : blackPieces);
		
		// inv: pieces [0..n-1] have been added to the map
		for (Piece p : pieceSet) {
			if (p.getType() == PieceType.KING) continue;
			moves.put(p, p.tileMovement());
		}
		
		if (white) whiteMoves = moves;
		else blackMoves = moves;
	}
	
	/** Update the isWhite king's movement map. */
	private void updateMapKing(boolean isWhite) {
		if (isWhite && whiteKing != null) whiteMoves.put(whiteKing, whiteKing.tileMovement());
		else if (blackKing != null) blackMoves.put(blackKing, blackKing.tileMovement());
	}
	
	/** "isWhite king is in check" */
	public boolean isInCheck(boolean isWhite) {
		return (isWhite ? whiteKingInCheck : blackKingInCheck);
	}
	
	/** Calculates if the isWhite king is in check, and returns if the field
	 * 	has been updated. */
	private boolean updateInCheck(boolean isWhite) {
		if ((isWhite ? whiteKing : blackKing) == null) return false;
		Map<Piece, Set<Point>> threatMap = (isWhite ? whiteMoves : blackMoves);
		Point xy = (isWhite ? whiteKing.getLocation() : blackKing.getLocation());
		boolean kingInCheck = (isWhite ? whiteKingInCheck : blackKingInCheck);
		
		// inv: pieces before entry have been checked
		for (Entry<Piece, Set<Point>> entry : threatMap.entrySet()) {
			// king is on a threatened tile (in check)
			if (entry.getValue().contains(xy)) {
				if (kingInCheck == false) {
					kingInCheck = true;
					return true;
				}
				return false;
			}
		}
		
		// king is not in check
		if (kingInCheck == true) {
			kingInCheck = false;
			return true;
		}
		return false;
	}
	
	/** Increment the isWhite player's turn count. */
	private void updateTurnCount(boolean isWhite) {
		if (isWhite) player1.nextTurn();
		else player2.nextTurn();
	}
	
	/** Return player1 if player1's turn count is less than or equal to
	 * 	player2's turn count. Otherwise, return player2. */
	public Player whoseTurn() {
		return (player1.getTurnCount() <= player2.getTurnCount() ? 
				player1 : player2);
	}
}
