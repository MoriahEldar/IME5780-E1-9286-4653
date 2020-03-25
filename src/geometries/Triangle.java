package geometries;

import primitives.Point3D;
import java.util.List;

/**
 * Triangle class represents two-dimensional triangle in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Triangle extends Polygon {

    /**
     * Triangle constructor receiving 3 3D points that are the vertices of the triangle
     *
     * @param p1 point p1, p2 point p2, p3 point p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    /**
     * _vertices value getter
     *
     * @return a new list of the vertices
     */
    public List<Point3D> get_vertices() {
        return List.of(new Point3D(_vertices.get(0)), new Point3D(_vertices.get(1)), new Point3D(_vertices.get(2)));
    }

    /*************** Admin *****************/
    @Override
    public String toString() {
        return "{" +
                _vertices.get(0) +
                ", " + _vertices.get(1) +
                ", " + _vertices.get(2) +
                '}';
    }
}