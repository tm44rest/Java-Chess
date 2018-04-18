import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/** An instance is a chess piece. */
public class Piece {
	private boolean isWhite;
	private PieceType type;	
	
	/** Constructor: creates a chess piece with specified color
	 * 	and piece type. */
	public Piece(boolean isWhite, PieceType type) {
		this.isWhite = isWhite;
		this.type = type;
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
		case ROOK: break;
		case QUEEN: break;
		case KING: break;
		}
		removeInvalidPoints(movementPoints);
		return movementPoints;
	}
	
	/** Helper method to remove all points that are not between (0,0) and (7,7)*/
	private void removeInvalidPoints(List<Point> list) {
		for (Point xy : list) {
			int x = (int) xy.getX();
			int y = (int) xy.getY();
			if (x > 7 || x < 0 || y > 7 || y < 0)  {
				list.remove(xy);
			}
		}
	}
}
