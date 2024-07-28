package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.PhotoManager;
import de.fhkiel.fotomanager.model.datastructures.DataStructure;
import de.fhkiel.fotomanager.model.datastructures.DataStructureType;
import de.fhkiel.fotomanager.model.datastructures.impl.Album;
import de.fhkiel.fotomanager.model.datastructures.impl.Folder;
import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import javafx.collections.ObservableList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Photo manager controller.
 */
@Data
public class PhotoManagerController {
    /**
     * The constant instance.
     */
    private static PhotoManagerController instance;
    /**
     * The Photo manager.
     */
    private PhotoManager photoManager = PhotoManager.getInstance();

    private PhotoManagerController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized PhotoManagerController get() {
        if (instance == null) {
            instance = new PhotoManagerController();
        }
        return instance;
    }

    /**
     * Handle load data.
     */
    public void loadData() {
        loadDataFromXML();
    }

    /**
     * Load data into fotomanager.
     * This method is used to load data into the fotomanager.
     */
    private void loadDataFromXML() {
        TagList tags = XMLController.get().loadTagsFromXML();
        PhotoManagerController.get().setTagList(tags);

        List<Photo> photos = XMLController.get().loadPhotosFromXML();
        photos.forEach(photo -> {
            System.out.println("Adding photo: '" + photo.getName() + "' from XML to PhotoManager");
            PhotoManagerController.get().addPhoto(photo);
        });

        List<Video> videos = XMLController.get().loadVideosFromXML();
        videos.forEach(video -> {
            System.out.println("Adding video: '" + video.getName() + "' from XML to PhotoManager");
            PhotoManagerController.get().addVideo(video);
        });

        List<Folder> folders = XMLController.get().loadFoldersFromXML();
        folders.forEach(folder -> {
            System.out.println("Adding folder: '" + folder.getName() + "' from XML to PhotoManager");
            addDataStructure(folder);
        });

        List<Album> albums = XMLController.get().loadAlbumsFromXML();
        albums.forEach(album -> {
            System.out.println("Adding album: '" + album.getName() + "' from XML to PhotoManager");
            addDataStructure(album);
        });

        List<Slideshow> slideshows = XMLController.get().loadSlideshowsFromXML();
        slideshows.forEach(slideshow -> {
            System.out.println("Adding slideshow: '" + slideshow.getName() + "' from XML to PhotoManagerController");
            addDataStructure(slideshow);
        });
    }

    /**
     * Handle add data structure.
     *
     * @param dataStructure the data structure
     */
    public void addDataStructure(DataStructure dataStructure) {
        photoManager.addDataStructure(dataStructure);
    }

    /**
     * Rename data structure.
     *
     * @param dataStructure the data structure
     * @param newName       the new name
     */
    public void renameDataStructure(DataStructure dataStructure, String newName) {
        photoManager.renameDataStructure(dataStructure, newName);
    }

    /**
     * Handle delete data structure.
     *
     * @param dataStructure the data structure
     */
    public void deleteDataStructure(DataStructure dataStructure) {
        photoManager.deleteDataStructure(dataStructure);
    }

    /**
     * Add photo.
     *
     * @param photo the photo
     */
    public void addPhoto(Photo photo) {
        photoManager.getPhotos().add(photo);
    }

    /**
     * Add video.
     *
     * @param video the video
     */
    public void addVideo(Video video) {
        photoManager.getVideos().add(video);
    }

    /**
     * Remove media from data structure.
     *
     * @param media         the media
     * @param dataStructure the data structure
     */
    public void removeMediaFromDataStructure(Media media, DataStructure dataStructure) {
        photoManager.removeMediaFromDataStructure(media, dataStructure);
    }

    /**
     * Gets tag list.
     *
     * @return the tag list
     */
    public TagList getTagList() {
        return photoManager.getTagList();
    }

    /**
     * Get list of slideshows.
     *
     * @return the list of slideshows
     */
    public List<Slideshow> getSlideshows() {
        return photoManager.getSlideshows();
    }

    /**
     * Get list of photos.
     *
     * @return the list of photos
     */
    public List<Photo> getPhotos() {
        return photoManager.getPhotos();
    }

    /**
     * Get list of videos.
     *
     * @return the list of videos
     */
    public List<Video> getVideos() {
        return photoManager.getVideos();
    }

    /**
     * Get observable list of tags.
     *
     * @return the list of tags
     */
    public ObservableList<Tag> getAllObservableTags() {
        return photoManager.getTagList().getObservableTags();
    }

    /**
     * Sets tag list.
     *
     * @param tagList the tag list
     */
    public void setTagList(TagList tagList) {
        photoManager.setTagList(tagList);
    }

    /**
     * Add tag.
     *
     * @param tag the tag
     */
    public void addTag(Tag tag) {
        photoManager.addTag(tag);
    }

    /**
     * Remove tag.
     *
     * @param tag the tag
     */
    public void removeTag(Tag tag) {
        photoManager.removeTag(tag);
    }

    /**
     * Delete photo.
     *
     * @param photo the photo to delete
     */
    public void deletePhoto(Photo photo) {
        photoManager.deletePhoto(photo);
    }

    /**
     * Delete video.
     *
     * @param video the video to delete
     */
    public void deleteVideo(Video video) {
        photoManager.deleteVideo(video);
    }

    /**
     * Get list of folders, albums or slideshows.
     *
     * @param type the data structure type
     * @return the list of data structures
     */
    public List<DataStructure> getDataStructures(DataStructureType type) {
        return photoManager.getDataStructures(type);
    }

    /**
     * Replaces old media with updatedMedia in folders, slideshows, albums and videos/photos.
     *
     * @param updatedMedia the updated media
     */
    public void replaceOldMediaInEverything(Media updatedMedia) {
        photoManager.replaceOldMediaInEverything(updatedMedia);
    }

    /**
     * Gets all media, including photos and videos.
     * @return the list of all media
     */
    public List<Media> getAllMedia() {
        List<Media> mediaList = new ArrayList<>();
        mediaList.addAll(photoManager.getPhotos());
        mediaList.addAll(photoManager.getVideos());
        return mediaList;
    }
}