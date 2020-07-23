package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

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
     * Sets sphere's color (_emission) to black
     * Sets the material to (0, 0, 0)
     *
     * @param _radius double
     * @param _center the 3D point
     */
    public Sphere(double _radius, Point3D _center) {
        this(Color.BLACK, _radius, _center);
    }

    /**
     * Sphere constructor receiving a color which is the sphere's color,
     * a radius and a 3D point which is the center of the sphere
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the color
     * @param _radius double
     * @param _center the 3D point
     */
    public Sphere(Color _emission, double _radius, Point3D _center) {
        this(_emission, new Material(0, 0, 0), _radius, _center);
    }

    /**
     * Sphere constructor receiving a color which is the sphere's color,
     * a material which is the material that the sphere is made from,
     * a radius and a 3D point which is the center of the sphere
     *
     * @param _emission the color
     * @param _material material, the sphere material
     * @param _radius double
     * @param _center the 3D point
     */
    public Sphere(Color _emission, Material _material, double _radius, Point3D _center) {
        super(_emission, _material, _radius);
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
        return point.subtract(_center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere {" +
                "center = " + _center +
                ", R = " + _radius +
                '}';
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        if (!getBox().anyIntersections(ray))
            return null;
        Vector u;
        try {
            u = _center.subtract(ray.get_startPoint());
        }
        catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this, ray.getPoint(_radius)));
        }
        double tm = alignZero(ray.get_direction().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= _radius)
            return null;
        double th = alignZero(Math.sqrt(_radius * _radius - d * d));
        if (tm - th <= 0) {
            if (tm + th <= 0)
                return null;
            else
                return List.of(new GeoPoint(this, ray.getPoint(tm + th)));
        }
        if (tm + th <= 0)
            return List.of(new GeoPoint(this, ray.getPoint(tm - th)));
        if (th == 0)
            return List.of(new GeoPoint(this, ray.getPoint(tm)));
        return List.of(new GeoPoint(this, ray.getPoint(tm + th)), new GeoPoint(this, ray.getPoint(tm - th)));
    }

    @Override
    protected BVHBox calcBox() {
        return new BVHBox(new Point3D(_center.get_x().get() - _radius,
                _center.get_y().get() - _radius,
                _center.get_z().get() - _radius),
                new Point3D(_center.get_x().get() + _radius,
                        _center.get_y().get() + _radius,
                        _center.get_z().get() + _radius));
    }
}
