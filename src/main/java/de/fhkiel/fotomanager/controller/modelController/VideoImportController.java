package de.fhkiel.fotomanager.controller.modelController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The type Video import controller.
 */
public class VideoImportController {
    /**
     * The instance.
     */
    private static VideoImportController instance;

    private VideoImportController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static VideoImportController get() {
        if (instance == null) {
            instance = new VideoImportController();
        }
        return instance;
    }

    /**
     * Import video files from the source folder to the destination folder.
     *
     * @param sourceFolderPath the source folder path
     * @param destFolderPath   the dest folder path
     * @throws IOException the io exception
     */
    public void importVideoFiles(String sourceFolderPath, String destFolderPath) throws IOException {
        try {
            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(destFolderPath);

            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("Source Folder not found");
            }

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            copyFilesInsideFolder(sourceFolder, destinationFolder);

            System.out.println("Folder imported into: " + destinationFolder);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Copies all video files from the source folder to the destination folder.
     *
     * @param sourceFolder      the source folder
     * @param destinationFolder the destination folder
     * @throws IOException the io exception
     */
    private void copyFilesInsideFolder(File sourceFolder, File destinationFolder) throws IOException {
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        File[] files = sourceFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                File destFile = new File(destinationFolder, file.getName());
                if (file.isDirectory()) {
                    copyFilesInsideFolder(file, destFile);
                } else if (isVideoFile(file)) {
                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * Checks if the file is a video file.
     *
     * @param file the file
     * @return the boolean
     */
    private boolean isVideoFile(File file) {
        String[] videoExtensions = {".mp4", ".avi", ".mkv", ".mov", ".wmv", ".flv"};
        for (String extension : videoExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}