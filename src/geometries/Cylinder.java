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
        this._axisRay = new Ray(_axisRay);
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
        return null;
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
