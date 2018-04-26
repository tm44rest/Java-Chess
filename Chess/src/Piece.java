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
		// TODO implement tile movement for this piece
		Set<Point> movementPoints = new HashSet<Point>();
		switch (type) {
		case PAWN: {
			movementPoints.addAll(pawnMovement(location,board,isWhite));
			break;
		}
		case KNIGHT: {
			movementPoints.addAll(knightMovement(location,board,isWhite));
			break;
		}
		case BISHOP: {
			movementPoints.addAll(bishopMovement(location,board,isWhite));
			break;
		}
		case ROOK: {
			movementPoints.addAll(rookMovement(location,board,isWhite));
			break;
		}
		case QUEEN: {
			movementPoints.addAll(queenMovement(location,board,isWhite));
			break;
		}
		case KING: {
			movementPoints.addAll(kingMovement(location,board,isWhite));
			break;
		}
		}
		return movementPoints;
	}
	
	/** Return false iff 0 <= x <= 7 and 0 <= y <= 7. */
	private static boolean isOutOfBounds(int x, int y) {
		return !(x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	/** Return a list of all the points a pawn can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static HashSet<Point> pawnMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement pawn movement
		HashSet<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}

	/** Return a list of all the points a knight can go to starting at xy on
	 * 	the board. */
	private static HashSet<Point> knightMovement(Point xy, Board board, boolean
			isWhite) {
		// TODO: Implement knight movement
		HashSet<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}

	/** Return a list of all the points a bishop can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static HashSet<Point> bishopMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement bishop movement
		HashSet<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}

	/** Return a list of all the points a rook can go to starting at xy along 
	 *  board and interacting with pieces according to its color isWhite. */
	private static HashSet<Point> rookMovement(Point xy, Board board, boolean isWhite) {
		HashSet<Point> movementPoints = new HashSet<Point>();
		
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
	private static HashSet<Point> queenMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement queen movement
		HashSet<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}
	
	/** Return a list of all the points a king can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static HashSet<Point> kingMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement king movement (need a set to store all the places
		// that are threatened by every piece to make it easy to prevent the
		// king from moving into check
		HashSet<Point> movementPoints = new HashSet<Point>();
		
		return movementPoints;
	}
	
}
