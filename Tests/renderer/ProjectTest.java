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
        scene.setCamera(new Camera(new Point3D(0, -25, -25), new Vector(0, 0, 1), new Vector(0, -1, 0), 2.6, 170));
        scene.setDistance(50);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        for (int i = 0; i < 3600; i++)
            scene.addGeometries(new Sphere(new Color(java.awt.Color.CYAN), new Material(0.2, 0.3, 100),
                    2, new Point3D(5*Math.cos(i/10.0), 0, 5*Math.sin(i/10.0))));
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
