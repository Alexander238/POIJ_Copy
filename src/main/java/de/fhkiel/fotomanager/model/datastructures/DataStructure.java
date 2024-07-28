package de.fhkiel.fotomanager.model.datastructures;

import de.fhkiel.fotomanager.controller.modelController.PhotoManagerController;
import de.fhkiel.fotomanager.model.mediatypes.Media;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Data structure.
 *
 * @param <T> the type parameter
 */
@Getter
public abstract class DataStructure<T extends Media> implements DataStructureInterface<T> {
    /**
     * A list to store media objects.
     */
    private final List<T> mediaList = new ArrayList<>();
    /**
     * The name of the data structure.
     */
    private String name;

    /**
     * Instantiates a new Data structure.
     *
     * @param name the name
     */
    public DataStructure(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }
        this.name = name;
    }

    /**
     * Rename.
     *
     * @param newName the new name
     */
    public void rename(String newName) {
        if (newName == null || newName.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }
        this.name = newName;
    }

    /**
     * Sets name to null.
     */
    protected void setNameToNull() {
        this.name = null;
    }

    /**
     * Add media to the media list.
     *
     * @param media the media
     */
    @Override
    public void addMedia(T media) {
        mediaList.add(media);
    }

    /**
     * Remove media from the media list.
     *
     * @param media the media
     */
    @Override
    public void removeMedia(T media) {
        mediaList.remove(media);
    }

    /**
     * Extract photos from XML list.
     *
     * @param element the element
     * @return the list
     */
    public static List<Photo> extractPhotosFromXML(Element element) {
        List<Photo> photoList = new ArrayList<>();
        NodeList photoNodes = element.getElementsByTagName("Photo");
        for (int i = 0; i < photoNodes.getLength(); i++) {
            boolean photoExists = false;
            Photo photo = Photo.fromXML((Element) photoNodes.item(i));
            for (Photo availabePhoto : PhotoManagerController.get().getPhotoManager().getPhotos()) {
                if (availabePhoto.equals(photo)) {
                    photoList.add(availabePhoto);
                    photoExists = true;
                    break;
                }
            }
            if (!photoExists) {
                photoList.add(photo);
            }
        }
        return photoList;
    }

    /**
     * Extract videos from XML list.
     *
     * @param element the element
     * @return the list
     */
    public static List<Video> extractVideosFromXML(Element element) {
        List<Video> videoList = new ArrayList<>();
        NodeList videoNodes = element.getElementsByTagName("Video");
        for (int i = 0; i < videoNodes.getLength(); i++) {
            boolean videoExists = false;
            Video video = Video.fromXML((Element) videoNodes.item(i));
            for (Video availabeVideo : PhotoManagerController.get().getPhotoManager().getVideos()) {
                if (availabeVideo.equals(video)) {
                    videoList.add(availabeVideo);
                    videoExists = true;
                    break;
                }
            }
            if (!videoExists) {
                videoList.add(video);
            }
        }
        return videoList;
    }

    /**
     * To xml element.
     *
     * @param document the document
     * @param element  the element
     * @return the element
     */
    protected void mediaListToXML(Document document, Element element) {
        for (Media media : this.getMediaList()) {
            if (media instanceof Photo) {
                Element photoElement = ((Photo) media).toXML(document);
                element.appendChild(photoElement);
            } else if (media instanceof Video) {
                Element videoElement = ((Video) media).toXML(document);
                element.appendChild(videoElement);
            }
        }
    }
}