package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Geometries class represents a bundle of geometric elements.
 * system
 *
 * @author Moriah and Shahar
 */

public class Geometries implements Intersectable {
    /**
     * A list of elements in the scene
     */
    private List<Intersectable> _elements;

    /**
     * Geometries default constructor. Puts in elements an empty list
     */
    public Geometries() {
        this._elements = new LinkedList<Intersectable>();
    }

    /**
     * Geometries constructor receiving a number of geometry elements and puts it in _elements
     *
     * @param geometries - list of geometry elements
     */
    public Geometries(Intersectable... geometries) {
        this._elements = List.of(geometries);
    }

    /**
     * add, receiving a number of geometry elements and adds them to _elements
     *
     * @param geometries - list of geometry elements
     */
    public void add(Intersectable... geometries) {
        this._elements.addAll(List.of(geometries));
    }

    /*************** Admin *****************/
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> Intersections = new ArrayList<Point3D>();
        for (Intersectable element : _elements) {
            List<Point3D> temp = element.findIntersections(ray);
            if (temp != null)
                Intersections.addAll(temp);
        }
        if (Intersections.size() == 0)
            return null;
        return Intersections;
    }
}
