package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.SlideshowController;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Slideshow controller test.
 */
public class SlideshowControllerTest {
    /**
     * The Slideshow controller.
     */
    private static SlideshowController slideshowController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        slideshowController = SlideshowController.get();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        SlideshowController anotherInstance = SlideshowController.get();
        assertNotNull(slideshowController, "SlideshowController instance should not be null");
        assertSame(slideshowController, anotherInstance, "SlideshowController instance should be the same");
    }

    /**
     * Create slideshow test.
     */
    @Test
    public void createSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        assertNotNull(slideshow, "Slideshow should not be null");
        assertEquals("Slideshow", slideshow.getName(), "Slideshow should have the name 'Slideshow'");
        assertEquals(5, slideshow.getSecondsPerImage(), "Slideshow should have 5 seconds per image");
    }

    /**
     * Create folder with null name test.
     */
    @Test
    public void createFolderWithNullNameTest() {
        assertThrows(IllegalArgumentException.class, () -> slideshowController.createSlideshow(null, 10), "Album name should not be null");
    }

    /**
     * Create folder with empty name test.
     */
    @Test
    public void createFolderWithEmptyNameTest() {
        assertThrows(IllegalArgumentException.class, () -> slideshowController.createSlideshow("", 10), "Album name should not be empty");
    }

    /**
     * Create slideshow with invalid seconds per image test.
     */
    @Test
    public void createSlideshowWithInvalidSecondsPerImageTest() {
        assertThrows(IllegalArgumentException.class, () -> slideshowController.createSlideshow("Slideshow", -1));
    }

    /**
     * Rename slideshow test.
     */
    @Test
    public void renameSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        slideshowController.renameSlideshow(slideshow, "New Slideshow");
        assertEquals("New Slideshow", slideshow.getName(), "Slideshow should have the new name 'New Diashow'");
    }

    /**
     * Rename slideshow with null name test.
     */
    @Test
    void renameSlideshowWithNullNameTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        assertThrows(IllegalArgumentException.class, () -> slideshowController.renameSlideshow(slideshow, null));
    }

    /**
     * Rename slideshow with empty name test.
     */
    @Test
    void renameSlideshowWithEmptyNameTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        assertThrows(IllegalArgumentException.class, () -> slideshowController.renameSlideshow(slideshow, ""));
    }

    /**
     * Delete slideshow test.
     */
    @Test
    public void deleteSlideshowSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshowController.deleteSlideshow(slideshow);

        assertEquals(List.of(), slideshow.getMediaList(), "Media list should be empty");
        assertNull(slideshow.getName(), "Name should be null");
        assertEquals(0, slideshow.getSecondsPerImage(), "Seconds per image should be 0");
        assertEquals(0, slideshow.getDuration(), "Duration should be 0");
    }

    /**
     * Change seconds per image test.
     */
    @Test
    public void changeSecondsPerImageTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        slideshowController.changeSecondsPerImage(slideshow, 10);
        assertEquals(10, slideshow.getSecondsPerImage(), "Slideshow should have 10 seconds per image");
    }

    /**
     * Change seconds per image with invalid seconds per image test.
     */
    @Test
    public void changeSecondsPerImageWithInvalidSecondsPerImageTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        assertThrows(IllegalArgumentException.class, () -> slideshowController.changeSecondsPerImage(slideshow, -1));
    }

    /**
     * Add photo to slideshow adjust duration test.
     */
    @Test
    public void addPhotoToSlideshowAdjustDurationTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto2();
        slideshowController.addPhotoToSlideshow(slideshow, photo);
        slideshowController.addPhotoToSlideshow(slideshow, photo2);
        slideshowController.removePhotoFromSlideshow(slideshow, photo2);
        assertEquals(10, slideshow.getDuration(), "Duration should be 5");
    }

    /**
     * Play slideshow slideshow test.
     */
    @Test
    public void playSlideshowSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 1);
        slideshowController.playSlideshow(slideshow);
        assertTrue(slideshow.isPlaying(), "Slideshow should be playing");
        slideshowController.stopSlideshow(slideshow);
    }

    /**
     * Stop slideshow slideshow test.
     */
    @Test
    public void stopSlideshowSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 1);
        slideshowController.playSlideshow(slideshow);
        slideshowController.stopSlideshow(slideshow);
        assertFalse(slideshow.isPlaying(), "Slideshow should not be playing");
    }

    /**
     * Show next image test.
     */
    @Test
    public void showNextImageTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto2();
        slideshowController.addPhotoToSlideshow(slideshow, photo);
        slideshowController.addPhotoToSlideshow(slideshow, photo2);
        slideshowController.showNextImage(slideshow);
        assertEquals(1, slideshow.getCurrentImageIndex(), "Current image index should be 1");
    }

    /**
     * Show previous image test.
     */
    @Test
    public void showPreviousImageTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshowController.addPhotoToSlideshow(slideshow, photo);
        slideshowController.addPhotoToSlideshow(slideshow, photo2);
        slideshowController.showNextImage(slideshow);
        slideshowController.showPreviousImage(slideshow);
        assertEquals(0, slideshow.getCurrentImageIndex(), "Current image index should be 0");
    }
}
