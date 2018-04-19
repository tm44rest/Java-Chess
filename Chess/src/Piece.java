import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/** An instance is a chess piece. */
public class Piece {
	private boolean isWhite;	// "this piece is white"
	private PieceType type;		// the type of piece this is i.e. pawn, knight etc.
	private Board board;	// the board this piece is on
	
	/** Constructor: creates a chess piece with specified color
	 * 	and piece type. */
	public Piece(boolean isWhite, PieceType type, Board board) {
		this.isWhite = isWhite;
		this.type = type;
		this.board = board;
	}
	
	/** Return this piece's type. */
	public PieceType getType() {
		return type;
	}
	
	/** Return true if this piece's color is white. */
	public boolean isWhite() {
		return isWhite;
	}
	
	/** Return a list of possible points this piece can move to depending on its
	 * 	type and the given location.
	 * 	Precondition: Point xy is between (0,0) and (7,7) */
	public List<Point> tileMovement(Point xy)  {
		// TODO implement tile movement for this piece
		List<Point> movementPoints = new ArrayList<Point>();
		switch (type) {
		case PAWN: break;
		case KNIGHT: break;
		case BISHOP: break;
		case ROOK: {
			movementPoints.addAll(rookMovement(xy,true,true));	// Right
			movementPoints.addAll(rookMovement(xy,false,true));	// Left
			movementPoints.addAll(rookMovement(xy,true,false));	// Forward
			movementPoints.addAll(rookMovement(xy,false,false));// Behind	
			break;
		}
		case QUEEN: break;
		case KING: break;
		}
		return movementPoints;
	}
	
	/** Return false iff 0 <= x <= 7 and 0 <= y <= 7. */
	private boolean isOutOfBounds(int x, int y) {
		return !(x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	/** Return a list of all the points the rook can go to starting at xy and
	 * 	moving positively if positive and negatively if not positive along 
	 * 	the x-axis if isX and y-axis if not isX. */
	private ArrayList<Point> rookMovement(Point xy, boolean positive, boolean isX) {
		ArrayList<Point> movementPoints = new ArrayList<Point>();
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
		return movementPoints;
	}
}
