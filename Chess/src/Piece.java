
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
}
