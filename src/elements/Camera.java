package elements;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
     * Size of the aperture adjuster
     */
    double _aperture;
    /**
     * The distance from the view plane to the focal plane
     */
    double _focusDistance;

    /**
     * Camera constructor receiving a point and two vectors.
     * Creates a third vector (vRight) that is orthogonal to the two vectors.
     * Throws an exception if the 2 receiving vectors are not orthogonal to each other.
     *
     * @param _p0 a point that represents the camera in the scene.
     * @param _vTo a vector that goes towards the view plane (And orthogonal to it).
     * @param _vUp a vector that his direction is up from the camera.
     * @throws IllegalArgumentException if the 2 receiving vectors are not orthogonal to each other.
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) {
        this(_p0, _vTo, _vUp, 0, 0);
    }

    /**
     * Camera constructor receiving a point, two vectors, and two numbers.
     * Creates a third vector (vRight) that is orthogonal to the two vectors.
     *
     * @param _p0 a point that represents the camera in the scene.
     * @param _vTo a vector that goes towards the view plane (And orthogonal to it).
     * @param _vUp a vector that his direction is up from the camera.
     * @param _aperture a double that is the size of the aperture adjuster.
     * @param _focusDistance a double that is the distance from the view plane to the focal plane.
     * @throws IllegalArgumentException if the 2 receiving vectors are not orthogonal to each other.
     * @throws IllegalArgumentException if aperture size is less then zero.
     * @throws IllegalArgumentException if focal distance is less then zero.
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp, double _aperture, double _focusDistance) {
        if (_vTo.dotProduct(_vUp) != 0)
            throw new IllegalArgumentException("The vectors are not orthogonal to each other");
        if (_aperture < 0)
            throw new IllegalArgumentException("The aperture size cannot be negative");
        if (_focusDistance < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        this._p0 = new Point3D(_p0);
        this._vTo = new Vector(_vTo.normalized());
        this._vUp = new Vector(_vUp.normalized());
        this._vRight = this._vTo.crossProduct(this._vUp).normalized();
        this._aperture = _aperture;
        this._focusDistance = _focusDistance;
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
     * _aperture getter
     *
     * @return the size of the aperture adjuster
     */
    public double get_aperture() {
        return _aperture;
    }
    /**
     * _focusDistance getter
     *
     * @return the distance from the view plane to the focal plane
     */
    public double get_focusDistance() {
        return _focusDistance;
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

    /**
     * Constructs all the rays from the aperture adjuster plane
     * All of these rays are in the direction to the focal point (Where the basic ray intersects with the focal plane)
     * For the depth field feature
     *
     * @param screenDistance the distance of the view plane from the camera
     * @param basicRay the ray from the camera to a pixel. Defines the focal point
     * @param numOfRays number of rays to make from the adjuster plane. Defines photo and deep field feature quality
     * @return A list with numOfRays constructed rays
     */
    public List<Ray> constructRaysFromAperture(double screenDistance, Ray basicRay, double numOfRays) {
        Random rand = new Random();
        // distance is the distance from the point on the view screen (that the aperture is around it) to the camera
        double distance = screenDistance/(_vTo.dotProduct(basicRay.get_direction()));
        Point3D pointOnPixel = _p0.add(basicRay.get_direction().scale(distance));
        List<Ray> rays = new LinkedList<>(List.of(new Ray(pointOnPixel, basicRay.get_direction())));
        double distanceBetweenCameraAndFocalPlane = screenDistance + _focusDistance;
        // focalPointDistance is the distance from the focalPoint to the camera
        double focalPointDistance = distanceBetweenCameraAndFocalPlane/(_vTo.dotProduct(basicRay.get_direction()));
        Point3D focalPoint = _p0.add(basicRay.get_direction().scale(focalPointDistance));
        for (int k = 1; k < numOfRays; k++) {
            Point3D pointInAperture = new Point3D(pointOnPixel);
            double up = (rand.nextDouble() * _aperture) - (_aperture / 2); // A random between -_aperture/2 and _aperture/2
            if (!isZero(up))
                pointInAperture = pointInAperture.add(this._vRight.scale(up));
            double right = (rand.nextDouble() * _aperture) - (_aperture / 2); // A random between -_aperture/2 and _aperture/2
            if (!isZero(right))
                pointInAperture = pointInAperture.add(this._vUp.scale(right));
            rays.add(new Ray(pointInAperture, focalPoint));
        }
        return rays;
    }

}
