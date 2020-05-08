package primitives;

/**
 * Material class represents materials that from it each object is created
 * system
 *
 * @author Moriah and Shahar
 */

public class Material {
    /**
     * Diffusion Level of the material
     */
    double _kD;
    /**
     * Specular Level of the material
     */
    double _kS;
    /**
     * Shininess Level of the material
     */
    int _nShininess;

    /**
     * Material constructor. Gets the material details, and puts them in their variables
     *
     * @param _kD Diffusion Level of the material (double)
     * @param _kS Specular Level of the material (double)
     * @param _nShininess Shininess Level of the material (int)
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * _kD getter
     *
     * @return diffusion Level of the material
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * _kS getter
     *
     * @return specular Level of the material
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * _nShininess getter
     *
     * @return shininess Level of the material
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
