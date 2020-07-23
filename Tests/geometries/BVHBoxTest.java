package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class BVHBoxTest {

    @Test
    public void anyIntersections() {
        BVHBox box = new BVHBox(Point3D.ZERO, new Point3D(3,3,3));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the box
        assertFalse("Ray's line out of box",
                box.anyIntersections(new Ray(new Point3D(2, -3, 2), new Vector(3, 5, 0))));

        // TC02: Ray starts before and crosses the box
        assertTrue("Ray starts before",
                box.anyIntersections(new Ray(new Point3D(2, -3, 2), new Vector(-3, 7, 0))));

        // TC03: Ray starts inside the box
        assertTrue("Ray starts inside",
                box.anyIntersections(new Ray(new Point3D(1, 1, 2), new Vector(0, 3, 3))));

        // TC04: Ray starts after the box
        assertFalse("Ray starts after",
                box.anyIntersections(new Ray(new Point3D(1, 4, 5), new Vector(0, 3, 3))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line parallel to 2 (non parallel) box edges
        // TC11: Ray starts before and crosses the box
        assertTrue("Ray starts before 2 parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(1, 0, 0))));

        // TC12: Ray starts inside and crosses the box
        assertTrue("Ray starts inside 2 parallel",
                box.anyIntersections(new Ray(new Point3D(1, 1, 2), new Vector(1, 0, 0))));

        // TC13: Ray starts after and crosses the box
        assertFalse("Ray starts after 2 parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(-1, 0, 0))));

        // TC14: Ray does not cross the box
        assertFalse("Ray does not cross 2 parallel",
                box.anyIntersections(new Ray(new Point3D(-1, -1, 2), new Vector(1, 0, 0))));

        // TC15: Ray crosses edge
        assertTrue("Ray crosses edge 2 parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 0, 2), new Vector(1, 0, 0))));

        // TC16: Ray crosses border
        assertTrue("Ray crosses border 2 parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0))));

        // **** Group: Ray's line parallel to 1 box edges
        // TC17: Ray starts before and crosses the box
        assertTrue("Ray starts before parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(2, -0.75, 0))));

        // TC18: Ray starts inside and crosses the box
        assertTrue("Ray starts inside parallel",
                box.anyIntersections(new Ray(new Point3D(1, 0.25, 2), new Vector(2, -0.75, 0))));

        // TC19: Ray starts after
        assertFalse("Ray starts after parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(-2, 0.75, 0))));

        // TC20: Ray crosses border
        assertTrue("Ray crosses border parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(1, -1, 0))));

        // TC21: Ray crosses vertex
        assertTrue("Ray crosses vertex parallel",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 3), new Vector(1, -1, 0))));

        // **** Group: Ray's line crosses edges/borders/vertex
        // TC22: Ray crosses vertex
        assertTrue("Ray crosses vertex",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(1, -1, 1))));

        // TC23: Ray crosses border
        assertTrue("Ray crosses border",
                box.anyIntersections(new Ray(new Point3D(-1, 1, 2), new Vector(2, -2, 1))));
    }
}