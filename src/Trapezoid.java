
import java.awt.Point;
import java.util.ArrayList;

/**
 * Trapezoid - Quadrilateral with one pair of parallel sides
 * @author Sunny Jiao
 */
public class Trapezoid extends Quadrilateral {
	
	/**
	 * Calls superconstructor with 3 as the number of freely drawn points
	 */
	public Trapezoid() {
		super(3);
	}
	
	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Trapezoid, points will form 1 pair of parallel sides.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		double m = slope(vertices.get(1), vertices.get(0));
		// If dy < dx, draw horizontally
		if(Math.abs(vertices.get(1).y - vertices.get(0).y) < Math.abs(vertices.get(1).x - vertices.get(0).x)){
			int y = (int)(m * (cursor.x - vertices.get(vertices.size()-1).x) + vertices.get(vertices.size()-1).y);		
			return new Point(cursor.x, y);
		}
		// If dy > dx, draw vertically
		else {
			int x = (int)((cursor.y - vertices.get(vertices.size()-1).y) / m + vertices.get(vertices.size()-1).x);
			return new Point(x, cursor.y);
		}
	}
}
