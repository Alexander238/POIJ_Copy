package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.*;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;

import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Media controller.
 */
public class MediaController {
    /**
     * The instance.
     */
    private static MediaController instance;

    private MediaController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized MediaController get() {
        if (instance == null) {
            instance = new MediaController();
        }
        return instance;
    }

    /**
     * Create photo.
     *
     * @param file        the file
     * @param name        the name
     * @param date        the date
     * @param description the description
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param zoomFactor  the zoom factor
     * @param rating      the rating
     * @param orientation the orientation
     * @param resolution  the resolution
     * @return the photo
     */
    public Photo createPhoto(
            String file,
            String name,
            LocalDate date,
            String description,
            boolean isPrivate,
            TagList tags,
            double zoomFactor,
            Rating rating,
            Orientation orientation,
            Resolution resolution
    ) {
        return new Photo(
                file,
                name,
                date,
                description,
                isPrivate,
                tags,
                zoomFactor,
                rating,
                orientation,
                resolution
        );
    }

    /**
     * Create video.
     *
     * @param file        the file
     * @param name        the name
     * @param date        the date
     * @param description the description
     * @param isPrivate   the is private
     * @param tags        the tags
     * @param duration    the duration
     * @param rating      the rating
     * @param orientation the orientation
     * @param resolution  the resolution
     * @return the video
     */
    public Video createVideo(
            String file,
            String name,
            LocalDate date,
            String description,
            boolean isPrivate,
            TagList tags,
            long duration,
            Rating rating,
            Orientation orientation,
            Resolution resolution
    ) {
        return new Video(
                file,
                name,
                date,
                description,
                isPrivate,
                tags,
                duration,
                rating,
                orientation,
                resolution
        );
    }

    /**
     * Renames the media name
     *
     * @param media   The media to rename.
     * @param newName The new name.
     */
    public void renameMedia(Media media, String newName) {
        media.rename(newName);
    }

    /**
     * Adds a tag to the media
     *
     * @param media The media to add the tag to.
     * @param tag   The tag to add.
     */
    public void addTag(Media media, Tag tag) {
        if (media.getTags().getTags().contains(tag)) return;
        media.addTag(tag);
    }

    /**
     * Removes a tag from the media
     *
     * @param media The media to remove the tag from.
     * @param tag   The tag to remove.
     */
    public void removeTag(Media media, Tag tag) {
        media.removeTag(tag);
    }

    /**
     * Deletes the media from all albums, folders and slideshows and sets all attributes to null
     * so the garbage collector can delete the media.
     *
     * @param media The media to delete.
     */
    public void deleteMedia(Media media) {
        PhotoManagerController.get().getPhotoManager().getAlbums().forEach(album -> album.removeMedia(media));
        PhotoManagerController.get().getPhotoManager().getFolders().forEach(folder -> folder.removeMedia(media));
        PhotoManagerController.get().getPhotoManager().getSlideshows().forEach(slideshow -> slideshow.removeMedia((Photo) media));
        media.delete();
    }

    /**
     * Rotates the media clockwise.
     *
     * @param media The media to rotate.
     * @throws UnexpectedException the unexpected exception
     */
    public void rotateMediaClockwise(Media media) throws UnexpectedException {
        media.rotateClockwise();
    }

    /**
     * Rotates the media counterclockwise.
     *
     * @param media The media to rotate.
     * @throws UnexpectedException the unexpected exception
     */
    public void rotateMediaCounterClockwise(Media media) throws UnexpectedException {
        media.rotateCounterClockwise();
    }

    /**
     * Zooms in the photo.
     *
     * @param photo The photo to zoom in.
     */
    public void zoomInPhoto(Photo photo) {
        photo.zoomIn();
    }

    /**
     * Zooms out the photo.
     *
     * @param photo The photo to zoom out.
     */
    public void zoomOutPhoto(Photo photo) {
        photo.zoomOut();
    }

    /**
     * Play video.
     *
     * @param video the video
     */
    public void playVideo(Video video) {
        video.play();
    }

    /**
     * Pause video.
     *
     * @param video the video
     */
    public void pauseVideo(Video video) {
        video.pause();
    }

    /**
     * Stop video.
     *
     * @param video the video
     */
    public void stopVideo(Video video) {
        video.stop();
    }

    /**
     * Change playback speed.
     *
     * @param video the video
     * @param speed the speed
     */
    public void changePlaybackSpeed(Video video, PlaybackSpeed speed) {
        video.changePlaybackSpeed(speed);
    }

    /**
     * Increases the playback speed of the video to the next higher value.
     *
     * @param video The video to increase the playback speed.
     */
    public void increasePlaybackSpeed(Video video) {
        video.increasePlaybackSpeed();
    }

    /**
     * Decreases the playback speed of the video to the next lower value.
     *
     * @param video The video to decrease the playback speed.
     */
    public void decreasePlaybackSpeed(Video video) {
        video.decreasePlaybackSpeed();
    }

    /**
     * Returns the list of albums containing this media.
     *
     * @param media The media to search for.
     * @return The list of albums containing this media.
     */
    public List<Album> showAlbums(Media media) {
        List<Album> albumsContainingThisMedia = new ArrayList<>();
        PhotoManagerController.get().getPhotoManager().getAlbums().forEach(album -> {
            if (album.getMediaList().contains(media)) {
                albumsContainingThisMedia.add((Album) album);
            }
        });
        return albumsContainingThisMedia;
    }

    /**
     * Returns the list of slideshows containing this media.
     *
     * @param photo The photo to search for.
     * @return The list of slideshows containing this media.
     */
    public List<Slideshow> showSlideshows(Photo photo) {
        List<Slideshow> slideshowsContainingThisMedia = new ArrayList<>();
        PhotoManagerController.get().getPhotoManager().getSlideshows().forEach(slideshow -> {
            if (slideshow.getMediaList().contains(photo)) {
                slideshowsContainingThisMedia.add((Slideshow) slideshow);
            }
        });
        return slideshowsContainingThisMedia;
    }

    /**
     * Returns the list of folders containing this media.
     *
     * @param media The media to search for.
     * @return The list of folders containing this media.
     */
    public List<Folder> showFolders(Media media) {
        List<Folder> foldersContainingThisMedia = new ArrayList<>();
        PhotoManagerController.get().getPhotoManager().getFolders().forEach(folder -> {
            if (folder.getMediaList().contains(media)) {
                foldersContainingThisMedia.add((Folder) folder);
            }
        });
        return foldersContainingThisMedia;
    }
}
