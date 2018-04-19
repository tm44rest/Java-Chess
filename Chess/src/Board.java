import javax.swing.Box;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
/** An instance is a chess board. */
public class Board extends Box {

	private Tile[][] tiles;	// tile grid
	
	/** Constructor: creates an horizontal box of vertical boxes, each
	 * 	containing 8 tiles with no pieces on them. */
	public Board() {
		super(BoxLayout.Y_AXIS);
		
		// Create the tile grid
		tiles = new Tile[8][8];
		
		// inv: rows 0..i-1 have been set up
		for (int i = 0; i != 8; i++) {
			// Create the ith row of tiles
			Box row = new Box(BoxLayout.X_AXIS);
			
			// inv: tiles 0..i-1 have been set up
			for (int j = 0; j != 8; j++) {
				// Create the tiles for each row
				tiles[i][j] = new Tile(i,j);
				row.add(tiles[i][j]);
				// TODO: add a mouse event listener to the tile
			}
			
			this.add(row);	// add the row of tiles to this board
		}
		
	}
	
	/** Return the tile on this board at (x,y).
	 * 	Precondition: (x,y) is on the board. */
	public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
		return tiles[x][y];
	}
}
