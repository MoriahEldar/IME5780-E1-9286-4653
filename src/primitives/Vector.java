package primitives;

/**
 * Class Vector is the basic class representing a line between a 3-Dimensional point and the ZERO point, with length and direction of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Moriah and Shahar
 */

public class Vector {
    /**
     * The end point of the vector
     */
    Point3D _point;

    /**
     * Vector constructor receiving point coordinates
     *
     * @param x point coordinate x, y point coordinate y, z point coordinate z
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D temp = new Point3D(x, y ,z);
        if(temp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero");
        this._point = temp;
    }

    /**
     * Vector constructor receiving point coordinates values
     *
     * @param x point coordinate x value, y point coordinate y value, z point coordinate z value
     */
    public Vector(double x, double y, double z) {
        Point3D temp = new Point3D(x, y ,z);
        if(temp.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero");
        this._point = temp;
    }

    /**
     * Vector constructor receiving point
     *
     * @param _point
     */
    public Vector(Point3D _point) {
        if(_point.equals(Point3D.ZERO))
            throw new IllegalArgumentException("Vector cannot be zero");
        this._point = new Point3D(_point);
    }

    /**
     * Copy constructor for Vector
     *
     * @param other
     */
    public Vector(Vector other) {
        this._point = new Point3D(other._point);
    }

    /**
     * _point value getter
     *
     * @return point value
     */
    public Point3D get_point() {
        return new Point3D(_point);
    }

    /**
     * Makes a new vector that its point is the subtraction between the received vector point and this vector point
     *
     * @param vec, the second vector
     * @return a new vector with the new point
     */
    public Vector subtract(Vector vec) {
        return this._point.subtract(vec._point);
    }

    /**
     * Makes a new vector that its point is the addition between the received vector point and this vector point
     *
     * @param vec, the second vector
     * @return a new vector with the new point
     */
    public Vector add(Vector vec) {
        return new Vector(this._point.add(vec));
    }

    /**
     * Multiplies the vector with a scalar
     *
     * @param num, the scalar value
     * @return a new vector the is multiplied with the scale
     */
    public Vector scale(double num) {
        return new Vector(num * this._point.get_x().get(), num * this._point.get_y().get(), num * this._point.get_z().get());
    }

    /**
     * Does a dot product between a received vector and this vector
     *
     * @param vec, the second vector
     * @return double, the value of the dot production between the vectors
     */
    public double dotProduct(Vector vec) {
        return this._point.get_x().get() * vec._point.get_x().get() +
                this._point.get_y().get() * vec._point.get_y().get() +
                this._point.get_z().get() * vec._point.get_z().get();
    }

    /**
     * Does a cross product between a received vector and this vector
     *
     * @param vec, the second vector
     * @return a new vector, which is the outcome of the cross production between the vectors
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(this._point.get_y().get() * vec._point.get_z().get() - this._point.get_z().get() * vec._point.get_y().get(),
                this._point.get_z().get() * vec._point.get_x().get() - this._point.get_x().get() * vec._point.get_z().get(),
                this._point.get_x().get() * vec._point.get_y().get() - this._point.get_y().get() * vec._point.get_x().get());
    }

    /**
     * Calculates the squared length of this vector
     *
     * @return a double that is the squared length
     */
    public double lengthSquared() {
        return this._point.distanceSquared(Point3D.ZERO);
    }

    /**
     * Calculates the length of this vector
     *
     * @return a double that is the length
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes (keeps the direction, changes the length to one) this vector (changes this vector)
     *
     * @return this vector normalized
     */
    public Vector normalize() {
        this._point = scale(1/this.length())._point;
        return this;
    }

    /**
     * Normalizes (keeps the direction, changes the length to one) this vector into a new vector (does not change this vector)
     *
     * @return a new vector which is this vector normalized
     */
    public Vector normalized() {
        return new Vector(this).normalize();
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return _point.equals(vector._point);
    }

    @Override
    public String toString() {
        return _point.toString();
    }
}
