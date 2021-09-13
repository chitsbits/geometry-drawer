
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Superclass for all implemented quadrilateral shapes
 * @author Sunny Jiao
 */
public abstract class Quadrilateral extends Shape {
	
	private double a;	// Top side length
	private double b;	// Bottom side length
	private double h;	// Length between top and bottom

	/**
	 * Constructor - Subclasses will call this, with their shape's specific
	 * number of points that can be freely drawn. All quadrilaterals have 4
	 * vertices and will be called in this superclass.
	 * @param numFreelyDrawnPoints Number of points that can be freely drawn
	 */
	public Quadrilateral(int numFreelyDrawnPoints) {
		super(numFreelyDrawnPoints, 4);
	}
	
	/**
	 * Calculate and set the side lengths a, b, and height h
	 */
	@Override
	public void setAttributes() {
		ArrayList<Point> vertices = getVertices();
		a = edgeLength(vertices.get(0),vertices.get(1));
		b = edgeLength(vertices.get(2),vertices.get(3));
		h = Math.sqrt(Math.pow(edgeLength(vertices.get(0),vertices.get(3)),2) - 
				Math.pow(Math.abs(a - b),2));
	}
	
	/**
	 * Calculate the perimeter by summing side lengths
	 * @return Perimeter as a double
	 */
	@Override
	public double calculatePerimeter() {
		double perimeter = 0;
		ArrayList<Point> vertices = getVertices();
		for(int i = 0; i < vertices.size()-1; i++) {
			perimeter += edgeLength(vertices.get(i+1), vertices.get(i));
		}
		perimeter += edgeLength(vertices.get(vertices.size()-1), vertices.get(0));
		return perimeter;
	}
	
	/**
	 * Calculate the area using the shared area formula
	 * @return Area as a double
	 */
	@Override
	public double calculateArea() {
		return (a + b) * h * 0.5;
	}
	
	/**
	 * Get string representation of the shape. Overrides shape to include
	 * quadrilateral specific attributes (i.e. side lengths, height)
	 * @return String with the shape's name, vertex points, area, perimeter, and side lengths
	 */
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		return super.toString() + "Lengths:\nA: " + df.format(a)
				+ "\nB: " + df.format(b) + "\nH: " + df.format(h);
	}
}
