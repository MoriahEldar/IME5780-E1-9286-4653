package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a bundle of geometric elements.
 * system
 *
 * @author Moriah and Shahar
 */

public class Geometries extends Intersectable {
    /**
     * A list of non-infinite elements in the scene (including elements from Geometries kind)
     */
    private List<Intersectable> _finalElements;

    /**
     * A list of infinite elements in the scene
     */
    private List<Intersectable> _infinityElements;

    /**
     * A list of all elements. In order to turn off the BVH improvement
     */
    private List<Intersectable> _elements;

    /**
     * Geometries default constructor. Puts in elements an empty list
     */
    public Geometries() {
        this._finalElements = new LinkedList<Intersectable>();
        this._infinityElements = new LinkedList<Intersectable>();
        this._elements = new LinkedList<Intersectable>();
        _improvementBVH = false;
    }

    /**
     * Geometries constructor receiving some intersectable elements and puts it to the elements in the scene
     * (Divides it into non infinite elements, and infinite elements)
     *
     * @param geometries - list of intersectable elements
     */
    public Geometries(Intersectable... geometries) {
        this._elements = List.of(geometries);
        this._finalElements = new LinkedList<Intersectable>();
        this._infinityElements = new LinkedList<Intersectable>();
        // separate the geometries into infinite and none infinite geometries
        for (Intersectable element : geometries)
            if (element.getBox() == null)
                this._infinityElements.add(element);
            else
                this._finalElements.add(element);
    }

    /**
     * Add, receiving receiving some intersectable elements and adds it to the elements in the scene
     * (Divides it into non infinite elements, and infinite elements)
     *
     * @param geometries - list of intersectable elements
     */
    public void add(Intersectable... geometries) {
        this._elements.addAll(List.of(geometries));
        // separate the geometries into infinite and none infinite geometries
        for (Intersectable element : geometries)
            if (element.getBox() == null)
                this._infinityElements.add(element);
            else
                this._finalElements.add(element);
    }

    /**
     * Auxiliary function. Finds the minimal or maximal point for the box (from all the minimals and maximals points of the other geometries)
     *
     * @param isMin true if we want to get the minimal value, false if maximal
     * @return the min or max point of the box (That is the box that surrounds all the elements). (Has min or max X coordinate, min or max Y coordinate, min or max Z coordinate)
     */
    private Point3D minOrMaxPoint(boolean isMin) {
        double maxOrMinX = isMin ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        double maxOrMinY = maxOrMinX;
        double maxOrMinZ = maxOrMinX;
        for (Intersectable element : _finalElements) {
            Point3D maxOrMinPoint = isMin ? element.getBox().getMin() : element.getBox().getMax();
            double valueX = maxOrMinPoint.get_x().get();
            double valueY = maxOrMinPoint.get_y().get();
            double valueZ = maxOrMinPoint.get_z().get();
            if (isMin ? valueX < maxOrMinX : valueX > maxOrMinX)
                maxOrMinX = valueX;
            if (isMin ? valueY < maxOrMinY : valueY > maxOrMinY)
                maxOrMinY = valueY;
            if (isMin ? valueZ < maxOrMinZ : valueZ > maxOrMinZ)
                maxOrMinZ = valueZ;
        }
        return new Point3D(maxOrMinX, maxOrMinY, maxOrMinZ);
    }

    /**
     * Divides all the different final geometries into boxes (puts in finalElements list two Elements (two boxes))
     * Tries to divide the geometries in the middle according to x axis, to y axis and to z axis
     * and checks which division is the best according to the sum of the boxes volume (the smallest sum)
     */
    public void separateToGeometries() {
        if (this._finalElements.size() <= 2)
            return;
        // We get a separation (cut in the middle) according to x axis, to y axis, and to z axis.
        // The separation is a list of two "boxes" (2 Intersectable)
        List<Intersectable> xSeparation = separateToGeometries('x');
        List<Intersectable> ySeparation = separateToGeometries('y');
        List<Intersectable> zSeparation = separateToGeometries('z');
        // finds to each separation, what is the sum of the boxes volume (each separation divides the box, the geometries, into 2 boxes)
        double xVolume = xSeparation.get(0).getBox().volume() + xSeparation.get(1).getBox().volume();
        double yVolume = ySeparation.get(0).getBox().volume() + ySeparation.get(1).getBox().volume();
        double zVolume = zSeparation.get(0).getBox().volume() + zSeparation.get(1).getBox().volume();
        // sets the separation to be the one that the sum of the boxes volume is the smallest.
        // the way it sets it is setting the _finalElements to be the separation (the list of the 2 boxes)
        if (xVolume < yVolume)
            if (xVolume < zVolume)
                this._finalElements = xSeparation;
            else
                this._finalElements = zSeparation;
        else
            if (yVolume < zVolume)
                this._finalElements = ySeparation;
            else
                this._finalElements = zSeparation;
        // if the first box is Geometries type - has a lot of geometries in it
        if (_finalElements.get(0) instanceof Geometries)
            // set improvementBVH to be true, but as a Geometries (so it will activate separateToGeometries on the box)
            ((Geometries) _finalElements.get(0)).set_improvementBVH(true);
        else
            // any way activate improvementBVH to be true
            _finalElements.get(0).set_improvementBVH(true);
        // if the second box is Geometries type - has a lot of geometries in it
        if (_finalElements.get(1) instanceof Geometries)
            // set improvementBVH to be true, but as a Geometries (so it will activate separateToGeometries on the box)
            ((Geometries) _finalElements.get(1)).set_improvementBVH(true);
        else
            // any way activate improvementBVH to be true
            _finalElements.get(1).set_improvementBVH(true);
    }

