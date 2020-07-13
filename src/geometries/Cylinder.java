package geometries;

import primitives.*;

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
     * sets the _emission (the color shape) as black color
     * Sets the material to (0, 0, 0)
     *
     * @param _radius  double
     * @param _axisRay the Ray
     * @param _height  double
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        this(Color.BLACK, _radius, _axisRay, _height);
    }

    /**
     * Cylinder constructor receiving radius and a Ray which is the center line of the sphere
     * and a double that is the height of the cylinder, and a color which is the cylinders color (_emission)
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the color
     * @param _radius   double
     * @param _axisRay  the Ray
     * @param _height   double
     */
    public Cylinder(Color _emission, double _radius, Ray _axisRay, double _height) {
        this(_emission, new Material(0, 0, 0), _radius, _axisRay, _height);
    }

    /**
     * Cylinder constructor receiving radius and a Ray which is the center line of the sphere
     * a double that is the height of the cylinder, and a color which is the cylinders color (_emission)
     * and a material which is the material that the cylinder is made from
     *
     * @param _emission the color
     * @param _material material, the cylinder material
     * @param _radius   double
     * @param _axisRay  the Ray
     * @param _height   double
     */
    public Cylinder(Color _emission, Material _material, double _radius, Ray _axisRay, double _height) {
        super(_emission, _material, _radius, _axisRay);
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
        if (plane.isOnPlane(point))
            return _axisRay.get_direction().scale(-1);
        // Creates the plane that the "higher" base (the base that does not contain the ray point) is on
        // The point on the plane (that we give the constructor) is the ray point plus the cylinder height in the ray direction
        plane = new Plane(_axisRay.get_startPoint().add(_axisRay.get_direction().scale(_height)), _axisRay.get_direction());
        // Checks if the given point is on the plane (We know the point is on the cylinder, so if it's on the plane, it's on the base)
        if (plane.isOnPlane(point))
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

    /**
     * We did not execute cylinder for the scene so this function is irrelevant for cylinder, therefore it returns null
     *
     * @return null, because we didn't execute cylinder. Supposed to return the BVHBox
     **/
    @Override
    protected BVHBox calcBox() {
        return null;
    }
}
