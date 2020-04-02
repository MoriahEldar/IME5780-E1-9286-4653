package geometries;

import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Moriah and Shahar
 */
public class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}
     */
    @Test
    public void testGetNormal() {
        Cylinder cylinder = new Cylinder(3, new Ray(Point3D.ZERO, new Vector(1, 0, 0)), 5);

        // ============ Equivalence Partitions Tests ==============
        // T1, the point is in the base where the Ray point is. (We will call this base the lower base)
        assertEquals("Cylinder: GetNormal() wrong result when the point is in the lower base", new Vector(-1,0 ,0), cylinder.getNormal(new Point3D(0, 1, 0)));
        // T2, the point is in the other base. (We will call this base the higher base)
        assertEquals("Cylinder: GetNormal() wrong result when the point is in the higher base", new Vector(1,0 ,0), cylinder.getNormal(new Point3D(5, 1, 0)));
        // T3, the point is in the casing.
        assertEquals("Cylinder: GetNormal() wrong result when the point is in the casing", new Vector(0,1 ,0), cylinder.getNormal(new Point3D(2, 3, 0)));

        // =============== Boundary Values Tests ==================
        // T1, the point is on the line between the lower base and the casing
        // We decided it should bring back the normal as if the point is on the base
        assertEquals("Cylinder: GetNormal() wrong result when the point is in the connection between the lower base and the casing", cylinder.getNormal(new Point3D(0, 3, 0)), new Vector(-1,0 ,0));
        // T1, the point is on the line between the higher base and the casing
        // We decided it should bring back the normal as if the point is on the base
        assertEquals("Cylinder: GetNormal() wrong result when the point is in the connection between the higher base and the casing", cylinder.getNormal(new Point3D(5, 3, 0)), new Vector(1,0 ,0));
    }
}