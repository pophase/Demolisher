package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import core.GameController;
import core.GameModel;

/**
 * @author hypno
 * @date 19.09.2011
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	GameModel gameModel;
	GameController gameController;

	private int width;

	public ControlPanel(GameModel gameModel, GameController gameController,
			int width) {
		super();
		this.gameModel = gameModel;
		this.gameController = gameController;
		this.width = width;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, 800);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Rectangle rect = new Rectangle(0, 0, getBounds().width - 1,
				getBounds().height - 1);
		// rect.grow(-2, -2);
		g2.setPaint(Color.black);
		g2.draw(rect);
	}

}
