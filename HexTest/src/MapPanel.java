import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {

	GameModel gameModel;
	HexCell[] cells;

	Point mousePos;
	Point scrollPos;
	double scale = 1.0;
	AffineTransform at;

	public MapPanel(GameModel gameModel) {
		this.gameModel = gameModel;
		MapPanelMouseHandler mouseHandler = new MapPanelMouseHandler(this);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		this.cells = gameModel.getCells();
	}

	public void clickAt(Point point) {
		HexCell[] cells = gameModel.getCells();
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].contains(point)) {
				cells[i].toggleSelection();
				break;
			}
		}
		repaint();
		System.out.println("click at " + point.x + ", " + point.y);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(Color.blue);
		g2.draw(getBounds());

		AffineTransform saveAt = g2.getTransform();
		at = AffineTransform.getTranslateInstance(scrollPos.x, scrollPos.y);
		at.concatenate(AffineTransform.getScaleInstance(scale, scale));
		g2.transform(at);
		g2.setPaint(Color.black);
		Rectangle panelBounds = getBounds();
		panelBounds.grow(30, 30);
		for (int i = 0; i < cells.length; i++) {
			Point transformedCenter = new Point();
			at.transform(cells[i].center, transformedCenter);
			if (panelBounds.contains(transformedCenter)) {
				cells[i].draw(g2);
			}
		}

		g2.transform(saveAt);
		Point scrolledMousePos = new Point();
		// Point scaledMousePos = new Point();
		// scrolledMousePos.x = mousePos.x- scrollPos.x;
		// scrolledMousePos.y = mousePos.y - scrollPos.y;
		at.transform(mousePos, scrolledMousePos);
		Rectangle mouseShower = new Rectangle(scrolledMousePos, new Dimension(
				10, 10));
		System.out.println(mousePos.x + ", " + mousePos.y);
		g2.draw(mouseShower);
	}

	public Point getMousePos() {
		return mousePos;
	}

	public void setMousePos(Point mousePos) {
		this.mousePos = mousePos;
	}

	public Point getScrollPos() {
		return scrollPos;
	}

	public void setScrollPos(Point scrollPos) {
		this.scrollPos = scrollPos;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

}
