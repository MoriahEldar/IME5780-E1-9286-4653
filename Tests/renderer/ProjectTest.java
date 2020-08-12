package renderer;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

public class ProjectTest {
    @Test
    /**
     * Make a picture from all the effects we did, with 8 geometries, 3 light source and the feature
     */
    public void TestProject() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(20, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1), 0.2, 3.349));
        scene.setDistance(15);
        scene.setBackground(new Color(97, 158, 255));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        // teapot body
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                1.651, Point3D.ZERO));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.716/2, new Point3D(1.062*Math.cos(i/10.0), 1.062*Math.sin(i/10.0), 1.054)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.675/2, new Point3D(0.938*Math.cos(i/10.0), 0.938*Math.sin(i/10.0), 1.154)));

        // teapot lid
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.629/2, new Point3D(0.874*Math.cos(i/10.0), 0.874*Math.sin(i/10.0), 1.385)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.532/2, new Point3D(0.738*Math.cos(i/10.0), 0.738*Math.sin(i/10.0), 1.486)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.432/2, new Point3D(0.599*Math.cos(i/10.0), 0.599*Math.sin(i/10.0), 1.569)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.367/2, new Point3D(0.51*Math.cos(i/10.0), 0.51*Math.sin(i/10.0), 1.652)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.306/2, new Point3D(0.425*Math.cos(i/10.0), 0.425*Math.sin(i/10.0), 1.714)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.233/2, new Point3D(0.324*Math.cos(i/10.0), 0.324*Math.sin(i/10.0), 1.767)));
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                0.471, new Point3D(0, 0, 1.412)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.108, new Point3D(0, 0, 1.948)));

        // teapot handle
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.351/2, new Point3D(0, 0.557*Math.cos(i/10.0) + 1.565, 0.557*Math.sin(i/10.0))));

        // teapot tap
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.381, new Point3D(0.137, -1.62, 0.280)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.332, new Point3D(0.137, -1.85, 0.396)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.332, new Point3D(0.137, -1.69, 0.486 )),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.307, new Point3D(0.137, -1.96, 0.589)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.294, new Point3D(0.137, -2.02, 0.888)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                        0.294, new Point3D(0.137, -2.02, 1.161))
                );
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.342)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.406)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.456)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.513)));

        // teapot base
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.409/2, new Point3D(0.814*Math.cos(i/10.0), 0.814*Math.sin(i/10.0), -1.45)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.429/2, new Point3D(0.852*Math.cos(i/10.0), 0.852*Math.sin(i/10.0), -1.57)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.448/2, new Point3D(0.891*Math.cos(i/10.0), 0.891*Math.sin(i/10.0), -1.69)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 0.6),
                    0.288/2, new Point3D(1.1*Math.cos(i/10.0), 1.1*Math.sin(i/10.0), -1.81)));

        // table
        // top of table
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Triangle(Color.BLACK, new Material(0.2, 0.8, 100, 0.3, 0.35),
                    new Point3D(8*Math.cos(i/10.0), 8*Math.sin(i/10.0), -1.85),
                    new Point3D(8*Math.cos((i + 1)/10.0), 8*Math.sin((i + 1)/10.0), -1.85), new Point3D(0, 0, -1.85)));
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Triangle(Color.BLACK, new Material(0.2, 0.8, 100, 0.3, 0.35),
                    new Point3D(8*Math.cos(i/10.0), 8*Math.sin(i/10.0), -1.95),
                    new Point3D(8*Math.cos((i + 1)/10.0), 8*Math.sin((i + 1)/10.0), -1.95), new Point3D(0, 0, -1.95)));
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Polygon(Color.BLACK, new Material(0.2, 0.8, 100),
                    new Point3D(8*Math.cos(i/10.0), 8*Math.sin(i/10.0), -1.95),
                    new Point3D(8*Math.cos((i + 1)/10.0), 8*Math.sin((i + 1)/10.0), -1.95),
                    new Point3D(8*Math.cos((i + 1)/10.0), 8*Math.sin((i + 1)/10.0), -1.85),
                    new Point3D(8*Math.cos(i/10.0), 8*Math.sin(i/10.0), -1.85)));

        // table leg
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                1.183, new Point3D(0, 0, -2.96)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        1.048, new Point3D(0, 0, -3.24)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.859, new Point3D(0, 0, -3.73)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.787, new Point3D(0, 0, -3.98)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.72, new Point3D(0, 0, -4.16)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.605, new Point3D(0, 0, -4.44)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.494, new Point3D(0, 0, -4.75)),
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                        0.468, new Point3D(0, 0, -4.99)),
                new Sphere(Color.BLACK, new Material(0.1, 0.8, 40),
                        0.468, new Point3D(0, 0, -5.06))
                );
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                    0.298/2, new Point3D(0.465*Math.cos(i/10.0), 0.465*Math.sin(i/10.0), -5.17)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                    0.38/2, new Point3D(0.593*Math.cos(i/10.0), 0.593*Math.sin(i/10.0), -5.47)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                    0.333/2, new Point3D(0.519*Math.cos(i/10.0), 0.519*Math.sin(i/10.0), -5.27)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                    0.363/2, new Point3D(0.566*Math.cos(i/10.0), 0.566*Math.sin(i/10.0), -5.36)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
                    0.247/2, new Point3D(0.647*Math.cos(i/10.0), 0.647*Math.sin(i/10.0), -5.59)));

        // floor
        for(int i = -18; i < 18; i += 2)
            scene.addGeometries(new Polygon(new Color(95, 51, 27), new Material(0.1, 0.2, 10),
                            new Point3D(-10, i, -5.67), new Point3D(-10, i + 1.8, -5.67),
                            new Point3D(150, i + 1.8, -5.67), new Point3D(150, i , -5.67)),
                    new Polygon(Color.BLACK, new Material(0, 0, 0),
                            new Point3D(-10, i + 1.8, -5.67), new Point3D(-10, i + 2, -5.67),
                            new Point3D(150, i + 2, -5.67), new Point3D(150, i + 1.8, -5.67))
            );

        // background
        Color [] colors = {new Color(2, 126, 3), new Color(2, 126, 91),
                new Color(11, 58, 10), new Color(173, 219, 69)};
        for(int i = -5000, j = -50; i < 5000; i += 100, j++)
            scene.addGeometries(new Sphere(colors[Math.abs(j % colors.length)], new Material(0.3, 0.3, 0), 800,
                            new Point3D(-10000, i, -1100)),
                    new Sphere(colors[Math.abs(j % colors.length)], new Material(0.3, 0.3, 0), (Math.abs(i / 10.0) + Math.abs(j % (colors.length - 1)) * 5 + 795),
                            new Point3D(-10000, i, 495 + Math.abs(i / 10.0) + Math.abs(j % (colors.length - 1)) * 5)));
        scene.addGeometries(new Sphere(new Color(242, 243, 243), new Material(0, 0, 0),
                        250, new Point3D(-12000, 2400, 2700)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, 2650, 2800)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, 2400, 2950)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, 2150, 2800)),
                //
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, -1400, 3900)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, -1650, 4000)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, -1400, 4150)),
                new Sphere(new Color(242, 243, 243), new Material(0.4, 0.1, 0),
                        250, new Point3D(-12000, -1150, 4000)));

        scene.addLights(new SpotLight(new Color(255, 255, 255), new Point3D(0,4, 50),
                        new Vector(0, -4, -50), 1, 0.00004, 0.000002),
                new SpotLight(new Color(255, 200, 100), new Point3D(20, 0, 0),
                        new Vector(-20, 0, -4.44), 1, 0.000005, 0.0000001),
                new PointLight(new Color(255, 255, 0), new Point3D(-365, 0, 120),
                        1, 0.0003, 0.000002),
                new SpotLight(new Color(173, 123, 0), new Point3D(0, -30, -4),
                        new Vector(0.5, 20, -1), 1, 0.0006,0.00002));

        ImageWriter imageWriter = new ImageWriter("ProjectWithDepthOfField", 15, 15, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
        render.set_numOfRays(100);
        render.set_depthField(true);
        render.set_improvementBVH(true);

        render.renderImage();
        render.writeToImage();
    }
}
