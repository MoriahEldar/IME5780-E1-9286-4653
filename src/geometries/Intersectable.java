package geometries;

import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Intersectable interface represents a shape that a ray can cross it
 * system
 *
 * @author Moriah and Shahar
 */

public abstract class Intersectable {
    /**
     * The virtual box for each geometry
     */
    protected BVHBox box;

    /**
     * Default constructor. By default box is null if not yet calculated.
     */
    public Intersectable() {
        box = null;
    }

    /**
     * finds the points where the given ray "hit" the shape
     *
     * @param ray the given ray
     * @return a list of points where the ray "hit" the shape
     */
    public abstract List<GeoPoint> findIntersections(Ray ray);

    /**
     * Box Getter. Provides only one calculation of box;
     *
     * @return The box for that shape
     */
    public final BVHBox getBox() {
        if (box == null)
            box = calcBox();
        return box;
    }

    /**
     * Calculates the BVHBox for the specific intersectable geometry.
     *
     * @return The calculated BVHBox
     */
    protected abstract BVHBox calcBox();

    /**
     * GeoPoint is an static help class. represents a point and the geometry the point is on
     * (for when finding intersections. In order to know for witch intersection it is)
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * GeoPoint constructor. gets a geometry and a point and puts it in geometry and point variables
         *
         * @param geometry the given geometry
         * @param point the given point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = new Point3D(point);
        }

        /*************** Admin *****************/
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.getClass() == geoPoint.geometry.getClass() &&
                    point.equals(geoPoint.point);
        }
    }
}
