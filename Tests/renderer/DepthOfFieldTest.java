package renderer;


import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

/**
 * Unit tests for Depth Of field effect
 * @author Moriah and Shahar
 */

public class DepthOfFieldTest {
    @Test
    /**
     * Make a picture from all the effects we did, with 8 geometries, 3 light source and the feature
     */
    public void TestDepthOfField() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0), 2.6, 170));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.6, 0.5, 100, 0.4, 0), 30,
                        new Point3D(-50, 30, 100)),
                new Sphere(new Color(142, 0, 168), new Material(0.3, 0.5, 100, 0, 0.9), 30,
                        new Point3D(-15, 30, 200)),
                new Sphere(new Color(255, 0, 0), new Material(0.3, 0.5, 100, 0.3, 0), 30,
                        new Point3D(35, 30, 300)),
                new Sphere(new Color(java.awt.Color.GRAY), new Material(0.3, 0.4, 100, 0, 0.6), 20,
                        new Point3D(35, 30, 300)),
                new Sphere(new Color(java.awt.Color.GREEN), new Material(0.3, 0.5, 100, 0, 0.2), 20,
                        new Point3D(100, 30, 500)),
                new Sphere(new Color(java.awt.Color.CYAN), new Material(0.3, 0.5, 100, 0, 0.2), 35,
                        new Point3D(20, 15, 400)),
                new Tube(new Color(255, 194, 59), new Material(0.2, 0.3, 100, 0, 0.9), 20,
                        new Ray(new Point3D(35, -100, 500), new Vector(-35, 35, -100))),
                new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 100, 0.7, 0), 60,
                        new Point3D(-260, -260, 4000)),
                new Plane(new Color(java.awt.Color.GRAY), new Material(0.5, 0.3, 0, 0, 1), new Point3D(-50, 60, 100),
                        new Vector(0, 1, 0))
                );
        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-300, -300, 4000),
                        new Vector(245, 270, -3800), 0.1, 0.0004, 0.0000006),
                new SpotLight(new Color(255, 255, 255), new Point3D(-100, 0, -500),
                        new Vector(-1, -1, 2), 1, 0.0004, 0.0000006),
                new SpotLight(new Color(255, 195, 154), new Point3D(0, -200, 200),
                        new Vector(0, 1, 0), 1, 0.004, 0.00006)
        );

        ImageWriter imageWriter = new ImageWriter("DepthOfField", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
        render.set_numOfRays(100);
        render.set_depthField(true);

        render.renderImage();
        render.writeToImage();
    }
}
