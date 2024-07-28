package de.fhkiel.fotomanager.controller.modelController;

import de.fhkiel.fotomanager.model.datastructures.impl.Slideshow;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;

/**
 * The type Slideshow controller.
 */
public class SlideshowController {
    /**
     * The constant instance.
     */
    private static SlideshowController instance;

    private SlideshowController() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SlideshowController get() {
        if (instance == null) {
            instance = new SlideshowController();
        }
        return instance;
    }

    /**
     * Create slideshow slideshow.
     *
     * @param name            the name
     * @param secondsPerImage the seconds per image
     * @return the slideshow
     */
    public Slideshow createSlideshow(String name, int secondsPerImage) {
        return new Slideshow(name, secondsPerImage);
    }

    /**
     * Rename slideshow.
     *
     * @param slideshow the slideshow
     * @param newName   the new name
     */
    public void renameSlideshow(Slideshow slideshow, String newName) {
        slideshow.rename(newName);
    }

    /**
     * Change seconds per image.
     *
     * @param slideshow       the slideshow
     * @param secondsPerImage the seconds per image
     */
    public void changeSecondsPerImage(Slideshow slideshow, int secondsPerImage) {
        try {
            slideshow.changeSecondsPerImage(secondsPerImage);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid seconds per image");
        }
    }

    /**
     * Delete the slideshow object from the XML file and from the memory.
     *
     * @param slideshow the slideshow
     */
    public void deleteSlideshow(Slideshow slideshow) {
        slideshow.delete();
    }

    /**
     * Add photo to slideshow.
     *
     * @param slideshow the slideshow
     * @param photo     the photo
     */
    public void addPhotoToSlideshow(Slideshow slideshow, Photo... photo) {
        for (Photo p : photo) {
            if (!p.isPrivate()) {
                if (!slideshow.getMediaList().contains(p)) slideshow.addMedia(p);
            }
        }
    }

    /**
     * Remove photo from slideshow.
     *
     * @param slideshow the slideshow
     * @param photo     the photo
     */
    public void removePhotoFromSlideshow(Slideshow slideshow, Photo photo) {
        slideshow.removeMedia(photo);
    }

    /**
     * Play slideshow.
     *
     * @param slideshow the slideshow
     */
    public void playSlideshow(Slideshow slideshow) {
        slideshow.play();
    }

    /**
     * Stop slideshow.
     *
     * @param slideshow the slideshow
     */
    public void stopSlideshow(Slideshow slideshow) {
        slideshow.stop();
    }

    /**
     * Show next image.
     *
     * @param slideshow the slideshow
     */
    public void showNextImage(Slideshow slideshow) {
        slideshow.next();
    }

    /**
     * Show previous image.
     *
     * @param slideshow the slideshow
     */
    public void showPreviousImage(Slideshow slideshow) {
        slideshow.previous();
    }


}
