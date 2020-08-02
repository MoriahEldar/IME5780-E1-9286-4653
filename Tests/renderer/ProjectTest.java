package renderer;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

public class ProjectTest {
    @Test
    /**
     * Make a picture from all the effects we did, with 8 geometries, 3 light source and the feature
     */
    public void TestProject() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(30, 0, 0), new Vector(-1, 0, 0), new Vector(0, 0, 1), 2.6, 170));
        scene.setDistance(100);
        scene.setBackground(new Color(java.awt.Color.GRAY));
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        // teapot body
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                1.651, Point3D.ZERO));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.716/2, new Point3D(1.062*Math.cos(i/10.0), 1.062*Math.sin(i/10.0), 1.054)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.675/2, new Point3D(0.938*Math.cos(i/10.0), 0.938*Math.sin(i/10.0), 1.154)));

        // teapot lid
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.629/2, new Point3D(0.874*Math.cos(i/10.0), 0.874*Math.sin(i/10.0), 1.385)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.532/2, new Point3D(0.738*Math.cos(i/10.0), 0.738*Math.sin(i/10.0), 1.486)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.432/2, new Point3D(0.599*Math.cos(i/10.0), 0.599*Math.sin(i/10.0), 1.569)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.367/2, new Point3D(0.51*Math.cos(i/10.0), 0.51*Math.sin(i/10.0), 1.652)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.306/2, new Point3D(0.425*Math.cos(i/10.0), 0.425*Math.sin(i/10.0), 1.714)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.233/2, new Point3D(0.324*Math.cos(i/10.0), 0.324*Math.sin(i/10.0), 1.767)));
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                0.471, new Point3D(0, 0, 1.412)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.108, new Point3D(0, 0, 1.948)));

        // teapot handle
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.351/2, new Point3D(0, 0.557*Math.cos(i/10.0) + 1.565, 0.557*Math.sin(i/10.0))));

        // teapot tap
        scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.381, new Point3D(0.137, -1.62, 0.280)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.332, new Point3D(0.137, -1.85, 0.396)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.332, new Point3D(0.137, -1.69, 0.486 )),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.307, new Point3D(0.137, -1.96, 0.589)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.294, new Point3D(0.137, -2.02, 0.888)),
                new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                        0.294, new Point3D(0.137, -2.02, 1.161))
                );
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.342)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.406)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.456)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.19/2, new Point3D(0.19*Math.cos(i/10.0) + 0.137, 0.19*Math.sin(i/10.0) - 2.02, 1.513)));

        // teapot base
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.409/2, new Point3D(0.814*Math.cos(i/10.0), 0.814*Math.sin(i/10.0), -1.45)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.429/2, new Point3D(0.852*Math.cos(i/10.0), 0.852*Math.sin(i/10.0), -1.57)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.448/2, new Point3D(0.891*Math.cos(i/10.0), 0.891*Math.sin(i/10.0), -1.69)));
        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(Color.BLACK, new Material(0, 1, 100, 0, 1),
                    0.288/2, new Point3D(1.1*Math.cos(i/10.0), 1.1*Math.sin(i/10.0), -1.81)));

        // table
        // top of table
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Triangle(Color.BLACK, new Material(0.2, 0.8, 100, 0.3, 0.3),
                    new Point3D(8*Math.cos(i/10.0), 8*Math.sin(i/10.0), -1.85),
                    new Point3D(8*Math.cos((i + 1)/10.0), 8*Math.sin((i + 1)/10.0), -1.85), new Point3D(0, 0, -1.85)));
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Triangle(Color.BLACK, new Material(0.2, 0.8, 100, 0.3, 0.3),
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
                new Sphere(Color.BLACK, new Material(0.4, 0.8, 40),
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
        for (int i = 0; i < 3599; i++)
            scene.addGeometries(new Triangle(Color.BLACK, new Material(0.4, 0.8, 40),
                    new Point3D(2*Math.cos(i/10.0), 2*Math.sin(i/10.0), -5.66),
                    new Point3D(2*Math.cos((i + 1)/10.0), 2*Math.sin((i + 1)/10.0), -5.66), new Point3D(0, 0, -5.66)));



        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-300, -300, 4000),
                        new Vector(245, 270, -3800), 0.1, 0.0004, 0.0000006),
                new SpotLight(new Color(255, 255, 255), new Point3D(-100, 0, -500),
                        new Vector(-1, -1, 2), 1, 0.0004, 0.0000006),
                new SpotLight(new Color(255, 195, 154), new Point3D(0, -200, 200),
                        new Vector(0, 1, 0), 1, 0.004, 0.00006)
        );

        ImageWriter imageWriter = new ImageWriter("Project", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
        render.set_numOfRays(100);
        //render.set_depthField(true);

        render.renderImage();
        render.writeToImage();
    }
}
