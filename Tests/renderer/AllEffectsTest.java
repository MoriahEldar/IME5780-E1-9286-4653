package renderer;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

public class AllEffectsTest {
    @Test
    /**
     * Make a picture from all the effects we did, with 3 geometries
     */
    public void TestAllEffects() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
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
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Bonus - 10 geometries
     */
    @Test
    public void TestAllEffectsBonus() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 7000, 300), new Vector(0, -1, 0), new Vector(0, 0, 1)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLUE));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0.9, 0),
                        new Point3D(100, 100, 0), new Point3D(100, -100, 0),
                        new Point3D(-100, -100, 0), new Point3D(-100, 100, 0)),
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0, 0.2),
                        new Point3D(100, 100, 300), new Point3D(100, -100, 300),
                        new Point3D(-100, -100, 300), new Point3D(-100, 100, 300)),
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0.9, 0),
                        new Point3D(100, 100, 0), new Point3D(100, -100, 0),
                        new Point3D(100, -100, 300), new Point3D(100, 100, 300)),
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0, 0.2),
                        new Point3D(-100, -100, 0), new Point3D(100, -100, 0),
                        new Point3D(100, -100, 300), new Point3D(-100, -100, 300)),
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0.9, 0),
                        new Point3D(100, 100, 0), new Point3D(-100, 100, 0),
                        new Point3D(-100, 100, 300), new Point3D(100, 100, 300)),
                new Polygon(new Color(java.awt.Color.YELLOW), new Material(0.5, 0.3, 0, 0.9, 0),
                        new Point3D(-100, -100, 0), new Point3D(-100, 100, 0),
                        new Point3D(-100, 100, 300), new Point3D(-100, -100, 300)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.5, 0.3, 0, 0, 0.1),
                        new Point3D(-100, -100, 300), new Point3D(-100, 100, 300),
                        new Point3D(0, 0, 500)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.5, 0.3, 0, 0, 0.1),
                        new Point3D(100, -100, 300), new Point3D(-100, -100, 300),
                        new Point3D(0, 0, 500)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.5, 0.3, 0, 0, 0.1),
                        new Point3D(100, 100, 300), new Point3D(100, -100, 300),
                        new Point3D(0, 0, 500)),
                new Triangle(new Color(java.awt.Color.RED), new Material(0.5, 0.3, 0, 0, 0.1),
                        new Point3D(100, 100, 300), new Point3D(-100, -100, 300),
                        new Point3D(0, 0, 500)),
                new Plane(new Color(Color.BLACK), new Material(0.5, 0.3, 0),
                        Point3D.ZERO, new Vector(0, 0, 1)),
                new Tube(new Color(java.awt.Color.GRAY), new Material(0.2, 0.5, 100, 0, 0.9),
                        15, new Ray(new Point3D(50, 0, 0), new Vector(0, 0, 1))),
                new Sphere(new Color(java.awt.Color.CYAN), new Material(0.2, 0.2, 30, 0, 0.4),
                        60, new Point3D(150, -100, 400)),
                new Sphere(new Color(java.awt.Color.CYAN), new Material(0.2, 0.2, 30, 0, 0.4),
                        60, new Point3D(80, -100, 400))
        );
        scene.addLights(new SpotLight(new Color(java.awt.Color.GREEN), new Point3D(0, -400, 500), new Vector(0, 11, -3.48), 1,
                0.0004, 0.0000006),
                new SpotLight(new Color(java.awt.Color.GREEN), new Point3D(0, 400, 500), new Vector(0, -11, -3.48), 1,
                        0.0004, 0.0000006)
                );

        ImageWriter imageWriter = new ImageWriter("allEffectsBonus", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
