
/** An instance is a chess player. */
public class Player {
	private boolean isPlayerOne;	// "this player is the first player"
	private int turnCount;	// the turn this player is on
	
	/** Constructor: creates a player object of specified turn order. */
	public Player(boolean isPlayerOne) {
		this.isPlayerOne = isPlayerOne;
		turnCount = 1;
	}
	
	/** Return this player's turn count. */
	public int getTurnCount() {
		return turnCount;
	}
	
	/** "this player moves the white pieces" */
	public boolean isWhite() {
		return isPlayerOne;
	}
	
	/** Add 1 to the turn count. */
	public void nextTurn() {
		turnCount++;
	}
}
