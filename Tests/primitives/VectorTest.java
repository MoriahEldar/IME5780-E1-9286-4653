package primitives;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Moriah and Shahar
 */
public class VectorTest {
    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}
     * Test method for {@link primitives.Vector#Vector(primitives.Coordinate, primitives.Coordinate, primitives.Coordinate)}
     * Test method for {@link primitives.Vector#Vector(primitives.Point3D)}
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============
        // T1, on Vector(double, double, double), ERROR when it's the ZERO vector
        try {
            Vector vec = new Vector(0, 0, 0);
            fail("Vector: doubleConstructor() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
        // T2, on Vector(primitives.Coordinate, primitives.Coordinate, primitives.Coordinate), ERROR when it's the ZERO vector
        try {
            Vector vec = new Vector(new Coordinate(0), new Coordinate(0), new Coordinate(0));
            fail("Vector: CoordinateConstructor() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
        // T3, on Vector(primitives.Point3D), ERROR when it's the ZERO vector
        try {
            Vector vec = new Vector(Point3D.ZERO);
            fail("Vector: pointConstructor() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any 2 vectors that are not the same vector
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(-1, 3, 1);
        assertEquals("Vector: subtract() wrong", new Vector(2, -2, 0), v1.subtract(v2));
        // T2, 2 vectors that are the same
        try {
            v1.subtract(v1);
            fail("Vector: subtract() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any 2 vectors that are not reversed
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(-1, 3, 1);
        assertEquals("Vector: add() wrong", new Vector(0, 4, 2), v1.add(v2));
        // T2, 2 vectors that are the reversed
        try {
            v1.add(v1.scale(-1));
            fail("Vector: add() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    public void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any scalar that is not zero with any vector
        Vector v1 = new Vector(1, -3, 1);
        assertEquals("Vector: scale() wrong", new Vector(-3, 9, -3), v1.scale(-3));
        assertEquals("Vector: scale() wrong", new Vector(3, -9, 3), v1.scale(3));
        // T2, multiply by zero
        try {
            v1.scale(0);
            fail("Vector: scale() doesn't throw error to a zero vector");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}
     */
    @Test
    public void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // T1, 2 vectors with the same direction
        Vector v1 = new Vector(1, 2, -3);
        Vector v2 = new Vector(2, 4, -6);
        assertTrue("Vector: dotProduct() wrong with 2 vectors in the same direction", isZero(28 - v1.dotProduct(v2)));
        // T2, 2 vectors with a sharp angle
        v1 = new Vector(1, 0, 0);
        v2 = new Vector(2, 1, 0);
        assertTrue("Vector: dotProduct() wrong with 2 vectors with sharp angle", isZero(2 - v1.dotProduct(v2)));
        // T3, 2 orthogonal vectors
        v1 = new Vector(1, 3, -2);
        v2 = new Vector(2, 2, 4);
        assertTrue("Vector: dotProduct() wrong with 2 orthogonal vectors", isZero(v1.dotProduct(v2)));
        // T4, 2 vectors with obtuse angle
        v1 = new Vector(1, 0, 0);
        v2 = new Vector(-2, -2, 0);
        assertTrue("Vector: dotProduct() wrong with 2 vectors with obtuse angle", isZero(-2 - v1.dotProduct(v2)));
        // T5, 2 vectors on opposite directions
        v1 = new Vector(3, -5, 2);
        v2 = new Vector(-6, 10, -4);
        assertTrue("Vector: dotProduct() wrong with 2 vectors on opposite directions", isZero(-76 - v1.dotProduct(v2)));
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}
     */
    @Test
    public void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // T1, 2 vectors with the same direction
        Vector v1 = new Vector(1, 2, -3);
        Vector v2 = new Vector(2, 4, -6);
        try {
            v1.crossProduct(v2);
            fail("Vector: crossProduct() does not throw exception with 2 vectors in the same direction");
        }
        catch (IllegalArgumentException e) {}
        // T2, 2 vectors with a sharp angle
        v1 = new Vector(1, 0, 0);
        v2 = new Vector(2, 1, 0);
        assertEquals("Vector: crossProduct() wrong with 2 vectors with sharp angle", new Vector(0, 0, 1), v1.crossProduct(v2));
        // T3, 2 orthogonal vectors
        v1 = new Vector(1, 3, -2);
        v2 = new Vector(2, 2, 4);
        assertEquals("Vector: crossProduct() wrong with 2 orthogonal vectors", new Vector(16, -8, -4), v1.crossProduct(v2));
        // T4, 2 vectors with obtuse angle
        v1 = new Vector(1, 0, 0);
        v2 = new Vector(-2, -2, 0);
        assertEquals("Vector: crossProduct() wrong with 2 vectors with obtuse angle", new Vector(0, 0, -2), v1.crossProduct(v2));
        // T5, 2 vectors in opposite directions
        v1 = new Vector(3, -5, 2);
        v2 = new Vector(-6, 10, -4);
        try {
            v1.crossProduct(v2);
            fail("Vector: crossProduct() does not throw exception with 2 vectors in the opposite direction");
        }
        catch (IllegalArgumentException e) {}
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}
     */
    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any vector
        Vector v1 = new Vector(1, -3, 9);
        assertTrue("Vector: lengthSquared() wrong", isZero(91 - v1.lengthSquared()));
    }

    /**
     * Test method for {@link primitives.Vector#length()}
     */
    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        // T1, Any vector
        Vector v1 = new Vector(1, -3, 9);
        assertTrue("Vector: length() wrong", isZero(Math.sqrt(91) - v1.length()));
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}
     */
    @Test
    public void normalize() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any vector
        Vector v1 = new Vector(Math.PI, 10, 5);
        Vector v2 = v1.normalize();
        assertTrue("Vector: normalize() wrong", isZero(1 - v2.length()));
        assertSame("Vector: normalize() creates a new vector", v1, v2);
    }

    /**
     * Test method for {@link Vector#normalized()}
     */
    @Test
    public void normalized() {
        // ============ Equivalence Partitions Tests ==============
        // T1, any Vector
        Vector v1 = new Vector(Math.PI, 10, 5);
        Vector v2 = v1.normalized();
        assertTrue("Vector: normalized() wrong", isZero(1 - v2.length()));
        assertNotSame("Vector: normalized() changes it self and does not crate a new vector", v1, v2);
    }
}