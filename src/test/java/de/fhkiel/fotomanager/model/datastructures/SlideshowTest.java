package de.fhkiel.fotomanager.model.datastructures;

import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.sound.midi.SysexMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Slideshow test.
 */
public class SlideshowTest {

    /**
     * The DocumentBuilder is needed to create a Document for the tests
     */
    private static DocumentBuilder documentBuilder;

    /**
     * Set up Album before running the tests by getting the instance of it
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @BeforeAll
    public static void setUp() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
    }

    /**
     * Initialize slideshow test.
     */
// Test if the Slideshow class is correctly initialized
    @Test
    void initializeSlideshowTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = 10;
        Slideshow slideshow = new Slideshow(slideshowName, secondsPerImage);

        // Check if the slideshow name is correctly set to "TestSlideshow"
        assertEquals(slideshowName, slideshow.getName());

        // Check if the seconds per image is correctly set to 10
        assertEquals(secondsPerImage, slideshow.getSecondsPerImage());
    }

    /**
     * Initialize slideshow with null name test.
     */
// Test creating a slideshow with a null name
    @Test
    void initializeSlideshowWithNullNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create a slideshow with a null name
        assertThrows(IllegalArgumentException.class, () -> new Slideshow(null, 10));
    }

    /**
     * Initialize slideshow with empty name test.
     */
// Test creating a slideshow with an empty name
    @Test
    void initializeSlideshowWithEmptyNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create a slideshow with an empty name
        assertThrows(IllegalArgumentException.class, () -> new Slideshow("", 10));
    }

    /**
     * Initialize slideshow with invalid seconds per image test.
     */
// Test creating a slideshow with a negative seconds per image
    @Test
    void initializeSlideshowWithInvalidSecondsPerImageTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = -10;

        // Check if an IllegalArgumentException is thrown when trying to create a slideshow with a negative seconds per image
        assertThrows(IllegalArgumentException.class, () -> new Slideshow(slideshowName, secondsPerImage));
    }

    /**
     * Change seconds per image test.
     */
// Test changing the seconds per image
    @Test
    void changeSecondsPerImageTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = 10;
        Slideshow slideshow = new Slideshow(slideshowName, secondsPerImage);
        int newSecondsPerImage = 5;
        slideshow.changeSecondsPerImage(newSecondsPerImage);

        // Check if the seconds per image is correctly set to 5
        assertEquals(newSecondsPerImage, slideshow.getSecondsPerImage());
    }

    /**
     * Change seconds per image with invalid seconds per image test.
     */
// Test changing the seconds per image with a negative value
    @Test
    void changeSecondsPerImageWithInvalidSecondsPerImageTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = 10;
        Slideshow slideshow = new Slideshow(slideshowName, secondsPerImage);
        int newSecondsPerImage = -5;

        // Check if an IllegalArgumentException is thrown when trying to change the seconds per image to a negative value
        assertThrows(IllegalArgumentException.class, () -> slideshow.changeSecondsPerImage(newSecondsPerImage));
    }

    /**
     * Rename slideshow test.
     */
// Test renaming a slideshow
    @Test
    void renameSlideshowTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = 10;
        Slideshow slideshow = new Slideshow(slideshowName, secondsPerImage);
        String newSlideshowName = "TheCoolerSlideshow";
        slideshow.rename(newSlideshowName);

        // Check if the slideshow name is correctly set to "TheCoolerSlideshow"
        assertEquals(newSlideshowName, slideshow.getName());
    }

    /**
     * Rename slideshow with null name test.
     */
// Test renaming a slideshow with a null name
    @Test
    void renameSlideshowWithNullNameTest() {
        Slideshow slideshow = new Slideshow("Slideshow", 10);
        // Check if an IllegalArgumentException is thrown when trying to rename a slideshow with a null name
        assertThrows(IllegalArgumentException.class, () -> slideshow.rename(null));
    }

    /**
     * Rename slideshow with empty name test.
     */
// Test renaming a slideshow with an empty name
    @Test
    void renameSlideshowWithEmptyNameTest() {
        Slideshow slideshow = new Slideshow("Slideshow", 10);
        // Check if an IllegalArgumentException is thrown when trying to rename a slideshow with an empty name
        assertThrows(IllegalArgumentException.class, () -> slideshow.rename(""));
    }

    /**
     * Add photo test.
     */
