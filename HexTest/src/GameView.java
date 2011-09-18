import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameView extends JFrame {

	private static final String PROJECT_NAME = "Hexwar";
	private static final int CONTROL_PANEL_WIDTH = 200;
	private GameModel gameModel;

	public GameView(GameModel gameModel) {
		this.gameModel = gameModel;

		// initialisiere die Fenster
		this.setTitle(PROJECT_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panels für die Karte und die Steuerung
		MapPanel mapPanel = new MapPanel(gameModel);
		ControlPanel controlPanel = new ControlPanel();

		this.add(mapPanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.LINE_END);
		this.setVisible(true);
		this.pack();
		this.addComponentListener(resizeMonitor);
	}

	public Dimension getPreferredSize() {
		return new Dimension(1024, 768);
	}

	private ComponentListener resizeMonitor = new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			System.out.println("duh!");
		}
	};

	public static void main(String[] args) {
		GameModel gameModel = new GameModel();
		GameView gameView = new GameView(gameModel);
	}
}
