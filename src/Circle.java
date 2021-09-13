import java.awt.Point;
import java.util.ArrayList;

/**
 * Circle class
 * @author Sunny Jiao
 */
public class Circle extends Ellipse {
	
	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Circle, Points will form a square, which will act as
	 * the ends of each axis.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		Point mid = new Point((vertices.get(0).x + vertices.get(1).x) / 2,
				(vertices.get(0).y + vertices.get(1).y) / 2);
		
		int dx = mid.y - vertices.get(0).y;
		int dy = mid.x - vertices.get(0).x;
		if(vertices.size() == 2) {			
			return new Point(mid.x - dx, mid.y + dy);
		}
		else {
			return new Point(mid.x + dx, mid.y - dy);
		}
	}
}
