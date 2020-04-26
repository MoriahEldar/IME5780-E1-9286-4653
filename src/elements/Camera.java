package elements;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the scene
 * system
 *
 * @author Moriah and Shahar
 */

public class Camera {
    /**
     * _p0 is the point that represents the camera.
     */
    Point3D _p0;

    /**
     * 3 vectors that represent the 3 vectors of the 3D space (x axis, y axis and z axis)
     * _vTo is the vector that goes towards the view plane (And orthogonal to it)
     * _vUp is a vector that his direction is up
     * _vRight is a vector that his direction is right (according to the other vectors)
     * All the vectors are orthogonal to each other
     */
    Vector _vTo;
    Vector _vUp;
    Vector _vRight;

    /**
     * Camera constructor receiving a point and two vectors. creates a third vector (vRight) that is orthogonal to the two vectors.
     * Throws an exception if the 2 receiving vectors are not orthogonal to each other.
     *
     * @param _p0 a point (represents the variable this._p)
     * @param _vTo a vector (represents the variable this._vTo)
     * @param _vUp another vector (represents the variable this._vUp)
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        if (_vTo.dotProduct(_vUp) != 0)
            throw new IllegalArgumentException("The vectors are not orthogonal to each other");
        this._p0 = new Point3D(_p0);
        this._vTo = new Vector(_vTo.normalized());
        this._vUp = new Vector(_vUp.normalized());
        this._vRight = this._vTo.crossProduct(this._vUp).normalized();
    }

    /**
     * _p0 value getter
     *
     * @return a new point with _p0 value
     */
    public Point3D get_p0() {
        return new Point3D(_p0);
    }

    /**
     * _vTo value getter
     *
     * @return a new vector with _vTo value
     */
    public Vector get_vTo() {
        return new Vector(_vTo);
    }

    /**
     * _vUp value getter
     *
     * @return a new vector with _vUp value
     */
    public Vector get_vUp() {
        return new Vector(_vUp);
    }

    /**
     * _vRight value getter
     *
     * @return a new vector with _vRight value
     */
    public Vector get_vRight() {
        return new Vector(_vRight);
    }

    /**
     * Calculates the ray from the camera that gets to the middle of the given pixel on the view plane
     *
     * @param nX pixel width
     * @param nY pixel height
     * @param j x index of given pixel on the view plane
     * @param i y index of given pixel on the view plane
     * @param screenDistance the distance of the view plane from the camera
     * @param screenWidth view plane's width
     * @param screenHeight view plane's height
     * @return A ray from the camera to the middle of the pixel with the indexes j, i
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        if (nX <= 0 || nY <= 0)
            throw new IllegalArgumentException("Number of pixels has to be positive");
        if (i < 0 || j < 0)
            throw new IllegalArgumentException("The coordinate cannot be negative");
        if (i >= nY || j >= nX)
            throw new IllegalArgumentException("Coordinates out of boundaries");
        if (alignZero(screenDistance) <= 0)
            throw new IllegalArgumentException("Distance has to be positive");
        if (alignZero(screenWidth) <= 0)
            throw new IllegalArgumentException("Width has to be positive");
        if (alignZero(screenHeight) <= 0)
            throw new IllegalArgumentException("Height has to be positive");
        Point3D pC = this._p0.add(this._vTo.scale(screenDistance));
        double yI = (i - (nY-1)/2d) * (screenHeight/nY);
        double xJ = (j - (nX-1)/2d) * (screenWidth/nX);
        Point3D pIJ = pC;
        if (!isZero(xJ))
            pIJ = pIJ.add(this._vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(this._vUp.scale(-yI));
        return new Ray(this._p0, pIJ);
    }
}
