package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class represents the lights in the scene that are far away and does not lose power, just have a direction (like the sun)
 * system
 *
 * @author Moriah and Shahar
 */

public class DirectionalLight extends Light implements LightSource {
    /**
     * The direction of the light
     */
    private Vector _direction;

    /**
     * DirectionalLight constructor. Gets the color of the light, and puts it in intensity,
     * and the direction of the light and puts in _direction
     *
     * @param _direction the vector direction
     * @param _intensity the color of the light
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = new Vector(_direction.normalized());
    }

    /*************** Admin *****************/
    @Override
    public Color getIntensity(Point3D p) {
        return new Color(_intensity);
    }

    @Override
    public Vector getL(Point3D p) {
        return new Vector(_direction);
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
