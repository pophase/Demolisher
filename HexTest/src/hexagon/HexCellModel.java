package hexagon;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hypno
 * @date 19.09.2011
 */
public class HexCellModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int hexWidth = 320;
	public int hexHeight = 240;
	public int mapWidth = 1280;
	public int mapHeight = 1024;
	private double R = Math.min(hexWidth, hexHeight) / 8;
	public double MIN_DIST = R / 4;

	HexCell[] cells;

	public HexCellModel() {
		initHexCells(hexWidth, hexHeight, R, new Rectangle(mapWidth, mapHeight));
	}

	public HexCell[] getCells() {
		return cells;
	}

	public void setCells(HexCell[] cells) {
		this.cells = cells;
	}

	private void initHexCells(double w, double h, double R, Rectangle range) {
		Path2D.Double path = getPath(w / 2, h / 2, R);
		Rectangle2D bounds = path.getBounds2D();
		double radius = Math.min(bounds.getWidth(), bounds.getHeight());
		List<Point2D.Double> list = getAllPoints(bounds.getCenterX(),
				bounds.getCenterY(), radius, range);
		cells = new HexCell[list.size()];
		System.out.println("Amount of cells: " + list.size());
		// For HexCell to find the side that starts at zero degrees.
		double theta = 0;
		for (int id = 0; id < list.size(); id++) {
			Point2D.Double p = list.get(id);
			double x = p.x - w / 2;
			double y = p.y - h / 2;
			AffineTransform at = AffineTransform.getTranslateInstance(x, y);
			Shape s = at.createTransformedShape(path);
			String[] adjacentIds = getNeighbors(id, radius, list);
			cells[id] = new HexCell(id, p, s, false, theta, adjacentIds);
		}
	}

	private String[] getNeighbors(int index, double radius,
			List<Point2D.Double> list) {
		// Collect neighbors clockwise starting at zero degrees.
		String[] ids = new String[6];
		double thetaInc = Math.PI / 6;
		Point2D.Double center = list.get(index);
		// Make ellipse larger to include the points we're
		// looking for so we can use the intersects method.
		radius += 1;
		Ellipse2D.Double e = new Ellipse2D.Double(center.x - radius, center.y
				- radius, 2 * radius, 2 * radius);
		for (int i = 0; i < list.size(); i++) {
			if (i == index)
				continue;
			Point2D.Double p = list.get(i);
			if (e.contains(p)) {
				// Get bearing to p.
				double phi = Math.atan2(p.y - center.y, p.x - center.x);
				// Avoid case of -0.0 for negative phi.
				if (phi < 0.0 && phi < -0.0001)
					phi += 2 * Math.PI;
				// Index into array found with thetaInc.
				int j = (int) Math.round(phi / thetaInc);
				j /= 2;
				if (j < 0)
					j += 5;
				if (j < ids.length) {
					ids[j] = String.valueOf(i);
				}
			}
		}
		return ids;
	}

	private List<Point2D.Double> getAllPoints(double cx, double cy,
			double radius, Rectangle range) {
		Point2D.Double center = new Point2D.Double(cx, cy);
		List<Point2D.Double> list = new ArrayList<Point2D.Double>();
		list.add(center);
		Point2D.Double[] points = { new Point2D.Double(cx, cy) };
		List<Point2D.Double> subList = null;
		do {
			List<Point2D.Double> nextPoints = new ArrayList<Point2D.Double>();
			for (int i = 0; i < points.length; i++) {
				subList = getPoints(points[i].x, points[i].y, radius, range,
						center);
				for (int j = 0; j < subList.size(); j++) {
					Point2D.Double p = subList.get(j);
					if (!haveCloseEnoughPoint(p, list)) {
						list.add(p);
						nextPoints.add(p);
					}
				}
			}
			points = nextPoints.toArray(new Point2D.Double[nextPoints.size()]);
		} while (points.length > 0);

		return list;
	}

	private List<Point2D.Double> getPoints(double cx, double cy, double radius,
			Rectangle r, Point2D.Double center) {
		List<Point2D.Double> list = new ArrayList<Point2D.Double>();
		double minDist = center.distance(cx, cy);
		for (int i = 0; i < 6; i++) {
			double theta = i * Math.PI / 3;
			theta += Math.PI / 6;
			double x = cx + radius * Math.cos(theta);
			double y = cy + radius * Math.sin(theta);
			double distance = center.distance(x, y);
			if (r.contains(x, y) && distance > minDist) {
				list.add(new Point2D.Double(x, y));
			}
		}
		return list;
	}

	private boolean haveCloseEnoughPoint(Point2D.Double p,
			List<Point2D.Double> list) {
		for (int i = 0; i < list.size(); i++) {
			Point2D.Double next = list.get(i);
			if (next.distance(p) < MIN_DIST) {
				return true;
			}
		}
		return false;
	}

	private Path2D.Double getPath(double cx, double cy, double R) {
		Path2D.Double path = new Path2D.Double();
		double thetaInc = 2 * Math.PI / 6;
		double theta = thetaInc;
		double x = cx + R * Math.cos(theta);
		double y = cy + R * Math.sin(theta);
		path.moveTo(x, y);
		for (int i = 0; i < 6; i++) {
			theta += thetaInc;
			x = cx + R * Math.cos(theta);
			y = cy + R * Math.sin(theta);
			path.lineTo(x, y);
		}
		return path;
	}

}
