package primitives;

/**
 * Class Ray is the basic class representing a line from a 3-Dimensional point to the infinity, with a start point and direction of Euclidean geometry in Cartesian
 * 3-Dimensional coordinate system.
 * @author Moriah and Shahar
 */

public class Ray {
    /**
     * The start point of the ray
     */
    Point3D _startPoint;
    /**
     * A normalized vector the represents the ray direction
     */
    Vector _direction;

    /**
     * Ray constructor receiving a start point and the direction
     *
     * @param _startPoint of the ray
     * @param _direction vector of the ray
     */
    public Ray(Point3D _startPoint, Vector _direction) {
        this._startPoint = new Point3D(_startPoint);
        this._direction = new Vector(_direction.normalized());
    }

    /**
     * Ray constructor receiving a start point and a point on the ray
     *
     * @param _startPoint a point that is the point we will save on the ray
     * @param pointOnTheRay a different point on the ray
     * @throws IllegalArgumentException if the points are the same point
     */
    public Ray(Point3D _startPoint, Point3D pointOnTheRay) {
        this._startPoint = new Point3D(_startPoint);
        try {
            this._direction = pointOnTheRay.subtract(_startPoint).normalize();
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Cannot get the same point as a direction");
        }
    }

    /**
     * Copy constructor for Ray
     *
     * @param other
     */
    public Ray(Ray other) {
        this._startPoint = new Point3D(other._startPoint);
        this._direction = new Vector(other._direction);
    }

    /**
     * _startPoint value getter
     *
     * @return a point that is _startPoint
     */
    public Point3D get_startPoint() {
        return new Point3D(_startPoint);
    }

    /**
     * _direction value getter
     *
     * @return a vector that is _direction
     */
    public Vector get_direction() {
        return new Vector(_direction);
    }

    /**
     * Gets a scalar and returns the point on the ray according to the scalar (P0 + tv)
     *
     * @param t the scalar
     * @return The point on the ray
     */
    public Point3D getPoint(double t) {
        if (t < 0)
            throw new IllegalArgumentException("t (the scalar) cannot be negative");
        try
        {
            return _startPoint.add(_direction.scale(t));
        }
        catch (IllegalArgumentException e) {
            return get_startPoint();
        }
    }

    /*************** Admin *****************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _startPoint.equals(ray._startPoint) &&
                _direction.equals(ray._direction);
    }

    @Override
    public String toString() {
        return "{" +
                _startPoint.toString() +
                " + t" + _direction.toString() +
                '}';
    }
}
