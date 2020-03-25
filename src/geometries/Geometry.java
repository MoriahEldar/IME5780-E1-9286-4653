package geometries;
import primitives.*;

/**
 * Geometry interface represents any geometry shape in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public interface Geometry {
    /**
     * returns the normal (normalized) to the geometry shape in the given point
     *
     * @param point
     * @return vector that represent the normal (normalized)
     */
    public Vector getNormal(Point3D point);
}
