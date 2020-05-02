package renderer;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Unit tests for renderer.ImageWriter class
 * @author Moriah and Shahar
 */

public class ImageWriterTest {
    /**
     * Test method for {@link renderer.ImageWriter#writePixel(int, int, java.awt.Color)}
     */
    @Test
    public void writePixelTest() {
        // T01: build an image with no renderer
        // A grid with 10X16 squares
        ImageWriter imageWriter = new ImageWriter("Test01", 1600, 1000, 800, 500);
        for (int i = 0; i < 500; i++)
            for (int j = 0; j < 800; j++) {
                if (i % 50 == 0 || j % 50 == 0)
                    imageWriter.writePixel(j, i, Color.YELLOW);
                else
                    imageWriter.writePixel(j, i, Color.PINK);
            }
        imageWriter.writeToImage();
    }
}