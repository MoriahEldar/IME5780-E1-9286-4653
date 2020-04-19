package geometries;

import primitives.*;
import java.util.List;

/**
 * Intersectable interface represents a shape that a ray can cross it
 * system
 *
 * @author Moriah and Shahar
 */

public interface Intersectable {
    /**
     * finds the points where the given ray "hit" the shape
     *
     * @param ray
     * @return a list of points where the ray "hit" the shape
     */
    List<Point3D> findIntersections(Ray ray);
}
