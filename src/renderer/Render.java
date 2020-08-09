package renderer;

import elements.*;
import geometries.Geometries;
import primitives.*;
import primitives.Color;
import geometries.Intersectable.GeoPoint;
import scene.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Renderer class is responsible for generating pixel color map from a graphic scene, using ImageWriter class
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
     * Is depth of field feature on or off
     */
    boolean _depthField;

    /**
     * Number of rays to send when calculating depthField. Defines photo and depth field feature quality
     */
    int _numOfRays;

    /**
     * For multithreading
     */
    private int _threads = 1; // Number of threads to activate
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     * @author Dan
     *
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    } // end pixel class

    /**
     * Render constructor. gets the elements for the render - imageWriter and scene
     *
     * @param _imageWriter The image
     * @param _scene The scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._imageWriter = _imageWriter;
        this._scene = _scene;
        this._depthField = false;
        this._numOfRays = 0;
        _scene.set_improvementBVH(false);
    }

    /**
     * This function renders image's pixel color map from the scene included with the Renderer object
     */
    public void renderImage() {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();
        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
                    _imageWriter.writePixel(pixel.col, pixel.row, calcColor(ray).getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            }
            catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Calculates the color that the ray hits in accordance to if the depth of field feature is on.
     * If it's on, it calculates according to the aperture.
     *
     * @param ray the ray the color is all about
     * @return the color according to the ray and depth of field feature if on
     */
    private Color calcColor(Ray ray)
    {
        if (!_depthField) {
            GeoPoint closestPoint = findClosestIntersection(ray);
            return closestPoint == null ? _scene.getBackground() : calcColor(closestPoint, ray);
        }
        else {
            Color sumColor = Color.BLACK;
            List<Ray> rays = _scene.getCamera().constructRaysFromAperture(_scene.getDistance(), ray, _numOfRays);
            for (Ray apertureRay : rays) {
                GeoPoint closestPoint = findClosestIntersection(apertureRay);
                sumColor = sumColor.add(closestPoint == null ? _scene.getBackground() : calcColor(closestPoint, apertureRay));
            }
            return sumColor.reduce(_numOfRays);
        }
    }

    /**
     * Calculates the color of a point on the object
     *
     * @param gp The point we need to calculate the color on
     * @param ray The ray from the camera throw a pixel at view plane that we are calculating the color at
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

    /**
     * Is depth of field feature on or off (_depthField Getter)
     *
     * @return _depthField value. True if on, otherwise false
     */
    public Boolean isDepthFieldOn() {
        return _depthField;
    }

    /**
     * _depthField Setter (set depth of field feature on or off)
     *
     * @param _depthField true if you want to set depth of field feature on, otherwise false.
     * @throws IllegalArgumentException if _numOfRays is not set yet (is 0)
     */
    public void set_depthField(Boolean _depthField) {
        if (_depthField && _numOfRays == 0)
            throw new IllegalArgumentException("Cannot use depth of field feature when number of rays to use is 0 (not defined yet)");
        this._depthField = _depthField;
    }

    /**
     * _numOfRays getter
     *
     * @return number of rays to construct from the aperture adjuster in depth of field feature
     */
    public int get_numOfRays() {
        return _numOfRays;
    }

    /**
     * _numOfRays setter
     *
     * @param _numOfRays number of rays to construct from the aperture adjuster in depth of field feature
     * @throws IllegalArgumentException if input is less than 50
     */
    public void set_numOfRays(int _numOfRays) {
        if (_numOfRays < 50)
            throw new IllegalArgumentException("To get a good affect, the number of rays should be more than 50");
        this._numOfRays = _numOfRays;
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * Is BVH ray tracing improvement on or off (_improvementBVH Getter)
     *
     * @return _improvementBVH value. True if on, otherwise false
     */
    public boolean isImprovementBVHOn() {
        return _scene.getGeometries().isImprovementBVHOn();
    }

    /**
     * _improvementBVH Setter (set BVH ray tracing improvement on or off)
     *
     * @param _improvementBVH true if you want to set BVH ray tracing improvement on, otherwise false.
     */
    public void set_improvementBVH(boolean _improvementBVH) {
        _scene.set_improvementBVH(_improvementBVH);
    }
}