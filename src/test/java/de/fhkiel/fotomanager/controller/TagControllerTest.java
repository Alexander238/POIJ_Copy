package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.controller.modelController.MediaController;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.TagController;
import de.fhkiel.fotomanager.model.tags.Tag;
import de.fhkiel.fotomanager.model.tags.TagType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Tag controller test.
 */
public class TagControllerTest {
    /**
     * The Tag controller.
     */
    private static TagController tagController;
    /**
     * The Media controller.
     */
    private static MediaController mediaController;
    /**
     * The Photo manager controller.
     */
    private static PhotoManagerController photoManagerController;
    /**
     * The Album controller.
     */
    private static AlbumController albumController;

    /**
     * Sets up.
     */
    @BeforeAll
    static void setUp() {
        tagController = TagController.get();
        mediaController = MediaController.get();
        photoManagerController = PhotoManagerController.get();
        albumController = AlbumController.get();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        TagController anotherInstance = TagController.get();
        assertNotNull(tagController, "TagController instance should not be null");
        assertSame(tagController, anotherInstance, "TagController instance should be the same");
    }

    /**
     * Create index test.
     */
    @Test
    public void createIndexTest() {
        String name = "Index";
        Tag tag = tagController.createTag(TagType.INDEX, name);
        assertNotNull(tag, "Tag should not be null");
        assertEquals(name, tag.getName(), "Tag name should be 'Index'");
        assertEquals(Color.BLACK, tag.getColor(), "Tag color should be WHITE");
        assertTrue(tag.getType() == TagType.INDEX, "Tag should be instance of Index");
    }

    /**
     * Create adjective test.
     */
    @Test
    public void createAdjectiveTest() {
        String name = "Adjective";
        Tag tag = tagController.createTag(TagType.ADJECTIVE, name);
        assertNotNull(tag, "Tag should not be null");
        assertEquals(name, tag.getName(), "Tag name should be 'Adjective'");
        assertEquals(Color.BLACK, tag.getColor(), "Tag color should be WHITE");
        assertTrue(tag.getType() == TagType.ADJECTIVE, "Tag should be instance of Adjective");
    }

    /**
     * Create location test.
     */
    @Test
    public void createLocationTest() {
        String name = "Location";
        Tag tag = tagController.createTag(TagType.LOCATION, name);
        assertNotNull(tag, "Tag should not be null");
        assertEquals(name, tag.getName(), "Tag name should be 'Location'");
        assertEquals(Color.BLACK, tag.getColor(), "Tag color should be WHITE");
        assertTrue(tag.getType() == TagType.LOCATION, "Tag should be instance of Location");
    }

    /**
     * Create person test.
     */
    @Test
    public void createPersonTest() {
        String name = "Person";
        Tag tag = tagController.createTag(TagType.PERSON, name);
        assertNotNull(tag, "Tag should not be null");
        assertEquals(name, tag.getName(), "Tag name should be 'Person'");
        assertEquals(Color.BLACK, tag.getColor(), "Tag color should be WHITE");
        assertTrue(tag.getType() == TagType.PERSON, "Tag should be instance of Person");
    }

    /**
     * Rename tag tag test.
     */
    @Test
    public void renameTagTagTest() {
        String name = "Index";
        Tag tag = tagController.createTag(TagType.INDEX, name);
        String newName = "New Index";
        tagController.renameTag(tag, newName);
        assertEquals(newName, tag.getName(), "Tag name should be 'New Index'");
    }

    /**
     * Change tag color test.
     */
    @Test
    public void changeTagColorTest() {
        String name = "Index";
        Tag tag = tagController.createTag(TagType.INDEX, name);
        Color newColor = Color.BLACK;
        tagController.changeTagColor(tag, newColor);
        assertEquals(newColor, tag.getColor(), "Tag color should be BLACK");
    }

    /**
     * Delete tag tag test.
     */
    /* needs adjustment because of XML
    @Test
    public void deleteTagTagTest() {
        String name = "Index";
        Tag tag = tagController.createTag(TagType.INDEX, name);
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        mediaController.addTag(photo, tag);
        Album album = albumController.createAlbum("Test Album");
        albumController.addMediaToAlbum(album, photo);
        photoManagerController.addDataStructure(album);
        photoManagerController.addPhoto(photo);
        tagController.deleteTag(tag);
        photoManagerController.getPhotoManager().getPhotos().forEach(p -> assertFalse(p.getTags().contains(tag), "Photo should not contain tag"));
        photoManagerController.getPhotoManager().getVideos().forEach(p -> assertFalse(p.getTags().contains(tag), "Video should not contain tag"));
        List<Album> albums = photoManagerController.getPhotoManager().getAlbums();
        for (DataStructure a : albums) {
            List<Media> mediaList = ((Album) a).getMediaList();
            mediaList.forEach(media -> assertFalse(media.getTags().contains(tag), "Media should not contain tag"));
        }
        assertNull(tag.getName(), "Tag name should be null");
        assertNull(tag.getColor(), "Tag color should be null");
        assertNull(tag.getType(), "Tag type should be null");
    }
     */
}
