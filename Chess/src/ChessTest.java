import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChessTest {

	@Test
	void testPieces() {
		testRookMovement();
	}

	void testRookMovement() {
		// No pieces in the way, center
		Board board1 = new Board();
		Piece rookPiece1 = new Piece(true, PieceType.ROOK, board1, new Point(4,4));
		Set<Point> methodSet1 = rookPiece1.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(4,0));	manualSet1.add(new Point(4,1));
		manualSet1.add(new Point(4,2));	manualSet1.add(new Point(4,3));
		manualSet1.add(new Point(4,5));	manualSet1.add(new Point(4,6));	
		manualSet1.add(new Point(4,7));	manualSet1.add(new Point(0,4));
		manualSet1.add(new Point(1,4));	manualSet1.add(new Point(2,4));
		manualSet1.add(new Point(3,4));	manualSet1.add(new Point(5,4));
		manualSet1.add(new Point(6,4));	manualSet1.add(new Point(7,4));
		assertEquals(true, manualSet1.equals(methodSet1));
		
		// No pieces in the way, corner
		Board board2 = new Board();
		Piece rookPiece2 = new Piece(false, PieceType.ROOK, board2, new Point(7,0));
		Set<Point> methodSet2 = rookPiece2.tileMovement();
		Set<Point> manualSet2 = new HashSet<Point>();
		manualSet2.add(new Point(0,0));	manualSet2.add(new Point(1,0));
		manualSet2.add(new Point(2,0));	manualSet2.add(new Point(3,0));
		manualSet2.add(new Point(4,0));	manualSet2.add(new Point(5,0));
		manualSet2.add(new Point(6,0));	manualSet2.add(new Point(7,1));
		manualSet2.add(new Point(7,2));	manualSet2.add(new Point(7,3));
		manualSet2.add(new Point(7,4));	manualSet2.add(new Point(7,5));
		manualSet2.add(new Point(7,6));	manualSet2.add(new Point(7,7));
		assertEquals(true, manualSet2.equals(methodSet2));
		
		// Pieces in the way, corner
		Board board3 = new Board();
		Piece whiteRook = new Piece(true, PieceType.ROOK, board3, new Point(7,7));
		Piece whiteBishop = new Piece(true, PieceType.BISHOP, board3, new Point(5,7));
		Piece blackPawn = new Piece(false, PieceType.PAWN, board3, new Point(7,2));
		Set<Point> methodSet3 = whiteRook.tileMovement();
		Set<Point> manualSet3 = new HashSet<Point>();
		manualSet3.add(new Point(6,7));	manualSet3.add(new Point(7,6));
		manualSet3.add(new Point(7,5));	manualSet3.add(new Point(7,4));
		manualSet3.add(new Point(7,3));	manualSet3.add(new Point(7,2));
		assertEquals(true, manualSet3.equals(methodSet3));
	}
}
