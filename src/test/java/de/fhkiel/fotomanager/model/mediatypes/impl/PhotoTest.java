package de.fhkiel.fotomanager.model.mediatypes.impl;

import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.rmi.UnexpectedException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Photo test.
 */
public class PhotoTest {

    /**
     *  The TagController is needed to create Tags for the tests
     */
    private static TagController tagController;

    /**
     * The Photo object that is used for the tests
     */
    private static Photo photo;

    /**
     * The DocumentBuilder is needed to create a Document for the tests
     */
    private static DocumentBuilder documentBuilder;

    /**
     * Set up TagController before running the tests by getting the instance of it
     *
     * @throws ParserConfigurationException the parser configuration exception
     */
    @BeforeAll
    public static void setUp() throws ParserConfigurationException {
        tagController = TagController.get();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
    }

    /**
     * Set up a new photo object before each test and clear the tagList
     */
    @BeforeEach
    public void beforeEach() {
        photo = TestUtilMediaController.createDefaultPhoto();
        photo.setTags(TagListController.get().createEmptyTagList());
    }

    /**
     * Test if the photo object can be created
     */
    @Test
    public void createPhotoTest() {
        Photo photo = new Photo(
                TestUtilMediaController.defaultFile,
                TestUtilMediaController.defaultName,
                TestUtilMediaController.defaultDate,
                TestUtilMediaController.defaultDescription,
                TestUtilMediaController.defaultIsPrivate,
                TestUtilMediaController.defaultTags,
                TestUtilMediaController.defaultZoomFactor,
                TestUtilMediaController.defaultRating,
                TestUtilMediaController.defaultOrientation,
                TestUtilMediaController.defaultResolution
        );

        // Check if the photo object is not null
        assertNotNull(photo, "Photo should not be null");

        // Check if the photo object has the correct file "defaultFile"
        assertEquals(TestUtilMediaController.defaultFile, photo.getFile(), "Photo should have the file 'defaultFile'");

        // Check if the photo object has the correct name "defaultName"
        assertEquals(TestUtilMediaController.defaultName, photo.getName(), "Photo should have the name 'defaultName'");

        // Check if the photo object has the correct date "new Date() -> concrete moment in time"
        assertEquals(TestUtilMediaController.defaultDate, photo.getDate(), "Photo should have the date 'new Date()'");

        // Check if the photo object has the correct description "defaultDescription"
        assertEquals(TestUtilMediaController.defaultDescription, photo.getDescription(), "Photo should have the description 'defaultDescription'");

        // Check if the photo object has the correct privacy set to true
        assertEquals(TestUtilMediaController.defaultIsPrivate, photo.isPrivate(), "Photo should be private");

        // Check if the photo object has the correct tags "defaultTags", which is an empty list
        assertEquals(TestUtilMediaController.defaultTags, photo.getTags(), "Photo should have the tags 'defaultTags'");

        // Check if the photo object has the correct zoom factor of 1
        assertEquals(TestUtilMediaController.defaultZoomFactor, photo.getZoomFactor(), "Photo should have the zoom factor 1");

        // Check if the photo object has the correct rating of zero stars
        assertEquals(TestUtilMediaController.defaultRating, photo.getRating(), "Photo should have the rating 'ZERO_STARS'");

        // Check if the photo object has the correct orientation of zero degrees
        assertEquals(TestUtilMediaController.defaultOrientation, photo.getOrientation(), "Photo should have the orientation 'D0'");

        // Check if the photo object has the correct resolution of 1920x1080
        assertEquals(TestUtilMediaController.defaultResolution, photo.getResolution(), "Photo should have the resolution '1920x1080'");
    }

    /**
     * Test if the photo object can be created with a file path.
     */
    @Test
    public void createPhotoFromFileTest() {
        Photo photo = new Photo("path\\to\\file.jpg");

        // Check if the photo object is not null
        assertNotNull(photo, "Photo should not be null");

        // Check if the photo object has the correct file "path/to/file.jpg"
        assertEquals("path\\to\\file.jpg", photo.getFile(), "Photo should have the file 'path\\to\\file.jpg'");

        // Check if the photo object has the correct name "file.jpg"
        assertEquals("file.jpg", photo.getName(), "Photo should have the name 'file.jpg'");

        // Check if the photo has the zoom factor of 0
        assertEquals(0, photo.getZoomFactor(), "Photo should have the zoom factor 0");
    }

