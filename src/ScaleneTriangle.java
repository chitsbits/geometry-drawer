import java.awt.Point;

/**
 * Scalene Triangle - Arbitrary side lengths
 * @author Sunny Jiao
 */
public class ScaleneTriangle extends Triangle {
	
	/**
	 * Constructor - Calls superconstructor with 3 as
	 * the number of freely drawn points
	 */
	public ScaleneTriangle() {
		super(3);
	}

	/**
	 * Find the appropriate vertex to add when creating a shape,
	 * relative to the cursor and in accordance with geometric rules.
	 * Once the shape exceeds numFreelyDrawnPoints vertices, this
	 * method will be called to find the appropriate vertex.
	 * For ScaleneTriangle, all points are unrestricted.
	 * @param cursor Point with the coordinates of the cursor
	 * @return A vertex that conforms to the geometric rules of the shape
	 */
	@Override
	public Point getRestrictedPoint(Point cursor) {
		return cursor;  // All points in a scalene triangle are unrestricted, thus cursor is returned
	}
}
