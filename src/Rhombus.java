import java.awt.Point;
import java.util.ArrayList;

/**
 * Rhombus - 2 pairs of parallel sides, all equal.
 * @author Sunny Jiao
 */
public class Rhombus extends Quadrilateral {

	/**
	 * Calls superconstructor with numFreelyDrawnPoints as 2
	 */
	public Rhombus() {
		super(2);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Rhombus, points will form 2 pairs of equal, parallel sides.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		if(vertices.size() == 2) {
			// Use similar triangles to trim length
			double r = edgeLength(vertices.get(0), vertices.get(1));
			double ratio = r / edgeLength(vertices.get(1), cursor);
			int dx = cursor.x - vertices.get(1).x;
			int dy = cursor.y - vertices.get(1).y;
			int x = vertices.get(1).x + (int)(dx * ratio);
			int y = vertices.get(1).y + (int)(dy * ratio);
			return new Point(x,y);
		}
		else {
			// Last point
			int dy = vertices.get(0).y - vertices.get(1).y;
			int dx = vertices.get(0).x - vertices.get(1).x;
			return new Point(vertices.get(2).x + dx, vertices.get(2).y + dy);
		}
	}
}
