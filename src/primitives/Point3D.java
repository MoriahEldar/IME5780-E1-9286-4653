package primitives;

/**
 * Class Point3D is the basic class representing a 3-Dimensional point of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Moriah and Shahar
 */

public class Point3D {
    /**
     * The 3 Coordinates that make a 3D point.
     * _x is the x axis
     * _y is the y axis
     * _z is the z axis
     */
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    final static public Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * Point3D constructor receiving 3 coordinates
     *
     * @param _x coordinate x
     * @param _y coordinate y
     * @param _z coordinate z
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Point3D constructor receiving 3 coordinate values
     *
     * @param _x coordinate x value
     * @param _y coordinate y value
     * @param _z coordinate z value
     */
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * Copy constructor for Point3D
     *
     * @param other
     */
    public Point3D(Point3D other) {
        this._x = new Coordinate(other._x);
        this._y = new Coordinate(other._y);
        this._z = new Coordinate(other._z);
    }

    /**
     * _x value getter
     *
     * @return _x value
     */
    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    /**
     * _y value getter
     *
     * @return _y value
     */
    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    /**
     * _z value getter
     *
     * @return _z value
     */
    public Coordinate get_z() {
        return new Coordinate(_z);
    }

    /**
     * Gets another 3D point and returns the vector from the other point to this point
     *
     * @param point3D a 3D point
     * @return a Vector
     */
    public Vector subtract(Point3D point3D) {
        return new Vector(this._x.get() - point3D._x.get(), this._y.get() - point3D._y.get(), this._z.get() - point3D._z.get());
    }

    /**
     * Adds a vector to this point
     *
     * @param vec a Vector
     * @return a 3D point
     */
    public Point3D add(Vector vec) {
        return new Point3D(vec.get_point()._x.get() + this._x.get(), vec.get_point()._y.get() + this._y.get(), vec.get_point()._z.get() + this._z.get());
    }

    /**
     * Calculates the squared distance between 2 3D points
     *
     * @param point3D a 3D point
     * @return a double that represents the distance squared
     */
    public double distanceSquared(Point3D point3D) {
        double distanceX = this._x.get() - point3D._x.get();
        double distanceY = this._y.get() - point3D._y.get();
        double distanceZ = this._z.get() - point3D._z.get();
        return distanceX*distanceX + distanceY*distanceY + distanceZ*distanceZ;
    }

    /**
     * Calculates the distance between 2 3D points
     *
     * @param point3D a 3D point
     * @return a double that represents the distance
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x +
                ", " + _y +
                ", " + _z +
                ')';
    }
}
