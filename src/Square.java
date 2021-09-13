import java.awt.Point;
import java.util.ArrayList;

/**
 * Square - 4 equal sides, all right angles.
 * @author Sunny Jiao
 */
public class Square extends Quadrilateral {
	
	/**
	 * Calls superconstructor with numFreelyDrawnPoints as 2
	 */
	public Square() {
		super(2);
	}
	
	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For Square, points will form 4 equal sides with 90 degree angles.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		ArrayList<Point> vertices = getVertices();
		int dx = vertices.get(1).y - vertices.get(0).y;
		int dy = vertices.get(1).x - vertices.get(0).x;
		// Find 3rd point
		if(vertices.size() == 2) {
			// Place horizontally
			if(Math.abs(dy) > Math.abs(dx)) {
				if(cursor.y > vertices.get(1).y){
					return new Point(vertices.get(1).x - dx, vertices.get(1).y + dy);
				}
				else {
					return new Point(vertices.get(1).x + dx, vertices.get(1).y - dy);
				}
			}
			// Place vertically
			else {
				if(cursor.x > vertices.get(1).x){
					return new Point(vertices.get(1).x - dx, vertices.get(1).y + dy);
				}
				else {
					return new Point(vertices.get(1).x + dx, vertices.get(1).y - dy);
				}
			}
		}
		// Find last point
		else {
			dy = vertices.get(0).y - vertices.get(1).y;
			dx = vertices.get(0).x - vertices.get(1).x;
			return new Point(vertices.get(2).x + dx, vertices.get(2).y + dy);
		}
	}
}
