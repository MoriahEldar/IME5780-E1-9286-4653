package elements;

import primitives.Color;

/**
 * AmbientLight class represents an environmental light
 * system
 *
 * @author Moriah and Shahar
 */

public class AmbientLight extends Light{

    /**
     * Ambientlight constructor. Calculates the _intensity according to _kA * _IA
     *
     * @param _IA The color
     * @param _kA Fixed attenuation (Because of the distance)
     */
    public AmbientLight(Color _IA, double _kA) {
        super( _IA.scale(_kA));
    }
}
