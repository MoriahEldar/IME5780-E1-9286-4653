package elements;

import primitives.Color;

/**
 * Light class represents the lights in the scene (colors of them and so on)
 * system
 *
 * @author Moriah and Shahar
 */

abstract class Light {
    /**
     * The color of the light
     */
    protected Color _intensity;

    /**
     * Light constructor. Gets the color of the light, and puts it in intensity.
     *
     * @param _intensity the color of the light
     */
    public Light(Color _intensity) {
        this._intensity = new Color(_intensity);
    }

    /**
     * _intensity getter
     *
     * @return a new color with _intensity value.
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}
