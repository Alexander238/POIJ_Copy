package de.fhkiel.fotomanager.model.mediatypes;

import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Media test.
 */
public class MediaTest {

    /**
     * TagController is needed to create Tags for the tests.
     */
    private static TagController tagController;

    /**
     * The media object that is used for the tests.
     */
    private static Media media;

    /**
     * Sets up.
     */
// Set up TagController before running the tests by getting the instance of it
    @BeforeAll
    public static void setUp() {
        tagController = TagController.get();
    }

    /**
     * Before each.
     */
// Set up a new media object before each test and clear the tagList
    @BeforeEach
    public void beforeEach() {
        media = TestUtilMediaController.createDefaultPhoto();
        media.setTags(TagListController.get().createEmptyTagList());
    }

    /**
     * Rename media test.
     */
// Test if the media object can be renamed
    @Test
    public void renameMediaTest() {
        media.rename("newName");

        // Check if the name of the media object has been changed to the new name
        assertEquals("newName", media.getName());
    }

    /**
     * Add tag test.
     */
// Test if a Tag can be added to a media object
    @Test
    public void addTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        media.addTag(tag);

        // Check if the tags list of the media object has a size of 1
        assertEquals(1, media.getTags().getTags().size());

        // Check if the media object has the added Tag
        assertTrue(media.getTags().getTags().contains(tag));
    }

    /**
     * Remove tag test.
     */
// Test if a Tag can be removed from a media object
    @Test
    public void removeTagTest() {
        Tag tag = tagController.createTag(TagType.INDEX, "newTag");
        media.addTag(tag);
        media.removeTag(tag);

        // Check if the tags list of the media object is empty
        assertEquals(0, media.getTags().getTags().size());

        // Check if the media object does not have the removed Tag
        assertFalse(media.getTags().getTags().contains(tag));
    }

    /**
     * Delete media test.
     */
// Test if a Media object can be deleted and all its attributes are set to null or false
    @Test
    public void deleteMediaTest() {
        // Check if delete function returned true
        assertTrue(media.delete());

        // Check if the file of the media object is null
        assertNull(media.getFile());

        // Check if the name of the media object is null
        assertNull(media.getName());

        // Check if the date of the media object is null
        assertNull(media.getDate());

        // Check if the description of the media object is null
        assertNull(media.getDescription());

        // Check if the tags list of the media object is empty
        assertNull(media.getTags());

        // Check if the rating of the media object is null
        assertNull(media.getRating());

        // Check if the orientation of the media object is null
        assertNull(media.getOrientation());

        // Check if the resolution of the media object is null
        assertNull(media.getResolution());

        // Check if the isPrivate attribute of the media object is false
        assertFalse(media.isPrivate());
    }

    /**
     * Change description test.
     */
// Test if the description can be changed
    @Test
    public void changeDescriptionTest() {
        String newDescription = "newDescription";
        media.setDescription(newDescription);

        // Check if the description of the media object has been changed to "newDescription"
        assertEquals(newDescription, media.getDescription());
    }

    /**
     * Change privacy test.
     */
// Test if the isPrivate attribute can be changed
    @Test
    public void changePrivacyTest() {
        boolean newIsPrivate = true;
        media.setPrivate(newIsPrivate);

        // Check if the isPrivate attribute of the media object has been changed to the true
        assertEquals(newIsPrivate, media.isPrivate());
    }

    /**
     * Change rating test.
     */
// Test if the rating can be changed
    @Test
    public void changeRatingTest() {
        Rating newRating = Rating.ONE_STARS;
        media.setRating(newRating);

        // Check if the rating of the media object has been changed to a one-star rating
        assertEquals(newRating, media.getRating());
    }

    /**
     * Rotate clockwise from 0 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated clockwise, origin is 0 degrees
    @Test
    public void rotateClockwiseFrom0DegreeTest() {
        media.rotateClockwise();

        // Check if the orientation of the media object has been changed to 90 degrees
        assertEquals(Orientation.D90, media.getOrientation());
    }

    /**
     * Rotate clockwise from 90 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated clockwise, origin is 90 degrees
    @Test
    public void rotateClockwiseFrom90DegreeTest() {
        media.setOrientation(Orientation.D90);
        media.rotateClockwise();

        // Check if the orientation of the media object has been changed to 180 degrees
        assertEquals(Orientation.D180, media.getOrientation());
    }

    /**
     * Rotate clockwise from 180 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated clockwise, origin is 180 degrees
    @Test
    public void rotateClockwiseFrom180DegreeTest() {
        media.setOrientation(Orientation.D180);
        media.rotateClockwise();

        // Check if the orientation of the media object has been changed to 270 degrees
        assertEquals(Orientation.D270, media.getOrientation());
    }

    /**
     * Rotate clockwise from 270 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated clockwise, origin is 270 degrees
    @Test
    public void rotateClockwiseFrom270DegreeTest() {
        media.setOrientation(Orientation.D270);
        media.rotateClockwise();

        // Check if the orientation of the media object has been changed to 0 degrees
        assertEquals(Orientation.D0, media.getOrientation());
    }

    /**
     * Rotate counter clockwise from 0 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated counter-clockwise, origin is 0 degrees
    @Test
    public void rotateCounterClockwiseFrom0DegreeTest() {
        media.rotateCounterClockwise();

        // Check if the orientation of the media object has been changed to 270 degrees
        assertEquals(Orientation.D270, media.getOrientation());
    }

    /**
     * Rotate counter clockwise from 90 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated counter-clockwise, origin is 90 degrees
    @Test
    public void rotateCounterClockwiseFrom90DegreeTest() {
        media.setOrientation(Orientation.D90);
        media.rotateCounterClockwise();

        // Check if the orientation of the media object has been changed to 0 degrees
        assertEquals(Orientation.D0, media.getOrientation());
    }

    /**
     * Rotate counter clockwise from 180 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated counter-clockwise, origin is 180 degrees
    @Test
    public void rotateCounterClockwiseFrom180DegreeTest() {
        media.setOrientation(Orientation.D180);
        media.rotateCounterClockwise();

        // Check if the orientation of the media object has been changed to 90 degrees
        assertEquals(Orientation.D90, media.getOrientation());
    }

    /**
     * Rotate counter clockwise from 270 degree test.
     *
     * @throws UnexpectedException the unexpected exception
     */
// Test if the media can be rotated counter-clockwise, origin is 270 degrees
    @Test
    public void rotateCounterClockwiseFrom270DegreeTest() {
        media.setOrientation(Orientation.D270);
        media.rotateCounterClockwise();

        // Check if the orientation of the media object has been changed to 180 degrees
        assertEquals(Orientation.D180, media.getOrientation());
    }

    /**
     * Equals test.
     */
// Test if the equals function works as expected
    @Test
    public void equalsTest() {
        // Check if the media object is equal to itself
        assertTrue(media.equals(media));

        // Check if the media object is not equal to a different object
        assertFalse(media.equals(new String("test")));

        // Check if the media object is equal to a media object with the same attributes
        assertTrue(media.equals(TestUtilMediaController.createDefaultPhoto()));
    }
}
