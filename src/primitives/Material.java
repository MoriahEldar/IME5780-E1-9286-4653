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
     * Transparency Level of the material
     */
    double _kT;
    /**
     * Reflection Level of the material
     */
    double _kR;

    /**
     * Material constructor. Gets the material details, and puts them in their variables
     * puts in _kT and _kR zero
     *
     * @param _kD Diffusion Level of the material (double)
     * @param _kS Specular Level of the material (double)
     * @param _nShininess Shininess Level of the material (int)
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD, _kS, _nShininess, 0, 0);
    }

    /**
     * Material constructor. Gets the material details, and puts them in their variables
     *
     * @param _kD Diffusion Level of the material (double)
     * @param _kS Specular Level of the material (double)
     * @param _nShininess Shininess Level of the material (int)
     * @param _kR Transparency Level of the material (double)
     * @param _kT Reflection Level of the material (double)
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
        this._kT = _kT;
        this._kR = _kR;
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

    /**
     * _kT getter
     *
     * @return transparency Level of the material
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * _kR getter
     *
     * @return reflection Level of the material
     */
    public double get_kR() {
        return _kR;
    }
}
