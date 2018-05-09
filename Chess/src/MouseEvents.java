import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.event.MouseInputAdapter;

/** Object that processes a mouse click on a tile. */
public class MouseEvents extends MouseInputAdapter {
	
	private boolean isTileSelected;	// "a tile has been selected by the user"
	// if "isTileSelected", selectedTile and selectedPiece represent the tile
	// and piece selected by the user
	private Tile selectedTile;	
	private Piece selectedPiece;
	private Set<Point> selectedAvailable;
	
	private Board board;	// the board associated with this object
	
	/** Constructor: an instance is an object that processes mouse clicks
	 * 	on the given board. */
	public MouseEvents(Board board) {
		this.board = board;
	}
	
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		if (ob instanceof Tile) {
			Tile clickedTile = (Tile) ob;
			Piece clickedPiece = clickedTile.getPiece();
			
			// Print tile click to console
			Point xy = clickedTile.getLocation();
			int x = (int) xy.getX();	int y = (int) xy.getY();
			System.out.printf("Clicked on tile: (%d,%d)%n",x,y);
			
			// If there is a piece on the tile and no tile has been selected, 
			// select clicked tile
			if (!isTileSelected) {
				// return if the tile does not have a piece of the same color
				// as the player whose turn it is.
				if (clickedPiece == null || clickedPiece.isWhite() 
						!= board.whoseTurn().isWhite()) return;
				
				// Available tiles for the piece to move to
				Set<Point> availablePoints = clickedPiece.tileMovement();
				
				// Change tile's color to the "selected" color
				clickedTile.setColorSelected();
				
				// Select the tile and store the tile+piece
				selectedTile = clickedTile;
				selectedPiece = clickedPiece;
				selectedAvailable = availablePoints;
				isTileSelected = true;
				
				board.repaint();
			}
				
			// If a tile has been selected, attempt to move piece and deselect
			else {
				// If that tile is a valid move, place the selected piece
				if (selectedPiece.tileMovement().contains(xy) && 
						!Piece.putsKingInCheck(
								(int)selectedTile.getLocation().getX(), 
								(int)selectedTile.getLocation().getY(),
								x, y, board))
					board.placePiece(selectedPiece, xy);
				
				// If that tile is not an available tile
				else System.out.println("INVALID MOVE!");
				
				// Change the colors back to default and deselect tile
				selectedTile.setColorDefault();
				isTileSelected = false;
				
				board.repaint();
			}
		}
	}

}
