package renderer;

import elements.*;
import primitives.*;
import primitives.Color;
import geometries.Intersectable.GeoPoint;
import scene.*;
import java.util.List;

import static primitives.Util.alignZero;

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
                List<GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null)
                    _imageWriter.writePixel(j, i, _scene.getBackground().getColor());
                else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
    }

    /**
     * Calculates the color of a point on the object
     *
     * @param intersection The point we need to calculate the color on
     * @return The color in that point
     */
    private Color calcColor(GeoPoint intersection) {
        Color color = _scene.getAmbientLight().getIntensity();
        color = color.add(intersection.geometry.get_emission());
        Vector v = intersection.point.subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Material material = intersection.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection.point);
            if (Math.signum(alignZero(n.dotProduct(l))) == Math.signum(alignZero(n.dotProduct(v)))) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive component for the light on the element in a specific point
     *
     * @param kd diffusion Level of the material
     * @param l a direction vector from the light to the point
     * @param n a normal to the element
     * @param lightIntensity the color that the light makes on the element on a specific point
     * @return The diffusive component for the light on the element in the specific point
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }

    /**
     * Calculates the specular component for the light on the element in a specific point
     *
     * @param ks Specular Level of the material
     * @param l a direction vector from the light to the point
     * @param n a normal to the element
     * @param v a direction vector from the camera to the point
     * @param nShininess Shininess Level of the material
     * @param lightIntensity the color that the light makes on the element on a specific point
     * @return The specular component for the light on the element in a specific point
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r;
        try {
            r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        }
        catch (IllegalArgumentException e) {
            return Color.BLACK;
        }
        return lightIntensity.scale(ks * Math.pow(Math.max(0, -v.dotProduct(r)), nShininess));
    }

    /**
     * Get the closest point to the camera from a list of intersections
     *
     * @param points the list of intersections
     * @return the closest point
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points) {
        if (points == null)
            return null;
        GeoPoint smallest = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).point.distance(_scene.getCamera().get_p0()) < smallest.point.distance(_scene.getCamera().get_p0()))
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