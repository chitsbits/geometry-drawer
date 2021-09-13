
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Superclass for all triangle variants
 * @author Sunny Jiao
 */
public abstract class Triangle extends Shape {
	
	private double a;	// First side
	private double b;	// Second side
	private double c;	// Third side

	/**
	 * Constructor - All triangle variants will call this
	 * with their specific number of freely drawn points
	 * @param numFreelyDrawnPoints Number of freely drawn points
	 */
	public Triangle(int numFreelyDrawnPoints) {
		super(numFreelyDrawnPoints, 3);
	}
	
	/**
	 * Calculate and set side lengths
	 */
	@Override
	public void setAttributes() {
		ArrayList<Point> vertices = getVertices();
		a = edgeLength(vertices.get(0), vertices.get(1));
		b = edgeLength(vertices.get(1),vertices.get(2));
		c = edgeLength(vertices.get(2), vertices.get(0));
	}
	
	/**
	 * Calculate the perimeter by summing side lengths
	 * @return Perimter as a double
	 */
	@Override
	public double calculatePerimeter() {
		return a + b + c;
	}
	
	/**
	 * Calculate the area using Heron's formula
	 * @return Area as a double
	 */
	@Override
	public double calculateArea() {		
		double s = getPerimeter() / 2;
		return Math.sqrt(s * (s - a) * (s - b) * (s - c));
	}
	
	/**
	 * Get string representation of the shape. Overrides shape to include
	 * triangle specific attributes (i.e. side lengths)
	 * @return String with the shape's name, vertex points, area, perimeter, and side lengths
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return super.toString() + "Lengths:\nA: " + df.format(a)
				+ "\nB: " + df.format(b) + "\nC: " + df.format(c);
	}
}
