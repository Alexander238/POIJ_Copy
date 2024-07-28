package de.fhkiel.fotomanager.model.datastructures;

import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Album test.
 */
public class AlbumTest {
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
     * Initialize album test.
     */
// Test if the Album class is correctly initialized
    @Test
    void initializeAlbumTest() {
        String albumName = "TestAlbum";
        Album album = new Album(albumName);

        // Check if the album name is correctly set to "TestAlbum"
        assertEquals(albumName, album.getName());

        // Check if the album is not null and exists
        assertNotNull(album);
    }

    /**
     * Initialize album with null name test.
     */
// Test creating an album with a null name
    @Test
    void initializeAlbumWithNullNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create an album with a null name
        assertThrows(IllegalArgumentException.class, () -> new Album(null));
    }

    /**
     * Initialize album with empty name test.
     */
// Test creating an album with an empty name
    @Test
    void initializeAlbumWithEmptyNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create an album with an empty name
        assertThrows(IllegalArgumentException.class, () -> new Album(""));
    }

    /**
     * Rename album test.
     */
// Test renaming an album
    @Test
    void renameAlbumTest() {
        Album album = new Album("Daniel");
        String newName = "TheCoolerDaniel";
        album.rename(newName);

        // Check if the album name is correctly set to "TheCoolerDaniel"
        assertEquals(newName, album.getName());
    }

    /**
     * Rename album with null name test.
     */
// Test renaming an album with a null name
    @Test
    void renameAlbumWithNullNameTest() {
        Album album = new Album("Daniel");

        // Check if an IllegalArgumentException is thrown when trying to rename an album with a null name
        assertThrows(IllegalArgumentException.class, () -> album.rename(null));
    }

    /**
     * Rename album with empty name test.
     */
// Test renaming an album with an empty name
    @Test
    void renameAlbumWithEmptyNameTest() {
        Album album = new Album("Daniel");

        // Check if an IllegalArgumentException is thrown when trying to rename an album with an empty name
        assertThrows(IllegalArgumentException.class, () -> album.rename(""));
    }

    /**
     * Add media test.
     */
// Test adding Media to an album
    @Test
    public void addMediaTest() {
        Album album = new Album("TestAlbum");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        album.addMedia(photo);
        album.addMedia(video);

        // Check if the album contains the photo
        assertTrue(album.getMediaList().contains(photo));

        // Check if the album contains the video
        assertTrue(album.getMediaList().contains(video));

        // Check if the album contains 2 media
        assertEquals(2, album.getMediaList().size());
    }

    /**
     * Remove media test.
     */
// Test removing Media from an album
    @Test
    public void removeMediaTest() {
        Album album = new Album("TestAlbum");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        album.addMedia(photo);
        album.removeMedia(photo);

        // Check if the album does not contain the photo
        assertFalse(album.getMediaList().contains(photo));

        // Check if the album contains 0 media
        assertEquals(0, album.getMediaList().size());
    }

    /**
     * Delete album test.
     */
// Test deleting an album
    @Test
    public void deleteAlbumTest() {
        Album album = new Album("TestAlbum");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        album.addMedia(photo);
        album.addMedia(video);
        album.delete();

        // Check if the album contains 0 media
        assertEquals(0, album.getMediaList().size());

        // Check if the album name is null
        assertNull(album.getName());
    }

    /**
     * Test converting Album to XML.
     */
    @Test
    void albumToXMLTest() {
        // Create an Album with some Media
        Album album = new Album("TestAlbum");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        album.addMedia(photo);
        album.addMedia(video);

        // Create a new XML document
        Document document = documentBuilder.newDocument();

        Element albumElement = album.toXML(document);
        assertNotNull(albumElement, "Album Element should not be null");
        assertEquals("Album", albumElement.getTagName(), "Element should be named 'Album'");
        assertEquals("TestAlbum", albumElement.getAttribute("name"), "Attribute 'name' should match album name");

        assertEquals(1, albumElement.getElementsByTagName("Photo").getLength(), "Album should have 1 photo element");
        assertEquals(1, albumElement.getElementsByTagName("Video").getLength(), "Album should have 1 video element");
    }

    /**
     * Test converting XML to Album.
     */
    @Test
    void albumFromXMLTest() {
        String albumName = "TestAlbum";
        Document document = documentBuilder.newDocument();
        Element albumElement = document.createElement("Album");
        albumElement.setAttribute("name", albumName);

        Element photoElement = document.createElement("Photo");
        photoElement.setAttribute("file", "C:/test/path/test.jpg");
        photoElement.setAttribute("name", "TestPhoto");
        photoElement.setAttribute("date", "2021-01-01");
        photoElement.setAttribute("description", "This is a test photo");
        photoElement.setAttribute("isPrivate", "false");
        photoElement.setAttribute("zoomFactor", "1.0");
        photoElement.setAttribute("rating", "ZERO_STARS");
        photoElement.setAttribute("orientation", "D0");
        photoElement.setAttribute("resolution", "1920x1080");
        albumElement.appendChild(photoElement);

        Element videoElement = document.createElement("Video");
        videoElement.setAttribute("file", "C:/test/path/test.mp4");
        videoElement.setAttribute("name", "TestVideo");
        videoElement.setAttribute("date", "2021-01-01");
        videoElement.setAttribute("description", "This is a test video");
        videoElement.setAttribute("isPrivate", "false");
        videoElement.setAttribute("duration", "60");
        videoElement.setAttribute("orientation", "D0");
        videoElement.setAttribute("resolution", "1920x1080");
        videoElement.setAttribute("playbackSpeed", "X1");
        videoElement.setAttribute("rating", "ZERO_STARS");
        albumElement.appendChild(videoElement);

        Album album = Album.fromXML(albumElement);

        assertNotNull(album, "Parsed Album should not be null");
        assertEquals(albumName, album.getName(), "Album name should match XML attribute");

        List<Media> mediaList = album.getMediaList();
        assertEquals(2, mediaList.size(), "Should have 2 Media objects");

        assertEquals(Photo.class, mediaList.get(0).getClass(), "First media should be of type Photo");
        assertEquals(Video.class, mediaList.get(1).getClass(), "Second media should be of type Video");
    }
}
