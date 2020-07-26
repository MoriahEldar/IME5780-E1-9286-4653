package geometries;
import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Plane class represents a plane in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Plane extends Geometry {
    /**
     * The plane is represented by a 3D point in the plane and a normal (normalized) to the plane
     */
    Point3D _p;
    Vector _normal;

    /**
     * Plane constructor receiving a color which is the plane's color,
     * a material which is the material that the plane is made from, and 3 3D points in the plane
     *
     * @param _emission the color of the plane
     * @param _material the material the plane is made of
     * @param _p point in the plane (the point we will save as represents the plane)
     * @param _p1 point in the plane
     * @param _p2 point in the plane
     * @throws IllegalArgumentException if the 3 points are on the same line
     */
    public Plane(Color _emission, Material _material, Point3D _p, Point3D _p1, Point3D _p2) {
        super(_emission, _material);
        this._p = new Point3D(_p);
        // Calculating the normal
        Vector v1 = _p.subtract(_p1).normalize();
        Vector v2 = _p.subtract(_p2).normalize();
        if (v1.equals(v2) || v1.equals(v2.scale(-1))) // If the three points are on the same line
            throw new IllegalArgumentException("The three points cannot be on the same line");
        _normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Plane constructor receiving a color which is the plane's color, and 3 3D points in the plane
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the color of the plane
     * @param _p point in the plane (the point we will save as represents the plane)
     * @param _p1 point in the plane
     * @param _p2 point in the plane
     * @throws IllegalArgumentException if the 3 points are on the same line
     */
    public Plane(Color _emission, Point3D _p, Point3D _p1, Point3D _p2) {
        this(Color.BLACK, new Material(0, 0, 0), _p, _p1, _p2);
    }

    /**
     * Plane constructor receiving 3 3D points in the plane
     * Sets plane's color (_emission) to black
     * Sets the material to (0, 0, 0)
     *
     * @param _p point in the plane (the point we will save as represents the plane)
     * @param _p1 point in the plane
     * @param _p2 point in the plane
     * @throws IllegalArgumentException if the 3 points are on the same line
     */
    public Plane(Point3D _p, Point3D _p1, Point3D _p2) {
        this(Color.BLACK, _p, _p1, _p2);
    }

    /**
     * Plane constructor receiving a color which is the plane's color,
     * a material which is the material that the plane is made from,
     * 3D point in the plane, and a normal to the plane
     *
     * @param _emission the plane color
     * @param _material the material the plane is made of
     * @param _p the point in the plane
     * @param _normal a vector that is a normal to the plane
     */
    public Plane(Color _emission, Material _material, Point3D _p, Vector _normal) {
        super(_emission, _material);
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal.normalize());
    }

    /**
     * Plane constructor receiving a color which is the plane's color, 3D point in the plane, and a normal to the plane
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the plane color
     * @param _p the point in the plane
     * @param _normal a vector that is a normal to the plane
     */
    public Plane(Color _emission, Point3D _p, Vector _normal) {
        this(_emission, new Material(0, 0, 0), _p, _normal);
    }

    /**
     * Plane constructor receiving a 3D point in the plane, and a normal to the plane
     * Sets plane's color (_emission) to black. Sets the material to (0, 0, 0)
     *
     * @param _p the point in the plane
     * @param _normal a vector that is a normal to the plane
     */
    public Plane(Point3D _p, Vector _normal) {
        this(Color.BLACK, _p, _normal);
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
     * @param point a 3D point
     * @return True if the point is on the plane, False if not.
     */
    public boolean isOnPlane(Point3D point) {
        try {
            // make a vector between the given point and the point on the plane
            Vector vecBetweenPoints = point.subtract(_p);
            // check if the vector is in the plane - orthogonal to the normal
            return isZero(vecBetweenPoints.dotProduct(_normal));
        }
        catch(IllegalArgumentException e) {
            // if the given point is the same point as the point on the plane (approximately),
            // the vector will be the zero vector, so there will be an exception.
            // but in that case, the point is on the plane
            return true;
        }
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

    @Override
    public List<GeoPoint> findIntersectionsTemp(Ray ray) {
        double denominator = _normal.dotProduct(ray.get_direction());
        // If ray is parallel to the plane
        if (isZero(denominator))
            return null;
        try {
            double t = _normal.dotProduct(_p.subtract(ray.get_startPoint())) / denominator;
            if (t <= 0)
                return null;
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        catch (IllegalArgumentException e) {
            // _p is the same point as ray.get_startPoint()
            return null;
        }
    }

    @Override
    protected boolean shouldFindIntersections(Ray ray) {
        return true;
    }

    /**
     * Plane is an infinite geometry, therefore it has no box that can capture it, so the box is null
     *
     * @return null, because it's an infinite shape
     */
    @Override
    protected BVHBox calcBox() {
        return null;
    }
}
