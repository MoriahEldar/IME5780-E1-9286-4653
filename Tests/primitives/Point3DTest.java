package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Point3D class
 * @author Moriah and Shahar
 */
public class Point3DTest {
    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any 2 point that don't equal
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(-3, 0, 1);
        assertEquals("Point3D: subtract() wrong", new Vector(4, 1, 0), p1.subtract(p2));
        // =============== Boundary Values Tests ==================
        // T2, 2 points that equal
        p2 = new Point3D(1, 1, 1);
        try
        {
            p1.subtract(p2);
            fail("Point3D: subtract() does not throw an error when the zero vector is made");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any point and vector
        Point3D p1 = new Point3D(1, 1, 1);
        Vector v1 = new Vector(-3, 0, 1);
        assertEquals("Point3D: add() wrong", new Point3D(-2, 1, 2), p1.add(v1));
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}
     */
    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any 2 points
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(3, 3, -1);
        assertTrue("Point3D: distanceSquared() wrong", isZero(12 - p1.distanceSquared(p2)));
    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}
     */
    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any 2 points
        Point3D p1 = new Point3D(1, 1, 1);
        Point3D p2 = new Point3D(3, 3, -1);
        assertTrue("Point3D: distance() wrong", isZero(Math.sqrt(12) - p1.distance(p2)));

    }
}