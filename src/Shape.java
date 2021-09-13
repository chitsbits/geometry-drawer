
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Abstract superclass for all shapes.
 * 
 * All implementable shapes are based on three abstract children of
 * Shape: Quadrilateral, Triangle, and Ellipse (with Ellipse as
 * an exception that is not abstract).
 * 
 * The inheritance structure of the shapes are based on the shape's
 * higher level attributes, i.e. area/perimeter formula, number of
 * vertices, types of lengths (e.g. height, axis, side length),
 * rather than geometric relation with each other; i.e. all
 * quadrilaterals will extend Quadrilateral; Square will not extend
 * Rectangle and Rectangle will not extend Parallelogram. This
 * structure was chosen since the program does not store attributes
 * such as # of parallel sides, angles, etc.
 * @author Sunny Jiao
 */
public abstract class Shape implements Serializable {
	
	private ArrayList<Point> vertices;	
	private int numFreelyDrawnPoints;	// Number of vertices that can be freely drawn, i.e. not geometrically restricted
	private int maxVertices;			// Total number of vertices in the shape
	private double area;
	private double perimeter;
	
	/**
	 * Constructor - When a shape is "added", this constructor
	 * will be called to initialize numFreelyDrawnPoints and
	 * maxVertices, while the vertex list will be empty.
	 * The vertices will be dynamically added to the ArrayList
	 * by the user, via the addPoint() method.
	 * @param numFreelyDrawnPoints	Number of vertices that can be freely drawn by user
	 * @param maxVertices			Total number of vertices in the shape
	 */
	public Shape(int numFreelyDrawnPoints, int maxVertices) {
		this.numFreelyDrawnPoints = numFreelyDrawnPoints;
		this.vertices = new ArrayList<Point>();
		this.maxVertices = maxVertices;
	}
	
	/**
	 * Sets shape specific attributes i.e. side lengths, heights, etc.
	 * Overriden by Quadrilateral, Triangle and Ellipse.
	 */
	public abstract void setAttributes();
	
	/**
	 * Uses shape attributes to calculate perimeter
	 * @return Perimeter of the shape
	 */
	public abstract double calculatePerimeter();
	
	/**
	 * Uses shape attributes to calculate area
	 * @return Area of the shape
	 */
	public abstract double calculateArea();
	
	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * @param p Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	public abstract Point getRestrictedPoint(Point p);
	
	/**
	 * Calculates and sets the perimeter and area after
	 * calculating the shape attributes
	 */
	public void initialize() {
		setAttributes();
		setPerimeter(calculatePerimeter());
		setArea(calculateArea());
	}

	/**
	 * Append a point to the vertex list. If the shape's current number
	 * of vertices is less than the number of points that can be freely
	 * drawn, the cursor point will be added as a vertex. Otherwise, an
	 * appropriate point that conforms to the shape's geometric rules
	 * will be appended.
	 * @param cursor Point with cursor's coordinates
	 * @return True if all vertices have been added
	 */
	public boolean addPoint(Point cursor) {
		// Add first vertex
		if(vertices.size() == 0) {
			vertices.add(new Point(cursor.x, cursor.y));
		}
		else {
			boolean validPoint = true;
			// Freely drawn points
			if(vertices.size() < numFreelyDrawnPoints) {
				// Check that second point isn't too close to first point
				if(vertices.size() == 1) {
					if(edgeLength(vertices.get(0), cursor) < 10) {
						validPoint = false;
						System.out.println("Too close to point!");
					}
				}
				else {
					// Check that point isn't too close to other lines
					Line2D line;
					for(int i = 0; i < vertices.size()-1; i++) {
						line = new Line2D.Double(vertices.get(i), vertices.get(i+1));
						if(line.ptLineDist(cursor) < 5) {
							validPoint = false;
							System.out.println("Too close to edge!");
						}
					}
				}
				if(validPoint) {
					vertices.add(new Point(cursor.x, cursor.y));
					System.out.println("Point #" + vertices.size() + " added.");
				}
			}
			else {
				// Add restricted points
				Point pointToAdd = getRestrictedPoint(cursor);
				Line2D line;
				for(int i = 0; i < vertices.size()-1; i++) {
					line = new Line2D.Double(vertices.get(i), vertices.get(i+1));
					if(line.ptLineDist(pointToAdd) < 5) {
						validPoint = false;
						System.out.println("Too close to edge!");
					}
				}
				if(validPoint) {
					vertices.add(pointToAdd);
					System.out.println("Point #" + vertices.size() + " added.");
				}
			}
		}
		return vertices.size() == maxVertices;
	}
	
