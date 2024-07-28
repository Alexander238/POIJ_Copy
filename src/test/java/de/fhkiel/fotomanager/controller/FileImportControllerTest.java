package de.fhkiel.fotomanager.controller;

import de.fhkiel.fotomanager.controller.modelController.FileImportController;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type File import controller test.
 */
@ExtendWith(MockitoExtension.class)
public class FileImportControllerTest {

    @Mock
    private FileImportController fileImportController; // Mock the controller itself

    @Mock
    private File mockImageFile;

    @Mock
    private File mockVideoFile;

    /**
     * Test the getPhotoResolution method.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetPhotoResolution() throws IOException {
        Resolution expectedResolution = new Resolution(1920, 1080);
        when(fileImportController.getPhotoResolution(mockImageFile)).thenReturn(expectedResolution);

        Resolution resolution = fileImportController.getPhotoResolution(mockImageFile);
        assertNotNull(resolution, "Resolution should not be null");
        assertEquals(1920, resolution.getWidth(), "Width should be 1920");
        assertEquals(1080, resolution.getHeight(), "Height should be 1080");
    }

    /**
     * Test the getVideoResolution method.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetVideoResolution() throws IOException {
        Resolution expectedResolution = new Resolution(1280, 720);
        when(fileImportController.getVideoResolution(mockVideoFile)).thenReturn(expectedResolution);

        Resolution resolution = fileImportController.getVideoResolution(mockVideoFile);
        assertNotNull(resolution, "Resolution should not be null");
        assertEquals(1280, resolution.getWidth(), "Width should be 1280");
        assertEquals(720, resolution.getHeight(), "Height should be 720");
    }

    /**
     * Test the getVideoDuration method.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetVideoDuration() throws IOException {
        long expectedDuration = 60000; // 1 minute in milliseconds
        when(fileImportController.getVideoDuration(mockVideoFile)).thenReturn(expectedDuration);

        long duration = fileImportController.getVideoDuration(mockVideoFile);
        assertTrue(duration > 0, "Duration should be greater than 0");
        assertEquals(60000, duration, "Duration should be 60000 milliseconds");
    }

    /**
     * Test the getFileCreationDate method.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetFileCreationDate() throws IOException {
        LocalDate expectedCreationDate = LocalDate.of(2022, 7, 1);
        when(fileImportController.getFileCreationDate(mockImageFile)).thenReturn(expectedCreationDate);

        LocalDate creationDate = fileImportController.getFileCreationDate(mockImageFile);
        assertNotNull(creationDate, "Creation date should not be null");
        assertEquals(expectedCreationDate, creationDate, "Creation date should be 2022-07-01");
    }
}
