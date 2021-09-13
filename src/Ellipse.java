
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Ellipse class
 * @author Sunny Jiao
 */
public class Ellipse extends Shape {
	
	private double axis1;	// Length of first axis
	private double axis2;	// Length of second axis
	
	/**
	 * Constructor - The ends of the axis will
	 * be considered as vertices in this program.
	 */
	public Ellipse() {
		super(2,4);
	}
	
	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Ellipse, points will form a parallelogram, which will
	 * represent the ends of each axis.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		
		if(vertices.size() == 2) {	
			double m = 1.0 * (vertices.get(1).y - vertices.get(0).y) / (vertices.get(1).x - vertices.get(0).x);
			
			// If dy > dx, draw horizontally
			if(Math.abs(vertices.get(1).y - vertices.get(0).y) > Math.abs(vertices.get(1).x - vertices.get(0).x)){
				int y = (int)((-1.0 / m) * (cursor.x - mid.x) + mid.y);
				return new Point(cursor.x, y);
			}
			// dx > dy, draw vertically 
			else {
				int x = (int)((cursor.y - mid.y) / (-1.0 / m) + mid.x);
				return new Point(x, cursor.y);
			}
		}
		else {
			// Last point
			int dx = vertices.get(2).x - vertices.get(1).x;
			int dy = vertices.get(2).y - vertices.get(1).y;
			return new Point(vertices.get(0).x - dx, vertices.get(0).y - dy);
		}
	}
	
	/**
	 * Calculate and set the lengths of the axis
	 */
	@Override
	public void setAttributes() {
		ArrayList<Point> vertices = getVertices();
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		axis1 = edgeLength(vertices.get(0),vertices.get(1));
		axis2 = edgeLength(mid, vertices.get(3)) * 2;
	}
	
	/**
	 * Draw the ellipse using a rotated Ellipse2D.
	 * @param g The Graphics object to draw to
	 */
	@Override
	public void draw(Graphics g) {
		ArrayList<Point> vertices = getVertices();
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		double theta = getAngle(vertices.get(0), mid);
		AffineTransform at = new AffineTransform();
		at.rotate(theta, mid.x, mid.y);
		Ellipse2D e = new Ellipse2D.Double(mid.x - axis1/2, mid.y - axis2/2, axis1, axis2);
		((Graphics2D)g).draw(at.createTransformedShape(e));
	}
	
	/**
	 * Draws a ghost of the Ellipse while adding
	 * @param g Graphics object to draw to
	 * @param cursor Point with the cursor's coordinates
	 */
	@Override
	public void drawGhost(Graphics g, Point cursor) {
		ArrayList<Point> vertices = getVertices();
		
		g.setColor(Color.RED);
		if(vertices.size() == 1) {
			// Draw first lin
			g.drawLine(vertices.get(0).x, vertices.get(0).y, cursor.x, cursor.y);
		}
		else if(vertices.size() != 0){
			Point restrictedPoint = getRestrictedPoint(cursor);
			Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
					(vertices.get(0).y + vertices.get(1).y) / 2);
			double theta = getAngle(vertices.get(0), mid);
			AffineTransform at = new AffineTransform();
			at.rotate(theta, mid.x, mid.y);
			double tempAxis1 = edgeLength(vertices.get(0), vertices.get(1));
			double tempAxis2 = edgeLength(mid, restrictedPoint) * 2;
			Ellipse2D e = new Ellipse2D.Double(mid.x - tempAxis1/2, mid.y - tempAxis2/2, tempAxis1, tempAxis2);
			((Graphics2D)g).draw(at.createTransformedShape(e));
		}
	}
	
	/**
	 * Calculate the perimeter of the ellipse
	 * @return Perimeter as a double
	 */
	@Override
	public double calculatePerimeter() {
		return 2 * Math.PI * Math.sqrt((axis1 * axis1 + axis2 * axis2) / 2);
	}

	/**
	 * Calculate an approximation of the area of the ellipse
	 * @return Area as a double
	 */
	@Override
	public double calculateArea() {
		return Math.PI * axis1 * axis2;
	}
	
	/**
	 * Calculate the angle between two points
	 * @param p1 First point
	 * @param p2 Second point
	 * @return Angle between the two points relative to the x-axis in radians
	 */
	private static double getAngle(Point p1, Point p2) {
	    return Math.atan2(p2.y - p1.y, p2.x - p1.x);
	}
	
	/**
	 * Get string representation of the shape. Overrides shape to include
	 * ellipse specific attributes (i.e. axis lengths)
	 * @return String with the shape's name, vertex points, area, perimeter, and side lengths
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return super.toString() + "Lengths:\nAxis 1: " + df.format(axis1)
				+ "\nAxis 2: " + df.format(axis2);
	}
}
