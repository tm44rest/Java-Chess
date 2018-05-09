import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	
	/** Return the location of this piece as a point. */
	public Point getLocation() {
		return location;
	}
	
	/** Update the location of this piece on the board. */
	public void updateLocation(Point xy) {
		board.getTile((int) xy.getX(), (int) xy.getY()).updatePiece(this);
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
		case KING: {
			Map<Piece, Set<Point>> threatMap = board.getMoves(!isWhite);
			return kingMovement(location,board,isWhite,threatMap);
		}
		}
		
		// this piece's type is null
		return new HashSet<Point>();
	}
	
	/** Return false iff 0 <= x <= 7 and 0 <= y <= 7. */
	private static boolean isOutOfBounds(int x, int y) {
		return !(x >= 0 && x <= 7 && y >= 0 && y <= 7);
	}
	
	/** Return true if the move (x,y) to (a,b) puts the king in check. 
	 * 	Precondition: there is a piece on (x,y) and (x,y) -> (a,b) is valid. */
	private static boolean putsKingInCheck(int x, int y, int a, int b,
			Board board) {
		// Makes a temporary board and calculates isInCheck on that board after
		// moving piece on (x,y) to (a,b)
		// TODO
		Board tempBoard = board.clone();
		return false;
	}
	
	/** Return a list of all the points a pawn can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> pawnMovement(Point xy, Board board, 
			boolean isWhite) {
		// TODO: Implement pawn movement
		Set<Point> movementPoints = new HashSet<Point>();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		
		// Color determines whether north or south is "in front"
		int f = (isWhite ? -1 : 1);
		
		// Tile in front of pawn
		pawnHelper(x,y,x,y+f, board, isWhite, movementPoints);
		
		// If on the starting tile, 2 tiles in front of pawn
		if (y == (isWhite ? 6 : 1)) {
			pawnHelper(x,y,x,y+2*f, board, isWhite, movementPoints);
		}
		
		// If diagonal in front of pawn has a piece of the opposing color
		if (x != 0) {
			Piece dp = board.getTile(x-1, y+f).getPiece();
			if (dp != null && dp.isWhite() != isWhite && 
					!putsKingInCheck(x,y,x-1,y+f,board)) 
				movementPoints.add(new Point(x-1, y+f));
		} 
		if (x != 7) {
			Piece dp = board.getTile(x+1, y+f).getPiece();
			if (dp != null && dp.isWhite() != isWhite &&
					!putsKingInCheck(x,y,x+1,y+f,board)) 
				movementPoints.add(new Point(x+1, y+f));
		}
		
		// If en passant conditions are met:
		// (1) This pawn is on its fifth rank
		// (2) An adjacent pawn of the opposite color just made a double step 
		// in the last move
		Piece ds = board.getDoubleStep(!isWhite);
		if (ds != null && y == (isWhite ? 3 : 4)) {
			if (x != 0 && board.getTile(x-1, y).getPiece() == ds &&
					!putsKingInCheck(x,y,x-1,y+f,board))
				movementPoints.add(new Point(x-1, y+f));
			if (x != 7 && board.getTile(x+1, y).getPiece() == ds &&
					!putsKingInCheck(x,y,x+1,y+f,board))
				movementPoints.add(new Point(x+1, y+f));
		}
		
		return movementPoints;
	}

	/** Return a list of all the points a knight can go to starting at xy on
	 * 	the board. */
	private static Set<Point> knightMovement(Point xy, Board board, boolean
			isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		
		// Check and potentially add the 8 points a knight can go to at any time
		moveHelper(x,y,x-1,y-2,board,isWhite,movementPoints);
		moveHelper(x,y,x-2,y-1,board,isWhite,movementPoints);
		moveHelper(x,y,x+2,y+1,board,isWhite,movementPoints);
		moveHelper(x,y,x+1,y+2,board,isWhite,movementPoints);
		moveHelper(x,y,x+1,y-2,board,isWhite,movementPoints);
		moveHelper(x,y,x+2,y-1,board,isWhite,movementPoints);
		moveHelper(x,y,x-2,y+1,board,isWhite,movementPoints);
		moveHelper(x,y,x-1,y+2,board,isWhite,movementPoints);
		
		return movementPoints;
	}

	/** Helper method: Checks if tile at point ab has a piece of the same 
	 * 	color as isWhite or is out of bounds and adds it to the set if not. */
	private static void moveHelper(int x, int y, int a, int b, Board board, 
			boolean isWhite, Set<Point> movementPoints) {
		if (isOutOfBounds(a,b)) return;
		Piece checkedPiece = board.getTile(a,b).getPiece();
		if ((checkedPiece == null || checkedPiece.isWhite() != isWhite) && 
				!putsKingInCheck(x,y,a,b,board)) {
			movementPoints.add(new Point(a,b));
		}
	}
	
	/** Helper method: Checks if tile at point ab has a piece on it or is
	 *  out of bounds and adds it to the set if not. */
	private static void pawnHelper(int x, int y, int a, int b, Board board, 
			boolean isWhite, Set<Point> movementPoints) {
		if (isOutOfBounds(a,b)) return;
		Piece checkedPiece = board.getTile(a,b).getPiece();
		if (checkedPiece == null && !putsKingInCheck(x,y,a,b,board)) {
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
		int a = x;	int b = y;
		while (!mustStop) {	
			x = x+1;
			y = y-1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
					}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
		int a = x;	int b = y;
		while (!mustStop) {	
			x = x+1;
			if (isOutOfBounds(x,y)) mustStop = true;
			else {
				Piece checkedPiece = board.getTile(x,y).getPiece();
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
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
				if (checkedPiece != null && checkedPiece.isWhite() == isWhite) {
					mustStop = true;
				}
				else if (!putsKingInCheck(a,b,x,y,board)) {
					movementPoints.add(new Point(x,y));
					if (checkedPiece != null) mustStop = true;	
				}
			}
		}
		
		return movementPoints;
	}
	
	/** Return a list of all the points a queen can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> queenMovement(Point xy, Board board, 
			boolean isWhite) {
		Set<Point> movementPoints = new HashSet<Point>();
		// Queen is just a combination of rook and bishop
		movementPoints.addAll(rookMovement(xy, board, isWhite));
		movementPoints.addAll(bishopMovement(xy, board, isWhite));
		return movementPoints;
	}
	
	/** Return a list of all the points a king can go to starting at xy on the
	 * 	board and interacting with pieces according to isWhite. */
	private static Set<Point> kingMovement(Point xy, Board board, 
			boolean isWhite, Map<Piece, Set<Point>> threatMap) {
		Set<Point> movementPoints = new HashSet<Point>();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		
		// Check the 8 tiles around the king
		for (int i=-1; i <= 1; i++) {
			for (int j=-1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;	// tile the king is on
				kingHelper(x+i,y+j,board,isWhite,movementPoints,threatMap);
			}
		}
		
		return movementPoints;
	}
	
	/** Helper method: adds tile (x,y) to the set of movement points if it does
	 *  not contain another isWhite piece or is a value in any of the sets of
	 *  threatMap (i.e. puts the king in check). */
	private static void kingHelper(int a, int b, Board board, boolean isWhite, 
			Set<Point> movementPoints, Map<Piece, Set<Point>> threatMap) {
		if (isOutOfBounds(a,b)) return;
		Piece checkedPiece = board.getTile(a,b).getPiece();
		// If a piece of the same color is on this point, don't add it
		if (checkedPiece != null && checkedPiece.isWhite() == isWhite) return;
		Point ab = new Point(a,b);
		// If the point puts the king in check, don't add it
		for (Entry<Piece, Set<Point>> entry : threatMap.entrySet()) {
			Piece p = entry.getKey();
			if (p.getType() == PieceType.KING && 
					surroundingEight(p).contains(ab)) return;
			if (entry.getValue().contains(ab)) return;
		}
		movementPoints.add(ab);
	}
	
	/** Return the surrounding eight tiles around this piece. */
	private static Set<Point> surroundingEight(Piece p) {
		Point xy = p.getLocation();
		int x = (int) xy.getX();	int y = (int) xy.getY();
		Set<Point> surroundingPoints = new HashSet<Point>();
		
		for (int i=-1; i <= 1; i++) {
			for (int j=-1; j <= 1; j++) {
				if (i == 0 && j == 0) continue;	// tile p is on
				surroundingPoints.add(new Point(x+i,y+j));
			}
		}
		
		return surroundingPoints;
	}
	
	/** Return a clone of this piece placed onto Board board. */
	public Piece clone(Board board) {
		return new Piece(isWhite, type, board, location);
	}
	
}
