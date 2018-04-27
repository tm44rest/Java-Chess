import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** An instance is a chess piece. */
public class Piece {
	private boolean isWhite;	// "this piece is white"
	private PieceType type;		// the type of piece this is i.e. pawn, knight etc.
	private Board board;	// the board this piece is on
	private Point location;	// the location of this piece on board
	
	/** Constructor: creates a chess piece with specified color
	 * 	and piece type at a certain location on the board. */
	public Piece(boolean isWhite, PieceType type, Board board, Point location) {
		this.isWhite = isWhite;
		this.type = type;
		this.board = board;
		updateLocation(location);	// updates both this field and the
									// corresponding tile's field
	}
	
	/** Return this piece's type. */
	public PieceType getType() {
		return type;
	}
	
	/** Return true if this piece's color is white. */
	public boolean isWhite() {
		return isWhite;
	}
	
	/** Update the location of this piece on the board. */
	public void updateLocation(Point xy) {
		board.getTile((int) xy.getX(), (int) xy.getY()).updatePiece(this);;
		location = xy;
	}
	
	/** Return a set of possible points this piece can move to depending on its
	 * 	type and the current location. */
	public Set<Point> tileMovement()  {
		switch (type) {
		case PAWN: return pawnMovement(location,board,isWhite);
		case KNIGHT: return knightMovement(location,board,isWhite);
		case BISHOP: return bishopMovement(location,board,isWhite);
		case ROOK: return rookMovement(location,board,isWhite);
		case QUEEN: return queenMovement(location,board,isWhite);
		case KING: return kingMovement(location,board,isWhite);
		}
		
		// this piece's PieceType is null
		return new HashSet<Point>();
	}
	
	/** Return false iff 0 <= x <= 7 and 0 <= y <= 7. */
	private static boolean isOutOfBounds(int x, int y) {
		return !(x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	/** Return a list of all the points a pawn can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> pawnMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement pawn movement
		Set<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}

	/** Return a list of all the points a knight can go to starting at xy on
	 * 	the board. */
	private static Set<Point> knightMovement(Point xy, Board board, boolean
			isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		
		// Check and potentially add the 8 points a knight can go to at any time
		knightHelper(x-1,y-2,board,isWhite,movementPoints);
		knightHelper(x-2,y-1,board,isWhite,movementPoints);
		knightHelper(x+2,y+1,board,isWhite,movementPoints);
		knightHelper(x+1,y+2,board,isWhite,movementPoints);
		knightHelper(x+1,y-2,board,isWhite,movementPoints);
		knightHelper(x+2,y-1,board,isWhite,movementPoints);
		knightHelper(x-2,y+1,board,isWhite,movementPoints);
		knightHelper(x-1,y+2,board,isWhite,movementPoints);
		
		return movementPoints;
	}

	/** Helper method: Checks if tile at point ab has a piece of the same 
	 * 	color as isWhite or is out of bounds and adds it to the set if not. */
	private static void knightHelper(int a, int b, Board board, boolean isWhite,
			Set<Point> movementPoints) {
		if (isOutOfBounds(a,b)) return;
		Piece checkedPiece = board.getTile(a,b).getPiece();
		if (checkedPiece == null || checkedPiece.isWhite() != isWhite) {
			movementPoints.add(new Point(a,b));
		}
	}
	
	/** Return a list of all the points a bishop can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> bishopMovement(Point xy, Board board, 
			boolean isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();

		// Points to the north-east of the bishop
		boolean mustStop = false;
		int x = (int) xy.getX();
		int y = (int) xy.getY();
		while (!mustStop) {	
			x = x+1;
			y = y-1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}

		// Points to the south-east of the bishop
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {
			x = x+1;
			y = y+1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}

		// Points to the north-west of the bishop
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {
			x = x-1;
			y = y-1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}

		// Points to the south-west of the bishop
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {	
			x = x-1;
			y = y+1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}

		return movementPoints;
	}

	/** Return a list of all the points a rook can go to starting at xy along 
	 *  board and interacting with pieces according to its color isWhite. */
	private static Set<Point> rookMovement(Point xy, Board board, boolean isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();
		
		// Points to the east of the rook
		boolean mustStop = false;
		int x = (int) xy.getX();
		int y = (int) xy.getY();
		while (!mustStop) {	
			x = x+1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}
		
		// Points to the south of the rook
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {
			y = y+1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}
		
		// Points to the west of the rook
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {
			x = x-1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}
		
		// Points to the north of the rook
		mustStop = false;
		x = (int) xy.getX();
		y = (int) xy.getY();
		while (!mustStop) {	
			y = y-1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece == null) {movementPoints.add(new Point(x,y));}
				else if (checkedPiece.isWhite() == isWhite) mustStop = true;
				else {movementPoints.add(new Point(x,y)); mustStop = true;}
			}
		}
		
		return movementPoints;
	}
	
	/** Return a list of all the points a queen can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> queenMovement(Point xy, Board board, 
			boolean isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();
		movementPoints.addAll(rookMovement(xy, board, isWhite));
		movementPoints.addAll(bishopMovement(xy, board, isWhite));
		return movementPoints;
	}
	
	/** Return a list of all the points a king can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> kingMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement king movement (need a set to store all the places
		// that are threatened by every piece to make it easy to prevent the
		// king from moving into check
		Set<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}
	
}
