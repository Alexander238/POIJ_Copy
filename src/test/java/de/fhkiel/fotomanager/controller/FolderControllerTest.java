package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.FolderController;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Folder controller test.
 */
public class FolderControllerTest {
    /**
     * The Folder controller.
     */
    private static FolderController folderController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        folderController = FolderController.get();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        FolderController anotherInstance = FolderController.get();
        assertNotNull(folderController, "FolderController instance should not be null");
        assertSame(folderController, anotherInstance, "FolderController instance should be the same");
    }

    /**
     * Create folder test.
     */
    @Test
    public void createFolderTest() {
        LocalDate testDate = LocalDate.now();
        Either<LocalDate, Period> date = Either.left(testDate);
        Folder folder = folderController.createFolder("Folder", date, "Event");
        assertNotNull(folder, "Folder should not be null");
        assertEquals("Folder", folder.getName(), "Folder should have the name 'Folder'");
        assertEquals("Event", folder.getEvent(), "Folder should have the event 'Event'");
        assertEquals(testDate, folder.getDate().getLeft(), "Folder should have the date " + testDate);
    }

    /**
     * Create folder with null name test.
     */
    @Test
    public void createFolderWithNullNameTest() {
        assertThrows(IllegalArgumentException.class, () -> folderController.createFolder(null, Either.left(LocalDate.now()), "Event"), "Album name should not be null");
    }

    /**
     * Create folder with empty name test.
     */
    @Test
    public void createFolderWithEmptyNameTest() {
        assertThrows(IllegalArgumentException.class, () -> folderController.createFolder("", Either.left(LocalDate.now()), "Event"), "Album name should not be empty");
    }
}
