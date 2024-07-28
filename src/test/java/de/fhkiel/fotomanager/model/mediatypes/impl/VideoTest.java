package de.fhkiel.fotomanager.model.mediatypes.impl;

import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.PlaybackSpeed;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.rmi.UnexpectedException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Video test.
 */
public class VideoTest {

    /**
     * The Tag controller.
     */
    private static TagController tagController;

    /**
     * The Video object.
     */
    private static Video video;

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
     * Set up a new video object before each test and clear the tagList
     */
    @BeforeEach
    public void beforeEach() {
        video = TestUtilMediaController.createDefaultVideo();
        video.setTags(TagListController.get().createEmptyTagList());
    }

    /**
     * Create video test.
     */
    @Test
    public void createVideoTest() {
        Video video = new Video(
                TestUtilMediaController.defaultFile,
                TestUtilMediaController.defaultName,
                TestUtilMediaController.defaultDate,
                TestUtilMediaController.defaultDescription,
                TestUtilMediaController.defaultIsPrivate,
                TestUtilMediaController.defaultTags,
                TestUtilMediaController.defaultDuration,
                TestUtilMediaController.defaultRating,
                TestUtilMediaController.defaultOrientation,
                TestUtilMediaController.defaultResolution
        );

        // Check if the video object is not null
        assertNotNull(video, "Video should not be null");

        // Check if the video object has the correct file "defaultFile"
        assertEquals(TestUtilMediaController.defaultFile, video.getFile(), "Video should have the file 'defaultFile'");

        // Check if the video object has the correct name "defaultName"
        assertEquals(TestUtilMediaController.defaultName, video.getName(), "Video should have the name 'defaultName'");

        // Check if the video object has the correct date "new Date() -> concrete moment in time"
        assertEquals(TestUtilMediaController.defaultDate, video.getDate(), "Video should have the date 'new Date()'");

        // Check if the video object has the correct description "defaultDescription"
        assertEquals(TestUtilMediaController.defaultDescription, video.getDescription(), "Video should have the description 'defaultDescription'");

        // Check if the video object has the correct privacy set to true
        assertEquals(TestUtilMediaController.defaultIsPrivate, video.isPrivate(), "Video should be private");

        // Check if the video object has the correct tags "defaultTags", which is an empty list
        assertEquals(TestUtilMediaController.defaultTags, video.getTags(), "Video should have the tags 'defaultTags'");

        // Check if the video object has the correct duration of 1000
        assertEquals(TestUtilMediaController.defaultDuration, video.getDuration(), "Video should have the duration '1000'");

        // Check if the video object has the correct rating of zero stars
        assertEquals(TestUtilMediaController.defaultRating, video.getRating(), "Video should have the rating 'ZERO_STARS'");

        // Check if the video object has the correct orientation of zero degrees
        assertEquals(TestUtilMediaController.defaultOrientation, video.getOrientation(), "Video should have the orientation 'D0'");

        // Check if the video object has the correct resolution of 1920x1080
        assertEquals(TestUtilMediaController.defaultResolution, video.getResolution(), "Video should have the resolution '1920x1080'");
    }

    /**
     * Test if the video object can be renamed.
     */
    @Test
    public void renameVideoTest() {
        video.rename("newName");

        // Check if the name of the video object has been changed to "newName"
        assertEquals("newName", video.getName());
    }

    /**
     * Test if a Tag can be added to a video object.
     */
    @Test
    public void addTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        video.addTag(tag);

        // Check if the tags list of the video object has a size of 1
        assertEquals(1, video.getTags().getTags().size());

