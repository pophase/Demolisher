import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class HexCell {
	String id;
	Point2D.Double center;
	Shape shape;
	boolean showCenter;
	double start;
	String[] neighbors;
	boolean isSelected = false;
	GradientPaint cellPaint;

	public HexCell(String id, Point2D.Double center, Shape shape,
			boolean showCenter, double start, String[] neighbors) {
		this.id = id;
		this.center = center;
		this.shape = shape;
		this.showCenter = showCenter;
		this.start = start;
		this.neighbors = neighbors;
		cellPaint = new GradientPaint(0f, 0f, Color.black, 0f, 0.5f,
				Color.white);
	}

	public void draw(Graphics2D g2) {
		if (showCenter) {
			g2.setPaint(Color.red);
			g2.fill(new Ellipse2D.Double(center.x - 1.5, center.y - 1.5, 4, 4));
			g2.drawString(id, (float) (center.x + 3), (float) (center.y + 3));
			g2.setPaint(Color.black);
		}
		if (isSelected) {
			g2.setPaint(cellPaint);
			g2.fill(shape);
			g2.setColor(Color.black);
		}
		g2.draw(shape);
		if (isSelected) {
			// Show that we know who our neighbors are and where they live.
			Font font = g2.getFont();
			FontRenderContext frc = g2.getFontRenderContext();
			LineMetrics lm = font.getLineMetrics("0", frc);
			float sh = lm.getAscent() + lm.getDescent();
			Rectangle r = shape.getBounds();
			int R = Math.max(r.width, r.height) / 2;
			double thetaInc = 2 * Math.PI / 6;
			double theta = start;
			double lastX = 0, lastY = 0;
			for (int i = 0; i <= neighbors.length; i++) {
				double x = center.x + R * Math.cos(theta);
				double y = center.y + R * Math.sin(theta);
				if (i > 0 && neighbors[i - 1] != null) {
					float midx = (float) (x - (x - lastX) / 2);
					float midy = (float) (y - (y - lastY) / 2);
					double phi = Math.atan2(midy - center.y, midx - center.x);
					String s = neighbors[i - 1];
					double sw = font.getStringBounds(s, frc).getWidth();
					double diag = Math.sqrt(sw * sw + sh * sh) / 2;
					float sx = (float) (midx - diag * Math.cos(phi) - sw / 2);
					float sy = (float) (midy - diag * Math.sin(phi))
							+ lm.getDescent();
					g2.drawString(s, sx, sy);
				}
				lastX = x;
				lastY = y;
				theta += thetaInc;
			}
		}
	}

	public void setShowCenter(boolean show) {
		showCenter = show;
	}

	public void toggleSelection() {
		isSelected = !isSelected;
	}

	public boolean contains(Point p) {
		return shape.contains(p);
	}

	public String toString() {
		return "HexCell[id:" + id + ", neighbors:" + Arrays.toString(neighbors)
				+ "]";
	}
}