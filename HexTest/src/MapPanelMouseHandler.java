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
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDragStart = e.getPoint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousePos = e.getPoint();
		mapPanel.setMousePos(mousePos);
		scrollPos.x += mousePos.x - mouseDragStart.x;
		scrollPos.y += mousePos.y - mouseDragStart.y;
		mouseDragStart.x = mousePos.x;
		mouseDragStart.y = mousePos.y;
		mapPanel.setScrollPos(scrollPos);
		mapPanel.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mapPanel.setMousePos(e.getPoint());
		mapPanel.repaint(); // temp debug
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		Point p = e.getPoint();
		double scale = mapPanel.getScale();
		p.x = (int) ((p.getX() - scrollPos.x) / scale);
		p.y = (int) ((p.getY() - scrollPos.y) / scale);
		scrollPos.x += p.x * scale;
		scrollPos.y += p.y * scale;
		if (e.getWheelRotation() > 0) {
			scale *= 0.9;
		} else {
			scale *= 1.1;
		}
		scrollPos.x -= p.x * scale;
		scrollPos.y -= p.y * scale;
		mapPanel.setScale(scale);
		mapPanel.setScrollPos(scrollPos);
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