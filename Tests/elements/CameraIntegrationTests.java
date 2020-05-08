package elements;

import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

/**
 * Unit tests for integration between creating rays from camera and between calculating intersections of the rays with geometries shapes
 * @author Moriah and Shahar
 */
public class CameraIntegrationTests {
    /**
     * Test method in order to check integration between creating rays from camera to view plane
     * and between calculating intersections of the rays with sphere
     */
    @Test
    public void testCameraIntegrationSphere() {
        // TC01: Sphere is after the view plane, the continuation of vector vTo crosses the center point of the sphere
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sphere = new Sphere(1, new Point3D(0, 0, 3));
        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("01: Incorrect number of points when sphere after view plane", 2, count);
        // TC02: View plane is in the sphere
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(2.5, new Point3D(0, 0, 2.5));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("02: Incorrect number when view plane is in sphere", 18, count);
        // TC03: A part of view plane is in the sphere
        sphere = new Sphere(2, new Point3D(0, 0, 2));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("03: Incorrect number when part of view plane is in sphere", 10, count);
        // TC04: Camera and view plane is in the sphere
        sphere = new Sphere(4, new Point3D(0, 0, 1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("04: Incorrect number when camera and view plane is in sphere", 9, count);
        // TC05: The sphere is behind the camera
        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        sphere = new Sphere(0.5, new Point3D(0, 0, -1));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("05: Incorrect number when sphere is behind the camera", 0, count);
    }

    /**
     * Test method in order to check integration between creating rays from camera to view plane
     * and between calculating intersections of the rays with plane
     */
    @Test
    public void testCameraIntegrationPlane() {
        // TC01: Plane is after the view plane, and parallel to it
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Plane plane = new Plane(new Point3D(0, 0, 3), camera.get_vTo());
        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("01: Incorrect number of points when plane parallel to view plane", 9, count);
        // TC02: All the rays cut the plane, not parallel
        plane = new Plane(new Point3D(1.5, -1.5, 3), new Vector(0, 2, 3));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("02: Incorrect number of points when all the rays cut the plane", 9, count);
        // TC03: The plane is parallel to one of the planes that are created from connecting 3 of the points in the middle of 3 pixels in the same row, and the camera point.
        // All the other rays (except fo the one that connect the camera with these point) cut the plane
        // Only 6 rays cut the plane
        plane = new Plane(new Point3D(1.5, 1.5, 1), new Vector(0, -1.5, -1.125));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("03: Incorrect number of points when 6 rays cut the plane", 6, count);
        // TC04: Plane is before the camera, and parallel to it
        plane = new Plane(new Point3D(0, 0, -3), camera.get_vTo());
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("04: Incorrect number of points when plane behind camera", 0, count);
    }

    /**
     * Test method in order to check integration between creating rays from camera to view plane
     * and between calculating intersections of the rays with triangle
     */
    @Test
    public void testCameraIntegrationTriangle() {
        // TC01: Triangle is smaller then the view plane, and parallel to it
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Triangle triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = triangle.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("01: Incorrect number of points when triangle is smaller then the view plane", 1, count);
        // TC02: Triangle higher then the view plane, goes out of view plan's boundaries, and parallel to it.
        triangle = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                List<GeoPoint> intersections = triangle.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
                if (intersections != null)
                    count += intersections.size();
            }
        assertEquals("02: Incorrect number of points when triangle goes out of view plan's boundaries", 2, count);
    }
}
