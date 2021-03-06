import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ChessTest {

	@Test
	void testPieces() {
		testRookMovement();
		testKnightMovement();
		testBishopMovement();
		// Queen is just bishop + rook, don't need to test
		testKingMovement();
		testPawnMovement();
	}
	
	@Test 
	void testSetUpBoard() {
		Board board1 = new Board(true);
		assertEquals(true, board1.getTile(0,0).getPiece().getType() == PieceType.ROOK);
		assertEquals(true, board1.getTile(0,1).getPiece().getType() == PieceType.PAWN);
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
	
	void testKnightMovement() {
		// No pieces in the way, center
		Board board1 = new Board();
		Piece knightPiece1 = new Piece(true, PieceType.KNIGHT, board1, new Point(4,4));
		Set<Point> methodSet1 = knightPiece1.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(3,2));	manualSet1.add(new Point(2,3));
		manualSet1.add(new Point(2,5));	manualSet1.add(new Point(3,6));
		manualSet1.add(new Point(5,6));	manualSet1.add(new Point(6,5));	
		manualSet1.add(new Point(5,2));	manualSet1.add(new Point(6,3));
		assertEquals(true, manualSet1.equals(methodSet1));
		
		// No pieces in the way, corner
		Board board2 = new Board();
		Piece knightPiece2 = new Piece(false, PieceType.KNIGHT, board2, new Point(7,0));
		Set<Point> methodSet2 = knightPiece2.tileMovement();
		Set<Point> manualSet2 = new HashSet<Point>();
		manualSet2.add(new Point(5,1));	manualSet2.add(new Point(6,2));
		assertEquals(true, manualSet2.equals(methodSet2));
		
		// Pieces in the way, corner
		Board board3 = new Board();
		Piece knightPiece3 = new Piece(true, PieceType.KNIGHT, board3, new Point(4,4));
		Piece whiteBishop = new Piece(true, PieceType.BISHOP, board3, new Point(3,2));
		Piece blackPawn = new Piece(false, PieceType.PAWN, board3, new Point(5,2));
		Set<Point> methodSet3 = knightPiece3.tileMovement();
		Set<Point> manualSet3 = new HashSet<Point>();
										manualSet3.add(new Point(2,3));
		manualSet3.add(new Point(2,5));	manualSet3.add(new Point(3,6));
		manualSet3.add(new Point(5,6));	manualSet3.add(new Point(6,5));	
		manualSet3.add(new Point(5,2));	manualSet3.add(new Point(6,3));
		assertEquals(true, manualSet3.equals(methodSet3));
	}

	void testBishopMovement() {
		// No pieces in the way, center
		Board board1 = new Board();
		Piece bishopPiece1 = new Piece(true, PieceType.BISHOP, board1, new Point(4,4));
		Set<Point> methodSet1 = bishopPiece1.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(0,0));	manualSet1.add(new Point(1,1));
		manualSet1.add(new Point(2,2));	manualSet1.add(new Point(3,3));
		manualSet1.add(new Point(7,1));	manualSet1.add(new Point(6,2));	
		manualSet1.add(new Point(5,3));	manualSet1.add(new Point(3,5));
		manualSet1.add(new Point(2,6));	manualSet1.add(new Point(1,7));
		manualSet1.add(new Point(5,5));	manualSet1.add(new Point(6,6));
		manualSet1.add(new Point(7,7));
		assertEquals(true, manualSet1.equals(methodSet1));
		
		// No pieces in the way, corner
		Board board2 = new Board();
		Piece bishopPiece2 = new Piece(false, PieceType.BISHOP, board2, new Point(0,7));
		Set<Point> methodSet2 = bishopPiece2.tileMovement();
		Set<Point> manualSet2 = new HashSet<Point>();
		manualSet2.add(new Point(7,0));	manualSet2.add(new Point(6,1));
		manualSet2.add(new Point(5,2));	manualSet2.add(new Point(4,3));
		manualSet2.add(new Point(3,4));	manualSet2.add(new Point(2,5));
		manualSet2.add(new Point(1,6));	
		assertEquals(true, manualSet2.equals(methodSet2));
		
		// Pieces in the way, center
		Board board3 = new Board();
		Piece bishopPiece3 = new Piece(true, PieceType.BISHOP, board3, new Point(4,4));
		Piece blackPawn = new Piece(false, PieceType.PAWN, board3, new Point(2,2));
		Piece whiteKnight = new Piece(true, PieceType.KNIGHT, board3, new Point(5,5));
		Set<Point> methodSet3 = bishopPiece3.tileMovement();
		Set<Point> manualSet3 = new HashSet<Point>();
		manualSet3.add(new Point(2,2));	manualSet3.add(new Point(3,3));
		manualSet3.add(new Point(7,1));	manualSet3.add(new Point(6,2));	
		manualSet3.add(new Point(5,3));	manualSet3.add(new Point(3,5));
		manualSet3.add(new Point(2,6));	manualSet3.add(new Point(1,7));
		assertEquals(true, manualSet3.equals(methodSet3));
	}

	void testKingMovement() {
		// No pieces in the way, center
		Board board1 = new Board();
		Piece whiteKing1 = new Piece(true, PieceType.KING, board1, new Point(4,4));
		Set<Point> methodSet1 = whiteKing1.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(3,3)); manualSet1.add(new Point(4,3));
		manualSet1.add(new Point(5,3)); manualSet1.add(new Point(3,4));
		manualSet1.add(new Point(5,4)); manualSet1.add(new Point(3,5));
		manualSet1.add(new Point(4,5)); manualSet1.add(new Point(5,5));
		assertEquals(true, manualSet1.equals(methodSet1));
		
		// No pieces in the way, corner
		Board board2 = new Board();
		Piece whiteKing2 = new Piece(true, PieceType.KING, board2, new Point(0,0));
		Set<Point> methodSet2 = whiteKing2.tileMovement();
		Set<Point> manualSet2 = new HashSet<Point>();
		manualSet2.add(new Point(1,0));
		manualSet2.add(new Point(1,1)); manualSet2.add(new Point(0,1));
		assertEquals(true, manualSet2.equals(methodSet2));
		
		// Walking into check, center
		Board board3 = new Board();
		Piece whiteKing3 = new Piece(true, PieceType.KING, board3, new Point(4,4));
		Piece blackRook3 = new Piece(false, PieceType.ROOK, board3, new Point(0,0));
		board3.placeNewPiece(whiteKing3);
		board3.placeNewPiece(blackRook3);
		board3.placePiece(blackRook3, new Point(3,0));
		Set<Point> methodSet3 = whiteKing3.tileMovement();
		Set<Point> manualSet3 = new HashSet<Point>();
		manualSet3.add(new Point(4,3));
		manualSet3.add(new Point(5,3)); 
		manualSet3.add(new Point(5,4)); 
		manualSet3.add(new Point(4,5)); manualSet3.add(new Point(5,5));
		assertEquals(true, manualSet3.equals(methodSet3));
		
		// Opposition
		Board board4 = new Board();
		Piece whiteKing4 = new Piece(true, PieceType.KING, board4, new Point(2,4));
		Piece blackKing4 = new Piece(false, PieceType.KING, board4, new Point(2,1));
		board4.placeNewPiece(whiteKing4);
		board4.placeNewPiece(blackKing4);
		board4.placePiece(blackKing4, new Point(2,2));
		Set<Point> methodSet4 = whiteKing4.tileMovement();
		Set<Point> manualSet4 = new HashSet<Point>();
		manualSet4.add(new Point(1,4)); manualSet4.add(new Point(3,4));
		manualSet4.add(new Point(1,5)); manualSet4.add(new Point(2,5));
		manualSet4.add(new Point(3,5));
		assertEquals(true, manualSet4.equals(methodSet4));
	}
	
	void testPawnMovement() {
		// Set up board, get necessary pawns
		Board board1 = new Board(true);
		Piece ePawnWhite = board1.getTile(4,6).getPiece();
		Piece dPawnBlack = board1.getTile(3,1).getPiece();
		Piece fPawnBlack = board1.getTile(5,1).getPiece();
		
		// Initial move (1 or 2 spaces ahead)
		Set<Point> methodSet1 = ePawnWhite.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(4,5));	manualSet1.add(new Point(4,4));
		assertEquals(true, methodSet1.equals(manualSet1));
		
		// Capturing
		board1.placePiece(ePawnWhite, new Point(4,4));
		board1.placePiece(dPawnBlack, new Point(3,3));
		Set<Point> methodSet2 = ePawnWhite.tileMovement();
		Set<Point> manualSet2 = new HashSet<Point>();
		manualSet2.add(new Point(3,3)); manualSet2.add(new Point(4,3));
		assertEquals(true, methodSet2.equals(manualSet2));
		
		// En passant
		board1.placePiece(ePawnWhite, new Point(4,3));
		board1.placePiece(fPawnBlack, new Point(5,3));
		Set<Point> methodSet3 = ePawnWhite.tileMovement();
		Set<Point> manualSet3 = new HashSet<Point>();
		manualSet3.add(new Point(5,2)); manualSet3.add(new Point(4,2));
		assertEquals(true, methodSet3.equals(manualSet3));
	}
	
	@Test
	void testPlacePiece() {
		// Test placing a piece in the way of another piece
		Board board1 = new Board();
		Piece whiteRook = new Piece(true, PieceType.ROOK, board1, new Point(3,3));
		Piece whiteKnight = new Piece(true, PieceType.KNIGHT, board1, new Point(5,1));
		board1.placePiece(whiteKnight, new Point(6,3));
		Set<Point> methodSet1 = whiteRook.tileMovement();
		Set<Point> manualSet1 = new HashSet<Point>();
		manualSet1.add(new Point(3,0));	manualSet1.add(new Point(3,1));
		manualSet1.add(new Point(3,2));	manualSet1.add(new Point(3,4));
		manualSet1.add(new Point(3,5));	manualSet1.add(new Point(3,6));	
		manualSet1.add(new Point(3,7));	manualSet1.add(new Point(0,3));
		manualSet1.add(new Point(1,3));	manualSet1.add(new Point(2,3));
		manualSet1.add(new Point(4,3));	manualSet1.add(new Point(5,3));
		assertEquals(true, manualSet1.equals(methodSet1));
		
		// Test placing a piece on another piece
		Piece blackBishop = new Piece(false, PieceType.BISHOP, board1, new Point(4,5));
		board1.placePiece(blackBishop, new Point(6,3));
		methodSet1 = whiteRook.tileMovement();
		manualSet1.add(new Point(6,3));
		assertEquals(true, manualSet1.equals(methodSet1));
	}
	
	@Test
	void testMovementMaps() {
		// Test piece sets and placeNewPiece
		Board board1 = new Board();
		Piece whiteRook = new Piece(true, PieceType.ROOK, board1, new Point(0,7));
		Piece whiteKnight = new Piece(true, PieceType.KNIGHT, board1, new Point(1,7));
		board1.placeNewPiece(whiteRook);
		board1.placeNewPiece(whiteKnight);
		board1.placePiece(whiteRook, new Point(3,3));
		board1.placePiece(whiteKnight, new Point(6,3));
		Set<Piece> whitePieces1 = board1.getPieces(true);
		assertEquals(true, whitePieces1.contains(whiteRook));
		assertEquals(true, whitePieces1.contains(whiteKnight));
		
		// Test the movement map maps the correct pieces and sets
		Map<Piece, Set<Point>> movementMap1 = board1.getMoves(true);
		assertEquals(whiteRook.tileMovement(), movementMap1.get(whiteRook));
		assertEquals(whiteKnight.tileMovement(), movementMap1.get(whiteKnight));
	}
}
