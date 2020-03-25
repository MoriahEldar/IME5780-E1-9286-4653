package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class represents a 3D sphere in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Sphere extends RadialGeometry {
    /**
     * A 3D point that represents the center of the Sphere
     */
    Point3D _center;

    /**
     * Sphere constructor receiving radius and a 3D point which is the center of the sphere
     *
     * @param _radius double, _center the 3D point
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = new Point3D(_center);
    }

    /**
     * _center value getter
     *
     * @return a new Point3D with _center value
     */
    public Point3D get_center() {
        return new Point3D(_center);
    }

    /*************** Admin *****************/
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Sphere {" +
                "center = " + _center +
                ", R = " + _radius +
                '}';
    }
}
