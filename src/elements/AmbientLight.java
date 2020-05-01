package elements;

import primitives.Color;

/**
 * AmbientLight class represents an environmental light
 * system
 *
 * @author Moriah and Shahar
 */

public class AmbientLight {
    /**
     * The color of the environment
     */
    Color _intensity;

    /**
     * Ambientlight constructed. Calculates the _intensity according to _kA * _IA
     *
     * @param _IA The color
     * @param _kA Fixed attenuation (Because of the distance)
     */
    public AmbientLight(Color _IA, double _kA) {
        this._intensity = _IA.scale(_kA);
    }

    /**
     * Getter function for _intensity
     *
     * @return A copy of the _intensity color
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}
