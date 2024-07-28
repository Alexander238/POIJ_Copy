package de.fhkiel.fotomanager.model.datastructures;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import de.fhkiel.fotomanager.util.TestXMLController;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.Period;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.fhkiel.fotomanager.model.mediatypes.Media;

/**
 * The type Folder test.
 */
public class FolderTest {

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
     * Initialize folder test.
     */
    @Test
    void initializeFolderTest() {
        String folderName = "TestFolder";
        Either<LocalDate, Period> date = Either.left(LocalDate.now());
        String event = "Cool Event";
        Folder folder = new Folder(folderName, date, event);

        // Check if the folder name is correctly set to "TestFolder"
        assertEquals(folderName, folder.getName());

        // Check if the folder date is correctly set to the date
        assertEquals(date, folder.getDate());

        // Check if the folder event is correctly set to "Cool Event"
        assertEquals(event, folder.getEvent());
    }

    /**
     * Initialize folder with null name test.
     */
// Test creating a folder with a null name
    @Test
    void initializeFolderWithNullNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create a folder with a null name
        assertThrows(IllegalArgumentException.class, () ->
                new Folder(null, Either.left(LocalDate.now()), "Summer of '69"));
    }

    /**
     * Initialize folder with empty name test.
     */
// Test creating a folder with an empty name
    @Test
    void initializeFolderWithEmptyNameTest() {
        // Check if an IllegalArgumentException is thrown when trying to create a folder with an empty name
        assertThrows(IllegalArgumentException.class, () ->
                new Folder("", Either.left(LocalDate.now()), "Summer of '69"));
    }

    /**
     * Rename folder test.
     */
// Test renaming a folder
    @Test
    void renameFolderTest() {
        Folder folder = new Folder("Daniel", Either.left(LocalDate.now()), "Summer of '69");
        String newName = "TheCoolerDaniel";
        folder.rename(newName);

        // Check if the folder name is correctly set to "TheCoolerDaniel"
        assertEquals(newName, folder.getName());
    }

    /**
     * Rename folder with null name test.
     */
// Test renaming a folder with a null name
    @Test
    void renameFolderWithNullNameTest() {
        Folder folder = new Folder("Folder", Either.left(LocalDate.now()), "Summer of '69");
        // Check if an IllegalArgumentException is thrown when trying to rename a folder with a null name
        assertThrows(IllegalArgumentException.class, () -> folder.rename(null));
    }

    /**
     * Rename folder with empty name test.
     */
// Test renaming a folder with an empty name
    @Test
    void renameFolderWithEmptyNameTest() {
        Folder folder = new Folder("Folder", Either.left(LocalDate.now()), "Summer of '69");
        // Check if an IllegalArgumentException is thrown when trying to rename a folder with an empty name
        assertThrows(IllegalArgumentException.class, () -> folder.rename(""));
    }

    /**
     * Add media test.
     */
// Test adding Media to a folder
    @Test
    public void addMediaTest() {
        Folder folder = new Folder("Daniel", Either.left(LocalDate.now()), "Summer of '69");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        folder.addMedia(photo);
        folder.addMedia(video);

        // Check if the folder contains the photo
        assertTrue(folder.getMediaList().contains(photo));

        // Check if the folder contains the video
        assertTrue(folder.getMediaList().contains(video));

        // Check if the folder contains 2 media
        assertEquals(2, folder.getMediaList().size());
    }

    /**
     * Remove media test.
     */
