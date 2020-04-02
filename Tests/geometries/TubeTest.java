package geometries;

import org.junit.Test;
import primitives.*;

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
}