// Test adding photos to a slideshow
    @Test
    public void addPhotoTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);

        // Check if the photo is added to the slideshow
        assertTrue(slideshow.getMediaList().contains(photo));

        // Check if the slideshow contains only one photo
        assertEquals(1, slideshow.getMediaList().size());
    }

    /**
     * Remove photo test.
     */
// Test removing a photo from a slideshow
    @Test
    public void removePhotoTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.removeMedia(photo);

        // Check if the photo is removed from the slideshow
        assertFalse(slideshow.getMediaList().contains(photo));

        // Check if the slideshow contains no photos
        assertEquals(0, slideshow.getMediaList().size());
    }

    /**
     * Add media adjusted duration test.
     */
// Test adjusting the duration of a slideshow
    @Test
    public void addMediaAdjustedDurationTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        Photo photo3 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.addMedia(photo3);
        slideshow.removeMedia(photo2);

        // Check if the duration of the slideshow is correctly set to 20
        assertEquals(20, slideshow.getDuration());
    }

    /**
     * Play slideshow test.
     *
     * @throws InterruptedException the interrupted exception
     */
// Test playing a slideshow
    @Test
    public void playSlideshowTest() throws InterruptedException {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.play();
        Thread.sleep((slideshow.getMediaList().size() - 1) * slideshow.getSecondsPerImage() * 1000);

        // Check if the slideshow is playing
        assertTrue(slideshow.isPlaying());
        Thread.sleep((slideshow.getSecondsPerImage() + 1) * 1000);

        // Check if the slideshow is not playing anymore
        assertFalse(slideshow.isPlaying());

        // Check if the index of the current image is correctly set back to 0
        assertEquals(0, slideshow.getCurrentImageIndex());
    }

    /**
     * Stop slideshow test.
     *
     * @throws InterruptedException the interrupted exception
     */
// Test stopping a slideshow
    @Test
    public void stopSlideshowTest() throws InterruptedException {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.play();
        Thread.sleep((slideshow.getMediaList().size() - 1) * slideshow.getSecondsPerImage() * 1000);
        slideshow.stop();

        // Check if the slideshow is not playing anymore
        assertFalse(slideshow.isPlaying());

        // Check if the index of the current image is correctly set back to 0
        assertEquals(0, slideshow.getCurrentImageIndex());
    }

    /**
     * Next slideshow test.
     */
// Test going to the next image in a slideshow
    @Test
    public void nextSlideshowTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.next();

        // Check if the index of the current image is correctly set to 1
        assertEquals(1, slideshow.getCurrentImageIndex());
        slideshow.next();

        // Check if the index of the current image is correctly set back to 0 since there are only 2 images
        assertEquals(0, slideshow.getCurrentImageIndex());
    }

    /**
     * Previous slideshow test.
     */
// Test going to the previous image in a slideshow
    @Test
    public void previousSlideshowTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.previous();

        // Check if the index of the current image is correctly set to 1 since there are only 2 images
        assertEquals(1, slideshow.getCurrentImageIndex());
        slideshow.previous();

        // Check if the index of the current image is correctly set back to 0
        assertEquals(0, slideshow.getCurrentImageIndex());
    }

    /**
     * Delete slideshow test.
     */
// Test deleting a slideshow
    @Test
    public void deleteSlideshowTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        slideshow.delete();

        // Check if the slideshow contains no photos
        assertEquals(0, slideshow.getMediaList().size());

        // Check if the slideshow name is set to null
        assertNull(slideshow.getName());

        // Check if the seconds per image is set to 0
        assertEquals(0, slideshow.getSecondsPerImage());

        // Check if the duration is set to 0
        assertEquals(0, slideshow.getDuration());
    }

    /**
     * Gets timer test.
     */
