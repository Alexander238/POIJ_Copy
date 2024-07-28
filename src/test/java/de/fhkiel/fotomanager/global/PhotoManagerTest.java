package de.fhkiel.fotomanager.global;

import de.fhkiel.fotomanager.PhotoManager;
import de.fhkiel.fotomanager.controller.modelController.AlbumController;
import de.fhkiel.fotomanager.controller.modelController.FolderController;
import de.fhkiel.fotomanager.controller.modelController.SlideshowController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Photo manager test.
 */
public class PhotoManagerTest {
    /**
     * The Photo manager to test.
     */
    private static PhotoManager photoManager;
    /**
     * The Album controller.
     */
    private static AlbumController albumController;
    /**
     * The Slideshow controller.
     */
    private static SlideshowController slideshowController;
    /**
     * The Folder controller.
     */
    private static FolderController folderController;

    /**
     * Sets up.
     * Initializes the controllers.
     * Gets the instance of the PhotoManager.
     */
    @BeforeAll
    public static void setUp() {
        photoManager = PhotoManager.getInstance();
        albumController = AlbumController.get();
        slideshowController = SlideshowController.get();
        folderController = FolderController.get();
    }

    /**
     * Before each.
     * Clears the data structures in the PhotoManager.
     */
    @BeforeEach
    public void beforeEach() {
        photoManager.getAlbums().clear();
        photoManager.getFolders().clear();
        photoManager.getPhotos().clear();
        photoManager.getSlideshows().clear();
        photoManager.getVideos().clear();
    }

    /**
     * Test singleton.
     */
    @Test
    public void singletonInstanceTest() {
        PhotoManager anotherInstance = PhotoManager.getInstance();
        assertNotNull(photoManager, "PhotoManager instance should not be null");
        assertSame(photoManager, anotherInstance, "PhotoManager instance should be the same");
    }

    /**
     * Test add data structure album.
     */
    /* Not needed for the task -> TODO: if time
    @Test
    public void addDataStructureFolderTest() {
        Folder folder = folderController.createFolder("Daniel", Either.left(LocalDate.now()), "Summer of '69");

        // Check if the folder is added to the list of folders
        assertTrue(photoManager.addDataStructure(folder));

        // Check if the folder is in the list of folders
        assertTrue(photoManager.getFolders().contains(folder));
    }
     */

    /**
     * Test add data structure duplicate folder.
     */
    /* Not needed for the task -> TODO: if time
    @Test
    public void testAddDataStructureDuplicateFolder() {
        Folder folder = folderController.createFolder("Daniel", Either.left(LocalDate.now()), "Summer of '69");

        // Check if the folder is added to the list of folders
        assertTrue(photoManager.addDataStructure(folder));

        // Check if exception is thrown when trying to add the same folder again
        assertThrows(IllegalArgumentException.class, () -> photoManager.addDataStructure(folder));
    }
     */
}
