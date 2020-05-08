package geometries;

import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Tube class
 * @author Moriah and Shahar
 */
public class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}
     */
    @Test
    public void testGetNormal() {
        Tube tube = new Tube(3, new Ray(Point3D.ZERO, new Vector(1, 0, 0)));
        // ============ Equivalence Partitions Tests ==============
        // T1, the point is on the tube
        assertEquals("Tube: GetNormal() wrong result", new Vector(0,1 ,0), tube.getNormal(new Point3D(2, 3, 0)));
    }

    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}
     */
    @Test
    public void testFindIntersections() {
        Tube tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
        assertEquals("Ray's line out of tube", null,
                tube.findIntersections(new Ray(new Point3D(0, -3, 0), new Vector(1, 1, 1))));

        // TC02: Ray starts before and crosses the tube (2 points)
        Point3D p1 = new Point3D(0.2587520733765, -0.6712313396113, 1.552512440259);
        Point3D p2 = new Point3D(0.4241747558918, 0.8175728030259, 2.545048535351);
        List<Intersectable.GeoPoint> result = tube.findIntersections(new Ray(new Point3D(0, -3, 0),
                new Vector(0.3, 2.7, 1.8)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube wrong", List.of(new Intersectable.GeoPoint(tube, p1), new Intersectable.GeoPoint(tube, p2)), result);

        // TC03: Ray starts inside the tube (1 point)
        assertEquals("Not good when ray starts inside the tube", List.of(new Intersectable.GeoPoint(tube, new Point3D(0.4241747558918, 0.8175728030259, 2.545048535351))),
                tube.findIntersections(new Ray(new Point3D(0.3, -0.3, 1.8), new Vector(0.3, 2.7, 1.8))));

        // TC04: Ray starts after the tube (0 points)
        assertEquals("Not good when ray starts after the tube", null,
                tube.findIntersections(new Ray(new Point3D(0.6, 2.4, 3.6), new Vector(0.3, 2.7, 1.8))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the tube (but not the center)
        // TC11: Ray starts at tube and goes inside (1 points)
        assertEquals("Not good when ray starts at the tube", List.of(new Intersectable.GeoPoint(tube, new Point3D(0.4241747558918, 0.8175728030259, 2.545048535351))),
                tube.findIntersections(new Ray(new Point3D(0.2587520733765, -0.6712313396113, 1.552512440259), new Vector(0.3, 2.7, 1.8))));
        // TC12: Ray starts at tube and goes outside (0 points)
        assertEquals("Not good when ray starts at the tube and outside", null,
                tube.findIntersections(new Ray(new Point3D(0.2587520733765, -0.6712313396113, 1.552512440259), new Vector(-0.3, -2.7, -1.8))));

        // **** Group: Ray's line goes through the center point
        // TC13: ray starts before the tube (2 points)
        p1 = new Point3D(1, -1, -1.0/3);
        p2 = new Point3D(1, 1, 1.0/3);
        result = tube.findIntersections(new Ray(new Point3D(1, -3, -1),
                new Vector(0, 3, 1)));
        assertEquals("Wrong number of points when through the center", 2, result.size());
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube wrong when through the center", List.of(new Intersectable.GeoPoint(tube, p1), new Intersectable.GeoPoint(tube,  p2)), result);
        // TC14: Ray starts at tube and goes inside (1 points)
        assertEquals("Not good when ray starts at the tube throw center", List.of(new Intersectable.GeoPoint(tube, new Point3D(1, 1,1.0/3))),
                tube.findIntersections(new Ray(new Point3D(1, -1, -1.0/3), new Vector(0, 3, 1))));
        // TC15: Ray starts inside (1 points)
        assertEquals("Not good when ray starts inside the sphere throw center", List.of(new Intersectable.GeoPoint(tube, new Point3D(1, 1, 1.0/3))),
                tube.findIntersections(new Ray(new Point3D(1, -2.0/3, -2.0/9), new Vector(0, 3, 1))));
        // TC16: Ray starts at the center (1 points)
        assertEquals("Not good when ray starts on the center", List.of(new Intersectable.GeoPoint(tube, new Point3D(1, 1, 1.0/3))),
                tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 3, 1))));
        // TC17: Ray starts at tube and goes outside (0 points)
        assertEquals("Not good when ray starts at the tube and outside, center direction", null,
                tube.findIntersections(new Ray(new Point3D(1, 1, 1.0/3), new Vector(0, 3, 1))));
        // TC18: Ray starts after tube (0 points)
        assertEquals("Not good when ray starts after tube and outside, center direction", null,
                tube.findIntersections(new Ray(new Point3D(1, 4, 4.0/3), new Vector(0, 3, 1))));

        // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Not good when tangent, before the point", null,
                tube.findIntersections(new Ray(new Point3D(0, 1, 0), new Vector(1, 0, 1.0/3))));
        // TC20: Ray starts at the tangent point
        assertEquals("Not good when tangent, before the point", null,
                tube.findIntersections(new Ray(new Point3D(1, 1, 1.0/3), new Vector(1, 0, 1.0/3))));
        // TC21: Ray starts after the tangent point
        assertEquals("Not good when tangent, before the point", null,
                tube.findIntersections(new Ray(new Point3D(2, 1, 2/3), new Vector(1, 0, 1/3))));

        // **** Group: Ray is parallel to tube's ray (all tests 0 points)
        // TC22: Ray's line is outside
        assertEquals("Not good when ray is parallel outside", null,
                tube.findIntersections(new Ray(new Point3D(-2, 0, 0), new Vector(0, 0, 1))));
        // TC23: Ray's line is inside
        assertEquals("Not good when ray is parallel inside", null,
                tube.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(0, 0, 1))));
        // TC24: Ray's line is the same as the tubes line
        assertEquals("Not good when ray is parallel same", null,
                tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1))));
        // TC25: Ray's line is the same as the tubes line with different point
        assertEquals("Not good when ray is parallel same except point", null,
                tube.findIntersections(new Ray(new Point3D(1, 0, 1), new Vector(0, 0, 1))));
        // TC26: Ray's line is on tube's border
        assertEquals("Not good when ray is parallel border", null,
                tube.findIntersections(new Ray(new Point3D(1, 1, 1.0/3), new Vector(0, 0, 1))));

        // **** Group: Ray is orthogonal to tube's ray
        // TC27: Ray is outside
        assertEquals("Not good when ray is orthogonal outside", null,
                tube.findIntersections(new Ray(new Point3D(-2, 0, 0), new Vector(0, 1, 0))));
        // TC28: Ray has 2 intersections
        p1 = new Point3D(0.5, -0.8660254037844, 0);
        p2 = new Point3D(0.5, 0.8660254037844, 0);
        result = tube.findIntersections(new Ray(new Point3D(0.5, -2, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points when orthogonal", 2, result.size());
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube wrong when orthogonal", List.of(new Intersectable.GeoPoint(tube, p1), new Intersectable.GeoPoint(tube, p2)), result);
        // TC29: Ray has 2 intersections throw middle point
        p1 = new Point3D(1, -1, 0);
        p2 = new Point3D(1, 1, 0);
        result = tube.findIntersections(new Ray(new Point3D(1, -2, 0),
                new Vector(0, 1, 0)));
        assertEquals("Wrong number of points when orthogonal throw middle", 2, result.size());
        if (result.get(0).point.get_y().get() > result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube wrong when orthogonal throw middle", List.of(new Intersectable.GeoPoint(tube, p1), new Intersectable.GeoPoint(tube, p2)), result);
        // TC30: Ray starts inside
        assertEquals("Not good when ray is orthogonal inside middle point", List.of(new Intersectable.GeoPoint(tube, new Point3D(1, 1, 0))),
                tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));
    }
}