// Test removing Media from a folder
    @Test
    public void removeMediaTest() {
        Folder folder = new Folder("Daniel", Either.left(LocalDate.now()), "Summer of '69");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        folder.addMedia(photo);
        folder.removeMedia(photo);

        // Check if the folder does not contain the photo
        assertFalse(folder.getMediaList().contains(photo));

        // Check if the folder contains 0 media
        assertEquals(0, folder.getMediaList().size());
    }

    /**
     * Test converting Folder to XML.
     */
    @Test
    void folderToXMLTest() {
        Either<LocalDate, Period> date = Either.left(LocalDate.of(2021, 1, 1));
        Folder folder = new Folder("TestFolder", date, "TestEvent");

        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        folder.addMedia(photo);
        folder.addMedia(video);

        Document document = documentBuilder.newDocument();

        Element folderElement = folder.toXML(document);
        assertNotNull(folderElement, "Folder Element should not be null");
        assertEquals("Folder", folderElement.getTagName(), "Element should be named 'Folder'");
        assertEquals("TestFolder", folderElement.getAttribute("name"), "Attribute 'name' should match folder name");
        assertEquals("Left(2021-01-01)", folderElement.getAttribute("date"), "Attribute 'date' should match folder date");
        assertEquals("TestEvent", folderElement.getAttribute("event"), "Attribute 'event' should match folder event");

        assertEquals(1, folderElement.getElementsByTagName("Photo").getLength(), "Folder should have 1 photo element");
        assertEquals(1, folderElement.getElementsByTagName("Video").getLength(), "Folder should have 1 video element");
    }

    /**
     * Test converting XML to Folder.
     */
    @Test
    void folderFromXMLTest() {
        String folderName = "TestFolder";
        Either<LocalDate, Period> date = Either.left(LocalDate.of(2021, 1, 1));
        String event = "TestEvent";

        Document document = documentBuilder.newDocument();
        Element folderElement = document.createElement("Folder");
        folderElement.setAttribute("name", folderName);
        folderElement.setAttribute("date", "Left(2021-01-01)");
        folderElement.setAttribute("event", event);

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
        folderElement.appendChild(photoElement);

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
        folderElement.appendChild(videoElement);

        Folder folder = Folder.fromXML(folderElement);

        assertNotNull(folder, "Parsed Folder should not be null");
        assertEquals(folderName, folder.getName(), "Folder name should match XML attribute");
        assertEquals(date, folder.getDate(), "Folder date should match XML attribute");
        assertEquals(event, folder.getEvent(), "Folder event should match XML attribute");

        List<Media> mediaList = folder.getMediaList();
        assertEquals(2, mediaList.size(), "Should have 2 Media objects");

        assertEquals(Photo.class, mediaList.get(0).getClass(), "First media should be of type Photo");
        assertEquals(Video.class, mediaList.get(1).getClass(), "Second media should be of type Video");
    }

    /**
     * Test converting Left date to LocalDate.
     */
    @Test
    void parseDateWithLeftTest() {
        String inputDate = "Left(2023-01-01)";
        Either result = Folder.parseDate(inputDate);

        assertTrue(result.isLeft());
        assertEquals(LocalDate.of(2023, 1, 1), result.getLeft());
    }

    /**
     * Test converting Right date to Period.
     */
    @Test
    void parseDateWithRightTest() {
        String inputDate = "Right(2023-01-01, 2023-01-31)";
        Either result = Folder.parseDate(inputDate);

        assertTrue(result.isRight());
        Period expectedPeriod = new Period(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));
        if (result.isRight()) {
            LocalDate start = ((Period) result.get()).getStart();
            LocalDate end = ((Period) result.get()).getEnd();
            assertEquals(expectedPeriod.getStart(), start);
            assertEquals(expectedPeriod.getEnd(), end);
        }
    }

    /**
     * Test converting invalid date.
     */
    @Test
    void parseDateInvalidTest() {
        String inputDate = "InvalidDate";
        Either result = Folder.parseDate(inputDate);

        assertNull(result);
    }

    /**
     * Test extract photos from xml.
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Test
    void extractPhotosFromXMLTest() throws ParserConfigurationException {
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        photo.setDate(LocalDate.of(2021, 1, 1));
        PhotoManagerController.get().getPhotoManager().getPhotos().add(photo);
        Element folderElement = TestXMLController.createFolderElement();

        // Check if the extracted photo list has a size of 1
        assertEquals(1, Folder.extractPhotosFromXML(folderElement).size());

        // Check if the extracted photo is the same as the one created
        assertEquals(photo, Folder.extractPhotosFromXML(folderElement).get(0));
    }

    /**
     * Test extract videos from xml.
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @Test
    void extractVideosFromXMLTest() throws ParserConfigurationException {
        Video video = TestUtilMediaController.createDefaultVideo();
        video.setDate(LocalDate.of(2021, 1, 1));
        PhotoManagerController.get().getPhotoManager().getVideos().add(video);
        Element folderElement = TestXMLController.createFolderElement();

        // Check if the extracted video list has a size of 1
        assertEquals(1, Folder.extractVideosFromXML(folderElement).size());

        // Check if the extracted video is the same as the one created
        assertEquals(video, Folder.extractVideosFromXML(folderElement).get(0));
    }

    /**
     * Delete folder test.
     */
// Test deleting a folder
    @Test
    public void deleteFolderTest() {
        Folder folder = new Folder("TestFolder", Either.left(LocalDate.now()), "Summer of '69");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        folder.addMedia(photo);
        folder.addMedia(video);
        folder.delete();

        // Check if the folder contains 0 media
        assertEquals(0, folder.getMediaList().size());

        // Check if the folder name is null
        assertNull(folder.getName());

        // Check if the folder date is null
        assertNull(folder.getDate());

        // Check if the folder event is null
        assertNull(folder.getEvent());
    }
}
