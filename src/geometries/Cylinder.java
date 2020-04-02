package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class represents a 3D Cylinder (a final tube) in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Cylinder extends Tube {
    /**
     * A number that represents the height of the cylinder - makes it a final tube
     */
    double _height;

    /**
     * Cylinder constructor receiving radius and a Ray which is the center line of the sphere
     * and a double that is the height of the cylinder
     *
     * @param _radius double, _axisRay the Ray, _height double
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    /**
     * _height value getter
     *
     * @return _height value
     */
    public double get_height() {
        return _height;
    }

    /*************** Admin *****************/
    @Override
    public Vector getNormal(Point3D point) {
        // Creates the plane that the "lower" base (the base that contains the ray point) is on
        Plane plane = new Plane(_axisRay.get_startPoint(), _axisRay.get_direction());
        // Checks if the given point is on the plane (We know the point is on the cylinder, so if it's on the plane, it's on the base)
        if(plane.isOnPlane(point))
            return _axisRay.get_direction().scale(-1);
        // Creates the plane that the "higher" base (the base that does not contain the ray point) is on
        // The point on the plane (that we give the constructor) is the ray point plus the cylinder height in the ray direction
        plane = new Plane(_axisRay.get_startPoint().add(_axisRay.get_direction().scale(_height)), _axisRay.get_direction());
        // Checks if the given point is on the plane (We know the point is on the cylinder, so if it's on the plane, it's on the base)
        if(plane.isOnPlane(point))
            return _axisRay.get_direction();
        // The point is on the casing
        return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder {" +
                " height = " + _height +
                ", axisRay = " + _axisRay +
                ", R = " + _radius +
                '}';
    }
}
