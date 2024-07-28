package de.fhkiel.fotomanager.model.mediatypes;

import de.fhkiel.fotomanager.model.taglists.TagList;
import de.fhkiel.fotomanager.model.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Media.
 */
@Data
@AllArgsConstructor
public abstract class Media {
    /**
     * The file path.
     */
    private String file;
    /**
     * The name of the media.
     */
    private String name;
    /**
     * The date of the media.
     */
    private LocalDate date;
    /**
     * The description of the media.
     */
    private String description;
    /**
     * The privacy-attribute of the media.
     * Influences if the media is able to be in a slideshow.
     */
    private boolean isPrivate;
    /**
     * The tags of the media.
     */
    private TagList tags;
    /**
     * The rating of the media.
     */
    private Rating rating;
    /**
     * The orientation of the media.
     */
    private Orientation orientation;
    /**
     * The resolution of the media.
     */
    private Resolution resolution;

    /**
     * Renames the media name
     *
     * @param newName the new name
     */
    public void rename(String newName) {
        if (newName == null || newName.isEmpty()) throw new IllegalArgumentException("Name cannot be null or empty");
        this.name = newName;
    }

    /**
     * Adds a tag to the media
     *
     * @param tag to add
     */
    public void addTag(Tag tag) {
        tags.addTag(tag);
    }

    /**
     * Removes a tag from the media
     *
     * @param tag to remove
     */
    public void removeTag(Tag tag) {
        tags.removeTag(tag);
    }

    /**
     * Sets all attributes to null so the garbage collector can delete the media
     *
     * @return true if the media was deleted successfully
     */
    public boolean delete() {
        this.file = null;
        this.name = null;
        this.date = null;
        this.description = null;
        this.isPrivate = false;
        this.rating = null;
        this.orientation = null;
        this.resolution = null;
        this.tags = null;
        return true;
    }

    /**
     * Rotate the media clockwise.
     *
     * @throws UnexpectedException If the orientation is not one of the four possible orientations.
     */
    public void rotateClockwise() {
        Orientation currentOrientation = this.orientation;

        switch (currentOrientation) {
            case D0 -> this.orientation = Orientation.D90;
            case D90 -> this.orientation = Orientation.D180;
            case D180 -> this.orientation = Orientation.D270;
            case D270 -> this.orientation = Orientation.D0;
        }
    }

    /**
     * Rotate the media counter-clockwise.
     *
     * @throws UnexpectedException If the orientation is not one of the four possible orientations.
     */
    public void rotateCounterClockwise() {
        Orientation currentOrientation = this.orientation;

        switch (currentOrientation) {
            case D0 -> this.orientation = Orientation.D270;
            case D90 -> this.orientation = Orientation.D0;
            case D180 -> this.orientation = Orientation.D90;
            case D270 -> this.orientation = Orientation.D180;
        }
    }

    /**
     * Check if this media object is equal to another object.
     * @param obj the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Media)) {
            return false;
        }
        Media media = (Media) obj;
        return file.equals(media.file)
                && name.equals(media.name)
                && date.equals(media.date)
                && description.equals(media.description)
                && isPrivate == media.isPrivate
                && tags.equals(media.tags)
                && rating.equals(media.rating)
                && orientation.equals(media.orientation)
                && resolution.equals(media.resolution);
    }
}