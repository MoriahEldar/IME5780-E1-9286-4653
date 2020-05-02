package renderer;

import org.junit.Test;
import static org.junit.Assert.*;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class RenderTest {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(100);
        scene.setBackground(new Color(75, 127, 90));
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

    /**
     * Test method for {@link renderer.Render#getClosestPoint(List<Point3D>)}
     */
    // The function getClosestPoint is private, so we temporary changed it to public in order to run the tests.
    // All the tests pass, these are the tests:
    /*
    @Test
    public void getClosestPointTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        // ============ Equivalence Partitions Tests ==============
        // TC01: A point (not the first) is the closest
        List<Point3D> points = List.of(new Point3D(2, 4, 1), new Point3D(0, 3, 1), new Point3D(0, 0, 1));
        assertEquals("Doesn't work in general", new Point3D(0, 0, 1), render.getClosestPoint(points));

        // =============== Boundary Values Tests ==================
        // TC11: The first point is the closest
        points = List.of(new Point3D(0, 0, 1), new Point3D(0, 3, 1), new Point3D(3, 5, 1));
        assertEquals("Doesn't work when first is closest", new Point3D(0, 0, 1), render.getClosestPoint(points));
        // TC12: There are no points
        points = null;
        assertEquals("Doesn't work when no points", null, render.getClosestPoint(points));
        // TC13: There are 2 equal points that are the closest
        points = List.of(new Point3D(5, 6, 1), new Point3D(0, 0, 1), new Point3D(0, 0, 1));
        assertEquals("Doesn't work when 2 points are closest", new Point3D(0, 0, 1), render.getClosestPoint(points));
        // TC14: The closest point is the camera
        points = List.of(new Point3D(0, 0, 1), Point3D.ZERO, new Point3D(3, 5, 1));
        assertEquals("Doesn't work when camera is closest", Point3D.ZERO, render.getClosestPoint(points));
        // TC15: only one point in the list
        points = List.of(new Point3D(0, 0, 1));
        assertEquals("Doesn't work when only one point", new Point3D(0, 0, 1), render.getClosestPoint(points));
    } */
}
