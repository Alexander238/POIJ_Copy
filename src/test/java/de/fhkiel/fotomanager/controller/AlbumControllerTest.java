package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.util.TestUtilMediaController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Album controller test.
 */
public class AlbumControllerTest {
    /**
     * The Album controller.
     */
    private static AlbumController albumController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        albumController = AlbumController.get();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        AlbumController anotherInstance = AlbumController.get();
        assertNotNull(albumController, "AlbumController instance should not be null");
        assertSame(albumController, anotherInstance, "AlbumController instance should be the same");
    }

    /**
     * Create album test.
     */
    @Test
    public void createAlbumTest() {
        Album album = albumController.createAlbum("Album");
        assertNotNull(album, "Album should not be null");
        assertEquals("Album", album.getName(), "Album should have the name 'Album'");
    }

    /**
     * Create album with null name test.
     */
    @Test
    public void createAlbumWithNullNameTest() {
        assertThrows(IllegalArgumentException.class, () -> albumController.createAlbum(null), "Album name should not be null");
    }

    /**
     * Create album with empty name test.
     */
    @Test
    public void createAlbumWithEmptyNameTest() {
        assertThrows(IllegalArgumentException.class, () -> albumController.createAlbum(""), "Album name should not be empty");
    }

    /**
     * Rename album test.
     */
    @Test
    public void renameAlbumTest() {
        Album album = albumController.createAlbum("Album");
        albumController.renameAlbum(album, "New Album");
        assertEquals("New Album", album.getName(), "Album should have the new name 'New Album'");
    }

    /**
     * Rename album with null name test.
     */
    @Test
    void renameAlbumWithNullNameTest() {
        Album album = albumController.createAlbum("Album");
        assertThrows(IllegalArgumentException.class, () -> albumController.renameAlbum(album, null));
    }

    /**
     * Rename album with empty name test.
     */
    @Test
    void renameAlbumWithEmptyNameTest() {
        Album album = albumController.createAlbum("Album");
        assertThrows(IllegalArgumentException.class, () -> albumController.renameAlbum(album, ""));
    }

    /**
     * Delete album album test.
     */
    @Test
    public void deleteAlbumAlbumTest() {
        Album album = albumController.createAlbum("Album");
        Photo photo = TestUtilMediaController.createDefaultPhoto();
        Video video = TestUtilMediaController.createDefaultVideo();
        album.addMedia(photo);
        album.addMedia(video);
        albumController.deleteAlbum(album);
        assertEquals(List.of(), album.getMediaList(), "Media list should be null");
        assertNull(album.getName(), "Name should be null");
    }
}
