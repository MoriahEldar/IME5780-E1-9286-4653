package geometries;

import primitives.Color;
import primitives.Material;

/**
 * RadialGeometry abstract class represents any radial geometry shape in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public abstract class RadialGeometry extends Geometry {
    /**
     * The radius of the Radial geometry shape
     */
    double _radius;

    /**
     * RadialGeometry constructor receiving a radius value
     * Sets geometry's color (_emission) to black
     * Sets the material to (0, 0, 0)
     *
     * @param _radius value
     */
    public RadialGeometry(double _radius) {
        this(Color.BLACK, _radius);
    }

    /**
     * RadialGeometry constructor receiving a color which is the geometry's color and a radius value
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the color
     * @param _radius value
     */
    public RadialGeometry(Color _emission, double _radius) {
        this(_emission, new Material(0, 0, 0), _radius);
    }

    /**
     * RadialGeometry constructor receiving a color which is the geometry's color and a radius value,
     * and a material which is the material that the geometry is made from
     *
     * @param _emission the color
     * @param _material material, the geometry material
     * @param _radius value
     */
    public RadialGeometry(Color _emission, Material _material, double _radius) {
        super(_emission, _material);
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
