import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

/** Object that processes a mouse click on a tile. */
public class MouseEvents extends MouseInputAdapter {
	
	public void mouseClicked(MouseEvent e) {
		Object ob = e.getSource();
		if (ob instanceof Tile) {
			Tile tile = (Tile) ob;	
			
			// Print tile click to console
			Point xy = tile.getLocation();
			int x = (int) xy.getX();	int y = (int) xy.getY();
			System.out.printf("Clicked on tile: (%d,%d)%n",x,y);
			
			// If no tile has been selected
				// Change tile's color to dark green
				// Change available tiles colors to light green
				// Switch flag to indicate a tile has been selected
			
			// If a tile has been selected
				// If that tile is an available tile
					// placePiece
				// If that tile is not an available tile
					// Tell the user that the move is invalid
				// Change the colors back to default
				// Switch flag to indicate a tile is not selected anymore
		}
	}
	
}
