package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Tube class represents a 3D tube (an infinity cylinder) in 3D Cartesian coordinate
 * system
 *
 * @author Moriah and Shahar
 */

public class Tube extends RadialGeometry {
    /**
     * A Ray that represents the center line of the Tube
     */
    protected Ray _axisRay;

    /**
     * Tube constructor receiving radius and a Ray which is the center line of the sphere
     * Sets tube's color (_emission) to black
     * Sets the material to (0, 0, 0)
     *
     * @param _radius double
     * @param _axisRay the Ray
     */
    public Tube(double _radius, Ray _axisRay) {
        this(Color.BLACK, _radius, _axisRay);
    }

    /**
     * Tube constructor receiving a color which is the tube's color, a radius and a Ray which is the center line of the sphere
     * Sets the material to (0, 0, 0)
     *
     * @param _emission the color
     * @param _radius double
     * @param _axisRay the Ray
     */
    public Tube(Color _emission, double _radius, Ray _axisRay) {
        this(_emission, new Material(0, 0, 0), _radius, _axisRay);
    }

    /**
     * Tube constructor receiving a color which is the tube's color,
     * a material which is the material that the tube is made from,
     * a radius and a Ray which is the center line of the sphere
     *
     * @param _emission the color
     * @param _material material, the tube material
     * @param _radius double
     * @param _axisRay the Ray
     */
    public Tube(Color _emission, Material _material, double _radius, Ray _axisRay) {
        super(_emission, _material, _radius);
        this._axisRay = new Ray(_axisRay);
    }

    /**
     * _axisRay value getter
     *
     * @return a new Ray with _axisRay value
     */
    public Ray get_axisRay() {
        return new Ray(_axisRay);
    }

    /*************** Admin *****************/
    @Override
    public Vector getNormal(Point3D point) {
        double t = get_axisRay().get_direction().dotProduct(point.subtract(get_axisRay().get_startPoint()));
        Point3D O = get_axisRay().get_startPoint().add(get_axisRay().get_direction().scale(t));
        return point.subtract(O).normalize();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        // ray: (a, b, c) + t(x, y, z)
        // _axisRay: (a1, b1, c1) + t1(x1, y1, z1)
        Point3D dots;
        try {
            // dots = (a-a1, b-b1, c-c1)
            dots = ray.get_startPoint().subtract(_axisRay.get_startPoint()).get_point();
        }
        catch (IllegalArgumentException e)
        {
            dots = Point3D.ZERO;
        }
        // What we did:
        // We took a general point in each of the rays.
        // Then we calculated the general vector that we get from thous points.
        // We used two equations:
        // 1. We know that the intersection points are the ones that maintains that the vector length is radius
        // (the distance between the dot on the ray to the one on the axis ray is the radius, what means the dot is on the ray  and on the tube - intersection point)
        // So we made this equation.
        // 2. we know that the vector is orthogonal to the axis ray,
        // so we did an equation which is the dot product between the vector and the axis ray vector equals 0
        // The variables that are here are the promotes and constants after simplifying the equations

        // mult = x*x1 + y*y1 + z*z1
        double mult = ray.get_direction().dotProduct(_axisRay.get_direction());
        // The calculations to ge to each of the variables:
        // constant (scalar) in eq1
        double scl = dots.get_x().get() * dots.get_x().get() +
                dots.get_y().get() * dots.get_y().get() +
                dots.get_z().get() * dots.get_z().get() - _radius * _radius;
        // promote of t in eq1
        double sclt = 2 * (dots.get_x().get() * ray.get_direction().get_point().get_x().get() +
                dots.get_y().get() * ray.get_direction().get_point().get_y().get() +
                dots.get_z().get() * ray.get_direction().get_point().get_z().get());
        // promote of t1 in eq1
        double sclt1 = -2 * (dots.get_x().get() * _axisRay.get_direction().get_point().get_x().get() +
                dots.get_y().get() * _axisRay.get_direction().get_point().get_y().get() +
                dots.get_z().get() * _axisRay.get_direction().get_point().get_z().get());
        // promote of t*t1 in eq1
        double scltt1 = -2 * mult;
        // constant (scalar) in eq2
        double scl2 = _axisRay.get_direction().get_point().get_x().get() * dots.get_x().get() +
                _axisRay.get_direction().get_point().get_y().get() * dots.get_y().get() +
                _axisRay.get_direction().get_point().get_z().get() * dots.get_z().get();
        // promote of t in eq1
        double sclt2 = mult;
        // After, we did t1 = scl2 + sclt2 * t from eq2 and we set it in eq1
        // After simplifying the equation we got: at^2 + bt + c = 0
        // The calculations to ge to each of the variables:
        double a = alignZero(1 + scltt1 * sclt2 + sclt2 * sclt2);
        double b = alignZero(sclt + sclt1 * sclt2 + scltt1 * scl2 + 2 * scl2 * sclt2);
        double c = alignZero(scl + sclt1 * scl2 + scl2 * scl2);
        // And we solve the equation
        if (a == 0) {
            if (b == 0)
                return null;
            else {
                double t = alignZero((-c) / b);
                if (t <= 0)
                    return null;
                return List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }
        // Whats under the sqrt in the quadratic equation
        double det = b * b - 4 * a * c;
        if (det < 0)
            return null;
        if (det == 0) {
            double t = alignZero((-b) / (2 * a));
            if (t <= 0)
                return null;
            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        double t_1 = alignZero((-b + Math.sqrt(det)) / (2 * a));
        double t_2 = alignZero((-b - Math.sqrt(det)) / (2 * a));
        if (t_1 <= 0) {
            if (t_2 <= 0)
                return null;
            return List.of(new GeoPoint(this, ray.getPoint(t_2)));
        }
        if (t_2 <= 0)
            return List.of(new GeoPoint(this, ray.getPoint(t_1)));
        return List.of(new GeoPoint(this, ray.getPoint(t_1)), new GeoPoint(this, ray.getPoint(t_2)));
    }

    /**
     * Tube is an infinite geometry, therefore it has no box that can capture it, so the box is null
     *
     * @return null, because it's an infinite shape
     */
    @Override
    protected BVHBox calcBox() {
        return null;
    }

    @Override
    public String toString() {
        return "Tube {" +
                "axis Ray = " + _axisRay +
                ", R = " + _radius +
                '}';
    }
}
