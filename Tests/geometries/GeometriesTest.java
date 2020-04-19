package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class GeometriesTest {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void testFindIntersections() {
        Geometries scene = new Geometries(new Sphere(1, new Point3D(1, 0, 0)),
                new Plane(new Point3D(3, 5, 9), new Vector(0, 0, 1)),
                new Triangle(new Point3D(0, 0, 4), new Point3D(2, -5, 4), new Point3D(4, 3, 4)));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Some of the shapes (not all) have Intersections
        assertEquals("Wrong when some of the shapes (not all) have Intersections", 3,
                scene.findIntersections(new Ray(new Point3D(1, 0, 6), new Vector(0, 0, -1))).size());

        // =============== Boundary Values Tests ==================

        // TC11: The elements list is empty
        assertEquals("Wrong when the elements list is empty", null, new Geometries().findIntersections(null));

        // TC12: None of the shapes have Intersections
        assertEquals("Wrong when none of the shapes have Intersections", null,
                scene.findIntersections(new Ray(new Point3D(1, 0, -2), new Vector(1, 0, 0))));

        // TC13: Only one of the shapes has Intersections
        assertEquals("Wrong when only one of the shapes has Intersections", 1,
                scene.findIntersections(new Ray(new Point3D(-5, 0, -2), new Vector(0, 0, 1))).size());

        // TC14: All of the shapes have Intersections
        assertEquals("Wrong when all of the shapes have Intersections", 4,
                scene.findIntersections(new Ray(new Point3D(1, 0, -2), new Vector(0, 0, 1))).size());
    }
}