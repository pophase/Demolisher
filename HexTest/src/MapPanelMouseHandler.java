import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MapPanelMouseHandler implements MouseListener,
		MouseMotionListener, MouseWheelListener {

	MapPanel mapPanel;
	Point mouseDragStart = new Point();
	Point mousePos = new Point();
	Point scrollPos = new Point();

	public MapPanelMouseHandler(MapPanel mapPanel) {
		this.mapPanel = mapPanel;
		mapPanel.setMousePos(mousePos);
		mapPanel.setScrollPos(scrollPos);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDragStart = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos = e.getPoint();
		scrollPos.x += mousePos.x - mouseDragStart.x;
		scrollPos.y += mousePos.y - mouseDragStart.y;
		mouseDragStart.x = mousePos.x;
		mouseDragStart.y = mousePos.y;
		mapPanel.setMousePos(mousePos);
		mapPanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point();
		double scale = mapPanel.getScale();
		p.x = (int) ((e.getX() - scrollPos.x) / scale);
		p.y = (int) ((e.getY() - scrollPos.y) / scale);

		mousePos = p;
		mapPanel.setMousePos(mousePos);
		mapPanel.repaint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Point p = new Point();
		double scale = mapPanel.getScale();
		p.x = (int) ((e.getX() - scrollPos.x) / scale);
		p.y = (int) ((e.getY() - scrollPos.y) / scale);
		scrollPos.x += p.x * scale;
		scrollPos.y += p.y * scale;
		if (e.getWheelRotation() > 0) {
			scale *= 0.9;
		} else {
			scale *= 1.1;
		}
		System.out.println("scale: " + scale);
		scrollPos.x -= p.x * scale;
		scrollPos.y -= p.y * scale;
		mapPanel.setScale(scale);
		mapPanel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mapPanel.clickAt(e.getPoint());
	}
}