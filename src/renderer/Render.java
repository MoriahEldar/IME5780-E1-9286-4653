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
    /**
     * The image
     */
    ImageWriter _imageWriter;
    /**
     * The scene that needs to become a scene
     */
    Scene _scene;
    /**
     * Constants for stop recursion condition for transparency / reflection calculation of color
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
                Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNy(), _imageWriter.getNx(), j, i,
                        _scene.getDistance(), _imageWriter.getHeight(), _imageWriter.getWidth());
                GeoPoint closestPoint = findClosestIntersection(ray);
                _imageWriter.writePixel(j, i, closestPoint == null ? _scene.getBackground().getColor() : calcColor(closestPoint, ray).getColor());
            }
    }

    /**
     * Calculates the color of a point on the object
     *
     * @param gp The point we need to calculate the color on
     * @param ray The ray from the camera threw a pixel at view plane that we are calculating the color at
     * @return The color in that point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculates the color of a point on the object without the ambient light
     *
     * @param intersection The point we need to calculate the color on
     * @param inRay The ray from the camera threw a pixel at view plane that we are calculating the color at
     * @param level Recursion level (for calculating the reflection and refraction)
     * @param k dimming level
     * @return The color without ambient light, in that point
     */
    private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
        Color color = intersection.geometry.get_emission();
        Vector v = intersection.point.subtract(_scene.getCamera().get_p0()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Material material = intersection.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.getLights()) {
            Vector l = lightSource.getL(intersection.point);
            if (n.dotProduct(l) * n.dotProduct(v) > 0) {
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        if (level == 1)
            return Color.BLACK;
        double kr = intersection.geometry.get_material().get_kR();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, intersection.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level-1, kkr).scale(kr));
        }
        double kt = intersection.geometry.get_material().get_kT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, intersection.point, inRay) ;
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level-1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * Checks if a point is in shadow from the light or not
     *
     * @param light the light source we are checking if the point is blocked from
     * @param l a vector from the light to the point
     * @param n normal vector to the geometry in the specific point
     * @param gp the point (GeoPoint - the point and it's geometry)
     * @return false if the point is blocked from the light, true if not
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint thisGp : intersections) {
            if (thisGp.geometry.get_material().get_kT() == 0 && alignZero(thisGp.point.distance(gp.point) - lightDistance) <= 0)
                return false;
        }
        return true;
    }

    /**
     * Checks how much shadow should be
     *
     * @param ls the light source we are checking if the point is blocked from
     * @param l a vector from the light to the point
     * @param n normal vector to the geometry in the specific point
     * @param geopoint the point (GeoPoint - the point and it's geometry)
     * @return a double that represents how much shadow should be.
     * (how much should we multiply the color, when 0 if completely shadow, 1 if no shadow, and in between for a partly shadow (according to level of shadow))
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)
            return 1.0;
        double lightDistance = ls.getDistance(geopoint.point);
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
        }
        return ktr;
    }

    /**
     * Calculates the refracted ray (from the camera) on a specific point and ray
     *
     * @param n normal to the geometry
     * @param point a point on the geometry that the ray has intersection with
     * @param inRay a ray, from the camera throw the view plane
     * @return the refracted ray from the inRay at that point
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
        return new Ray(point, inRay.get_direction(), n);
    }

    /**
     * Calculates the reflected ray (from the camera) on a specific point and ray
     *
     * @param n normal to the geometry
     * @param point a point on the geometry that the ray has intersection with
     * @param inRay a ray, from the camera throw the view plane
     * @return the reflected ray from the inRay at that point
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
        Vector dir = inRay.get_direction().subtract(n.scale(2 * inRay.get_direction().dotProduct(n)));
        return new Ray(point, dir, n);
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
        GeoPoint closest = points.get(0);
        for (GeoPoint thisPoint : points) {
            if (thisPoint.point.distance(_scene.getCamera().get_p0()) < closest.point.distance(_scene.getCamera().get_p0()))
                closest = thisPoint;
        }
        return closest;
    }

    /**
     * Finds the closest intersection (that the given ray has with any geometry in the scene)
     * to the starting point of the ray
     *
     * @param ray the given ray in the scene
     * @return The closest intersection to the ray's starting point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;
        GeoPoint closest = intersections.get(0);
        for (GeoPoint intersection : intersections) {
            if (intersection.point.distance(ray.get_startPoint()) < closest.point.distance(ray.get_startPoint()))
                closest = intersection;
        }
        return closest;
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