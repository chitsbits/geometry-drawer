
import java.awt.Point;
import java.util.ArrayList;

/**
 * Parallelogram - 2 pairs of parallel sides.
 * @author Sunny Jiao
 */
public class Parallelogram extends Quadrilateral {
	
	/**
	 * Calls superconstructor with numFreelyDrawnPoints as 2
	 */
	public Parallelogram() {
		super(3);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Parallelogram, points will form 2 pairs of equal and
	 * parallel sides.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		int dy = vertices.get(0).y - vertices.get(1).y;
		int dx = vertices.get(0).x - vertices.get(1).x;
		return new Point(vertices.get(2).x + dx, vertices.get(2).y + dy);
	}
}