        // Check if the video object has the added Tag
        assertTrue(video.getTags().getTags().contains(tag));
    }

    /**
     * Test if a Tag can be removed from a video object.
     */
    @Test
    public void removeTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        video.addTag(tag);
        video.removeTag(tag);
        
        // Check if the tags list of the video object is empty
        assertEquals(0, video.getTags().getTags().size());
        
        // Check if the video object does not have the removed Tag
        assertFalse(video.getTags().getTags().contains(tag));
    }

    /**
     * Test if a video object can be deleted and all its attributes are set to null or false.
     */
    @Test
    public void deleteVideoTest() {
        // Check if delete function returned true
        assertTrue(video.delete());
        
        // Check if the file of the video object is null
        assertNull(video.getFile());
        
        // Check if the name of the video object is null
        assertNull(video.getName());
        
        // Check if the date of the video object is null
        assertNull(video.getDate());
        
        // Check if the description of the video object is null
        assertNull(video.getDescription());
        
        // Check if the tags list of the video object is empty
        assertNull(video.getTags());
        
        // Check if the rating of the video object is null
        assertNull(video.getRating());
        
        // Check if the orientation of the video object is null
        assertNull(video.getOrientation());
        
        // Check if the resolution of the video object is null
        assertNull(video.getResolution());
        
        // Check if the privacy of the video object is false
        assertFalse(video.isPrivate());
        
        // Check if the duration of the video object is 0
        assertEquals(0, video.getDuration());
    }

    /**
     * Test if the description of the video object can be changed.
     */
    @Test
    public void changeDescriptionTest() {
        String newDescription = "newDescription";
        video.setDescription(newDescription);
        
        // Check if the description of the video object has been changed to "newDescription"
        assertEquals(newDescription, video.getDescription());
    }

    /**
     * Test if the privacy of the video object can be changed.
     */
    @Test
    public void changePrivacyTest() {
        boolean newIsPrivate = true;
        video.setPrivate(newIsPrivate);
        
        // Check if the privacy of the video object has been changed to true
        assertEquals(newIsPrivate, video.isPrivate());
    }

    /**
     * Test if the rating of the video object can be changed.
     */
    @Test
    public void changeRatingTest() {
        Rating newRating = Rating.ONE_STARS;
        video.setRating(newRating);
        
        // Check if the rating of the video object has been changed to one star
        assertEquals(newRating, video.getRating());
    }

    /**
     * Test if the video can be rotated clockwise, origin is 0 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom0DegreeTest() {
        video.rotateClockwise();
        
        // Check if the orientation of the video object has been changed to 90 degrees
        assertEquals(Orientation.D90, video.getOrientation());
    }

    /**
     * Test if the video can be rotated clockwise, origin is 90 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom90DegreeTest() {
        video.setOrientation(Orientation.D90);
        video.rotateClockwise();
        
        // Check if the orientation of the video object has been changed to 180 degrees
        assertEquals(Orientation.D180, video.getOrientation());
    }

    /**
     * Test if the video can be rotated clockwise, origin is 180 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom180DegreeTest() {
        video.setOrientation(Orientation.D180);
        video.rotateClockwise();
        
        // Check if the orientation of the video object has been changed to 270 degrees
        assertEquals(Orientation.D270, video.getOrientation());
    }

    /**
     * Test if the video can be rotated clockwise, origin is 270 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateClockwiseFrom270DegreeTest() {
        video.setOrientation(Orientation.D270);
        video.rotateClockwise();
        
        // Check if the orientation of the video object has been changed to 0 degrees
        assertEquals(Orientation.D0, video.getOrientation());
    }

    /**
     * Test if the video can be rotated counter-clockwise, origin is 0 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom0DegreeTest() {
        video.rotateCounterClockwise();
        
        // Check if the orientation of the video object has been changed to 270 degrees
        assertEquals(Orientation.D270, video.getOrientation());
    }

    /**
     * Test if the video can be rotated counter-clockwise, origin is 90 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom90DegreeTest() {
        video.setOrientation(Orientation.D90);
        video.rotateCounterClockwise();
        
        // Check if the orientation of the video object has been changed to 0 degrees
        assertEquals(Orientation.D0, video.getOrientation());
    }

    /**
     * Test if the video can be rotated counter-clockwise, origin is 180 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom180DegreeTest() {
        video.setOrientation(Orientation.D180);
        video.rotateCounterClockwise();
        
        // Check if the orientation of the video object has been changed to 90 degrees
        assertEquals(Orientation.D90, video.getOrientation());
    }

    /**
     * Test if the video can be rotated counter-clockwise, origin is 270 degrees.
     *
     * @throws UnexpectedException the unexpected exception
     */
    @Test
    public void rotateCounterClockwiseFrom270DegreeTest() {
        video.setOrientation(Orientation.D270);
        video.rotateCounterClockwise();
        
        // Check if the orientation of the video object has been changed to 180 degrees
        assertEquals(Orientation.D180, video.getOrientation());
    }

    /**
     * Test if playback speed can be changed.
     */
    @Test
    public void changePlaybackSpeedTest() {
        PlaybackSpeed newPlaybackSpeed = PlaybackSpeed.X2;
        video.changePlaybackSpeed(newPlaybackSpeed);
        
        // Check if the playback speed of the video object has been changed to X2
        assertEquals(newPlaybackSpeed, video.getPlaybackSpeed());
    }

    /**
     * Test if the video can be played.
     */
    @Test
    public void playTest() {
        video.play();

        // Check if the video is playing
        assertTrue(video.isPlaying());

        video.pause();

        // Check if the video is paused
        assertFalse(video.isPlaying());

        video.play();
        video.stop();

        // Check if the video is stopped
        assertFalse(video.isPlaying());
        assertEquals(0, video.getCurrentTime());

        video.playFrom(1000);

        // Check if the video is playing from 1000 milliseconds
        assertEquals(1000, video.getCurrentTime());
    }

    /**
     * Test if playback speed can be increased.
     */
    @Test
    public void increasePlaybackSpeedTest() {
        video.increasePlaybackSpeed();

        // Check if the playback speed of the video object has been increased
        assertEquals(PlaybackSpeed.X2, video.getPlaybackSpeed());
    }

    /**
     * Test if playback speed can be decreased.
     */
    @Test
    public void decreasePlaybackSpeedTest() {
        video.decreasePlaybackSpeed();

        // Check if the playback speed of the video object has been decreased
        assertEquals(PlaybackSpeed.X05, video.getPlaybackSpeed());
    }

    /**
     * Test if playback speed can't be decreased below X0.25.
     */
    @Test
    public void decreasePlaybackSpeedToMinimumTest() {
        video.changePlaybackSpeed(PlaybackSpeed.X025);
        assertThrows(IllegalArgumentException.class, video::decreasePlaybackSpeed);
    }

    /**
     * Test if playback speed can't be increased above X32.
     */
    @Test
    public void increasePlaybackSpeedToMaximumTest() {
        video.changePlaybackSpeed(PlaybackSpeed.X32);
        assertThrows(IllegalArgumentException.class, video::increasePlaybackSpeed);
    }

    /**
     * To xml tags test.
     */
    @Test
    public void toXMLTagsTest() {
        Tag tag1 = tagController.createTag(TagType.PERSON, "Tag1", Color.WHITE);
        Tag tag2 = tagController.createTag(TagType.LOCATION, "Tag2", Color.RED);
        Tag tag3 = tagController.createTag(TagType.LOCATION, "Tag3", Color.RED);

        video.addTag(tag1);
        video.addTag(tag2);
        video.addTag(tag3);

        Document document = documentBuilder.newDocument();
        Element videoElement = video.toXML(document);

        TagList tags = video.getTags();
        NodeList tagElements = videoElement.getElementsByTagName("Tag");
        assertEquals(tags.getTags().size(), tagElements.getLength(), "Number of Tag elements should match the number of tags");

        for (int i = 0; i < tags.getTags().size(); i++) {
            Element tagElement = (Element) tagElements.item(i);
            Tag tag = tags.getTags().get(i);
            assertEquals(tag.getName(), tagElement.getAttribute("name"), "Tag name should match");
            assertEquals(tag.getType().name(), tagElement.getAttribute("type"), "Tag type should match");
        }
    }

    /**
     * Test if toXML generates a valid XML element.
     */
    @Test
    public void toXMLValidElementTest() {
        Document document = documentBuilder.newDocument();
        Element videoElement = video.toXML(document);

        assertNotNull(videoElement, "Video Element should not be null");
        assertEquals("Video", videoElement.getTagName(), "Element should be named 'Video'");

        assertEquals(TestUtilMediaController.defaultFile, videoElement.getAttribute("file"), "Attribute 'file' should match defaultFile");
        assertEquals(TestUtilMediaController.defaultName, videoElement.getAttribute("name"), "Attribute 'name' should match defaultName");
        assertEquals(TestUtilMediaController.defaultDate.toString(), videoElement.getAttribute("date"), "Attribute 'date' should match defaultDate");
        assertEquals(TestUtilMediaController.defaultDescription, videoElement.getAttribute("description"), "Attribute 'description' should match defaultDescription");
        assertEquals(String.valueOf(TestUtilMediaController.defaultIsPrivate), videoElement.getAttribute("isPrivate"), "Attribute 'isPrivate' should match defaultIsPrivate");
        assertEquals(TestUtilMediaController.defaultRating.name(), videoElement.getAttribute("rating"), "Attribute 'rating' should match defaultRating");
        assertEquals(String.valueOf(TestUtilMediaController.defaultResolution.getWidth()) + "x" + String.valueOf(TestUtilMediaController.defaultResolution.getHeight()), videoElement.getAttribute("resolution"), "Attribute 'resolution' should match defaultResolution");
        assertEquals(String.valueOf(TestUtilMediaController.defaultDuration), videoElement.getAttribute("duration"), "Attribute 'duration' should match defaultDuration");
        assertEquals(TestUtilMediaController.defaultOrientation.name(), videoElement.getAttribute("orientation"), "Attribute 'orientation' should match defaultOrientation");

        TagList tags = video.getTags();
        assertEquals(0, videoElement.getElementsByTagName("Tag").getLength(), "Should have 0 Tag elements");
    }

    private Element createValidVideoElement(Document document) {
        Element videoElement = document.createElement("Video");
        videoElement.setAttribute("file", TestUtilMediaController.defaultFile);
        videoElement.setAttribute("name", TestUtilMediaController.defaultName);
        videoElement.setAttribute("date", TestUtilMediaController.defaultDate.toString());
        videoElement.setAttribute("description", TestUtilMediaController.defaultDescription);
        videoElement.setAttribute("isPrivate", String.valueOf(TestUtilMediaController.defaultIsPrivate));
        videoElement.setAttribute("rating", TestUtilMediaController.defaultRating.name());
        videoElement.setAttribute("duration", String.valueOf(TestUtilMediaController.defaultDuration));
        videoElement.setAttribute("orientation", TestUtilMediaController.defaultOrientation.name());
        videoElement.setAttribute("resolution", TestUtilMediaController.defaultResolution.getWidth() + "x" + TestUtilMediaController.defaultResolution.getHeight());

        Element tagElement1 = document.createElement("Tag");
        tagElement1.setAttribute("type", TagType.PERSON.name());
        tagElement1.setAttribute("name", "Tag1");
        tagElement1.setAttribute("color", "#FF0000");
        videoElement.appendChild(tagElement1);

        Element tagElement2 = document.createElement("Tag");
        tagElement2.setAttribute("type", TagType.LOCATION.name());
        tagElement2.setAttribute("name", "Tag2");
        tagElement2.setAttribute("color", "#FF0000");
        videoElement.appendChild(tagElement2);

        return videoElement;
    }

    /**
     * From xml valid element test.
     */
    @Test
    public void fromXMLValidElementTest() {
        Document document = documentBuilder.newDocument();
        Element videoElement = createValidVideoElement(document);

        Video parsedVideo = Video.fromXML(videoElement);

        assertNotNull(parsedVideo, "Parsed Video should not be null");

        assertEquals(TestUtilMediaController.defaultFile, parsedVideo.getFile(), "File should match defaultFile");
        assertEquals(TestUtilMediaController.defaultName, parsedVideo.getName(), "Name should match defaultName");
        assertEquals(TestUtilMediaController.defaultDate, parsedVideo.getDate(), "Date should match defaultDate");
        assertEquals(TestUtilMediaController.defaultDescription, parsedVideo.getDescription(), "Description should match defaultDescription");
        assertEquals(TestUtilMediaController.defaultIsPrivate, parsedVideo.isPrivate(), "isPrivate should match defaultIsPrivate");
        assertEquals(TestUtilMediaController.defaultRating, parsedVideo.getRating(), "Rating should match defaultRating");
        assertEquals(TestUtilMediaController.defaultResolution, parsedVideo.getResolution(), "Resolution should match defaultResolution");
        assertEquals(TestUtilMediaController.defaultDuration, parsedVideo.getDuration(), "Duration should match defaultDuration");
        assertEquals(TestUtilMediaController.defaultOrientation, parsedVideo.getOrientation(), "Orientation should match defaultOrientation");

        TagList parsedTags = parsedVideo.getTags();
        assertEquals(2, parsedTags.getTags().size(), "Should have 2 tags");

        assertEquals("Tag1", parsedTags.getTags().get(0).getName(), "First tag name should be 'Tag1'");
        assertEquals(TagType.PERSON, parsedTags.getTags().get(0).getType(), "First tag type should be PERSON");

        assertEquals("Tag2", parsedTags.getTags().get(1).getName(), "Second tag name should be 'Tag2'");
        assertEquals(TagType.LOCATION, parsedTags.getTags().get(1).getType(), "Second tag type should be LOCATION");
    }

    /**
     * From xml invalid attributes test.
     */
    @Test
    public void fromXMLInvalidAttributesTest() {
        // Setup: Create a valid XML element with invalid attributes
        Document document = documentBuilder.newDocument();
        Element videoElement = createValidVideoElement(document);
        videoElement.setAttribute("date", "InvalidDate");

        // Assertion: Check exception handling
        assertThrows(DateTimeParseException.class, () -> Video.fromXML(videoElement), "Invalid date format should throw DateTimeParseException");
    }
}
