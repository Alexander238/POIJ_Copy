package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.XMLController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Xml controller test.
 */
public class XMLControllerTest {
    /**
     * The Xml controller.
     */
    private static XMLController xmlController;

    /**
     * Sets up.
     */
    @BeforeAll
    public static void setUp() {
        xmlController = XMLController.get();
    }

    @AfterAll
    public static void tearDown() {
        xmlController = null;
        // delete ROOT directory
        File file = new File("ROOT");
        deleteDir(file);
    }

    /**
     * Delete dir after testing
     * @param file the file
     */
    private static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    /**
     * Gets singleton instance test.
     */
    @Test
    public void getSingletonInstanceTest() {
        XMLController anotherInstance = XMLController.get();
        assertNotNull(xmlController, "XMLController instance should not be null");
        assertSame(xmlController, anotherInstance, "XMLController instance should be the same");
    }

    /**
     * Create xml file test.
     *
     * @param tempDir the temp dir
     * @throws IOException the io exception
     */
    @Test
    public void createXMLFileTest(@TempDir Path tempDir) throws IOException {
        String filePath = "ROOT";
        xmlController.exportXMLFiles(filePath);

        File expectedAlbumsXML = new File(filePath + File.separator + "albums.xml");
        File expectedFoldersXML = new File(filePath + File.separator + "folders.xml");
        File expectedPhotosXML = new File(filePath + File.separator + "photos.xml");
        File expectedSlideshowsXML = new File(filePath + File.separator + "slideshows.xml");
        File expectedTagsXML = new File(filePath + File.separator + "tags.xml");
        File expectedVideosXML = new File(filePath + File.separator + "videos.xml");

        // Check if file were created successfully
        assertTrue(expectedAlbumsXML.exists(), "XML file should exist in the expected location");
        assertTrue(expectedFoldersXML.exists(), "XML file should exist in the expected location");
        assertTrue(expectedPhotosXML.exists(), "XML file should exist in the expected location");
        assertTrue(expectedSlideshowsXML.exists(), "XML file should exist in the expected location");
        assertTrue(expectedTagsXML.exists(), "XML file should exist in the expected location");
        assertTrue(expectedVideosXML.exists(), "XML file should exist in the expected location");
    }
}
