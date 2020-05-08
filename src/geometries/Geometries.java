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
        this._elements = new ArrayList<Intersectable>();
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
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> Intersections = new ArrayList<GeoPoint>();
        for (int i = 0; i < _elements.size(); i++) {
            List<GeoPoint> temp = _elements.get(i).findIntersections(ray);
            if (temp != null)
                Intersections.addAll(temp);
        }
        if (Intersections.size() == 0)
            return null;
        return Intersections;
    }
}
