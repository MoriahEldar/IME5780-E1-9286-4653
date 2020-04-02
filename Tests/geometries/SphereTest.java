package geometries;

import org.junit.Test;
import primitives.*;

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
        assertEquals("Sphere: getNormal() wrong", new Vector(0, 0, 1), sphere.getNormal(new Point3D(0, 0, 3)));
    }
}