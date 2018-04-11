import javax.swing.JFrame;

@SuppressWarnings("serial")
/**	An instance is a chess window. */
public class Window extends JFrame {
	private static final String windowTitle = "Chess";	// Title of the window
	
	/**	Constructor: the main window of the chess game. */
	public Window() {
		super(windowTitle);
		
		// Make window visible
		pack();
		setLocationRelativeTo(null);	// center the window on the screen
		setVisible(true);
	}
	
	/** Start the GUI */
	public static void main (String args[]) {
		Window mainWindow = new Window();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