    /**
     * Helps the separateToGeometries() function. Gets an axis letter and divides all the elements in the middle to two boxes
     * according to their value in this specific axis.
     *
     * @param axis The axis to divide by. Can be x, y or z.
     * @return a list with 2 elements - two boxes that are made after dividing the elements into two.
     * @throws IllegalArgumentException if the axis parameter is different from 'x', 'y' or 'z'.
     */
    private List<Intersectable> separateToGeometries(char axis) {
        if (axis != 'x' && axis != 'y' & axis != 'z')
            throw new IllegalArgumentException("Axes must be x, y, z");
        // get the value of the middle point according to the axis (x, y or z axis)
        double middle = ((axis == 'x' ? getBox().min.get_x() : axis == 'y'? getBox().min.get_y() : getBox().min.get_z()).get() +
                (axis == 'x'? getBox().max.get_x() : axis == 'y'? getBox().max.get_y() : getBox().max.get_z()).get()) / 2;
        List<Intersectable> small = new LinkedList<Intersectable>(); // all the geometries that their the middle value of their box is smaller than middle value of the big box (Geometry box)
        List<Intersectable> big = new LinkedList<Intersectable>(); // all the geometries that their the middle value of their box is bigger than middle value of the big box (Geometry box)
        for (Intersectable element : this._finalElements) {
            // find the geometry (element) middle value according to axis
            double elementMid = ((axis == 'x' ? element.getBox().max.get_x() : axis == 'y' ?
                    element.getBox().max.get_y() : element.getBox().max.get_z()).get() +
                    (axis == 'x' ? element.getBox().min.get_x() : axis == 'y' ?
                            element.getBox().min.get_y() : element.getBox().min.get_z()).get()) / 2d;
            // associate the geometry to the right list
            if (elementMid < middle)
                small.add(element);
            else
                if (elementMid > middle)
                    big.add(element);
                else
                    // if elementMid == middle
                    // then chose randomly to which list to associate it to
                    if (Math.random() < 0.5)
                        small.add(element);
                    else
                        big.add(element);
        }
        if (small.size() == 0 || big.size() == 0) // if none of the geometries went to small or to big, then the random did not divide it good
            return separateToGeometries(axis); // so do it again
        if (small.size() == 1) // needs only to make big list to Geometries
            return List.of(small.get(0), new Geometries(big.toArray(new Intersectable[big.size()])));
        if (big.size() == 1) // needs only to make small list to Geometries
            return List.of(big.get(0), new Geometries(small.toArray(new Intersectable[small.size()])));
        // make both lists to Geometries
        return List.of(new Geometries(small.toArray(new Intersectable[small.size()])),
                new Geometries(big.toArray(new Intersectable[big.size()])));
    }

    /*************** Admin *****************/
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<GeoPoint>();
        if (!_improvementBVH) {
            for (Intersectable element : _elements) { // check for intersections with all the elements (not in boxes)
                element.set_improvementBVH(false); // set for each geometry the _improvementBVH to be false
                List<GeoPoint> temp = element.findIntersections(ray); // list of intersection with the element
                if (temp != null) // if there are intersections
                    intersections.addAll(temp); // add them to all the intersections with Geometries
            }
        }
        else {
            // find intersection with the infinite elements (infinite elements don't have a box)
            for (Intersectable element : _infinityElements) {
                List<GeoPoint> temp = element.findIntersections(ray); // list of intersection with the infinite element
                if (temp != null) // if there are intersections
                    intersections.addAll(temp); // add them to all the intersections with Geometries
            }
            if (getBox().anyIntersections(ray)) {
                List<GeoPoint> finalIntersections = findIntersectionsTemp(ray); // list of intersections with final elements
                if (finalIntersections != null) // if there are intersections
                    intersections.addAll(finalIntersections); // add them to all the intersections with the infinite elements
            }
        }
        if (intersections.isEmpty()) // if there are intersections
            return null;
        return intersections;
    }

    @Override
    public List<GeoPoint> findIntersectionsTemp(Ray ray) {
        List<GeoPoint> intersections = new LinkedList<GeoPoint>();
        for (Intersectable element : _finalElements) {
            // temp is list of intersection with the finite element
            List<GeoPoint> temp = element.findIntersections(ray); // in separateToGeometries we had set for all the elements the _improvementBVH to be true,
                                                                  // so it will calculate first (at Intersectable class) if the ray intersects with the box.
            if (temp != null) // if there are intersections
                intersections.addAll(temp); // add them to all the intersections with Geometries
        }
        if (intersections.isEmpty()) // if there are intersections
            return null;
        return intersections;
    }

    @Override
    public void set_improvementBVH(boolean _improvementBVH) {
        this._improvementBVH = _improvementBVH;
        if (_improvementBVH)
            separateToGeometries();
    }

    /**
     * Calculates the box over all the elements according to the boxes over each element
     *
     * @return The BVHBox over all the elements
     */
    @Override
    protected BVHBox calcBox() {
        return new BVHBox(minOrMaxPoint(true), // min point
                minOrMaxPoint(false)); // max point
    }
}
