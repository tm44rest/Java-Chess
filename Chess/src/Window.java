import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**	An instance is a chess window. */
public class Window extends JFrame {
	private static final String windowTitle = "Chess";	// Title of the window
	
	private Board board;	// Chess board associated with this window
	
	// Chess players
	private Player player1;
	private Player player2;
	
	private JPanel sidebar;	// sidebar with information about the game such as
							// turn #, time, score, and a reset button
	
	/**	Constructor: the main window of the chess game. */
	public Window() {
		super(windowTitle);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// Create the chess board
		board = new Board(true);
		
		// Create the players
		Player player1 = new Player(true);
		Player player2 = new Player(false);
		
		// Create the sidebar
		sidebar = createSidebar();
		
		// Add board and sidebar to the window
		add(board);
		add(sidebar);
		
		// Make window visible
		pack();
		setLocationRelativeTo(null);	// center the window on the screen
		setVisible(true);
	}
	
	/**	Create and return the sidebar. */
	private JPanel createSidebar() {
		// TODO: Implement sidebar
		return new JPanel();
	}
	
	/** Start the GUI */
	public static void main (String args[]) {
		Window mainWindow = new Window();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
