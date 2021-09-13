import java.awt.Point;
import java.util.ArrayList;

/**
 * Equilateral triangle - All sides equal
 * @author Sunny Jiao
 */
public class EquilateralTriangle extends Triangle {

	/**
	 * Constructor - Calls superconstructor with 2 as
	 * the number of freely drawn points
	 */
	public EquilateralTriangle() {
		super(2);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For EquilateralTriangle, points will form 3 equal sides.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		
		double m = slope(vertices.get(1), vertices.get(0));
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		Point temp;
		
		// dy > dx, draw horizontally
		if(Math.abs(vertices.get(1).y - vertices.get(0).y) > Math.abs(vertices.get(1).x - vertices.get(0).x)){
			int y = (int)((-1.0 / m) * (cursor.x - mid.x) + mid.y);
			temp = new Point(cursor.x, y);
		}
		// dx > dy, draw vertically 
		else {
			int x = (int)((cursor.y - mid.y) / (-1.0 / m) + mid.x);
			temp = new Point(x, cursor.y);
		}
		
		// Use similar triangles to trim length
		double height = edgeLength(vertices.get(0), vertices.get(1)) * Math.sqrt(3) / 2.0;
		double ratio = height / edgeLength(mid, temp);
		int dx = temp.x - mid.x;
		int dy = temp.y - mid.y;
		int x2 = mid.x + (int)(dx * ratio);
		int y2 = mid.y + (int)(dy * ratio);
		
		return new Point(x2, y2);
	}
}
