package gui;
import hexagon.HexCell;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import core.GameController;
import core.GameModel;

/**
 * @author hypno
 * @date 19.09.2011
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel {

	GameModel gameModel;
	GameController gameController;
	HexCell[] cells;

	Point mousePos = new Point(0, 0);
	Point scrollPos = new Point(0, 0);
	double scale = 1.0;
	AffineTransform mapAt;

	public MapPanel(GameModel gameModel, GameController gameController) {
		MapPanelMouseHandler mouseHandler = new MapPanelMouseHandler(this);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.addMouseWheelListener(mouseHandler);
		this.gameModel = gameModel;
		this.gameController = gameController;
		this.cells = gameModel.getHexCellModel().getCells();
	}

	/**
	 * Transformiert Bildschirmkoordinaten zu Kartenkoordinaten
	 * 
	 * @param source
	 * @return
	 */
	public Point getPointOnCellMap(Point source) {
		Point p = new Point();
		p.x = (int) ((source.getX() - scrollPos.x) / scale);
		p.y = (int) ((source.getY() - scrollPos.y) / scale);
		return p;
	}

	/**
	 * Aktualisiert die Transformation, die zur Darstellung der Karte verwendet
	 * wird
	 */
	private void updateAffineTransform() {
		mapAt = AffineTransform.getTranslateInstance(scrollPos.x, scrollPos.y);
		mapAt.concatenate(AffineTransform.getScaleInstance(scale, scale));
	}

	public void clickAt(Point point) {
		// process clicks for hex cells
		point = getPointOnCellMap(point);
		for (int i = 0; i < cells.length; i++) {
			if (cells[i].contains(point)) {
				gameController.commandCellClicked(i);
				break;
			}
		}
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		AffineTransform saveAt = g2.getTransform();
		if (mapAt == null)
			updateAffineTransform();
		g2.transform(mapAt);
		g2.setPaint(Color.black);
		Rectangle panelBounds = getBounds();
		panelBounds.grow(30, 30);
		for (int i = 0; i < cells.length; i++) {
			Point transformedCenter = new Point();
			mapAt.transform(cells[i].getCenter(), transformedCenter);
			if (panelBounds.contains(transformedCenter)) {
				cells[i].draw(g2);
			}
		}

		Point scrolledMousePos = getPointOnCellMap(mousePos);
		Rectangle mouseShower = new Rectangle(scrolledMousePos, new Dimension(
				10, 10));
		g2.draw(mouseShower);

		g2.setTransform(saveAt);
		g2.drawString("mouse: " + mousePos.x + ", " + mousePos.y, 10, 20);
		g2.drawString("scroll: " + scrollPos.x + ", " + scrollPos.y, 10, 40);
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
		updateAffineTransform();
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		updateAffineTransform();
	}

}
