package elements;

import primitives.*;
import static primitives.Util.alignZero;

/**
 * PointLight class represents the lights in the scene that are models omni-directional point source (such as a bulb)
 * (They have a point of the light, and the light weakens as it goes further. No direction)
 * system
 *
 * @author Moriah and Shahar
 */

public class PointLight extends Light implements LightSource {
    /**
     * Light position of the light source in the scene
     */
    protected Point3D _position;
    /**
     * Factors for attenuation with distance
     */
    protected double _kC;
    protected double _kL;
    protected double _kQ;

    /**
     * PointLight constructor. Gets the color of the light, and puts it in intensity.
     * Gets also the position of the light source and puts it im position. Gets the 3 factors for attenuation with distance
     *
     * @param _intensity the color of the light
     * @param _position the point in the scene of the light source
     * @param _kC factor for attenuation with distance, the single factor
     * @param _kL factor for attenuation with distance, the d factor
     * @param _kQ factor for attenuation with distance, the d^2 factor
     * @throws IllegalArgumentException if the factors are'nt positive
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        this._position = new Point3D(_position);
        if(alignZero(_kC) <= 0 || alignZero(_kL) <= 0 || alignZero(_kQ) <= 0)
            throw new IllegalArgumentException("The factors must be positive");
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;
    }

    /*************** Admin *****************/
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity.scale(1.0/(_kC + _kL * p.distance(this._position) + _kQ * p.distanceSquared(this._position)));

    }

    @Override
    public Vector getL(Point3D p) {
        try {
            return p.subtract(this._position).normalize();
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public double getDistance(Point3D point) {
        return point.distance(_position);
    }
}