// Test getting the timer of a slideshow
    @Test
    public void getTimerTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 1);

        // Check if the timer is null as the slideshow is not playing
        assertNull(slideshow.getTimer());
    }

    /**
     * Test converting Slideshow to XML.
     */
    @Test
    void slideshowToXMLTest() {
        // Create a Slideshow with some Photos
        Slideshow slideshow = new Slideshow("TestSlideshow", 5);
        Photo photo1 = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo1);
        slideshow.addMedia(photo2);

        // Create a new XML document
        Document document = documentBuilder.newDocument();

        Element slideshowElement = slideshow.toXML(document);
        assertNotNull(slideshowElement, "Slideshow Element should not be null");
        assertEquals("Slideshow", slideshowElement.getTagName(), "Element should be named 'Slideshow'");
        assertEquals("TestSlideshow", slideshowElement.getAttribute("name"), "Attribute 'name' should match slideshow name");
        assertEquals("5", slideshowElement.getAttribute("secondsPerImage"), "Attribute 'secondsPerImage' should match the slideshow's setting");

        assertEquals(2, slideshowElement.getElementsByTagName("Photo").getLength(), "Slideshow should have 2 photo elements");
    }

    /**
     * Test converting XML to Slideshow.
     */
    @Test
    void slideshowFromXMLTest() {
        String slideshowName = "TestSlideshow";
        int secondsPerImage = 5;

        Document document = documentBuilder.newDocument();
        Element slideshowElement = document.createElement("Slideshow");
        slideshowElement.setAttribute("name", slideshowName);
        slideshowElement.setAttribute("secondsPerImage", String.valueOf(secondsPerImage));

        Element photoElement1 = document.createElement("Photo");
        photoElement1.setAttribute("file", "C:/test/path/test1.jpg");
        photoElement1.setAttribute("name", "TestPhoto1");
        photoElement1.setAttribute("date", "2021-01-01");
        photoElement1.setAttribute("description", "This is test photo 1");
        photoElement1.setAttribute("isPrivate", "false");
        photoElement1.setAttribute("zoomFactor", "1.0");
        photoElement1.setAttribute("rating", "ZERO_STARS");
        photoElement1.setAttribute("orientation", "D0");
        photoElement1.setAttribute("resolution", "1920x1080");
        slideshowElement.appendChild(photoElement1);

        Element photoElement2 = document.createElement("Photo");
        photoElement2.setAttribute("file", "C:/test/path/test2.jpg");
        photoElement2.setAttribute("name", "TestPhoto2");
        photoElement2.setAttribute("date", "2021-01-01");
        photoElement2.setAttribute("description", "This is test photo 2");
        photoElement2.setAttribute("isPrivate", "false");
        photoElement2.setAttribute("zoomFactor", "1.0");
        photoElement2.setAttribute("rating", "ZERO_STARS");
        photoElement2.setAttribute("orientation", "D0");
        photoElement2.setAttribute("resolution", "1920x1080");
        slideshowElement.appendChild(photoElement2);

        Slideshow slideshow = Slideshow.fromXML(slideshowElement);

        assertNotNull(slideshow, "Parsed Slideshow should not be null");
        assertEquals(slideshowName, slideshow.getName(), "Slideshow name should match XML attribute");
        assertEquals(secondsPerImage, slideshow.getSecondsPerImage(), "Seconds per image should match XML attribute");

        List<Photo> mediaList = slideshow.getMediaList();
        assertEquals(2, mediaList.size(), "Should have 2 Photo objects");

        assertEquals(Photo.class, mediaList.get(0).getClass(), "First media should be of type Photo");
        assertEquals(Photo.class, mediaList.get(1).getClass(), "Second media should be of type Photo");
    }

    /**
     * Add private photo test.
     */
    @Test
    void addPrivatePhotoTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        photo.setPrivate(true);

        // Check if an IllegalArgumentException is thrown when trying to add a private photo to a slideshow
        assertThrows(IllegalArgumentException.class, () -> slideshow.addMedia(photo));
    }

    /**
     * Clear private photos from list test.
     */
    @Test
    void clearPrivatePhotosFromListTest() {
        Slideshow slideshow = new Slideshow("TestSlideshow", 10);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Photo photo2 = TestUtilMediaController.createDefaultPhoto();
        slideshow.addMedia(photo);
        slideshow.addMedia(photo2);
        photo2.setPrivate(true);
        slideshow.clearPrivatePhotosFromList();

        // Check if the private photo is removed from the slideshow
        assertFalse(slideshow.getMediaList().contains(photo2));

        // Check if the slideshow contains only one photo
        assertEquals(1, slideshow.getMediaList().size());
    }
}
