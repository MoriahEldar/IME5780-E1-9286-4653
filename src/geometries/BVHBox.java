package geometries;

import primitives.*;

/**
 * Box class represents an imaginary box we will put over our objects in the scene
 * The box is parallel to the axis
 * The purpose of the box is to improve our picture building run time
 * The box is part of the BVH improvement
 * system
 * @author Moriah and Shahar
 */

public class BVHBox {
    // A parallel box to the axis can be represented by 2 points
    /**
     * Min point of the box. (Has min X coordinate, min Y coordinate, min Z coordinate)
     */
    Point3D min;
    /**
     * Max point of the box. (Has max X coordinate, max Y coordinate, max Z coordinate)
     */
    Point3D max;

    /**
     * Box constructor. Gets the 2 points that represent the box, and puts their copy in the class's min, max
     *
     * @param min Min point of the box. (Has min X coordinate, min Y coordinate, min Z coordinate)
     * @param max Max point of the box. (Has max X coordinate, max Y coordinate, max Z coordinate)
     */
    public BVHBox(Point3D min, Point3D max) {
        this.min = new Point3D(min);
        this.max = new Point3D(max);
    }

    /**
     * Min getter
     *
     * @return copy of min point of the box value. (Has min X coordinate, min Y coordinate, min Z coordinate)
     */
    public Point3D getMin() {
        return new Point3D(min);
    }

    /**
     * Max getter
     *
     * @return copy of max point of the box value. (Has max X coordinate, max Y coordinate, max Z coordinate)
     */
    public Point3D getMax() {
        return new Point3D(max);
    }

    /**
     * Checks if a ray intersects the box
     * The idea of how to build this function is taken from:
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection
     *
     * @param ray The ray the we need to check
     * @return true if there are any intersections, false if none.
     */
    public boolean anyIntersections(Ray ray) {
        double tMin, tMax, tYMin, tYMax, tZMin, tZMax;
        Point3D invDir = new Point3D(1 / ray.get_direction().get_point().get_x().get(),
                1 / ray.get_direction().get_point().get_y().get(),
                1 / ray.get_direction().get_point().get_z().get());
        double invDirX = invDir.get_x().get();
        double invDirY = invDir.get_y().get();

        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        Point3D rayStartPoint = ray.get_startPoint();
        tmin = ((invDirX < 0 ? max : min).get_x().get() - rayStartPoint.get_x().get()) * invDirX;
        tmax = ((invDirX < 0 ? min : max).get_x().get() - rayStartPoint.get_x().get()) * invDirX;
        tymin = ((invDirY < 0 ? max : min).get_y().get() - rayStartPoint.get_y().get()) * invDirY;
        tymax = ((invDirY < 0 ? min : max).get_y().get() - rayStartPoint.get_y().get()) * invDirY;

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;
        if (tymax < tmax)
            tmax = tymax;

        double invDirZ = invDir.get_z().get();
        tzmin = ((invDirZ < 0 ? max : min).get_z().get() - rayStartPoint.get_z().get()) * invDirZ;
        tzmax = ((invDirZ < 0 ? min : max).get_z().get() - rayStartPoint.get_z().get()) * invDirZ;

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;
        if (tzmax < tmax)
            tmax = tzmax;

        return !(tmin < 0) || !(tmax < 0);
    }

    /**
     * Calculates the box's volume
     *
     * @return The volume of the box
     */
    public double volume() {
        return (max.get_x().get() - min.get_x().get()) * (max.get_y().get() - min.get_y().get()) * (max.get_z().get() - min.get_z().get());
    }
}
