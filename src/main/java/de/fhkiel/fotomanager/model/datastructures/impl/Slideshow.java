package de.fhkiel.fotomanager.model.datastructures.impl;


import de.fhkiel.fotomanager.model.datastructures.DataStructure;

import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;

import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Slideshow.
 */
@Getter
public class Slideshow extends DataStructure<Photo> {
    /**
     * The Duration of the slideshow.
     */
    private int duration;
    /**
     * The time in seconds the slideshow should show an image.
     */
    private int secondsPerImage;
    /**
     * Index of the current image in the slideshow.
     */
    private int currentImageIndex;
    /**
     * Boolean to check if the slideshow is playing.
     */
    private boolean isPlaying;
    /**
     * The Timer for displaying the images in the slideshow with a certain time interval.
     */
    private Timer timer;

    /**
     * Instantiates a new Slideshow.
     *
     * @param name            the name
     * @param secondsPerImage the seconds per image
     */
    public Slideshow(String name, int secondsPerImage) {
        super(name);
        if (secondsPerImage <= 0) {
            throw new IllegalArgumentException("Seconds per image must be greater than 0");
        }
        this.duration = this.getMediaList().size() * secondsPerImage;
        this.secondsPerImage = secondsPerImage;
        this.currentImageIndex = 0;
        this.isPlaying = false;
    }

    /**
     * Converts a slideshow to an XML element
     *
     * @param document the XML document
     * @return the XML element
     */
    public Element toXML(Document document) {
        Element slideshowElement = document.createElement("Slideshow");
        slideshowElement.setAttribute("name", this.getName());
        slideshowElement.setAttribute("secondsPerImage", String.valueOf(this.getSecondsPerImage()));

        for (Photo photo : this.getMediaList()) {
            Element photoElement = photo.toXML(document);
            slideshowElement.appendChild(photoElement);
        }

        return slideshowElement;
    }

    /**
     * From xml slideshow.
     *
     * @param element the element
     * @return the slideshow
     */
    public static Slideshow fromXML(Element element) {
        String name = element.getAttribute("name");
        int secondsPerImage = Integer.parseInt(element.getAttribute("secondsPerImage"));

        Slideshow slideshow = new Slideshow(name, secondsPerImage);
        NodeList photoNodes = element.getElementsByTagName("Photo");

        for (int j = 0; j < photoNodes.getLength(); j++) {
            Element photoElement = (Element) photoNodes.item(j);
            Photo photo = Photo.fromXML(photoElement);

            slideshow.addMedia(photo);
        }

        return slideshow;
    }

    /**
     * Change seconds per image.
     *
     * @param secondsPerImage the seconds per image
     */
    public void changeSecondsPerImage(int secondsPerImage) {
        if (secondsPerImage <= 0) {
            throw new IllegalArgumentException("Seconds per image must be greater than 0");
        }
        this.secondsPerImage = secondsPerImage;
        this.duration = this.getMediaList().size() * secondsPerImage;
    }

    /**
     * Clears all private Photos from Media List
     */
    public void clearPrivatePhotosFromList() {
        List<Photo> filteredPhotos = getMediaList().stream()
            .filter(photo -> !photo.isPrivate())
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        this.getMediaList().clear();
        this.getMediaList().addAll(filteredPhotos);
        this.duration = this.getMediaList().size() * this.secondsPerImage;
    }

    /**
     * Play.
     */
    public void play() {
        this.clearPrivatePhotosFromList();

        isPlaying = true;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentImageIndex < getMediaList().size() - 1) {
                    currentImageIndex++;
                } else {
                    isPlaying = false;
                    currentImageIndex = 0;
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, secondsPerImage * 1000);
    }

    /**
     * Stop.
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
        isPlaying = false;
        currentImageIndex = 0;
    }

    /**
     * Next.
     */
    public void next() {
        if (currentImageIndex < getMediaList().size() - 1) {
            currentImageIndex++;
        } else {
            currentImageIndex = 0;
        }
    }

    /**
     * Previous.
     */
    public void previous() {
        if (currentImageIndex > 0) {
            currentImageIndex--;
        } else {
            currentImageIndex = getMediaList().size() - 1;
        }
    }

    /**
     * Set duration and seconds per image to zero.
     */
    private void setDurationAndSecondsPerImageToZero() {
        this.duration = 0;
        this.secondsPerImage = 0;
    }

    /**
     * Delete.
     */
    public void delete() {
        this.getMediaList().clear();
        this.setNameToNull();
        this.setDurationAndSecondsPerImageToZero();
    }

    /**
     * Add Media.
     * @param media the media
     */
    @Override
    public void addMedia(Photo media) {
        if (!media.isPrivate()) {
            super.addMedia(media);
            this.duration = this.getMediaList().size() * this.secondsPerImage;
        }
    }

    /**
     * Remove Media.
     * @param media the media
     */
    @Override
    public void removeMedia(Photo media) {
        super.removeMedia(media);
        this.duration = this.getMediaList().size() * this.secondsPerImage;
    }

    /**
     * Remove media by name.
     *
     * @param file the file
     */
    public void removeMediaByName(Photo file) {
        for (Photo photo : this.getMediaList()) {
            if (photo.getFile().equals(file.getFile())) {
                this.removeMedia(photo);
                break;
            }
        }
    }
}
