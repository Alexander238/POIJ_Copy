package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.Period;
import de.fhkiel.fotomanager.model.taglists.TagList;
import io.vavr.control.Either;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.DemuxerTrackMeta;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type File import controller.
 */
public class FileImportController {
    /**
     * The Instance.
     */
    public static FileImportController instance;

    private FileImportController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized FileImportController get() {
        if (instance == null) {
            instance = new FileImportController();
        }
        return instance;
    }

    /**
     * Import image files from a source folder to a destination folder.
     *
     * @param sourceFolderPath the source folder path
     * @param destFolderPath   the dest folder path
     * @throws IOException the io exception
     */
    public void importImageFiles(String sourceFolderPath, String destFolderPath) throws IOException {
        try {
            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(destFolderPath);

            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("Source Folder not found");
            }

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            copyImageFiles(sourceFolder, destinationFolder);

            System.out.println("Image files imported into: " + destinationFolder);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Function to import files and folders from a source folder to a destination folder.
     *
     * @param sourceFolderPath the source path
     * @param destFolderPath   the destination path
     * @throws IOException the io exception
     */
    public void importFilesAndFolders(String sourceFolderPath, String destFolderPath) throws IOException {
        try {

            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(destFolderPath);

            if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
                throw new IllegalArgumentException("Source Folder not found");
            }

            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            File destFolderWithSourceName = new File(destinationFolder, sourceFolder.getName());
            copyFilesInsideFolder(sourceFolder, destFolderWithSourceName);

            System.out.println("Folder and files imported into: " + destFolderWithSourceName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Copy files from a source folder to a destination folder.
     *
     * @param sourceFolder      the source folder
     * @param destinationFolder the destination folder
     * @throws IOException the io exception
     */
    private void copyImageFiles(File sourceFolder, File destinationFolder) throws IOException {
        File[] files = sourceFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && (isImage(file) || isVideo(file))) {
                    File destFile = new File(destinationFolder, file.getName());
                    Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    /**
     * Parse date from string.
     *
     * @param date the date string
     * @return the local date object
     */
    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        if (date.chars().filter(ch -> ch == '.').count() == 1) {
            date += "." + LocalDate.now().getYear();
        }
        return LocalDate.parse(date, formatter);
    }

    /**
     * Create folder folder.
     *
     * @param folder the folder
     * @return the folder
     * @throws IOException the io exception
     */
    public Folder createFolder(File folder) throws IOException {
        Path folderPath = Paths.get(folder.toURI());
        try {
            BasicFileAttributes attributes = Files.readAttributes(folderPath, BasicFileAttributes.class);
            String name = folderPath.getFileName().toString();

            // Parse the name to extract event and date(s)
            String eventName;
            Either<LocalDate, Period> date;

            // Regular expression to match the folder names
            Pattern pattern = Pattern.compile("^(.*?)(\\d{1,2}\\.\\d{1,2}(?:\\.\\d{4})?)(?:-(\\d{1,2}\\.\\d{1,2}\\.\\d{4}))?$");
            Matcher matcher = pattern.matcher(name);

            if (matcher.find()) {
                eventName = matcher.group(1).trim();
                String startDateString = matcher.group(2).trim();
                String endDateString = matcher.group(3) != null ? matcher.group(3).trim() : null;

                if (endDateString != null) {
                    LocalDate startDate = parseDate(startDateString);
                    LocalDate endDate = parseDate(endDateString);
                    date = Either.right(new Period(startDate, endDate));
                } else {
                    LocalDate singleDate = parseDate(startDateString);
                    date = Either.left(singleDate);
                }
            } else {
                throw new IOException("Invalid folder name format");
            }

            // Use the eventName and date
            return FolderController.get().createFolder(name, date, eventName);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Copy files inside a folder to a destination folder.
     * @param sourceFolder the source folder
     * @param destinationFolder the destination folder
     * @throws IOException
     */
    private void copyFilesInsideFolder(File sourceFolder, File destinationFolder) throws IOException {
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        if (!sourceFolder.isDirectory()) return;

        Folder folder = createFolder(sourceFolder);
        PhotoManagerController.get().addDataStructure(folder);

        String[] data = sourceFolder.list();
        for (String dataFile : data) {
            File file = new File(sourceFolder +"\\" + dataFile);
            if (file.isDirectory()) {
                // do not copy the folder itself
                // copyFilesInsideFolder(file, new File(destinationFolder, file.getName()));
            } else if (isImage(file)) {
                File newPhoto = new File(destinationFolder, file.getName());
                Files.copy(file.toPath(), newPhoto.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Photo photo = createPhotoFromFile(newPhoto);
                XMLController.get().savePhotoToXML(photo, true);
                PhotoManagerController.get().addPhoto(photo);
                FolderController.get().addMediaToFolder(folder, photo);
            } else if (isVideo(file)) {
                File newVideo = new File(destinationFolder, file.getName());
                Files.copy(file.toPath(), newVideo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Video video = createVideoFromFile(newVideo);
                XMLController.get().saveVideoToXML(video, true);
                PhotoManagerController.get().addVideo(video);
                FolderController.get().addMediaToFolder(folder, video);
            }
        }
        try {
            XMLController.get().saveFolderToXML(folder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the file is an image.
     * @param file the file to check
     * @return true if the file is an image, false otherwise
     */
    private boolean isImage(File file) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff"};
        for (String extension : imageExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the file is a video.
     * @param file the file to check
     * @return true if the file is a video, false otherwise
     */
    private boolean isVideo(File file) {
        String[] videoExtensions = {".mp4", ".avi", ".mkv", ".mov", ".wmv", ".flv"};
        for (String extension : videoExtensions) {
            if (file.getName().toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create photo from file.
     *
     * @param file the file to create the photo from
     * @return the photo object
     */
    private Photo createPhotoFromFile(File file) {
        Resolution photoResolution = getPhotoResolution(file);;

        if (photoResolution == null) {
            photoResolution = new Resolution(0, 0);
        }

        return MediaController.get().createPhoto(
                file.getAbsolutePath(),
                file.getName(),
                getFileCreationDate(file),
                "",
                false,
                TagListController.get().createEmptyTagList(),
                1.0,
                Rating.ZERO_STARS,
                Orientation.D0,
                photoResolution
        );
    }

    /**
     * Create video from file.
     *
     * @param file the file to create the video from
     * @return the video object
     */
    private Video createVideoFromFile(File file) {
        return MediaController.get().createVideo(
                file.getAbsolutePath(),
                file.getName(),
                getFileCreationDate(file),
                "",
                false,
                TagListController.get().createEmptyTagList(),
                getVideoDuration(file),
                Rating.ZERO_STARS,
                Orientation.D0,
                getVideoResolution(file)
        );
    }

    /**
     * Gets photo resolution.
     *
     * @param file the file
     * @return the photo resolution
     */
    public Resolution getPhotoResolution(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image != null) {
                return new Resolution(image.getWidth(), image.getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets video resolution.
     *
     * @param file the file
     * @return the video resolution
     */
    public Resolution getVideoResolution(File file) {
        try {
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            Picture picture = grab.getNativeFrame();
            if (picture != null) {
                int width = picture.getWidth();
                int height = picture.getHeight();
                return new Resolution(width, height);
            }
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
        return new Resolution(0, 0);
    }

    /**
     * Gets video duration.
     *
     * @param file the file
     * @return the video duration
     */
    public long getVideoDuration(File file) {
        try {
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
            DemuxerTrackMeta meta = grab.getVideoTrack().getMeta();
            double duration = (double) meta.getTotalDuration();
            return (long) duration;
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets file creation date.
     *
     * @param file the file
     * @return the file creation date
     */
    public LocalDate getFileCreationDate(File file) {
        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            return attr.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LocalDate.now();
    }
}