	/**
	 * Translate the shape given the change
	 * @param dx Change in x
	 * @param dy Change in y
	 */
	public void translate(int dx, int dy) {
		for(Point p : vertices) {
			p.translate(dx, dy);
		}
	}
	
	/**
	 * Draw the shape by drawing lines between each vertex
	 * @param g The Graphics object to draw to
	 */
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < vertices.size()-1; i++) {
			g.drawLine(vertices.get(i).x, vertices.get(i).y,
					vertices.get(i+1).x, vertices.get(i+1).y);
		}
		g.drawLine(vertices.get(vertices.size()-1).x, vertices.get(vertices.size()-1).y,
				vertices.get(0).x, vertices.get(0).y);
	}
	
	/**
	 * Draws a line from the previous point to the cursor,
	 * along with the line to finish the shape
	 * @param g Graphics object to draw to
	 * @param cursor Point with the cursor's coordinates
	 */
	public void drawGhost(Graphics g, Point cursor) {
		ArrayList<Point> vertices = getVertices();
		
		g.setColor(Color.RED);
		if(vertices.size() >= getNumFreelyDrawnPoints()) {
			// Draw first lines
			for(int i = 0; i < vertices.size()-1; i++) {
				g.drawLine(vertices.get(i).x, vertices.get(i).y,
						vertices.get(i+1).x, vertices.get(i+1).y);
			}
			// Draw ghost line
			Point restrictedPoint = getRestrictedPoint(cursor);
			g.drawLine(vertices.get(vertices.size()-1).x, vertices.get(vertices.size()-1).y,
					restrictedPoint.x, restrictedPoint.y);
			
			g.drawLine(vertices.get(0).x, vertices.get(0).y,
					restrictedPoint.x, restrictedPoint.y);
		}
		else if(vertices.size() != 0){
			for(int i = 0; i < vertices.size()-1; i++) {
				g.drawLine(vertices.get(i).x, vertices.get(i).y,
						vertices.get(i+1).x, vertices.get(i+1).y);
			}
			g.drawLine(vertices.get(vertices.size()-1).x, vertices.get(vertices.size()-1).y,
					cursor.x, cursor.y);
		}
	}
	
	/**
	 * Set the area of the shape
	 * @param a Double to set area to
	 */
	public void setArea(double a) {
		area = a;
	}

	/**
	 * Set the perimeter of the shape
	 * @param p a Double to set perimeter to
	 */
	public void setPerimeter(double p) {
		perimeter = p;
	}
	
	/**
	 * Get the area of the shape
	 * @return Area of the shape
	 */
	public double getArea() {
		return area;
	}

	/**
	 * Get the perimeter of the shape
	 * @return Perimeter of the shape
	 */
	public double getPerimeter() {
		return perimeter;
	}
	
	/**
	 * Get the number of points that can be freely drawn
	 * @return Number of points that can be freely drawn
	 */
	public int getNumFreelyDrawnPoints() {
		return numFreelyDrawnPoints;
	}
	
	/**
	 * Get maximum number of vertices
	 * @return Maximum number of vertices
	 */
	public int getMaxVertices() {
		return maxVertices;
	}
	
	/**
	 * Get ArrayList of Points representing vertices
	 * @return ArrayList of Points representing vertices
	 */
	public ArrayList<Point> getVertices(){
		return vertices;
	}
	
	/**
	 * Set the ArrayList of vertices
	 * @param vertices ArrayList to set from
	 */
	public void setVertices(ArrayList<Point> vertices) {
		this.vertices = vertices;
	}
	
	/**
	 * Get string representation of the shape
	 * @return String which includes the shape's name, vertex points, area, and perimeter
	 */
	@Override
	public String toString() {
		String strVertices = "";
		for(Point p : vertices) {
			strVertices += "(" + p.x + "," + p.y + ") ";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		return this.getClass().getSimpleName() + "\nVertices: " + strVertices +
				"\nArea: " + df.format(this.getArea()) +
				"\nPerimeter: " + df.format(this.getPerimeter()) + "\n";
	}
	
	/**
	 * Returns the length of an edge formed by two points
	 * @param p1 First point
	 * @param p2 Second point
	 * @return The length between p1 and p2
	 */
	public static double edgeLength(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}
	
	/**
	 * Find the slope between two Points
	 * @param p1 First point
	 * @param p2 Second point
	 * @return The slope between p1 and p2
	 */
	public static double slope(Point p1, Point p2) {
		return 1.0 * (p1.y - p2.y) / (p1.x - p2.x);
	}
}
