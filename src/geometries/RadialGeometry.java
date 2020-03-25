package geometries;

/**
 * RadialGeometry abstract class represents any radial geometry shape in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of the Radial geometry shape
     */
    double _radius;

    /**
     * RadialGeometry constructor receiving a radius value
     *
     * @param _radius value
     */
    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    /**
     * Copy constructor for RadialGeometry
     *
     * @param other
     */
    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
    }

    /**
     * Radius value getter
     *
     * @return _radius value
     */
    public double get_radius() {
        return _radius;
    }
}