    /**
     * Test if the photo object can be renamed
     */
    @Test
    public void renamePhotoTest() {
        photo.rename("newName");

        // Check if the name of the photo object has been changed to "newName"
        assertEquals("newName", photo.getName());
    }

    /**
     * Test if a Tag can be added to a photo object
     */
    @Test
    public void addTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        photo.addTag(tag);

        // Check if the tags list of the photo object has a size of 1
        assertEquals(1, photo.getTags().getTags().size());

        // Check if the photo object has the added Tag
        assertTrue(photo.getTags().getTags().contains(tag));
    }

    /**
     * Test if a Tag can be removed from a photo object
     */
    @Test
    public void removeTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        photo.addTag(tag);
        photo.removeTag(tag);

        // Check if the tags list of the photo object is empty
        assertEquals(0, photo.getTags().getTags().size());

        // Check if the photo object does not have the removed Tag
        assertFalse(photo.getTags().getTags().contains(tag));
    }

    /**
     * Test if a photo object can be deleted and all its attributes are set to null or false
     */
    @Test
    public void deletePhotoTest() {
        // Check if delete function returned true
        assertTrue(photo.delete());

        // Check if the file of the photo object is null
        assertNull(photo.getFile());

        // Check if the name of the photo object is null
        assertNull(photo.getName());

        // Check if the date of the photo object is null
        assertNull(photo.getDate());

        // Check if the description of the photo object is null
        assertNull(photo.getDescription());

        // Check if the tags list of the photo object is empty
        assertNull(photo.getTags());

        // Check if the rating of the photo object is null
        assertNull(photo.getRating());

        // Check if the orientation of the photo object is null
        assertNull(photo.getOrientation());

        // Check if the resolution of the photo object is null
        assertNull(photo.getResolution());

        // Check if the privacy of the photo object is false
        assertFalse(photo.isPrivate());

        // Check if the zoom factor of the photo object is 0
        assertEquals(0, photo.getZoomFactor());
    }

    /**
     * Test if the description of the photo object can be changed
     */
    @Test
    public void changeDescriptionTest() {
        String newDescription = "newDescription";
        photo.setDescription(newDescription);

        // Check if the description of the photo object has been changed to "newDescription"
        assertEquals(newDescription, photo.getDescription());
    }

    /**
     * Test if the privacy of the photo object can be changed
     */
    @Test
    public void changePrivacyTest() {
        boolean newIsPrivate = true;
        photo.setPrivate(newIsPrivate);

        // Check if the privacy of the photo object has been changed to true
        assertEquals(newIsPrivate, photo.isPrivate());
    }

    /**
     * Test if the rating of the photo object can be changed
     */
    @Test
    public void changeRatingTest() {
        Rating newRating = Rating.ONE_STARS;
        photo.setRating(newRating);

        // Check if the rating of the photo object has been changed to a one-star rating
        assertEquals(newRating, photo.getRating());
    }

    /**
     * Test if the orientation of the photo object can be changed
     */
    @Test
    public void rotateClockwiseFrom0DegreeTest() {
        photo.rotateClockwise();

        // Check if the orientation of the photo object has been changed to 90 degrees
        assertEquals(Orientation.D90, photo.getOrientation());
    }

    /**
     * Rotate clockwise from 90 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom90DegreeTest() {
        photo.setOrientation(Orientation.D90);
        photo.rotateClockwise();

        // Check if the orientation of the photo object has been changed to 180 degrees
        assertEquals(Orientation.D180, photo.getOrientation());
    }

    /**
     * Rotate clockwise from 180 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom180DegreeTest() {
        photo.setOrientation(Orientation.D180);
        photo.rotateClockwise();

        // Check if the orientation of the photo object has been changed to 270 degrees
        assertEquals(Orientation.D270, photo.getOrientation());
    }

    /**
     * Rotate clockwise from 270 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom270DegreeTest() {
        photo.setOrientation(Orientation.D270);
        photo.rotateClockwise();

        // Check if the orientation of the photo object has been changed to 0 degrees
        assertEquals(Orientation.D0, photo.getOrientation());
    }

    /**
     * Test if the photo can be rotated counter-clockwise, origin is 0 degrees
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom0DegreeTest() {
        photo.rotateCounterClockwise();

        // Check if the orientation of the photo object has been changed to 270 degrees
        assertEquals(Orientation.D270, photo.getOrientation());
    }

    /**
     * Test if the photo can be rotated counter-clockwise, origin is 90 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom90DegreeTest() {
        photo.setOrientation(Orientation.D90);
        photo.rotateCounterClockwise();

        // Check if the orientation of the photo object has been changed to 0 degrees
        assertEquals(Orientation.D0, photo.getOrientation());
    }

    /**
     * Test if the photo can be rotated counter-clockwise, origin is 180 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom180DegreeTest() {
        photo.setOrientation(Orientation.D180);
        photo.rotateCounterClockwise();

        // Check if the orientation of the photo object has been changed to 90 degrees
        assertEquals(Orientation.D90, photo.getOrientation());
    }

    /**
     * Test if the photo can be rotated counter-clockwise, origin is 270 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom270DegreeTest() {
        photo.setOrientation(Orientation.D270);
        photo.rotateCounterClockwise();

        // Check if the orientation of the photo object has been changed to 180 degrees
        assertEquals(Orientation.D180, photo.getOrientation());
    }

    /**
     * Test if the photo can be zoomed in
     */
    @Test
    public void zoomInTest() {
        photo.zoomIn();

        // Check if the zoom factor of the photo object has been changed to 1.1
        assertEquals(1.1, photo.getZoomFactor());
    }

    /**
     * Test if the photo can be zoomed out
     */
    @Test
    public void zoomOutTest() {
        photo.zoomOut();

        // Check if the zoom factor of the photo object has been changed to 0.9
        assertEquals(0.9, photo.getZoomFactor());
    }

    /**
     * To xml test.
     */
    @Test
    public void toXMLTest() {
        Document document = documentBuilder.newDocument();
        Element photoElement = photo.toXML(document);

        // Check if the returned element is not null
        assertNotNull(photoElement, "Photo Element should not be null");

        // Check if the element name is "Photo"
        assertEquals("Photo", photoElement.getTagName(), "Element should be named 'Photo'");

        // Check if attributes are correctly set using default values from TestUtilMediaController
        assertEquals(TestUtilMediaController.defaultFile, photoElement.getAttribute("file"), "Attribute 'file' should match defaultFile");
        assertEquals(TestUtilMediaController.defaultName, photoElement.getAttribute("name"), "Attribute 'name' should match defaultName");
        assertEquals(TestUtilMediaController.defaultDate.toString(), photoElement.getAttribute("date"), "Attribute 'date' should match defaultDate");
        assertEquals(TestUtilMediaController.defaultDescription, photoElement.getAttribute("description"), "Attribute 'description' should match defaultDescription");
        assertEquals(String.valueOf(TestUtilMediaController.defaultIsPrivate), photoElement.getAttribute("isPrivate"), "Attribute 'isPrivate' should match defaultIsPrivate");
        assertEquals(String.valueOf(TestUtilMediaController.defaultZoomFactor), photoElement.getAttribute("zoomFactor"), "Attribute 'zoomFactor' should match defaultZoomFactor");
        assertEquals(TestUtilMediaController.defaultRating.name(), photoElement.getAttribute("rating"), "Attribute 'rating' should match defaultRating");
        assertEquals(TestUtilMediaController.defaultOrientation.name(), photoElement.getAttribute("orientation"), "Attribute 'orientation' should match defaultOrientation");
        assertEquals(TestUtilMediaController.defaultResolution.getWidth() + "x" + TestUtilMediaController.defaultResolution.getHeight(), photoElement.getAttribute("resolution"), "Attribute 'resolution' should match defaultResolution");

        // Additional tests for tags
        TagList tags = TagListController.get().createEmptyTagList();
        tags.addTag(tagController.createTag(TagType.PERSON, "Tag1"));
        tags.addTag(tagController.createTag(TagType.LOCATION, "Tag2"));
        photo.setTags(tags);

        photoElement = photo.toXML(document);
        assertEquals(2, photoElement.getElementsByTagName("Tag").getLength(), "Should have 2 Tag elements");
    }

    private Element createValidPhotoElement(Document document) {
        Element photoElement = document.createElement("Photo");
        photoElement.setAttribute("file", TestUtilMediaController.defaultFile);
        photoElement.setAttribute("name", TestUtilMediaController.defaultName);
        photoElement.setAttribute("date", TestUtilMediaController.defaultDate.toString());
        photoElement.setAttribute("description", TestUtilMediaController.defaultDescription);
        photoElement.setAttribute("isPrivate", String.valueOf(TestUtilMediaController.defaultIsPrivate));
        photoElement.setAttribute("zoomFactor", String.valueOf(TestUtilMediaController.defaultZoomFactor));
        photoElement.setAttribute("rating", TestUtilMediaController.defaultRating.name());
        photoElement.setAttribute("orientation", TestUtilMediaController.defaultOrientation.name());
        photoElement.setAttribute("resolution", TestUtilMediaController.defaultResolution.getWidth() + "x" + TestUtilMediaController.defaultResolution.getHeight());

        // Adding tags
        Element tagElement1 = document.createElement("Tag");
        tagElement1.setAttribute("type", TagType.PERSON.name());
        tagElement1.setAttribute("name", "Tag1");
        tagElement1.setAttribute("color", "#FF0000");
        photoElement.appendChild(tagElement1);

        Element tagElement2 = document.createElement("Tag");
        tagElement2.setAttribute("type", TagType.ADJECTIVE.name());
        tagElement2.setAttribute("name", "Tag2");
        tagElement2.setAttribute("color", "#FF0000");
        photoElement.appendChild(tagElement2);

        return photoElement;
    }

    /**
     * From xml valid element test.
     */
    @Test
    public void fromXMLValidElementTest() {
        Document document = documentBuilder.newDocument();
        Element photoElement = createValidPhotoElement(document);

        Photo parsedPhoto = Photo.fromXML(photoElement);
        assertNotNull(parsedPhoto, "Parsed Photo should not be null");

        // Check attributes
        assertEquals(TestUtilMediaController.defaultFile, parsedPhoto.getFile(), "File should match defaultFile");
        assertEquals(TestUtilMediaController.defaultName, parsedPhoto.getName(), "Name should match defaultName");
        assertEquals(TestUtilMediaController.defaultDate, parsedPhoto.getDate(), "Date should match defaultDate");
        assertEquals(TestUtilMediaController.defaultDescription, parsedPhoto.getDescription(), "Description should match defaultDescription");
        assertEquals(TestUtilMediaController.defaultIsPrivate, parsedPhoto.isPrivate(), "isPrivate should match defaultIsPrivate");
        assertEquals(TestUtilMediaController.defaultZoomFactor, parsedPhoto.getZoomFactor(), "zoomFactor should match defaultZoomFactor");
        assertEquals(TestUtilMediaController.defaultRating, parsedPhoto.getRating(), "Rating should match defaultRating");
        assertEquals(TestUtilMediaController.defaultOrientation, parsedPhoto.getOrientation(), "Orientation should match defaultOrientation");
        assertEquals(TestUtilMediaController.defaultResolution, parsedPhoto.getResolution(), "Resolution should match defaultResolution");

        // Check tags
        TagList tagList = parsedPhoto.getTags();
        assertEquals(2, tagList.getTags().size(), "Should have 2 tags");
        assertEquals("Tag1", tagList.getTags().get(0).getName(), "First tag name should be 'Tag1'");
        assertEquals(TagType.PERSON, tagList.getTags().get(0).getType(), "First tag type should be PERSON");
        assertEquals("Tag2", tagList.getTags().get(1).getName(), "Second tag name should be 'Tag2'");
        assertEquals(TagType.ADJECTIVE, tagList.getTags().get(1).getType(), "Second tag type should be ADJECTIVE");
    }

    /**
     * From xml error tests.
     */
    @Test
    public void fromXMLErrorTests() {
        Element photoElement = createValidPhotoElement(documentBuilder.newDocument());

        // Test invalid attributes
        photoElement.setAttribute("date", "InvalidDate");
        assertThrows(DateTimeParseException.class, () -> Photo.fromXML(photoElement), "Invalid date format should throw DateTimeParseException");

        photoElement.setAttribute("zoomFactor", "InvalidZoomFactor");
        assertThrows(DateTimeParseException.class, () -> Photo.fromXML(photoElement), "Invalid zoomFactor format should throw NumberFormatException");

        photoElement.setAttribute("rating", "InvalidRating");
        assertThrows(DateTimeParseException.class, () -> Photo.fromXML(photoElement), "Invalid rating should throw IllegalArgumentException");

        photoElement.setAttribute("orientation", "InvalidOrientation");
        assertThrows(DateTimeParseException.class, () -> Photo.fromXML(photoElement), "Invalid orientation should throw IllegalArgumentException");

        photoElement.setAttribute("resolution", "InvalidResolution");
        assertThrows(DateTimeParseException.class, () -> Photo.fromXML(photoElement), "Invalid resolution format should throw NumberFormatException");
    }

}
