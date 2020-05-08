package geometries;

import org.junit.Test;
import primitives.*;

import java.util.List;

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
        } catch (IllegalArgumentException e) {
        }
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

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane thisPlane = new Plane(new Point3D(1, 0, 0), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        assertEquals("Plane: findIntersections() wrong when ray intersects the plane", List.of(new Intersectable.GeoPoint(plane, new Point3D(0, 1, 0))),
                thisPlane.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(0, 1, 1))));

        // TC02: Ray does not intersect the plane
        assertEquals("Plane: findIntersections() wrong when ray doesn't intersects the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(0, 1, 1))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: The ray is included in the plane
        assertEquals("Plane: findIntersections() wrong when ray is included in the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, 1, 0))));
        // TC12: The ray is not included in the plane
        assertEquals("Plane: findIntersections() wrong when ray is parallel in the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, 1), new Vector(0, 1, 0))));

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane
        assertEquals("Plane: findIntersections() wrong when ray is orthogonal and starts before the plane", List.of(new Intersectable.GeoPoint(plane, new Point3D(0, 1, 0))),
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, -1), new Vector(0, 0, 1))));
        // TC14: Ray starts at the plane
        assertEquals("Plane: findIntersections() wrong when ray is orthogonal and starts at the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(0, 0, 1))));
        // TC15: Ray starts after the plane
        assertEquals("Plane: findIntersections() wrong when ray is orthogonal and starts after the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, 1), new Vector(0, 0, 1))));

        // **** Group: Special cases
        // TC16: Ray is neither orthogonal nor parallel to the plane and begins at the plane
        assertEquals("Plane: findIntersections() wrong when ray starts at the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 1, 1))));
        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
        assertEquals("Plane: findIntersections() wrong when ray starts with the same point which appears as reference point in the plane", null,
                thisPlane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 1, 1))));
    }
}