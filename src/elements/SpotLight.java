package elements;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SpotLight class represents the lights in the scene that are models point light source with direction (such as a luxo lamp)
 * (They have a point of the light, with direction direction, and the light weakens as it goes further, or off the direction)
 * system
 *
 * @author Moriah and Shahar
 */

public class SpotLight extends PointLight {
    /**
     * Lights direction
     */
    Vector _dir;

    /**
     * SpotLight constructor. Gets the color of the light, and puts it in intensity.
     * Gets also the position of the light source and puts it im position. Gets the 3 factors for attenuation with distance
     * Gets light direction
     *
     * @param _intensity the color of the light
     * @param _position the point in the scene of the light source
     * @param _dir the vector that is the light direction
     * @param _kC factor for attenuation with distance, the single factor
     * @param _kL factor for attenuation with distance, the d factor
     * @param _kQ factor for attenuation with distance, the d^2 factor
     * @throws IllegalArgumentException if the factors are'nt positive
     */
    public SpotLight(Color _intensity, Point3D _position, Vector _dir, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        this._dir = new Vector(_dir.normalized());
    }

    /*************** Admin *****************/
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0, _dir.dotProduct(getL(p))));
    }
}
