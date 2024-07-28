package de.fhkiel.fotomanager.util;

import de.fhkiel.fotomanager.controller.modelController.TagListController;
import de.fhkiel.fotomanager.model.mediatypes.Orientation;
import de.fhkiel.fotomanager.model.mediatypes.Rating;
import de.fhkiel.fotomanager.model.mediatypes.Resolution;
import de.fhkiel.fotomanager.model.mediatypes.impl.Photo;
import de.fhkiel.fotomanager.model.mediatypes.impl.Video;
import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Test util media controller.
 */
@Data
public class TestUtilMediaController {

    /**
     * The constant instance.
     */
    public static TestUtilMediaController instance;
    /**
     * The constant defaultFile.
     */
    public static String defaultFile = "defaultFile";
    /**
     * The constant defaultName.
     */
    public static String defaultName = "defaultName";
    /**
     * The constant defaultDate.
     */
    public static LocalDate defaultDate = LocalDate.now();
    /**
     * The constant defaultDescription.
     */
    public static String defaultDescription = "defaultDescription";
    /**
     * The constant defaultIsPrivate.
     */
    public static boolean defaultIsPrivate = false;
    /**
     * The constant defaultTags.
     */
    public static TagList defaultTags = TagListController.get().createEmptyTagList();
    /**
     * The constant defaultZoomFactor.
     */
    public static double defaultZoomFactor = 1.0;
    /**
     * The constant defaultRating.
     */
    public static Rating defaultRating = Rating.ZERO_STARS;
    /**
     * The constant defaultOrientation.
     */
    public static Orientation defaultOrientation = Orientation.D0;
    /**
     * The constant defaultResolution.
     */
    public static Resolution defaultResolution = new Resolution(1920, 1080);
    /**
     * The constant defaultDuration.
     */
    public static long defaultDuration = 1000;

    private TestUtilMediaController() {}

    /**
     * Create default photo photo.
     *
     * @return the photo
     */
    public static Photo createDefaultPhoto() {
        return new Photo(
            defaultFile,
            defaultName,
            defaultDate,
            defaultDescription,
            defaultIsPrivate,
            defaultTags,
            defaultZoomFactor,
            defaultRating,
            defaultOrientation,
            defaultResolution
        );
    }

    /**
     * Create default photo 2 photo.
     *
     * @return the photo
     */
    public static Photo createDefaultPhoto2() {
        return new Photo(
            defaultFile + "2",
            defaultName,
            defaultDate,
            defaultDescription,
            defaultIsPrivate,
            defaultTags,
            defaultZoomFactor,
            defaultRating,
            defaultOrientation,
            defaultResolution
        );
    }

    /**
     * Create default video video.
     *
     * @return the video
     */
    public static Video createDefaultVideo() {
        return new Video(
            defaultFile,
            defaultName,
            defaultDate,
            defaultDescription,
            defaultIsPrivate,
            defaultTags,
            defaultDuration,
            defaultRating,
            defaultOrientation,
            defaultResolution
        );
    }
}
