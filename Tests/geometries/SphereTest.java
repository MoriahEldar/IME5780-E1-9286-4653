package geometries;

import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Moriah and Shahar
 */
public class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}
     */
    @Test
    public void testGetNormal() {
        Sphere sphere = new Sphere(3, Point3D.ZERO);
        // ============ Equivalence Partitions Tests ==============
        // T1, any point on the sphere
        assertEquals("sSphere: getNormal() wrong", new Vector(0, 0, 1), sphere.getNormal(new Point3D(0, 0, 3)));
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).get_x().get() > result.get(1).get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere wrong", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        assertEquals("Not good when ray starts inside the sphere", List.of(new Point3D(1, 0.5, Math.sqrt(0.75))),
                sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, 0, 1))));

        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray crosses sphere when starts after sphere", null,
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 1))));


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        assertEquals("Not good when ray starts at the sphere", List.of(new Point3D(1, 0,1)),
                sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 1))));
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Not good when ray starts at the sphere and outside", null,
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 1))));

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(1, -1, 0);
        p2 = new Point3D(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point3D(1, -2, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points when through the center", 2, result.size());
        if (result.get(0).get_y().get() > result.get(1).get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere wrong when through the center", List.of(p1, p2), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        assertEquals("Not good when ray starts at the sphere throw center", List.of(new Point3D(1, 1,0)),
                sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, 1, 0))));
        // TC15: Ray starts inside (1 points)
        assertEquals("Not good when ray starts inside the sphere throw center", List.of(new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, -0.5, 0), new Vector(0, 1, 0))));
        // TC16: Ray starts at the center (1 points)
        assertEquals("Not good when ray starts on the center", List.of(new Point3D(1, 1, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Not good when ray starts at the sphere and outside, center direction", null,
                sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, 1, 0))));
        // TC18: Ray starts after sphere (0 points)
        assertEquals("Not good when ray starts after sphere and outside, center direction", null,
                sphere.findIntersections(new Ray(new Point3D(1, 2, 0), new Vector(0, 1, 0))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Not good when tangent, before the point", null,
                sphere.findIntersections(new Ray(new Point3D(2, -1, 0), new Vector(0, 1, 0))));
        // TC20: Ray starts at the tangent point
        assertEquals("Not good when tangent, before the point", null,
                sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 0))));
        // TC21: Ray starts after the tangent point
        assertEquals("Not good when tangent, before the point", null,
                sphere.findIntersections(new Ray(new Point3D(2, 1, 0), new Vector(0, 1, 0))));

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Not good in special case", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(0, 1, 0))));
    }

}