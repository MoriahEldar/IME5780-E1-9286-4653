package geometries;
import primitives.*;

/**
 * Plane class represents a plane in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Plane implements Geometry {
    /**
     * The plane is represented by a 3D point in the plane and a normal (normalized) to the plane
     */
    Point3D _p;
    Vector _normal;

    /**
     * Vector constructor receiving 3 3D points in the plane
     *
     * @param _p, _p1, _p2 are all points in the plane
     */
    public Plane(Point3D _p, Point3D _p1, Point3D _p2) {
        this._p = new Point3D(_p);
        // Calculating the normal
        Vector v1 = _p.subtract(_p1).normalize();
        Vector v2 = _p.subtract(_p2).normalize();
        if (v1.equals(v2) || v1.equals(v2.scale(-1))) // If the three points are on the same line
            throw new IllegalArgumentException("The three points cannot be on the same line");
        _normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Vector constructor receiving a 3D point in the plane and a normal to the plane
     *
     * @param _p the point and _normal a vector that is a _normal to the plane
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal.normalize());
    }

    /**
     * _p value getter
     *
     * @return a new 3D point with _p values
     */
    public Point3D get_p() {
        return new Point3D(_p);
    }

    /**
     * _normal value getter
     *
     * @return a new vector with _normal values
     */
    public Vector get_normal() {
        return new Vector(_normal);
    }

    /**
     * gets a 3D point and determent's whether the point is on the plane or not
     *
     * @param point, a 3D point
     * @return True if the point is on the plane, False if not.
     */
    public boolean isOnPlane(Point3D point) {
        // Makes the plane equation
        double d = _normal.get_point().get_x().get() * _p.get_x().get() +
                _normal.get_point().get_y().get() * _p.get_y().get() +
                _normal.get_point().get_z().get() * _p.get_z().get();
        // Checks if the point holds the equation
        return _normal.get_point().get_x().get() * point.get_x().get() +
                _normal.get_point().get_y().get() * point.get_y().get() +
                _normal.get_point().get_z().get() * point.get_z().get() - d == 0;
    }

    /*************** Admin *****************/
    @Override
    public Vector getNormal(Point3D point) {
        return get_normal();
    }

    @Override
    public String toString() {
        double d = _normal.get_point().get_x().get() * _p.get_x().get() +
                _normal.get_point().get_y().get() * _p.get_y().get() +
                _normal.get_point().get_z().get() * _p.get_z().get();
        // We returned a string that represents the plane equation
        return _normal.get_point().get_x().toString() + "x + " +
                _normal.get_point().get_y().toString() + "y + " +
                _normal.get_point().get_z().toString() + "z + " + -d + " = 0";
    }
}
