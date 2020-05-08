package geometries;

import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Triangle class
 * @author Moriah and Shahar
 */

public class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any triangle
        Triangle triangle = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(1, 1, 0));
        Plane expected = new Plane(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(1, 1, 0));
        assertEquals("Triangle: getNormal() wrong", expected.getNormal(null), triangle.getNormal(null));
    }

    /**
    * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
    */
    @Test
    public void testFindIntersections() {
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: ray intersects with the triangle
        assertEquals("Ray does not intersects with the triangle correctly", List.of(new Intersectable.GeoPoint(tr, new Point3D(0.25, 0.25, 0.5))),
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0.25, -0.75, -1.5))));
        // TC02: ray intersects with the plane outside the triangle against edge
        assertEquals("Wrong when ray intersects with the plane outside the triangle against edge", null,
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(1, 0, -3))));
        // TC03: ray intersects with the plane outside the triangle against vertex
        assertEquals("Wrong when ray intersects with the plane outside the triangle against vertex", null,
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(3, -2, -3))));

        // =============== Boundary Values Tests ==================

        // TC11: ray intersects with the triangle on edge
        assertEquals("Wrong when ray intersects with the triangle on edge", null,
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0.5, -0.5, -2))));

        // TC12: ray intersects with the triangle in vertex
        assertEquals("Wrong when ray intersects with the triangle in vertex", null,
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(0, -1, -1))));

        // TC13: ray intersects with the triangle on edge's continuation
        assertEquals("Wrong when ray intersects with the triangle on edge's continuation", null,
                tr.findIntersections(new Ray(new Point3D(0, 1, 2), new Vector(1, -1, -3))));
    }
}