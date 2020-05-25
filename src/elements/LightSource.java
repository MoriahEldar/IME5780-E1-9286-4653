package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * LightSource class represents all the light sources in the scene
 * (That are from a real source (there is an importance to the lite location), not like ambient light)
 * system
 *
 * @author Moriah and Shahar
 */

public interface LightSource {
    /**
     * Gets a point in the scene, and calculates the light intensity there
     *
     * @param p a point in the scene
     * @return a color that is the light intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * Returns a vector direction from the light source to the given point
     *
     * @param p the given point in the scene
     * @return a vector direction from the light source to the given point
     */
    public Vector getL(Point3D p);

    /**
     * Calculates the distance of the point from the light source
     *
     * @param point the given point in the scene
     * @return the distance from the light source to the point
     */
    double getDistance(Point3D point);
}
