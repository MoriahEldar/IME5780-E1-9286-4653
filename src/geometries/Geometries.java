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
        List<Intersectable> xSeparation = separateToGeometries('x');
        List<Intersectable> ySeparation = separateToGeometries('y');
        List<Intersectable> zSeparation = separateToGeometries('z');
        double xVolume = xSeparation.get(0).getBox().volume() + xSeparation.get(1).getBox().volume();
        double yVolume = ySeparation.get(0).getBox().volume() + ySeparation.get(1).getBox().volume();
        double zVolume = zSeparation.get(0).getBox().volume() + zSeparation.get(1).getBox().volume();
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
        if (_finalElements.get(0) instanceof Geometries)
            ((Geometries) _finalElements.get(0)).set_improvementBVH(true);
        else
            _finalElements.get(0).set_improvementBVH(true);
        if (_finalElements.get(1) instanceof Geometries)
            ((Geometries) _finalElements.get(1)).set_improvementBVH(true);
        else
            _finalElements.get(1).set_improvementBVH(true);
    }

    /**
     * Helps the separateToGeometries() function. Gets an axes letter and divides all the elements in the middle to two boxes
     * according to their value in this specific axis.
     *
     * @param axes The axes to divide by.can be x, y or z.
     * @return a list with 2 elements - two boxes that are made after dividing the elements into two.
     * @throws IllegalArgumentException if the axis parameter is different from 'x', 'y' or 'z'.
     */
    private List<Intersectable> separateToGeometries(char axes) {
        if (axes != 'x' && axes != 'y' & axes != 'z')
            throw new IllegalArgumentException("Axes must be x, y, z");
        double middle = ((axes == 'x' ? getBox().min.get_x() : axes == 'y'? getBox().min.get_y() : getBox().min.get_z()).get() +
                (axes == 'x'? getBox().max.get_x() : axes == 'y'? getBox().max.get_y() : getBox().max.get_z()).get()) / 2;
        List<Intersectable> small = new LinkedList<Intersectable>();
        List<Intersectable> big = new LinkedList<Intersectable>();
        for (Intersectable element : this._finalElements) {
            double elementMid = ((axes == 'x' ? element.getBox().max.get_x() : axes == 'y' ?
                    element.getBox().max.get_y() : element.getBox().max.get_z()).get() +
                    (axes == 'x' ? element.getBox().min.get_x() : axes == 'y' ?
                            element.getBox().min.get_y() : element.getBox().min.get_z()).get());
            if (elementMid / 2 < middle)
                small.add(element);
            else
                if (elementMid / 2 > middle)
                    big.add(element);
                else
                    if (Math.random() < 0.5)
                        small.add(element);
                    else
                        big.add(element);
        }
        if (small.size() == 0 || big.size() == 0)
            return separateToGeometries(axes);
        if (small.size() == 1)
            return List.of(small.get(0), new Geometries(big.toArray(new Intersectable[big.size()])));
        if (big.size() == 1)
            return List.of(big.get(0), new Geometries(small.toArray(new Intersectable[small.size()])));
        return List.of(new Geometries(small.toArray(new Intersectable[small.size()])),
                new Geometries(big.toArray(new Intersectable[big.size()])));
    }

    /*************** Admin *****************/
    @Override
    public List<GeoPoint> findIntersectionsTemp(Ray ray) {
        if (_improvementBVH) {
            List<GeoPoint> intersections = new LinkedList<GeoPoint>();
            boolean findFinalElementsIntersections = true;
            if (!_infinityElements.isEmpty()) { // If we did not check if the ray intersects the box
                findFinalElementsIntersections = getBox().anyIntersections(ray);
                for (Intersectable element : _infinityElements) {
                    List<GeoPoint> temp = element.findIntersections(ray);
                    if (temp != null)
                        intersections.addAll(temp);
                }
            }
            if (findFinalElementsIntersections)
                for (Intersectable element : _finalElements) {
                    List<GeoPoint> temp = element.findIntersections(ray);
                    if (temp != null)
                        intersections.addAll(temp);
                }
            if (intersections.isEmpty())
                return null;
            return intersections;
        }
        else {
            List<GeoPoint> Intersections = new LinkedList<GeoPoint>();
            for (Intersectable element : _elements) {
                element.set_improvementBVH(false);
                List<GeoPoint> temp = element.findIntersections(ray);
                if (temp != null)
                    Intersections.addAll(temp);
            }
            if (Intersections.size() == 0)
                return null;
            return Intersections;
        }
    }

    @Override
    protected boolean shouldFindIntersections(Ray ray) {
        return !_infinityElements.isEmpty() || getBox().anyIntersections(ray);
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
        return new BVHBox(minOrMaxPoint(true), minOrMaxPoint(false));
    }
}
