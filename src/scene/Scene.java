package scene;

import elements.*;
import geometries.*;
import primitives.*;

/**
 * Scene class represents the whole scene that is making the picture - The camera and the picture arguments
 * system
 *
 * @author Moriah and Shahar
 */

public class Scene {
    // Scene name
    String _name;
    // The background color of the scene
    Color _background;
    // The environmental light in the scene
    AmbientLight _ambientLight;
    // The geometries shapes in the scene
    Geometries _geometries;
    // The camera that is taking the picture
    Camera _camera;
    // The distance from the camera to the view plane
    double _distance;

    /**
     * Scene constructor. Sets the name of the scene with a given name, and resets the geometries to an empty list
     *
     * @param _name the scenes name
     */
    public Scene(String _name) {
        this._name = _name;
        this._geometries = new Geometries();
    }

    /**
     * _name Getter
     *
     * @return The name of the scene
     */
    public String getName() {
        return _name;
    }

    /**
     * _background Getter
     *
     * @return The background color of the scene
     */
    public Color getBackground() {
        return new Color(_background);
    }

    /**
     * _ambientLight Getter
     *
     * @return The environmental light color in the scene (an AmbientLight type variable)
     */
    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    /**
     * _geometries Getter
     *
     * @return A collection of the geometries shapes in the scene (a Geometry type variable)
     */
    public Geometries getGeometries() {
        return new Geometries(_geometries);
    }

    /**
     * _camera Getter
     *
     * @return The details of the camera in the scene (a Camera type variable)
     */
    public Camera getCamera() {
        return _camera;
    }

    /**
     * _distance Getter
     *
     * @return The distance between the camera and the view plane in the scene
     */
    public double getDistance() {
        return _distance;
    }

    /**
     * _background Setter
     *
     * @param _background the background color of the scene (an Color type variable)
     */
    public void setBackground(Color _background) {
        this._background = new Color(_background);
    }

    /**
     * _ambientLight Setter
     *
     * @param _ambientLight the environmental light color in the scene (an AmbientLight type variable)
     */
    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * _camera Setter
     *
     * @param _camera the details of the camera in the scene (a Camera type variable)
     */
    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * _distance Setter
     *
     * @param _distance the distance between the camera and the view plane in the scene
     */
    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    /**
     * Adds Geometries shapes to the scene
     *
     * @param geometries a list of the new geometries shapes to add
     */
    public void addGeometries(Intersectable... geometries) {
        this._geometries.add(geometries);
    }
}
