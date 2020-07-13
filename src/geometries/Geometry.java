package geometries;
import primitives.*;

/**
 * Geometry interface represents any geometry shape in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public abstract class Geometry extends Intersectable {
    /**
     * The color of the geometry's shapes
      */
    protected Color _emission;
    /**
     * The material the the geometry is created from
     */
    protected Material _material;

    /**
     * Geometry constructor. Gets the shapes color and put it in _emission
     * Sets material to new Material(0, 0, 0)
     *
     * @param _emission the color
     */
    public Geometry(Color _emission) {
        this(_emission, new Material(0, 0, 0));
    }

    /**
     * Geometry default constructor. Puts in _emission the color black. Puts in material new Material(0, 0, 0)
     */
    public Geometry() {
        this(Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * Geometry constructor. Gets the shapes color and put it in _emission
     * Gets shape material and puts it in _material
     *
     * @param _emission shapes color
     * @param _material shapes material
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = new Color(_emission);
        this._material = _material;
    }

    /**
     * returns the normal (normalized) to the geometry shape in the given point
     *
     * @param point Point3D
     * @return vector that represent the normal (normalized)
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * _emission getter
     *
     * @return a new color with emission value
     */
    public Color get_emission() {
        return new Color(_emission);
    }

    /**
     * _material getter
     *
     * @return the material of this geometry
     */
    public Material get_material() {
        return _material;
    }

}
