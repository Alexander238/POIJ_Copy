package de.fhkiel.fotomanager.model.mediatypes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Resolution test.
 */
public class ResolutionTest {

    /**
     * Create valid orientation test.
     */
// Test create a Resolution object
    @Test
    public void createValidOrientationTest() {
        Resolution resolution = new Resolution(1920, 1080);

        // Check if created resolution instance is not null
        assertNotNull(resolution, "Resolution should not be null");

        // Check if width is set to 1920
        assertEquals(1920, resolution.getWidth());

        // Check if height is set to 1080
        assertEquals(1080, resolution.getHeight());
    }

    /**
     * Create invalid width orientation test.
     */
// Test create a Resolution object with negative values
    @Test
    public void createInvalidWidthOrientationTest() {
        try {
            new Resolution(-100, 100);
        } catch (IllegalArgumentException e) {

            // Check if message says width must be greater than or equal to 0
            assertEquals("width must be greater than or equal to 0", e.getMessage());
        }
    }

    /**
     * Create invalid height orientation test.
     */
// Test create a Resolution object with negative values
    @Test
    public void createInvalidHeightOrientationTest() {
        try {
            new Resolution(100, -100);
        } catch (IllegalArgumentException e) {

            // Check if message says height must be greater than or equal to 0
            assertEquals("height must be greater than or equal to 0", e.getMessage());
        }
    }

    /**
     * Sets valid width test.
     */
// Test to set a valid width value
    @Test
    public void setValidWidthTest() {
        Resolution resolution = new Resolution(100, 100);
        resolution.setWidth(200);

        // Check if width is set to 200 correctly
        assertEquals(200, resolution.getWidth());
    }

    /**
     * Sets invalid width test.
     */
// Test to set an invalid width value
    @Test
    public void setInvalidWidthTest() {
        // Check if IllegalArgumentException is thrown when width is set to negative value
        assertThrows(IllegalArgumentException.class, () -> {
            Resolution resolution = new Resolution(100, 100);
            resolution.setWidth(-100);
        });
    }

    /**
     * Sets valid height test.
     */
// Test to set a valid height value
    @Test
    public void setValidHeightTest() {
        Resolution resolution = new Resolution(100, 100);
        resolution.setHeight(200);

        // Check if height is set to 200 correctly
        assertEquals(200, resolution.getHeight());
    }

    /**
     * Sets invalid height test.
     */
    @Test
    public void setInvalidHeightTest() {
        // Check if IllegalArgumentException is thrown when height is set to negative value
        assertThrows(IllegalArgumentException.class, () -> {
            Resolution resolution = new Resolution(100, 100);
            resolution.setHeight(-100);
        });
    }
}
