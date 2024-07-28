package de.fhkiel.fotomanager;

import de.fhkiel.fotomanager.controller.modelController.*;
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
import lombok.Data;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Photo manager.
 */
@Data
public class PhotoManager {
    /**
     * The instance of the PhotoManager.
     */
    private static PhotoManager instance;
    /**
     * A list of photos.
     */
    private LocalDate date;
    /**
     * A list of photos.
     */
    private List<Photo> photos = new ArrayList<>();
    /**
     * A list of videos.
     */
    private List<Video> videos = new ArrayList<>();
    /**
     * A list of folders.
     */
    private List<Folder> folders = new ArrayList<>();
    /**
     * A list of albums.
     */
    private List<Album> albums = new ArrayList<>();
    /**
     * A list of slideshows.
     */
    private List<Slideshow> slideshows = new ArrayList<>();
    /**
     * The tag list.
     */
    private TagList tagList = TagListController.get().createEmptyTagList();

    private PhotoManager() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized PhotoManager getInstance() {
        if (instance == null) {
            instance = new PhotoManager();
        }
        return instance;
    }

    /**
     * Set tagList.
     *
     * @param tagList the tagList
     */
    public void setTagList(TagList tagList) {
        this.tagList = tagList;
    }

    /**
     * Add tag.
     *
     * @param tag the tag
     */
    public void addTag(Tag tag) {
        TagListController.get().addTag(tagList, tag);
    }

    /**
     * Remove tag.
     *
     * @param tag the tag
     */
    public void removeTag(Tag tag) {
        tagList.removeTag(tag);
    }

    /**
     * Add data structure boolean.
     *
     * @param dataStructure the data structure
     * @throws IllegalArgumentException the illegal argument exception
     */
    public void addDataStructure(DataStructure dataStructure) throws IllegalArgumentException {
        if (dataStructure instanceof Folder) {
            if (isFolderInFolders(dataStructure.getName())) {
                throw new IllegalArgumentException("Folder with that name already exists");
            }
            folders.add((Folder) dataStructure);
        }
        else if (dataStructure instanceof Album) {
            if (isAlbumInAlbums(dataStructure.getName())) {
                throw new IllegalArgumentException("Album with that name already exists");
            }
            albums.add((Album) dataStructure);
        }
        else if (dataStructure instanceof Slideshow) {
            if (isSlideshowInSlideshows(dataStructure.getName())) {
                throw new IllegalArgumentException("Slideshow with that name already exists");
            }

            if (dataStructure.getName().isEmpty()) {
                throw new IllegalArgumentException("Please insert a name");
            }

            if (((Slideshow) dataStructure).getSecondsPerImage() <= 0) {
                throw new IllegalArgumentException("Seconds per image must be greater than 0");
            }

            slideshows.add((Slideshow) dataStructure);
        }
        else {
            throw new IllegalArgumentException("Unsupported DataStructure: " + dataStructure.getClass());
        }
    }

    /**
     * Rename data structure boolean.
     *
     * @param dataStructure the data structure
     * @param newName       the new name
     */
    public void renameDataStructure(DataStructure dataStructure, String newName) {
        if (dataStructure instanceof Folder) {
            if (isFolderInFolders(newName)) {
                throw new IllegalArgumentException("Folder with that name already exists");
            }
        }
        else if (dataStructure instanceof Album) {
            if (isAlbumInAlbums(newName)) {
                throw new IllegalArgumentException("Album with that name already exists");
            }
        }
        else if (dataStructure instanceof Slideshow) {
            if (isSlideshowInSlideshows(newName)) {
                throw new IllegalArgumentException("Slideshow with that name already exists");
            }
        }
        else {
            throw new IllegalArgumentException("Unsupported DataStructure: " + dataStructure.getClass());
        }
        dataStructure.rename(newName);
    }

    /**
     * Delete data structure boolean.
     *
     * @param dataStructure the data structure
     */
    public void deleteDataStructure(DataStructure dataStructure) {
        if (dataStructure instanceof Folder) {
            if (isFolderInFolders(dataStructure.getName())) {
                folders.remove(dataStructure);
                XMLController.get().deleteFolderFromXML((Folder) dataStructure);
                FolderController.get().deleteFolder((Folder) dataStructure);
            } else {
                throw new IllegalArgumentException("Folder with that name does not exist");
            }
        }
        else if (dataStructure instanceof Album) {
            if (isAlbumInAlbums(dataStructure.getName())) {
                albums.remove(dataStructure);
                AlbumController.get().deleteAlbum((Album) dataStructure);
            } else {
                throw new IllegalArgumentException("Album with that name does not exist");
            }
        }
        else if (dataStructure instanceof Slideshow) {
            System.out.println("PhotoManager: " + dataStructure.getName());
            if (isSlideshowInSlideshows(dataStructure.getName())) {
                slideshows.remove(dataStructure);
                XMLController.get().deleteSlideshowFromXML((Slideshow) dataStructure);
                SlideshowController.get().deleteSlideshow((Slideshow) dataStructure);
            } else {
                throw new IllegalArgumentException("Slideshow with that name does not exist");
            }
        }
        else {
            throw new IllegalArgumentException("Unsupported DataStructure: " + dataStructure.getClass());
        }
    }

