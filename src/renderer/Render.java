package renderer;

import primitives.*;
import primitives.Color;
import scene.Scene;
import java.util.List;

/**
 * Render class represents the whole thing, it connects between the imageWriter and the scene
 * system
 *
 * @author Moriah and Shahar
 */

public class Render {
    // The image
    ImageWriter _imageWriter;
    // The scene that needs to become a scene
    Scene _scene;

    /**
     * Render constructor. gets the elements for the render - imageWriter and scene
     *
     * @param _imageWriter The image
     * @param _scene The scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
    }

    /**
     * Makes the scene into an image (in imageWriter)
     */
    public void renderImage() {
        for (int i = 0; i < _imageWriter.getNy(); i++)
            for (int j = 0; j < _imageWriter.getNx(); j++) {
                Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(),  j, i,
                        _scene.getDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
                List<Point3D> intersectionPoints = _scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, _scene.getBackground().getColor());
                else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
    }

    /**
     * Calculates the color of a point on the object
     *
     * @param p The point we need to calculate the color on
     * @return The color in that point
     */
    private Color calcColor(Point3D p) {
        return _scene.getAmbientLight().getIntensity();
    }

    /**
     * Get the closest point to the camera from a list of intersections
     *
     * @param points the list of intersections
     * @return the closest point
     */
    private Point3D getClosestPoint(List<Point3D> points) {
        if (points == null)
            return null;
        Point3D smallest = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).distance(_scene.getCamera().get_p0()) < smallest.distance(_scene.getCamera().get_p0()))
                smallest = points.get(i);
        }
        return smallest;
    }

    /**
     * Draws a grid on top of the picture (With our running over the picture)
     *
     * @param interval The size of each square in the grid
     * @param color The color of the grid
     */
    public void printGrid(int interval, java.awt.Color color) {
        for (int i = 0; i < _imageWriter.getNy(); i++)
            for (int j = 0; j < _imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
    }

    /**
     * Creates the image as a JPG file
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
}