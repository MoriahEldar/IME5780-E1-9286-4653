package geometries;

import org.junit.Test;
import primitives.*;

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
}