
/** An enumeration of all possible chess piece types. */
public enum PieceType {
	PAWN	("p", "/pawn.png"),
	KNIGHT	("n", "/knight.png"),
	BISHOP	("B", "/bishop.png"),
	ROOK	("r", "/rook.png"),
	QUEEN	("q", "/queen.png"),
	KING	("k", "/king.png");
	
	private final String abbreviation;	// its chess abbreviation
	private final String picture;	// location of its picture
	
	PieceType(String abbreviation, String picture) {
		this.abbreviation = abbreviation;
		this.picture = picture;
	}
	
	public String getAbrreviation() {
		return abbreviation;
	}
}
