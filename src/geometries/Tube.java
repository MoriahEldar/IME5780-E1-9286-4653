package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represents a 3D tube (an infinity cylinder) in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Tube extends RadialGeometry {
    /**
     * A Ray that represents the center line of the Tube
     */
    protected Ray _axisRay;

    /**
     * Tube constructor receiving radius and a Ray which is the center line of the sphere
     *
     * @param _radius double, _axisRay the Ray
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
    }

    /**
     * _axisRay value getter
     *
     * @return a new Ray with _axisRay value
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    /*************** Admin *****************/
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube {" +
                "axis Ray = " + _axisRay +
                ", R = " + _radius +
                '}';
    }
}
