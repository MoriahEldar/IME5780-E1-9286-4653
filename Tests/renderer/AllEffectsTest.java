package renderer;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

/**
 * Unit tests for renderer.Render class
 * @author Moriah and Shahar
 */

public class AllEffectsTest {
    @Test
    /**
     * Make a picture from all the effects we did, with 3 geometries
     */
    public void TestAllEffects() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0), 0.3, 10));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 60)),
                new Sphere(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.5, 100, 0, 0.8), 25, new Point3D(0, -40, 60)),
                new Triangle(new Color(java.awt.Color.PINK), new Material(0, 0, 0, 0.3, 0.7),
                        new Point3D(-80, -60, 150), new Point3D(60, 40, 20), new Point3D(70, 20, 150))
                );

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("allEffects", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();;
        render.set_numOfRays(100);
        render.set_depthField(true);


        render.renderImage();
        render.writeToImage();
    }

    /**
     * Bonus - 10 geometries
     */
    @Test
    public void TestAllEffectsBonus() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, -7000, 300), new Vector(0, 1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Plane(new Color(43, 80, 54), new Material(0.3, 0.2, 0), Point3D.ZERO,
                        new Vector(0, 0, 1)),
                new Tube(new Color(106, 43, 19), new Material(0.5, 0, 100), 45,
                        new Ray(new Point3D(-230, 350,0), new Vector(0, 0, 1))),
                new Polygon(new Color(java.awt.Color.CYAN), new Material(0.9, 0.6, 100, 0.6, 0.6),
                        new Point3D(-120, -1000, 5), new Point3D(50, -1500, 5), new Point3D(300, -1250, 5),
                        new Point3D(450, 0, 5), new Point3D(430, 1300, 5), new Point3D(-100, 1350, 5),
                        new Point3D(-200, -50, 5)),
                new Sphere(new Color(59, 129, 42), new Material(0.6, 0, 0), 200,
                        new Point3D(-230, 300, 500))
                );
        scene.addLights(new SpotLight(new Color(java.awt.Color.GREEN), new Point3D(-500, 1000, 500),
                        new Vector(1, -1, -0.5), 1, 0.0004, 0.0000006)
                );

        ImageWriter imageWriter = new ImageWriter("allEffectsBonus", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
