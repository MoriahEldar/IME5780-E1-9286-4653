package geometries;

import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Plane class
 * @author Moriah and Shahar
 */
public class PlaneTest {
    /**
     * A test plane
     */
    Plane plane = new Plane(Point3D.ZERO, new Point3D(1, 0, 0), new Point3D(0, 1, 0));

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // T1, the 3 points are on the same line
        try {
            Plane plane1 = new Plane(Point3D.ZERO, new Point3D(1, 0, 0), new Point3D(-1, 0, 0));
            fail("Plane: constructor(primitives.Point3D, primitives.Point3D, primitives.Point3D) has no ERROR for 3 points on the same line");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link geometries.Plane#isOnPlane(primitives.Point3D)}
     */
    @Test
    public void testIsOnPlane() {
        // ============ Equivalence Partitions Tests ==============
        // T1, a point that is on the plane
        assertTrue("Plane: isOnPlane() wrong when point is on the plane", plane.isOnPlane(new Point3D(1, 1, 0)));
        // T2, a point that is not on the plane
        assertFalse("Plane: isOnPlane() wrong when point is not on the plane", plane.isOnPlane(new Point3D(1, 1, 1)));
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any point on the plane
        assertEquals("Plane: getNormal() wrong", new Vector(0, 0, 1), plane.getNormal(Point3D.ZERO));
    }
}