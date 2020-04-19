package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
    public List<Point3D> findIntersections(Ray ray) {
        Vector u;
        try {
            u = _center.subtract(ray.get_startPoint());
        }
        catch (IllegalArgumentException e) {
            return List.of(ray.get_startPoint().add(ray.get_direction().scale(_radius)));
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
                return List.of(ray.get_startPoint().add(ray.get_direction().scale(tm + th)));
        }
        if (tm + th <= 0)
            return List.of(ray.get_startPoint().add(ray.get_direction().scale(tm - th)));
        if (th == 0)
            return List.of(ray.get_startPoint().add(ray.get_direction().scale(tm)));
        return List.of(ray.get_startPoint().add(ray.get_direction().scale(tm + th)), ray.get_startPoint().add(ray.get_direction().scale(tm - th)));
    }
}
