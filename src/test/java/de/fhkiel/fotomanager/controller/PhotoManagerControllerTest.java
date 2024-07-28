package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.controller.modelController.FolderController;
import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.controller.modelController.SlideshowController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Photo manager controller test.
 */
public class PhotoManagerControllerTest {
    /**
     * The Photo manager controller.
     */
    private static PhotoManagerController photoManagerController;
    /**
     * The Album controller.
     */
    private static AlbumController albumController;
    /**
     * The Folder controller.
     */
    private static FolderController folderController;
    /**
     * The Slideshow controller.
     */
    private static SlideshowController slideshowController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        photoManagerController = PhotoManagerController.get();
        albumController = AlbumController.get();
        folderController = FolderController.get();
        slideshowController = SlideshowController.get();
    }

    /**
     * Clear data.
     */
    @BeforeEach
    public void clearData() {
        photoManagerController.getPhotoManager().getAlbums().clear();
        photoManagerController.getPhotoManager().getFolders().clear();
        photoManagerController.getPhotoManager().getSlideshows().clear();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        PhotoManagerController anotherInstance = PhotoManagerController.get();
        assertNotNull(photoManagerController, "PhotoManagerController instance should not be null");
        assertSame(photoManagerController, anotherInstance, "PhotoManagerController instance should be the same");
    }

    /**
     * Handle add album test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddAlbumTest() {
        Album album = albumController.createAlbum("Album");
        photoManagerController.addDataStructure(album);
        assertNotNull(photoManagerController.getPhotoManager().getAlbums(), "Albums should not be null");
        assertSame(album, photoManagerController.getPhotoManager().getAlbums().get(0), "Album should be added to Albums");
        assertEquals(1, photoManagerController.getPhotoManager().getAlbums().size(), "Albums should contain 1 Album");
    }
     */

    /**
     * Handle add folder test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddFolderTest() {
        Folder folder = folderController.createFolder("Folder", null, "Event");
        photoManagerController.addDataStructure(folder);
        assertNotNull(photoManagerController.getPhotoManager().getFolders(), "Folders should not be null");
        assertSame(folder, photoManagerController.getPhotoManager().getFolders().get(0), "Folder should be added to Folders");
        assertEquals(1, photoManagerController.getPhotoManager().getFolders().size(), "Folders should contain 1 Folder");
    }
     */

    /**
     * Handle add slideshow test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        photoManagerController.addDataStructure(slideshow);
        assertNotNull(photoManagerController.getPhotoManager().getSlideshows(), "Slideshows should not be null");
        assertSame(slideshow, photoManagerController.getPhotoManager().getSlideshows().get(0), "Slideshow should be added to Slideshows");
        assertEquals(1, photoManagerController.getPhotoManager().getSlideshows().size(), "Slideshows should contain 1 Slideshow");
    }
     */

    /**
     * Handle add duplicate album test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddDuplicateAlbumTest() {
        Album album = albumController.createAlbum("Album");
        Album duplicateAlbum = albumController.createAlbum("Album");
        photoManagerController.addDataStructure(album);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.addDataStructure(duplicateAlbum), "Adding a duplicate Album should throw an IllegalArgumentException");
    }
     */

    /**
     * Handle add duplicate folder test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddDuplicateFolderTest() {
        Folder folder = folderController.createFolder("Folder", null, "Event");
        Folder duplicateFolder = folderController.createFolder("Folder", null, "Event");
        photoManagerController.addDataStructure(folder);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.addDataStructure(duplicateFolder), "Adding a duplicate Folder should throw an IllegalArgumentException");
    }
     */

    /**
     * Handle add duplicate slideshow test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleAddDuplicateSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        Slideshow duplicateSlideshow = slideshowController.createSlideshow("Slideshow", 5);
        photoManagerController.addDataStructure(slideshow);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.addDataStructure(duplicateSlideshow), "Adding a duplicate Slideshow should throw an IllegalArgumentException");
    }
     */

    /**
     * Handle rename album test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameAlbumTest() {
        Album album = albumController.createAlbum("Album");
        photoManagerController.addDataStructure(album);
        photoManagerController.renameDataStructure(album, "New Album");
        assertEquals("New Album", album.getName(), "Album should have the new name 'New Album'");
    }
     */

    /**
     * Handle rename folder test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameFolderTest() {
        Folder folder = folderController.createFolder("Folder", null, "Event");
        photoManagerController.addDataStructure(folder);
        photoManagerController.renameDataStructure(folder, "New Folder");
        assertEquals("New Folder", folder.getName(), "Folder should have the new name 'New Folder'");
    }
     */

    /**
     * Handle rename slideshow test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        photoManagerController.addDataStructure(slideshow);
        photoManagerController.renameDataStructure(slideshow, "New Slideshow");
        assertEquals("New Slideshow", slideshow.getName(), "Slideshow should have the new name 'New Slideshow'");
    }
     */

    /**
     * Handle rename to duplicate album test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameToDuplicateAlbumTest() {
        Album album = albumController.createAlbum("Album");
        Album duplicateAlbum = albumController.createAlbum("Duplicate Album");
        photoManagerController.addDataStructure(album);
        photoManagerController.addDataStructure(duplicateAlbum);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.renameDataStructure(album, "Duplicate Album"), "Renaming to an existing Album name should throw an IllegalArgumentException");
    }
     */

    /**
     * Handle rename to duplicate folder test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameToDuplicateFolderTest() {
        Folder folder = folderController.createFolder("Folder", null, "Event");
        Folder duplicateFolder = folderController.createFolder("Duplicate Folder", null, "Event");
        photoManagerController.addDataStructure(folder);
        photoManagerController.addDataStructure(duplicateFolder);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.renameDataStructure(folder, "Duplicate Folder"), "Renaming to an existing Folder name should throw an IllegalArgumentException");
    }
     */

    /**
     * Handle rename to duplicate slideshow test.
     */
    /* needs adjustment because of XML
    @Test
    public void handleRenameToDuplicateSlideshowTest() {
        Slideshow slideshow = slideshowController.createSlideshow("Slideshow", 5);
        Slideshow duplicateSlideshow = slideshowController.createSlideshow("Duplicate Slideshow", 5);
        photoManagerController.addDataStructure(slideshow);
        photoManagerController.addDataStructure(duplicateSlideshow);
        assertThrows(IllegalArgumentException.class, () -> photoManagerController.renameDataStructure(slideshow, "Duplicate Slideshow"), "Renaming to an existing Slideshow name should throw an IllegalArgumentException");
    }
     */
}