    /**
     * Remove Media from DataStructure.
     *
     * @param currentMedia  the media
     * @param dataStructure the data structure
     */
    public void removeMediaFromDataStructure(Media currentMedia, DataStructure dataStructure) {
        if (dataStructure instanceof Folder) {
            for (Folder folder : folders) {
                if (folder.getName().equals(dataStructure.getName())) {
                    // No need to remove Folder from all XML files, because that's handled already.
                    folder.removeMedia(currentMedia);
                }
            }
        }
        else if (dataStructure instanceof Album) {
            for (Album album : albums) {
                if (album.getName().equals(dataStructure.getName())) {
                    XMLController.get().deleteMediaFromDatastructureXML(currentMedia, XMLController.DataStructureType.ALBUM);
                    album.removeMedia(currentMedia);
                }
            }
        }
        else if (dataStructure instanceof Slideshow) {
            for (Slideshow slideshow : slideshows) {
                XMLController.get().deleteMediaFromDatastructureXML(currentMedia, XMLController.DataStructureType.SLIDESHOW);
                if (slideshow.getName().equals(dataStructure.getName())) {
                    slideshow.removeMedia((Photo) currentMedia);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Unsupported DataStructure: " + dataStructure.getClass());
        }

    }

    /**
     * Add media boolean.
     *
     * @param albumName the albumName
     * @return the boolean
     */
    private boolean isAlbumInAlbums(String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add media boolean.
     *
     * @param slideshowName the slideshowName
     * @return the boolean
     */
    private boolean isSlideshowInSlideshows(String slideshowName) {
        for (Slideshow slideshow : slideshows) {
            if (slideshow.getName().equals(slideshowName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add media boolean.
     *
     * @param folderName the folderName
     * @return the boolean
     */
    private boolean isFolderInFolders(String folderName) {
        for (Folder folder : folders) {
            if (folder.getName().equals(folderName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of data structures of a specific type.
     *
     * @param type the data structure type
     * @return the list of data structures
     */
    public List<DataStructure> getDataStructures(DataStructureType type) {
        List<DataStructure> dataStructures = new ArrayList<>();
        switch (type) {
            case ALBUM -> dataStructures.addAll(albums);
            case FOLDER -> dataStructures.addAll(folders);
            case SLIDESHOW -> dataStructures.addAll(slideshows);
        }
        return dataStructures;
    }

    /**
     * Add photo.
     *
     * @param currentMedia the photo
     */
    public void deletePhoto(Photo currentMedia) {
        photos.remove(currentMedia);
        System.out.println("PhotoManager: " + currentMedia);
        XMLController.get().deleteFileOrDirectory(new File(currentMedia.getFile()));
        XMLController.get().deleteMediaFromAllXML(currentMedia, XMLController.MediaType.PHOTO);
        MediaController.get().deleteMedia(currentMedia);
    }

    /**
     * Delete video.
     *
     * @param currentMedia the video
     */
    public void deleteVideo(Video currentMedia) {
        this.videos.remove(currentMedia);
        MediaController.get().deleteMedia(currentMedia);
        XMLController.get().deleteMediaFromAllXML(currentMedia, XMLController.MediaType.VIDEO);
    }

    /**
     * replace old media in folders, slideshows, albums and videos/photos.
     * @param updatedMedia the media to update.
     */
    public void replaceOldMediaInEverything(Media updatedMedia) {
        if (updatedMedia instanceof Photo) {
            for (Photo photo : photos) {
                if (photo.getFile().equals(updatedMedia.getFile())) {
                    this.photos.remove(photo);
                    this.photos.add((Photo) updatedMedia);
                    break;
                }
            }
            iterateAndUpdateMediaInAllDataStructures(updatedMedia);
        } else if (updatedMedia instanceof Video) {
            for (Video video : videos) {
                if (video.getFile().equals(updatedMedia.getFile())) {
                    this.videos.remove(video);
                    this.videos.add((Video) updatedMedia);
                }
            }
            iterateAndUpdateMediaInAllDataStructures(updatedMedia);
        } else {
            throw new IllegalArgumentException("Unsupported Media: " + updatedMedia.getClass());
        }
    }

    /**
     * Iterates through all data structures and updates the media.
     * @param updatedMedia the updated media
     */
    private void iterateAndUpdateMediaInAllDataStructures(Media updatedMedia) {
        for (Album album : albums) {
            iterateDataStructureListAndRemove(updatedMedia, album.getMediaList());
        }

        for (Folder folder : folders) {
            iterateDataStructureListAndRemove(updatedMedia, folder.getMediaList());
        }

        for (Slideshow slideshow : slideshows) {
            for (Photo photoInSlideshow : slideshow.getMediaList()) {
                if (photoInSlideshow.getFile().equals(updatedMedia.getFile())) {
                    slideshow.getMediaList().remove(photoInSlideshow);
                    slideshow.getMediaList().add((Photo) updatedMedia);
                    break;
                }
            }
        }
    }

    /**
     * Iterates through a list of media and updates the media.
     * @param updatedMedia the updated media
     * @param mediaList the list of media
     */
    private void iterateDataStructureListAndRemove(Media updatedMedia, List<Media> mediaList) {
        for (Media media : mediaList) {
            if (media.getFile().equals(updatedMedia.getFile())) {
                mediaList.remove(media);
                mediaList.add(updatedMedia);
                break;
            }
        }
    }

}
