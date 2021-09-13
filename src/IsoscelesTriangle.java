import java.awt.Point;
import java.util.ArrayList;

/**
 * Iscoscles Triangle - 1 pair equal sides
 * @author Sunny Jiao
 *
 */
public class IsoscelesTriangle extends Triangle {

	/**
	 * Constructor - Calls superconstructor with 2 as
	 * the number of freely drawn points
	 */
	public IsoscelesTriangle() {
		super(2);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For IsoscelesTriangle, points will form 1 pair of equal sides.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		
		double m = slope(vertices.get(1), vertices.get(0));
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		
		// dy > dx, draw horizontally
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
}
