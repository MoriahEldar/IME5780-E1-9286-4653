package geometries;

import primitives.Color;
import primitives.Material;
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
     * Sets triangle's color (_emission) to black
     * Sets the material to (0, 0, 0)
     *
     * @param p1 point p1
     * @param p2 point p2
     * @param p3 point p3
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3);
    }

    /**
     * Triangle constructor receiving a color which is the triangle's color, and 3 3D points that are the vertices of the triangle
     * Sets the material to (0, 0, 0)
     *
     * @param _emission triangle's color
     * @param p1 point p1
     * @param p2 point p2
     * @param p3 point p3
     */
    public Triangle(Color _emission, Point3D p1, Point3D p2, Point3D p3) {
        this(_emission, new Material(0 ,0, 0), p1, p2, p3);
    }

    /**
     * Triangle constructor receiving a color which is the triangle's color,
     * a material which is the material that the triangle is made from,
     * and 3 3D points that are the vertices of the triangle
     *
     * @param _emission triangle's color
     * @param _material material, the triangle material
     * @param p1 point p1
     * @param p2 point p2
     * @param p3 point p3
     */
    public Triangle(Color _emission, Material _material, Point3D p1, Point3D p2, Point3D p3) {
        super(_emission, _material, p1, p2, p3);
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