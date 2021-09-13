
import java.awt.Point;
import java.util.ArrayList;

/**
 * Rectangle - 2 pairs of parallel sides, angles are all 90 degrees
 * @author Sunny Jiao
 *
 */
public class Rectangle extends Quadrilateral {
	
	/**
	 * Constructor - Calls superconstructor with 2 as
	 * the number of freely drawn points
	 */
	public Rectangle() {
		super(2);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For rectangle, point will form 2 pairs of equal sides at
	 * 90 degree angles of each other.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		if(vertices.size() == 2) {
			
			double m = slope(vertices.get(1), vertices.get(0));
			// If dy > dx, draw horizontally
			if(Math.abs(vertices.get(1).y - vertices.get(0).y) > Math.abs(vertices.get(1).x - vertices.get(0).x)){
				int y = (int)((-1.0 / m) * (cursor.x - vertices.get(vertices.size()-1).x) + vertices.get(vertices.size()-1).y);
				return new Point(cursor.x, y);
			}
			// dx > dy, draw vertically 
			else {
				int x = (int)((cursor.y - vertices.get(vertices.size()-1).y) / (-1.0 / m) + vertices.get(vertices.size()-1).x);
				return new Point(x, cursor.y);
			}
		}
		else {
			int dy = vertices.get(0).y - vertices.get(1).y;
			int dx = vertices.get(0).x - vertices.get(1).x;
			return new Point(vertices.get(2).x + dx, vertices.get(2).y + dy);
		}
	}
}
